<%-- 
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas 
--%>

<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String email = request.getParameter("email");
	
	try{
		Manager.get().enviarToken(email, "localhost:8080/OCA");
		
		response.sendRedirect("../index.html");
	}
	catch (Exception e){
		out.print("Ha fallado algo " + e.toString());
	}
%>