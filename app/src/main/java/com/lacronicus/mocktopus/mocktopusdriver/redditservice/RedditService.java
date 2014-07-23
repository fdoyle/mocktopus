package com.lacronicus.mocktopus.mocktopusdriver.redditservice;

import com.lacronicus.mocktopus.mocktopusdriver.redditservice.model.SubredditResponse;

import rx.Observable;

/**
 * Created by fdoyle on 7/23/14.
 */
public interface RedditService {
    public Observable<SubredditResponse> getSubreddit();
}
