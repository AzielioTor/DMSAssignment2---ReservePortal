<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
    Document   : FulltimePage
    Created on : 16/04/2016, 5:01:44 PM
    Author     : Sez Prouting
--%>

<%@page import="employees.FulltimeEmployee"%>
<%@page import="employees.ReserveEmployee"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="results.Request"%>
<%@page import = "javax.servlet.RequestDispatcher" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- CUSTOM STYLE CSS -->
        <link href="resources/style.css" rel="stylesheet" >
        <title>Worker's Relief - Fulltime</title>
    </head>
    <body>
        <%@include file="header.html" %>
        
        <jsp:useBean id="fulltimeEmployee" class="employees.FulltimeEmployee"
           scope="session" />

        <div class="main-content">
            <DIV>
<!-- DISPLAY WELCOME MESSAGE -->
                <P>Welcome  <jsp:getProperty name="fulltimeEmployee" property="fullName" />, 
                    (Employer ID: <jsp:getProperty name="fulltimeEmployee" property="id" />)</P>
                <P>Your region is: <jsp:getProperty name="fulltimeEmployee" property="region" /></P>
            </div>
            
            <HR>
            
<!-- DISPLAY CURRENT REQUEST -->
            <DIV align="center">
                    
                <H3>Your Request Status:</H3>
                <c:choose>  
                    <c:when test="${fulltimeEmployee.currentRequest == null}">
                        <P> You have no active requests </P>
                    </c:when>
                            
                    <c:otherwise>
                        <c:choose>  
                            <c:when test="${fulltimeEmployee.currentRequest.pending}">
                                <c:set var="status" value="Pending"/>
                            </c:when>
                            <c:otherwise>
                                <c:choose> 
                                    <c:when test="${fulltimeEmployee.currentRequest.accepted}">
                                        <c:set var="status" value="Accepted"/>
                                    </c:when>
                                    <c:otherwise>  
                                        <c:set var="status" value="Declined"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                        <FORM action = 'http://localhost:8080/ReservePortal/ReserveSelectionServlet' method="post">
                            <TABLE class='table table-responsive' border='1'>
                                <THEAD>
                                <TR>
                                    <th>ID</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Phone</th>
                                    <th>Licence Expiry</th>
                                    <th>Status</th>
                                </TR>
                                </thead>
                                <TBODY>
                                <TR>
                                    <TD>${fulltimeEmployee.currentRequest.reserveID}</TD>
                                    <TD>${fulltimeEmployee.currentRequest.reserveFirstName}</TD>
                                    <TD>${fulltimeEmployee.currentRequest.reserveLastName}</TD>
                                    <TD>${fulltimeEmployee.currentRequest.phone}</TD>
                                    <TD>${fulltimeEmployee.currentRequest.licenceExpiry}</TD>
                                    <TD>${status}</TD>
                                </TR>
                                </TBODY>
                            </table>
                                <P>Cancel or Clear the request: <INPUT type='submit' name='DeleteRequest' value='Cancel/Clear'/></P>
                        </FORM>
                    </c:otherwise>
                </c:choose>
            </DIV>
            
            <HR>
            
<!-- DISPLAY AVAILABLE RESERVES -->  
            <DIV align="center">
                <H3> Available Reserves For Your Area: </H3>
                <FORM action = 'http://localhost:8080/ReservePortal/ReserveSelectionServlet' method="post">
                <TABLE class='table table-responsive' border='1'>
                    <THEAD>
                    <TR>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Phone</th>
                        <th>Licence Expiry</th>
                        <th>Select</th>
                    </TR>
                    </thead>
                <c:forEach items="${reserveTable}" var="reserve" varStatus="row">
                    <tr>
                        <td>${reserve.id}</td>
                        <td>${reserve.firstName}</td>
                        <td>${reserve.lastName}</td>
                        <td>${reserve.phone}</td>
                        <td><fmt:formatDate value="${reserve.licence_expiry}" pattern="dd-MM-yy" /></td>
                        <TD><INPUT type='radio' value='${row.index}' name='reserves'></TD>
                    </tr>
                </c:forEach>
                </TABLE>
                <INPUT type='submit' name='selectReserve' value='Submit Request'>
                </FORM>
            </DIV>
            
            <P><A HREF="WelcomePage.jsp">Start again</A></P>
        </DIV>
    </body>
</html>
