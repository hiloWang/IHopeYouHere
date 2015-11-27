package com.hilo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilo.R;
import com.hilo.adapter.FeedAdapter;
import com.hilo.views.Button.widgets.ScrollDirectionListener;
import com.hilo.views.widgets.MultiSwipeRefreshLayout;

/**
 * Created by hilo on 15/11/10.
 * <p/>
 * Drscription:
 */
public class Section1Fragment extends Fragment implements MultiSwipeRefreshLayout.CanChildScrollUpCallback {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private FeedAdapter mFeedAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_NoActionBar);
//        // clone the inflater using the ContextThemeWrapper
//        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = inflater.inflate(R.layout.fragment_main, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvFeed);
        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        com.hilo.views.Button.FloatingActionButton fab_custom = (com.hilo.views.Button.FloatingActionButton) view.findViewById(R.id.fab_custom);
       /*
       // ListView
       fab_custom.attachToListView(mRecyclerView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrollUp() {

            }
        });*/

        fab_custom.attachToRecyclerView(mRecyclerView);

        setupFeed();
        return view;
    }

    protected void setupFeed() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        mRecyclerView.setLayoutManager(mLayoutManager);

        mFeedAdapter = new FeedAdapter(mContext);
        mRecyclerView.setAdapter(mFeedAdapter);
        mFeedAdapter.updateItems();
    }


    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return true;
    }
}
