package com.app.hackernews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author Pritesh Soni
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HackerNewsUser implements Serializable {
	
	private String id;
	private int delay;
	private Long created;
	private int karma;
	private String about;
	private int[] submitted;

  public HackerNewsUser(String id, int delay, Long created, int karma, String about, int[] submitted) {
    this.id = id;
    this.delay = delay;
    this.created = created;
    this.karma = karma;
    this.about = about;
    this.submitted = submitted;
  }

  public HackerNewsUser() {
  }

  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public int getKarma() {
		return karma;
	}

	public void setKarma(int karma) {
		this.karma = karma;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int[] getSubmitted() {
		return submitted;
	}

	public void setSubmitted(int[] submitted) {
		this.submitted = submitted;
	}

}
