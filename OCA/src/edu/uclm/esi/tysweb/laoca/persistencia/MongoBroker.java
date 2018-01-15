/*
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas
*/

package edu.uclm.esi.tysweb.laoca.persistencia;

import com.mongodb.MongoClient;

public class MongoBroker {
	private Pool pool;
	
	private MongoBroker() {
		this.pool=new Pool(13);
	}
	
	private static class MongoBrokeHolder{
		static MongoBroker singelton = new MongoBroker();
	}
	
	public static MongoBroker get() {
		return MongoBrokeHolder.singelton;
	}
	
	public MongoClient getBD() throws Exception {
		return this.pool.getBD();
	}

	public void close(MongoClient bd) {
		this.pool.close(bd);
	}
}