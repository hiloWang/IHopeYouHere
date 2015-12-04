package com.hilo.others;

import android.os.Environment;
import android.text.TextUtils;

import com.hilo.requesthttp.HttpClient;
import com.hilo.requesthttp.HttpParse;
import com.hilo.requesthttp.ReqParam;
import com.hilo.util.AESUtils;
import com.hilo.util.LogUtils;
import com.hilo.util.T;
import com.hilo.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hilo on 2015/12/3.
 * <p/>
 * Drscription:
 * <p/>
 * /**
 * 1. callback的泛型:
 * callback参数默认支持的泛型类型参见{@link org.xutils.http.loader.LoaderFactory},
 * 例如: 指定泛型为File则可实现文件下载, 使用params.setSaveFilePath(path)指定文件保存的全路径.
 * 默认支持断点续传(采用了文件锁和尾端校验续传文件的一致性).
 * 其他常用类型可以自己在LoaderFactory中注册,
 * 也可以使用{@link org.xutils.http.annotation.HttpResponse}
 * 将注解HttpResponse加到自定义返回值类型上, 实现自定义ResponseParser接口来统一转换.
 * 如果返回值是json形式, 那么利用第三方的json工具将十分容易定义自己的ResponseParser.
 * 如示例代码{@link org. xutils. sample.http.BaiduResponse}, 可直接使用BaiduResponse作为
 * callback的泛型.
 * <p/>
 * 2. callback的组合:
 * 可以用基类或接口组合个种类的Callback, 见{@link org.xutils.common.Callback}.
 * 例如:
 * a. 组合使用CacheCallback将使请求检测缓存或将结果存入缓存(仅GET请求生效).
 * b. 组合使用PrepareCallback的prepare方法将为callback提供一次后台执行耗时任务的机会,
 * 然后将结果给onCache或onSuccess.
 * c. 组合使用ProgressCallback将提供进度回调.
 * ...(可参考{@link org.xutils.image.ImageLoader}
 * 或 示例代码中的 {@link org. xutils. sample. download.DownloadCallback})
 * <p/>
 * 3. 请求过程拦截或记录日志: 参考 {@link org.xutils.http.app.RequestTracker}
 * <p/>
 * 4. 请求Header获取: 参考 {@link org.xutils.http.app.InterceptRequestListener}
 * <p/>
 * 5. 其他(线程池, 超时, 重定向, 重试, 代理等): 参考 {@link org.xutils.http.RequestParams
 */
public class HttpFactory {

    private static HttpFactory httpFactory;
    private HttpParamss params;
    private Callback.Cancelable cancelable;

    public HttpFactory() {}

    public static final HttpFactory getInstance() {
        if (httpFactory == null) {
            synchronized (HttpFactory.class) {
                if (httpFactory == null) {
                    httpFactory = new HttpFactory();
                }
            }
        }
        return httpFactory;
    }

    public void upLoadFile() {
        params = new HttpParamss();
        setupHttpParams(params);
    }

    public void cancelNow() {
        if(cancelable != null)
            cancelable.cancel();
    }

    /**
     * 获取服务器ServerUrl和port
     *
     * @param entity
     *                          Entity 数据
     * @param url
     *                          请求url
     * @param isEncryptJsonContent
     *                          request 数据是否加密
     * @param isDecrypt
     *                          result 数据是否解密
     */
    public void requestServerUrl(String entity, String url, boolean isEncryptJsonContent, boolean isDecrypt, boolean POST) {
        String aesContent = entity;
        if (isEncryptJsonContent) try {
            String aesString = AESUtils.encode(entity, AESUtils.Key, AESUtils.IV);
            aesContent = Utils.getGsonInstance().toJson(aesString);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(POST)
            httpPost(url, aesContent, isDecrypt);
        else
            httpGet(url, aesContent, isDecrypt);
    }

    /**
     * 返回请求服务器的url
     *
     * @param cfg
     *          ReqParam
     * @param param
     *          可变参数
     *
     * @return  服务端的 url
     */
    public void requestHttp(ReqParam cfg, String ... param) {
        Configuration c = Configuration.getConfig();
        StringBuilder sb = new StringBuilder(c.serverAddress + "/" + cfg.cmd); // http://cloud.sap360.com.cn:51001/MasterData/Partners
        if (cfg.hasSessionKey) {
            sb.append("/" + c.sessionKey); // http://cloud.sap360.com.cn:51001/MasterData/Partners/20d30dd0-441e-4961-a60f-12fbf343ec69
        }
        int i = 0;
        String serverUrl, encryptJsonContent = null;
        if (cfg.invokeMethod == ReqParam.GET || cfg.invokeMethod == ReqParam.DELETE) {
            for (; i < param.length; i++) {
                sb.append('/');
                String params;
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
                encryptJsonContent = Utils.getGsonInstance().toJson(aesString);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            else encryptJsonContent = param[i]; // json串: {"MailType":"3","RefDate":"2015.03.11 11:58:17.000","OptType":1}
        }
        if (cfg.hasToken) {
            // 生成Token值并拼接到Url后面
            String token = HttpClient.createToken(cfg, param);
            if (!TextUtils.isEmpty(token)) sb.append("/").append(token);
        }
        serverUrl = sb.toString(); // http://cloud.sap360.com.cn:51001/mail/read/20d30dd0-441e-4961-a60f-12fbf343ec69
        LogUtils.I("<ServerUrl: >" + serverUrl + " \n <isEncryptJsonContent: >" + encryptJsonContent);
        switch (cfg.invokeMethod) {
            case ReqParam.POST:
                httpPost(serverUrl, encryptJsonContent, cfg.isDecrypt);
                break;
            case ReqParam.GET:
                httpGet(serverUrl, encryptJsonContent, cfg.isDecrypt);
                break;
        }
    }




    private void setupHttpParams(HttpParamss params) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 有上传文件时使用multipart表单, 否则上传原始文件流.
            params.setMultipart(true);
            // 上传文件方式 1
            params.uploadFile = new File("/sdcard/test.txt");
            // 上传文件方式 2
            // params.addBodyParameter("uploadFile", new File("/sdcard/test.txt"));
            params.setSaveFilePath(params.uploadFile.getAbsolutePath());
        }
    }

    // 如果需要记录请求的日志, 可使用RequestTracker接口(优先级依次降低, 找到一个实现后会忽略后面的):
    // 1. 自定义Callback同时实现RequestTracker接口;
    // 2. 自定义ResponseParser同时实现RequestTracker接口;
    // 3. 在LoaderFactory注册.
    private void httpGet(HttpParamss params) {
        cancelable = x.http().get(params,
                new Callback.CommonCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        T.showLong(x.app(), result.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        T.showLong(x.app(), ex.getMessage());
                        if (ex instanceof HttpException) { // 网络错误
                            HttpException httpEx = (HttpException) ex;
                            T.showLong(x.app(), "<responseCode~: " + httpEx.getCode() + "> \n" +
                                    "<responseMsg~: " + httpEx.getMessage() + "> \n" +
                                    "<errorResult~: " + httpEx.getResult() + "> \n");
                            // ...
                        } else { // 其他错误
                            // ...
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        T.showLong(x.app(), "cancelled");
                    }

                    @Override
                    public void onFinished() {
                        // success
                    }
                });
    }

    private void httpPost(HttpParamss params) {
        cancelable = x.http().post(params,
                new Callback.CommonCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        T.showLong(x.app(), result.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        T.showLong(x.app(), ex.getMessage());
                        if (ex instanceof HttpException) { // 网络错误
                            HttpException httpEx = (HttpException) ex;
                            T.showLong(x.app(), "<responseCode~: " + httpEx.getCode() + "> \n" +
                                    "<responseMsg~: " + httpEx.getMessage() + "> \n" +
                                    "<errorResult~: " + httpEx.getResult() + "> \n");
                            // ...
                        } else { // 其他错误
                            // ...
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        T.showLong(x.app(), "cancelled");
                    }

                    @Override
                    public void onFinished() {
                        // success
                    }
                });
    }

    private void httpGet(String serverUrl, String params, final boolean isDecrypt) {
        RequestParams mRequestParams = new RequestParams(serverUrl);
        mRequestParams.setBodyContent(params);
        cancelable = x.http().get(mRequestParams,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtils.I("mlog", result);
                        if(isDecrypt) {
                            try {
                                result = AESUtils.Decrypt(result, AESUtils.Key, AESUtils.IV);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        HttpParse.NotifyGroup mNotifyGroup = Utils.getGsonInstance().fromJson(result, HttpParse.NotifyGroup.class);
                        LogUtils.I("NotifyGroup: " + mNotifyGroup.toString());
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LogUtils.E("mlog", ex.getMessage());
                        if (ex instanceof HttpException) { // 网络错误
                            HttpException httpEx = (HttpException) ex;
                            LogUtils.E("mlog", "<responseCode~: " + httpEx.getCode() + "> \n" +
                                    "<responseMsg~: " + httpEx.getMessage() + "> \n" +
                                    "<errorResult~: " + httpEx.getResult() + "> \n");
                            // ...
                        } else { // 其他错误
                            // ...
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        T.showLong(x.app(), "cancelled");
                    }

                    @Override
                    public void onFinished() {
                        // success
                    }
                });
    }

    private void httpPost(String serverUrl, String encryptJsonContent, final boolean isDecrypt) {
        final RequestParams params = new RequestParams(serverUrl);
        params.setBodyContent(encryptJsonContent);
        cancelable = x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        T.showLong(x.app(), result.toString());
                        try {
                            if(isDecrypt) {
                                result = AESUtils.Decrypt(result, AESUtils.Key, AESUtils.IV);
                            }
                            // 反序列化
                            JSONArray mJSONArray = new JSONArray(result);
                            HttpParse.LoginVerify mLoginVerify = null;
                            for(int i=0; i<mJSONArray.length(); i++) {
                                mLoginVerify = Utils.getGsonInstance().fromJson(mJSONArray.get(i).toString(), HttpParse.LoginVerify.class);
                            }
                            LogUtils.I(result.toString());
                        } catch (JSONException jsonEx) {
                            HttpParse.LoginVerify mLoginVerify = Utils.getGsonInstance().fromJson(result, HttpParse.LoginVerify.class);
                            LogUtils.I("LoginVerify: " + mLoginVerify.toString());
                            Configuration cfg = Configuration.getConfig();
                            cfg.expiredDate = mLoginVerify.ExpiredDate;
//                            cfg.serverAddress = "http://" + mLoginVerify.ServerName + ":" + mLoginVerify.Port;
                            cfg.serverAddress = "http://cloud2.sap360.com.cn:36010";
                            Configuration.saveConfig();
                            requestHttp(ReqParam.test, Utils.base64String("kaifaku2"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        T.showLong(x.app(), ex.getMessage());
                        if (ex instanceof HttpException) { // 网络错误
                            HttpException httpEx = (HttpException) ex;
                            LogUtils.E("<responseCode~: " + httpEx.getCode() + "> \n" +
                                    "<responseMsg~: " + httpEx.getMessage() + "> \n" +
                                    "<errorResult~: " + httpEx.getResult() + "> \n");
                            // ...
                        } else { // 其他错误
                            // ...
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        T.showLong(x.app(), "onCancelled");
                    }

                    @Override
                    public void onFinished() {
                        // success
                        T.showLong(x.app(), "onFinished");
                    }
                });
    }


    @HttpRequest(
            host = "http://cloud.sap360.com.cn:51001",
            path = "",
            builder = DefaultParamsBuilder.class // 可选参数, 控制参数构建过程, 定义参数签名, SSL证书等
    )
    class HttpParamss extends RequestParams {
        public String wd;
        // 数组参数 aa=1&aa=2&aa=4
        public int[] arraysParams = new int[]{1, 2, 4};
        public List<String> listsParams = new ArrayList<>();
        public long timeStamp = System.currentTimeMillis();
        public File uploadFile;

        public HttpParamss() {
//            listsParams.add("a");
//            listsParams.add("c");
        }
    }
}
