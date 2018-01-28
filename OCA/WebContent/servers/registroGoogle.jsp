<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String p = request.getParameter("p");
	JSONObject jso=new JSONObject(p);	

	JSONObject respuesta=new JSONObject();
	try {		
		
		String email = jso.optString("email");
		String token = jso.optString("token");
		String user = jso.optString("user");
		String photo = jso.optString("photo");


			
		Manager.get().registrarGoogle(email,token,user,photo);
		respuesta.put("result", "OK");
	}
	catch (Exception e) {
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	out.println(respuesta.toString());
%>
