package com.hilo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hilo.R;
import com.hilo.navigation.MaterialNavigationDrawer;
import com.hilo.views.Button.ButtonFloat;

/**
 * Created by hilo on 15/11/25.
 * <p/>
 * Drscription:
 */
public class Section2Fragment extends Fragment {

    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section2, container, false);
        Button mButton = (Button) view.findViewById(R.id.button);
        mButton.setText("nextSection");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSection();
            }
        });
        ButtonFloat mButtonFloat = (ButtonFloat) view.findViewById(R.id.buttonFloat);
        return view;
    }

    private void nextSection() {
        Fragment fragment = new TextFragment();
        Bundle data = new Bundle();
        data.putString("Test", "Banana");
        fragment.setArguments(data);
        ((MaterialNavigationDrawer) this.getActivity()).setFragmentChild(fragment, "Text Page 2");
    }


}
