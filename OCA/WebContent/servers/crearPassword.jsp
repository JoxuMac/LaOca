<%-- 
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas 
--%>

<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	try {
		String email = request.getParameter("email");
		String code = request.getParameter("code");
		
		Manager.get().recuperarPassword(email, code);
		
		response.sendRedirect("../index.html?err=0");
	
	}
	catch (Exception e) {
		System.out.println(e.getMessage());
		if(e.getMessage().equals("2"))
			response.sendRedirect("../index.html?err=2");
		else
			response.sendRedirect("../index.html?err=3");
	}
%>