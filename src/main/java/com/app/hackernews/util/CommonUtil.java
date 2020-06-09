package com.app.hackernews.util;

import java.time.Instant;
import java.util.*;

import com.app.hackernews.model.HackerNewsComment;
import com.app.hackernews.model.HackerNewsObject;
import com.app.hackernews.model.HackerNewsStory;
import com.app.hackernews.model.HackerNewsUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * @author Pritesh Soni
 *
 */
@Component
public class CommonUtil {

  ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * This method sorts two maps by their values
	 * @param unsortedMap
	 * @return Sorted map by value
	 */
  public Map<Integer, Integer> returnMapSortedByValues(Map<Integer, Integer> unsortedMap) {
    List<Map.Entry<Integer, Integer>> list = new LinkedList<>(unsortedMap.entrySet());
    list.sort(Map.Entry.comparingByValue());
    Collections.reverse(list);
    Map<Integer, Integer> sortedMap = new LinkedHashMap<>();
    for (Map.Entry<Integer, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap;
  }

  /**
   * This method returns the difference in years between the provided time and current time.
   *
   * @param givenTime given time
   * @return difference in years
   */
  public int getDifferenceInYearsFromCurrentTime(long givenTime) {
    long currentTime = Instant.now().getEpochSecond();
    return getDifferenceInYears(givenTime, currentTime);
  }
	
	/**
	 * This method returns the difference in years between two dates
	 * @param Date1
	 * @param Date2
	 * @return difference (in years) between the provided dates
   */
  public int getDifferenceInYears(long Date1, long Date2) {
    Date date = new Date();
    date.setTime(Date1 * 1000);
    Date currentDate = new Date();
    currentDate.setTime((long) Date2 * 1000);
    return (int) (((currentDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24)) / 365);
  }

  /**
   * This method converts JSON string to HackerNewsObject
   * @param jsonString string
   * @return HackerNewsObject object
   * @throws JsonMappingException
   * @throws JsonProcessingException
   */
  public HackerNewsObject parseJsonStringToHackerNewsObject(String jsonString) throws JsonMappingException, JsonProcessingException {
    return objectMapper.readValue(jsonString, HackerNewsObject.class);
  }

  /**
   * This method converts a list of HackerNewsObject elements to a list of HackerNewsStory elements
   *
   * @param topTenStories - List of HackerNewsObject elements with all the fields
   * @return List of HackerNewsStory elements
   */
  public List<HackerNewsStory> hackerNewsObjectsToHackerNewsStories(List<HackerNewsObject> topTenStories) {
    List<HackerNewsStory> hackerNewsStories = new ArrayList<>();
    for (HackerNewsObject hackerNewsObject : topTenStories) {
      HackerNewsStory hackerNewsStory =
          new HackerNewsStory(hackerNewsObject.getTitle(), hackerNewsObject.getUrl(), hackerNewsObject.getTime(),
              hackerNewsObject.getScore(), hackerNewsObject.getBy());
      hackerNewsStories.add(hackerNewsStory);
    }
    return hackerNewsStories;
  }

}
