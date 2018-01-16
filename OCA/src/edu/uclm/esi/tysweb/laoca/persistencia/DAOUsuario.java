package edu.uclm.esi.tysweb.laoca.persistencia;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import edu.uclm.esi.tysweb.laoca.dominio.Usuario;

public class DAOUsuario {
	
	private static String db = "OCA" ;
	
	/* LOGIN */
	public static Usuario login(String email, String pwd) throws Exception {
		MongoClient conexion = MongoBroker.get().getBD();
		
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
			usuario=new Usuario();
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
		MongoBroker.get().close(conexion);
		
		return usuario;
	}
	
	/* OBTENER FOTO */
	public static String getPhoto(String email) throws Exception {
		MongoClient conexion = MongoBroker.get().getBD();
		
		BsonDocument criterio1=new BsonDocument();
		criterio1.append("email", new BsonString(email));
		
		MongoCollection<BsonDocument> usuarios= conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
		FindIterable<BsonDocument> resultado = usuarios.find(criterio1);
		String photo=null;
		
		if (resultado.first()!=null) {
			photo = resultado.first().getString("photo").getValue();
		}else
			throw new Exception("Error en login");
		
		MongoBroker.get().close(conexion);
		
		return photo;
	}
	
	/* REGISTRAR */
	public static void insert(Usuario usuario, String pwd) throws Exception{
		if(existeUsuario(usuario))
			throw new Exception ("ESTAS REGISTRADO");
		
		BsonDocument bUsuario=new BsonDocument();
		bUsuario.append("email", new BsonString(usuario.geteMail()));
		bUsuario.put("pwd", new BsonString(pwd));
		bUsuario.put("user", new BsonString(usuario.getNombre()));
		bUsuario.put("score", new BsonInt32(usuario.getScore()));
		bUsuario.put("photo", new BsonString(usuario.getPhoto()));
		
		MongoClient conexion=MongoBroker.get().getBD();
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
	public static void changePassword(String email, String pwd_old, String pwd1) throws Exception{
		
		Usuario usuario = null;
		try {
			usuario = login(email, pwd_old);
		} catch(Exception e) {
			throw new Exception("El Usuario no Existe");
		}
		
		MongoClient conexion=MongoBroker.get().getBD();
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
	
	/* CAMBIAR FOTOGRAFIA */
	public static void changePhoto(String email, String photo) throws Exception{
		
		MongoClient conexion=MongoBroker.get().getBD();
		MongoCollection<BsonDocument> usuarios = 
				conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
		
		BasicDBObject carrier = new BasicDBObject();
		BasicDBObject query = new BasicDBObject();
		if(email.contains("@"))
			query.append("email", email);
		else
			query.append("user", email);
		
		carrier.put("photo", photo);   
		
		BasicDBObject set = new BasicDBObject("$set", carrier);
		try {
			usuarios.updateMany(query, set);
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
	
	/* EXISTE USUARIO */
	public static boolean existeUsuario (Usuario usuario) throws Exception {
        
		MongoClient conexion = MongoBroker.get().getBD();
		
        BsonDocument criterio=new BsonDocument();
        criterio.append("email", new BsonString(usuario.geteMail()));
        
        MongoCollection<BsonDocument> usuarios = conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
        BsonDocument usuario1=usuarios.find(criterio).first();
        
        return usuario1!=null;
	}
}