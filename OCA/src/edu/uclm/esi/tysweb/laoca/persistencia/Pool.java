/*
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas
*/

package edu.uclm.esi.tysweb.laoca.persistencia;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class Pool {
	private ConcurrentLinkedQueue<MongoClient> libres;
	private ConcurrentLinkedQueue<MongoClient> usadas;
	
	public Pool(int numeroDeConexiones) {
		try {
			String url="localhost";
			String db = "OCA";
			
			this.libres=new ConcurrentLinkedQueue<>();
			this.usadas=new ConcurrentLinkedQueue<>();
			for (int i=0; i<numeroDeConexiones; i++) {
				MongoClient bd;
		        MongoCredential credenciales = MongoCredential.createCredential("root", db, "root".toCharArray());
		        ServerAddress address = new ServerAddress(url);
		        List<MongoCredential> lista = Arrays.asList(credenciales);
		        bd =  new MongoClient(address, lista);
				this.libres.add(bd);
			}
		} catch (Exception e) {
			System.exit(-1);
		}
	}
	
	public MongoClient getBD() throws Exception {
		if (this.libres.size()==0)
			throw new Exception("No hay conexiones libres");
		MongoClient bd=this.libres.poll();
		this.usadas.offer(bd);
		return bd;
	}

	public void close(MongoClient bd) {
		this.usadas.remove(bd);
		this.libres.offer((MongoClient) bd);
	}
}