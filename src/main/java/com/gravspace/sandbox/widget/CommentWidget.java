package com.gravspace.sandbox.widget;

import java.util.HashMap;
import java.util.Map;

import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;

import com.gravspace.abstractions.IRenderer;
import com.gravspace.annotations.Widget;
import com.gravspace.bases.WidgetBase;
import com.gravspace.proxy.DataAccessors;
import com.gravspace.proxy.Renderers;
import com.gravspace.sandbox.beans.Comment;
import com.gravspace.sandbox.beans.User;
import com.gravspace.sandbox.data.IUserProfileData;
import com.gravspace.sandbox.data.UserProfileData;
import com.gravspace.util.Layers;

@Widget
public class CommentWidget extends WidgetBase   {

	Comment comment;
	User user;
	
	public CommentWidget(Map<Layers, ActorRef> routers,
			ActorRef coordinatingActor, UntypedActorContext actorContext) {
		super(routers, coordinatingActor, actorContext);
	}

	@Override
	public void initialise(Object... args) {
		comment = (Comment)args[0];
		IUserProfileData profile = DataAccessors.get(IUserProfileData.class, UserProfileData.class, this);
//		Promise<Object> wait = prepareSet();
		set("user", profile.getUser(comment.getUserId()));
	}

	@Override
	public void collect() {}

	@Override
	public void process() {}

	@Override
	public Future<String> render() throws Exception {
		IRenderer renderer = Renderers.getDefault(this);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("comment", comment);
		context.put("user", user);
		
		return renderer.render("templates.widgets.comment.html", context);
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
