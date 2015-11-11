package com.bdiegel.ocl.gitjobs.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bdiegel.ocl.gitjobs.R;
import com.bdiegel.ocl.gitjobs.rest.Job;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobDetailActivity extends AppCompatActivity {

    Job mJob;

    @Bind(R.id.details_company_name) TextView mCompanyNameTextView;

    @Bind(R.id.details_company_location) TextView mCompanyLocationTextView;

    @Bind(R.id.details_company_url) TextView mCompanyUrlTextView;

    @Bind(R.id.details_company_logo) ImageView mCompanyLogoImageView;

    @Bind(R.id.details_job_title) TextView mJobTitleTextView;

    @Bind(R.id.details_job_type) TextView mJobTypeTextView;

    @Bind(R.id.details_job_description) WebView mJobDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        mJob = intent.getParcelableExtra(MainActivity.JOB_EXTRA);
        loadData();
    }

    private void loadData() {
        mCompanyNameTextView.setText(mJob.getCompanyName());
        mCompanyLocationTextView.setText(mJob.getLocation());
        mCompanyUrlTextView.setText(mJob.getCompanyUrl());
        mJobTitleTextView.setText(mJob.getTitle());
        String jobType = !TextUtils.isEmpty(mJob.getType()) ? mJob.getType() : "";
        mJobTypeTextView.setText(jobType);
        mJobDescriptionView.loadData(mJob.getHtmlDescription(), "text/html", "UTF-8");
        loadCompanyLogo();
    }

    private void loadCompanyLogo() {
        if (TextUtils.isEmpty(mJob.getCompanyLogo()))
            return;

        Picasso.with(mCompanyLogoImageView.getContext())
              .load(mJob.getCompanyLogo())
              .into(mCompanyLogoImageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.details_header)
    public void launchCompanyUrl() {
        if (TextUtils.isEmpty(mJob.getCompanyUrl()))
            return;

        Uri browseUri = Uri.parse(mJob.getCompanyUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, browseUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
