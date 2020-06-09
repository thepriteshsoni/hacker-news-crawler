package com.app.hackernews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Pritesh Soni
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HackerNewsObject implements Serializable, Comparable<HackerNewsObject> {

  private int id;
  private String by;
	private int descendants;
	private int[] kids;
  private String url;
	private int score;
	private Long time;
	private String title;
	private String type;
	private String text;
	private Boolean deleted;
	private Boolean dead;
	private BigDecimal parent;

  public HackerNewsObject(int id, String by, int descendants, int[] kids, String url, int score, Long time, String title, String type,
      String text, Boolean deleted, Boolean dead, BigDecimal parent) {
    this.id = id;
    this.by = by;
    this.descendants = descendants;
    this.kids = kids;
    this.url = url;
    this.score = score;
    this.time = time;
    this.title = title;
    this.type = type;
    this.text = text;
    this.deleted = deleted;
    this.dead = dead;
    this.parent = parent;
  }

  public HackerNewsObject() {
  }

  public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public int getDescendants() {
		return descendants;
	}

	public void setDescendants(int descendants) {
		this.descendants = descendants;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getKids() {
		return kids;
	}

	public void setKids(int[] kids) {
		this.kids = kids;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getDead() {
		return dead;
	}

	public void setDead(Boolean dead) {
		this.dead = dead;
	}

	public BigDecimal getParent() {
		return parent;
	}

	public void setParent(BigDecimal parent) {
		this.parent = parent;
	}

  @Override
  public int compareTo(HackerNewsObject HackerNewsObject) {
    return Integer.compare(this.getScore(), HackerNewsObject.getScore());
  }
}
