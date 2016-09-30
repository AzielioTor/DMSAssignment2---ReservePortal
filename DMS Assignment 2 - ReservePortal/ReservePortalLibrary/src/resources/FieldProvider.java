/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Abstract class providing consistency in the form of static fields
 *  
 * @author Sez Prouting
 */
package resources;

public abstract class FieldProvider {
    
    public static final String  
            DB_ERROR_JSP="/DBAccessError.jsp",
            EMPLOYEE_NOT_FOUND_JSP="/EmployeeNotFound.jsp",
            ERROR_MESSAGE="errorMessage",
            
            FULLTIME_EMPLOYEE="fulltimeEmployee",
                FULLTIME_JSP="/FulltimePage.jsp",
                FULLTIME_SERVLET="/FulltimePageServlet",
            
            MANAGER="manager",
                MANAGER_JSP="/ManagerPage.jsp",
                MANAGER_SERVLET="/ManagerPageServlet",
                MANAGER_SERVLET_URL="http://localhost:8080/ReservePortal/ManagerPageServlet",
            
            RESERVE_EMPLOYEE="reserveEmployee",
                RESERVE_JSP="/ReservistPage.jsp",
                RESERVE_SERVLET="/ReservePageServlet",
                RESERVE_SERVLET_URL="http://localhost:8080/ReservePortal/ReservePageServlet",
            
            WELCOME_JSP="/WelcomePage.jsp",
            WELCOME_SERVLET="/WelcomePageServlet";
}
