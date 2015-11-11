package com.bdiegel.ocl.gitjobs.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bdiegel.ocl.gitjobs.R;
import com.bdiegel.ocl.gitjobs.rest.ApiError;
import com.bdiegel.ocl.gitjobs.rest.GithubJobsClient;
import com.bdiegel.ocl.gitjobs.rest.Job;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements GithubJobsClient.JobsListener {

    static final String TAG = MainActivity.class.getSimpleName();

    private GithubJobsClient mGithubJobsClient;

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

        mGithubJobsClient = GithubJobsClient.getInstance();

        mAdapter = new JobRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        ArrayList<Job> jobs = new ArrayList<Job>();
        mAdapter.setData(jobs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

        addListeners();
        loadJobs();
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

        mGithubJobsClient.findPositionsByLocation(searchTerm, searchLocation, this);
    }

    public void openDetailActivity(Job job) {
        //Toast.makeText(this, "Selected: " + record.getPlace().getName(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, JobDetailActivity.class);
//        intent.putExtra(JOB, job);
//        startActivity(intent);
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
    public void success(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty())
            return;

        mAdapter.setData(jobs);
    }

    @Override
    public void error(ApiError error) {
        Log.e(TAG, "API error: " + error.toString());
        Toast.makeText(this, "Jobs API error", Toast.LENGTH_SHORT).show();
    }
}
