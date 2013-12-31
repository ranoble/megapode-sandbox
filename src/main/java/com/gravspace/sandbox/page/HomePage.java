package com.gravspace.sandbox.page;

import java.util.HashMap;
import java.util.Map;

import scala.concurrent.Future;

import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;
import akka.dispatch.Futures;

import com.gravspace.abstractions.IPage;
import com.gravspace.abstractions.IRenderer;
import com.gravspace.abstractions.IWidget;
import com.gravspace.annotations.Page;
import com.gravspace.bases.PageBase;
import com.gravspace.proxy.Calculations;
import com.gravspace.proxy.Renderers;
import com.gravspace.proxy.Widgets;
import com.gravspace.sandbox.calculation.ISessionManager;
import com.gravspace.sandbox.calculation.SessionManager;
import com.gravspace.sandbox.widget.UserCommentsWidget;
import com.gravspace.sandbox.widget.UserProfileWidget;
import com.gravspace.util.Layers;

@Page(path="/")
public class HomePage extends PageBase implements IPage {
	
	String profileWidget;
	String commentsWidget;
	Integer userId;

	public HomePage(Map<Layers, ActorRef> routers, ActorRef coordinatingActor,
			UntypedActorContext actorContext) {
		super(routers, coordinatingActor, actorContext);
		
	}

	@Override
	public void collect() {
		ISessionManager sessions = Calculations.get(ISessionManager.class, SessionManager.class, this);
		set("userId", sessions.currentUser());
	}

	@Override
	public void process() {
		//IWidget profileWidget = Widgets.get(UserProfileWidget.class, this);
		getLogger().info("User Id = >"+userId);
		//set("profileWidget", profileWidget.build(userId));
		
//		IWidget commentsWidget = Widgets.get(UserCommentsWidget.class, this);
//		set("commentsWidget", commentsWidget.build(userId));
	}

	@Override
	public Future<String> render() throws Exception {
		
//		IRenderer renderer = Renderers.getDefault(this);
//		Map<String, Object> context = new HashMap<String, Object>();
//		context.put("profile", profileWidget);
//		context.put("comments", "comments");
//		return renderer.render("templates.home.vm", context);
		return Futures.successful("adssadas");
	}

	public String getProfileWidget() {
		return profileWidget;
	}

	public void setProfileWidget(String profileWidget) {
		this.profileWidget = profileWidget;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCommentsWidget() {
		return commentsWidget;
	}

	public void setCommentsWidget(String commentsWidget) {
		this.commentsWidget = commentsWidget;
	}

}
