package me.pedrazas.dropangular;

import com.codahale.metrics.health.HealthCheck;

public class PingHealthCheck extends HealthCheck {
	
	
	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

}
