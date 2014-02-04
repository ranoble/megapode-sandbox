package com.gravspace.sandbox.page;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;

import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;

import com.gravspace.abstractions.IPage;
import com.gravspace.abstractions.IRenderer;
import com.gravspace.abstractions.Widget;
import com.gravspace.annotations.Page;
import com.gravspace.bases.PageBase;
import com.gravspace.proxy.Calculations;
import com.gravspace.proxy.Renderers;
import com.gravspace.proxy.Widgets;
import com.gravspace.sandbox.beans.User;
import com.gravspace.sandbox.calculation.ISessionManager;
import com.gravspace.sandbox.calculation.SessionManager;
import com.gravspace.sandbox.widget.CommentInputWidget;
import com.gravspace.sandbox.widget.UserCommentsWidget;
import com.gravspace.sandbox.widget.UserProfileWidget;
import com.gravspace.util.Layers;

@Page(path="/")
public class HomePage extends PageBase implements IPage {
	
	String profileWidget;
	String inputWidget;
	String commentsWidget;
	Integer userId;
	User user;

	public HomePage(Map<Layers, ActorRef> routers, ActorRef coordinatingActor,
			UntypedActorContext actorContext) {
		super(routers, coordinatingActor, actorContext);
		
	}

	@Override
	public void collect() {
		super.collect();
	}

	@Override
	public void process() {
		
		Widget profileWidget = Widgets.get(UserProfileWidget.class, this);
		set("profileWidget", profileWidget.build(userId, 1));
		
		Widget inputWidget = Widgets.get(CommentInputWidget.class, this);
		set("inputWidget", inputWidget.build(userId));
		
		Widget commentsWidget = Widgets.get(UserCommentsWidget.class, this);
		set("commentsWidget", commentsWidget.build(userId));
	}

	@Override
	public Future<String> render() throws Exception {
		
		IRenderer renderer = Renderers.getDefault(this);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("profile", profileWidget);
		context.put("comments", commentsWidget);
		context.put("input", inputWidget);
		return renderer.render("templates.home.vm", context);
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

	public String getInputWidget() {
		return inputWidget;
	}

	public void setInputWidget(String inputWidget) {
		this.inputWidget = inputWidget;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
