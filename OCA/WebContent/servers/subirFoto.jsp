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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.fileupload.*, org.apache.commons.fileupload.disk.*, org.apache.commons.fileupload.servlet.*, java.io.*, java.util.*" %>

<%
	int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    int MAX_REQUEST_SIZE = 10 * 1024 * 1024;
    
    if (!ServletFileUpload.isMultipartContent(request)) {
    	response.sendRedirect("../index.html");
    	return;
    }
    
    DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setSizeThreshold(MAX_MEMORY_SIZE);
    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
    String uploadFolder = getServletContext().getRealPath("/") + "profile";  // Directorio en el que queremos dejar la foto. Ojo: 
	new File(uploadFolder).mkdir();
    	
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setSizeMax(MAX_REQUEST_SIZE);
    String filePath ="";
    
    try {
    	String fileName = "";
        List items = upload.parseRequest(request);
        Iterator iter = items.iterator();
        String email="";
        while (iter.hasNext()) {
        	FileItem item = (FileItem) iter.next();

            if (!item.isFormField()) {
            	String name = new File(item.getName()).getName();
                fileName = name.substring(0, name.length()-4) + (new Random((new java.util.Date()).getTime())).nextInt() + name.substring(name.length()-4,name.length());
                filePath = uploadFolder + File.separator + fileName;
                File uploadedFile = new File(filePath);
                item.write(uploadedFile);
            } else
            	if(item.getFieldName().contains("email"))
             		email=item.getString();
        }
        
        Manager.get().changePhoto(email, "profile/"+fileName);
       
        response.sendRedirect("../reloadPhoto.html");
        
    } catch (Exception ex) {
        out.print(ex.toString());
   	}
%>