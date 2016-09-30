/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 * Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 *
 * Takes employee id from JSP form and redirects to another servlet
 * based on employee type (e.g. permanent employee or a reserve)
 *
 * @author Sez Prouting
 */
package portal;

import employees.FulltimeEmployee;
import employees.Manager;
import employees.ReserveEmployee;
import java.io.IOException;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import resources.FieldProvider;
import sessionBeans.DBAccessorRemote;

public class WelcomePageServlet extends HttpServlet {

    @EJB
    private DBAccessorRemote dBAccessorBean;

     /**
     * Takes user ID from the Server Page form and re-directs to the correct
     * Servlet depending on employee type. Employee types are: DBAccessorRemote.FULLTIME_EMPLOYEE,
     * DBAccessorRemote.RESERVE_EMPLOYEE, DBAccessorRemote.MANAGER and DBAccessorRemote.NOT_FOUND.
     * Forwards to the relevant servlet.
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
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;
        
        // get employee ID from the Form
        String empID_temp = request.getParameter("empID");

        // NO ENTRY IN FORM
        if (empID_temp == null
                || empID_temp.length() == 0) {  //start again from welcome page
            dispatcher = getServletContext().
                    getRequestDispatcher(FieldProvider.WELCOME_JSP);
            dispatcher.forward(request, response);
        } 
        // EMPLOYEE NUMBER HAS BEEN ENTERED
        else {
            int empID = Integer.parseInt(empID_temp);
            int employeeType = DBAccessorRemote.NOT_FOUND;

            // Check if employee is in DB
            try {
                employeeType = dBAccessorBean.getEmployeeType(empID);
            } catch (SQLException e) {
                // send to error page
                System.out.println("----------> Error running dBAccessorBean.getEmployeeType(empID): " + e.getMessage());
                session.setAttribute(FieldProvider.ERROR_MESSAGE, e.getMessage());
                dispatcher = getServletContext().
                        getRequestDispatcher(FieldProvider.DB_ERROR_JSP);
                dispatcher.forward(request, response);
            }

            //Redirect based on employee type
            switch (employeeType) {
                //RESERVIST
                case DBAccessorRemote.RESERVE_EMPLOYEE: {
                    ReserveEmployee employee = new ReserveEmployee(empID);
                    session.setAttribute(FieldProvider.RESERVE_EMPLOYEE, employee);
                    dispatcher = getServletContext().
                            getRequestDispatcher(FieldProvider.RESERVE_SERVLET);
                    break;
                }

                //FULL TIME
                case DBAccessorRemote.FULLTIME_EMPLOYEE: {
                    FulltimeEmployee employee = new FulltimeEmployee(empID);
                    session.setAttribute(FieldProvider.FULLTIME_EMPLOYEE, employee);
                    dispatcher = getServletContext().
                            getRequestDispatcher(FieldProvider.FULLTIME_SERVLET);
                    break;
                }

                //MANAGER
                case DBAccessorRemote.MANAGER: {
                    Manager manager = new Manager(empID);
                    session.setAttribute(FieldProvider.MANAGER, manager);
                    dispatcher = getServletContext().
                            getRequestDispatcher(FieldProvider.MANAGER_SERVLET);
                    break;
                }

                //NOT FOUND
                default: {
                    dispatcher = getServletContext().
                            getRequestDispatcher(FieldProvider.EMPLOYEE_NOT_FOUND_JSP);
                }
            }
            dispatcher.forward(request, response);
        }
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

    public WelcomePageServlet() { // not yet used
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Takes employee id from JSP form and redirects to another servlet based on employee type (e.g. permanent employee or a reserve)";
    }// </editor-fold>

}
