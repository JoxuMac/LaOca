package edu.uclm.esi.tysweb.laoca.dao;

import java.sql.Connection;

public class Broker {
	private Pool pool;
	
	private Broker() {
		this.pool=new Pool(2);
	}
	
	private static class BrokerHolder {
		static Broker singleton=new Broker();
	}
	
	public static Broker get() {
		return BrokerHolder.singleton;
	}

	public Connection getBD() throws Exception {
		return this.pool.getBD();
	}

	public void close(Connection bd) {
		this.pool.close(bd);
	}
}
