package com.honu.gitjobs.rest;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ApiError {

    private String message;

    private int statusCode;

    private boolean isNetworkError = false;

    public ApiError() {
    }

    public ApiError(boolean isNetworkError) {
        this.isNetworkError = isNetworkError;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isNetworkError() {
        return isNetworkError;
    }

    public static ApiError parseError(Response<?> response) {
        ApiError error;
        Converter<ResponseBody, ApiError> converter =
              GithubJobsClient.getInstance()
                    .retrofit()
                    .responseBodyConverter(ApiError.class, new Annotation[0]);

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }

        return error;
    }

    public static ApiError parseNetworkError(Throwable t) {
        ApiError error = new ApiError(true);
        error.message = t.getMessage();
        return error;
    }
}
