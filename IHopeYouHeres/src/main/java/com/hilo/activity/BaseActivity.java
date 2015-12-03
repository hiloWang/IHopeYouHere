package com.hilo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.hilo.animotions.BounceEnter.BounceTopEnter;
import com.hilo.animotions.SlideExit.SlideBottomExit;
import com.hilo.dialog.animdilogs.NormalDialog;
import com.hilo.listeners.OnBtnClickL;
import com.hilo.others.MyApplication;
import com.hilo.receiver.ExceptionLoingOutReceiver;
import com.hilo.util.LogUtils;
import com.hilo.util.Utils;

import org.xutils.x;

import java.util.LinkedList;

/**
 * Created by hilo on 15/11/27.
 * <p/>
 * Drscription:
 */
public class BaseActivity extends AppCompatActivity {

    public static LinkedList<Activity> mActivityArray = new LinkedList<>();
    protected ExceptionLoingOutReceiver receiver;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        x.view().inject(this);
        receiver = new ExceptionLoingOutReceiver();
        IntentFilter intentFilter = new IntentFilter(
                "android.exception.ExceptionLoingOutReceiver");
        registerReceiver(receiver, intentFilter);
        mActivityArray.add(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onDestroy() {
        mActivityArray.remove(this);
        LogUtils.I("mlog", "BaseActivity onDestroy:" + mActivityArray.size());
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final NormalDialog exitDialog = new NormalDialog(mContext);
            exitDialog.content("亲,真的要走吗?再看会儿吧~(●—●)")
                    .style(NormalDialog.STYLE_TWO)
                    .titleTextSize(23)
                    .btnText("继续逛逛", "残忍退出")
                    .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#D4D4D4"))
                    .btnTextSize(16f, 16f)
                    .showAnim(new BounceTopEnter())
                    .dismissAnim(new SlideBottomExit())
                    .show();

            exitDialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            exitDialog.dismiss();
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            exitDialog.superDismiss();
                            finish();
                        }
                    });
        }
        return false;
    }


    public static void exitAllActivity() {
        for (Activity act : mActivityArray) {
            act.finish();
        }
        mActivityArray.clear();
    }

    public static void exitApp() {
        Utils.setAllStaticVarsNull();
        exitAllActivity();
//        DbfEngine.close();
        System.gc();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!Utils.isAppOnForeground(MyApplication.mContext)) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
