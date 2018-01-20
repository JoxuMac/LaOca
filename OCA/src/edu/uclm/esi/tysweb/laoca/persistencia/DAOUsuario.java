package edu.uclm.esi.tysweb.laoca.persistencia;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;

import edu.uclm.esi.tysweb.laoca.dominio.TokenRecuperacionPwd;
import edu.uclm.esi.tysweb.laoca.dominio.Usuario;
import jdk.nashorn.internal.parser.JSONParser;

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
		
		usuarios.insertOne(bUsuario);
		
		MongoBroker.get().close(conexion);
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
		
		
		usuarios.findOneAndReplace(bUsuario, nPassword);
		
		MongoBroker.get().close(conexion);
	}
	
	/* GUARDAR TOKEN CLAVE */
	public static void saveToken(String email, TokenRecuperacionPwd token) throws Exception{
		MongoClient conexion=MongoBroker.get().getBD();
		MongoCollection<BsonDocument> usuarios = 
				conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
		
		BasicDBObject carrier = new BasicDBObject();
		BasicDBObject query = new BasicDBObject();
		if(email.contains("@"))
			query.append("email", email);
		else
			query.append("user", email);
		
		carrier.put("tokenPassword", token.toBsonDocument());   
		
		BasicDBObject set = new BasicDBObject("$set", carrier);
		
		usuarios.updateMany(query, set);
		
		MongoBroker.get().close(conexion);
	}
	
	/* OBTENER EL TOKEN DE RECUPERACION */
	public static TokenRecuperacionPwd getTokenPwd(String email) throws Exception {
		MongoClient conexion = MongoBroker.get().getBD();
		
		BsonDocument criterio1=new BsonDocument();
		criterio1.append("email", new BsonString(email));
		
		MongoCollection<BsonDocument> usuarios= conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
		FindIterable<BsonDocument> resultado = usuarios.find(criterio1);
		TokenRecuperacionPwd token = null;
		
		if (resultado.first()!=null) {
			
			BsonValue sToken = resultado.first().get("tokenPassword");
			BsonDocument doc = new BsonDocument();
			doc = sToken.asDocument();
			
			token = new TokenRecuperacionPwd(doc.getString("email").getValue(), doc.getInt64("valor").getValue(), doc.getInt64("caducidad").getValue());
			
		}else
			throw new Exception("Error en el Token");
		
		MongoBroker.get().close(conexion);
		
		return token;
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
		
		usuarios.updateMany(query, set);
		
		MongoBroker.get().close(conexion);
		
	}
	
	/* EXISTE USUARIO */
	public static boolean existeUsuario (Usuario usuario) throws Exception {
		MongoClient conexion = MongoBroker.get().getBD();
		
		
        BsonDocument criterio=new BsonDocument();
        criterio.append("email", new BsonString(usuario.geteMail()));
        
        MongoCollection<BsonDocument> usuarios = conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
        BsonDocument usuario1=usuarios.find(criterio).first();
        
        MongoBroker.get().close(conexion);
        
        return usuario1!=null;
	}

	/* NUEVA CLAVE */
	public static void newPassword(String email, String newPassword) throws Exception {
		MongoClient conexion=MongoBroker.get().getBD();
		MongoCollection<BsonDocument> usuarios = 
				conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
		
		BasicDBObject carrier = new BasicDBObject();
		BasicDBObject query = new BasicDBObject();
		
		query.append("email", email);
		
		carrier.put("pwd", newPassword);   
		
		BasicDBObject set = new BasicDBObject("$set", carrier);
		
		usuarios.updateMany(query, set);
		
		MongoBroker.get().close(conexion);
	}
	
	public static JSONObject getRanking() throws Exception{
		JSONArray score_array = new JSONArray();
		JSONObject score_obj = new JSONObject();		
		MongoClient conexion=MongoBroker.get().getBD();
		Bson sort = Sorts.descending("usuarios", "score");
		
		MongoCollection<BsonDocument> usuarios = 
				conexion.getDatabase(db).getCollection("usuarios", BsonDocument.class);
        //BsonDocument usuario1=usuarios.find(criterio).first();

		FindIterable<BsonDocument> find= usuarios.find().sort(sort).limit(5);

		int pos=0;
		for(BsonDocument index : find) {
			pos++;
			score_obj.put("user", index.getString("user").getValue());
			score_obj.put("score", index.getInt32("score").getValue());
			score_obj.put("position", pos+"");
			score_array.put(score_obj);
			score_obj = new JSONObject();
		}
		JSONObject result = new JSONObject();

		result.put("scores", score_array);
		MongoBroker.get().close(conexion);
		return result;
				
	}
}