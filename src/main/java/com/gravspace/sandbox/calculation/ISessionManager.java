package com.gravspace.sandbox.calculation;

import scala.concurrent.Future;

public interface ISessionManager {
	public Future<Integer> currentUser();
}