package com.stxx.louvre.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 所有订单
 * Created by linan   on 2018/5/3.
 */

public class OrderTabWithVpAdapter extends FragmentPagerAdapter {
    private String[] title = { "全部","待支付", "待收货", "已完成", "评价"};
    private List<Fragment> views;

    public OrderTabWithVpAdapter(FragmentManager fm, List<Fragment> views) {
        super(fm);
        this.views = views;
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
