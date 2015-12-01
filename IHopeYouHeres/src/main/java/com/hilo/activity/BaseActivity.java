package com.hilo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.hilo.R;
import com.hilo.animotions.BaseAnimatorSet;
import com.hilo.animotions.BounceEnter.BounceTopEnter;
import com.hilo.animotions.SlideExit.SlideBottomExit;
import com.hilo.dialog.DialogManager;
import com.hilo.dialog.animdilogs.NormalDialog;
import com.hilo.fragment.FragmentButton;
import com.hilo.fragment.Section1Fragment;
import com.hilo.fragment.Section2Fragment;
import com.hilo.listeners.MaterialSectionListener;
import com.hilo.listeners.OnBtnClickL;
import com.hilo.navigation.MaterialAccount;
import com.hilo.navigation.MaterialNavigationDrawer;
import com.hilo.navigation.MaterialSection;
import com.hilo.views.widgets.MultiSwipeRefreshLayout;

/**
 * Created by hilo on 15/11/27.
 * <p>
 * Drscription:
 */
public class BaseActivity extends MaterialNavigationDrawer {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }


    @Override
    public void init(Bundle savedInstanceState) {
        // add accounts
        MaterialAccount account = new MaterialAccount(this.getResources(), "hilo", "253123123@qq.com", R.drawable.ic_launcher, R.drawable.navdrawer1);
        this.addAccount(account);

        MaterialAccount account2 = new MaterialAccount(this.getResources(), "Example", "example@example.com", R.drawable.ic_launcher, R.drawable.navdrawer2);
        this.addAccount(account2);

//        MaterialAccount account3 = new MaterialAccount(this.getResources(),"Example","example@example.com",R.drawable.ic_launcher,R.drawable.navdrawer3);
//        this.addAccount(account3);

        // add account sections
        this.addAccountSection(newSection("Account settings", R.drawable.ic_settings, new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {
                Toast.makeText(BaseActivity.this, "Account settings clicked", Toast.LENGTH_SHORT).show();

                // for default section is selected when you click on it
                section.unSelect(); // so deselect the section if you want
            }
        }));


        // set header data
//        setDrawerHeaderImage(R.drawable.navdrawer1);
//        setUsername("hilo");
//        setUserEmail("253123123@qq.com");
//        setFirstAccountPhoto(getResources().getDrawable(R.drawable.ic_launcher));

        // create sections
        this.addSection(newSection("Section 1", new Section1Fragment()));
        this.addSection(newSection("Section 2", new Section2Fragment()));
        this.addSection(newSection("Section 3", R.drawable.ic_mic_white_24dp, new FragmentButton()).setSectionColor(Color.parseColor("#10B8F6")));
        this.addSection(newSection("Section", R.drawable.ic_hotel_grey600_24dp, new FragmentButton()).setSectionColor(Color.parseColor("#10B8F6")));

        // create bottom section
        this.addBottomSection(newSection("Bottom Section", R.drawable.ic_settings, new Intent(this, Settings.class)));

//        enableToolbarElevation();
    }

    // true：disable swipeRefreshLayout scroll up false:otherwise
    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
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
}
