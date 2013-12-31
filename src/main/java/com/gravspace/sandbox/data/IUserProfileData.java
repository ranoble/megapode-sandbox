package com.gravspace.sandbox.data;

import java.util.List;

import scala.concurrent.Future;

import com.gravspace.sandbox.beans.Comment;
import com.gravspace.sandbox.beans.User;

public interface IUserProfileData {

	public abstract Future<User> getUser(Integer id);

	Future<List<Comment>> comments(Integer id);

}