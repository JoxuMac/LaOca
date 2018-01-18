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
		//String email = request.getParameter("nombre");
		//String pwd = request.getParameter("pass");
	
		Usuario usuario = Manager.get().login(email, pwd);
		session.setAttribute("usuario", usuario);
		if(usuario == null)
			throw new Exception();
		
		//session.setAttribute("usuario", usuario);
		//javax.servlet.http.Cookie cookieUsuario = new javax.servlet.http.Cookie("usuario", usuario.getNombre());
		//respueta.addCookie(cookieUsuario) ;
		//response.sendRedirect("../dashboard.html");
		
		respuesta.put("result", "OK");
		respuesta.put("nombre", usuario.getNombre());
		respuesta.put("email", usuario.geteMail());
		respuesta.put("photo", usuario.getPhoto());
	}
	catch (Exception e) {
		//response.sendRedirect("../login.html?err=1");
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	out.println(respuesta.toString());
%>