<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	JSONObject respuesta=new JSONObject();
	try {
		String email=request.getParameter("nombre");
		String pwd=request.getParameter("pass");
		
		Usuario usuario = Manager.get().login(email, pwd);
		if(usuario == null)
			throw new Exception();
		
		session.setAttribute("usuario", usuario);
		respuesta.put("result", "OK");
	}
	catch (Exception e) {
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	
	out.println(respuesta.toString());
%>