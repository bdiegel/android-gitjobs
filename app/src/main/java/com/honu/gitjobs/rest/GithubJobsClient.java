package com.honu.gitjobs.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Client SDK for GitHub Jobs API
 */
public class GithubJobsClient {

    private static GithubJobsClient sInstance;

    private static GithubJobsService sService;

    private static  Retrofit sRetrofit;

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
        Call<List<Job>> call = getService().findPositionsByLocation(description, location, fullTime);
        call.enqueue(new Callback<List<Job>>() {

            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                Log.d(TAG, "onResponse: success=" + response.isSuccess());
                if (response.isSuccess() && listener != null) {
                    Log.d(TAG, "Jobs found: " + response.body().size());
                    listener.success(response.body());
                } else {
                    listener.error(ApiError.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
                listener.error(ApiError.parseNetworkError(t));
            }
        });
    }

    public void findPositionsByLatLng(@NonNull String description, @NonNull double lattitude, @NonNull double longitude, @Nullable final JobsListener listener) {
        findPositionsByLatLng(description, lattitude, longitude, false, listener);
    }

    public void findPositionsByLatLng(@NonNull String description, @NonNull double lattitude, @NonNull double longitude, boolean fullTime, @Nullable final JobsListener listener) {
        Call<List<Job>> call = getService().findPositionsByLatLng(description, lattitude, longitude, fullTime);
        call.enqueue(new Callback<List<Job>>() {

            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                Log.d(TAG, "onResponse: success=" + response.isSuccess());
                if (response.isSuccess() && listener != null) {
                    Log.d(TAG, "Jobs found: " + response.body().size());
                    listener.success(response.body());
                } else {
                    listener.error(ApiError.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
                listener.error(ApiError.parseNetworkError(t));
            }
        });
    }

    public void findPositionById(@NonNull String jobId, @NonNull boolean markdown, @Nullable final JobsListener listener) {
        Call<Job> call = getService().findPositionById(jobId, markdown);
        call.enqueue(new Callback<Job>() {

            @Override
            public void onResponse(Call<Job> call, Response<Job> response) {
                Log.d(TAG, "onResponse: success=" + response.isSuccess());
                if (response.isSuccess() && listener != null) {
                    Log.d(TAG, "Position found");
                    List<Job> jobs = new ArrayList<Job>();
                    jobs.add(response.body());
                    listener.success(jobs);
                } else {
                    listener.error(ApiError.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
                listener.error(ApiError.parseNetworkError(t));
            }
        });
    }


    private GithubJobsService getService() {

        if (sService == null) {
            Gson gson = new GsonBuilder()
                .setDateFormat("EEE MMM dd HH:mm:ss 'UTC' yyyy")
                .create();

            Retrofit.Builder builder = new Retrofit.Builder()
                  .baseUrl(ENDPOINT)
                  .addConverterFactory(GsonConverterFactory.create(gson));
                  //.client(new OkClient());
                  //.setLogLevel(RestAdapter.LogLevel.BASIC);
                  //.setLogLevel(Retrofit.LogLevel.FULL);

            sRetrofit = builder.build();
            sService = sRetrofit.create(GithubJobsService.class);
        }

        return sService;
    }

    public Retrofit retrofit() {
        return sRetrofit;
    }
}
