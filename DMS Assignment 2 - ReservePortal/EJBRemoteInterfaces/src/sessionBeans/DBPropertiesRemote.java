/**
 * AUT DMS S1 2016
 * Assignment 2: - Multitier Web Application
 *  Prouting, Sez (0308852) and Shaw, Aziel (14847095)
 * 
 *  Interface for the singleton EJB: DBPropertiesBean, which is used to translate
 * DB attributes.
 * 
 * @author Sez Prouting
 */
package sessionBeans;

import javax.ejb.Remote;

@Remote
public interface DBPropertiesRemote {
    
    public static final int REQUESTS_TABLE=1, RESERVE_TABLE=2, FULLTIME_TABLE=3;

    String getDbDriver();

    String getDbUrl();

    String getUserName();

    String getPassword();

    String getRequestsTable();

    String getFulltimeTable();

    String getReservesTable();

    String getReserveID();
    
    String getReserveID(int table);

    String getFulltimeID();

    String getFulltimeID(int table);

    String getManagerFlag();

    String available();

    String getReserveFirstName();

    String getReserveLastName();

    String getFulltimeFirstName();

    String getFulltimeLastName();

    String getFulltimeWorkAddress();

    String getFulltimeStartTime();

    String getFulltimeEndTime();

    String getRegion();

    String getReservePhone();

    String getReserveLicenceExpiry();

    String getRequestID();

    String isPending();

    String isAccepted();

    String getDayOfWeek();
}
