package com.gravspace.sandbox.beans;

import java.util.Date;

public class Comment {
	Integer id;
	Integer userId;
	String comment;
	Date created;
	
	public Integer getId() {
		return id;
	}
	public void setCommentId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
}
