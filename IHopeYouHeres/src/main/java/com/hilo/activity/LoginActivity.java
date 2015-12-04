package com.hilo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.Toast;

import com.hilo.R;
import com.hilo.fragment.FragmentButton;
import com.hilo.fragment.Section1Fragment;
import com.hilo.fragment.Section2Fragment;
import com.hilo.listeners.MaterialSectionListener;
import com.hilo.navigation.MaterialAccount;
import com.hilo.navigation.MaterialNavigationDrawer;
import com.hilo.navigation.MaterialSection;
import com.hilo.others.Fields;
import com.hilo.util.ImageUtils;

import org.xutils.DbManager;

/**
 * 1. BaseActivity 为 Activity 栈管理类, 其实这样管理并不是理想的, 容易引起内存泄露.
 * 2. HttpClient.XX; // 请求网络方式
 * 3. new ImageUtils(imageView, url); // iamgeLoader 加载网络图片方式
 * 4. DialogManager.getInstance().XX // dialog 动画调用方式
 * 5. DbManager db = DataBaseFactory.getInstance; // 数据库管理类
 */
public class LoginActivity extends MaterialNavigationDrawer implements OnLayoutChangeListener {

    private boolean mLogout;
    // 屏幕高度
    private int screenHeight = 0;
    // 软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private static DbManager.DaoConfig daoConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ImageUtils.initImageLoaderOptions();
        mLogout = intent.getBooleanExtra(Fields.I_LOGOUT, false);
        // 获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        // 阀值设置为屏幕高度的1/4
        keyHeight = screenHeight / 4;


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
                Toast.makeText(LoginActivity.this, "Account settings clicked", Toast.LENGTH_SHORT).show();

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
    protected void onDestroy() {
        super.onDestroy();
        ImageUtils.AnimateFirstDisplayListener.displayedImages.clear();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        // 现在认为只要控件将Activity向上推的高度超过了1/4屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
//            bottom_rl.setVisibility(View.INVISIBLE); // 类似于 fab 底部弹出的 之前隐藏的 view
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) { // 软键盘处于关闭状态
//            bottom_rl.setVisibility(View.VISIBLE);

        }
    }

    // 记住密码 slipbutton
   /* OnChangedListener toggleListener = new OnChangedListener() {

        @Override
        public void OnChanged(boolean CheckState) {
            isRememberPassword = !isRememberPassword;
            Configuration.getConfig().isRememberPassword = isRememberPassword;
            Configuration.saveConfig();
        }
    };*/
}
