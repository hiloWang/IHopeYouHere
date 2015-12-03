package com.hilo.requesthttp;

import com.hilo.others.Company;
import com.hilo.others.Configuration;
import com.hilo.others.Fields;
import com.hilo.util.LogUtils;
import com.hilo.util.Utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.ByteArrayBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class HttpControl {
    public static String mException;
    public static int StatusCode;

    /**
     * HttpGet 请求，
     *
     * @param urlStr url地址
     * @return String 字符串
     */
    public static SapHttpResult httpGet(String urlStr) {
        SapHttpResult httpResult = new SapHttpResult();
        HttpClient httpclient = getHttpClient();
        HttpGet httpget = new HttpGet(urlStr);
        LogUtils.I("mlog", urlStr + "");
        try {
            HttpResponse response = httpclient.execute(httpget);
            httpResult.statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            Header[] headers = response.getAllHeaders();
            if (headers != null && headers.length > 0) {
                for (Header header : headers) {
                    if ("gzip".equals(header.getValue()))
                        instream = new GZIPInputStream(instream);
                }
            }

            ByteArrayBuffer baf = new ByteArrayBuffer(1024);

            byte[] byteBuf = new byte[2048];
            int l;
            while (true) {
                l = instream.read(byteBuf);
                if (l < 0)
                    break;
                baf.append(byteBuf, 0, l);
            }
            httpResult.result = new String(baf.toByteArray());
            //LogUtils.I("mlog", httpResult.result + "");
            return httpResult;
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return httpResult;
    }

    public static String get(String url) {

        HttpGet get = new HttpGet(url);
        HttpClient client = getHttpClient();
        // get.set("accept-encoding","gzip, deflate");
        // 在包头中添加gzip格式
        HttpResponse response;
        ByteArrayBuffer bt = new ByteArrayBuffer(4096);
        String resultString = "";

        try {
            response = client.execute(get);
            // 执行Get方法
            HttpEntity he = response.getEntity();
            // 以下是解压缩的过程
            GZIPInputStream gis = new GZIPInputStream(he.getContent());
            int l;
            byte[] tmp = new byte[4096];
            while ((l = gis.read(tmp)) != -1) {
                bt.append(tmp, 0, l);
            }

            resultString = new String(bt.toByteArray(), "utf-8");
            // 后面的参数换成网站的编码一般来说都是UTF-8

        } catch (Exception e) {
            LogUtils.I("ERR", e.toString()); // 抛出处理中的异常
        }

        return resultString;
    }

    // public static byte[] httpGet(String urlStr){
    // HttpClient httpclient = getHttpClient();
    // HttpGet httpget = new HttpGet(urlStr);
    // try{
    // HttpResponse response = httpclient.execute(httpget);
    // StatusCode = response.getStatusLine().getStatusCode();
    // HttpEntity entity = response.getEntity();
    //
    // InputStream instream = entity.getContent();
    //
    // ByteArrayBuffer baf = new ByteArrayBuffer(1024);
    // byte[] byteBuf = new byte[1024];
    // int l;
    // while(true){
    // l = instream.read(byteBuf);
    // if(l < 0)break;
    // baf.append(byteBuf, 0, l);
    // }
    //
    // httpclient.getConnectionManager().shutdown();
    // return baf.toByteArray();
    // }catch(Exception e){
    // Log.i("mlog", e.toString());
    // }
    // return null;
    // }

    /**
     * GET请求，将所有响应内容写入pathfile文件中。
     *
     * @param pathfile 文件全路径
     * @param urlStr   url地址
     * @return true 写入成功 false 写入失败
     */
    public static boolean httpDown(String pathfile, String urlStr) {
        LogUtils.I("mlog", urlStr + "");
        HttpClient httpclient = getHttpClient();
        HttpGet httpget = null;
        try {
            httpget = new HttpGet(urlStr);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        File file = null;
        try {
            file = new File(pathfile);
            FileOutputStream fw = new FileOutputStream(file);
            HttpResponse response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();

            byte[] byteBuf = new byte[10240];
            if (statusCode == 200)
                while (true) {
                    int len = instream.read(byteBuf);
                    if (len < 0)
                        break;
                    fw.write(byteBuf, 0, len);
                }
            fw.flush();
            fw.close();
            return true;
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
            if (file != null) {
                file.delete();
            }
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return false;
    }

    public static HttpEntity httpDown(String urlStr) {
        HttpClient httpclient = getHttpClient();
        HttpGet httpget = new HttpGet(urlStr);
        try {
            HttpResponse response = httpclient.execute(httpget);
            StatusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            return entity;
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
        }
        // finally{
        // httpclient.getConnectionManager().shutdown();
        // }
        return null;
    }

    public static String httpPut(String urlStr, String content) {
        HttpClient httpclient = getHttpClient();
        HttpPut httpput = new HttpPut(urlStr);
        // httpput.setHeader("Content-Type", "charset=utf-8");
        try {
            StringEntity sn = new StringEntity(content);
            httpput.setEntity(sn);
            HttpResponse response = httpclient.execute(httpput);
            StatusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();

            ByteArrayBuffer baf = new ByteArrayBuffer(2048);
            byte[] byteBuf = new byte[2048];
            int l;
            while (true) {
                l = instream.read(byteBuf);
                if (l < 0)
                    break;
                baf.append(byteBuf, 0, l);
            }

            return new String(baf.toByteArray());
            // byte[] buf = new byte[(int) entity.getContentLength()];
            // instream.read(buf);
            // return new String(buf);
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return null;
    }

    /**
     * HttpPost请求，返回服务端串数据
     *
     * @param urlStr  url地址
     * @param content 对象转串
     * @return 串
     */
    public static SapHttpResult httpPost(String urlStr, String content) {
        SapHttpResult httpResult = new SapHttpResult();
        HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost(urlStr);
        try {
            StringEntity sn = new StringEntity(content, "UTF-8");// 将参数转utf-8.
//			if (httppost.getURI().getPort() != 36010)
            sn.setContentType("application/json");
            httppost.setEntity(sn); // 设置请求参数
            HttpResponse response = httpclient.execute(httppost); // 发送请求
            httpResult.statusCode = response.getStatusLine().getStatusCode(); // 响应结果状态码
            HttpEntity entity = response.getEntity(); // 包含服务器的响应内容

            InputStream instream = entity.getContent(); // 拿到请求实体流
            Header[] headers = response.getAllHeaders();
            if (headers != null && headers.length > 0) {
                for (Header header : headers) {
                    if ("gzip".equals(header.getValue()))
                        instream = new GZIPInputStream(instream);
                }
            }

            ByteArrayBuffer baf = new ByteArrayBuffer(2048);
            byte[] byteBuf = new byte[2048];
            int l;
            while (true) {
                l = instream.read(byteBuf);
                if (l < 0)
                    break;
                baf.append(byteBuf, 0, l);
            }
            httpResult.result = new String(baf.toByteArray());
            return httpResult;
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return httpResult;
    }

    /**
     * HttpPost请求，返回服务端串数据
     *
     * @param urlStr  url地址
     * @param content 对象转串
     * @return 串
     */
    public static SapHttpResult httpPost(String urlStr, String content, ReqParam cfg) {
        SapHttpResult httpResult = new SapHttpResult();
        HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost(urlStr);
        LogUtils.I("mlog", urlStr + "");
        if (content != null) {
            LogUtils.I("mlog", content);
        }
        try {
            StringEntity sn = new StringEntity(content, "UTF-8");// 将参数转utf-8.
            // 如果需要加密，则设置内容类型为application/json
//			if (httppost.getURI().getPort() != 36010)
            sn.setContentType("application/json");
            httppost.setEntity(sn); // 设置请求参数
            // httppost.addHeader("ContentType", "application/json");
            HttpResponse response = httpclient.execute(httppost); // 发送请求
            httpResult.statusCode = response.getStatusLine().getStatusCode(); // 响应结果状态码
            HttpEntity entity = response.getEntity(); // 包含服务器的响应内容

            InputStream instream = entity.getContent(); // 拿到请求实体流
            Header[] headers = response.getAllHeaders();
            if (headers != null && headers.length > 0) {
                for (Header header : headers) {
                    if ("gzip".equals(header.getValue()))
                        instream = new GZIPInputStream(instream);
                }
            }

            ByteArrayBuffer baf = new ByteArrayBuffer(2048);
            byte[] byteBuf = new byte[2048];
            int l;
            while (true) {
                l = instream.read(byteBuf);
                if (l < 0)
                    break;
                baf.append(byteBuf, 0, l);
            }
            httpResult.result = new String(baf.toByteArray());

            //LogUtils.I("mlog", httpResult.result + "");
            //UtilTool.writeFileToSDOfTxt(httpResult.result + "", "sinya_cache");
            return httpResult;
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return httpResult;
    }

    public static SapHttpResult httpPost(String urlStr, byte[] content) {
        SapHttpResult httpResult = new SapHttpResult();
        HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost(urlStr);
        try {
            ByteArrayEntity sn = new ByteArrayEntity(content);
            httppost.setEntity(sn);
//			sn.setContentType("application/json");
            HttpResponse response = httpclient.execute(httppost);
            httpResult.statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();
            ByteArrayBuffer baf = new ByteArrayBuffer(2048);
            byte[] byteBuf = new byte[2048];
            int l;
            while (true) {
                l = instream.read(byteBuf);
                if (l < 0)
                    break;
                baf.append(byteBuf, 0, l);
            }
            httpResult.result = new String(baf.toByteArray());
            return httpResult;
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return httpResult;
    }

    public static SapHttpResult httpPost(String urlStr, byte[] content, String pathFile, String RemoteFileName, boolean isLast) {
        SapHttpResult httpResult = new SapHttpResult();
        HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost(urlStr);
        try {
            ByteArrayEntity sn = new ByteArrayEntity(content);
            //公有云
            if (Company.getInstance().PrivateCloud == Fields.NO) {
                httppost.addHeader("CompanyCode", Utils.base64String(Configuration.getConfig().companyCode));
                httppost.addHeader("OrgFileName ", Utils.base64String(Utils.getFileName(pathFile)));
                httppost.addHeader("RemoteFileName", RemoteFileName);
                httppost.addHeader("IsDataEnd", isLast ? "1" : "0");
            }
            httppost.setEntity(sn);
            sn.setContentType("application/json");
            HttpResponse response = httpclient.execute(httppost);
            httpResult.statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();
            ByteArrayBuffer baf = new ByteArrayBuffer(2048);
            byte[] byteBuf = new byte[2048];
            int l;
            while (true) {
                l = instream.read(byteBuf);
                if (l < 0)
                    break;
                baf.append(byteBuf, 0, l);
            }
            httpResult.result = new String(baf.toByteArray());
            return httpResult;
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return httpResult;
    }

    public static String httpUpLoad(String urlStr, String pathFile) {
        HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost(urlStr);
        try {
            File file = new File(pathFile);
            if (file.exists() == false || file.length() == 0)
                return null;
            FileEntity sn = new FileEntity(file, "binary/octet-stream");
            //公有云
            if (Company.getInstance().PrivateCloud == Fields.NO) {
                String com = Utils.base64String(Configuration.getConfig().companyCode);
                String orgFileName = Utils.base64String(Utils.getFileName(pathFile));
                httppost.addHeader("CompanyCode", com);
                httppost.addHeader("OrgFileName ", orgFileName);
                httppost.addHeader("RemoteFileName", "");
                httppost.addHeader("IsDataEnd", "1");
            }
            httppost.setEntity(sn);
            HttpResponse response = httpclient.execute(httppost);
            StatusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();
            // byte[] buf = new byte[(int) entity.getContentLength()];
            // instream.read(buf);
            ByteArrayBuffer baf = new ByteArrayBuffer(2048);
            byte[] byteBuf = new byte[2048];
            int l;
            while (true) {
                l = instream.read(byteBuf);
                if (l < 0)
                    break;
                baf.append(byteBuf, 0, l);
            }

            return new String(baf.toByteArray());
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return null;
    }

    public static SapHttpResult httpDelete(String urlStr) {
        SapHttpResult httpResult = new SapHttpResult();
        HttpClient httpclient = getHttpClient();
        HttpDelete httpdelete = new HttpDelete(urlStr);
        httpdelete.addHeader("ContentType", "text/plain");
        LogUtils.I("mlog", urlStr + "");
        try {
            HttpResponse response = httpclient.execute(httpdelete);
            httpResult.statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();
            ByteArrayBuffer baf = new ByteArrayBuffer(2048);
            byte[] byteBuf = new byte[2048];
            int l;
            while (true) {
                l = instream.read(byteBuf);
                if (l < 0)
                    break;
                baf.append(byteBuf, 0, l);
            }
            httpResult.result = new String(baf.toByteArray());
            LogUtils.I("mlog", httpResult.result + "");
            return httpResult;
        } catch (Exception e) {
            mException = e.toString();
            LogUtils.I("mlog", mException + "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return httpResult;
    }

    public static HttpClient getHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setContentCharset(params, "utf-8");
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        return new DefaultHttpClient(params);
    }

    public static class SapHttpResult {
        public String result;
        public int statusCode;
    }
}
