package com.app.hackernews.repository;

import com.app.hackernews.model.HackerNewsObject;
import com.app.hackernews.model.HackerNewsUser;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

/**
 * @author Pritesh Soni
 *
 */
@Repository
public class HackerNewsRepository {

  private static final String HACKER_NEWS_FETCH_STORY_BY_ID_URL = "https://hacker-news.firebaseio.com/v0/item/";
  private static final String HACKER_NEWS_JSON_URL_SUFFIX = ".json?print=pretty";
  private static final String HACKER_NEWS_TOP_STORIES_URL = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";
  private static final String HACKER_NEWS_USER_INFO_URL = "https://hacker-news.firebaseio.com/v0/user/";

  /**
   * This method fetches the Hacker News element using ID by making REST call to Hacker News public API
   *
   * @param storyId Id of Hacker News story/ comment to be fetched
   * @return HackerNewsObject
   */
  public HackerNewsObject fetchHackerNewsStory(int storyId) {
    String uri = HACKER_NEWS_FETCH_STORY_BY_ID_URL + storyId + HACKER_NEWS_JSON_URL_SUFFIX;
    return new RestTemplate().getForObject(uri, HackerNewsObject.class);
  }

  /**
   * This method fetches the id of all the top stories via REST call to Hacker News public API
   *
   * @return array of id of top stories
   */
  public int[] getTopStoriesIds() {
    return new RestTemplate().getForObject(HACKER_NEWS_TOP_STORIES_URL, int[].class);
  }

  /**
   * This method returns the user object containing user details after making REST call to Hacker News public API
   *
   * @param userHandle the user handle
   * @return HackerNewsUser object
   */
  public HackerNewsUser getHackerNewsUserInfo(String userHandle) {
    String uri = HACKER_NEWS_USER_INFO_URL + userHandle + HACKER_NEWS_JSON_URL_SUFFIX;
    return new RestTemplate().getForObject(uri, HackerNewsUser.class);
  }

}

