package com.app.hackernews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Pritesh Soni
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HackerNewsComment implements Serializable {

  @JsonProperty("userName")
	private String userName;

  @JsonProperty("userAge")
	private int userAge;

  @JsonProperty("comment")
	private String comment;

  public HackerNewsComment(String userName, int userAge, String comment) {
    this.userName = userName;
    this.userAge = userAge;
    this.comment = comment;
  }

  public HackerNewsComment() {
  }

  @JsonProperty("userName")
	public String getUserName() {
		return userName;
	}

  @JsonProperty("userName")
	public void setUserName(String userName) {
		this.userName = userName;
	}

  @JsonProperty("userAge")
	public int getUserAge() {
		return userAge;
	}

  @JsonProperty("userAge")
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

  @JsonProperty("comment")
	public String getComment() {
		return comment;
	}

  @JsonProperty("comment")
	public void setComment(String comment) {
		this.comment = comment;
	}
}
