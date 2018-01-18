package edu.uclm.esi.tysweb.laoca.dominio;

import org.bson.*;

public class TokenRecuperacionPwd {
	private long valor;
	private long caducidad;
	private String email;
	
	public TokenRecuperacionPwd(String email, long valor) {
		this.valor=valor;
		this.caducidad=System.currentTimeMillis() + /*24*60**/60*1000 ;
		this.email = email;
	}
	
	public TokenRecuperacionPwd(String email, long valor, long caducidad) {
		this.valor=valor;
		this.caducidad=caducidad ;
		this.email = email;
	}
	
	public BsonDocument toBsonDocument() {
		BsonDocument result = new BsonDocument();
		result.put("valor",  new BsonInt64(this.valor));
		result.put("caducidad", new BsonInt64(this.caducidad));
		result.put("email", new BsonString(this.email));
		
		return result;
		
	}
}
