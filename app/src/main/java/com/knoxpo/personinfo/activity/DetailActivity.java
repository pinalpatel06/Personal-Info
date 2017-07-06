package com.knoxpo.personinfo.activity;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.knoxpo.personinfo.fragment.DetailFragment;

/**
 * Created by Tejas Sherdiwala on 12/10/2016.
 * &copy; Knoxpo
 */

public class DetailActivity extends ToolbarActivity {

    @Override
    public Fragment getContentFragment() {

        if(getIntent().getData()!=null) {
            Uri data = getIntent().getData();
            return DetailFragment.newInstance(data);
        }else{
            return new DetailFragment();
        }
/*        if(getIntent().getData()!=null) {
            Uri uri = getIntent().getData();
            long currentId = PersonContract.PersonEntry.getIdFromUri(uri);
            return DetailFragment.newInstance(currentId);
        }else{
            return new DetailFragment();
        }*/
    }

}
