<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String p = request.getParameter("p");
	JSONObject jso=new JSONObject(p);	

	JSONObject respuesta=new JSONObject();
	try {
		/* String usuario=request.getParameter("nombre");
		String email=request.getParameter("email");
		String pwd1=request.getParameter("pwd1");
		String pwd2=request.getParameter("pwd2");  */
		String usuario = jso.optString("nombre");
		String email = jso.optString("email");
		String pwd1 = jso.optString("pwd1");
		String pwd2 = jso.optString("pwd2");
		
		
		comprobarCredenciales(pwd1, pwd2);
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
private void comprobarCredenciales(String pwd1, String pwd2) throws Exception {
	if (!pwd1.equals(pwd2))
		throw new Exception("Las contraseñas no coinciden");
	if (pwd1.length()<4)
		throw new Exception("La contraseña tiene que tener 4 caracteres por lo menos");
}
%>