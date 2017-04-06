package com.feicuiedu.treasure_20170327.treasure.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.treasure_20170327.R;

/**
 * Created by gqq on 2017/3/31.
 */

// 宝藏的列表视图
public class TreasureListFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_test,container,false);
        return view;
    }
}
