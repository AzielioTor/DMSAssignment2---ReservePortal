/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Abstract class containing all fields common to both fulltime & reserve employees
 *  
 * @author Sez Prouting
 */
package employees;

import java.io.Serializable;
import java.util.Date;
import sessionBeans.DBAccessorRemote;

public abstract class Employee implements Serializable {    
        
    // generic attributes applicable to all employees only
    protected int id, phone;
    protected String firstName, 
                    lastName, 
                    fullName,
                    email,
                    address;
    protected Date licenceExpires;
    
    public Employee(){
        this(DBAccessorRemote.NOT_FOUND, "", "");
    }

    public Employee(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = "";
        phone = DBAccessorRemote.NOT_FOUND;
        email = "";
        address = "";
        licenceExpires = new Date(); // current DTG
    }
    
    

/*************************************************************************  
 * ACCESSORS 
 **/
    
    /**
     * @return The full name of the employee
     */
    public String getFullName() {return fullName;}
    
    /**
     * @return The first name of the employee
     */
    public String getFirstName() {return firstName;}
    
    /**
     * @return The last name of the employee
     */
    public String getLastName() {return lastName;}
    
    /**
     * @return The phone number of the employee
     */
    public int getPhone() {return phone;}
    
    /**
     * @return The email adress of the employee
     */
    public String getEmail() {return email;} // currently unused - for forward compatability as the DB holds emails
    
    /**
     * @return The employee's home address // currently unused - for forward compatability as the DB holds home address
     */
    public String getAddress() {return address;}
    
    /**
     * Provides the employee's unique identifier within the company. Note this can only be set on 
     * instantiation of the Employee object.
     * @return Employee ID.
     */
    public int getId() {return id;}   
    
    /**
     * @return Expiry date of the employee's licence
     */
    public Date getLicence_expiry() {return licenceExpires;}
  

    /*************************************************************************
     * MUTATORS 
     **/
    
    /**
     * Allows the full name of the employee to be updated
     * @param fullName The new full name for the employee
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    /**
     * Allows the first name of the employee to be updated
     * @param firstName The new first name for the employee
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Allows the last name of the employee to be updated
     * @param lastName The new last name for the employee
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Allows the employee,s phone number to be updated
     * @param phone The new phone number for the employee
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     * Allows the employee's email address to be updated
     * @param email The new email address for the employee
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Allows the employee's  address to be updated
     * @param address The new address for the employee
     */
    public void setAddress(String address) {
        this.address = address;
    }    

    /**
     * Allows the expiry date of the employee's licence to be updated
     * @param licenceExpiry The expiry date of the employee's licence
     */
    public void setLicenceExpiry(Date licenceExpiry) {
        this.licenceExpires = licenceExpiry;
    }    
    
    /**
     * Provides the ID and full name of the employee
     * @return A String in the format "ID: fullName"
     */
    @Override
    public String toString(){
        return "" + id + ": " + fullName;
    }
    
}
