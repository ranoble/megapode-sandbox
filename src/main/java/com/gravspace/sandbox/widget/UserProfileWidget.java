package com.gravspace.sandbox.widget;

import java.util.HashMap;
import java.util.Map;

import scala.concurrent.Future;
import scala.concurrent.Promise;

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
	private Integer userId;
	
	public UserProfileWidget(Map<Layers, ActorRef> routers,
			ActorRef coordinatingActor, UntypedActorContext actorContext) {
		super(routers, coordinatingActor, actorContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialise(Object... args) {
		userId = (Integer)args[0];
		
	}

	@Override
	public void collect() {
		IUserProfileData profile = DataAccessors.get(IUserProfileData.class, UserProfileData.class, this);
		//Promise<Object> wait = prepareSet();
		set("user", profile.getUser(userId));
	}

	@Override
	public void process() {
//		getLogger().info(user.toString());
	}

	@Override
	public Future<String> render() throws Exception {
		IRenderer renderer = Renderers.getDefault(this);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", user);
		return renderer.render("templates.widgets.profile.html", context);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
