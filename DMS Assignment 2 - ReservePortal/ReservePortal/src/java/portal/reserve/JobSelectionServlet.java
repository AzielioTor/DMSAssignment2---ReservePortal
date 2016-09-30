/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 *  Gets all requests from session bean, selects one as per the JSP form selection and
 * sets the request status to accepted. All other requests are rejected.
 * 
 * @author Aziel Shaw
 */
package portal.reserve;

import employees.ReserveEmployee;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import resources.FieldProvider;
import results.Request;
import sessionBeans.DBAccessorRemote;
import sessionBeans.DBModifierRemote;

public class JobSelectionServlet extends HttpServlet {
    @EJB
    private DBAccessorRemote dBAccessorBean;
    @EJB
    private DBModifierRemote dBModifierBean;
    
    
    /**
     * Sets a job request to accepted, as per user selection. Can also batch-set all requests
     * to rejected as per user input.
     * Forwards to Reserve Servlet.
     * HTTP <code>GET</code> and <code>POST</code> transparent.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get employee
        HttpSession session = request.getSession(true);
        ReserveEmployee employee = (ReserveEmployee)session.getAttribute(FieldProvider.RESERVE_EMPLOYEE);
        int empId = employee.getId();
        
        String postData = request.getParameter("submitButton");
        
        try {
/*******************************************************************
 * RESERVIST REJECTS ALL REQUESTS
 */            
            if(postData.equals("Reject All")) {
                System.out.println("Reject All");
                dBModifierBean.rejectPendingRequests(empId);
            }
                     
/******************************************************************
 * RESERVIST ACCEPTS A REQUEST
 */            
            if(postData.equals("Accept Request")) {
                // get index of selected request
                String selection = request.getParameter("requests");
                int selectionIndex = Integer.parseInt(selection);
            
                // get the specified request
                ArrayList<?> requestsTable = (ArrayList<?>)dBAccessorBean.getRequests(empId);
                Request selectedRequest = (Request)requestsTable.get(selectionIndex);
                int requestID = selectedRequest.getRequestID();
                
                // update DB as required
                dBModifierBean.acceptRequest(requestID);
                dBModifierBean.makeResEmpUnavailable(empId);
                dBModifierBean.rejectPendingRequests(empId);
            }
        } 
        catch (SQLException | EJBException ex) {
            System.out.println("---------> There was an error in JobSelectionServlet" + ex.getMessage());
            session.setAttribute(FieldProvider.ERROR_MESSAGE, ex.getMessage());
            RequestDispatcher dispatcher = getServletContext().
            getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
            dispatcher.forward(request, response);
        }
        
        response.sendRedirect(FieldProvider.RESERVE_SERVLET_URL);
    }
    
        

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Gets all requests from session bean, selects one as per the JSP form selection and sets the request status to accepted. All other requests are rejected.";
    }// </editor-fold>
}
