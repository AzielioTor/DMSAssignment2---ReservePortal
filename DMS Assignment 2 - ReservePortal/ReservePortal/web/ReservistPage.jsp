<%-- 
    Document   : ReservistPage
    Created on : 3/04/2016, 7:14:06 PM
    Author     : Sez Prouting
--%>

<%@page import="employees.ReserveEmployee"%>
<%@page import="java.util.ArrayList"%>
<%@page import = "javax.servlet.RequestDispatcher" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- CUSTOM STYLE CSS -->
        <link href="resources/style.css" rel="stylesheet" >
        <title>Worker's Relief - Reserve</title>
    </head>
    <body>
        <%@include file="header.html" %>
        <jsp:useBean id="reserveEmployee" class="employees.ReserveEmployee" scope="session" />

        <div class="main-content">
<!-- DISPLAY WELCOME MESSAGE -->
            <P>Welcome 
                <jsp:getProperty name="reserveEmployee" property="fullName" />, (Employer ID:
            <jsp:getProperty name="reserveEmployee" property="id" />)</P>
            
<!-- DISPLAY CURRENT STATUS -->
            <DIV align="center">
                    
            <H3>Your status is set to: 
                <c:choose>
                    <c:when test='${reserveEmployee.isAvailable}'>
                        Available
                    </c:when>
                    <c:otherwise>
                        Unavailable
                    </c:otherwise>
                </c:choose>
            </H3>
            <DIV align='center'>
            <FORM action = 'http://localhost:8080/ReservePortal/UpdateAvailibilityServlet' method='post'>
                <TABLE class='table table-responsive' border='1'>
                    <TR>
                    <TD>
                        <c:choose>
                            <c:when test="${reserveEmployee.isAvailable == true}">
                            <LABEL><INPUT type='radio' id='avail' name='availabilityRadioGroup' value='available' checked>Available</LABEL>
                        </c:when>
                        <c:otherwise>
                        <LABEL><INPUT type='radio' id='avail' name='availabilityRadioGroup' value='available'>Available</LABEL>
                        </c:otherwise> 
                        </c:choose> 
                    </TD>
                    
                    <TD>
                        <c:choose>
                            <c:when test="${reserveEmployee.isAvailable == false}">
                            <LABEL><INPUT type='radio' id='notAvail' name='availabilityRadioGroup' value='unavailable' checked>Unavailable</LABEL>
                        </c:when>
                        <c:otherwise>
                            <LABEL><INPUT type='radio' id='notAvail' name='availabilityRadioGroup' value='unavailable'>Unavailable</LABEL>
                        </c:otherwise> 
                        </c:choose>
                    </TD>
                    
                    <TD><INPUT type='submit' value='Update Status'></TD>
                    </TR>
                </TABLE>
            </FORM>
            </DIV>
                            
<!-- DISPLAY CURRENT REQUESTS -->    
            <H3> Available Work:</H3>
            
            <DIV>
            <c:choose>
                <c:when test="${requestsTable == null}">
                    <P> You have no active requests </P>
                </c:when>
                <c:otherwise>
                    <FORM action = 'http://localhost:8080/ReservePortal/JobSelectionServlet' method='POST'>
                    <TABLE class='table table-responsive' border='1'>
                        <THEAD>
                        <TR>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Shift Begins</th>
                            <th>Shift Ends</th>
                            <th>Address</th>
                            <th>Select</th>
                        </TR>
                        </thead>
                       <TBODY>
                        <c:forEach items="${requestsTable}" var="request" varStatus="row">
                         <TR>
                             <TD>${request.fulltimeFirstName}</TD>
                             <TD>${request.fulltimeLastName}</TD>
                             <TD>${request.shiftBegins}</TD>
                             <TD>${request.shiftEnds}</TD>
                             <TD>${request.address}</TD>
                             <TD><INPUT type='radio' value='${row.index}' name='requests'></TD>
                         </TR>
                        </c:forEach>
                        </TBODY>
                    </table>
                    <input class='centred' type='submit' id='reject' name="submitButton" value='Reject All'>
                    <input class='centred' type='submit' id='accept' name="submitButton" value='Accept Request'>
                    </FORM>
                </c:otherwise>
                </c:choose>
                    
            </DIV>
            
            <br> <H2> Accepted Job:</H2>
            <div>
                <c:choose>
                    <c:when test="${acceptedRequest == null}">
                        <P> You have not accepted a job </P>
                    </c:when>
                    <c:otherwise>
                    <table class='table table-responsive' border='1'>
                        <thead>
                            <tr>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Shift Begins</th>
                                <th>Shift Ends</th>
                                <th>Address</th>
                            </tr>
                        </thead>
                       <tbody>
                            <tr>
                                <td>${acceptedRequest.fulltimeFirstName}</td>
                                <td>${acceptedRequest.fulltimeLastName}</td>
                                <td>${acceptedRequest.shiftBegins}</td>
                                <td>${acceptedRequest.shiftEnds}</td>
                                <td>${acceptedRequest.address}</td>
                            </tr>
                        </tbody>
                    </table>
                    
                </c:otherwise>
                </c:choose>                
            </div>
            </DIV>
            
            <P><A HREF="WelcomePage.jsp">Start again</A></P>
        </div>
    </BODY>
</HTML>