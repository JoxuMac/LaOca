<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String email = request.getParameter("email");
	
	try{
		Manager.get().enviarToken(email, getServletContext().getRealPath("/"));
		
		response.sendRedirect("../index.html");
	}
	catch (Exception e){
		out.print("Ha fallado algo " + e.toString());
	}
%>