package com.hilo.others;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hilo.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 在Application中统一捕获异常，保存到文件中下次再打开时上传
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {

	private static final String TAG = "CrashHandler";

	/** 系统默认的UncaughtException处理类 */
	private UncaughtExceptionHandler mDefaultHandler;

	// 程序的Context对象
	private Context mContext;

	/** CrashHandler实例 */
	private static CrashHandler INSTANCE;

	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();
		}
		return INSTANCE;
	}

	/**
	 * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		try {

			// BaseActivity.exitAllActivity();

			if (!handleException(ex) && mDefaultHandler != null) {
				// 如果用户没有处理则让系统默认的异常处理器来处理
				mDefaultHandler.uncaughtException(thread, ex);

			} else {
				// 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(10);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成
	 * 
	 * @return true不会弹出那个错误提示框，false就会弹出
	 */
	private boolean handleException(final Throwable ex) {

		Log.i(TAG, "错误日志 handleException .... ex " + ex);

		if (ex == null) {
			return false;
		}
		Configuration cfg = Configuration.getConfig();
		cfg.logoff = true;
		Configuration.saveConfig();
		// 注释后，则开启后台bug
		if(Utils.isApkDebugable(mContext)){
			return false;
		}

		AppError appError = doAppError(ex);

		if (null != appError) {
			Gson gson = Utils.getGsonInstance();

			String jsonStr = gson.toJson(appError);

			Log.i(TAG, "错误日志 jsonStr ====>>>> " + jsonStr);

//			Result result = (Result) HttpClient.httpPost(Constants.APP_ERROR,
//					jsonStr, Result.class,false);

//			Log.i(TAG, "错误日志 result ====>>>> " + result);
		}

		return false;

	}

	class AppError {
		// 公司代码
		public String CompanyCode;
		// 用户编号
		public int UserSign;
		// 用户代码
		public String UserCode;
		// 用户名
		public String UserName;
		// app版本号
		public String AppVer;
		// 操作系统版本号
		public String OptSysVer;
		// 设备类型
		public int DeviceType;
		// 设备名称
		public String DeviceName;
		// 错误的堆栈信息
		public String Reason;

		public AppError() {
			super();
		}

		public AppError(String companyCode, int userSign, String userCode,
				String userName, String appVer, String optSysVer,
				int deviceType, String deviceName, String reason) {
			CompanyCode = companyCode;
			UserSign = userSign;
			UserCode = userCode;
			UserName = userName;
			AppVer = appVer;
			OptSysVer = optSysVer;
			DeviceType = deviceType;
			DeviceName = deviceName;
			Reason = reason;
		}

	}

	/**
	 * 拼接AppError
	 * 
	 * @param ex
	 * @return
	 */
	private AppError doAppError(Throwable ex) {

		try {

			Writer writer = new StringWriter();

			PrintWriter printWriter = new PrintWriter(writer);

			ex.printStackTrace(printWriter);

			printWriter.close();

			String result = writer.toString();

			Company c = Company.getInstance();
			
			Configuration configuration = Configuration.getConfig();

			String companyCode = configuration.companyCode;
					//c.companyCode;
			Log.i(TAG, " 拼接AppError companyCode  " + companyCode);

			int userSign = c.userSign;
			String userCode = c.userCode;
			String userName = c.userName;

			String versionName = Utils.getVersionName(mContext);

			if (!TextUtils.isEmpty(versionName)) {
				versionName = versionName.substring(versionName
						.lastIndexOf("_") + 1);
			}

			String appVer = versionName;
			// 发布版本
			String optSysVer = Utils.getVersionRelease();

			int deviceType = configuration.deviceType;
			String deviceName = configuration.deviceName;

			return new AppError(companyCode, userSign, userCode, userName,
					appVer, optSysVer, deviceType, deviceName, result);

		} catch (Exception e) {
		}

		return null;

	}

	private String errorInfo(String packageName) {
		Process mLogcatProc = null;
		BufferedReader reader = null;

		final StringBuffer buffer = new StringBuffer();

		try {
			mLogcatProc = Runtime.getRuntime().exec(
					new String[] { "logcat", "-d",
							"AndroidRuntime:E [" + packageName + "]:V *:S" });

			reader = new BufferedReader(new InputStreamReader(
					mLogcatProc.getInputStream()));

			String line;

			String separator = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append(separator);
			}

		}

		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		}

		return buffer.toString();

	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {

		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());

			} catch (Exception e) {

			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	public String saveCrashInfo2File(Throwable ex) {

		StringBuffer sb = new StringBuffer("\n");
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		sb.append("=============================" + "\n");

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);

		printWriter.close();
		String result = writer.toString();

		sb.append(result);
		try {

			String tempPath = null;
			String fileName = Utils.formatCurrentDate() + "_crash.txt";

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {

				String path = Environment.getExternalStorageDirectory()
						.toString() + File.separator + "crash/";

				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				tempPath = path + fileName;
				FileOutputStream fos = new FileOutputStream(tempPath, true);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return tempPath;
		} catch (Exception e) {

		}
		return null;
	}

	public void testBug() {
		try {
			// 制造bug
			File file = new File(Environment.getExternalStorageState(),
					"crash.bin");
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			fis.read(buffer);
		} catch (Exception e) {
			// e.printStackTrace();
			// 这里不能再向上抛异常，如果想要将log信息保存起来，则抛出runtime异常，
			// 让自定义的handler来捕获，统一将文件保存起来上传
			throw new RuntimeException(e);

		}

		// 注意，如果catch后不throw就默认是自己处理了，ExceptionHandler不会捕获异常了。
	}
}
