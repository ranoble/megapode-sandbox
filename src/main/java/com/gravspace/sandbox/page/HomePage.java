package com.gravspace.sandbox.page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

import scala.concurrent.Future;
import scala.concurrent.Promise;

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
		if (session != null){
			Future<Object> res = session.get("user");
			System.out.println(session);
			set("user", res);
		} else {
			System.out.println("Session not loaded");
		}
		
		ISessionManager sessions = Calculations.get(ISessionManager.class, SessionManager.class, this);
		set("userId", sessions.currentUser());
	}

	@Override
	public void process() {
//		if (User)
		if (user == null){
			user = new User();
			user.setLastname("asdasdsa");
			session.set("user", user);
			getLogger().info("User is null");
		} else {
			getLogger().info(String.format("User is [%s]", user.toString()));
		}
		getLogger().info("=====================================");
		
		getLogger().info("=====================================");
		//build could return a callable
		System.out.println(requestCookies.toString());
		getLogger().info("=====================================");
		if (getForm() != null){
			for (NameValuePair element: getForm()){
				getLogger().info(element.toString());
			}
		}
		
		getLogger().info("=====================================");
		
		IWidget profileWidget = Widgets.get(UserProfileWidget.class, this);
		set("profileWidget", profileWidget.build(userId, 1));
		
		IWidget inputWidget = Widgets.get(CommentInputWidget.class, this);
		set("inputWidget", inputWidget.build(userId));
		
		IWidget commentsWidget = Widgets.get(UserCommentsWidget.class, this);
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
