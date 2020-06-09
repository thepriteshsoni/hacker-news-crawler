package com.app.hackernews.Helper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.app.hackernews.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.app.hackernews.model.HackerNewsComment;
import com.app.hackernews.model.HackerNewsObject;
import com.app.hackernews.model.HackerNewsStory;
import com.app.hackernews.model.HackerNewsUser;

/**
 * @author Pritesh Soni
 *
 */
public class TestArtifacts {

  public static HackerNewsObject getHackerNewsEntity() throws URISyntaxException, IOException {
    ClassLoader loader = ClassLoader.getSystemClassLoader();
    String json = Files.lines(Paths.get(Objects.requireNonNull(loader.getResource("hackerNewsObject.json")).toURI())).parallel()
        .collect(Collectors.joining());
    return new CommonUtil().parseJsonStringToHackerNewsObject(json);
  }

  public static List<HackerNewsStory> getOldStories() throws IOException, URISyntaxException {
    ClassLoader loader = ClassLoader.getSystemClassLoader();
    String carInfoJson = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(loader.getResource("hackerNewsOldStories.json")).toURI())));
    Gson gson = new Gson();
    return gson.fromJson(carInfoJson, new TypeToken<List<HackerNewsStory>>() {
    }.getType());
  }

  public static List<HackerNewsComment> getComments() throws IOException, URISyntaxException {
    ClassLoader loader = ClassLoader.getSystemClassLoader();
    String carInfoJson = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(loader.getResource("hackerNewsComments.json")).toURI())));
    Gson gson = new Gson();
    return gson.fromJson(carInfoJson, new TypeToken<List<HackerNewsComment>>() {
    }.getType());
  }

  public static HackerNewsUser getUser() throws IOException, URISyntaxException {
    ClassLoader loader = ClassLoader.getSystemClassLoader();
    String carInfoJson = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(loader.getResource("hackerNewsUser.json")).toURI())));
    Gson gson = new Gson();
    return gson.fromJson(carInfoJson, HackerNewsUser.class);
  }

  public static List<HackerNewsComment> getTopComments() throws IOException, URISyntaxException {
    ClassLoader loader = ClassLoader.getSystemClassLoader();
    String carInfoJson = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(loader.getResource("hackerNewsTopComments.json")).toURI())));
    Gson gson = new Gson();
    return gson.fromJson(carInfoJson, new TypeToken<List<HackerNewsComment>>() {
    }.getType());
  }

  public static int[] getTopStories() throws IOException, URISyntaxException {
    ClassLoader loader = ClassLoader.getSystemClassLoader();
    String carInfoJson = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(loader.getResource("hackerNewsTopStories.json")).toURI())));
    return new ObjectMapper().readValue(carInfoJson, int[].class);
  }
}
