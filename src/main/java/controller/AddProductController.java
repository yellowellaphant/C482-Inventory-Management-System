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
 * Controller class for the Add Product Screen
 *
 * @author Ella Upchurch
 */
public class AddProductController implements Initializable {
    /** creates an observable list of associated parts */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML
    private TableView<Part>addPartTable;
    @FXML
    private TableView<Part> assocPartTable;
    @FXML
    private TextField partSearchField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productStockField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;
    @FXML
    private TableColumn<Part, Integer> addPartIdCol;
    @FXML
    private TableColumn<Part, String> addPartNameCol;
    @FXML
    private TableColumn<Part, Integer> addPartStockCol;
    @FXML
    private TableColumn<Part, Double> addPartPriceCol;
    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;
    @FXML
    private TableColumn<Part, String> assocPartNameCol;
    @FXML
    private TableColumn<Part, Integer> assocPartStockCol;
    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;

    /**
     * Searches for parts based on values entered into search field & refreshes Add Part Table showing only search results
     *
     * Parts can be searched by id and name (regardless of capitalization)
     * When the search field is left blank, the table shows all products
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

        addPartTable.setItems(partsFound);

        if (partsFound.isEmpty()) {
            displayAlert(6);
        }
    }

    /**
     * Associates selected part with the `Product & adds it to product's associated parts table
     *
     * @param actionEvent add part button click
     */
    @FXML
    void onAddPartButtonClick(ActionEvent actionEvent) {

        Part selectedPart = addPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(2);
        }
        else {
            assocParts.add(selectedPart);
            assocPartTable.setItems(assocParts);
        }

    }

    /**
     * Adds new product to inventory and returns to Main Screen
     *
     * Error messages are displayed when invalid inputs are detected, preventing empty or invalid values
     *
     * @param actionEvent save button click
     */
    @FXML
    void onSaveButtonClick(ActionEvent actionEvent) {

        try {
            int id = 0;
            String name = productNameField.getText();
            double price = Double.parseDouble(productPriceField.getText());
            int stock = Integer.parseInt(productStockField.getText());
            int min = Integer.parseInt(productMinField.getText());
            int max = Integer.parseInt(productMaxField.getText());
            boolean addProductSuccessful = false;

            if (name.isEmpty()) {
                displayAlert(5);
            }
            else {
                if (validMin(min, max) && validInv(min, max, stock)) {

                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : assocParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    newProduct.setId(Inventory.getNewProductId());
                    Inventory.addProduct(newProduct);
                    addProductSuccessful = true;
                }
                if (addProductSuccessful) {
                    returnToMain(actionEvent);
                }
            }
        }
        catch (Exception e) {
            displayAlert(1);
        }
    }

    /**
     * Removes selected associated part from the Product & its associated parts table
     *
     * @param actionEvent remove associated part button click
     */
    @FXML
    void onRemoveAssocButtonClick(ActionEvent actionEvent){

        Part selectedPart = assocPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(2);
        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){

                assocParts.remove(selectedPart);
                assocPartTable.refresh();
            }
        }
    }

    /**
     * Displays confirmation and returns to Main Screen
     *
     * @param actionEvent cancel button click
     */
    @FXML
    void onCancelButtonClick(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to cancel and return to the main screen? Any data you have created will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMain(actionEvent);
        }
    }

    /**
     * Returns to Main Screen
     */
    private void returnToMain(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/c482/main/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Checks if Min value is less than Max and greater than 0
     */
    private boolean validMin(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(4);
        }
        return isValid;
    }

    /**
     *Checks that inventory value is equal to or between Min & Max
     */
    private boolean validInv(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(3);
        }
        return isValid;
    }

    /**
     *Displays various alerts
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Product Cannot be Added");
                alert.setContentText("One or more field is blank or contains invalid value");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Selection");
                alert.setContentText("Please select a part");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Inventory Value");
                alert.setContentText("Inventory must equal to or between Min & Max");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Min Value");
                alert.setContentText("Min must be greater than 0 and less than Max");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Empty Name Field");
                alert.setContentText("Name Field cannot be empty.");
                alert.showAndWait();
                break;
            case 6:
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText("Part not Found");
                infoAlert.showAndWait();
                break;
        }
    }

    /**
     * Initializes controller; populates Add Part Table with data from Main Screen Part Table.
     * @param resourceBundle
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPartTable.setItems(Inventory.getAllParts());

        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
