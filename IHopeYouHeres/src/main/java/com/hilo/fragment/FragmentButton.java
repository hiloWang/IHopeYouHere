package com.hilo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hilo.data.T_TEST;
import com.hilo.others.DataBaseFactory;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

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
            /*Intent intent = new Intent(mContext, ActivityDialog.class);
            startActivity(intent);*/
            try {
            DbManager db = DataBaseFactory.getInstance();
            List<T_TEST> arrs = db.selector(T_TEST.class).findAll();
                for(T_TEST findAll : arrs) {
                    System.out.println(findAll);
                }
                long l = db.selector(T_TEST.class).count();
                System.out.println(l);
                db.deleteById(T_TEST.class, 6);
                T_TEST test1 = db.selector(T_TEST.class).where("id", "!=", 1).findFirst();
                List<T_TEST> arrs2 = db.selector(T_TEST.class).where("id", "!=", 1).findAll();
                System.out.println(test1 + "::::" + arrs2.toString());

            T_TEST t2 = new T_TEST("test t4", "test@126.com", 44);
                db.saveOrUpdate(t2);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    };
}
