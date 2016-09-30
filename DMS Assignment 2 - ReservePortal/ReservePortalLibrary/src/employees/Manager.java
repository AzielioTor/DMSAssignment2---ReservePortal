/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Session bean for a manager
 *  
 * @author Sez Prouting
 */
package employees;

import java.util.ArrayList;
import results.Request;
import sessionBeans.DBAccessorRemote;

public class Manager extends Employee {
    ArrayList<Request> pendingRequests, acceptedRequests;

/*****************************************************************************
 *  CONSTRUCTORS
 *****************************************************************************/
    public Manager() {
        this(DBAccessorRemote.NOT_FOUND, "", "");
    }
    
    public Manager(int id){
        this(id, "", "");
    }

    public Manager(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
        pendingRequests = new ArrayList<>();
        acceptedRequests = new ArrayList<>();
    }

/*****************************************************************************
 *  ACCESSORS
 *****************************************************************************/    

    /**
     * Retrieve all saved requests with a status of 'pending'
     * @return an ArrayList which holds all the pending requests
     */
    public ArrayList<Request> getPendingRequests() {
        return pendingRequests;
    }

    /**
     * Retrieve all saved requests with a status of 'accepted'
     * @return an ArrayList which holds all the accepted requests
     */
    public ArrayList<Request> getAcceptedRequests() {
        return acceptedRequests;
    }

/*****************************************************************************
 *  MUTATORS
 *****************************************************************************/    
    
    /**
     * Allows a collection of requests with a status of 'pending' to be stored in this
     * Manager object
     * @param pendingRequests The ArrayList which holds all the current pending requests
     */
    public void setPendingRequests(ArrayList<Request> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    /**
     * Allows a collection of requests with a status of 'accepted' to be stored in this
     * Manager object
     * @param acceptedRequests The ArrayList which holds all the current accepted requests
     */
    public void setAcceptedRequests(ArrayList<Request> acceptedRequests) {
        this.acceptedRequests = acceptedRequests;
    }
    
}
