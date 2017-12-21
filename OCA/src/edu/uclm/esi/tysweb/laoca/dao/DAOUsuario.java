package edu.uclm.esi.tysweb.laoca.dao;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.bson.BsonString;

import edu.uclm.esi.tysweb.laoca.dominio.Usuario;
import edu.uclm.esi.tysweb.laoca.mongodb.MongoBroker;

public class DAOUsuario {

	public static boolean existe(String nombreJugador) throws Exception {
		return MongoBroker.get().existeUsuario(nombreJugador);
	}
	
	public static void insert(Usuario usuario, String pwd) throws Exception {
		MongoBroker.get().registrarUsuario(usuario, pwd);
	}
	
	public static Usuario login(String email, String pwd) throws Exception {
		return MongoBroker.get().loginUsuario(email, pwd);
	}

	private static BsonString encriptar(String pwd) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest(pwd.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);
		 
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return new BsonString(hashtext);
	}

	

}








