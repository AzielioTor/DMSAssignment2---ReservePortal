/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 * Takes place of DOA to handle database access for ReservePortal servlets
 * 
 *  @author Sez Prouting
 */
package sessionBeans;

import employees.ReserveEmployee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import results.Request;


@Stateless
public class DBAccessorBean implements DBAccessorRemote {

    @EJB
    private DBPropertiesRemote properties;
    private final String TRUE="1", FALSE="0";
    
    private Connection connection;
    private PreparedStatement   checkReserveEmployee, 
                                checkFulltimeEmployee, 
                                getRequestsAssignedToReservist,
                                getRequests,
                                getLatestRequestByFTid,
                                getAcceptedRequestByReserveID,
                                getRequestByRequestID,
                                getRequestID,
                                getAvailableReserves,   // note this statement is prepared in local method
                                getIsAvailableStatus,
                                getReserveFullName,
                                getFulltimeFullName,
                                getFTRegion,
                                getAcceptedRequest;
    
    
    public DBAccessorBean(){
        
        // refer init() for bean initialisation
    }
    
    /**
     * Makes initial database connection and prepares SQL statements
     */
    @PostConstruct
    public void init(){
            
        // DB CONNECTION
        try{
            Class.forName(properties.getDbDriver());
        }
        catch(ClassNotFoundException e){
            System.err.println("---------------> DB Driver class not found: " + e.getMessage());
        }
        
        try{
            connection = DriverManager.getConnection(properties.getDbUrl(), properties.getUserName(), properties.getPassword());
        
        //Prepare Statements
            
            checkReserveEmployee = connection.prepareStatement( "SELECT * FROM " + properties.getReservesTable() +
                                                                " WHERE " + properties.getReserveID() +
                                                                " = ?"); 
            
            checkFulltimeEmployee = connection.prepareStatement("SELECT * FROM " + properties.getFulltimeTable()+
                                                                " WHERE " + properties.getFulltimeID() +
                                                                " = ?"); 

            getRequestsAssignedToReservist = connection.prepareStatement("SELECT " +properties.getRequestID()+ ", "
                                                                +properties.getFulltimeFirstName()+ ", " 
                                                                +properties.getFulltimeLastName()+ ", "
                                                                +properties.getFulltimeStartTime()+ ", "
                                                                +properties.getFulltimeEndTime()+ ", "
                                                                +properties.getFulltimeWorkAddress()+ 
                                                       " FROM " +properties.getFulltimeTable()+ ", " +properties.getRequestsTable()+
                                                      " WHERE " +properties.getReserveID(DBPropertiesRemote.REQUESTS_TABLE)+ 
                                                          "= ?" +
                                                        " AND " +properties.getFulltimeID(DBPropertiesRemote.FULLTIME_TABLE) + "=" 
                                                                +properties.getFulltimeID(DBPropertiesRemote.REQUESTS_TABLE) +
                                                        " AND " +properties.isPending()+ " = " + TRUE);
            
            getRequests = connection.prepareStatement("SELECT "  +properties.getRequestID()+ ", "
                                                                        +properties.getFulltimeFirstName()+ ", "
                                                                        +properties.getFulltimeLastName()+ ", "
                                                                        +properties.getReserveFirstName()+ ", "
                                                                        +properties.getReserveLastName()+ ", "
                                                                        +properties.getFulltimeWorkAddress()+ ", "
                                                                        +properties.getFulltimeStartTime()+ ", "
                                                                        +properties.getFulltimeEndTime()+
                                                               " FROM " +properties.getRequestsTable()+ ", "
                                                                        +properties.getReservesTable()+ ", "
                                                                        +properties.getFulltimeTable()+
                                                              " WHERE " +properties.getFulltimeID(DBPropertiesRemote.FULLTIME_TABLE) + "="
                                                                        +properties.getFulltimeID(DBPropertiesRemote.REQUESTS_TABLE) +
                                                                " AND " +properties.getReserveID(DBPropertiesRemote.RESERVE_TABLE) + "="
                                                                        +properties.getReserveID(DBPropertiesRemote.REQUESTS_TABLE) +
                                                                " AND " +properties.isPending() + "=?" + 
                                                                " AND " +properties.isAccepted() + "=?");
            
            getLatestRequestByFTid = connection.prepareStatement("SELECT MAX(" +properties.getRequestID() +
                                                                ") FROM "+properties.getRequestsTable() +
                                                              " WHERE " +properties.getFulltimeID(DBPropertiesRemote.REQUESTS_TABLE) +
                                                                "=?");
            
            getAcceptedRequestByReserveID = connection.prepareStatement("SELECT MAX(" +properties.getRequestID() +
                                                                            ") FROM " +properties.getRequestsTable() +
                                                                            " WHERE " +properties.getReserveID(DBPropertiesRemote.REQUESTS_TABLE) + "=?" +
                                                                              " AND " +properties.isPending() + "=" + FALSE +
                                                                              " AND " +properties.isAccepted() + "=" + TRUE);
            
            getRequestByRequestID = connection.prepareStatement("SELECT " +properties.getReserveID(DBPropertiesRemote.REQUESTS_TABLE) + ", "
                                                                        +properties.getFulltimeID(DBPropertiesRemote.REQUESTS_TABLE) + ", "
                                                                        +properties.isPending() + ", " 
                                                                        +properties.isAccepted() + ", "
                                                                        +properties.getDayOfWeek() + ", "
                                                                        +properties.getFulltimeFirstName() + ", "
                                                                        +properties.getFulltimeLastName() + ", "
                                                                        +properties.getReserveFirstName()+ ", "
                                                                        +properties.getReserveLastName()+ ", "
                                                                        +properties.getReservePhone() + ", "
                                                                        +properties.getReserveLicenceExpiry() + ", "
                                                                        +properties.getFulltimeWorkAddress() + ", "
                                                                        +properties.getFulltimeStartTime() + ", "
                                                                        +properties.getFulltimeEndTime() +
                                                               " FROM " +properties.getRequestsTable()+ ", "
                                                                        +properties.getReservesTable() + ", "
                                                                        +properties.getFulltimeTable()+
                                                              " WHERE " +properties.getRequestID() +
                                                              "=? AND " +properties.getReserveID(DBPropertiesRemote.RESERVE_TABLE) + "="
                                                                        +properties.getReserveID(DBPropertiesRemote.REQUESTS_TABLE) +
                                                                " AND " +properties.getFulltimeID(DBPropertiesRemote.FULLTIME_TABLE) + "="
                                                                        +properties.getFulltimeID(DBPropertiesRemote.REQUESTS_TABLE));
            
            getRequestID = connection.prepareStatement("SELECT " +properties.getRequestID() +
                                                        " FROM " +properties.getRequestsTable() +
                                                       " WHERE " +properties.getFulltimeID(DBPropertiesRemote.REQUESTS_TABLE) + "=?" +
                                                        " AND " +properties.getReserveID(DBPropertiesRemote.REQUESTS_TABLE) + "=?");
             
            getIsAvailableStatus = connection.prepareStatement( "SELECT " + properties.available() + 
                                                                " FROM "  + properties.getReservesTable() + 
                                                                " WHERE " + properties.getReserveID() +
                                                                " = ?");
            
            getReserveFullName = connection.prepareStatement("SELECT "  +properties.getReserveFirstName()+ ", "
                                                                        + properties.getReserveLastName()+ 
                                                               " FROM " +properties.getReservesTable()+
                                                              " WHERE " +properties.getReserveID()+ 
                                                                "= ?");
            
            getFulltimeFullName = connection.prepareStatement("SELECT " +properties.getFulltimeFirstName()+ ", "
                                                                        + properties.getFulltimeLastName()+ 
                                                               " FROM " +properties.getFulltimeTable()+
                                                              " WHERE " +properties.getFulltimeID()+ "= ?");
            
            getFTRegion = connection.prepareStatement("SELECT " +properties.getRegion()+ 
                                                       " FROM " +properties.getFulltimeTable()+
                                                      " WHERE " +properties.getFulltimeID()+ 
                                                        "= ?");
            
         // getAvailableReserves is prepared in getAvailableReserves() method, due to variables injected into SQL

        }
        catch(SQLException e){
            System.out.println("------------>  Error during DBAccessor statement preparation: " + e.getMessage());
        }
    }

    
    //Determines the employment type of an employee. (Refer interface javadocs)
    @Override
    public int getEmployeeType(int id) throws SQLException {
       ResultSet result;
        
        synchronized(this){
            // check full timers first
            checkFulltimeEmployee.setInt(1, id);
            result = checkFulltimeEmployee.executeQuery();
            
            //if found in fulltime table, check if manager
            if(result.next()){
               boolean isManager =  result.getBoolean(properties.getManagerFlag());
               if(isManager) return MANAGER;
               else return FULLTIME_EMPLOYEE;
            }
            
            // if not in the fulltime table, check if reserve
            checkReserveEmployee.setInt(1, id);
            result = checkReserveEmployee.executeQuery();
            if(result.next()) 
                return RESERVE_EMPLOYEE;
            
            return NOT_FOUND;  
        }
    }
    
    
    //Gathers all pending results assigned to a reserve employee. (Refer interface javadocs)
    @Override
    public ArrayList<Request> getRequests(int id) throws SQLException {
        
        if(getEmployeeType(id) != NOT_FOUND){
            synchronized(this){
                ResultSet results;
                ArrayList<Request> table = new ArrayList<>();
                        
                getRequestsAssignedToReservist.setInt(1, id);
                results = getRequestsAssignedToReservist.executeQuery();
                            
                if(results != null){
                    Request temp;

                    // Creates a Request bean for each result, storing the relevant work detail
                     //for display to the reservist.
                    while(results.next()){
                        temp = new Request();
                        temp.setRequestID(results.getInt(properties.getRequestID()));
                        temp.setFulltimeFirstName(results.getString(properties.getFulltimeFirstName()));
                        temp.setFulltimeLastName(results.getString(properties.getFulltimeLastName()));
                        temp.setShiftBegins(results.getString(properties.getFulltimeStartTime()));
                        temp.setShiftEnds(results.getString(properties.getFulltimeEndTime()));
                        temp.setAddress(results.getString(properties.getFulltimeWorkAddress()));
                        table.add(temp);
                    }
                }
                else System.out.println("---> getRequests() returned no results");  // for server log & sysadmin
                return table;
            }
        }
        return null;
    }
    
    
    //Fetches the most recent request made by a fulltime employee. (Refer interface javadocs)
    @Override
    public Request getFTCurrentRequest(int id) throws SQLException{
        int requestID;
        
        synchronized (this){
            
            //The most current request is the one most recently entered into the DB therefore with the highest requestID
            getLatestRequestByFTid.setInt(1,id);   // fetches max requestID out of each request made by this employee
            ResultSet latestRequest = getLatestRequestByFTid.executeQuery();
            
            if(latestRequest.next()){
                requestID = latestRequest.getInt(1); // only expect one result
                return getRequestByRequestID(requestID);
            }
        }
        return null;  
    }

    
    //Fetches a single request as determined by the request table's primary key. (Refer interface javadocs)
    @Override
    public Request getRequestByRequestID(int requestID) throws SQLException {
        Request tempRequest;
        
        synchronized(this){
            // Use the requestID to fetch details & populate the Request object
            getRequestByRequestID.setInt(1, requestID);
            ResultSet results = getRequestByRequestID.executeQuery();

            if(results.next()){
                tempRequest = new Request(   results.getInt(properties.getReserveID(DBPropertiesRemote.REQUESTS_TABLE)),
                                                results.getInt(properties.getFulltimeID(DBPropertiesRemote.REQUESTS_TABLE)),
                                                results.getBoolean(properties.isPending()), 
                                                results.getString(properties.getDayOfWeek()));
                tempRequest.setAccepted(results.getBoolean(properties.isAccepted()));
                tempRequest.setRequestID(requestID);
                tempRequest.setFulltimeFirstName(results.getString(properties.getFulltimeFirstName()));
                tempRequest.setFulltimeLastName(results.getString(properties.getFulltimeLastName()));
                tempRequest.setAddress(results.getString(properties.getFulltimeWorkAddress()));
                tempRequest.setShiftBegins(results.getString(properties.getFulltimeStartTime()));
                tempRequest.setShiftEnds(results.getString(properties.getFulltimeEndTime()));                
                tempRequest.setReserveFirstName(results.getString(properties.getReserveFirstName()));
                tempRequest.setReserveLastName(results.getString(properties.getReserveLastName()));
                tempRequest.setPhone(results.getInt(properties.getReservePhone()));
                tempRequest.setLicenceExpiry(results.getDate(properties.getReserveLicenceExpiry())); 

                return tempRequest;
            }
        }
        return null;   //DB holds no requests that were made by this employee
    }
    
    
    //Gathers all requests which are pending. (Refer interface javadocs)
    @Override
    public ArrayList<Request> getPendingRequests() throws SQLException {
        
        synchronized(this){
            getRequests.setBoolean(1, true);    //sets isPending value
            getRequests.setBoolean(2, false);   //sets isAccepted value
        }
        return executeGetRequests();
    }
    
    
    //Gathers requests which have been set to accepted. (Refer interface javadocs)
    @Override
    public ArrayList<Request> getAcceptedRequests() throws SQLException {
        
        synchronized(this){
            getRequests.setBoolean(1, false);   //sets isPending value
            getRequests.setBoolean(2, true);    //sets isAccepted value
            return executeGetRequests();
        }
    }
    
    
    //Gathers requests which have been set to rejected. (Refer interface javadocs)
    @Override
    public ArrayList<Request> getRejectedRequests() throws SQLException {
        synchronized(this){
            getRequests.setBoolean(1, false);    //sets isPending value
            getRequests.setBoolean(2, false);    //sets isAccepted value
            return executeGetRequests();
        }
    }
    
   // Fetches the most recent job accepted by the reservist (Refer interface javadocs)
   @Override
    public Request getCurrentReserveJob(int empID) throws SQLException {
        synchronized(this){
            
            getAcceptedRequestByReserveID.setInt(1, empID); // gets most recent request with accepted status
            ResultSet requests = getAcceptedRequestByReserveID.executeQuery();
            
            if(requests.next()){
                int requestID = requests.getInt(1);
                return getRequestByRequestID(requestID);
            }
        }
        return null;
    }
   
    /**
     * Local method to run the getRequests statement. Note that getRequests is not set by this method,
     * it is required that getRequests be set before this method is called.
     * @return ArrayList of Requests which represent accepted, pending or rejected results depending
     * on the parameters previously set by other methods in this class
     * @throws SQLException 
     */
    private ArrayList<Request> executeGetRequests() throws SQLException{
        
        ArrayList<Request> requests = new ArrayList<>();
        Request temp;
        
        synchronized(this){
            ResultSet results = getRequests.executeQuery();
            while(results.next()){
                temp = new Request();
                temp.setRequestID(results.getInt(properties.getRequestID()));
                temp.setFulltimeFirstName(results.getString(properties.getFulltimeFirstName()));
                temp.setFulltimeLastName(results.getString(properties.getFulltimeLastName()));
                temp.setReserveFirstName(results.getString(properties.getReserveFirstName()));
                temp.setReserveLastName(results.getString(properties.getReserveLastName()));
                temp.setAddress(results.getString(properties.getFulltimeWorkAddress()));
                temp.setShiftBegins(results.getString(properties.getFulltimeStartTime()));
                temp.setShiftEnds(results.getString(properties.getFulltimeEndTime()));
                requests.add(temp);
            }
            return requests;
        }
    }
    
    
    //Finds the primary key of a request specific to a fulltime (requestor) employee and a 
    // reserve (requestee) employee. (Refer interface javadocs)
    @Override
    public int getRequestID(int fulltimeID, int reserveID) throws SQLException {
        
        getRequestID.setInt(1, fulltimeID);
        getRequestID.setInt(2, reserveID);
        ResultSet idSet = getRequestID.executeQuery();
        
        /**
         * There should only be one result. Once an employee has made a request, that request
         * must be deleted from the request table before the same employee can make another.
         */
        if(idSet.next())    
            return idSet.getInt(properties.getRequestID());
        else return NOT_FOUND;
    }
    
    
    //Gathers all available reserves according to the workplace-region, reserve availability, day-of-week and if 
     //licence is current. (Refer interface javadocs)
    @Override
    public ArrayList<ReserveEmployee> getAvailableReserves(int id) throws SQLException {
        
        if(getEmployeeType(id) != NOT_FOUND){
            synchronized(this){
                ResultSet reserves;
                String region = getRegion(id).toUpperCase();  // to check which reserves will work in the same region as the 
                                                            // fulltime (requester) work address
               // to check reserves which will work on "this" day (today)
                Format formatter = new SimpleDateFormat("EEEE"); 
                String dayOfWeek = formatter.format(new Date()).toUpperCase();
            
                // DB query depends on the region in which the fulltime (reqester) work address lies,
                    // and the day-of-week for which a replacement is requested.
                getAvailableReserves = connection.prepareStatement("SELECT " +properties.getReserveID() +", "
                                                                            +properties.getReserveFirstName() +", "
                                                                            +properties.getReserveLastName() + ", "
                                                                            +properties.getReservePhone() + ", " 
                                                                            +properties.getReserveLicenceExpiry()+ 
                                                                   " FROM " +properties.getReservesTable() +
                                                                   " WHERE " + region + "=" + TRUE +
                                                                    " AND " + dayOfWeek + "=" + TRUE +
                                                                    " AND " +properties.getReserveLicenceExpiry()+ ">CURDATE()" +
                                                                    " AND " +properties.available() + "=" + TRUE);
                reserves = getAvailableReserves.executeQuery();
                if(reserves == null) System.out.println("---> getAvailableReserves() found no reserves");  // for server log & sysadmin

                ArrayList<ReserveEmployee> reservesList = new ArrayList<>();
                ReserveEmployee temp; 

                while(reserves.next()){
                     temp = new ReserveEmployee(reserves.getInt(properties.getReserveID()));
                     temp.setFirstName(reserves.getString(properties.getReserveFirstName()));
                     temp.setLastName(reserves.getString(properties.getReserveLastName()));
                     temp.setPhone(reserves.getInt(properties.getReservePhone()));
                     temp.setLicenceExpiry(reserves.getDate(properties.getReserveLicenceExpiry()));
                     reservesList.add(temp);
                }
                return reservesList;
            }
        }
        return null;
    }  
    
    // Discover the region in which a fulltime employee's normal place of work is located. (Refer interface javadocs)
    @Override
    public String getRegion(int id) throws SQLException {
        String region = "";
        synchronized(this){
            getFTRegion.setInt(1, id);
            ResultSet regions = getFTRegion.executeQuery();
            if(regions.next())
                region = regions.getString(properties.getRegion());
            return region;
        }
    }

    
    //Used to establish if an employee is of the type DBAccessorRemote.MANAGER.  (Refer interface javadocs)
    @Override
    public boolean isManager(int id) throws SQLException {
        return (getEmployeeType(id) == MANAGER);
    }

    
    //Used to determine if an employee is of the type DBAccessorRemote.RESERVE_EMPLOYEE. (Refer interface javadocs)
    @Override
    public boolean isReserve(int id) throws SQLException {
        return (getEmployeeType(id) == RESERVE_EMPLOYEE);

    }

    
    //Used to determine if an employee is of the type DBAccessorRemote.FULLTIME_EMPLOYEE, but is not a DBAccessorRemote.MANAGER (Refer interface javadocs)
    @Override
    public boolean isFulltime(int id) throws SQLException {
        return (getEmployeeType(id) == FULLTIME_EMPLOYEE);
    }
    
    
    //Assesses whether a reserve employee has a status set to available. Only checks employees
     // of type DBAccessorRemote.RESERVE_EMPLOYEE - all other employees will return a false value. (Refer interface javadocs)
    @Override
    public boolean isAvailable(int id) throws SQLException {
        
        // method is for reserves only
        if(isReserve(id)){
            ResultSet result;
            
            synchronized(this){
                getIsAvailableStatus.setInt(1, id);
                result = getIsAvailableStatus.executeQuery();
                // return available status
                if(result.next())
                    return result.getBoolean(properties.available());
            }
            
        }
        return false;
    }
    
    
    //Provides the first and last names of a given employee. (Refer interface javadocs)
    @Override
    public String getFullName(int id) throws SQLException {
        
        synchronized (this){
            String fullName="Name not found";
            PreparedStatement stmt;
            String fName, lName;

            // change DB query values according to type of employee
            int empType = getEmployeeType(id);

            switch(empType){

                case RESERVE_EMPLOYEE : {
                    stmt = getReserveFullName;
                    fName = properties.getReserveFirstName();
                    lName = properties.getReserveLastName();
                    break;
                }

                case FULLTIME_EMPLOYEE : {
                    stmt = getFulltimeFullName;
                    fName = properties.getFulltimeFirstName();
                    lName = properties.getFulltimeLastName();
                    break;
                }
                case MANAGER : {
                    stmt = getFulltimeFullName;
                    fName = properties.getFulltimeFirstName();
                    lName = properties.getFulltimeLastName();
                    break;
                }
                default : {return fullName;} // was initially set to "Name not found"
            }

            // set & execute DB query
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if(result.next()){
                fullName = result.getString(fName) + " " + result.getString(lName);
            }
            return fullName;   
        }
    }   
    
    
    //Closes all statements when the bean is destroyed.
    @Override
    public void destroy() {
        try{
            if(getRequestsAssignedToReservist != null) getRequestsAssignedToReservist.close();
            if(getRequests != null) getRequests.close();
            if(checkFulltimeEmployee != null) checkFulltimeEmployee.close();
            if(checkReserveEmployee != null) checkReserveEmployee.close();
            if(getIsAvailableStatus != null) getIsAvailableStatus.close();
            if(getReserveFullName != null) getReserveFullName.close();
            if(getFulltimeFullName != null) getFulltimeFullName.close();
            if(getFTRegion != null) getFTRegion.close();
            if(getAvailableReserves != null) getAvailableReserves.close();
            if(getLatestRequestByFTid != null) getLatestRequestByFTid.close();
            if(getRequestByRequestID != null) getRequestByRequestID.close();
            if(getRequestID != null) getRequestID.close();
        }
        catch(SQLException e){
            System.err.println("Issue closing DB Access Statements: " + e.getMessage());
        }
    }
}
