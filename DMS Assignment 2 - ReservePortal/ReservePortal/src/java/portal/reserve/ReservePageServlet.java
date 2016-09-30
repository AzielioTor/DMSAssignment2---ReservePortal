/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 *  Gathers all pending requests against Reservists ID and passes to ReservistPage.jsp
 *          for display.
 * 
 * @author Sez Prouting and Aziel Shaw
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

public class ReservePageServlet extends HttpServlet{

    @EJB
    private DBAccessorRemote dBAccessorBean;
      
    /**
     * Gets current accepted job (if it exists) and all pending requests assigned to the user.
     * Forwards to the Reservist Server Page.
     * HTTP <code>GET</code> and <code>POST</code> transparent.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, 
                                  HttpServletResponse response)
                                  throws ServletException, IOException{
        
        String fullName;
        ArrayList<Request> requestsTable;
        boolean isAvailable;
        RequestDispatcher dispatcher;
        Request acceptedRequest;
        
        // get employee id from session bean
        HttpSession session = request.getSession(true);
        ReserveEmployee employee = (ReserveEmployee)session.getAttribute(FieldProvider.RESERVE_EMPLOYEE);
        int empId = employee.getId();
        
        // get name & availablitiy
        try{
            fullName = dBAccessorBean.getFullName(empId);
            isAvailable = dBAccessorBean.isAvailable(empId);
            employee.setFullName(fullName);
            employee.setAvailable(isAvailable);
        }
        catch(SQLException | EJBException e)
        {
            System.out.println("---------> Error running dBAccessorBean.isAvailable(empID) or dBAccessorBean.getFullName(empID): " +e.getMessage());
            session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
            dispatcher = getServletContext().getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
            dispatcher.forward(request, response);
        }
        
        // Get open requests
        try{
           requestsTable = dBAccessorBean.getRequests(empId);
           session.setAttribute("requestsTable", requestsTable);
        }
        catch(SQLException | EJBException e){
            System.out.println("---------> Can't get requests: " + e.getMessage());
            session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
            dispatcher = getServletContext().getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
            dispatcher.forward(request, response);
        }
        
        // Get accepted request
        try{
           acceptedRequest = (Request)dBAccessorBean.getCurrentReserveJob(empId);
           session.setAttribute("acceptedRequest", acceptedRequest);
        }
        catch(SQLException | EJBException e){
            System.out.println("Error accessing accepted job request: " + e.getMessage());
            session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
            dispatcher = getServletContext().getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
            dispatcher.forward(request, response);
        }
        
        // Pass all to the JSP
        session.setAttribute(FieldProvider.RESERVE_EMPLOYEE, employee);
        dispatcher = getServletContext().getRequestDispatcher(FieldProvider.RESERVE_JSP);
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
    }
    
    public ReservePageServlet(){
        // not yet required
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

