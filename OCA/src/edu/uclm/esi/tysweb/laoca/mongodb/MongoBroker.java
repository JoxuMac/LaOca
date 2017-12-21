package edu.uclm.esi.tysweb.laoca.mongodb;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.bson.BsonDocument;
import org.bson.BsonString;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.dominio.Usuario;

public class MongoBroker {
	
	private ConcurrentLinkedQueue<MongoClient> usadas, libres;
	private MongoClient conexionPrivilegiada;
	private String server = "localhost";
	private int puerto = 27017;
	private String db = "OCA";
			
	public MongoBroker() {
		MongoClient mongo = new MongoClient(server, puerto);
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

	public MongoClient getConexionPrivilegiada() {
		return this.conexionPrivilegiada;
	}
	
	/* LOGIN */
	public Usuario loginUsuario(String email, String pwd) {
		BsonDocument criterio=new BsonDocument();
		criterio.append("email", new BsonString(email));
		MongoCollection<BsonDocument> usuarios= MongoBroker.get().getDatabase(db).getCollection("usuarios", BsonDocument.class);
		FindIterable<BsonDocument> resultado = usuarios.find(criterio);
		Usuario usuario=null;
		if (resultado.first()!=null) {
			usuario=new Usuario();
			usuario.setNombre(email);
		}
		return usuario;
	}
	
	/* REGISTRAR */
	public void registrarUsuario(Usuario usuario, String pwd) throws Exception{
		
		if(existeUsuario(usuario))
			throw new Exception ("ESTAS REGISTRADO");
		
		BsonDocument bUsuario=new BsonDocument();
		bUsuario.append("email", new BsonString(usuario.geteMail()));
		bUsuario.put("pwd", new BsonString(pwd));
		bUsuario.put("user", new BsonString(usuario.getNombre()));
		
		MongoClient conexion=MongoBroker.get().getConexionPrivilegiada();
		MongoCollection<BsonDocument> usuarios = 
				conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
		try {
			usuarios.insertOne(bUsuario);
		}
		catch (MongoWriteException e) {
			if (e.getCode()==11000)
				throw new Exception("¿No estarás ya registrado, chaval/chavala?");
			throw new Exception("Ha pasado algo muy malorrr");
		}
	}
	
	public boolean existeUsuario (Usuario usuario) {
        
		MongoBroker broker=MongoBroker.get();

        BsonDocument criterio=new BsonDocument();
        criterio.append("email", new BsonString(usuario.geteMail()));
        
        MongoClient conexion=broker.getConexionPrivilegiada();
        MongoDatabase db=conexion.getDatabase("OCA");
        MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
        BsonDocument usuario1=usuarios.find(criterio).first();
        return usuario1!=null;
/*
		
		
		BsonDocument criterio=new BsonDocument();
		criterio.append("email", new BsonString(usuario.geteMail()));
		MongoCollection<BsonDocument> usuarios= MongoBroker.get().getDatabase(db).getCollection("usuarios", BsonDocument.class);
		FindIterable<BsonDocument> resultado = usuarios.find(criterio);
		if (resultado.first()!=null) {
			return true;
		}
		return false;*/
	}
	
	public void CREARPRUEBA() {
		
		MongoBroker broker=MongoBroker.get();
		MongoDatabase db = broker.conexionPrivilegiada.getDatabase(this.db);
		
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







