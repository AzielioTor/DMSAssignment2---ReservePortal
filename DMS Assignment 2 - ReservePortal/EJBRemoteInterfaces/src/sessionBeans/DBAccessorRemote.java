/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Interface for read-only access to DB
 * 
 *  @author Sez Prouting
 */
package sessionBeans;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Remote;

@Remote
public interface DBAccessorRemote {
    public static final int RESERVE_EMPLOYEE=1, FULLTIME_EMPLOYEE=2, MANAGER=3, NOT_FOUND=-1;

    /**
     * Determines the employment type of an employee. 
     * @param id The employee ID for the employee whose type is to be determined
     * @return An int value representing the employee type. See DBAccessorRemote.MANAGER,
     * DBAccessorRemote.RESERVE_EMPLOYEE, DBAccessorRemote.FULLTIME_EMPLOYEE and DBAccessorRemote.NOT_FOUND
     * @throws SQLException 
     */
    int getEmployeeType(int id) throws SQLException;

    /**
     * Gathers all pending results assigned to a reserve employee.
     * @param id The employee ID number of the Reservist to whom the requests are assigned
     * @return An ArrayList of Request objects containing each request assigned to the given reservist
     * @throws SQLException 
     */
    ArrayList getRequests(int id) throws SQLException;

    /**
     * Gathers all available reserves according to the workplace-region, reserve availability, day-of-week and if 
     * licence is current. 
     * @param id ID of the fulltime employee who would be requesting a replacement.
     * @return ArrayList representing all reserves available to work for the requesting employee.
     * Returns null if no open results.
     * @throws SQLException 
     */
    ArrayList getAvailableReserves(int id) throws SQLException;

    /**
     * Used to establish if an employee is of the type DBAccessorRemote.MANAGER
     * @param id ID of the employee who might be a manager
     * @return true if the employee is a manager, else returns false
     * @throws SQLException 
     */
    boolean isManager(int id) throws SQLException;
    
    /**
     * Used to determine if an employee is of the type DBAccessorRemote.RESERVE_EMPLOYEE
     * @param id ID of the employee who might be a reserve
     * @return true if the employee is a reserve, else returns false
     * @throws SQLException 
     */
    boolean isReserve(int id) throws SQLException;

    
    /**
     * Used to determine if an employee is of the type DBAccessorRemote.FULLTIME_EMPLOYEE, but is not a DBAccessorRemote.MANAGER
     * @param id ID of the employee who might be a fulltime employee
     * @return true if the employee is a fulltime employee and not a manager, else returns false
     * @throws SQLException 
     */
    boolean isFulltime(int id) throws SQLException;

    
    /**
     * Carries out safe destruction of bean by closing all statements and streams, etc.
     */
    void destroy();

    /**
     * Assesses whether a reserve employee has a status set to available. Only checks employees
     * of type DBAccessorRemote.RESERVE_EMPLOYEE - all other employees will return a false value
     * @param id The ID number of the employee to be checked
     * @return true if employee is a reservist and is available, else false
     * @throws SQLException 
     */
    boolean isAvailable(int id) throws SQLException;

    /**
     * Provides the first and last names of a given employee
     * @param id The ID of the employee
     * @return String of format "firstName lastName"
     * @throws SQLException 
     */
    String getFullName(int id) throws SQLException;

    /**
     * Discover the region in which a fulltime employee's normal place of work is located.
     * @param id ID of the fulltime employee whose region is being queried
     * @return String containing the region value (refer DBAccessorRemote interface static fields NORTH, SOUTH, WEST, EAST and CENTRAL)
     * @throws SQLException 
     */
    String getRegion(int id) throws SQLException;

    /**
     * Gathers all requests which are pending
     * @return ArrayList of all Requests which are pending and not yet accepted
     * @throws SQLException 
     */
    ArrayList getPendingRequests() throws SQLException;

    /**
     * Gathers requests which have been set to accepted
     * @return ArrayList of Requests which have been accepted
     * @throws SQLException 
     */
    ArrayList getAcceptedRequests() throws SQLException;

    
    /**
     * Gathers requests which have been set to rejected
     * @return ArrayList of Requests which have been rejected
     * @throws SQLException 
     */
    ArrayList getRejectedRequests() throws SQLException;

    /**
     * Fetches the most recent request made by a fulltime employee.
     * @param id The employee number of the fulltime employee who has made a request
     * @return An Object containing the relevant details of the current request. If there is no 
     * current request, the method returns null.
     * @throws java.sql.SQLException 
     */
    Object getFTCurrentRequest(int id) throws SQLException;

    /**
     * Fetches a single request as determined by the request table's primary key
     * @param requestID The ID (primary key) of the request to be fetched
     * @return An Object populated with the following request details: dayOfWeek, fulltimeID, isAccepted,
     * isPending, reserveFirstName, reserveID, reserveLastName, reserveLicenceExpiry, reservePhone, requestID.
     * All other fields are left at default values.
     * @throws SQLException 
     */
    Object getRequestByRequestID(int requestID) throws SQLException;

    /**
     * Finds the primary key of a request specific to a fulltime (requestor) employee and a 
     * reserve (requestee) employee.
     * @param fulltimeID The employee ID of the fulltime employee who made the request
     * @param reserveID The employee ID of the reservist against whom the request was made
     * @return An int value representing the primary key (requestID) of the request.
     * @throws SQLException 
     */
    int getRequestID(int fulltimeID, int reserveID) throws SQLException;

    /**
     * Fetches the most recent job accepted by the reservist.
     * @param empID The companies unique identifier for the employee to whom the work is assigned
     * @return An object containing the relevant details of the accepted request. If there is no current
     * request the method returns null.
     * @throws SQLException 
     */
    Object getCurrentReserveJob(int empID) throws SQLException;
    
}
