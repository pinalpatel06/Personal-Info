package com.knoxpo.personinfo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;

import com.knoxpo.personinfo.fragment.MainFragment;

public class MainActivity extends ToolbarActivity{

    private ActionMode mActionMode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment getContentFragment() {
        return new MainFragment();
    }


}
