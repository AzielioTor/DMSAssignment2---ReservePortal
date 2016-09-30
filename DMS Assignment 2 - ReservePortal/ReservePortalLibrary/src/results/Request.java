/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 *  Stores a work request
 * 
 * @author Sez Prouting
 */
package results;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable{
    protected String ftFirstName, ftLastName, 
                     rFirstName, rLastName, 
                     shiftBegins, shiftEnds, 
                     address, 
                     dayOfWeek;
    protected boolean pending, accepted;
    protected int requestID, 
                    reserveID,
                    fulltimeID,
                    phone;
    protected Date licenceExpiry;

    public Request() {
        this(-1, -1, false, "");
    }

    public Request(int reserveID, int fulltimeID, boolean pending, String dayOfWeek) {
        
        this.reserveID = reserveID;
        this.fulltimeID = fulltimeID;
        this.pending = pending;
        this.dayOfWeek = dayOfWeek;
        ftFirstName = "";
        ftLastName = "";
        rFirstName = "";
        rLastName = "";
        shiftBegins = "";
        shiftEnds = "";
        address = "";
        accepted = false;
    }

//*****************************************************************************
// *  ACCESSORS
// *****************************************************************************/
    
    public String getFulltimeFirstName() {
        return ftFirstName;
    }

    public String getFulltimeLastName() {
        return ftLastName;
    }
    
    public String getReserveFirstName() {
        return rFirstName;
    }

    public String getReserveLastName() {
        return rLastName;
    }

    public int getRequestID() {
        return requestID;
    }

    public int getReserveID() {
        return reserveID;
    }

    public int getFulltimeID() {
        return fulltimeID;
    }

    public int getPhone() {
        return phone;
    }
    
    public String getShiftBegins() {
        return shiftBegins;
    }

    public String getShiftEnds() {
        return shiftEnds;
    }

    public String getAddress() {
        return address;
    }

    public boolean isPending() {
        return pending;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public Date getLicenceExpiry() {
        return licenceExpiry;
    }
    

//*****************************************************************************
//*  MUTATORS
// *****************************************************************************/
    public void setFulltimeFirstName(String ftFirstName) {
        this.ftFirstName = ftFirstName;
    }

    public void setFulltimeLastName(String ftLastName) {
        this.ftLastName = ftLastName;
    }
     public void setReserveFirstName(String rFirstName) {
        this.rFirstName = rFirstName;
    }

    public void setReserveLastName(String rLastName) {
        this.rLastName = rLastName;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public void setReserveID(int reserveID) {
        this.reserveID = reserveID;
    }

    public void setFulltimeID(int fulltimeID) {
        this.fulltimeID = fulltimeID;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setShiftBegins(String shiftBegins) {
        this.shiftBegins = shiftBegins;
    }

    public void setShiftEnds(String shiftEnds) {
        this.shiftEnds = shiftEnds;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setLicenceExpiry(Date licenceExpiry) {
        this.licenceExpiry = licenceExpiry;
    }
    
}
