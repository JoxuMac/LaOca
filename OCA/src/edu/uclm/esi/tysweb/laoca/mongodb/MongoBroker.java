package edu.uclm.esi.tysweb.laoca.mongodb;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.bson.BsonDocument;
import org.bson.BsonString;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.dominio.Usuario;

public class MongoBroker {
	
	private ConcurrentLinkedQueue<MongoClient> usadas, libres;
	private MongoClient conexionPrivilegiada;
	
	public MongoBroker() {
		MongoClient mongo = new MongoClient("localhost", 27017);
		conexionPrivilegiada = mongo;
		
		this.usadas=new ConcurrentLinkedQueue<>();
		this.libres=new ConcurrentLinkedQueue<>();
	}
	
	private static class MongoBrokerHolder {
		static MongoBroker singleton=new MongoBroker();
	}
	
	public static MongoBroker get() {
		return MongoBrokerHolder.singleton;
	}

	public MongoDatabase getDatabase(String databaseName) {
		return conexionPrivilegiada.getDatabase(databaseName);
	}

	public void close() {
		this.conexionPrivilegiada.close();
	}
/*
	public MongoClient getDatabase(String databaseName, String email, String pwd) throws Exception {
		MongoCredential credenciales=MongoCredential.createCredential(email, databaseName, pwd.toCharArray());
		ServerAddress address=new ServerAddress("localhost");
		List<MongoCredential> lista=Arrays.asList(credenciales);
		return new MongoClient(address, lista);
	}*/
	
	public MongoClient getConexionPrivilegiada() {
		return this.conexionPrivilegiada;
	}
	
	public Usuario loginUsuario(String email, String pwd) {
		BsonDocument criterio=new BsonDocument();
		criterio.append("email", new BsonString(email));
		MongoCollection<BsonDocument> usuarios= MongoBroker.get().getDatabase("OCA").getCollection("usuarios", BsonDocument.class);
		FindIterable<BsonDocument> resultado = usuarios.find(criterio);
		Usuario usuario=null;
		if (resultado.first()!=null) {
			usuario=new Usuario();
			usuario.setNombre(email);
		}
		return usuario;
	}
	
	public void CREARPRUEBA() {
		
		MongoBroker broker=MongoBroker.get();
		MongoDatabase db = broker.conexionPrivilegiada.getDatabase("OCA");
		
		if (db.getCollection("usuarios")==null)
			db.createCollection("usuarios");
		
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
				
		for (int i=1; i<=100; i++) {
			BsonDocument pepe=new BsonDocument();
			pepe.put("email", new BsonString("pepe" + i + "@pepe.com"));
			pepe.put("pwd", new BsonString("pepe"));
			usuarios.insertOne(pepe);
		}
		
		BsonDocument criterio=new BsonDocument();
		criterio.append("email", new BsonString("pepe100@pepe.com"));
		FindIterable<BsonDocument> busqueda = usuarios.find(criterio);
		BsonDocument elementoBuscado = busqueda.first();
		System.out.println(elementoBuscado.getString("email"));
		System.out.println(elementoBuscado.getString("pwd"));
		
		broker.conexionPrivilegiada.close();
	}
}







