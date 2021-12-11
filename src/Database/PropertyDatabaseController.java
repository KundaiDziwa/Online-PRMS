/**
 *@author Kundai Dziwa <a href="mailto:kundai.dziwa@ucalgary.ca">
 *         kundai.dziwa@ucalgary.ca</a>
 *
 *@author Tommy Dinh <a href="mailto:tommy.dinh@ucalgary.ca">
 *         tommy.dinh@ucalgary.ca</a>
 * 
 *@author Tien Dat Johny Do <ahref ="tiendat.do@ucalgary.ca">
 *        tiendat.do@ucalgary.ca</a>
 * 
 *@author Stalin D Cunha<a href="mailto:stalin.dcunha@ucalgary.ca">
 *         stalin.dcunha@ucalgary.ca</a>
 * 
 * @version 1.1
 * @since 1.0
 */

package Database;

import Domain.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PropertyDatabaseController {
    private SQLConnection db; 

    // Constructor
    public PropertyDatabaseController() {
        db = new SQLConnection(); // Creating a SQL connection
    }

    // Creating the database
    public boolean checkdb(String dbs) {
        boolean dbExists = false;

        db = new SQLConnection(dbs);
        db.initializeConnection();  
        try {
            // Create a select query to get all of the corresponding properties matching criteria.
            String search = "SHOW DATABASES;";
            Statement myStmt = db.getConnection().createStatement();
            ResultSet results = myStmt.executeQuery(search);
            
            while (results.next()) {
                if (results.getString(1).equals("RPMSdb")) {
                    dbExists = true;
                    break;
                }
            }
            db.closeConn(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbExists;
    }
    public void createRPMSdb(String dbs) {
        db = new SQLConnection(dbs);
        db.initializeConnection();

        String s = new String();
        StringBuffer sb = new StringBuffer();
 
        try
        {
            FileReader fr = new FileReader(new File("rpmsDB.sql"));
            // be sure to not have line starting with "--" or "/*" or any other non aplhabetical character
 
            BufferedReader br = new BufferedReader(fr);
 
            while((s = br.readLine()) != null)
            {
                sb.append(s);
            }
            br.close();
 
            // We use ";" as a delimiter for each sql query.
            String[] inst = sb.toString().split(";");
            Statement st = db.getConnection().createStatement();
 
            for(int i = 0; i<inst.length; i++)
            {
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if(!inst[i].trim().equals(""))
                {
                    st.executeUpdate(inst[i]);
                }
            }
            db.closeConn();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Method Functions
    public ArrayList<Property> performSearch(String[] ht, int bathMin, int bathMax, int bedMin, int bedMax, String[] furnished, String[] cityQ, double pLow, double pHigh) {
        ArrayList<Property> properties = new ArrayList<Property>();
        String hTypes = "";
        String furnish = "";
        String cityQuad = "";
        String bathMaximum = " AND bedrooms<=" + Integer.toString(bedMax);
        String bedMaximum = " AND bathrooms<=" + Integer.toString(bathMax);
        String priceMax = " AND price<=" + Double.toString(pHigh);

        // Add collect and add all house type search criteria data to a string
        for (int i = 0; i < ht.length; i++) {
            hTypes += "p_type='";
            hTypes += ht[i];
            if (i+1 == ht.length) {
                hTypes += "' AND ";
            } else {
                hTypes += "' OR ";
            }
        }
        // Add collect and add furnished search criteria data to a string
        for (int i = 0; i < furnished.length; i++) {
            furnish += "furnished='";
            furnish += furnished[i];
            if (i+1 == furnished.length) {
                furnish += "' AND ";
            } else {
                furnish += "' OR ";
            }
        }
        // Add collect and add city quadrant search criteria data to a string
        for (int i = 0; i < cityQ.length; i++) {
            cityQuad += "city_quadrant='";
            cityQuad += cityQ[i];
            if (i+1 == cityQ.length) {
                cityQuad += "' AND ";
            } else {
                cityQuad += "' OR ";
            }
        }
        // User want all the values above the minimum
        if (bathMax == -1) bathMaximum = "";
        if (bedMax == -1) bedMaximum = "";
        if (pHigh == -1) priceMax = "";

        db.initializeConnection();  
        try {
            // Create a select query to get all of the corresponding properties matching criteria.
            String search = "SELECT * FROM Properties WHERE " 
            + hTypes + "bedrooms>=" + Integer.toString(bedMin) + bedMaximum + " AND bathrooms>=" + Integer.toString(bathMin) + bathMaximum + " AND " + furnish + cityQuad + "state_of_listing='Active' AND "
            + "price>=" + Double.toString(pLow) + priceMax + ";";

            Statement myStmt = db.getConnection().createStatement();
            ResultSet results = myStmt.executeQuery(search);
            
            while (results.next()) {
                // Filter through matching properties and make multiple properties, then add them to the ArrayList.
                int idNum = Integer.parseInt(results.getString(1));
                Property prop = new Property(results.getString("address"), results.getString("p_type"), Integer.parseInt(results.getString("bathrooms")), Integer.parseInt(results.getString("bedrooms")), results.getString("furnished"), results.getString("city_quadrant"), Double.parseDouble(results.getString("price")));
                prop.setSOL(results.getString("state_of_listing"));
                prop.setID(idNum);
                Landlord l = new Landlord(Integer.parseInt(results.getString("landlord")));
                prop.setLandlord(l);
                properties.add(prop);
            }
            db.closeConn(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return properties; // return all matching properties
    }
    public ArrayList<RegisteredRenter> getRenters() {
        ArrayList<RegisteredRenter> rrs = new ArrayList<RegisteredRenter>();

        db.initializeConnection();  
        try {
            // Select query to retrieve all renters from the database
            String search = "SELECT * FROM Renters;";

            Statement myStmt = db.getConnection().createStatement();
            ResultSet results = myStmt.executeQuery(search);
            
            while (results.next()) {
                // Create a registered renetr class and add it to the array. 
                RegisteredRenter r = new RegisteredRenter(Integer.parseInt(results.getString("rID")));
                rrs.add(r);
            }
            db.closeConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rrs; // Returning an ArrayList of all Registered renters in the database. 
    }
}