package com.nkorchak.sunrisesunset.activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nkorchak.sunrisesunset.R;
import com.nkorchak.sunrisesunset.views.BaseView;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RelativeLayout progressBarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarContainer = (RelativeLayout) findViewById(R.id.progress_container);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.colorPrimary));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
    }

    protected void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        progressBarContainer.setVisibility(View.VISIBLE);
    }

    protected void hideProgressBar() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE
                && progressBarContainer != null && progressBarContainer.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            progressBarContainer.setVisibility(View.GONE);
        }
    }

    protected void createSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.main_content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void showLoading() {
        showProgressBar();
    }

    @Override
    public void hideLoading() {
        hideProgressBar();
    }

    @Override
    public void showSnackBar(String message) {
        createSnackBar(message);
    }
}