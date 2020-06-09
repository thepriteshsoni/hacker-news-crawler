package com.app.hackernews.repository;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import com.app.hackernews.Helper.TestArtifacts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.app.hackernews.model.HackerNewsUser;

/**
 * @author Pritesh Soni
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HackerNewsRepositoryTest {
	
	@InjectMocks
	HackerNewsRepository hackerNewsRepo;
	
	@Test
  public void testGetHackerNewsUserInfo() throws IOException, URISyntaxException {
    HackerNewsUser user = TestArtifacts.getUser();
    HackerNewsUser actualUserResponse = hackerNewsRepo.getHackerNewsUserInfo("ab");
    assertArrayEquals(user.getSubmitted(), actualUserResponse.getSubmitted());
  }

}
