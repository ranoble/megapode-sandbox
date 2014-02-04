package com.gravspace.sandbox.calculation;

import java.util.Map;

import scala.concurrent.Future;

import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;
import akka.dispatch.Futures;

import com.gravspace.abstractions.ICalculation;
import com.gravspace.annotations.Calculation;
import com.gravspace.bases.CalculationBase;
import com.gravspace.util.Layers;

@Calculation
public class SessionManager extends CalculationBase implements ICalculation, ISessionManager {

	public SessionManager(Map<Layers, ActorRef> routers,
			ActorRef coordinatingActor, UntypedActorContext actorContext) {
		super(routers, coordinatingActor, actorContext);
		// TODO Auto-generated constructor stub
	}

	public Future<Integer> currentUser(){
		return Futures.successful(1);
	}
	
	
}
