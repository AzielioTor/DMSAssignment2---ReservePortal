<%-- 
    Document   : DBAccessError
    Created on : 11/04/2016, 11:51:17 AM
    Author     : Sez Prouting
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- CUSTOM STYLE CSS -->
        <link href="resources/style.css" rel="stylesheet" >
        <title>Worker's Relief - DB Access Error</title>
    </head>
    <body>
        <%@include file="header.html" %>
        
        <div class="main-content">
            <h1>I'm sorry</h1>
            <p>We are unable to access the employee database. Please try again another time,</p>
            <p>or call IT Support on 09-555-5521 and quote:</P>
            <P>Error message: ${errorMessage}</p>
            
            <P><A HREF="WelcomePage.jsp">Try again</A></P>
        </div>
    </body>
</html>
