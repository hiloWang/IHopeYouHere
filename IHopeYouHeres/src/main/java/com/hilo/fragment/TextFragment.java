package com.hilo.fragment;

import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.hilo.R;
import com.hilo.util.UIUtils;
import com.hilo.views.Button.CircularProgressButton;
import com.hilo.views.ProgressBar.ProgressBarIndeterminateDeterminate;


/**
 * Created by hilo on 15/11/25.
 * <p/>
 * Drscription:
 */
public class TextFragment extends Fragment {

    private ProgressBarIndeterminateDeterminate mProgressBarIndeterminateDeterminate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        Bundle data = this.getArguments();
//        ((MaterialNavigationDrawer) this.getActivity()).getToolbar().setTitle(data.getString("Test"));
        mProgressBarIndeterminateDeterminate = (ProgressBarIndeterminateDeterminate) view.findViewById(R.id.progressBarIndeterminateDeterminate);
        progresstimer.start();

        view.findViewById(R.id.checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.bProgressDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.getProgressDialog(getActivity());
            }
        });

        final CircularProgressButton circularButton1 = (CircularProgressButton) view.findViewById(R.id.circularButton1);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    simulateSuccessProgress(circularButton1);
                } else {
                    circularButton1.setProgress(0);
                }
            }
        });

        final CircularProgressButton circularButton2 = (CircularProgressButton) view.findViewById(R.id.circularButton2);
        circularButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton2.getProgress() == 0) {
                    simulateErrorProgress(circularButton2);
                } else {
                    circularButton2.setProgress(0);
                }
            }
        });

        return view;
    }

    private Thread progresstimer = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(4000);
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(100);
                    handler.sendMessage(new Message());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private Handler handler = new Handler(new Handler.Callback() {
        int progress = 0;

        @Override
        public boolean handleMessage(Message msg) {
            mProgressBarIndeterminateDeterminate.setProgress(progress++);
            return false;
        }
    });

    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }


}
