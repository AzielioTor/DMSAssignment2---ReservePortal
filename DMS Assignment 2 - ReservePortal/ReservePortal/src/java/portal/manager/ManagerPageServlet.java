/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 *  Gathers all groups of requests - eg accepted, pending and rejected
 * 
 * @author Sez Prouting
 */
package portal.manager;

import employees.Manager;
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

public class ManagerPageServlet extends HttpServlet {
    @EJB
    private DBAccessorRemote dBAccessorBean;

    public ManagerPageServlet() {
    }

   /**
     * Gathers tables of requests: Pending, accepted and rejected.
     * Forwards to Manager Server Page
     * HTTP <code>GET</code> and <code>POST</code> transparent.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ArrayList<Request> requestsPending, requestsAccepted, requestsRejected;
        String fullName;
        
        HttpSession session = request.getSession(true);
        Manager manager = (Manager)session.getAttribute("manager");
        int id = manager.getId();
        
        // FULL NAME
        try {
            fullName = dBAccessorBean.getFullName(id);
            manager.setFullName(fullName);
        } 
        catch (SQLException | EJBException e) {
            System.out.println("----------> Error running dBAccessorBean.getFullName(empID): " +e.getMessage());
            session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
            RequestDispatcher dispatcher = getServletContext().
            getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
            dispatcher.forward(request, response);
        }
        
        // GET ALL REQUEST TABLES
        try {
            requestsPending = (ArrayList<Request>)dBAccessorBean.getPendingRequests();
            requestsAccepted = (ArrayList<Request>)dBAccessorBean.getAcceptedRequests();
            requestsRejected = (ArrayList<Request>)dBAccessorBean.getRejectedRequests();
            session.setAttribute("pending", requestsPending);
            session.setAttribute("accepted", requestsAccepted);
            session.setAttribute("rejected", requestsRejected);
        } 
        catch (SQLException | EJBException e) {
            System.out.println("-----> can't one or all of the request tables: " + e.getMessage());
            session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
            RequestDispatcher dispatcher = getServletContext().
            getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
            dispatcher.forward(request, response);
        }
        
        // Pass data on to Server Page
        RequestDispatcher dispatcher = getServletContext().
        getRequestDispatcher(FieldProvider.MANAGER_JSP);
        dispatcher.forward(request, response);
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
    }// </editor-fold>
}
