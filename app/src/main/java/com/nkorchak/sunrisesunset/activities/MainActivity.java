package com.nkorchak.sunrisesunset.activities;

import android.os.Bundle;

import com.nkorchak.sunrisesunset.R;
import com.nkorchak.sunrisesunset.Utils;
import com.nkorchak.sunrisesunset.fragments.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Utils.isConnectionAvailable(this)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_content, MainFragment.newInstance(), "MainFragment")
                    .commit();
        }
    }
}
