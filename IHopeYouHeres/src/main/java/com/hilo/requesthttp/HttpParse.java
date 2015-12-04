package com.hilo.requesthttp;

import com.hilo.bean.PacketBase;

import java.io.Serializable;

public class HttpParse {

    public static class LoginVerify extends PacketBase {
        public String ServerName;
        public int Port;
        public String ExpiredDate;
    }

    public static class LoginData {
        public int CanAddCustome;
        public int CanAddSupplier;
        public String CompanyName;
        public String DataFormat;
        public String DbName;
        public String DocKey;
        public String DocUrl;
        public int GpsTrack;
        public String GpsTrackEndTime;
        public String GpsTrackStartTime;
        public int Interval;
        public int IsSbo;
        public int PercentDec;
        public int PriceDec;
        public int QuantityDec;
        public int RateDec;
        public String SessionKey;
        public int SumDec;
        public int SuperUser;
        public String UserCode;
        public String UserName;
        public int UserSign;
        public int Demo;
        public int BuilderBaseData;
        public String MyDept;
        public String MyRole;
        public String ApiVersion;
        // 签到抄送用户类型 0:可以不选 1:强制手动选择 2:自动选择直接上级 3:选择固定用户
        public int SignInCCType;
        // 签到固定抄送用户
        public int SignInCCUser;
        public int AddProject;// 是否可以添加项目
        public int ShareChooseAllBP; // 分享时可选择所有的业务伙伴
        public int ShareChooseAllProject; // 分享时可选择所有的项目
        public int ShareChooseAllCntct; // 分享时可选择所有的联系
        public String ImServer;
        public String ImPort;
        public String SecretCode;//安全码   用于计算tonken值
        public int PrivateCloud;//是否是私有云服务  1:是  0:否
        // public String serverAddress; //it's no promble to add this fields to
        // change to Company class.
        // public String userImagePath;
        // public String userFilePath;
        // public String appPath;
        public String DownLoadUrl;   //文档下载url
        public String UploadUrl;  //文档上传url
        public int Active;
        public String LogHint1;//工作总结",
        public String LogHint2;//工作计划",
        public String LogHint3;//心得体会"
    }

    public static class Login extends PacketBase {
        public LoginData LoginData;
    }



/*	public static class DisscussGroups extends PacketBase {
        private List<DisscussGroupItem> Items;

		public List<DisscussGroupItem> getItems() {
			if (Items == null)
				Items = new ArrayList<>();
			return Items;
		}

		public void setItems(List<DisscussGroupItem> items) {
			Items = items;
		}

	}*/


    public class NotifyGroup implements Serializable {

/*    “ID”:1,//唯一编号
            “Title”:”欢迎使用企商宝”,//标题
            “Content”:”欢迎使用企商宝，使用过程中有任何疑问，请联系客服人员”,//内容
            “CreateDate”:””,//创建日期，日期格式
            “NotifyDate”:””,//通知日期，日期格式*/

        private static final long serialVersionUID = 1L;

        public int ID;
        public String Title;
        public String Content;
        public String CreateDate;
        public String NotifyDate;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }

        public String getNotifyDate() {
            return NotifyDate;
        }

        public void setNotifyDate(String notifyDate) {
            NotifyDate = notifyDate;
        }

        @Override
        public String toString() {
            return "NotifyGroup{" +
                    "ID=" + ID +
                    ", Title='" + Title + '\'' +
                    ", Content='" + Content + '\'' +
                    ", CreateDate='" + CreateDate + '\'' +
                    ", NotifyDate='" + NotifyDate + '\'' +
                    '}';
        }
    }
}
