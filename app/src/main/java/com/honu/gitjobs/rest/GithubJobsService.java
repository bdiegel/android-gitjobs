package com.honu.gitjobs.rest;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * GiHub Jobs API: https://jobs.github.com/api
 */
public interface GithubJobsService {

    /**
     * Search for jobs by term, location, full time vs part time, or any combination of the three. All parameters are optional.
     *
     * GET/positions.json
     *
     *   https://jobs.github.com/positions.json?description=python&location=sf&full_time=true
     *
     * @param description A search term, such as "ruby" or "java". This parameter is aliased to search.
     * @param location    A city name, zip code, or other location search term.
     * @param fulltime   If you want to limit results to full time positions set this parameter to 'true'.
     * @param callback
     */
    @GET("/positions.json")
    void findPositionsByLocation(@Query("description") String description,
                                 @Query("location") String location,
                                 @Query("full_time") boolean fulltime,
                                 Callback<List<Job>> callback);

    /**
     * Search for jobs by term, lat-lng, full time vs part time, or any combination of the three. All parameters are optional.
     *
     * GET/positions.json
     *
     *   https://jobs.github.com/positions.json?lat=37.3229978&long=-122.0321823
     *
     * @param description A search term, such as "ruby" or "java". This parameter is aliased to search.
     * @param lat         A specific latitude. If used, you must also send long and must not send location.
     * @param lng         A specific longitude. If used, you must also send lat and must not send location.
     * @param fulltime   If you want to limit results to full time positions set this parameter to 'true'.
     * @param callback
     */
    @GET("/positions.json")
    void findPositionsByLatLng(@Query("description") String description,
                               @Query("lat") double lat,
                               @Query("long") double lng,
                               @Query("full_time") boolean fulltime,
                               Callback<List<Job>> callback);

    /**
     * Retrieve the JSON representation of a single job posting.
     *
     * GET /positions/ID.json
     *
     *   https://jobs.github.com/positions/14774.json
     *   https://jobs.github.com/positions/14774.json?markdown=true
     *
     * @param jobId     job id
     * @param markdown  Set to 'true' to get the description and how_to_apply fields as Markdown.
     * @param callback
     */
    @GET("/positions/{jobId}.json")
    void findPositionById(@Path("jobId") String jobId,
                          @Query("markdown") boolean markdown,
                          Callback<Job> callback);
}
