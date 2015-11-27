package com.hilo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hilo.dialog.ActivityDialog;

/**
 * Created by hilo on 15/11/10.
 * <p/>
 * Drscription:
 */
public class FragmentButton extends Fragment {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Button button = new Button(this.getActivity());
        button.setOnClickListener(mButtonClickListener);
        button.setText("Click Me");
        button.setGravity(Gravity.CENTER);
        return button;
    }

    View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ActivityDialog.class);
            startActivity(intent);
        }
    };
}
