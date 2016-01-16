package com.hilo.requesthttp;

/**
 * 封装 api
 *
 * @author Hilo
 */

public class ReqParam {
    /**
     * api分支节点
     */
    public String cmd;
    /**
     * 请求方式
     */
    public int invokeMethod;
    /**
     * 是否需要SessionKey
     */
    public boolean hasSessionKey;
    /**
     * 是否需要压缩
     */
    public boolean isCompress;
    /**
     * 封装了实体bean
     */
    public Class<?> praseClass;
    /**
     * POST提交的数据是否要加密
     */
    public boolean isEncrypt;
    /**
     * Url是否需要提交Token
     */
    public boolean hasToken;
    /**
     * 返回的数据是否需要解密
     */
    public boolean isDecrypt;
    /**
     * 需要提交的参数的参数名，用作加密用
     */
    public String[] parameterNames;
    public static final int GET = 1;
    public static final int POST = 2;
    public static final int DELETE = 3;

    /**
     * 请求参数
     *
     * @param cmd           api分支节点
     * @param invokeMethod  请求方式
     * @param hasSessionKey 是否需要SessionKey
     * @param isCompress    是否需要压缩
     * @param praseClass    HttpParse类下，封装了实体bean，通过getItem（）方式获取。
     */
    public ReqParam(String cmd, int invokeMethod, boolean hasSessionKey,
                    boolean isCompress, Class<?> praseClass) {
        this.cmd = cmd;
        this.invokeMethod = invokeMethod;
        this.hasSessionKey = hasSessionKey;
        this.isCompress = isCompress;
        this.praseClass = praseClass;
    }

    /**
     * @param cmd            api分支节点
     * @param invokeMethod   请求方式
     * @param hasSessionKey  是否需要SessionKey
     * @param praseClass     HttpParse类下，封装了实体bean，通过getItem（）方式获取。
     * @param isEncrypt      数据是否要加密提交
     * @param parameterNames 参数名数组
     * @param hasToken       Url是否需要Token
     * @param isDecrypt      返回的数据是否需要解密
     */
    public ReqParam(String cmd, int invokeMethod, boolean hasSessionKey,
                    Class<?> praseClass, boolean isEncrypt, String[] parameterNames,
                    boolean hasToken, boolean isDecrypt) {
        super();
        this.cmd = cmd;
        this.invokeMethod = invokeMethod;
        this.hasSessionKey = hasSessionKey;
        this.praseClass = praseClass;
        this.isEncrypt = isEncrypt;
        this.parameterNames = parameterNames;
        this.hasToken = hasToken;
        this.isDecrypt = isDecrypt;
    }

    /**
     * 默认的SessionKey为true，isEncrypt为false, hasToken为true
     *
     * @param cmd            api分支节点
     * @param invokeMethod   请求方式
     * @param praseClass     HttpParse类下，封装了实体bean，通过getItem（）方式获取。
     * @param parameterNames parameterNames
     */
    public ReqParam(String cmd, int invokeMethod,
                    Class<?> praseClass, String[] parameterNames) {
        super();
        this.cmd = cmd;
        this.invokeMethod = invokeMethod;
        this.hasSessionKey = true;
        this.praseClass = praseClass;
        this.isEncrypt = false;
        this.parameterNames = parameterNames;
        this.hasToken = true;
        this.isDecrypt = false;
    }

    /**
     * 3.2.8	获取单个用户图像
     * http://localhost:9998/api/User/V1/ UserImage/{sessionKey}/{userSign:int}/{token}
     * 已测试，Dick
     */
    public static ReqParam user_image_cfg = new ReqParam("/api/User/V1/UserImage/", GET, null, new String[]{"userSign"});

    public static ReqParam test = new ReqParam("api/SysMsg/V1/GetViewMessage", GET, HttpParse.NotifyGroup.class, new String[]{"companyCode"});

/*
    *//**
     * 系统图片
     * http://localhost:9998/API/BaseData/Image/V1/SysImage/{sessionKey}/{imageIndex:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam sys_cfg = new ReqParam("/API/BaseData/Image/V1/SysImage/", GET, null, new String[]{"imageIndex"});

    *//**
     * 设置用户图像
     * http://localhost:9998/api/User/V1/UserImage/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam set_user_image = new ReqParam("/api/User/V1/UserImage/", POST, null, null);
    *//**
     * 获取窗口图片
     * //http://localhost:9998/API/BaseData/Image/V1/FormIcon/{sessionKey}/{formID:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam getProjectImage = new ReqParam("/API/BaseData/Image/V1/FormIcon/", GET, null, new String[]{"formID"});
    *//**
     * 3.2.1 用户登录
     * http://localhost:9998/api/User/V1/Login
     * 已测试，Dick
     *//*
    public static ReqParam login = new ReqParam("api/User/V1/Login", POST,
            false, LoginData.class, true, null, false, true);

    *//**
     * 3.2.2 获取用户可用的模块(对于超级用户不需要调用)
     * http://localhost:9998/api/User/V1/UserModuleEx/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam mobile_user_user_moduleex = new ReqParam(
            "api/User/V1/UserModuleEx", GET, UserModuleDataItems.class, null);

    *//**
     * 3.2.3 用户退出登录
     * http://localhost:9998/api/User/V1/ LoginOff /{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam loginOff = new ReqParam("api/User/V1/LoginOff", GET, null, null);

    *//**
     * 3.10.1 获取用户GPS信息
     * http://localhost:9998/api/Gps/V1/UserLocus/{sessionKey}/{userSign:int}/{findDate:DateTime}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam userLocus = new ReqParam("api/Gps/V1/UserLocus", GET, T_OGPS.class, new String[]{"userSign", "findDate"});

    *//**
     * 3.10.2提交用户GPS信息
     * http://localhost:9998/api/Gps/V1/AddUserLocus/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam addUserLocus = new ReqParam("api/Gps/V1/AddUserLocus", POST, null, null);

    *//**
     * 暂时不处理
     * 3.10.3 添加业务伙伴GPS信息
     * http://localhost:9998/GPS/UpdatePartnerGPS/{sessionKey}/{cardCode}/{lng}/{lat}
     *//*
    public static ReqParam UpdatePartnerGPS = new ReqParam(
            "GPS/UpdatePartnerGPS", GET, true, false, Result.class);

    *//**
     * 3.10.11 获取用户当前位置
     * http://localhost:9998/api/Gps/V1/CurrentLocation/{sessionKey}/{token}
     * 已测试
     *//*
    public static ReqParam currentLocation = new ReqParam(
            "api/Gps/V1/CurrentLocation", POST, GpsInfo.class, null);

    *//**
     * 3.10.12 获取用户提交GPS信息的日期组
     * http://localhost:9998/api/Gps/V1/UserGpsDateGroup/{sessionKey}/{userSign:int}/{currentPage:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam userGpsDateGroup = new ReqParam(
            "api/Gps/V1/UserGpsDateGroup", GET, TrackDate.class, new String[]{"userSign", "currentPage"});

    *//**
     * 3.11.7 邮件联系人列表
     * http://localhost:9998/api/Mail/V1/Contact/{sessionKey}/{mailType:int}/{currentPage:int}/{token}
     *//*
    public static ReqParam mail_contact = new ReqParam("api/Mail/V1/Contact", GET,
            MailContactsResult.class, new String[]{"mailType", "currentPage"});

    *//**
     * 3.14.1 获取当前用户的工作台信息
     * http://localhost:9998/api/WorkConsole/V1/GetItem/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam workConsole = new ReqParam(
            "api/WorkConsole/V1/GetItem", GET, BenchData.class,
            null);
    *//**
     * 3.16.19 获取每月是存在工作日程的天
     *//*
    // http://localhost:9998/Remind/MonthCalendarSchedule/{sessionKey}/{year}/{month}
    // public static ReqParam monthCalendarSchedule = new ReqParam(
    // "Remind/MonthCalendarSchedule", GET, true, false, MonthAgenda.class);

    *//**
     * 3.16.20
     * 获取每年是存在工作日程的天
     * http://localhost:9998/API/Remind/V1/YearCalendarSchedule/{sessionKey}/{year:int}/{token}
     * Sinya - (日程-日历listview - 已测试)
     *//*
    public static ReqParam yearCalendarSchedule = new ReqParam(
            "API/Remind/V1/YearCalendarSchedule", GET, MonthAgendaItem.class, new String[]{"year"});

    *//**
     * 3.17.4 业务伙伴联系人
     * http://localhost:9998/API/BaseData/MasterData/V1/PartnerCntct/{sessionKey}/{cardCode}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam partner_cntct = new ReqParam(
            "API/BaseData/MasterData/V1/PartnerCntct", GET,
            MasterDataContacts.class, new String[]{"cardCode"});
    *//**
     * 3.17.24 删除业务伙伴联系人
     * http://localhost:9998/API/BaseData/MasterData/V1/DeletePartnerCntct/{sessionKey}/{token}
     * 已测试
     *//*
    public static ReqParam partner_deletePartnerCntct = new ReqParam(
            "API/BaseData/MasterData/V1/DeletePartnerCntct", POST,
            null, null);
    *//**
     * 3.17.23 关联业务伙伴联系人
     * http://localhost:9998/API/BaseData/MasterData/V1/AddPartnerCntct/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam masterDataAddPartnerCntct = new ReqParam(
            "API/BaseData/MasterData/V1/AddPartnerCntct", POST, null, null);

    *//**
     * 3.17.11 项目数据
     * http://localhost:9998/API/BaseData/MasterData/V1/GetProjects/{sessionKey}/{token}
     * 已测试
     *//*
    public static ReqParam master_data_project_data = new ReqParam(
            "API/BaseData/MasterData/V1/GetProjects", POST, ProjectData.class, null);

    *//**
     * 3.2.16 创建用户
     * http://localhost:9998/api/User/V1/AddUser/{sessionKey}/{companyCode}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam add_user = new ReqParam("api/User/V1/AddUser", POST, Contacts.class, new String[]{"companyCode"});

    *//**
     * 3.19.3	获取登录用户编号
     * http://localhost:9998/api/User/V1/DemoUser
     *//*
    public static ReqParam demoUser = new ReqParam("api/User/V1/DemoUser", POST,
            false, null, true, null, false, false);

    *//**
     * 用户数据同步
     * http://localhost:9998/API/BaseData/SyncData/V1/User/{sessionKey}/{updateTime:DateTime}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam sync_data_user = new ReqParam("API/BaseData/SyncData/V1/User", GET,
            AllUsersResult.class, new String[]{"updateTime"});

    *//**
     * 界面布局数据同步
     * http://localhost:9998/API/BaseData/SyncData/V1/FormLayout/{sessionKey}/{currentPage:int}/{updateTime:DateTime}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam sync_data_FormLayout = new ReqParam(
            "API/BaseData/SyncData/V1/FormLayout", GET,
            SynchronizationDataAsyncTaskResult.class, new String[]{"currentPage", "updateTime"});

    *//**
     * 部门数据同步
     * http://localhost:9998/API/BaseData/SyncData/V1/Dept/{sessionKey}/{updateTime:DateTime}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam sync_data_dept = new ReqParam("API/BaseData/SyncData/V1/Dept", GET,
            DeptData.class, new String[]{"updateTime"});

    *//**
     * 角色数据同步
     * http://localhost:9998/API/BaseData/SyncData/V1/Role/{sessionKey}/{updateTime:DateTime}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam sync_data_role = new ReqParam("API/BaseData/SyncData/V1/Role", GET,
            RoleData.class, new String[]{"updateTime"});

    *//**
     * 角色、部门与用户对应关系
     * http://localhost:9998/API/BaseData/SyncData/V1/RoleDeptUser/{sessionKey}/{updateTime:DateTime}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam sync_data_role_dept_user = new ReqParam(
            "API/BaseData/SyncData/V1/RoleDeptUser", GET, RoleDeptUserData.class, new String[]{"updateTime"});

    *//**
     * 需要同步的系统图像
     * http://localhost:9998/API/BaseData/SyncData/V1/SysImage/{sessionKey}/{updateTime:DateTime}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam sys_image = new ReqParam("API/BaseData/SyncData/V1/SysImage", GET,
            SysImage.class, new String[]{"updateTime"});

    *//**
     * 同步所有数据
     * http://localhost:9998/API/BaseData/SyncData/V1/all/{sessionKey}/{updateTime:DateTime}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam sync_data_all = new ReqParam("API/BaseData/SyncData/V1/all", GET,
            AllData.class, new String[]{"updateTime"});

    *//**
     * 3.16.11
     * 新建待办/提醒
     * http://localhost:9998/API/Remind/V1/Create/{sessionKey}/{dataType:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam newBacmlog = new ReqParam("API/Remind/V1/Create", POST,
            null, new String[]{"dataType"});

    *//**
     * 3.16.12
     * 读取提醒或待办数据
     * http://localhost:9998/API/Remind/V1/Read/{sessionKey}/{dataType:int}/{status:int}/{important:int}/{sliderDown:int}/{refDate:datetime}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam readBacmlog = new ReqParam("API/Remind/V1/Read",
            GET, NewBacmlogOrRemindResult.class, new String[]{"dataType", "status", "important", "sliderDown", "refDate"});

    *//**
     * 获取当前用户所有能用的工作台
     * http://localhost:9998/api/WorkConsole/V1/All /{sessionKey}/{token}
     * 已测试
     *//*
    public static ReqParam work_console_all = new ReqParam("api/WorkConsole/V1/All",
            GET, BenchData.class, null);

    *//**
     * 3.16.14
     * 标记待办完成状态
     * http://localhost:9998/API/Remind/V1/MarkStatus/{sessionKey}/{docEntry:long}/{status:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam markFinishStatus = new ReqParam("API/Remind/V1/MarkStatus",
            GET, null, new String[]{"docEntry", "status"});

    *//**
     * 3.17.1
     * 获取联系人数据
     * http://localhost:9998/API/BaseData/MasterData/V1/Contacts/{sessionKey}/{token}
     * Sinya - （包括工作台-联系人、日程-关联联系人、日程-关联业务伙伴 已测试）
     *//*
    public static ReqParam master_data_contacts = new ReqParam(
            "API/BaseData/MasterData/V1/Contacts", POST, MasterDataContacts.class, null);

    *//**
     * 3.17.25 没有关联业务伙伴的联系人
     * http://localhost:9998/API/BaseData/MasterData/V1/NotPartnerCntct/{sessionKey}/{currentPage:int}/{token}
     *//*
    public static ReqParam master_data_contacts_2 = new ReqParam(
            "API/BaseData/MasterData/V1/NotPartnerCntct", GET,
            MasterDataContacts.class, new String[]{"currentPage"});

    *//**
     * 更改为3.17.1	获取联系人数据
     * 暂时不处理
     * 搜索联系人数据
     * http://localhost:9998/MasterData/SearchContacts/{sessionKey}/{dataType}/{currentPage}
     *//*
    public static ReqParam masterDataContactsSearch = new ReqParam(
            "MasterData/SearchContacts", POST, true, true,
            MasterDataContacts.class);

    *//**
     * 获取业务伙伴数据
     * http://localhost:9998/API/BaseData/MasterData/V1/Partners/{sessionKey}/{token}
     * Hilo 已测试
     *//*
    public static ReqParam master_data_Partners = new ReqParam(
            "API/BaseData/MasterData/V1/Partners", POST, MasterDataPartners.class, null);

    *//**
     * 改为获取业务伙伴数据一样的API
     * 搜索业务伙伴数据
     *//*
    public static ReqParam MasterDataPartnersSearch = new ReqParam(
            "MasterData/SearchPartners", POST, true, true,
            MasterDataPartners.class);

    *//**
     * 暂时不处理
     * 3.17.12 获取单个项目数据
     * http://localhost:9998/MasterData/ProjectByKey/{sessionKey}/{prjCode}
     *//*
    public static ReqParam MasterDataProjectByKey = new ReqParam(
            "MasterData/ProjectByKey", GET, true, true, ProjectData.class);

    *//**
     * 获取业务伙伴数据
     * http://localhost:9998/API/BaseData/MasterData/V1/CardType/{sessionKey}/{bpCode}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam MasterDataCardType = new ReqParam(
            "API/BaseData/MasterData/V1/CardType", GET,
            null, new String[]{"bpCode"});

    *//**
     * 获取费用
     * http://localhost:9998/API/BaseData/MasterData/V1/ExpenseData/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam master_data_expense_data = new ReqParam(
            "API/BaseData/MasterData/V1/ExpenseData", GET, ExpenseDataItems.class, null);

    *//**
     * 设置当前用户的工作台信息
     * http://localhost:9998/api/WorkConsole/V1/SetItem/{sessionKey}/{items}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam work_console_set_item = new ReqParam(
            "api/WorkConsole/V1/SetItem", GET, null, new String[]{"items"});

    *//**
     * 3.17.15 创建费用
     * http://localhost:9998/API/BaseData/MasterData/V1/CreateExpense/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam create_expense = new ReqParam(
            "API/BaseData/MasterData/V1/CreateExpense", POST, null, null);
    *//**
     * 3.17.20	修改费用
     * http://localhost:9998/API/BaseData/MasterData/V1/ModifyExpenseName/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam modifyExpenseName = new ReqParam(
            "API/BaseData/MasterData/V1/ModifyExpenseName", POST, null, null);
    *//**
     * 3.17.19	删除费用
     * http://localhost:9998/API/BaseData/MasterData/V1/DeleteExpense/{sessionKey}/{expenseCode:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam deleteExpense = new ReqParam("API/BaseData/MasterData/V1/DeleteExpense", DELETE, null, new String[]{"expenseCode"});
    *//**
     * 3.2.14
     * 修改用户密码
     * http://localhost:9998/api/User/V1/ ChangePwd /{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam change_pwd = new ReqParam("api/User/V1/ChangePwd",
            POST, null, null);
    *//**
     * 3.2.19
     * 修改邮件密码
     * http://localhost:9998/api/User/V1/ChangeMailPwd/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam change_email_pwd = new ReqParam(
            "api/User/V1/ChangeMailPwd", POST, null, null);

    *//**
     * 3.2.13
     * 修改用户数据
     * http://localhost:9998/api/User/V1/ Update /{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam mobile_user_update = new ReqParam(
            "api/User/V1/Update", POST, null, null);

    *//**
     * 暂时不处理，已弃用
     * 3.17.12 创建项目
     * http://localhost:9998/MasterData/CreatePrject/{sessionKey}
     *//*
    public static ReqParam master_data_create_prject = new ReqParam(
            "MasterData/CreatePrject", POST, true, false, Result.class);

    *//**
     * 3.2.6 设置用户图像
     * 废弃
     * http://localhost:9998/api/User/V1/UserImage/{sessionKey}/{token}
     *//*
    public static ReqParam user_set_image = new ReqParam(
            "api/User/V1/UserImage", POST, Result.class, null);

    *//**
     * 获取我的下属
     * http://localhost:9998/API/BaseData/MasterData/V1/MyUnderling/{sessionKey}/{dataType:int}/{token}
     * 已测试
     *//*
    public static ReqParam my_underling = new ReqParam(
            "API/BaseData/MasterData/V1/MyUnderling", GET, MyUnderlingItems.class, new String[]{"dataType"});

    *//**
     * 读取单据
     * http://localhost:9998/API/OA/V1/Read/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaGetAPI = new ReqParam("API/OA/V1/Read", POST,
            OAMasterData.class, null);

    *//**
     * 3.16.26 访客报表链接
     * http://localhost:9998/API/OA/V1/WeekReportLink/{sessionKey}/{formID:int}/{createUser:int}/{startDate:datetime}/{endDate:datetime}/{cardCode}/{token}
     * 已测试
     *//*
    public static ReqParam weekReportLink = new ReqParam("API/OA/V1/WeekReportLink",
            GET, OAMasterData.class, new String[]{"formID", "createUser", "startDate", "endDate", "cardCode"});

    *//**
     * 3.17.20 客户日志报表
     * http://localhost:9998/API/BaseData/MasterData/V1/VisitYearReport/{sessionKey}/{orderType}/{cardCode}/{token}
     * 已测试
     *//*
    public static ReqParam masterDataVisitYearReport = new ReqParam(
            "API/BaseData/MasterData/V1/VisitYearReport", GET,
            YearItem.class, new String[]{"orderType", "cardCode"});

    *//**
     * 3.16.27 用户周报表
     * http://localhost:9998/API/OA/V1/UserWeekReport/{sessionKey}/{userSign:int}/{orderTypes}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaUserWeekReport = new ReqParam("API/OA/V1/UserWeekReport",
            GET, DayCountItem.class, new String[]{"userSign", "orderTypes"});

    *//**
     * 暂时不处理
     * 读取修改的单据
     * http://localhost:9998/OA/ModifyData/{sessionKey}
     *//*
    public static ReqParam oaModifyData = new ReqParam("OA/ModifyData", POST,
            true, true, OAMasterData.class);

    *//**
     * 3.16.18
     * OA报表单据相关
     * http://localhost:9998/API/OA/V1/ReportByOrder/{sessionKey}/{optType:int}/{sliderDown:int}/{refDate:datetime}/{createUser:int}/{token}
     * Sinya - 已测试 可以返回json数据
     *//*
    public static ReqParam oaReportByOrder = new ReqParam("API/OA/V1/ReportByOrder",
            GET, OAMasterData.class, new String[]{"optType", "sliderDown", "refDate", "createUser"});

    *//**
     * 读取单据回复相关
     * http://localhost:9998/API/OA/V1/ReportByReply/{sessionKey}/{optType:int}/{sliderDown:int}/{refDate:datetime}/{createUser:int}/{token}
     * 已测试
     *//*
    public static ReqParam oaGetReportByReply = new ReqParam(
            "API/OA/V1/ReportByReply", GET,
            HttpParse.CommentForAboutEntity.class, new String[]{"optType", "sliderDown", "refDate", "createUser"});

    *//**
     * 根据单据编号查询单据
     * http://localhost:9998/API/OA/V1/GetByKey/{sessionKey}/{docEntry:long}/{orderType:int}/{flowID:long}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaGetByKey = new ReqParam("API/OA/V1/GetByKey", GET, CommentDatas.class, new String[]{"docEntry", "orderType", "flowID"});

    *//**
     * 3.16.29
     * 读取OA互动待办单据
     * http://localhost:9998/API/Remind/V1/GetByKey/{sessionKey}/{docEntry:long}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam remindGetByKey = new ReqParam("API/Remind/V1/GetByKey",
            GET, NewBacmlogOrRemind.class, new String[]{"docEntry"});

    *//**
     * 3.16.28 读取OA单据相关的互动待办
     * http://localhost:9998/API/Remind/V1/ReadByOrder/{sessionKey}/{oaOrderType:int}/{oaDocEntry:long}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam remindReadByOrder = new ReqParam(
            "API/Remind/V1/ReadByOrder", GET,
            NewBacmlogOrRemindResult.class, new String[]{"oaOrderType", "oaDocEntry"});

    *//**
     * 3.16.4	赞
     * http://localhost:9998/API/OA/V1/Praise/{sessionKey}/{isAdd:int}/{docEntry:long}/{orderType:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam praise = new ReqParam("API/OA/V1/Praise", GET, null, new String[]{"isAdd", "docEntry", "orderType"});

    *//**
     * 3.16.25 单据点评前检查
     * http://localhost:9998/API/OA/V1/CommentCheck/{sessionKey}/{formID:int}/{docEntry:long}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaCommentCheck = new ReqParam("API/OA/V1/CommentCheck",
            GET, OACommentCheckResult.class, new String[]{"formID", "docEntry"});

    *//**
     * 提交单据
     * http://localhost:9998/API/OA/V1/Create/{sessionKey}/{token}
     * Sinya - (在创建日程计划中已测试)
     *//*
    public static ReqParam oaCreate = new ReqParam("API/OA/V1/Create", POST, null, null);

    *//**
     * 提交审批单据
     * http://localhost:9998//api/Approve/V1/SaveDraft/{sessionKey}/{formID:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaApproveSave = new ReqParam("api/Approve/V1/SaveDraft",
            POST, null, new String[]{"formID"});
    *//**
     * 暂时不处理，已经弃用，从本地数据库获取
     * 获取直接上属
     *//*
    public static ReqParam myLeader = new ReqParam("MasterData/MyLeader", GET,
            true, false, MasterData.class);
    *//**
     * 获取默认审批人
     * http://locahost:9998/api/Approve/V1/UserApprove/{sessionKey}/{approveID:long}/{status:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaApproveOrder = new ReqParam("api/Approve/V1/UserApprove",
            POST, null, new String[]{"approveID", "status"});

    *//**
     * 评论
     * http://localhost:9998/API/OA/V1/Comment/{sessionKey}/{docEntry:long}/{orderType:int}/{token}
     * 已测试
     *//*
    public static ReqParam oaComment = new ReqParam("API/OA/V1/Comment", POST, null, new String[]{"docEntry", "orderType"});
    *//**
     * 3.15.32	设置任务的完成时间
     * hhttp://localhost:9998/API/OA/V1/TaskNextFinishDate/{sessionKey}/{token}
     * 已测试
     *//*
    public static ReqParam taskNextFinishDate = new ReqParam("API/OA/V1/TaskNextFinishDate", POST, null, null);

    *//**
     * 更改状态(日志、任务评分)
     * http://localhost:9998/API/OA/V1/Status/{sessionKey}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaStatus = new ReqParam("API/OA/V1/Status", POST, null, null);

    *//**
     * 立即回执
     * http://localhost:9998/API/OA/V1/Receipt/{sessionKey}/{docEntry:long}/{orderType:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam receipt = new ReqParam("API/OA/V1/Receipt", GET, null, new String[]{"docEntry", "orderType"});

    *//**
     * 查看审批单据
     * http://localhost:9998//api/Approve/V1/Document/{sessionKey}/{flowID:long}/{token}
     * 已测试
     *//*
    public static ReqParam oaCheckOutApprove = new ReqParam("api/Approve/V1/Document",
            GET, null, new String[]{"flowID"});
    *//**
     * 3.16.13
     * 删除提醒或待办数据
     * http://localhost:9998/API/Remind/V1/Delete/{sessionKey}/{docEntry:long}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam remindDelete = new ReqParam("API/Remind/V1/Delete", DELETE,
            null, new String[]{"docEntry"});

    *//**
     * 关注与取消关注
     * http://localhost:9998/API/OA/V1/Concern/{sessionKey}/{optType:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaConcern = new ReqParam("API/OA/V1/Concern", POST, null, new String[]{"optType"});
    *//**
     * 3.15.9	对象是否关注
     * http://localhost:9998/API/OA/V1/Concern/{sessionKey}/{docEntry:int}/{orderType:int}/{objCode}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam objectConcern = new ReqParam("API/OA/V1/Concern", GET, null, new String[]{"docEntry", "orderType", "objCode"});

    *//**
     * 删除单据
     * http://localhost:9998/API/OA/V1/Delete/{sessionKey}/{docEntry:long}/{orderType:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaDeleteOrder = new ReqParam("API/OA/V1/Delete", DELETE, null, new String[]{"docEntry", "orderType"});
    *//**
     * 3.15.33	OA单据是否能删除
     * http://localhost:9998/API/OA/V1/CanDelete/{sessionKey}/{orderType:int}/{docEntry:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam oaCanDelete = new ReqParam("API/OA/V1/CanDelete", GET, null, new String[]{"orderType", "docEntry"});
    *//**
     * 3.16.5
     * 获取评论，赞和回执
     * http://localhost:9998/API/OA/V1/ReplyData/{sessionKey}/{docEntry:long}/{orderType:int}/{dataType:int}/{refDate:datetime}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam oaReplyData = new ReqParam("API/OA/V1/ReplyData", GET,
            HttpParse.ReplyCommentPraise.class, new String[]{"docEntry", "orderType", "dataType", "refDate"});

    *//**
     * 3.16.15
     * 设置待办提醒时间
     * http://localhost:9998/API/Remind/V1/SetRemindTime/{sessionKey}/{docEntry:long}/{remindTime:datetime}/{token}
     * Sinya -
     *//*
    public static ReqParam setRemindTime = new ReqParam("API/Remind/V1/SetRemindTime",
            GET, null, new String[]{"docEntry", "remindTime"});

    *//**
     * 3.17.9 联系人或业务伙伴的联合跟进人
     * http://localhost:9998/API/BaseData/MasterData/V1/ShareUser/{sessionKey}/{dataType:int}/{ sourcCode }/{token}
     * 已测试
     *//*
    public static ReqParam share_user = new ReqParam("API/BaseData/MasterData/V1/ShareUser",
            GET, ShareUser.class, new String[]{"dataType", "sourcCode"});
    *//**
     * 3.16.10	修改业务伙伴的负责人
     * http://localhost:9998/API/BaseData/MasterData/V1/UpdateBPuser/{sessionKey}/{cardCode}/{targetUser:int}/{token}
     *//*
    public static ReqParam updateBPuser = new ReqParam("API/BaseData/MasterData/V1/UpdateBPuser", GET, null, new String[]{"cardCode", "targetUser"});

    *//**
     * 3.17.7 共享或删除联系人与业务伙伴的用户
     * http://localhost:9998/API/BaseData/MasterData/V1/ShareDeleteMasterUser/{sessionKey}/{token}
     * 已测试
     *//*
    public static ReqParam MasterDataShareDeleteMasterUser = new ReqParam(
            "API/BaseData/MasterData/V1/ShareDeleteMasterUser", POST, Result.class, null);

    *//**
     * 3.17.7 共享或删除联系人与业务伙伴的用户
     * http://localhost:9998/MasterData/ShareOrDelete/{sessionKey}/{optType}/{dataType}/{cntctCode}/{cardCode}/{targetUser}
     * 已废弃
     *//*
    public static ReqParam ShareOrDelete = new ReqParam(
            "MasterData/ShareOrDelete", GET, true, false, Result.class);

    *//**
     * 3.8.1 获取户报表
     * http://localhost:9998/API/Report/V1/UserReport/{sessionKey}/{token}
     * 已测试
     *//*
    public static ReqParam report_user_report = new ReqParam(
            "API/Report/V1/UserReport", GET, CategorysList.class, null);

    *//**
     * 3.8.2
     * 获取报表的参数列表
     * http://localhost:9998/API/Report/V1/Param/{sessionKey}/{queryID:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam report_param = new ReqParam("API/Report/V1/Param", GET,
            QueryResultItems.class, new String[]{"queryID"});

    *//**
     * 3.8.3 获取报表数据(针对带打印模板的查询)
     * http://localhost:9998/API/Report/V1/Data/{sessionKey}/{queryID:int}/{reportID:int}/{currentPage:int}/{token}
     * Sinya - 已测试（用于报表list item获取数据）
     *//*
    public static ReqParam report_data = new ReqParam("API/Report/V1/Data", POST,
            ReportHtmlResult.class, new String[]{"queryID", "reportID", "currentPage"});

    *//**
     * 暂时不处理
     * 带指示文本框 查询
     * http://localhost:9998/Report/ExecuteQuery/{sessionKey}/{queryID}/{currentPage/
     *//*
    public static ReqParam report_execute_query = new ReqParam(
            "Report/ExecuteQuery", POST, true, true, null);

    *//**
     * 3.16.16
     * 修改互动待办
     * http://localhost:9998/API/Remind/V1/Update/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam remindUpdate = new ReqParam("API/Remind/V1/Update", POST,
            null, null);

    *//**
     * 3.2.12 获取最近联系人
     * http://localhost:9998/api/User/V1/ RecentContacts /{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam recentContacts = new ReqParam("api/User/V1/RecentContacts", GET,
            RecentContacts.class, null);

    *//**
     * 3.2.13 删除最近联系人
     * http://localhost:9998/api/User/V1/ RecentContacts/{sessionKey}/{ userSign}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam deleteRecentContacts = new ReqParam("api/User/V1/RecentContacts",
            DELETE, null, new String[]{"userSign"});

    *//**
     * 3.2.18 删除用户
     * http://localhost:9998/api/User/V1/ DeleteUser /{sessionKey}/{ userSign }/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam deleteUser = new ReqParam("api/User/V1/DeleteUser",
            DELETE, null, new String[]{"userSign"});

    *//**
     * 3.16.22 我的工作汇总
     * http://localhost:9998/API/OA/V1/MyWorkSum/{sessionKey}/{token}
     * Sinya -> 已测试，可请求到json数据
     *//*
    public static ReqParam my_work_sum = new ReqParam("API/OA/V1/MyWorkSum",
            GET, MyWorkSumItem.class, null);

    *//**
     * 3.16.23 标记新的回复与赞
     * http://localhost:9998/API/OA/V1/MarkRead/{sessionKey}/{msgType:int}/{token}
     * Sinya -> 已测试 可以请求到json
     *//*
    public static ReqParam oa_mark_read = new ReqParam("API/OA/V1/MarkRead",
            GET, null, new String[]{"msgType"});

    *//**
     * 3.12.1 查看单据信息
     * http://localhost:9998/Approve/OrderTrigger/{sessionKey}/{msgID}
     * Sinya 暂不处理
     *//*
    public static ReqParam orderTrigger = new ReqParam("Approve/OrderTrigger",
            GET, true, true, CommandResult.class);

    *//**
     * 3.23.15 读取消息组 {readAll}
     * 1:读取所有 0:读取未读的,当用户第一次登录时读取所有
     * Hilo 已测试
     *//*
    // http://localhost:9998/API/IM/V1/ReadGroup/{sessionKey}/{readAll:int}/{token} 
    public static ReqParam ReadGroup = new ReqParam(
            "API/IM/V1/ReadGroup", GET, T_OMSG.class, new String[]{"readAll"});

    *//**
     *   * 3.4.2 标记已经读取的消息组  * Hilo 已测试 返回errorCode，errorMessage
     *//*
    // http://localhost:9998/API/IM/V1/MarkGroup/{sessionKey}/{token}
    public static ReqParam mark_group = new ReqParam("API/IM/V1/MarkGroup", GET, Result.class, null);

    *//**
     *   * 3.23.8 标记消息  (每次所所有的未读消息读取完后调用)  * Hilo 已测试
     *//*
    // http://localhost:9998/API/IM/V1/MarkImRead/{sessionKey}/{imType:int}/{sourceID:long}/{token}
    public static ReqParam mark_im = new ReqParam("API/IM/V1/MarkImRead", GET, null, new String[]{"imType", "sourceID"});

    *//**
     * * 5.6 获取讨论组人数
     *//*
    // http://localhost:9998/API/Discussion/V1/Total/{sessionKey}/{docEntry:long}/{token}
    public static ReqParam discussionGroupTotals = new ReqParam("API/Discussion/V1/Total", GET, discussionGroupTotal.class, new String[]{"docEntry"});

    *//**
     *   * 3.5.1 创建讨论组  * Hilo 已测试
     *//*
    // http://localhost:9998/API/Discussion/V1/Builder/{sessionKey}/{token}
    public static ReqParam discussionBuilder = new ReqParam("API/Discussion/V1/Builder", POST, null, null);

    *//**
     *   * 3.5.6 修改主题  * Sinya 找不到newAPI 暂不处理
     *//*
    // http://localhost:9998/Discussion/ModifySubject/{sessionKey}/{groupCode}/{newsubject}
    public static ReqParam modify_subject = new ReqParam("Discussion/ModifySubject", GET, true, false, Result.class);

    *//**
     *   * 3.23.14 修改讨论组主题  * Hilo 已测试
     *//*
    // http://localhost:9998/API/Discussion/V1/ModifyGroupName/{sessionKey}/{docEntry:int}/{token}
    public static ReqParam discussionModifyGroupName = new ReqParam("API/Discussion/V1/ModifyGroupName", POST, null, new String[]{"docEntry"});

    *//**
     *   * 删除用户  * Sinya 找不到newAPI 暂不处理
     *//*
    // http://localhost:9998/Discussion/DeleteUser/{sessionKey}/{groupCode}
    public static ReqParam discussion_delete_user = new ReqParam("Discussion/DeleteUser", POST, true, false, Result.class);

    *//**
     *   * 3.23.13退出讨论组  * Hilo - 已测试
     *//*
    // http://localhost:9998/API/Discussion/V1/UserExit/{sessionKey}/{docEntry:long}/{userSign:int}/{token}
    public static ReqParam discussionUserExit = new ReqParam("API/Discussion/V1/UserExit", DELETE, null, new String[]{"docEntry", "userSign"});

    *//**
     *   * 3.5.3 添加新用户  * Hilo 已测试
     *//*
    // http://localhost:9998/API/Discussion/V1/ AddUsers/{sessionKey}/{docEntry:long}/{token}
    public static ReqParam discussion_add_users = new ReqParam("API/Discussion/V1/AddUsers", POST, null, new String[]{"docEntry"});

    *//**
     *   * 3.23.11	获取讨论组数据  * Hilo 已测试
     *//*
    // http://localhost:9998/API/Discussion/V1/Data/{sessionKey}/{docEntry:long}/{token}
    public static ReqParam disscussGroupData = new ReqParam("API/Discussion/V1/Data", GET, DiscussionBuilder.class, new String[]{"docEntry"});
    *//**
     * 3.5.7	根据警报编号获取查询编号
     *//*
    // http://localhost:9998/api/Alert/V1/AlertQueryID/{sessionKey}/{msgCode:long}/{token}
    public static ReqParam alertQueryID = new ReqParam("api/Alert/V1/AlertQueryID", GET, null, new String[]{"msgCode"});

    *//**
     *   * 3.23.7 读取所有的消息  * Hilo 已测试
     *//*
    // http://localhost:9998/API/IM/V1/ImReadAll/{sessionKey}/{imType:int}/{sourceID:long}/{currentPage:int}/{pageSize:int}/{token}
    public static ReqParam messageImReadAll = new ReqParam("API/IM/V1/ImReadAll", GET, HttpParse.ChatImNOReadResult.class, new String[]{"imType", "sourceID", "currentPage", "pageSize"});

    *//**
     *   * 3.23.16 设置讨论组是否推送消息(免打扰)  * Hilo 已测试
     *//*
    // http://localhost:9998/API/Discussion/V1/PushMsgSet/{sessionKey}/{docEntry:long}/{flag:int}/{token}
    public static ReqParam disscussPushMsgSet = new ReqParam("API/Discussion/V1/PushMsgSet", GET, null, new String[]{"docEntry", "flag"});

    *//**
     * 3.2.10 获取所有需要更新图片的用户
     * http://localhost:9998/MobileUser/UpdateImageUser/{sessionKey}/{updateTime}
     * Sinya 暂不处理
     *//*
    public static ReqParam updateImageUser = new ReqParam(
            "MobileUser/UpdateImageUser", GET, true, true,
            UpdateImageUser.class);

    *//**
     * 3.8.4 根据编号查询数据（针对没有设置打印模板的查询报表）
     * http://localhost:9998/API/Report/V1/DoQuery/{sessionKey}/{queryID:int}/{currentPage:int}/{token}
     * Sinya 已测试
     * Sinya - newAPI中划横线 表示已经弃用？
     *//*
    public static ReqParam doQuery = new ReqParam("API/Report/V1/DoQuery",
            POST, null, new String[]{"queryID", "currentPage"});

    *//**
     * 根据编号查询警报数据
     * http://localhost:9998/api/Alert/V1/Data/{sessionKey}/{msgCode:long}/{currentPage:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam doAlertQuery = new ReqParam("api/Alert/V1/Data",
            GET, null, new String[]{"msgCode", "currentPage"});

    *//**
     * 3.13.3 API 控件
     * 在工程模板文件中获取到Code值后，通过API获取窗口的控件信息。需要在手机客户端缓存.
     * http://localhost:9998/API/BaseData/MobileForm/V1/Control/{sessionKey}/{formID:int}/{token}
     * Sinya
     *//*
    public static ReqParam getControl = new ReqParam("API/BaseData/MobileForm/V1/Control",
            GET, ControlDetail.class, new String[]{"formID"});

    *//**
     * 3.13.3 API
     * 获取单据数据
     * Sinya
     * http://localhost:9998/api/business/document/getdata/{sessionKey}/{formID:int}/{docEntry}/{token}
     * 已测试
     *//*
    public static ReqParam docData = new ReqParam("api/business/document/getdata",
            GET, null, new String[]{"formID", "docEntry"});

    *//**
     * 3.13.3 API
     * 删除单据
     * http://localhost:9998/api/business/document/Delete/{sessionKey}/{formID:int}/{docEntry:int}/{token}
     * 已测试
     * Sinya
     *//*
    public static ReqParam docDelete = new ReqParam("api/business/document/Delete",
            DELETE, null, new String[]{"formID", "docEntry"});

    *//**
     * 3.13.3 API
     * 取消单据
     * http://localhost:9998/api/business/document/Cancel/{sessionKey}/{formID:int}/{docEntry:int}/{token}
     * Sinya - 已测试
     *//*
    //
    public static ReqParam docCancel = new ReqParam("api/business/document/Cancel",
            DELETE, null, new String[]{"formID", "docEntry"});

    *//**
     * 3.13.3 API
     * 关闭单据
     * http://localhost:9998/api/business/document/Close/{sessionKey}/{formID:int}/{docEntry:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam docClose = new ReqParam("api/business/document/Close",
            DELETE, null, new String[]{"formID", "docEntry"});

    *//**
     * 3.7.5
     * 是否需要审批
     * http://localhost:9998//api/Approve/V1/DocIsApproved/{sessionKey}/{formID:int}/{docEntry:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam docIsApproved = new ReqParam("api/Approve/V1/DocIsApproved",
            GET, null, new String[]{"formID", "docEntry"});

    *//**
     * 3.13.3 API
     * 修改单据
     * http://localhost:9998/api/business/document/update/{sessionKey}/{formID:int}/{docEntry:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam docUpdate = new ReqParam("api/business/document/update",
            POST, null, new String[]{"formID", "docEntry"});

    *//**
     * 3.17.20
     * 添加客户主数据
     * http://localhost:9998/api/business/bpartner/add/{sessionkey}/{formID:int}/{token}
     * 已测试
     * Sinya
     *//*
    public static ReqParam customerAdd = new ReqParam("api/business/bpartner/add",
            POST, null, new String[]{"formID"});

    *//**
     * 客户主数据修改
     * http://localhost:36002/api/business/bpartner/update/{sessionKey}/{formID}/{cardCode}/{token}
     *//*
    public static ReqParam customerUpdate = new ReqParam(
            "api/business/bpartner/update", POST, null, new String[]{"formID", "cardCode"});

    *//**
     * 供应商主数据添加
     * http://localhost:9998/Supplier/Add/Json/{sessionKey}/{formID}
     * Sinya 找不到newAPI 暂不处理
     *//*
    public static ReqParam supplierAdd = new ReqParam("Supplier/Add/Json",
            POST, true, false, CommandResult.class);

    *//**
     * 供应商主数据修改
     * http://localhost:9998/Supplier/Update/Json/{sessionKey}/{formID}/{cardCode}
     * Sinya 找不到newAPI 暂不处理
     *//*
    public static ReqParam supplierUpdate = new ReqParam(
            "Supplier/Update/Json", POST, true, false, CommandResult.class);

    *//**
     * 3.13.3 API
     * 单据添加
     * http://localhost:9998/api/business/document/Add/{sessionKey}/{formID:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam docAdd = new ReqParam("api/business/document/Add",
            POST, null, new String[]{"formID"});

    *//**
     * 3.13.3 API
     * 单据验证
     * http://localhost:9998/API/BaseData/MobileForm/V1/OrderDataCheck/{sessionKey}/{formID:int}/{token}
     * 已测试
     * Sinya
     *//*
    public static ReqParam orderDataCheck = new ReqParam("API/BaseData/MobileForm/V1/OrderDataCheck",
            POST, OrderCheckMsg.class, new String[]{"formID"});

    *//**
     * 3.7.3
     * 获取审批模版
     * http://localhost:9998//api/Approve/V1/TempletList/{sessionKey}/{formID:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam getApproveTemplet = new ReqParam("api/Approve/V1/TempletList",
            POST, ApproveTemplate.class, new String[]{"formID"});

    *//**
     * 3.7.4
     * 保存审批的草稿单
     * http://localhost:9998//api/Approve/V1/SaveDraft/{sessionKey}/{formID:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam saveDraftApprove = new ReqParam("api/Approve/V1/SaveDraft",
            POST, null, new String[]{"formID"});

    *//**
     * 3.13.3 API
     * 获取格式化搜索模版
     * http://localhost:9998/API/BaseData/MobileForm/V1/FormatSearch/{sessionKey}/{formID:int}/{token}
     * Sinya 找不到newAPI 暂不处理
     *//*
    public static ReqParam formatSearch = new ReqParam("API/BaseData/MobileForm/V1/FormatSearch",
            GET, FormQueryEntity.class, new String[]{"formID"});

    *//**
     * 3.13.3 API
     * 获取字段选择值
     * http://localhost:9998/MobileForm/FieldSelectValue/{sessionKey}
     * Sinya newAPI没修改 暂不处理
     *//*
    //
    public static ReqParam mobileFormFieldSelectValue = new ReqParam(
            "MobileForm/FieldSelectValue", POST, true, true,
            CommandResult.class);

    *//**
     * 3.11.1
     * 邮件类别
     * http://localhost:9998/api/Mail/V1/Category/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam mailCategory = new ReqParam("api/Mail/V1/Category",
            GET, CategoriesItems.class, null);

    *//**
     * 3.11.11
     * 读取邮件
     * http://localhost:9998/api/Mail/V1/Read/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam mailRead = new ReqParam("api/Mail/V1/Read",
            POST, Mail.class, null);

    *//**
     * 3.11.8
     * 搜索邮件
     * http://localhost:9998/mail/MailSearch/{sessionKey}/{currentPage}/{type}
     * Sinya 暂不处理
     *//*
    public static ReqParam mailSearch = new ReqParam("mail/MailSearch", POST,
            true, true, EmailReadResult.class);

    *//**
     * 3.11.2
     * 邮件类别查询下一页邮件
     * http://localhost:9998/mail/Data/{sessionKey}/{type}/{currentPage}
     * Sinya 暂不处理
     *//*
    public static ReqParam mailData = new ReqParam("mail/Data", GET, true,
            true, CommandResult.class);

    *//**
     * 3.11.4
     * 邮件标记已读
     * http://localhost:9998/api/Mail/V1/MarkRead/{sessionKey}/{mailID:long}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam mailMarkRead = new ReqParam("api/Mail/V1/MarkRead",
            GET, null, new String[]{"mailID"});

    *//**
     * 3.11.9
     * 删除邮件到垃圾箱
     * http://localhost:9998/api/Mail/V1/TrashBox/{sessionKey}/{mailID:long}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam mailTrashBox = new ReqParam("api/Mail/V1/TrashBox",
            DELETE, null, new String[]{"mailID"});

    *//**
     * 3.11.10
     * 删除邮件
     * http://localhost:9998/api/Mail/V1/DeleteMail/{sessionKey}/{mailID:long}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam mailDeleteMail = new ReqParam("api/Mail/V1/DeleteMail",
            DELETE, null, new String[]{"mailID"});

    *//**
     * 3.11.5
     * 获取邮件内容
     * http://localhost:9998/api/Mail/V1/MailBody/{sessionKey}/{mailID:long}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam mailMailBody = new ReqParam("api/Mail/V1/MailBody",
            GET, null, new String[]{"mailID"});

    *//**
     * 获取邮件附近信息
     * http://localhost:9998/ReceiveData/FileInfo/{sessionKey}/{fileName}
     * Sinya 找不到newAPI 暂不处理
     *//*
    public static ReqParam mailFileInfo = new ReqParam("ReceiveData/FileInfo",
            GET, true, false, CommandResult.class);

    *//**
     * 3.11.6发送邮件
     * http://localhost:9998/api/Mail/V1/SendMail/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam mailSendMail = new ReqParam("api/Mail/V1/SendMail",
            POST, null, null);

    *//**
     * 3.16.21
     * 获取周报表数据
     * http://localhost:9998/API/OA/V1/WeekReport/{sessionKey}/{formID:int}/{dataType:int}/{startDate:datetime}/{endDate:datetime}/{deptCode}/{token}
     * 已测试
     * Sinya
     *//*
    public static ReqParam OAWeekReport = new ReqParam("API/OA/V1/WeekReport",
            GET, VisitWeekResult.class, new String[]{"formID", "dataType", "startDate", "endDate", "deptCode"});

    *//**
     * 3.19.6
     * 删除部门
     * http://localhost:9998/API/BaseData/Config/V1/ DeleteDept/{sessionKey}/{token}
     * Sinya - 暂无调用的地方
     *//*
    public static ReqParam deleteDept = new ReqParam("API/BaseData/Config/V1/DeleteDept",
            POST, null, null);

    *//**
     * 3.19.7
     * 修改部门名称
     * http://localhost:9998/API/BaseData/Config/V1/ ModifyDeptName/{sessionKey}/{token}
     * Sinya - 暂无调用的地方
     *//*
    public static ReqParam modifyDeptName = new ReqParam("API/BaseData/Config/V1/ModifyDeptName",
            GET, null, null);

    *//**
     * 3.19.4
     * 创建部门
     * http://localhost:9998/API/BaseData/Config/V1/CreateDept/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam createDept = new ReqParam("API/BaseData/Config/V1/CreateDept",
            POST, null, null);

    *//**
     * 3.19.1
     * 创建角色
     * http://localhost:9998/API/BaseData/Config/V1/ CreateRole/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam createRole = new ReqParam("API/BaseData/Config/V1/CreateRole",
            POST, null, null);

    *//**
     * 3.19.5
     * 设置用户部门与角色
     * http://localhost:9998/API/BaseData/Config/V1/ SetUserDeptRole/{sessionKey}/{token}
     * Sinya -已测试
     *//*
    public static ReqParam setUserDeptRole = new ReqParam("API/BaseData/Config/V1/SetUserDeptRole",
            POST, null, null);

    *//**
     * 3.20.1
     * 获取图库类别
     * http://localhost:9998/API/BaseData/ImageLibrary/V1/Category/{sessionKey}/{menuID:int}/{token}
     * Hilo 已测试
     *//*
    public static ReqParam category = new ReqParam("API/BaseData/ImageLibrary/V1/Category",
            GET, Category.class, new String[]{"menuID"});

    *//**
     * 3.20.2
     * 获取精选图片
     * http://localhost:9998/API/BaseData/ImageLibrary/V1/Chosen/{sessionKey}/{menuID:int}/{currentPage:int}/{token}
     * Hilo 已测试
     *//*
    public static ReqParam handPick = new ReqParam("API/BaseData/ImageLibrary/V1/Chosen",
            GET, HandPickResult.class, new String[]{"menuID", "currentPage"});

    *//**
     * 3.20.3
     * 按类别搜索图片
     * http://localhost:9998/API/BaseData/ImageLibrary/V1/Search/{sessionKey}/{category:int}/{category1:int}/{category2:int}/{category3:int}/{currentPage:int}/{token}
     * Hilo 已测试
     *//*
    public static ReqParam searchCategory = new ReqParam("API/BaseData/ImageLibrary/V1/Search",
            GET, SearchCategoryResult.class, new String[]{"category", "category1", "category2", "category3", "currentPage"});

    *//**
     * 3.19.4
     * 创建部门
     * http://localhost:9998/API/BaseData/Config/V1/CreateDept/{sessionKey}/{token}
     * Sinya 已测试
     *//*
    public static ReqParam baseDataCreateDept = new ReqParam(
            "API/BaseData/Config/V1/CreateDept", POST, null, null);
    *//**
     * 3.19.4
     * 删除部门
     * http://localhost:9998/API/BaseData/Config/V1/ DeleteDept/{sessionKey}/{token}
     * Sinya 已测试
     *//*
    public static ReqParam baseDataDeleteDept = new ReqParam("API/BaseData/Config/V1/DeleteDept",
            POST, null, null);

    *//**
     * 3.19.7
     * 修改部门名称
     * http://localhost:9998/API/BaseData/Config/V1/ ModifyDeptName/{sessionKey}/{token}
     * Sinya 已测试
     *//*
    public static ReqParam baseDataModifyDeptName = new ReqParam("API/BaseData/Config/V1/ModifyDeptName",
            POST, null, null);

    *//**
     * 3.19.4
     * 创建角色
     * http://localhost:9998/API/BaseData/Config/V1/ CreateRole/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam baseDataCreateRole = new ReqParam("API/BaseData/Config/V1/CreateRole",
            POST, null, null);

    *//**
     * 3.19.4
     * 删除角色
     * http://localhost:9998/API/BaseData/Config/V1/DeleteRole/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam baseDataDeleteRole = new ReqParam("API/BaseData/Config/V1/DeleteRole",
            POST, null, null);

    *//**
     * 3.19.8
     * 删除角色用户或部门用户
     * http://localhost:9998/API/BaseData/Config/V1/DeleteUser/{sessionKey}/{type:int}/{deptOrRoleID}/{userSign:int}/{token}
     * Sinya 已测试
     *//*
    public static ReqParam baseDataDeleteUser = new ReqParam("API/BaseData/Config/V1/DeleteUser",
            GET, null, new String[]{"type", "deptOrRoleID", "userSign"});

    *//**
     * 3.19.3
     * 修改角色名称
     * http://localhost:9998/API/BaseData/Config/V1/ModifyRoleName/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam baseDataModifyRoleName = new ReqParam("API/BaseData/Config/V1/ModifyRoleName",
            POST, null, null);

    *//**
     * 3.20.4
     * 查看图片组详情
     * http://localhost:9998/API/BaseData/ImageLibrary/V1/Detail/{sessionKey}/{code:int}/{currentPage:int}/{token}
     * Hilo 已测试
     *//*
    public static ReqParam detailPick = new ReqParam("API/BaseData/ImageLibrary/V1/Detail",
            GET, DetailPickResult.class, new String[]{"code", "currentPage"});
    *//**
     * 3.17.28
     * 添加或删除强制关联用户信息(只有管理员才能调用)
     * http://localhost:9998/API/BaseData/MasterData/V1/AddForceLinkUser/{sessionKey}/{userSign:int}/{token}
     * Sinya 已测试
     *//*
    public static ReqParam masterDataAddForceLinkUser = new ReqParam("API/BaseData/MasterData/V1/AddForceLinkUser",
            POST, null, new String[]{"userSign"});

    *//**
     * 3.19.9
     * 添加角色用户或部门用户
     * http://localhost:9998/API/BaseData/Config/V1/AddDeptOrRoleUser/{sessionKey}/{type:int}/{deptOrRoleID}/{token}
     * Sinya 已测试
     *//*
    public static ReqParam addDeptOrRoleUser = new ReqParam("API/BaseData/Config/V1/AddDeptOrRoleUser",
            POST, null, new String[]{"type", "deptOrRoleID"});

    *//**
     * 3.16.30
     * 系统文件
     * http://localhost:9998/API/OA/V1/UserFile/{sessionKey}/{dataType:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam oaUserFile = new ReqParam("API/OA/V1/UserFile",
            GET, FileSelectBean.class, new String[]{"dataType"});

    *//**
     * 3.23.6
     * 读取所有的未读取消息
     * http://localhost:9998/API/IM/V1/ImNotRead/{sessionKey}/{imType:int}/{sourceID:long}/{clientMaxLineNum:long}/{currentPage:int}/{pageSize:int}/{token}
     * Hilo 已测试
     *//*
    public static ReqParam imNotRead = new ReqParam("API/IM/V1/ImNotRead",
            GET, ChatImNOReadResult.class, new String[]{"imType", "sourceID", "clientMaxLineNum", "currentPage", "pageSize"});

    *//**
     * 3.6.1
     * 获取警报列表
     * http://localhost:9998/api/Alert/V1/AlterList/{sessionKey}/{currentPage:int}/{token}
     * Sinya - 已测试，可获取{}json
     *//*
    public static ReqParam AlterList = new ReqParam("api/Alert/V1/AlterList",
            GET, AlterListResult.class, new String[]{"currentPage"});

    *//**
     * 3.6.15
     * 刪除警报
     * http://localhost:9998/api/Alert/V1/Delete/{sessionKey}/{msgCode:long}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam deleteAlert = new ReqParam("api/Alert/V1/Delete",
            DELETE, PacketBase.class, new String[]{"msgCode"});

    *//**
     * 3.6.3
     * 警报标记已读取
     * http://localhost:9998/api/Alert/V1/MarkRead/{sessionKey}/{msgCode:long}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam alertMarkRead = new ReqParam("api/Alert/V1/MarkRead",
            GET, PacketBase.class, new String[]{"msgCode"});

    *//**
     * 3.17.26
     * 读取强制关联用户信息(只有管理员才能调用)
     * http://localhost:9998/API/BaseData/MasterData/V1/ForceLinkUser/{sessionKey}/{token}
     * Sinya 已测试
     *//*
    public static ReqParam asterDataForceLinkUser = new ReqParam("API/BaseData/MasterData/V1/ForceLinkUser",
            GET, ForceLinkUserBean.class, null);

    *//**
     * 3.17.27
     * 读取强制关联用户信息名细数据(只有管理员才能调用)
     * http://localhost:9998/API/BaseData/MasterData/V1/ForceLinkUserItem/{sessionKey}/{userSign:int}/{token}
     * 已测试
     * Sinya
     *//*
    public static ReqParam asterDataForceLinkUserItem = new ReqParam("API/BaseData/MasterData/V1/ForceLinkUserItem",
            GET, ForceLinkUserItemBean.class, new String[]{"userSign"});

    *//**
     * 3.17.22
     * 导入通讯录到联系人
     * http://localhost:9998/API/BaseData/MasterData/V1/ImportAddressBook/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam importAddressBook = new ReqParam("API/BaseData/MasterData/V1/ImportAddressBook",
            POST, null, null);

    *//**
     * 3.17.28
     * 删除关联用户(只有管理员才能调用)
     * http://localhost:9998/API/BaseData/MasterData/V1/DeleteForceLinkUser/{sessionKey}/{userSign:int}/{targetUser:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam masterDataDeleteForceLinkUser = new ReqParam("API/BaseData/MasterData/V1/DeleteForceLinkUser",
            GET, null, new String[]{"userSign", "targetUser"});

    *//**
     * 3.14.5
     * 创建工作台菜单
     * http://localhost:9998/api/WorkConsole/V1/CreateMenu/{sessionKey}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam WorkConsoleCreateMenu = new ReqParam("api/WorkConsole/V1/CreateMenu",
            POST, null, null);

    *//**
     * 3.14.6
     * 修改菜单组标题
     * http://localhost:9998/api/WorkConsole/V1/ModifyMenuText/{sessionKey}/{menuGroupCode:int}/{token}
     * Sinya - 已测试
     *//*
    public static ReqParam WorkConsoleModifyMenuText = new ReqParam("api/WorkConsole/V1/ModifyMenuText",
            POST, null, new String[]{"menuGroupCode"});
    *//**
     * 3.17.21	获取客户组
     * http://localhost:9998/API/BaseData/MasterDataConfig/V1/CardGroup/{sessionKey}/{cardType:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam master_data_cardgroup_data = new ReqParam(
            "API/BaseData/MasterDataConfig/V1/CardGroup", GET, ExpenseDataItems.class, new String[]{"cardType"});

    *//**
     * 3.17.22	添加客户组
     * http://localhost:9998/API/BaseData/MasterDataConfig/V1/AddCardGroup/{sessionKey}/{cardType:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam addCardGroup = new ReqParam(
            "API/BaseData/MasterDataConfig/V1/AddCardGroup", POST, null, new String[]{"cardType"});
    *//**
     * 3.17.24	修改客户组
     * http://localhost:9998/API/BaseData/MasterDataConfig/V1/ModifyCardGroup/{sessionKey}/{cardType:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam modifyCardGroup = new ReqParam(
            "API/BaseData/MasterDataConfig/V1/ModifyCardGroup", POST, null, new String[]{"cardType"});
    *//**
     * 3.17.23	删除客户组
     * http://localhost:9998/API/BaseData/MasterDataConfig/V1/DeleteCardGroup/{sessionKey}/{cardGroupCode:int}/{token}
     * 已测试，Dick
     *//*
    public static ReqParam deleteCardGroup = new ReqParam("API/BaseData/MasterDataConfig/V1/DeleteCardGroup", DELETE, null, new String[]{"cardGroupCode"});


    *//**
     * 3.27.1 获取能创建自定义字段的所有数据表
     * http://localhost:8080/API/BaseData/UserField/V1/CustomTable/{sessionKey}/{token}
     *//*
    public static ReqParam customTable = new ReqParam("API/BaseData/UserField/V1/CustomTable",
            GET, FieldBean.class, null);


    *//**
     * 3.27.4获取数据已经添加的自定义字段
     * http://localhost:8080/API/BaseData/UserField/V1/ExistField/{sessionKey}/{token}
     *//*
    public static ReqParam existField = new ReqParam("API/BaseData/UserField/V1/ExistField",
            POST, UserExistFieldItem.class, null);


    *//**
     * 3.27.2获取数据表可使用的自定义字段
     * http://localhost:8080/API/BaseData/UserField/V1/TabeUseFields/{sessionKey}/{tableID:int}/{token}
     *//*
    public static ReqParam tabeUseFields2 = new ReqParam("API/BaseData/UserField/V1/TabeUseFields",
            GET, TabeUseField.class, new String[]{"tableID"});

    *//**
     * 3.27.3添加自定义字段
     * http://localhost:8080/API/BaseData/UserField/V1/Add/{sessionKey}/{tableID:int}/{token}
     *//*
    public static ReqParam fieldsAdd = new ReqParam("API/BaseData/UserField/V1/Add",
            POST, null, new String[]{"tableID"});

    *//**
     * 3.28.12	获取所有设置的自定义字段
     * http://localhost:8080/API/BaseData/UserField/V1/All/{sessionKey}/{token}
     *//*
    public static ReqParam tabeUseFields = new ReqParam("API/BaseData/UserField/V1/All",
            GET, UserExistFieldItem.class, null);

    *//**
     * 3.28.5删除数据表的自定义字段
     * http://localhost:8080/API/BaseData/UserField/V1/Delete/{sessionKey}/{tableID:int}/{fieldID:int}/{token}
     *//*
    public static ReqParam fieldsItemDelete = new ReqParam("API/BaseData/UserField/V1/Delete",
            DELETE, null, new String[]{"tableID", "fieldID"});

    *//**
     * 3.28.10字段空值属性设置
     * http://localhost:8080/API/BaseData/UserField/V1/FieldNotNull/{sessionKey}/{tableID:int}/{fieldID:int}/{flag:int}/{token}
     *//*
    public static ReqParam fieldNotNull = new ReqParam("API/BaseData/UserField/V1/FieldNotNull",
            GET, null, new String[]{"tableID", "fieldID", "flag"});

    *//**
     * 3.28.10字段空值属性设置
     * http://localhost:8080/API/BaseData/UserField/V1/FieldMultiLine/{sessionKey}/{tableID:int}/{fieldID:int}/{flag:int}/{token}
     *//*
    public static ReqParam fieldMultiLine = new ReqParam("API/BaseData/UserField/V1/FieldMultiLine",
            GET, null, new String[]{"tableID", "fieldID", "flag"});

    *//**
     * 3.28.8修改数据表字段的标题
     * http://localhost:8080/API/BaseData/UserField/V1/ModifyFieldCaption/{sessionKey}/{tableID:int}/{fieldID:int}/{token}
     *//*
    public static ReqParam modifyFieldCaption = new ReqParam("API/BaseData/UserField/V1/ModifyFieldCaption",
            POST, null, new String[]{"tableID", "fieldID"});

    *//**
     * 3.28.6删除数据表下拉列表字段的键值
     * http://localhost:8080/API/BaseData/UserField/V1/DeleteFieldDropItem/{sessionKey}/{tableID:int}/{fieldID:int}/{delCode:int}/{token}
     *//*
    public static ReqParam deleteFieldDropItem = new ReqParam("API/BaseData/UserField/V1/DeleteFieldDropItem",
            DELETE, null, new String[]{"tableID", "fieldID", "delCode"});

    *//**
     * 3.28.7修改数据表下拉列表字段的键值
     * http://localhost:8080/API/BaseData/UserField/V1/ModifyFieldDropDownValue/{sessionKey}/{tableID:int}/{fieldID:int}/{modifyCode:int}/{token}
     *//*
    public static ReqParam modifyFieldDropDownValue = new ReqParam("API/BaseData/UserField/V1/ModifyFieldDropDownValue",
            POST, null, new String[]{"tableID", "fieldID", "modifyCode"});

    *//**
     * 3.28.9添加字段的下拉列表项
     * http://localhost:8080/API/BaseData/UserField/V1/AddFieldDropItem/{sessionKey}/{tableID:int}/{fieldID:int}/{token}
     *//*
    public static ReqParam addFieldDropItem = new ReqParam("API/BaseData/UserField/V1/AddFieldDropItem",
            POST, null, new String[]{"tableID", "fieldID"});
    *//**
     * 3.17.46	获取控件下拉数据选项
     * http://localhost:9998/API/BaseData/MasterDataConfig/V1/DropItem/{sessionKey}/{token}
     *//*
    public static ReqParam masterDropItem = new ReqParam("API/BaseData/MasterDataConfig/V1/DropItem",
            POST, DropItemValue.class, null);
    *//**
     * 12.2.1	添加联系人
     * http://localhost:36002/api/business/contact/v1/add/{sessionKey}/{token}
     *//*
    public static ReqParam contactAdd = new ReqParam("api/business/contact/v1/add",
            POST, null, null);
    *//**
     * 12.2.2	修改联系人
     * http://localhost:36002/api/business/contact/v1/update/{sessionKey}/{token}
     *//*
    public static ReqParam contactUpdate = new ReqParam("api/business/contact/v1/update",
            POST, null, null);
    *//**
     * 12.2.3	获取单个联系人
     * http://localhost:36002/api/business/contact/v1/get/{sessionKey}/{id}/{token}
     *//*
    public static ReqParam contactGet = new ReqParam("api/business/contact/v1/get",
            GET, null, new String[]{"id"});
    *//**
     * 12.2.4	删除联系人
     * http://localhost:36002/api/business/contact/v1/delete/{sessionKey}/{id}/{token}
     *//*
    public static ReqParam contactDelete = new ReqParam("api/business/contact/v1/delete",
            DELETE, null, new String[]{"id"});
    *//**
     * 12.3.3	获取单个业务伙伴
     * http://localhost:36002/api/business/bpartner/get/{sessionKey}/{formID}/{cardCode}/{token}
     *//*
    public static ReqParam businessGet = new ReqParam("api/business/bpartner/get",
            GET, null, new String[]{"formID", "cardCode"});
    *//**
     * 12.2.4	删除客户
     * http://localhost:36002/api/business/bpartner/delete/{sessionKey}/{formID}/{cardCode}/{token}
     *//*
    public static ReqParam businessDelete = new ReqParam("api/business/bpartner/delete",
            DELETE, null, new String[]{"formID", "cardCode"});
    *//**
     * 3.7.6	获取审批单据的数据
     * http://localhost:9998/api/Approve/V1/DraftOrderData/{sessionKey}/{flowID:long}/{token}
     *//*
    public static ReqParam draftOrderData = new ReqParam("api/Approve/V1/DraftOrderData",
            GET, null, new String[]{"flowID"});
    *//**
     * 3.20.10	激活公司
     * http://localhost:9998/API/BaseData/Config/V1/ActiveCompany/{sessionKey}/{token}
     *//*
    public static ReqParam activeCompany = new ReqParam("API/BaseData/Config/V1/ActiveCompany",
            POST, null, null);

    *//**
     * 3.30.6	获取用户绑定的WIFI (移动调用)
     * http://localhost:9999/api/business/WifiSignin/v1/UserBind/{sessionKey}/{token}
     *//*
    public static ReqParam wifiUserBind = new ReqParam("api/business/WifiSignin/v1/UserBind", GET, WifiSigninResult.class, null);
    *//**
     * 3.30.11	签到(移动调用)
     * http://localhost:9999/api/business/WifiSignin/v1/Signin/{sessionKey}/{token}
     *//*
    public static ReqParam wifiSignin = new ReqParam("api/business/WifiSignin/v1/Signin", POST, null, null);
    *//**
     * 3.30.12	我的签到记录(移动调用)
     * http://localhost:9999/api/business/WifiSignin/v1/Report/{sessionKey}/{year:int}/{month:int}/{token}
     *//*
    public static ReqParam wifiSigninReport = new ReqParam("api/business/WifiSignin/v1/Report", GET, SiginItem.class, new String[]{"year", "month"});

    *//**
     * 3.2.32更改用户名（只有管理员才能修改其它用户的名称，自己可以修改自己的名称）
     * http://localhost:9998/api/User/V1/Rename/{sessionKey}/{userSign:int}/{token}
     *//*
    public static ReqParam rename = new ReqParam("api/User/V1/Rename", POST, null, new String[]{"userSign"});

    *//**
     * 3.5.1 获取警报列表
     * http://localhost:9998api/Alert/V1/AlterList/{sessionKey}/{status:int}/{currentPage:int}/{token}
     *//*
    public static ReqParam alterList = new ReqParam("api/Alert/V1/AlterList", GET, AlterListResult.class, new String[]{"status", "currentPage"});

    *//**
     * 3.5.5 标记警报为已完成
     * http://localhost:9998/api/Alert/V1/MarkGroup/{sessionKey}/{msgCode:long}/{status:int}/{token}
     *//*
    public static ReqParam markAlertStatus = new ReqParam("api/Alert/V1/MarkStatus", GET, null, new String[]{"msgCode", "status"});

    *//**
     * 3.5.6获取警报的讨论组标号
     * http://localhost:9998/api/Alert/V1/AlterDiscussionGroup/{sessionKey}/{alamCode:long}/{token}
     *//*
    public static ReqParam alterDiscussionGroup = new ReqParam("api/Alert/V1/AlterDiscussionGroup", GET, null, new String[]{"alamCode"});

    *//**
     * 3.29.3 获取无效的讨论组
     * http://localhost:9998/API/Discussion/V1/InvalidGroup/{sessionKey}/{token}
     *//*
    public static ReqParam invalidGroup = new ReqParam("API/Discussion/V1/InvalidGroup", POST, Integer.class, null);*/

}
