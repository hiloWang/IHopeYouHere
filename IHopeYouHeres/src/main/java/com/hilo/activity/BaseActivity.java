package com.hilo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.hilo.R;
import com.hilo.fragment.FragmentButton;
import com.hilo.fragment.Section1Fragment;
import com.hilo.fragment.Section2Fragment;
import com.hilo.listeners.MaterialSectionListener;
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
        this.addSection(newSection("Section 3", R.drawable.ic_mic_white_24dp, new FragmentButton()).setSectionColor(Color.parseColor("#9c27b0")));
        this.addSection(newSection("Section", R.drawable.ic_hotel_grey600_24dp, new FragmentButton()).setSectionColor(Color.parseColor("#03a9f4")));

        // create bottom section
        this.addBottomSection(newSection("Bottom Section", R.drawable.ic_settings, new Intent(this, Settings.class)));

//        enableToolbarElevation();
    }

    // true：disable swipeRefreshLayout scroll up false:otherwise
    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
    }

}
