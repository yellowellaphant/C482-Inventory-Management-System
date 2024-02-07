package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates the model 'Product'.
 * Products can contain associated Parts.
 *
 * @author Ella Upchurch
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Constructs a new instance of a product
     *
     * @param id ID of product
     * @param name  name of product
     * @param price price of product
     * @param stock inventory level of product
     * @param min minimum of product
     * @param max maximum of product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** The getter for product ID
     * @return id of product
     */
    public int getId() {return id;}

    /** The setter for product ID
     * @param id of product
     */
    public void setId(int id) {this.id = id;}

    /** The getter for product name
     * @return name of product
     */
    public String getName() {return name;}

    /** The setter for product name
     * @param name
     */
    public void setName(String name) {this.name = name;}

    /** The getter for product price
     * @return price of product
     */
    public double getPrice() {return price;}

    /** The setter for product price
     * @param price
     */
    public void setPrice(double price) {this.price = price;}

    /** The getter for product stock
     * @return stock of product
     */
    public int getStock() {return stock;}

    /** The setter for product stock
     * @param stock
     */
    public void setStock(int stock) {this.stock = stock;}

    /** The getter for product min
     * @return min of product
     */
    public int getMin() {return min;}

    /** The setter for product min
     * @param min
     */
    public void setMin(int min) {this.min = min;}

    /** The getter for product max
     * @return max of product
     */
    public int getMax() {return max;}

    /** The setter for product max
     * @param max
     */
    public void setMax(int max) {this.max = max;}

    /** Creates an observable list of associated parts
     * @param part
     */
    public void  addAssociatedPart(Part part) {associatedParts.add(part);}

    /** Removes associated part from parts list of product
     *
     * @param selectAssociatedPart
     * @return boolean that indicates if part was deleted successfully
     */
    public boolean deleteAssociatedPart(Part selectAssociatedPart) {
        if (associatedParts.contains(selectAssociatedPart)) {
            associatedParts.remove(selectAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /** Gets list of parts associated with product
     * @return list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}

}
