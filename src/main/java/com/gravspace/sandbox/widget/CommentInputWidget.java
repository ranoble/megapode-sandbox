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
import com.gravspace.sandbox.beans.User;
import com.gravspace.sandbox.data.IUserProfileData;
import com.gravspace.sandbox.data.UserProfileData;
import com.gravspace.util.Layers;

@Widget
public class CommentInputWidget extends ComponentBase implements IComponent   {

	int commenter;
	
	public CommentInputWidget(Map<Layers, ActorRef> routers,
			ActorRef coordinatingActor, UntypedActorContext actorContext) {
		super(routers, coordinatingActor, actorContext);
	}

	@Override
	public void initialise(Object... args) {
		commenter = (int)args[0];
	}

	@Override
	public void collect() {}

	@Override
	public void process() {}

	@Override
	public Future<String> render() throws Exception {
		IRenderer renderer = Renderers.getDefault(this);
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("commenter", commenter);
		
		return renderer.render("templates.widgets.input.html", context);
	}

}
