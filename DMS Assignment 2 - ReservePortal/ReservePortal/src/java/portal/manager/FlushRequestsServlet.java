/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Gets request-type from JSP form and sends the request to delete all requests
 * of that type.
 * 
 *  @author Sez Prouting
 */
package portal.manager;

import java.io.IOException;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import resources.FieldProvider;
import sessionBeans.DBModifierRemote;

public class FlushRequestsServlet extends HttpServlet {
    @EJB
    private DBModifierRemote dBModifier;

    /**
     * Provides batch deletion of requests based on user input. Can delete
     * pending, accpted and rejected requests.
     * Forwards to Manager Servlet.
     * HTTP <code>GET</code> and <code>POST</code> transparent.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        
        // extract the form buttons
        String acceptedInput = request.getParameter("accepted");
        String pendingInput = request.getParameter("pending");
        String rejectedInput = request.getParameter("rejected");
        
        int flushType = DBModifierRemote.DO_NOTHING;
        
        if(acceptedInput != null) flushType = DBModifierRemote.ACCEPTED_REQUESTS;
        if(pendingInput != null) flushType = DBModifierRemote.PENDING_REQUESTS;
        if(rejectedInput != null) flushType = DBModifierRemote.REJECTED_REQUESTS;
        
        try {
            dBModifier.flushRequests(flushType);
        } catch (SQLException | EJBException e) {
            System.out.println("----> Could not flush requests table" + e.getMessage());
            session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
            RequestDispatcher dispatcher = getServletContext().
            getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
            dispatcher.forward(request, response);
        }
        
        response.sendRedirect(FieldProvider.MANAGER_SERVLET_URL);
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
        return "Gets request-type from JSP form and sends the request to delete all requests of that type.";
    }// </editor-fold>

}
