package com.app.hackernews.service;

import com.app.hackernews.HackerNewsSpringApplication;
import com.app.hackernews.Helper.TestArtifacts;
import com.app.hackernews.model.HackerNewsComment;
import com.app.hackernews.model.HackerNewsObject;
import com.app.hackernews.model.HackerNewsStory;
import com.app.hackernews.repository.HackerNewsRepository;
import com.app.hackernews.util.CommonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

/**
 * @author Pritesh Soni
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HackerNewsServiceTest {

	@InjectMocks
	HackerNewsService hackerNewsService;

	@Mock
	HackerNewsRepository hackerNewsRepo;

	@Mock
  CommonUtil commonUtil;
	
	@Mock
  HackerNewsSpringApplication newsApp;
	
	@Before
	public void setRepo() {
		hackerNewsService.setHackerNewsRepository(hackerNewsRepo);
		hackerNewsService.setCommonUtil(commonUtil);
	}
	
	@Test
	public void testGetTopStoriesError() throws IOException, URISyntaxException{
    int[] ids = TestArtifacts.getTopStories();
    HackerNewsObject hackerNewsObject = TestArtifacts.getHackerNewsEntity();
    List<HackerNewsObject> response = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      response.add(hackerNewsObject);
    }
    List<HackerNewsStory> hackerNewsStory = commonUtil.hackerNewsObjectsToHackerNewsStories(response);
    doReturn(ids).when(hackerNewsRepo).getTopStoriesIds();
    doReturn(hackerNewsObject).when(hackerNewsRepo).fetchHackerNewsStory(anyInt());
    List<HackerNewsStory> actualResponse = hackerNewsService.getTopStories();
    for (int j = 0; j < actualResponse.size(); j++) {
      assertTrue(testEqualityForHackerNewsResponseObject(actualResponse.get(j), hackerNewsStory.get(j)));
    }
  }

  @Test
  public void testGetHackerNewsTopComments() throws IOException, URISyntaxException {
    int storyID = 121003;
    List<HackerNewsComment> response = TestArtifacts.getTopComments();
    hackerNewsService.setHackerNewsRepository(new HackerNewsRepository());
    hackerNewsService.setCommonUtil(new CommonUtil());
    List<HackerNewsComment> actualResponse = hackerNewsService.getTopCommentsForStory(storyID);
    for (int i = 0; i < actualResponse.size(); i++) {
      assertTrue(testEqualityForHackerNewsComment(actualResponse.get(i), response.get(i)));
    }
  }

  private boolean testEqualityForHackerNewsResponseObject(HackerNewsStory actual, HackerNewsStory response) {
    return actual.getScore() == response.getScore() && actual.getSubmissionDate().equals(response.getSubmissionDate()) && actual.getTitle()
        .equals(response.getTitle()) && actual.getUrl().equals(response.getUrl()) && actual.getUser().equals(response.getUser());
  }

  private boolean testEqualityForHackerNewsComment(HackerNewsComment actual, HackerNewsComment response) {
    return actual.getComment().equals(response.getComment()) && actual.getUserAge() == response.getUserAge() && actual.getUserName()
        .equals(response.getUserName());
  }

}
