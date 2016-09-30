/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Session bean for a fulltime employee
 *  
 * @author Sez Prouting
 */
package employees;

import java.util.ArrayList;
import results.Request;

public class FulltimeEmployee extends Employee {
    
    protected String region;
    protected ArrayList<ReserveEmployee> availableReserves;
    protected Request currentRequest;
    protected boolean isOffWork;        // tracks if there is an active currentRequest
    
    
    public FulltimeEmployee(int id) {
        super(id, "", "");
    }

    public FulltimeEmployee(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
        region = null;
        availableReserves = new ArrayList<>();
        currentRequest = null;
        isOffWork = false;
    }
    
    /*************************************************************************
     *      ACCESSORS
     * ***********************************************************************/
    

    /**
     * Provides access to available reserves based on region, day of week and 
     * availability status.
     * @return String of table data representing available reserves
     */
    public ArrayList<ReserveEmployee> getAvailableReserves() {return availableReserves;
    }
    
    /**
     *  Used to access the region in which the fulltime employee's work address is located
     * @return A string representing the area in which this employee's work address is located.
     */
    public String getRegion() {return region;}

    /**
     * Provides the current request which the employee has submitted to a reserve
     * @return Request object containing details of the current request for a reserve (if any) which the employee has made.
     */
    public Request getCurrentRequest() {return currentRequest;}

    /**
     * Indicates whether the employee has made a request for a reserve to fill in for the day
     * @return True if there is a current request against this employee's ID, else false. If the current request 
     * exists, it does not have to be accepted, true will be returned regardless of the request status.
     */
    public boolean isOffWork(){ return isOffWork;}
    
    /*************************************************************************
     *      MUTATORS
     * ***********************************************************************/
    
    /**
     * Allows a list of all reserves who are prepared to work for this employee
     * @param reservesTable A list of all the reserves who are available to work
     */
    public void setAvailableReserves(ArrayList<ReserveEmployee> reservesTable) {
        availableReserves = reservesTable;
    }

    /**
     * Sets the region in which the fulltime employee's work address is located.
     * @param region The region in which the employee's work addrees is located.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Allows the detail of the current request for a reserve to be stored. The fulltime employee should only
     * make one request at a time.
     * @param currentRequest The request which has been submitted by the fulltime employee
     * @return true if this employee had no active request AND currentRequest was set to a value other than null, else false
     */
    public boolean setCurrentRequest(Request currentRequest) {
    // pre: the current request will only be saved if this employee has not already have 
    // an active request (note, pending & accepted requests are active).
        if(!isOffWork()){
            this.currentRequest = currentRequest;
            if(currentRequest != null)
                setOffWorkStatus(true);
            else setOffWorkStatus(false);
        }
        return isOffWork;
    }
    
    /**
     * Ensures there are no active requests against this employee by setting
     * currentRequest to null and isOffWork to false.
     */
    public void clearCurrentRequest(){
        currentRequest = null;
        setOffWorkStatus(false);
    }
    
    /**
     * Changes status of isOffWork. Note this changes the boolean value only and
     * the currentRequest must be updated accordingly.
     * @param status True if there is a current request against this employee's ID, else false.  
     * @return The new status
     */
    protected boolean setOffWorkStatus(boolean status){
        isOffWork = status;
        return isOffWork;
    }
}
