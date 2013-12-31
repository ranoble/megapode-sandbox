package com.gravspace.sandbox.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import scala.concurrent.Future;

import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;
import akka.dispatch.Futures;

import com.gravspace.abstractions.IPersistanceAccessor;
import com.gravspace.annotations.PersistanceAccessor;
import com.gravspace.bases.PersistanceBase;
import com.gravspace.sandbox.beans.Comment;
import com.gravspace.sandbox.beans.User;
import com.gravspace.util.Layers;

@PersistanceAccessor
public class UserProfileData extends PersistanceBase implements
		IPersistanceAccessor, IUserProfileData {

	public UserProfileData(Map<Layers, ActorRef> routers,
			ActorRef coordinatingActor, UntypedActorContext actorContext,
			Connection connection) {
		super(routers, coordinatingActor, actorContext, connection);
	}
	
	/* (non-Javadoc)
	 * @see com.gravspace.sandbox.data.IUserProfileData#getUser(java.lang.Integer)
	 */
	@Override
	public Future<User> getUser(Integer id){
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<User>> handler = new BeanListHandler(User.class);
		try {
			List<User> results = run.query(this.connection, 
					"select * from profile where id = "+id, handler);
			return Futures.successful(results.get(0));
		} catch (SQLException e) {
			return Futures.failed(e);
		}
	}
	
	@Override
	public Future<List<Comment>> comments(Integer id){
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<Comment>> handler = new BeanListHandler(Comment.class);
		try {
			List<Comment> results = run.query(this.connection, 
					"SELECT comment.* FROM connection " +
					"join comment ON comment.\"userId\" = connection.following " +
					"WHERE connection.\"userId\" = "+id+" " +
							"ORDER BY comment.created desc", handler);
			return Futures.successful(results);
		} catch (SQLException e) {
			return Futures.failed(e);
		}
	}

}
