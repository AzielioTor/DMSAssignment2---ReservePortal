<%-- 
    Document   : EmployeeNotFound
    Created on : 16/04/2016, 5:52:09 PM
    Author     : Sez Prouting
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- CUSTOM STYLE CSS -->
        <link href="resources/style.css" rel="stylesheet" >
        <title>Worker's Relief - Not Found</title>
    </head>
    <body>
        <%@include file="header.html" %>
        
        <div class="main-content">
            <h1>I'm sorry</h1>
            <p>You entered an employee number which is not in the database.</p>
            <p>Please try again</p>
            <p>or call IT Support on 09-555-5521</p>
            <P><A HREF="WelcomePage.jsp">Try again</A></P>
        </div>
    </body>
</html>
