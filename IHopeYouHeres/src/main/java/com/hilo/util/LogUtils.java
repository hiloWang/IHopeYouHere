package com.hilo.util;

import android.util.Log;

import com.hilo.others.MyApplication;

public class LogUtils {

    /* DEBUG为true，则使用LogUtils的日志打印有效
     * 为false，即使不把代码中的LogUtils方法删除或者注释掉，也可以让它不再打印信息
     */
    private static boolean DEBUG = Utils.isApkDebugable(MyApplication.mContext);

    //false 的表示取消打印mlog 测试log日志
    private static String AUTHOR_SINYA = "mlog";
    private static boolean LOG_DEBUG = true;

    /**
     * @param tag 标签
     * @param msg 消息
     * @Description: 打印info级别的日志
     */
    public static void I(String tag, String msg) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String className = stackTrace.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);

            if (tag == null || "".equals(tag.trim())) {
                tag = className;
            } else if (tag.equals(AUTHOR_SINYA) && !LOG_DEBUG) {
                return;
            }

            if (msg == null) {
            } else {
                Log.i(tag, head + msg);
            }
        }
    }

    /**
     * @param msg 消息
     * @Description: 打印info级别的日志，默认以使用此方法的类  不附带包名的类名作为 TAG。
     */
    public static void I(String msg) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String className = stackTrace.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);

            if (msg == null) {
            } else {
                Log.i(className, head + msg);
            }
        }
    }

    /**
     * @param tag 标签
     * @param msg 消息
     * @Description: 打印error级别的日志
     */
    public static void E(String tag, String msg) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String className = stackTrace.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);
            if (tag == null || "".equals(tag.trim())) {
                tag = className;
            }

            if (msg == null) {
            } else {
                Log.e(tag, head + msg);
            }
        }
    }

    /**
     * @param msg
     * @Description: 打印error级别的日志，默认以使用此方法的类的简单类名作为TAG
     */
    public static void E(String msg) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String className = stackTrace.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);

            if (msg == null) {
            } else {
                Log.e(className, head + msg);
            }
        }
    }

    /**
     * @param tag
     * @param msg
     * @Description: 打印warning级别的日志
     */
    public static void W(String tag, String msg) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String className = stackTrace.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);
            if (tag == null || "".equals(tag.trim())) {
                tag = className;
            }

            if (msg == null) {
            } else {
                Log.w(tag, head + msg);
            }
        }
    }

    /**
     * @param msg
     * @Description: 打印warning级别的日志，默认以使用此方法的类的简单类名作为TAG
     */
    public static void W(String msg) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String className = stackTrace.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);

            if (msg == null) {
            } else {
                Log.w(className, head + msg);
            }
        }
    }

    /**
     * @return
     * @Description: 获取日志头信息，包含打印日志的类全路径名，行号，方法名称。
     */
    public static String getLogHeadInfo() {
        StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
        String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
        return head;
    }

    /**
     * @return
     * @Description: 获取默认TAG，默认以类，简单名称作为TAG
     */
    public static String getDefaultTag() {
        StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
        String className = stackTrace.getClassName();
        return className.substring(className.lastIndexOf("."));
    }
}
