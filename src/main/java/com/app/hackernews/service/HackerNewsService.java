package com.app.hackernews.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.app.hackernews.model.HackerNewsComment;
import com.app.hackernews.model.HackerNewsObject;
import com.app.hackernews.model.HackerNewsStory;
import com.app.hackernews.model.HackerNewsUser;
import com.app.hackernews.repository.HackerNewsRepository;
import com.app.hackernews.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Pritesh Soni
 *
 */
@Service
public class HackerNewsService {

  private HackerNewsRepository hackerNewsRepository;
  private CommonUtil commonUtil;

  @Autowired
  public void setHackerNewsRepository(HackerNewsRepository hackerNewsRepository) {
    this.hackerNewsRepository = hackerNewsRepository;
  }

  @Autowired
  public void setCommonUtil(CommonUtil commonUtil) {
    this.commonUtil = commonUtil;
  }

  public HackerNewsRepository getHackerNewsRepository() {
    return hackerNewsRepository;
  }

  public CommonUtil getCommonUtil() {
    return commonUtil;
  }

  /**
   * This method fetches top stories from Hacker News public API.
   *
   * @return a trimmed list of HackerNewsStory objects
   * @throws IOException
   */
  @Cacheable(value = "topStories")
  public List<HackerNewsStory> getTopStories() throws IOException {
    HackerNewsObject response;
    List<HackerNewsObject> hackerNewsObjects = new ArrayList<>();
    int[] topStoriesIds = hackerNewsRepository.getTopStoriesIds();
    for (int topStoryId : topStoriesIds) {
      response = hackerNewsRepository.fetchHackerNewsStory(topStoryId);
      hackerNewsObjects.add(response);
    }
    Collections.sort(hackerNewsObjects);
    List<HackerNewsObject> topTenStories = hackerNewsObjects.subList(hackerNewsObjects.size() - 10, hackerNewsObjects.size());
    Collections.reverse(topTenStories);
    return commonUtil.hackerNewsObjectsToHackerNewsStories(topTenStories);
  }

  /**
   * This method fetches top 10 comments for a given story ID.
   *
   * @param storyId Id of the story to fetch top comments
   * @return List of Top 10 HackerNewsComment elements for the given story ID
   * @throws IOException
   */
  @Cacheable(value = "topComments", key = "#storyId")
  public List<HackerNewsComment> getTopCommentsForStory(Integer storyId) throws IOException {
    HackerNewsObject hackerNewsParentObject = hackerNewsRepository.fetchHackerNewsStory(storyId);
    if (StringUtils.isNotEmpty(hackerNewsParentObject.getType()) && StringUtils.equals("story", hackerNewsParentObject.getType())) {
      int commentCount;
      Map<Integer, Integer> commentCountMap = new HashMap<>();
      for (int kid : hackerNewsParentObject.getKids()) {
        commentCount = countComments(kid);
        commentCountMap.put(kid, commentCount);
      }
      Map<Integer, Integer> sortedCommentIDCountMap = commonUtil.returnMapSortedByValues(commentCountMap);
      List<Integer> topTenCommentIds = sortedCommentIDCountMap.keySet().stream().limit(10).collect(Collectors.toList());
      return populateHackerNewsCommentList(topTenCommentIds);
    }
    return null;
  }

  /**
   * This method counts the number of child comments for the given parent comment.
   *
   * @param parentCommentID ID of the parent comment
   * @return count of child comments in a parent comment
   */
  public int countComments(int parentCommentID) {
    HackerNewsObject commentParent = hackerNewsRepository.fetchHackerNewsStory(parentCommentID);
    if (commentParent.getKids() == null) {
      return 1;
    } else {
      int count = 0;
      for (int kidId : commentParent.getKids()) {
        count += countComments(kidId);
      }
      return count + 1;
    }
  }

  /**
   * This method returns the final HackerNewsComment object
   *
   * @param topTenCommentIds List of IDs of Top 10 comments on a story
   * @return List of Top 10 HackerNewsComment objects
   */
  public List<HackerNewsComment> populateHackerNewsCommentList(List<Integer> topTenCommentIds) {
    List<HackerNewsComment> topTenHackerNewsComments = new ArrayList<>();
    for (Integer commentId : topTenCommentIds) {
      HackerNewsObject commentObject = hackerNewsRepository.fetchHackerNewsStory(commentId);
      HackerNewsUser user = hackerNewsRepository.getHackerNewsUserInfo(commentObject.getBy());
      HackerNewsComment hackerNewsComment =
          new HackerNewsComment(user.getId(), commonUtil.getDifferenceInYearsFromCurrentTime(user.getCreated()), commentObject.getText());
      topTenHackerNewsComments.add(hackerNewsComment);
    }
    return topTenHackerNewsComments;
  }

}
