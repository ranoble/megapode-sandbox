package com.gravspace.sandbox.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scala.concurrent.Future;
import scala.concurrent.Promise;

import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;

import com.gravspace.abstractions.IComponent;
import com.gravspace.abstractions.IRenderer;
import com.gravspace.abstractions.IWidget;
import com.gravspace.annotations.Widget;
import com.gravspace.bases.ComponentBase;
import com.gravspace.proxy.DataAccessors;
import com.gravspace.proxy.Renderers;
import com.gravspace.proxy.Widgets;
import com.gravspace.sandbox.beans.Comment;
import com.gravspace.sandbox.data.IUserProfileData;
import com.gravspace.sandbox.data.UserProfileData;
import com.gravspace.util.Layers;

@Widget
public class UserCommentsWidget extends ComponentBase implements IComponent   {

	List<Comment> comments;
	List<String> commentWidgets;
	
	public UserCommentsWidget(Map<Layers, ActorRef> routers,
			ActorRef coordinatingActor, UntypedActorContext actorContext) {
		super(routers, coordinatingActor, actorContext);
	}

	@Override
	public void initialise(Object... args) {
		Integer userId = (Integer)args[0];
		IUserProfileData profile = DataAccessors.get(IUserProfileData.class, UserProfileData.class, this);
		Promise<Object> wait = prepareSet();
		set("comments", profile.comments(userId), wait);
	}

	@Override
	public void collect() {
		IWidget commentWidget = Widgets.get(CommentWidget.class, this);
		for (Comment comment: comments){
			Promise<Object> wait = prepareSet();
			add("commentWidgets", commentWidget.build(comment), wait);
		}
		
	}

	@Override
	public void process() {
	}

	@Override
	public Future<String> render() throws Exception {
		IRenderer renderer = Renderers.getDefault(this);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("comments", commentWidgets);
		
		return renderer.render("templates.widgets.comments.vm", context);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<String> getCommentWidgets() {
		return commentWidgets;
	}

	public void setCommentWidgets(List<String> commentWidgets) {
		this.commentWidgets = commentWidgets;
	}


}
