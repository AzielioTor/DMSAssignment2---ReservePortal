/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Selects one of the reserves based on form input from JSP,
 * passes to a database bean to have the request entered into the DB
 * 
 *  @author Sez Prouting
 */
package portal.fulltime;

import employees.FulltimeEmployee;
import employees.ReserveEmployee;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class ReserveSelectionServlet extends HttpServlet {

    @EJB
    private DBModifierRemote dBModifier;
    
    /**
     * If user input from the browser is to delet a request, the current (accepted) work request is
     * deleted from the database and from the session bean. The user's status is set to !offWork.
     * Else if user input is to make a work request, a request is entered into the database against
     * the employee ID of the selected reservist. The request is added to the session bean and the
     * user's status is set to offWork.
     * Forwards to the FulltimePageServlet.
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
        FulltimeEmployee thisEmployee= (FulltimeEmployee)session.getAttribute(FieldProvider.FULLTIME_EMPLOYEE);
        
        String deleteRequest = request.getParameter("DeleteRequest");
        String selectReserve = request.getParameter("selectReserve");
        
/***********************************************************************************************************
 * USER IS MAKING A REQUEST
 */        
        if(selectReserve != null){
            /**
             * A new job request will only be processed if thisEmployee does not already have an active request.
             * If there is a pending or accepted request in the database, isOffWork will return true.
             */
            if(! thisEmployee.isOffWork()){

                // Need the list of reserves
                ArrayList<?> reservesList = (ArrayList<?>)session.getAttribute("reserveTable");

                // Need the ReserveEmployee who was selected by the user
                String selection = request.getParameter("reserves"); // get the radio which was selected
                int selectionIndex = Integer.parseInt(selection);   // the selected radio is named after the ArrayList index of the reserve
                ReserveEmployee selectedReserve = (ReserveEmployee)reservesList.get(selectionIndex);
        
                // get today, for dayofWeek
                Format formatter = new SimpleDateFormat("EEEE"); 
                String dayOfWeek = formatter.format(new Date()).toUpperCase();

                // make a new request with all the relevant details
                Request currentRequest = new Request(selectedReserve.getId(), thisEmployee.getId(), true, dayOfWeek);
                    currentRequest.setReserveFirstName(selectedReserve.getFirstName());
                    currentRequest.setReserveLastName(selectedReserve.getLastName());
                    currentRequest.setPhone(selectedReserve.getPhone());
                    currentRequest.setLicenceExpiry(selectedReserve.getLicence_expiry());

                // write the request to the database  
                int requestID = DBAccessorRemote.NOT_FOUND;
                try {
                    requestID = dBModifier.makeRequest(currentRequest.getReserveID(), currentRequest.getFulltimeID(), currentRequest.getDayOfWeek());
                } 
                catch (SQLException | EJBException e) {
                    System.out.println("------> Can't make a request: " + e.getMessage());
                    session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
                    RequestDispatcher dispatcher = getServletContext().
                    getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
                    dispatcher.forward(request, response);
                }
                
                // also save the request in the session bean
                currentRequest.setRequestID(requestID);
                thisEmployee.setCurrentRequest(currentRequest);
            }
        }
                
/***********************************************************************************************************
 * USER IS DELETING A REQUEST
 */
        else if(deleteRequest != null){
            try {
                dBModifier.deleteRequest(thisEmployee.getCurrentRequest().getRequestID());
                thisEmployee.clearCurrentRequest();
                session.setAttribute(FieldProvider.FULLTIME_EMPLOYEE, thisEmployee);
            } 
            catch (SQLException | EJBException e) {
                System.out.println("-------> There was an error deleting the request");
                session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
                RequestDispatcher dispatcher = getServletContext().
                getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
                dispatcher.forward(request, response);
            }
        }
        
        RequestDispatcher dispatcher = getServletContext().
        getRequestDispatcher(FieldProvider.FULLTIME_SERVLET);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Selects one of the reserves based on form input from JSP, passes to a database bean to have the request entered into the DB";
    }// </editor-fold>

}
