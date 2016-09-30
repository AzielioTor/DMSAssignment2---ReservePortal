<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 5 Transitional//EN">
<%-- 
    Document   : WelcomePage
    Created on : 3/04/2016, 3:13:00 PM
    Author     : Sez Prouting 0308852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "javax.servlet.RequestDispatcher" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- CUSTOM STYLE CSS -->
        <link href="resources/style.css" rel="stylesheet" >
        
        <title>Worker's Relief - Home</title>
    </head>
    <body>
        <%@include file="header.html" %>
<!-- Set initial page functions -->
            <%
                String emp_ID = request.getParameter("empID");

                if(emp_ID == null){
                    emp_ID = "";
                }

                if(emp_ID.length() > 0){
                    System.out.println("emp_id is: " +emp_ID);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WelcomePageServlet");
                    dispatcher.forward(request, response);
                }            
            %>
        <div class="main-content">
            
<!-- Obtain employee ID from Form & send to servlet for processing -->
            <form action="http://localhost:8080/ReservePortal/WelcomePageServlet" method="post">
                <p align="center">Employee ID: 
                <input type="TEXT" name="empID" maxlength="5" 
                       pattern="[0-9]{5}" oninvalid="alert('Please enter a 5 digit number')">
                </p>
                <input class="centred" type="submit" value="Submit">
            </form>
        </div>
    </body>
</html>
