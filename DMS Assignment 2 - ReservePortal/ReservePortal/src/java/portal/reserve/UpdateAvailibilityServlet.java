/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 *  Sets a reservists availability to 'available' or 'unavailable' according
 * to the selection made in JSP form.
 * 
 * @author Aziel Shaw
 */
package portal.reserve;

import employees.ReserveEmployee;
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

public class UpdateAvailibilityServlet extends HttpServlet {

    @EJB
    private DBModifierRemote dBAccessorBean;

    /**
     * Sets the reserve availability to 'available' or 'unavailable' as per user input.
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
        
        //Get employee and form details
        String postData = request.getParameter("availabilityRadioGroup");
        HttpSession session = request.getSession(true);
        ReserveEmployee employee = (ReserveEmployee) session.getAttribute(FieldProvider.RESERVE_EMPLOYEE);
        int empId = employee.getId();

        try {
            if (postData.equals("available")) {
                dBAccessorBean.makeResEmpAvailable(empId);
            }
            if (postData.equals("unavailable")) {
                dBAccessorBean.makeResEmpUnavailable(empId);
            }
        } catch (SQLException | EJBException e) {
            System.out.println("------> Error in UpdateAvailablityServlet: " + e.getMessage());
            session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
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
        return "Sets a reservists availability to 'available' or 'unavailable' according to the selection made in JSP form";
    }// </editor-fold>

}
