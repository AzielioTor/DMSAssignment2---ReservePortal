/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Interface for write-access to DB
 * 
 *  @Author Aziel Shaw and Sez Prouting
 */
package sessionBeans;

import java.sql.SQLException;
import javax.ejb.Remote;

@Remote
public interface DBModifierRemote {
    
    public static final int ACCEPTED_REQUESTS=0, PENDING_REQUESTS=1, REJECTED_REQUESTS=2;
    public static final int DO_NOTHING=-1;

    /**
     * Sets a reserve employee's status to 'unavailable'
     * @param empID The employee ID of the reserve who's status is to be reset
     * @throws SQLException 
     */
    void makeResEmpUnavailable(int empID) throws SQLException;

    /**
     * Sets a reserve employee's status to 'available'
     * @param empID The employee ID of the reserve who's status is to be reset
     * @throws SQLException 
     */
    void makeResEmpAvailable(int empID) throws SQLException;

    /**
     * Used to delete all requests of a certain status out of the requests table
     * @param flushType Specifies what status of request is to be delete from the table.
     * See: DBModifierRemote.ACCEPTED_REQUESTS, DBModifierRemote.PENDING_REQUESTS, DBModifierRemote.REJECTED_REQUESTS
     * @throws SQLException 
     */
    void flushRequests(int flushType) throws SQLException;

    /**
     * Enters a new request into the requests table in the database
     * @param reserveID The employee number of the Reserve being requested
     * @param fulltimeID The employee number of the Fulltime employee making the request
     * @param dayOfWeek The day of the week on which the work is to be carried out
     * @throws SQLException 
     * @return A unique identifier for the new request
     */
    int makeRequest(int reserveID, int fulltimeID, String dayOfWeek)  throws SQLException;

    /**
     * Deletes the specified request from the database, regardless of request status
     * @param requestID The unique identifier of the request which is to be deleted
     * @throws SQLException 
     */
    void deleteRequest(int requestID) throws SQLException;

    /**
     * Sets all requests assigned to a reservist to 'rejected'
     * @param empID The id of the reservist who has rejected the requests
     * @throws SQLException 
     */
    void rejectPendingRequests(int empID) throws SQLException;

    /**
     * Sets the assigned request status to accepted
     * @param requestID The primary key of the request to be accepted
     * @throws SQLException 
     */
    void acceptRequest(int requestID) throws SQLException;
    
    /**
     * Carries out safe destruction of bean by closing all statements and streams, etc.
     */
    void destroy();
    
}
