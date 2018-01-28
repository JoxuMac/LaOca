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
		String token=jso.optString("token");
	
		Usuario usuario = Manager.get().loginGoogle(email, token); 
		session.setAttribute("usuario", usuario);
		if(usuario == null)
			throw new Exception();

		respuesta.put("result", "OK");
		respuesta.put("nombre", usuario.getNombre());
		respuesta.put("email", usuario.geteMail());
		//respuesta.put("photo", usuario.getPhoto());
	}
	catch (Exception e) {
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	out.println(respuesta.toString());
%>