<%-- 
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas 
--%>

<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String p = request.getParameter("p");
	JSONObject jso=new JSONObject(p);	
	JSONObject respuesta=new JSONObject();

	try {
		String email=jso.optString("email");
		String pwd=jso.optString("pwd1");
	
		Usuario usuario = Manager.get().login(email, pwd);
		session.setAttribute("usuario", usuario);
		if(usuario == null)
			throw new Exception();
		
		respuesta.put("result", "OK");
		respuesta.put("nombre", usuario.getNombre());
		respuesta.put("email", usuario.geteMail());
		respuesta.put("photo", usuario.getPhoto());
	}
	catch (Exception e) {
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	out.println(respuesta.toString());
%>