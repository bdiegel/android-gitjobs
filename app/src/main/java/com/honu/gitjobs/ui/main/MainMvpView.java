package com.honu.gitjobs.ui.main;

import com.honu.gitjobs.rest.Job;
import com.honu.gitjobs.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showJobs(List<Job> jobs);

    void showJobsEmpty();

    void showError(String error);
}
