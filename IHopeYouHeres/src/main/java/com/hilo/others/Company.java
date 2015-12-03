package com.hilo.others;

import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 用户登录
 * @author hilo
 *
 */
public class Company implements Serializable{

	private static final long serialVersionUID = 1L;
	public String companyName;
//	public String sessionKey;
	public int userSign;
	public String userCode;
	public String userName;
	public int priceDec;
	public int quantityDec;
	public int percentDec;
	public int rateDec;
	public int sumDec;
	public String dataFormat;
	public String localDBName;
	public int superUser;
	public int isSbo;
	public String docKey;
	public String docURL;
	public int GpsTrack;
	public String GpsTrackEndTime;
	public String GpsTrackStartTime;
	public int Interval;
//	public String companyCode;
//	public String serverAddress;
	public String userImagePath;
	public String userFilePath;
	public String appPath;
	public int CanAddCustome;
	public int CanAddSupplier;
	public String MyDept;
	public String MyRole;
	public String ApiVersion;
	//签到抄送用户类型 0:可以不选  1:强制手动选择 2:自动选择直接上级  3:选择固定用户
	public int SignInCCType;
	//签到固定抄送用户
	public int SignInCCUser;
	public int AddProject;//是否可以添加项目
	public int ShareChooseAllBP; //分享时可选择所有的业务伙伴
	public int ShareChooseAllProject;   //分享时可选择所有的项目
	public int ShareChooseAllCntct;     //分享时可选择所有的联系
	public String ImServer;
	public String ImPort;
	static private Company obj;
	public String SecretCode;//安全码   用于计算tonken值
	public int PrivateCloud;//是否是私有云服务  1:是  0:否
//	public static boolean isCurrentQuit;
	public String DownLoadUrl;   //文档下载url
	public String UploadUrl;  //文档上传url
	public String LogHint1;//工作总结",
	public String LogHint2;//工作计划",
	public String LogHint3;//心得体会"
	public static Company getInstance(){
		if(obj == null){
			obj = new Company();
			obj.loadFromFile();
		}
		return obj;
	}
	
	String getFileName(){
		return "company." + Configuration.getConfig().companyCode;
	}
	
	void loadFromFile(){
//		String pathfile = getCompanyRootPath() + "company.dat";
//		FileInputStream fs = null;
//		ObjectInputStream os = null;
//		try {
//			fs = new FileInputStream(pathfile);
//			os = new ObjectInputStream(fs);
//			obj = (Company) os.readObject();
//		} catch (Exception e) {
//			Log.i("mlog", e.toString());
//		}finally{
//			try {
//				if(os != null)os.close();
//				if(fs != null)fs.close();
//			} catch (Exception e) {
//				Log.i("mlog", e.toString());
//			}
//		}
		SharedPreferences pref = MyApplication.mContext.getSharedPreferences(getFileName(), 0);

		companyName = pref.getString(k_companyName, "");
//		public String sessionKey;
		userSign = pref.getInt(k_userSign, 0);
		
		userCode = pref.getString(k_userCode, "");
		userName = pref.getString(k_userName, "");
		priceDec = pref.getInt(k_priceDec, 0);
		quantityDec = pref.getInt(k_quantityDec, 0);
		percentDec = pref.getInt(k_percentDec, 0);
		rateDec = pref.getInt(k_rateDec, 0);
		sumDec = pref.getInt(k_sumDec, 0);
		dataFormat = pref.getString(k_dataFormat, "");
		localDBName = pref.getString(k_localDBName, "");
		superUser = pref.getInt(k_superUser, 0);
		isSbo = pref.getInt(k_isSbo, 0);
		docKey = pref.getString(k_docKey, "");
		docURL = pref.getString(k_docURL, "");
		GpsTrack = pref.getInt(k_GpsTrack, 0);
		GpsTrackEndTime = pref.getString(k_GpsTrackEndTime, "");
		GpsTrackStartTime = pref.getString(k_GpsTrackStartTime, "");
		Interval = pref.getInt(k_Interval, 0);
//		public String companyCode;
//		public String serverAddress;
		userImagePath = pref.getString(k_userImagePath, "");
		userFilePath = pref.getString(k_userFilePath, "");
		appPath = pref.getString(k_appPath, "");
		CanAddCustome = pref.getInt(k_CanAddCustome, 0);
		CanAddSupplier = pref.getInt(k_CanAddSupplier, 0);
		ApiVersion = pref.getString(k_apiVersion, "");
		MyDept = pref.getString(k_mydept, "");
		MyRole = pref.getString(k_myrole, "");
		SecretCode = pref.getString(k_seretCode, "");
		SignInCCType = pref.getInt(k_SignInCCType, 0);
		//签到固定抄送用户
		SignInCCUser = pref.getInt(k_SignInCCUser, 0);
		AddProject = pref.getInt(k_AddProject, 0);
		ShareChooseAllBP = pref.getInt(k_ShareChooseAllBP, 0);
		ShareChooseAllProject = pref.getInt(k_ShareChooseAllProject, 0);
		ShareChooseAllCntct = pref.getInt(k_ShareChooseAllCntct, 0);
		ImServer = pref.getString(k_ImServer, "");
		ImPort = pref.getString(k_ImPort, "");
		PrivateCloud = pref.getInt(k_PrivateCloud, 0);
		DownLoadUrl = pref.getString(k_DownLoadUrl, "");
		UploadUrl = pref.getString(k_UploadUrl, "");
		LogHint1 = pref.getString(k_LogHint1, "");
		LogHint2 = pref.getString(k_LogHint2, "");
		LogHint3 = pref.getString(k_LogHint3, "");
	}
	
	public void saveToFile(){
//		String pathfile = getCompanyRootPath() + "company.dat";
//		FileOutputStream fs = null;
//		ObjectOutputStream os = null;
//		try {
//			fs = new FileOutputStream(pathfile);
//			os = new ObjectOutputStream(fs);
//			os.writeObject(obj);
//		} catch (Exception e) {
//			Log.i("mlog", e.toString());
//		}finally{
//			try {
//				if(os != null)os.close();
//				if(fs != null)fs.close();
//			} catch (Exception e) {
//				Log.i("mlog", e.toString());
//			}
//		}
		SharedPreferences pref = MyApplication.mContext.getSharedPreferences(getFileName(), 0);
		SharedPreferences.Editor edit = pref.edit();
		
		edit.putString(k_companyName, companyName);
//		public String sessionKey;
		edit.putInt(k_userSign, userSign);
		edit.putString(k_userCode, userCode);
		edit.putString(k_userName, userName);
		edit.putInt(k_priceDec, priceDec);
		edit.putInt(k_quantityDec, quantityDec);
		edit.putInt(k_percentDec, percentDec);
		edit.putInt(k_rateDec, rateDec);
		edit.putInt(k_sumDec, sumDec);
		edit.putString(k_dataFormat, dataFormat);
		edit.putString(k_localDBName, localDBName);
		edit.putInt(k_superUser, superUser);
		edit.putInt(k_isSbo, isSbo);
		edit.putString(k_docKey, docKey);
		edit.putString(k_docURL, docURL);
		edit.putInt(k_GpsTrack, GpsTrack);
		edit.putString(k_GpsTrackEndTime, GpsTrackEndTime);
		edit.putString(k_GpsTrackStartTime, GpsTrackStartTime);
		edit.putInt(k_Interval, Interval);
//		public String companyCode;
//		public String serverAddress;
		edit.putString(k_userImagePath, userImagePath);
		edit.putString(k_userFilePath, userFilePath);
		edit.putString(k_appPath, appPath);
		edit.putInt(k_CanAddCustome, CanAddCustome);
		edit.putInt(k_CanAddSupplier, CanAddSupplier);
		edit.putInt(k_CanAddSupplier, CanAddSupplier);
		edit.putString(k_apiVersion, ApiVersion);
		edit.putString(k_mydept, MyDept);
		edit.putString(k_myrole, MyRole);
		edit.putString(k_seretCode, SecretCode);
		
		edit.putInt(k_SignInCCType, SignInCCType);
		edit.putInt(k_SignInCCUser, SignInCCUser);
		edit.putInt(k_AddProject, AddProject);
		edit.putInt(k_ShareChooseAllBP, ShareChooseAllBP);
		edit.putInt(k_ShareChooseAllProject, ShareChooseAllProject);
		edit.putInt(k_ShareChooseAllCntct, ShareChooseAllCntct);
		edit.putString(k_ImServer, ImServer);
		edit.putString(k_ImPort, ImPort);
		edit.putInt(k_PrivateCloud, PrivateCloud);
		edit.putString(k_DownLoadUrl, DownLoadUrl);
		edit.putString(k_UploadUrl, UploadUrl);
		edit.putString(k_LogHint1, LogHint1);
		edit.putString(k_LogHint2, LogHint2);
		edit.putString(k_LogHint3, LogHint3);
		edit.commit();
	}
	
//	public static void cleanFile(){
//		String pathfile = getCompanyRootPath() + "company.dat";
//		File file = new File(pathfile);
//		file.delete();
//	}
	
	public static String getCompanyRootPath(){
		Context context = MyApplication.mContext;
		String private_path;
//		if(UtilTool.isExistCard()){
//			private_path = context.getExternalCacheDir().getPath();
//		}else{
//			private_path = context.getCacheDir().getPath();
//		}
		private_path = context.getCacheDir().getPath();
		//尝试解决某些机型出现的数据库异常问题
		return private_path.replace("cache", "databases") + "/" + Configuration.getConfig().companyCode + "/";
	}
	
	final String k_companyName = "companyName";
//	public String sessionKey;
	final String k_userSign = "userSign";
	final String k_userCode = "userCode";
	final String k_userName = "userName";
	final String k_priceDec = "priceDec";
	final String k_quantityDec = "quantityDec";
	final String k_percentDec = "percentDec";
	final String k_rateDec = "rateDec";
	final String k_sumDec = "sumDec";
	final String k_dataFormat = "dataFormat";
	final String k_localDBName = "localDBName";
	final String k_superUser = "superUser";
	final String k_isSbo = "isSbo";
	final String k_docKey = "docKey";
	final String k_docURL = "docURL";
	final String k_GpsTrack = "GpsTrack";
	final String k_GpsTrackEndTime = "GpsTrackEndTime";
	final String k_GpsTrackStartTime = "GpsTrackStartTime";
	final String k_Interval = "Interval";
//	public String companyCode = "";
//	public String serverAddress = "";
	final String k_userImagePath = "userImagePath";
	final String k_userFilePath = "userFilePath";
	final String k_appPath = "appPath";
	final String k_CanAddCustome = "CanAddCustome";
	final String k_CanAddSupplier = "CanAddSupplier";
	final String k_apiVersion = "APIVersion";
	final String k_mydept = "mydept";
	final String k_myrole = "myrole";
	final String k_seretCode = "seretCode";
	
	final String k_SignInCCType = "SignInCCType";
	final String k_SignInCCUser = "SignInCCUser";
	final String k_AddProject = "AddProject";
	final String k_ShareChooseAllBP = "ShareChooseAllBP";
	final String k_ShareChooseAllProject = "ShareChooseAllProject";
	final String k_ShareChooseAllCntct = "ShareChooseAllCntct";
	final String k_ImServer = "ImServer";
	final String k_ImPort = "ImPort";
	final String k_PrivateCloud = "PrivateCloud";
	final String k_DownLoadUrl = "DownLoadUrl";
	final String k_UploadUrl = "UploadUrl";
	final String k_LogHint1 = "LogHint1";
	final String k_LogHint2 = "LogHint2";
	final String k_LogHint3 = "LogHint3";

}
