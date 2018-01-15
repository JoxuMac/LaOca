/*
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas
*/

package edu.uclm.esi.tysweb.laoca.mongodb;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.dominio.Usuario;
import edu.uclm.esi.tysweb.laoca.dominio.UsuarioRegistrado;

public class MongoBroker {
	
	private ConcurrentLinkedQueue<MongoClient> usadas, libres;
	private MongoClient conexionPrivilegiada;
	private String server = "localhost";
	private int puerto = 27017;
	private static String db = "OCA";
			
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

	
	public void close(MongoClient conexion) {
        this.usadas.remove(conexion);
        this.libres.offer(conexion);
    }
//	public void close() {
//		this.conexionPrivilegiada.close();
//	}

//	public MongoClient getConexionPrivilegiada() {
//		return this.conexionPrivilegiada;
//	}
	public MongoClient getConexionPrivilegiada() {
        MongoCredential credenciales = MongoCredential.createCredential("root", MongoBroker.db, "root".toCharArray());
        ServerAddress address = new ServerAddress("localhost");
        List<MongoCredential> lista = Arrays.asList(credenciales);
        return new MongoClient(address, lista);
    }
	
	/* LOGIN */
	public Usuario loginUsuario(String email, String pwd) throws Exception {
		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		
		BsonDocument criterio1=new BsonDocument();
		criterio1.append("email", new BsonString(email));
		criterio1.append("pwd", new BsonString(pwd));
		
		BsonDocument criterio2=new BsonDocument();
		criterio2.append("user", new BsonString(email));
		criterio2.append("pwd", new BsonString(pwd));

		MongoCollection<BsonDocument> usuarios= conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
		FindIterable<BsonDocument> resultado = usuarios.find(criterio1);
		Usuario usuario=null;
		if (resultado.first()!=null) {
			usuario=new UsuarioRegistrado();
			usuario.seteMail(resultado.first().getString("email").getValue());
			usuario.setNombre(resultado.first().getString("user").getValue());
			usuario.setPhoto(resultado.first().getString("photo").getValue());
		}else {
			resultado = usuarios.find(criterio2);
			if (resultado.first()!=null) {
				usuario=new Usuario();
				usuario.seteMail(resultado.first().getString("email").getValue());
				usuario.setNombre(resultado.first().getString("user").getValue());
				usuario.setPhoto(resultado.first().getString("photo").getValue());
			} else
				throw new Exception("Error en login");
		}
		
		//conexion.close();
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
		bUsuario.put("score", new BsonInt32(usuario.getScore()));
		bUsuario.put("photo", new BsonString(usuario.getPhoto()));
		
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
		finally {
			MongoBroker.get().close(conexion);
		}
	}
	
	/* CAMBIAR CLAVE */
	public void changePassword(String email, String pwd_old, String pwd1) throws Exception{
		
		Usuario usuario = null;
		try {
			usuario = loginUsuario(email, pwd_old);
		} catch(Exception e) {
			throw new Exception("El Usuario no Existe");
		}
		
		MongoClient conexion=MongoBroker.get().getConexionPrivilegiada();
		MongoCollection<BsonDocument> usuarios = 
				conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
		
		BsonDocument bUsuario=new BsonDocument();
		bUsuario.append("email", new BsonString(email));
		
		BsonDocument nPassword=new BsonDocument();
		nPassword.append("email", new BsonString(usuario.geteMail()));
		nPassword.put("pwd", new BsonString(pwd1));
		nPassword.put("user", new BsonString(usuario.getNombre()));
		nPassword.put("score", new BsonInt32(usuario.getScore()));
		nPassword.put("photo", new BsonString(usuario.getPhoto()));
		
		try {
			usuarios.findOneAndReplace(bUsuario, nPassword);
		}
		catch (MongoWriteException e) {
			if (e.getCode()==11000)
				throw new Exception("¿No estarás ya registrado, chaval/chavala?");
			throw new Exception("Ha pasado algo muy malorrr");
		}
		finally {
			MongoBroker.get().close(conexion);
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
}