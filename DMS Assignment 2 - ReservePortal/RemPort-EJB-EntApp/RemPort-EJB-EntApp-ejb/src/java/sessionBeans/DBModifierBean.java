/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Takes place of DOA to handle database updates for ReservePortal servlets
 * 
 *  @author Aziel Shaw & Sez Prouting
 */
package sessionBeans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import results.Request;
import static sessionBeans.DBPropertiesRemote.REQUESTS_TABLE;

@Stateless
public class DBModifierBean implements DBModifierRemote {
    @EJB
    private DBAccessorRemote dBAccessor;
    @EJB
    private DBPropertiesRemote properties;
    
    private final String TRUE="1", FALSE="0";   // for use in SQL satements
    private Connection conn;
    protected PreparedStatement makeUserAvailable, 
                                makeUserUnavailable,
                                flushRequests,
                                makeRequest,
                                deleteRequestByRequestID,
                                rejectPendingRequests,
                                acceptRequest;
    
    
    @Override
    public void makeResEmpUnavailable(int empID) throws SQLException {
        synchronized(this){
            makeUserUnavailable.setInt(1, empID);
            makeUserUnavailable.executeUpdate();
        }
    }
    
    //Sets a reserve employee's status to 'available.' (Refer interface javadocs)
    @Override
    public void makeResEmpAvailable(int empID) throws SQLException {
        synchronized(this){
            makeUserAvailable.setInt(1, empID);
            makeUserAvailable.executeUpdate();
        }
    }
    
    //Sets all requests assigned to a reservist to 'rejected.' (Refer interface javadocs)
    @Override
    public void rejectPendingRequests(int empID) throws SQLException {
        
        if(dBAccessor.isReserve(empID)){
            synchronized(this){
                rejectPendingRequests.setInt(1, empID);
                rejectPendingRequests.executeUpdate();
            }
        }
    }

    
    //Sets the assigned request status to accepted. (Refer interface javadocs)
    @Override
    public void acceptRequest(int requestID) throws SQLException {
        synchronized(this){
            acceptRequest.setInt(1, requestID);
            acceptRequest.executeUpdate();
        }
    }

    
    //Used to delete all requests of a certain status out of the requests table. (Refer interface javadocs)
    @Override
    public void flushRequests(int flushType) throws SQLException {
        
        synchronized(this){
            ArrayList<Request> results;
            
            switch (flushType) {
                case ACCEPTED_REQUESTS: results = dBAccessor.getAcceptedRequests();
                    break;
                case PENDING_REQUESTS: results = dBAccessor.getPendingRequests();
                    break;
                case REJECTED_REQUESTS: results = dBAccessor.getRejectedRequests();
                    break;
                default:
                    return;
            }

            for(Request request : results){
                flushRequests.setInt(1, request.getRequestID());
                flushRequests.executeUpdate();
            }
        }
    }
    
    
    //Enters a new request into the requests table in the database. (Refer interface javadocs)
    @Override
    public int makeRequest(int reserveID, int fulltimeID, String dayOfWeek)  throws SQLException{
        
        synchronized (this){
            makeRequest.setInt(1, reserveID);
            makeRequest.setInt(2, fulltimeID);
            makeRequest.setString(3, TRUE );     // new requests will always be pending
            makeRequest.setString(4, FALSE);    // new requests cannot start off as 'accepted'
            makeRequest.setString(5, dayOfWeek);

            makeRequest.executeUpdate();
            
            return dBAccessor.getRequestID(fulltimeID, reserveID);
        }
    }
    
    // Deletes the specified request from the database, regardless of request status.  (Refer interface javadocs)
    @Override
    public void deleteRequest(int requestID) throws SQLException {
        synchronized (this){
            //only attempt a delete statement if the request is in the DB
            Request checkDB = (Request)dBAccessor.getRequestByRequestID(requestID);
            if(checkDB != null){
                deleteRequestByRequestID.setInt(1, requestID);
                deleteRequestByRequestID.executeUpdate();
            }
        }
    }
    
    /**
     * Makes initial database connection and prepares SQL statements
     * */
    @PostConstruct
    public void init(){
            
        try{
            Class.forName(properties.getDbDriver());
        }
        catch(ClassNotFoundException e){
            System.err.println("---------------> DB Driver class not found: " + e.getMessage());
        }
        
        try{
            conn = DriverManager.getConnection(properties.getDbUrl(), properties.getUserName(), properties.getPassword());
        
             //Prepare Statements
            makeUserAvailable = conn.prepareStatement("UPDATE " + properties.getReservesTable()
                                                      + " SET " + properties.available() + " = 1 "   
                                                     + " WHERE "+ properties.getReserveID() + " = ?");
            
            makeUserUnavailable = conn.prepareStatement("UPDATE " + properties.getReservesTable()
                                                        + " SET " + properties.available() + " = 0 "
                                                       + " WHERE "+ properties.getReserveID() +  " = ?");
            
            flushRequests = conn.prepareStatement("DELETE FROM " +properties.getRequestsTable() +
                                                       " WHERE " +properties.getRequestID() + " =?");
            
            makeRequest = conn.prepareStatement("INSERT INTO " +properties.getRequestsTable() +
                                                           "(" +properties.getReserveID(DBPropertiesRemote.REQUESTS_TABLE) +
                                                          ", " +properties.getFulltimeID(DBPropertiesRemote.REQUESTS_TABLE) +
                                                          ", " +properties.isPending() +
                                                          ", " +properties.isAccepted() +
                                                          ", " +properties.getDayOfWeek() + 
                                                        ") VALUES (?, ?, ?, ?, ?)");
            
            deleteRequestByRequestID = conn.prepareStatement("DELETE FROM " +properties.getRequestsTable() +
                                                                  " WHERE " +properties.getRequestID() + "=?");
            rejectPendingRequests = conn.prepareStatement("UPDATE " + properties.getRequestsTable() + " "
                                                        + " SET " + properties.isPending() + " = 0 "
                                                        + " WHERE " + properties.getReserveID(REQUESTS_TABLE) + " = ?");
                    
            acceptRequest = conn.prepareStatement("UPDATE " + properties.getRequestsTable() + " "
                                                        + " SET " + properties.isAccepted() + " = 1 "
                                                        + " WHERE " + properties.getRequestID() + " = ?");
        }
        catch(SQLException e){
            System.out.println("------------>  Error during DBModifier statement preperation: " + e.getMessage());
        }
    }
    
    
    @Override
    //Closes all statements when the bean is destroyed.
    public void destroy(){
        try {
            if(makeUserAvailable != null) makeUserAvailable.close();
            if(makeUserUnavailable != null) makeUserUnavailable.close();
            if(flushRequests != null) flushRequests.close();
            if(makeRequest != null) makeRequest.close();
            if(deleteRequestByRequestID != null) deleteRequestByRequestID.close();
            if(rejectPendingRequests != null) rejectPendingRequests.close();
            if(acceptRequest != null) acceptRequest.close();
        } catch (SQLException e) {
            System.err.println("Issue closing DB Modifier Statements: " + e.getMessage());
        }
    }
}
