package com.honu.gitjobs.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.honu.gitjobs.R;
import com.honu.gitjobs.rest.Job;
import com.honu.gitjobs.ui.decorator.DividerItemDecoration;
import com.honu.gitjobs.ui.detail.JobDetailActivity;
import com.honu.gitjobs.ui.detail.JobRecyclerAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainMvpView,
      JobRecyclerAdapter.JobClickListener {

    static final String TAG = MainActivity.class.getSimpleName();

    private MainMvpPresenter mMainMvpPresenter;

    JobRecyclerAdapter mAdapter;

    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Bind(R.id.search_term_edittext) EditText mSearchTermTextEdit;

    @Bind(R.id.search_location_edittext) EditText mSearchLocationTextEdit;

    @Bind(R.id.jobs_recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mAdapter = new JobRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setData(Collections.<Job>emptyList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mMainMvpPresenter = new MainMvpPresenter();
        mMainMvpPresenter.attachView(this);
        loadJobs();

        addListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainMvpPresenter.detachView();
    }

    private void addListeners() {
        mSearchLocationTextEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    loadJobs();
                    return true;
                }
                return false;
            }
        });
    }

    private void dismissSoftKeyboard() {
        mSearchTermTextEdit.clearFocus();
        mSearchLocationTextEdit.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchLocationTextEdit.getWindowToken(), 0);
    }

    @OnClick(R.id.search_button)
    public void loadJobs() {
        dismissSoftKeyboard();
        String searchTerm = mSearchTermTextEdit.getText().toString();
        String searchLocation = "";
        try {
            searchLocation = URLEncoder.encode(mSearchLocationTextEdit.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "URL encode error: " + e.getMessage());
            Toast.makeText(this, "Invalid search location", Toast.LENGTH_SHORT).show();
        }

        mMainMvpPresenter.loadJobs(searchTerm, searchLocation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onJobClick(View view, Job selection) {
        ImageView imageView = (ImageView) view.findViewById(R.id.company_logo);
        ActivityOptionsCompat options = ActivityOptionsCompat.
              makeSceneTransitionAnimation(this, imageView, getResources().getString(R.string.transition_logo));
        Intent intent = JobDetailActivity.newStartIntent(this, selection);
        startActivity(intent, options.toBundle());
    }

    /** MainMvpView **/

    @Override
    public void showJobs(List<Job> jobs) {
        mAdapter.setData(jobs);
    }

    @Override
    public void showJobsEmpty() {
        mAdapter.setData(Collections.<Job>emptyList());
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "No jobs found for search", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String error) {
        Log.e(TAG, "API error: " + error);
        Toast.makeText(this, "Jobs API error", Toast.LENGTH_SHORT).show();
    }
}
