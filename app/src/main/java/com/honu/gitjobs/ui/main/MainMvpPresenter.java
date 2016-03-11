package com.honu.gitjobs.ui.main;

import com.honu.gitjobs.rest.ApiError;
import com.honu.gitjobs.rest.GithubJobsClient;
import com.honu.gitjobs.rest.Job;
import com.honu.gitjobs.ui.base.BasePresenter;

import java.util.List;

public class MainMvpPresenter extends BasePresenter<MainMvpView> implements GithubJobsClient.JobsListener {

    private GithubJobsClient mGithubJobsClient;

    public MainMvpPresenter() {
        mGithubJobsClient = GithubJobsClient.getInstance();;
    }

    public void loadJobs(String searchTerm, String searchLocation) {
        mGithubJobsClient.findPositionsByLocation(searchTerm, searchLocation, this);
    }

    @Override
    public void success(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            getMvpView().showJobsEmpty();
        } else {
            getMvpView().showJobs(jobs);
        }
    }

    @Override
    public void error(ApiError error) {
        getMvpView().showError(error.toString());
    }
}
