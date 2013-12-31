package com.gravspace.sandbox.widget;

import java.util.HashMap;
import java.util.Map;

import scala.concurrent.Future;

import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;

import com.gravspace.abstractions.IComponent;
import com.gravspace.abstractions.IRenderer;
import com.gravspace.annotations.Widget;
import com.gravspace.bases.ComponentBase;
import com.gravspace.proxy.DataAccessors;
import com.gravspace.proxy.Renderers;
import com.gravspace.sandbox.beans.User;
import com.gravspace.sandbox.data.IUserProfileData;
import com.gravspace.sandbox.data.UserProfileData;
import com.gravspace.util.Layers;

@Widget
public class UserProfileWidget extends ComponentBase implements IComponent   {
	
	User user;
	
	public UserProfileWidget(Map<Layers, ActorRef> routers,
			ActorRef coordinatingActor, UntypedActorContext actorContext) {
		super(routers, coordinatingActor, actorContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialise(Object... args) {
		Integer userId = (Integer)args[0];
		IUserProfileData profile = DataAccessors.get(IUserProfileData.class, UserProfileData.class, this);
		set("user", profile.getUser(userId));
	}

	@Override
	public void collect() {
		getLogger().info("Collect");
		
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Future<String> render() throws Exception {
		IRenderer renderer = Renderers.getDefault(this);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", user);
		return renderer.render("templates.widgets.profile.vm", context);
	}

}
