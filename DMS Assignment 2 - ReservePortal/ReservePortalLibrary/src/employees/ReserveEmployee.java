/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Session bean for a reservist
 *  
 * @author Sez Prouting
 */
package employees;

public class ReserveEmployee extends Employee{

    protected boolean available;

    /*******************************************************************
     * CONSTRUCTORS
     **/ 
    
    /**
     * 
     * @param id The unique identifier for the employee, as per the employing organisation
     */
    public ReserveEmployee(int id){
        super(id, "", "");
    }
    
    public ReserveEmployee(int id, String firstName, String lastName){
        super(id, firstName, lastName);
        available = false;
    }

    /*******************************************************************
     * ACCESSORS
     **/
    
    /**
     * Informs whether this ReserveEmployee is available for requests to me made
     * against her/his employee ID
     * @return True if the ReserveEmployee is available, else false
     */
    public boolean getIsAvailable() {
        return available;
    }

    /*******************************************************************
     * MUTATORS
     **/
    
    /**
     * Allows the availability of this ReserveEmployee to be set
     * @param available Set to true if the ReserveEmployee is available, else set to false
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
