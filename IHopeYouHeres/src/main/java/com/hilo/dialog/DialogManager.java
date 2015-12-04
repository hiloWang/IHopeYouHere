package com.hilo.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.hilo.R;
import com.hilo.adapter.TestDialogAdapter;
import com.hilo.animotions.BaseAnimatorSet;
import com.hilo.animotions.BounceEnter.BounceTopEnter;
import com.hilo.animotions.SlideExit.SlideBottomExit;
import com.hilo.bean.PacketBase;
import com.hilo.dialog.animdilogs.ActionSheetDialog;
import com.hilo.dialog.animdilogs.DialogMenuItem;
import com.hilo.dialog.animdilogs.MaterialDialog;
import com.hilo.dialog.animdilogs.NormalDialog;
import com.hilo.dialog.animdilogs.NormalListDialog;
import com.hilo.listeners.OnBtnClickL;
import com.hilo.listeners.OnOperItemClickL;
import com.hilo.others.HttpFactory;
import com.hilo.util.T;
import com.hilo.util.Utils;

import java.util.ArrayList;

/**
 * Created by hilo on 15/12/1.
 * <p/>
 * Drscription:
 */
public class DialogManager {

    private static DialogManager mDialogManager;
    public BaseAnimatorSet bas_in, bas_out;
    public ArrayList<DialogMenuItem> testItems = new ArrayList<>();
    public String[] stringItems = {"收藏", "下载", "分享", "删除", "歌手", "专辑"};

    public DialogManager() {
        bas_in = new BounceTopEnter();
        bas_out = new SlideBottomExit();

        testItems.add(new DialogMenuItem("收藏", R.mipmap.ic_winstyle_favor));
        testItems.add(new DialogMenuItem("下载", R.mipmap.ic_winstyle_download));
        testItems.add(new DialogMenuItem("分享", R.mipmap.ic_winstyle_share));
        testItems.add(new DialogMenuItem("删除", R.mipmap.ic_winstyle_delete));
        testItems.add(new DialogMenuItem("歌手", R.mipmap.ic_winstyle_artist));
        testItems.add(new DialogMenuItem("专辑", R.mipmap.ic_winstyle_album));
    }

    public static DialogManager getInstance() {
        if(mDialogManager == null) {
            synchronized (DialogManager.class) {
                if (mDialogManager == null) {
                    mDialogManager = new DialogManager();
                }
            }
        }
        return mDialogManager;
    }

    public void setBasIn(BaseAnimatorSet bas_in) {
        this.bas_in = bas_in;
    }

    public void setBasOut(BaseAnimatorSet bas_out) {
        this.bas_out = bas_out;
    }

    public void NormalDialogStyleOne(final Context context) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.content("是否确定退出程序?")//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "left");
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "right");
                        dialog.dismiss();
                    }
                });
    }

    public void NormalDialogStyleTwo(final Context context) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "left");
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "right");
                        dialog.dismiss();
                    }
                });

    }

    public void NormalDialogCustomAttr(final Context context) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#383838"))//
                .cornerRadius(5)//
                .content("是否确定退出程序?")//
                .contentGravity(Gravity.CENTER)//
                .contentTextColor(Color.parseColor("#ffffff"))//
                .dividerColor(Color.parseColor("#222222"))//
                .btnTextSize(15.5f, 15.5f)//
                .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                .btnPressColor(Color.parseColor("#2B2B2B"))//
                .widthScale(0.85f)//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "left");
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "right");
                        dialog.dismiss();
                    }
                });
    }

    public void NormalDialogOneBtn(final Context context) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.content("你今天的抢购名额已用完~")//
                .btnNum(1)
                .btnText("继续逛逛")//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                T.showShort(context, "middle");
                dialog.dismiss();
            }
        });
    }

    public void NormalDialoThreeBtn(final Context context) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.content("你今天的抢购名额已用完~")//
                .style(NormalDialog.STYLE_TWO)//
                .btnNum(3)
                .btnText("取消", "确定", "继续逛逛")//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "left");
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "right");
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "middle");
                        dialog.dismiss();
                    }
                });
    }

    public void MaterialDialogDefault(final Context context) {
        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.content(
                "嗨！这是一个 MaterialDialogDefault. 它非常方便使用，你只需将它实例化，这个美观的对话框便会自动地显示出来。"
                        + "它简洁小巧，完全遵照 Google 2014 年发布的 Material Design 风格，希望你能喜欢它！^ ^")//
                .btnText("取消", "确定")//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "left");
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "right");
                        dialog.dismiss();
                    }
                }
        );
    }


    public void MaterialDialogThreeBtns(final Context context) {
        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.isTitleShow(false)//
                .btnNum(3)
                .content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
                .btnText("确定", "取消", "知道了")//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "left");
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "right");
                        dialog.dismiss();
                    }
                }
                ,
                new OnBtnClickL() {//middle btn click listener
                    @Override
                    public void onBtnClick() {
                        T.showShort(context, "middle");
                        dialog.dismiss();
                    }
                }
        );
    }

    public void MaterialDialogOneBtn(final Context context) {
        final MaterialDialog dialog = new MaterialDialog(context);
        dialog//
                .btnNum(1)
                .content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
                .btnText("确定")//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                T.showShort(context, "middle");
                dialog.dismiss();
            }
        });
    }

    public void NormalListDialog(final Context context) {
        final NormalListDialog dialog = new NormalListDialog(context, testItems);
        dialog.title("请选择")//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(context, testItems.get(position).operName);
                dialog.dismiss();
            }
        });
    }


    public void NormalListDialogCustomAttr(final Context context) {
        final NormalListDialog dialog = new NormalListDialog(context, testItems);
        dialog.title("请选择")//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#409ED7"))//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show(R.style.myDialogAnim);

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(context, testItems.get(position).operName);
                dialog.dismiss();
            }
        });
    }


    public void NormalListDialogNoTitle(final Context context) {
        final NormalListDialog dialog = new NormalListDialog(context, testItems);
        dialog.title("请选择")//
                .isTitleShow(false)//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(15)//
                .cornerRadius(2)//
                .widthScale(0.75f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(context, testItems.get(position).operName);
                dialog.dismiss();
            }
        });
    }

    public void NormalListDialogStringArr(final Context context) {

        final NormalListDialog dialog = new NormalListDialog(context, stringItems);
        dialog.title("请选择")//
                .layoutAnimation(null)
                .show(R.style.myDialogAnim);
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(context, testItems.get(position).operName);
                dialog.dismiss();
            }
        });
    }

    public void NormalListDialogAdapter(final Context context) {
        final NormalListDialog dialog = new NormalListDialog(context, new TestDialogAdapter(context, testItems));
        dialog.title("请选择")//
                .show(R.style.myDialogAnim);
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(context, testItems.get(position).operName);
                dialog.dismiss();
            }
        });
    }


    public void ActionSheetDialog(final Context context) {
        final String[] stringItems = {"接收消息并提醒", "接收消息但不提醒", "收进群助手且不提醒", "屏蔽群消息"};
        final ActionSheetDialog dialog = new ActionSheetDialog(context, stringItems, null);
        dialog.title("选择群消息提醒方式\r\n(该群在电脑的设置:接收消息并提醒)")//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(context, stringItems[position]);
                switch (position) {
                    case 0:
//                        DemoReg dReg = new DemoReg("18676747673","ABCD", UUID.randomUUID().toString());
                        CloudData cData = new CloudData("243df2295c4c56bb19888285ee91e6e9", "kaifaku2", "SANDY", "", "");
                        HttpFactory.getInstance().requestServerUrl(Utils.parseToJson(cData), "http://cloud2.sap360.com.cn:36010/api/User/Login"/*"http://cloud.sap360.com.cn:36010/Register/DemoRegister"*/, true, false, true);
                        break;
                }
                dialog.dismiss();
            }
        });
    }

    public void ActionSheetDialogNoTitle(final Context context) {
        final String[] stringItems = {"版本更新", "帮助与反馈", "退出QQ"};
        final ActionSheetDialog dialog = new ActionSheetDialog(context, stringItems, null);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(context, stringItems[position]);
                dialog.dismiss();
            }
        });
    }

    class DemoReg{
        public String MobilePhone;
        public String CheckCode;   //验证码
        public String GuidCode;
        public DemoReg(String mobilePhone, String checkCode, String guidCode) {
            MobilePhone = mobilePhone;
            CheckCode = checkCode;
            GuidCode = guidCode;
        }
    }

    public class DemoRegister extends PacketBase {
        public int Port;
        public String ServerName;
    }

    public class CloudData {
        public String ClientID;
        public String CompanyCode;
        public String UserCode;
        public String MobilePhone;
        public String Name;

        public CloudData(String clientID, String companyCode, String userCode,
                         String mobilePhone, String name) {
            ClientID = clientID;
            CompanyCode = companyCode;
            UserCode = userCode;
            MobilePhone = mobilePhone;
            Name = name;
        }
    }
}
