<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- 
    Document   : ManagerPage
    Created on : Apr 22, 2016, 12:10:03 AM
    Author     : Sez Prouting
--%>
<%@page import= "java.util.ArrayList" %>
<%@page import= "javax.servlet.RequestDispatcher" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- CUSTOM STYLE CSS -->
        <link href="resources/style.css" rel="stylesheet" >
        <title>Worker's Relief - Manager</title>
    </head>
    <body>
        <%@include file="header.html" %>
        <jsp:useBean id="manager" class="employees.Manager" scope="session" />

        <div class="main-content">
<!-- DISPLAY WELCOME MESSAGE -->
            <P>Welcome 
                <jsp:getProperty name="manager" property="fullName" />, You are logged in with Manager ID:
                <jsp:getProperty name="manager" property="id" /></P>
            
            <FORM action = 'http://localhost:8080/ReservePortal/FlushRequestsServlet' method="post">
            <DIV align="center">
<!-- DISPLAY ACCEPTED REQUESTS -->
                <H3> Work Requests Accepted: </H3>
                <TABLE class='table table-responsive' border='1'>
                    <THEAD>
                    <TR>
                        <th>Request ID</th>
                        <th>First Name (FT)</th>
                        <th>Last Name (FT)</th>
                        <th>First Name (Rsv)</th>
                        <th>Last Name (Rsv)</th>
                        <th>Work Address</th>
                        <th>Shift Begins</th>
                        <th>Shift Ends</th>
                    </TR>
                    </thead>
                <c:forEach items="${accepted}" var="request">
                    <tr>
                        <td>${request.requestID}</td>
                        <td>${request.fulltimeFirstName}</td>
                        <td>${request.fulltimeLastName}</td>
                        <td>${request.reserveFirstName}</td>
                        <td>${request.reserveLastName}</td>
                        <td>${request.address}</td>
                        <td>${request.shiftBegins}</td>
                        <td>${request.shiftEnds}</td>
                    </tr>
                </c:forEach>
                </TABLE>
                    <P> Delete All Accepted Requests: <INPUT class='delete' type='submit' name='accepted' value='Delete'></P>
            </DIV>
                
                <HR>
            
<!-- DISPLAY PENDING REQUESTS -->
            <DIV align="center">
                <H3> Work Requests Outstanding: </H3>
                <TABLE class='table table-responsive' border='1'>
                    <THEAD>
                    <TR>
                        <th>Request ID</th>
                        <th>First Name (FT)</th>
                        <th>Last Name (FT)</th>
                        <th>First Name (Rsv)</th>
                        <th>Last Name (Rsv)</th>
                        <th>Work Address</th>
                        <th>Shift Begins</th>
                        <th>Shift Ends</th>
                    </TR>
                    </thead>
                <c:forEach items="${pending}" var="request">
                    <tr>
                        <td>${request.requestID}</td>
                        <td>${request.fulltimeFirstName}</td>
                        <td>${request.fulltimeLastName}</td>
                        <td>${request.reserveFirstName}</td>
                        <td>${request.reserveLastName}</td>
                        <td>${request.address}</td>
                        <td>${request.shiftBegins}</td>
                        <td>${request.shiftEnds}</td>
                    </tr>
                </c:forEach>
                </TABLE>
                    <P> Delete All Pending Requests: <INPUT class='delete' type='submit' name='pending' value='Delete'></P>
            </DIV>
                
                <HR>
            
<!-- DISPLAY REJECTED REQUESTS -->
            <DIV align="center">
                <H3> Work Requests Rejected: </H3>
                <TABLE class='table table-responsive' border='1'>
                    <THEAD>
                    <TR>
                        <th>Request ID</th>
                        <th>First Name (FT)</th>
                        <th>Last Name (FT)</th>
                        <th>First Name (Rsv)</th>
                        <th>Last Name (Rsv)</th>
                        <th>Work Address</th>
                        <th>Shift Begins</th>
                        <th>Shift Ends</th>
                    </TR>
                    </thead>
                <c:forEach items="${rejected}" var="request">
                    <tr>
                        <td>${request.requestID}</td>
                        <td>${request.fulltimeFirstName}</td>
                        <td>${request.fulltimeLastName}</td>
                        <td>${request.reserveFirstName}</td>
                        <td>${request.reserveLastName}</td>
                        <td>${request.address}</td>
                        <td>${request.shiftBegins}</td>
                        <td>${request.shiftEnds}</td>
                    </tr>
                </c:forEach>
                </TABLE>
                    <P> Delete All Rejected Requests: <INPUT class='delete' type='submit' name='rejected' value='Delete'></P>
            </DIV>
            </FORM>
            
            <P><A HREF="WelcomePage.jsp">Start again</A></P>
        </DIV>
    </body>
</html>