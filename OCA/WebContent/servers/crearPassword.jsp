<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@page import="org.bson.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
out.println("hola");
	try {
	//	String email=jso.optString("email");
	//	String pwd=jso.optString("pwd1");
	String email = request.getParameter("email");
	String code = request.getParameter("code");
	
	TokenRecuperacionPwd token = Manager.get().getTokenPassword(email);
	
	BsonDocument btoken = token.toBsonDocument();
	
	if(!btoken.getString("email").getValue().equals(email))
		throw new Exception();
		
	if(!btoken.getString("valor").getValue().equals(code))
		throw new Exception();
	
	//if(!btoken.getString("cadaucidad").getValue().equals(email))
	// lanzar excepcion
	
	
	
	//if(token.)
	
	//	Usuario usuario = Manager.get().login(email, pwd);
		
	//	if(usuario == null)
	//		throw new Exception();
		
		//session.setAttribute("usuario", usuario);
		//javax.servlet.http.Cookie cookieUsuario = new javax.servlet.http.Cookie("usuario", usuario.getNombre());
		//respueta.addCookie(cookieUsuario) ;
		//response.sendRedirect("../dashboard.html");
		
	//	respuesta.put("result", "OK");
	//	respuesta.put("nombre", usuario.getNombre());
	//	respuesta.put("email", usuario.geteMail());
	//	respuesta.put("photo", usuario.getPhoto());
	}
	catch (Exception e) {
		response.sendRedirect("./index.html?err=1");
	//	respuesta.put("result", "ERROR");
	//	respuesta.put("mensaje", e.getMessage());
	}
	//out.println(respuesta.toString());
%>