package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controller class for Main Screen
 *
 * @author Ella Upchurch
 */

public class MainScreenController implements Initializable {

    private static Part partToModify;
    private static Product productToModify;

    @FXML
    private TextField partSearchField;
    @FXML
    private TextField productSearchField;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**
     * Gets part to modify
     * @return part to modify
     */
    public static Part getPartToModify(){return partToModify;}

    /**
     * Gets product to modify
     * @return product to modify
     */
    public static Product getProductToModify(){return productToModify;}

    /**
     * Exits the Application
     *
     * @param actionEvent exit button click
     */
    @FXML
    void onExitButtonClick(ActionEvent actionEvent) {

        System.exit(0);
    }

    /**
     * Searches for parts based on values entered into search field
     * Refreshes Parts Table showing only search results
     *
     * Parts can be searched by id and name (regardless of capitalization)
     * When the search field is left blank, the table shows all parts
     *
     * @param actionEvent part search field used
     */
    @FXML
    void onPartSearch(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchField.getText().toLowerCase(); // Convert search string to lowercase

        for (Part part : allParts) {
            // Convert attributes to lowercase for case-insensitive comparison
            String partIdLower = String.valueOf(part.getId()).toLowerCase();
            String partNameLower = part.getName().toLowerCase();

            if (partIdLower.contains(searchString) || partNameLower.contains(searchString)) {
                partsFound.add(part);
            }
        }

        partsTable.setItems(partsFound);

        if (partsFound.isEmpty()) {
            displayAlert(3);
        }
    }

    /**
     * Deletes selected part from Part Table
     *
     * Displays an error message when no product is selected.
     * Displays confirmation dialog before deleting product
     *
     * @param actionEvent delete part button click
     */
    @FXML
    void onDeletePartButtonClick(ActionEvent actionEvent) {

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            displayAlert(1);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected part? This action cannot be undone.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * Takes user to Modify Part Screen.
     *
     * Displays error if no part is selected
     *
     * @param actionEvent modify button click
     */
    @FXML
    void onModifyPartButtonClick(ActionEvent actionEvent) throws IOException {

        partToModify = partsTable.getSelectionModel().getSelectedItem();
        if (partToModify == null) {
            displayAlert(1);
        }
        else {
            Parent parent = FXMLLoader.load(getClass().getResource("/c482/main/ModifyPartScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * Takes user to Add Part Screen
     *
     * @param actionEvent add part button click
     */
    @FXML
    void onAddPartButtonClick(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/c482/main/AddPartScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Searches for products based on values entered into search field & refreshes Products Table showing only search results
     *
     * Products can be searched by id and name (regardless of capitalization)
     * When the search field is left blank, the table shows all products
     *
     * @param actionEvent product search field used
     */
    @FXML
    void onProductSearch(ActionEvent actionEvent) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = productSearchField.getText().toLowerCase(); // Convert search string to lowercase

        for (Product product : allProducts) {
            // Convert attributes to lowercase for case-insensitive comparison
            String partIdLower = String.valueOf(product.getId()).toLowerCase();
            String partNameLower = product.getName().toLowerCase();

            if (partIdLower.contains(searchString) || partNameLower.contains(searchString)) {
                productsFound.add(product);
            }
        }

        productsTable.setItems(productsFound);

        if (productsFound.isEmpty()) {
            displayAlert(3);
        }
    }
    /**
     * Deletes selected product in the product table.
     *
     * Displays an error message if no product is selected
     * Displays confirmation before deleting the selected product.
     * Prevents user from deleting a product with one or more associated parts.
     *
     * @param actionEvent delete product button click
     */
    @FXML
    void onDeleteProductButtonClick(ActionEvent actionEvent) {

        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            displayAlert(2);
        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected product? This action cannot be undone.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();

                if (assocParts.size() >= 1) {
                    displayAlert(5);
                }
                else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    /**
     * Takes user to Modify Product Screen
     *
     * Displays error if no product is selected
     *
     * @param actionEvent modify product button click
     */
    @FXML
    void onModifyProductButtonClick(ActionEvent actionEvent) throws IOException{

        productToModify = productsTable.getSelectionModel().getSelectedItem();
        if (productToModify == null) {
            displayAlert(1);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("/c482/main/ModifyProductScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Takes user to Add Product Screen
     *
     * @param actionEvent add product button click
     */
    @FXML
    void onAddProductButtonClick(ActionEvent actionEvent) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/c482/main/AddProductScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     *Displays various alerts
     */
    private void displayAlert(int alertType){
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Please select a part");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Please select a product");
                alert.showAndWait();
                break;
            case 3:
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText("Part not found");
                infoAlert.showAndWait();
                break;
            case 4:
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText("Product not found");
                infoAlert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("All associated parts must be removed from product before deletion");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Initializes controller; populates Part and Product Table data
     *
     * @param resourceBundle
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partsTable.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}