package com.app.hackernews.controller;

import com.app.hackernews.model.HackerNewsComment;
import com.app.hackernews.model.HackerNewsStory;
import com.app.hackernews.service.HackerNewsService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Pritesh Soni
 *
 */
@RestController
@EnableCaching
@ComponentScan(basePackages = {"com.app.hackernews.*"})
public class HackerNewsController {

  private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
  private static final String STORY_ID_NOT_PROVIDED = "Story ID not provided!";
  private static final String STORY_ID_MANDATORY = "Story ID is mandatory";
  private static final String NO_PAST_STORIES_FOUND = "No past stories found";
  private static final String OK = "OK";

  private HackerNewsService service;
  private Logger logger = Logger.getLogger(HackerNewsController.class);
  List<HackerNewsStory> pastStories;

  @Autowired
  public void setHackerNewsService(HackerNewsService repo) {
    this.service = repo;
  }

  public HackerNewsService getHackerNewsService() {
    return service;
  }

  @CacheEvict(value = "topStories", allEntries = true)
  public void evictAllCacheValues() {
  }

  @GetMapping("/comments")
  public ResponseEntity<Map<String, Object>> getComments(@RequestParam(value = "storyId") Integer storyId)
      throws IOException {
    ResponseEntity<Map<String, Object>> response;
    if (storyId == null) {
      logger.log(Level.INFO, STORY_ID_MANDATORY);
      response = formatResponse(FALSE, STORY_ID_MANDATORY, null);
      return response;
    } else {
      try {
        List<HackerNewsComment> returnComments = service.getTopCommentsForStory(storyId);
        if (returnComments == null) {
          logger.log(Level.INFO, STORY_ID_NOT_PROVIDED);
          response = formatResponse(FALSE, STORY_ID_NOT_PROVIDED, returnComments);
        } else {
          response = formatResponse(TRUE, OK, returnComments);
        }
      } catch (Exception e) {
        logger.log(Level.ERROR, e.getMessage());
        response = formatResponse(FALSE, INTERNAL_SERVER_ERROR, null);
      }
      return response;
    }
  }

  @GetMapping("/old-stories")
  public ResponseEntity<Map<String, Object>> oldStories() {
    ResponseEntity<Map<String, Object>> response;
    if (pastStories == null) {
      logger.log(Level.INFO, NO_PAST_STORIES_FOUND);
      response = formatResponse(FALSE, NO_PAST_STORIES_FOUND, null);
    } else {
      response = formatResponse(TRUE, OK, pastStories);
    }
    return response;
  }

  @GetMapping("/top-stories")
  public ResponseEntity<Map<String, Object>> topStories() {
    ResponseEntity<Map<String, Object>> response;
    try {
      pastStories = service.getTopStories();
      response = formatResponse(TRUE, OK, pastStories);
    } catch (Exception e) {
      logger.log(Level.INFO, e.getMessage());
      response = formatResponse(FALSE, INTERNAL_SERVER_ERROR, null);
    }
    return response;
  }

  private ResponseEntity<Map<String, Object>> formatResponse(Boolean success, String message, Object dataObject) {
    Map<String, Object> obj = new HashMap<>();
    obj.put("success", success);
    obj.put("message", message);
    obj.put("data", dataObject);
    return ResponseEntity.status(HttpStatus.OK).body(obj);
  }

}
