package com.hilo.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hilo.R;
import com.hilo.activity.BaseActivity;
import com.hilo.activity.LoginActivity;
import com.hilo.others.Configuration;
import com.hilo.others.Fields;
import com.hilo.others.MyApplication;
import com.hilo.requesthttp.HttpClient;
import com.hilo.requesthttp.ReqParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hilo on 15/12/1.
 * <p/>
 * Drscription:
 */
public class Utils {

    /**
     * 判断当前应用是否处于debug状态
     */
    public static boolean isApkDebugable(Context context) {
        boolean flag = false;
        try {
            if (null != context) {
                ApplicationInfo info = context.getApplicationInfo();
                flag = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static Gson getGsonInstance() {
        Gson gson = null;
        if (gson == null) {
            synchronized (Utils.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = MyApplication.mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication.mContext.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前应用的版本号
     */
    public static String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo.versionName;
    }

    /**
     * 发布版本
     */
    public static String getVersionRelease() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 格式化 当前 系统 时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatCurrentDate() {
        String str = null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
            str = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 退出 app
     *
     * @param context 上下文
     */
    public static void appLogout(Context context) {
        BaseActivity.exitApp();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Fields.I_LOGOUT, true);
        context.startActivity(intent);
    }

    /**
     * 异常或者其他问题退出到登录界面
     */
    public static void sendbroadcastToLogin() {
//        if (!LoginActivity.isLoginActivityShow) {
//            Intent mIntent = new Intent("android.exception.ExceptionLoingOutReceiver");
//            SpdApplication.mContext.sendBroadcast(mIntent);
//        }
    }

    /**
     * 置空 静态 变量 ; 退出登录 或 强制退出 时 调用
     */
    public static void setAllStaticVarsNull() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断app 当前是否处于前台运行状态
     *
     * @param context 上下文
     * @return 返回false则为后台, otherwise
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 转Base64码 字符串
     */
    public static String base64String(String s) {
        String result = null;
        if (!TextUtils.isEmpty(s)) {
            try {
                result = android.util.Base64.encodeToString(s.getBytes("UTF-8"), android.util.Base64.NO_WRAP);
                // 特殊字符 / + = 替换成_a _b _c
                if (result != null) {
                    result = result.replaceAll("/", "_a");
                    result = result.replaceAll("\\+", "_b");
                    result = result.replaceAll("=", "_c");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 判断SD是否存在
     *
     * @return
     */
    public static boolean isSdcardExist() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取单个文件的MD5值！
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }


    public static String getFileName(String pathfile) {
        int pos = pathfile.lastIndexOf("/");
        if (pos > 0) {
            return pathfile.substring(pos + 1);
        }
        return pathfile;
    }

    /**
     * 是否 是平板
     */
    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    /**
     * json 转 实体 对象
     */
    public static <T> T parseFromJson(String jsonData, Class<T> classOfT) {
        T t = null;
        try {
            if (!TextUtils.isEmpty(jsonData) && classOfT != null) {
                Gson gson = getGsonInstance();
                t = gson.fromJson(jsonData, classOfT);
            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String parseToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * Server Url
     *
     * @param reqParam ReqParam.XX
     * @param code     userSign
     * @return Server Url
     */
    public static String getTokenUrl(ReqParam reqParam, String code) {
        String token = HttpClient.createToken(reqParam, code);
        return Configuration.getConfig().serverAddress + reqParam.cmd + Configuration.getConfig().sessionKey + "/" + Integer.parseInt(code) + "/" + token;
    }
}
