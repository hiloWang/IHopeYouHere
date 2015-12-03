package com.hilo.requesthttp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hilo.R;
import com.hilo.activity.BaseActivity;
import com.hilo.activity.LoginActivity;
import com.hilo.bean.PacketBase;
import com.hilo.others.Company;
import com.hilo.others.Configuration;
import com.hilo.others.Fields;
import com.hilo.others.MyApplication;
import com.hilo.util.AESUtils;
import com.hilo.util.LogUtils;
import com.hilo.util.Utils;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HttpClient {

    public static final int STAUS_OK = 200;
    /**
     * 服务器返回成功但是无数据
     */
    public static final int STATUS_OK_DATA_BACK_NULL = 204;
    /**
     * 服务器无响应或网络问题
     */
    public static final int STATUS_NET_ERROR = 400;
    /**
     * 404
     */
    public static final int STATUS_NET_NOT_FOUND = 404;

    /**
     * httpGet同步请求， JSON转对象。仅封装了PacketBase对象的Message。
     *
     * @param url       url地址
     * @param classt    实体bean，需继承PacketBase。
     * @param isDecrypt 是否需要解密
     * @return PacketBase对象： int ErrorCode; String ErrorMessage;
     */
    public static Object httpGet(final String url, final Class<?> classt, boolean isDecrypt) {
        LogUtils.I("mlog", url);

        final MyCountDownLatch latch = new MyCountDownLatch(1); // 同步辅助类,
        // count：一个工人工作
        new Thread() {
            HttpControl.SapHttpResult httpResult = null;

            public void run() {
                httpResult = HttpControl.httpGet(url);
                LogUtils.I("mlog", "" + httpResult.result);
                latch.httpResult = httpResult;
                latch.countDown(); // 当前线程调用此方法，则计数减一
            }
        }.start();

        try {
            latch.await(); // 一直阻塞当前线程，等待所有工人完成工作， 直到计时器的值为0。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dataProc(latch.httpResult, classt, isDecrypt);
    }

    /**
     * 封装了POST、GET、DELETE异步请求，以及sendMessage(msg)。
     *
     * @param handler handler对象
     * @param what    消息类型
     * @param param   请求可变参数
     */
    public static void HttpGet(final Handler handler, final int what, final Class<?> classt, final String mUrl, final boolean isDecrypt, String... param) {
        final StringBuilder sb = new StringBuilder(mUrl); // http://cloud.sap360.com.cn:51001/MasterData/Partners
        int i = 0;
        String content = null;
        for (; i < param.length; i++) {
            sb.append('/');
            String params = param[i];
            try {
                params = URLEncoder.encode(param[i], "utf-8");
            } catch (UnsupportedEncodingException e) {
                params = param[i];
                e.printStackTrace();
            }
            sb.append(params);
        }
        new HttpClient().new MyThread(sb.toString(), content) {
            public void run() {
                HttpControl.SapHttpResult httpResult = null;
                httpResult = HttpControl.httpGet(sb.toString());
                LogUtils.I("mlog", "url" + sb.toString());
                LogUtils.I("mlog", "httpResult" + httpResult.result);
                dataProc(httpResult, handler, what, classt, isDecrypt);
            }

            ;
        }.start();
    }

    /**
     * HttpPost请求，封装了sendMessage(msg)。
     *
     * @param handler   handler对象
     * @param what      消息类型
     * @param url       url地址（包含分支节点）
     * @param content   对象转串
     * @param classt    实体bean，需继承PacketBase。
     * @param isEncrypt post数据是否需要加密
     * @param isDecrypt 返回数据是否要解密
     */
    public static void httpPost(final Handler handler, final int what, final String url, final String content, final Class<?> classt, final boolean isEncrypt, final boolean isDecrypt) {
        LogUtils.I("mlog", url + "\n" + content);

        new Thread() {
            HttpControl.SapHttpResult httpResult = null;

            public void run() {
                String aescontent = content;
                if (isEncrypt) try {
                    String aesString = AESUtils.encode(content, AESUtils.Key, AESUtils.IV);
                    aescontent = Utils.getGsonInstance().toJson(aesString);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                httpResult = HttpControl.httpPost(url, aescontent);
                dataProc(httpResult, handler, what, classt, isDecrypt);
            }

            ;
        }.start();
    }

    /**
     * HttpPost同步请求，JSON转对象。仅封装了PacketBase对象的Message。
     *
     * @param url     url地址（包含分支节点）
     * @param content 对象转串
     * @param classt  实体bean，需继承PacketBase。
     * @return PacketBase对象： int ErrorCode; String ErrorMessage;
     */
    public static Object httpPost(final String url, final String content, final Class<?> classt, final boolean isEncrypt) {
        LogUtils.I("mlog", url + "\n" + content);

        final MyCountDownLatch latch = new MyCountDownLatch(1);
        new Thread() {
            HttpControl.SapHttpResult httpResult = null;

            public void run() {
                String aescontent = content;
                if (isEncrypt) try {
                    String aesString = AESUtils.encode(content, AESUtils.Key, AESUtils.IV);
                    aescontent = Utils.getGsonInstance().toJson(aesString);
                    LogUtils.I("mlog", "aescontent-----" + aescontent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                httpResult = HttpControl.httpPost(url, aescontent);
                LogUtils.I("mlog", "" + httpResult.result);
                latch.httpResult = httpResult;
                latch.countDown();
            }

            ;
        }.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dataProc(latch.httpResult, classt, isEncrypt);
    }

    /**
     * 封装了POST、GET、DELETE异步请求，以及sendMessage(msg)。
     *
     * @param handler handler对象
     * @param what    消息类型
     * @param cfg     封装api
     * @param param   请求可变参数
     */
    public static void HttpType(final Handler handler, final int what, final ReqParam cfg, String... param) {
        Configuration c = Configuration.getConfig(); // 用户登录配置
        StringBuilder sb = new StringBuilder(c.serverAddress + "/" + cfg.cmd); // http://cloud.sap360.com.cn:51001/MasterData/Partners

        if (cfg.hasSessionKey) {
            sb.append("/" + c.sessionKey); // http://cloud.sap360.com.cn:51001/MasterData/Partners/20d30dd0-441e-4961-a60f-12fbf343ec69
        }

        int i = 0;
        String url = "";
        String content = null;
        if (cfg.invokeMethod == ReqParam.GET || cfg.invokeMethod == ReqParam.DELETE) {
            for (; i < param.length; i++) {
                sb.append('/');
                String params = param[i];
                try {
                    params = URLEncoder.encode(param[i], "utf-8");
                } catch (UnsupportedEncodingException e) {
                    params = param[i];
                    e.printStackTrace();
                }
                sb.append(params);
            }
        } else if (cfg.invokeMethod == ReqParam.POST) {
            for (; i < param.length - 1; i++) {
                sb.append('/');
                sb.append(param[i]);
            }
            if (cfg.isEncrypt) try {
                String aesString = AESUtils.encode(param[i], AESUtils.Key, AESUtils.IV);
                content = Utils.getGsonInstance().toJson(aesString);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            else content = param[i]; // json串:
            // {"MailType":"3","RefDate":"2015.03.11 11:58:17.000","OptType":1}
        }
        if (cfg.hasToken) {
            // 生成Token值并拼接到Url后面
            String token = createToken(cfg, param);
            if (!TextUtils.isEmpty(token)) sb.append("/").append(token);
        }
        url = sb.toString(); // http://cloud.sap360.com.cn:51001/mail/read/20d30dd0-441e-4961-a60f-12fbf343ec69
        new HttpClient().new MyThread(url, content) {
            public void run() {
                HttpControl.SapHttpResult httpResult;
                if (cfg.invokeMethod == ReqParam.GET) {
                    httpResult = HttpControl.httpGet(mUrl);
                } else if (cfg.invokeMethod == ReqParam.POST) {
                    httpResult = HttpControl.httpPost(mUrl, mContent, cfg);
                } else {
                    httpResult = HttpControl.httpDelete(mUrl);
                }
                dataProc(httpResult, handler, what, cfg.praseClass, cfg.isDecrypt);
            }

            ;
        }.start();
    }

    /**
     * 把api的参数名（转换成大写）+参数值按参数名排序生成一个字符串,<br>
     * 再加上Post的字符串 (如果post流数据(比如图片,文件)，则不用处理)<br>
     * 然后再在字符串的前面和后面添加公司的安全码(SecretCode) <br>
     * 将以上字符串生成Md5码(全部转成大写)
     *
     * @param param 所有的参数值
     * @param cfg
     * @return
     */
    public static String createToken(ReqParam cfg, String... param) {
        String token = "";
        String postParam = null;
        if (cfg.invokeMethod == ReqParam.POST) {
            postParam = param[param.length - 1];
            param = Arrays.copyOf(param, param.length - 1);
        }
        StringBuffer tokenSb = new StringBuffer();
        String[] oldParamsNames = null;
        if (cfg.parameterNames != null)
            oldParamsNames = Arrays.copyOf(cfg.parameterNames, cfg.parameterNames.length);
        // 前后拼接安全码
        tokenSb.append(Company.getInstance().SecretCode);

        String[] upParameterNams;
        HashMap<String, String> parameAndValue = new HashMap<>();
        if (cfg.hasSessionKey) {// 把SessionKey参数也添加进去
            if (oldParamsNames == null) oldParamsNames = new String[0];
            oldParamsNames = Arrays.copyOf(oldParamsNames, oldParamsNames.length + 1);
            oldParamsNames[oldParamsNames.length - 1] = "sessionKey";
            if (param == null) param = new String[0];
            param = Arrays.copyOf(param, param.length + 1);
            param[param.length - 1] = Configuration.getConfig().sessionKey;
        }
        if (oldParamsNames != null && oldParamsNames.length > 0) {
            upParameterNams = new String[oldParamsNames.length];
            // 把所有的参数转换成大写状态
            for (int i = 0; i < upParameterNams.length; i++) {
                upParameterNams[i] = oldParamsNames[i].toUpperCase();
                parameAndValue.put(upParameterNams[i], param[i]);
            }
            // 按参数名进行排序
            Arrays.sort(upParameterNams);
            // 把参数名与参数值连接起来
            for (String parName : upParameterNams) {
                String value = parameAndValue.get(parName);
                tokenSb.append(parName).append(value);
            }
        }
        if (cfg.invokeMethod == ReqParam.POST) {
            // 把Post数据拼接起来
            if (postParam != null) tokenSb.append(postParam);
        }

        // 前后拼接安全码
        tokenSb.append(Company.getInstance().SecretCode);
        token = AESUtils.MD5Encode(tokenSb.toString(), "utf-8").toLowerCase();
        return token;
    }

    /**
     * 封装了POST、GET、DELETE同步请求，JSON转对象。仅封装了PacketBase对象的Message。
     *
     * @param cfg   封装api
     * @param param 请求可变参数
     * @return PacketBase对象
     */
    public static Object HttpType(final ReqParam cfg, String... param) {
        Configuration c = Configuration.getConfig(); // 用户登录配置
        StringBuilder sb = new StringBuilder(c.serverAddress + "/" + cfg.cmd);

        if (cfg.hasSessionKey) {
            sb.append("/" + c.sessionKey);
        }

        int i = 0;
        String url;
        String content = null;
        if (cfg.invokeMethod == ReqParam.GET || cfg.invokeMethod == ReqParam.DELETE) {
            for (; i < param.length; i++) {
                String params = param[i];
                try {
                    params = URLEncoder.encode(param[i], "utf-8");
                } catch (UnsupportedEncodingException e) {
                    params = param[i];
                    e.printStackTrace();
                }
                sb.append('/');
                sb.append(params);
            }
        } else if (cfg.invokeMethod == ReqParam.POST) {
            for (; i < param.length - 1; i++) {
                sb.append('/');
                sb.append(param[i]);
            }
            if (cfg.isEncrypt) try {
                String aesString = AESUtils.encode(param[i], AESUtils.Key, AESUtils.IV);
                content = Utils.getGsonInstance().toJson(aesString);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            else content = param[i];
        } else {
            return null;
        }
        if (cfg.hasToken) {
            // 生成Token值并拼接到Url后面
            String token = createToken(cfg, param);
            if (!TextUtils.isEmpty(token)) sb.append("/").append(token);
        }
        url = sb.toString();
        LogUtils.I("mlog", url);
        if (content != null) {
            LogUtils.I("mlog", content);
        }

        final MyCountDownLatch latch = new MyCountDownLatch(1);
        new HttpClient().new MyThread(url, content) {
            HttpControl.SapHttpResult httpResult = null;

            public void run() {
                if (cfg.invokeMethod == ReqParam.GET) {
                    httpResult = HttpControl.httpGet(mUrl);
                } else if (cfg.invokeMethod == ReqParam.POST) {
                    httpResult = HttpControl.httpPost(mUrl, mContent);
                } else {
                    httpResult = HttpControl.httpDelete(mUrl);
                }
                LogUtils.I("mlog", "" + httpResult.result + "  statusCode::" + httpResult.statusCode);
                latch.httpResult = httpResult;
                latch.countDown();
            }

            ;
        }.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dataProc(latch.httpResult, cfg.praseClass, cfg.isDecrypt);
    }

    /**
     * 将串通过gson转对象，返回给调用者。
     *
     * @param classt    实体bean
     * @param isDecrypt 是否需要解密
     * @return PacketBase对象
     */
    private static Object dataProc(HttpControl.SapHttpResult httpResult, Class<?> classt, boolean isDecrypt) {
        LogUtils.I("mlog", httpResult.statusCode + "");
        // 如果是加密并且返回状态码为200，则进行解密，否则不处理
        if (isDecrypt && httpResult.statusCode == STAUS_OK) try {
            httpResult.result = AESUtils.Decrypt(httpResult.result, AESUtils.Key, AESUtils.IV);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //表示该用户已经退出
        if (httpResult.statusCode == 400 && "-1".equals(httpResult.result))
            Utils.sendbroadcastToLogin();
        if (httpResult.result == null && httpResult.statusCode != STATUS_OK_DATA_BACK_NULL && httpResult.statusCode != STAUS_OK) {
            ShowNetError(httpResult); // 封装了sendMessage()
            return null;
        } else if (httpResult.statusCode != STAUS_OK && httpResult.statusCode != STATUS_OK_DATA_BACK_NULL) {
            showErrMsg(httpResult.result + "");
            return null;
        } else if (classt != null) {
            Object v = null; // PacketBase下的成员变量:ErrorCode, ErrorMessage
            try {
                // v = (PacketBase) gson.fromJson(httpResult.result, classt); // 串转对象，str：服务器返回的串。
                v = transResult(httpResult, classt, null);
            } catch (Exception e) {
                LogUtils.I("mlog", e.toString());
            }

            if (v != null) {
                // if (v.ErrorCode < 0) {
                // showErrMsg(v);
                // }
            } else {
                // showErrMsg(R.string.neterr_parse);
            }
            return v;
        } else {
            return httpResult.result; // 如果没有调用者传入对象，则把传入的str返回。
        }
    }

    /**
     * JSON转对象。封装了sendMessage(msg)。
     *
     * @param handler   handler对象
     * @param what      消息类型
     * @param classt    PacketBase封装的实体bean
     * @param isDecrypt 数据是否解密
     */
    private static void dataProc(HttpControl.SapHttpResult httpResult, Handler handler, int what, Class<?> classt, boolean isDecrypt) {
        //表示该用户已经退出
        if (httpResult.statusCode == 400 && "-1".equals(httpResult.result))
            Utils.sendbroadcastToLogin();
        // 如果是加密并且返回状态码为200，则进行解密，否则不处理
        if (isDecrypt && httpResult.statusCode == STAUS_OK) try {
            httpResult.result = AESUtils.Decrypt(httpResult.result, AESUtils.Key, AESUtils.IV);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        LogUtils.I("mlog", httpResult.statusCode + "\n" + httpResult.result); // 服务端返回的串数据
        Message msg = new Message();
        msg.what = what;
        // 返回状态码
        msg.arg1 = httpResult.statusCode;
        if (httpResult.result == null && httpResult.statusCode != STAUS_OK) {
            msg.obj = null;
            if (handler != null) handler.sendMessage(msg);
            ShowNetError(httpResult);
        } else if (httpResult.statusCode != STAUS_OK) {
            msg.obj = null;
            HttpControl.mException = httpResult.result;
            if (handler != null) handler.sendMessage(msg);
            ShowNetError(httpResult);
        } else if (classt != null) {
            // PacketBase v = null;
            transResult(httpResult, classt, msg);

            // msg.obj = v; // 反序列化
            if (handler != null) handler.sendMessage(msg); // 有bean传入，将对象发送给hander

            // if(v != null){
            // if(v.ErrorCode < 0){
            // showErrMsg(v);
            // }
            // }else{
            // if (msg.obj == null)
            // showErrMsg(R.string.neterr_parse);
            // }
        } else {
            msg.obj = httpResult.result; // classt==null： 如果没有bean传入，服务端的则将json串发消息给hander
            if (handler != null) handler.sendMessage(msg);
        }
    }

    /**
     * @param httpResult
     * @param classt
     * @param msg
     */
    public static Object transResult(HttpControl.SapHttpResult httpResult, Class<?> classt, Message msg) {
        Gson gson = Utils.getGsonInstance();
        try {
            List<Object> resultArray = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(httpResult.result);
            for (int i = 0; i < jsonArray.length(); i++) {
                resultArray.add(gson.fromJson(jsonArray.get(i).toString(), classt));
            }
            if (msg != null) msg.obj = resultArray;
            return resultArray;
        } catch (Exception e) {
            Object object = gson.fromJson(httpResult.result, classt);
            if (msg != null) msg.obj = object;
            return object;
        }
    }

    /**
     * CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
     */
    private static class MyCountDownLatch extends CountDownLatch {
        HttpControl.SapHttpResult httpResult = null;
        protected String str;

        public MyCountDownLatch(int count) {
            super(count);
        }
    }

    /**
     * 监听网络状态变化
     *
     * @param context 应用程序上下文
     * @return true 网络连接是否可用 false 无网络连接状态
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 同步的HttpGET请求，网络下载，将响应内容写入文件中。
     *
     * @param pathfile 文件全路径
     * @param url      url地址
     */
    public static void download(final String pathfile, final String url) {
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread() {
            public void run() {
                HttpControl.httpDown(pathfile, url);
                latch.countDown();
            }
        }.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     *
     * @param handler  handler对象
     * @param what     消息类型
     * @param pathfile 文件路径
     * @return 文件名
     */
    public static String upLoadFileDetach(final Handler handler, final int what, final String pathfile) {
        final Company comp = Company.getInstance();
        //如果是公有云，则先看服务端有没有该文件，如果有则不上传
        if (comp.PrivateCloud == Fields.NO) {
            File file = new File(pathfile);
            if (file.exists()) {
                String md5Url = Company.getInstance().UploadUrl + "/api/File/GetRemoteFileName/" + Utils.base64String(Configuration.getConfig().companyCode) + "/" + Utils.getFileMD5(file);
                String result = (String) httpGet(md5Url, null, false);
                if (!TextUtils.isEmpty(result)) {//表示验证该文件在服务端已经存在，不需要再上传
                    Message msg = new Message();
                    msg.what = what;
                    msg.arg1 = 100;
                    msg.arg2 = 100;
                    if (handler != null) handler.sendMessage(msg);
                    return result;
                }
            }
        }
        final MyCountDownLatch latch = new MyCountDownLatch(1);
        final int buf_size;
        ConnectivityManager cm = (ConnectivityManager) MyApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!isNetworkConnected(MyApplication.mContext)) {
            return null;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            buf_size = 1024 * 100;
        } else {
            buf_size = 1024 * 20;
        }
        new Thread() {
            public void run() {

                String urlPath = comp.UploadUrl + "/api/File/Post";
                if (comp.PrivateCloud == Fields.YES)
                    urlPath = comp.UploadUrl + "/UpLoadFile/" + comp.docKey + "/";
                String url = "";
                UploadBack back = new UploadBack();
                back.FileName = "";
                byte[] buf = new byte[buf_size];
                try {
                    String extName = "/" + getExtName(pathfile);

                    FileInputStream input = new FileInputStream(pathfile);
                    int divideCount = input.available() % buf_size;
                    int count = divideCount == 0 ? (input.available() / buf_size) : (input.available() / buf_size + 1);
                    boolean isLast = false;
                    for (int i = 0; ; i++) {
                        int rev = input.read(buf);
                        if (rev == -1) {
                            break;
                        }
                        if (i == count - 1) isLast = true;
                        //如果是私有云按照之前的方式来Post数据
                        if (comp.PrivateCloud == Fields.YES) {
                            if (i == 0) {
                                url = urlPath + null + extName;
                            } else {
                                url = urlPath + back.FileName + extName;
                            }
                        } else url = urlPath;
                        if (rev < buf_size) {
                            byte[] tmp = new byte[rev];
                            System.arraycopy(buf, 0, tmp, 0, rev);
                            buf = tmp;
                        }
                        HttpControl.SapHttpResult httpResult = null;
                        if (comp.PrivateCloud == Fields.YES)
                            httpResult = HttpControl.httpPost(url, buf);
                        else
                            httpResult = HttpControl.httpPost(url, buf, pathfile, back.FileName, isLast);
                        if (httpResult.statusCode == HttpClient.STAUS_OK)
                            back.FileName = httpResult.result;
                        if (comp.PrivateCloud == Fields.YES) {
                            //如果是私有云，返回的Json需要先解析再获取文件名
                            back = Utils.parseFromJson(httpResult.result, UploadBack.class);
                        }
                        Message msg = new Message();
                        msg.what = what;
                        msg.arg1 = input.available();
                        msg.arg2 = buf.length;
                        if (handler != null) handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    LogUtils.I("mlog", e.toString());
                }
                if (back != null) {
                    latch.str = back.FileName;
                }
                latch.countDown();
            }
        }.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return latch.str;
    }

    private static String getExtName(String pathfile) {
        String ext;
        int pos = pathfile.lastIndexOf("/");
        if (pos > 0) {
            ext = pathfile.substring(pos);
            pos = ext.lastIndexOf(".");
            if (pos > 0) {
                return ext.substring(pos);
            }
        }
        return null;
    }

    /**
     * 文件上传
     *
     * @param pathfile 文件路径
     * @return 文件名
     */
    public static String upLoadFile(final String pathfile) {
        final MyCountDownLatch latch = new MyCountDownLatch(1);
        new Thread() {
            public void run() {
                Company comp = Company.getInstance();
                if (comp.PrivateCloud == Fields.YES) {//私有云用的还是原来的上传地址
                    String urlPath = comp.UploadUrl + "/UpLoadFile/" + comp.docKey + "/" + null + "/" + getExtName(pathfile);
                    String str = HttpControl.httpUpLoad(urlPath, pathfile);
                    UploadBack back = Utils.parseFromJson(str, UploadBack.class);
                    if (back != null) {
                        latch.str = back.FileName;
                    }
                } else {//如果是公有云则先判断MD5码再判断是否上传
                    File file = new File(pathfile);
                    if (file.exists()) {
                        String md5Url = comp.UploadUrl + "/api/File/GetRemoteFileName/" + Utils.base64String(Configuration.getConfig().companyCode) + "/" + Utils.getFileMD5(file);
                        String result = (String) httpGet(md5Url, null, false);
                        if (!TextUtils.isEmpty(result)) {//表示验证该文件在服务端已经存在，不需要再上传
                            latch.str = result;
                        } else {//服务器上没有文件，必须上传该文件
                            String urlPath = comp.UploadUrl + "/api/File/Post";
                            String str = HttpControl.httpUpLoad(urlPath, pathfile);
                            if (str != null) {
                                latch.str = str;
                            }
                        }
                    }

                }

                latch.countDown();
            }
        }.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return latch.str;
    }

    /**
     * 显示错误信息： 联网超时、服务器无数据返回、服务器拒绝请求、服务器应答错误。
     *
     * @param httpResult
     */
    static void ShowNetError(HttpControl.SapHttpResult httpResult) {
        if (TextUtils.isEmpty(httpResult.result)) httpResult.result = "";
        int res_id = 0;
        String exception = HttpControl.mException.toLowerCase();

        if (exception.contains("timeout")) res_id = R.string.neterr_timeout;
        else if (exception.contains("nullpointer")) res_id = R.string.neterr_nullpt;
        else if (exception.contains("refused")) res_id = R.string.neterr_refused;
        else if (exception.contains("response")) res_id = R.string.neterr_response;
        else if (httpResult.statusCode == STATUS_NET_ERROR) {
            res_id = R.string.netrr_connected_error;
        } else if (STATUS_NET_NOT_FOUND == httpResult.statusCode) {
            res_id = R.string.netrr_connected_not_found;
        } else if (!TextUtils.isEmpty(httpResult.result)) {
            showErrMsg(httpResult.result);
        } else {
            res_id = R.string.neterr_error;
            LogUtils.I("mlog", httpResult.result);
        }
        if (res_id != 0) showErrMsg(res_id);
    }

    /**
     * 网络异常,错误码
     */
    static void ShowNetErrorCode() {
        String str = MyApplication.mContext.getResources().getString(R.string.neterr_errorcode) + HttpControl.StatusCode;
        showErrMsg(str);
    }

    /**
     * ErrorMessage、ErrorCode。
     *
     * @param pb PacketBase 对象
     */
    static void showErrMsg(PacketBase pb) {
        Message msg = new Message();
        msg.obj = pb.ErrorMessage;
        msg.arg1 = pb.ErrorCode;
        HttpClient.mHandler.sendMessage(msg);
    }

    /**
     * 显示错误信息、ErrorCode为0
     *
     * @param str 错误信息
     */
    static void showErrMsg(String str) {
        Message msg = new Message();
        msg.obj = str;
        msg.arg1 = 0;
        HttpClient.mHandler.sendMessage(msg);
    }

    /**
     * 将资源文件下，StringId，定义好的错误信息传入并显示。
     *
     * @param res_id context.getResources().getString(res_id);
     */
    static void showErrMsg(int res_id) {
        showErrMsg(MyApplication.mContext.getResources().getString(res_id));
    }

    public class MyThread extends Thread {
        protected String mUrl;
        protected String mContent;

        /**
         * 自定义MyThread类，继承Thread。
         *
         * @param url     url地址
         * @param content 对象转串
         */
        public MyThread(String url, String content) {
            mUrl = url;
            mContent = content;
        }
    }

    /**
     * HttpClient封装的handler。 封装Toast： 弹出错误日志信息
     */
    public static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String message = (String) msg.obj;
            if (!TextUtils.isEmpty(message) && !"没有查询到数据".equals(message))
                Toast.makeText(MyApplication.mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
            if (msg.arg1 == -1) {
                try {
                    Activity act = BaseActivity.mActivityArray.getLast();
                    if (act instanceof LoginActivity == false) {
                        Utils.appLogout(act); // 退出activity
                    }
                } catch (Exception e) {
                }
            }
        }
    };

    public static int getNetWorkType() {
        ConnectivityManager connectMgr = (ConnectivityManager) MyApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info == null) return -1;
        return info.getType();
    }

    static class UploadBack {
        public String DocumentKey;
        public int ErrorCode;
        public String ErrorMsg;
        public String FileName;
        public int FileSize;
    }

}
