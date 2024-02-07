package model;

/**
 * This class creates a part type, outsourced
 *
 * @author Ella Upchurch
 */

public class Outsourced extends Part{

    private String companyName;

    /**
     * Constructs a new instance of an outsourced part
     *
     * @param id ID of part
     * @param name name of part
     * @param price price of part
     * @param stock inventory level of part
     * @param min minimum of part
     * @param max maximum of part
     * @param companyName company name of part
     */
    public Outsourced(int id,String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**The getter for part company name
     * @return company name of part
     */
    public String getCompanyName(){return companyName;}

    /** The setter for part company name
     * @param companyName of part
     */
    public void setCompanyName(String companyName){this.companyName = companyName;}
}

