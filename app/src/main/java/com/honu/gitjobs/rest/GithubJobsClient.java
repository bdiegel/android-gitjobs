package com.honu.gitjobs.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Client SDK for GitHub Jobs API
 */
public class GithubJobsClient {

    private static GithubJobsClient sInstance;

    private static GithubJobsService sService;

    static final String ENDPOINT = "https://jobs.github.com";

    static final String TAG = GithubJobsClient.class.getSimpleName();


    public interface JobsListener {

        public void success(List<Job> jobs);

        public void error(ApiError error);
    }


    private GithubJobsClient() {
    }

    public static GithubJobsClient getInstance() {

        if (sInstance == null) {
            sInstance = new GithubJobsClient();
        }

        return sInstance;
    }

    public void findPositionsByLocation(@NonNull String description, @NonNull String location, @Nullable final JobsListener listener) {
        findPositionsByLocation(description, location, false, listener);
    }

    public void findPositionsByLocation(@NonNull String description, @NonNull String location, boolean fullTime, @Nullable final JobsListener listener) {

        getService().findPositionsByLocation(description, location, fullTime, new Callback<List<Job>>() {
            @Override
            public void success(List<Job> response, Response httpResponse) {
                Log.d(TAG, "Jobs found: " + response.size());
                if (listener != null) {
                    listener.success(response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "API error: " + error.getMessage());
                if (listener != null) {
                    listener.error(new ApiError(error));
                }
            }
        });
    }

    public void findPositionsByLatLng(@NonNull String description, @NonNull double lattitude, @NonNull double longitude, @Nullable final JobsListener listener) {
        findPositionsByLatLng(description, lattitude, longitude, false, listener);
    }

    public void findPositionsByLatLng(@NonNull String description, @NonNull double lattitude, @NonNull double longitude, boolean fullTime, @Nullable final JobsListener listener) {

        getService().findPositionsByLatLng(description, lattitude, longitude, fullTime, new Callback<List<Job>>() {
            @Override
            public void success(List<Job> response, Response httpResponse) {
                Log.d(TAG, "Jobs found: " + response.size());
                if (listener != null) {
                    listener.success(response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "API error: " + error.getMessage());
                if (listener != null) {
                    listener.error(new ApiError(error));
                }
            }
        });
    }

    public void findPositionById(@NonNull String jobId, @NonNull boolean markdown, @Nullable final JobsListener listener) {

        getService().findPositionById(jobId, markdown, new Callback<Job>() {
            @Override
            public void success(Job job, Response httpResponse) {
                if (listener != null) {
                    List<Job> jobs = new ArrayList<Job>();
                    jobs.add(job);
                    listener.success(jobs);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "API error: " + error.getMessage());
                if (listener != null) {
                    listener.error(new ApiError(error));
                }
            }
        });
    }


    private GithubJobsService getService() {

        if (sService == null) {
            Gson gson = new GsonBuilder()
                .setDateFormat("EEE MMM dd HH:mm:ss 'UTC' yyyy")
                .create();

            RestAdapter.Builder builder = new RestAdapter.Builder()
                  .setEndpoint(ENDPOINT)
                  .setConverter(new GsonConverter(gson))
                  .setClient(new OkClient())
                  //.setLogLevel(RestAdapter.LogLevel.BASIC);
                  .setLogLevel(RestAdapter.LogLevel.FULL);

            sService = builder.build().create(GithubJobsService.class);
        }

        return sService;
    }
}
