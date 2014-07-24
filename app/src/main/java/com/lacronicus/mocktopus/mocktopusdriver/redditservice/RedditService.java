package com.lacronicus.mocktopus.mocktopusdriver.redditservice;

import com.lacronicus.mocktopus.mocktopusdriver.redditservice.model.SubredditResponse;


import retrofit.http.GET;
import rx.Observable;

/**
 * Created by fdoyle on 7/23/14.
 */
public interface RedditService {
    @GET("/r/{subreddit}/")
    public Observable<SubredditResponse> getSubreddit();
}
