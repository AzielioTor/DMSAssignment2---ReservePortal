/**
 * AUT DMS S1 2016 
 * Assignment 2: - Multitier Web Application 
 * Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 *
 * Gathers the most recent work request and all available reserves. Forwards to JSP for
 * display
 *
 * @author Sez Prouting
 */
package portal.fulltime;

import employees.FulltimeEmployee;
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

public class FulltimePageServlet extends HttpServlet {

    @EJB
    private DBAccessorRemote dBAccessor;

    public FulltimePageServlet() {
        // not yet used
    }

    /**
     * Checks for and gathers the current (accepted) work request which replaces the user.
     * Gathers all reservists and their details who are available and  meet the criteria to
     * work on behalf of the user. Forwards to a Server Page for display.
     * HTTP <code>GET</code> and <code>POST</code> transparent.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String fullName, region;
        Request currentRequest;
        ArrayList<ReserveEmployee> reservesList;

        // get employee id from session bean
        HttpSession session = request.getSession(true);
        FulltimeEmployee employee = (FulltimeEmployee) session.getAttribute(FieldProvider.FULLTIME_EMPLOYEE);
        int empId = employee.getId();

        // get name & region
        try {
            fullName = dBAccessor.getFullName(empId);
                employee.setFullName(fullName);
            region = dBAccessor.getRegion(empId);
                employee.setRegion(region);
                
        }catch (SQLException | EJBException e){
            errorHandler(("Error running dBAccessorBean.getFullName(empID): " + e.getMessage()), 
                    request, response, session);
        }

        // get the current request, if any
        try { 
            // currentRequest may be null. If so, then it is expected the jsp will show there is no current request
            currentRequest = (Request) dBAccessor.getFTCurrentRequest(empId);
                employee.setCurrentRequest(currentRequest);
                
        }catch (SQLException | EJBException e){
            errorHandler(("Error running dBAccessorBean.FTCurrentRequest(empID): " + e.getMessage()), 
                    request, response, session);
        }

        try {    //each ReserveEmployee holds data which can be displayed an an html table
            reservesList = (ArrayList<ReserveEmployee>) dBAccessor.getAvailableReserves(empId);
            session.setAttribute("reserveTable", reservesList);
            
        }catch (SQLException | EJBException e){
            errorHandler(("can't get reserves: " + e.getMessage()), 
                    request, response, session);
        }

        session.setAttribute(FieldProvider.FULLTIME_EMPLOYEE, employee);
        RequestDispatcher dispatcher = getServletContext().
                getRequestDispatcher(FieldProvider.FULLTIME_JSP);
        dispatcher.forward(request, response);
    }

    private void errorHandler(String eMessage,  HttpServletRequest request,
                                                HttpServletResponse response,
                                                HttpSession session)
                                         throws ServletException, IOException{
        System.out.println("----------> "+ eMessage);
            session.setAttribute(FieldProvider.ERROR_MESSAGE, eMessage);
            RequestDispatcher dispatcher = getServletContext().
                    getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
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
        return "Gathers the most recent work request and all available reserves. Forwards to JSP for display";
    }// </editor-fold>

}
