package com.mvryan.katalogfilm.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvryan.katalogfilm.R;
import com.mvryan.katalogfilm.utils.adapter.NavDrawerAdapter;
import com.mvryan.katalogfilm.model.NavDrawerItem;
import com.mvryan.katalogfilm.utils.listener.ClickListener;
import com.mvryan.katalogfilm.utils.listener.FragmentDrawerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mvryan on 21/08/18.
 */

public class NavDrawerFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavDrawerAdapter navDrawerAdapter;
    private View viewContainer;
    private static String[] titles = null;
    private FragmentDrawerListener fragmentDrawerListener;

    TextView introduceTxt;

    public NavDrawerFragment() {
    }

    public void setFragmentDrawerListener(FragmentDrawerListener fragmentDrawerListener) {
        this.fragmentDrawerListener = fragmentDrawerListener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> navDrawerItems = new ArrayList<>();

        // preparing navigation drawer items
        for (String title : titles) {
            NavDrawerItem item = new NavDrawerItem();
            item.setTitle(title);
            navDrawerItems.add(item);
        }
        return navDrawerItems;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        recyclerView = layout.findViewById(R.id.drawerList);
        introduceTxt = layout.findViewById(R.id.intorduce);
        introduceTxt.setText(R.string.welcome);

        navDrawerAdapter = new NavDrawerAdapter(getData());
        recyclerView.setAdapter(navDrawerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecycleTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                fragmentDrawerListener.onDrawerItemSelected(view, position);
                drawerLayout.closeDrawer(viewContainer);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout mDrawerLayout, final Toolbar toolbar) {
        viewContainer = getActivity().findViewById(fragmentId);
        drawerLayout = mDrawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

    }

    static class RecycleTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecycleTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
