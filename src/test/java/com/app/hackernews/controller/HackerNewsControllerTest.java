package com.app.hackernews.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.app.hackernews.Helper.TestArtifacts;
import com.app.hackernews.Helper.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.app.hackernews.model.HackerNewsComment;
import com.app.hackernews.model.HackerNewsStory;
import com.app.hackernews.repository.HackerNewsRepository;
import com.app.hackernews.service.HackerNewsService;

/**
 * @author Pritesh Soni
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HackerNewsControllerTest {

  @InjectMocks
  HackerNewsController hackerNewsController;

  @Mock
  HackerNewsService hackerNewsServiceMock;

  @Mock
  HackerNewsRepository hackerNewsRepoMock;

  @Before
  public void basicConfig() {
    hackerNewsController.setHackerNewsService(hackerNewsServiceMock);
  }


  @Test
  public void testGetCommentsNormal() throws IOException, URISyntaxException {
    int storyID = 1234;
    List<HackerNewsComment> commentsList = TestArtifacts.getComments();
    ResponseEntity<Map<String, Object>> response = TestUtil.formatResponse(Boolean.TRUE, "OK", commentsList);
    when(hackerNewsServiceMock.getTopCommentsForStory(storyID)).thenReturn(commentsList);
    ResponseEntity<Map<String, Object>> actualResponse = hackerNewsController.getComments(storyID);
    assertEquals(response, actualResponse);
  }

  @Test
  public void testGetCommentsErrorForNoStory() throws IOException {
    int storyID = 1234;
    ResponseEntity<Map<String, Object>> responseEntity = TestUtil.formatResponse(false, "Story ID not provided!", null);
    HackerNewsService hackerNewsService = new HackerNewsService();
    HackerNewsRepository hackerNewsRepo = new HackerNewsRepository();
    hackerNewsController.setHackerNewsService(hackerNewsService);
    hackerNewsService.setHackerNewsRepository(hackerNewsRepo);
    ResponseEntity<Map<String, Object>> actualResponse = hackerNewsController.getComments(storyID);
    assertEquals(responseEntity, actualResponse);
  }

  @Test
  public void testGetCommentsNullStoryID() throws IOException, URISyntaxException {
    ResponseEntity<Map<String, Object>> responseEntity = TestUtil.formatResponse(false, "Story ID is mandatory", null);
    HackerNewsService hackerNewsService = new HackerNewsService();
    HackerNewsRepository hackerNewsRepo = new HackerNewsRepository();
    hackerNewsController.setHackerNewsService(hackerNewsService);
    hackerNewsService.setHackerNewsRepository(hackerNewsRepo);
    ResponseEntity<Map<String, Object>> actualResponse = hackerNewsController.getComments(null);
    assertEquals(responseEntity, actualResponse);
  }

  @Test
  public void testGetCommentsException() throws IOException {
    int storyID = 1234;
    ResponseEntity<Map<String, Object>> responseExpected = TestUtil.formatResponse(false, "Internal Server Error", null);
    Mockito.doThrow(IOException.class).when(hackerNewsServiceMock).getTopCommentsForStory(storyID);
    ResponseEntity<Map<String, Object>> actualResponse = hackerNewsController.getComments(storyID);
    assertEquals(responseExpected, actualResponse);
  }

  @Test
  public void testTopStories() throws IOException, URISyntaxException {
    List<HackerNewsStory> topStories = TestArtifacts.getOldStories();
    ResponseEntity<Map<String, Object>> response = TestUtil.formatResponse(true, "OK", topStories);
    when(hackerNewsServiceMock.getTopStories()).thenReturn(topStories);
    ResponseEntity<Map<String, Object>> actualResponse = hackerNewsController.topStories();
    assertEquals(response, actualResponse);
  }

  @Test
  public void testTopStoriesException() throws IOException {
    hackerNewsController.pastStories = null;
    ResponseEntity<Map<String, Object>> responseExpected = TestUtil.formatResponse(false, "Internal Server Error", null);
    when(hackerNewsServiceMock.getTopStories()).thenThrow(IOException.class);
    ResponseEntity<Map<String, Object>> response = hackerNewsController.topStories();
    assertEquals(responseExpected, response);
  }

}
