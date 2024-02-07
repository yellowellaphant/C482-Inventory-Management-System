package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates an inventory that holds parts and products
 *
 * @author Ella Upchurch
 */
public class Inventory {
    //may need to be removed, see UML diagram
    private static int partId = 0;
    private static int productId = 0;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds a part to the inventory
     * @param newPart part to add
     */
    public static void addPart(Part newPart){allParts.add(newPart);}

    /** Adds a product to the inventory
     * @param newProduct product to be added
     */
    public static void addProduct(Product newProduct){allProducts.add(newProduct);}

    /** Creates new part ID
     * @return unique part ID
     */
    public static int getNewPartId(){return ++partId;}

    /** Creates new product ID
     * @return unique product ID
     */
    public static int getNewProductId(){return ++productId;}

    /**
     * Searches for parts by ID
     *
     * @param partID of part
     * @return found parts
     */
    public static Part lookupPart(int partID) {
        Part partFound = null;

        for (Part part : allParts){
            if (part.getId() == partID){
                partFound = part;
            }
        }
        return partFound;
    }

    /**
     * searches for parts by name
     *
     * @param partName of part
     * @return found parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for (Part part : allParts){
            if (part.getName().equals(partName)){
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    /**
     * searches for products by ID
     *
     * @param productId of product
     * @return found products
     */
    public static Product lookupProduct(int productId) {
        Product productFound = null;

        for (Product product : allProducts){
            if (product.getId() == productId){
                productFound = product;
            }
        }
        return productFound;
    }

    /**
     * searches for products by name
     *
     * @param productName of product
     * @return found products
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)){
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * replaces part in inventory
     *
     * @param index of replaced part
     * @param selectedPart replacing part
     */
    public static void updatePart (int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * replaces product in inventory
     * @param index of replaced product
     * @param selectedProduct replacing product
     */
    public static void updateProduct (int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     * removes part from inventory
     *
     * @param selectedPart
     * @return boolean that indicates if delete was successful
     */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * removes product from inventory
     *
     * @param selectedProduct
     * @return boolean that indicates if delete was successful
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gets a list of all parts
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts() {return allParts;}

    /**
     * Gets a list of all products
     * @return list of all productgdfs
     */
    public static ObservableList<Product> getAllProducts(){return allProducts;}



}


