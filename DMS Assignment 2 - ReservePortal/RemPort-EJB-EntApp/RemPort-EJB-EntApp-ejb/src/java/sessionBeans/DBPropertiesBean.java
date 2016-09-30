/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * A singleton bean used to translate between database config xml and
 * EJBs wishing to access the database
 * 
 * @author Sez Prouting
 *  
 */
package sessionBeans;

import java.io.IOException;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class DBPropertiesBean implements DBPropertiesRemote {

    private Properties properties;
    
    @PostConstruct
    private void init(){
        properties = new Properties();
        try{
            properties.loadFromXML(getClass().getResourceAsStream
            ("resources/DBAccessConfig.xml"));
            
        }catch(IOException e){
            System.err.println("======= Cannot access properties file: "+ e.getMessage());
        }
    }

    // The group headings and methods are in the same order as found in DBAccessConfig.xml
    
    /*************************************************************************
     *      DATABASE CONNECTION
     * ***********************************************************************/
    
    @Override
    public String getDbDriver() {
        return properties.get("dbDriver").toString();
    }

    @Override
    public String getDbUrl() {
       // return properties.get("dbUrlLAN").toString();  // Development and Maintenance code
        return properties.get("dbUrl").toString();       // Commercial/Deployed code
    }

    @Override
    public String getUserName() {
        return properties.get("user").toString();
    }

    @Override
    public String getPassword() {
        return properties.get("password").toString();
    }
    
    /*************************************************************************
     *      TABLES
     * ***********************************************************************/
    

    @Override
    public String getRequestsTable() {
        return properties.get("requestsTable").toString();
    }

    @Override
    public String getFulltimeTable() {
        return properties.get("fulltimeTable").toString();
    }

    @Override
    public String getReservesTable() {
        return properties.get("reservesTable").toString();
    }
    
    /*************************************************************************
     *      USER IDs
     * ***********************************************************************/

    /**
     * As per getReserveID(int table) but forces the table selection to reserves table
     * @return The column name of reserve employee ID in reserves table
     */
    @Override
    public String getReserveID() {
        return getReserveID(RESERVE_TABLE);
    }

    /**
     * Returns the name of ID column dependant on which table is being accessed
     * @param table Use DBPropertiesBean static fields REQUESTS_TABLE or RESERVE_TABLE
     * @return The column name of reserve employee ID in the given table
     */
    @Override
    public String getReserveID(int table) {
        switch(table){
            case(REQUESTS_TABLE) : {
                return properties.get("reserveIDinRequestTable").toString();
            }
            default : {return properties.get("reserveID").toString();}
        }
    }    
    
    /**
     * As per getFulltimeID(int table) but forces the table selection to fulltime table
     * @return The column name of fulltime employee in full time table
     */
    @Override
    public String getFulltimeID() {
        return getFulltimeID(FULLTIME_TABLE);
    }

    /**
     * Returns the name of ID column dependant on which table is being accessed
     * @param table Use DBPropertiesBean static fields REQUESTS_TABLE or FULLTIME_TABLE
     * @return The column name of fulltime employee ID in the given table
     */
    @Override
    public String getFulltimeID(int table) {
        switch(table){
            case(REQUESTS_TABLE) : {
                return properties.get("fulltimeIDinRequestsTable").toString();
            }
            default : { return properties.get("fulltimeID").toString();}
        }
    }
    
    @Override
    public String getRequestID() {
        return properties.get("requestID").toString();
    }
    
    /*************************************************************************
     *      NAMES
     * ***********************************************************************/
    
    @Override
    public String getReserveFirstName() {
        return properties.get("reserveFirstName").toString();
    }
    
    @Override
    public String getReserveLastName() {
        return properties.get("reserveLastName").toString();
    }

    @Override
    public String getFulltimeFirstName() {
        return properties.get("fulltimeFirstName").toString();
    }

    @Override
    public String getFulltimeLastName() {
        return properties.get("fulltimeLastName").toString();
    }
    
    
    /*************************************************************************
     *      CONTACT DETAILS
     * ***********************************************************************/
    
        
    @Override
    public String getFulltimeWorkAddress() {
        return properties.get("fulltimeWorkAddress").toString();
    }
    
    @Override
    public String getReservePhone() {
        return properties.get("reservePhone").toString();
    }
    
    /*************************************************************************
     *     FULLTIME MISC
     * ***********************************************************************/
    

    @Override
    public String getManagerFlag() {
        return properties.get("isManager").toString();
    }

    @Override
    public String getFulltimeStartTime() {
        return properties.get("fulltimeStartTime").toString();
    }

    @Override
    public String getFulltimeEndTime() {
        return properties.get("fulltimeEndTime").toString();
    }

    @Override
    public String getRegion() {
        return properties.get("region").toString();
    }
    
    
    /*************************************************************************
     *     RESERVE MISC
     * ***********************************************************************/
    
    @Override
    public String available() {
        return properties.get("available").toString();
    } 

    @Override
    public String getReserveLicenceExpiry() {
        return properties.get("reserveLicenceExpiry").toString();
    }

    /*************************************************************************
     *     REQUESTS MISC
     * ***********************************************************************/
    
    @Override
    public String isPending() {
        return properties.get("pending").toString();
    }

    @Override
    public String isAccepted() {
        return properties.get("accepted").toString();
    }

    @Override
    public String getDayOfWeek() {
        return properties.get("dayOfWeek").toString();
    }
}
