<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	JSONObject respuesta=new JSONObject();
	try {
		String email=request.getParameter("email");
		String usuario=request.getParameter("usuario");
		String pwd1=request.getParameter("pwd1");
		String pwd2=request.getParameter("pwd2");
		
		comprobarCredenciales(email, pwd1, pwd2, usuario);
		Manager.get().registrar(email, pwd1, usuario);
		respuesta.put("result", "OK");
	}
	catch (Exception e) {
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	out.println(respuesta.toString());
%>

<%!
private void comprobarCredenciales(String email, String pwd1, String pwd2, String usuario) throws Exception {
	if (!pwd1.equals(pwd2))
		throw new Exception("Las contraseñas no coinciden");
	if (pwd1.length()<4)
		throw new Exception("La contraseña tiene que tener 4 caracteres por lo menos");
}
%>