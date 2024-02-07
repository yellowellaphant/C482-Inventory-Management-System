package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the Add Part Screen
 *
 * @author Ella Upchurch
 */
public class AddPartController implements Initializable {

    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private ToggleGroup partSourceToggle;
    @FXML
    private Label partManufacturerId;
    @FXML
    private TextField partIdField;
    @FXML
    private TextField partNameField;
    @FXML
    private TextField partStockField;
    @FXML
    private TextField partPriceField;
    @FXML
    private TextField partMaxField;
    @FXML
    private TextField partMinField;
    @FXML
    private TextField machineOrCompanyId;

    /** Sets Machine ID/Company Name field to "Machine ID".
     *
     * @param actionEvent in-house radio button selected
     */
    @FXML
    void onSelectInHouse(ActionEvent actionEvent) {

        partManufacturerId.setText("Machine ID");
    }

    /** Sets Machine ID/Company Name field to "Company Name".
     *
     * @param actionEvent outsourced radio button selected
     */
    @FXML
    void onSelectOutsourced(ActionEvent actionEvent) {

        partManufacturerId.setText("Company Name");
    }

    /** Adds new part to inventory and returns to Main Screen.
     *
     * Error messages are displayed when invalid inputs are detected,
     * preventing empty or invalid values
     *
     * @param actionEvent save button click
     */
    @FXML
    void onSaveButtonClick(ActionEvent actionEvent) throws IOException{

        try {
            int id = 0;
            String name = partNameField.getText();
            double price = Double.parseDouble(partPriceField.getText());
            int stock = Integer.parseInt(partStockField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            int machineId;
            String companyName;
            boolean addPartSuccessful = false;

            if (name.isEmpty()) {
                displayAlert(5);
            }
            else {
                if (validMin(min, max) && validInv(min, max, stock)) {

                    if (inHouseRadio.isSelected()) {
                        try{
                            machineId = Integer.parseInt(machineOrCompanyId.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getNewPartId());
                            Inventory.addPart(newInHousePart);
                            addPartSuccessful = true;
                        }
                        catch (Exception e) {
                            displayAlert(2);
                        }
                    }

                    if (outsourcedRadio.isSelected()) {
                        companyName = machineOrCompanyId.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        newOutsourcedPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newOutsourcedPart);
                        addPartSuccessful = true;
                    }
                    if (addPartSuccessful) {
                        returnToMain(actionEvent);
                    }
                }
            }
        }
        catch (Exception e) {
            displayAlert(1);
        }
    }

    /** Displays confirmation and returns to Main Screen.
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
     * Returns to Main Screen.
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

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Part Cannot be Added");
                alert.setContentText("One or more field is blank or contains invalid value");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Machine ID");
                alert.setContentText("Machine ID must be numeric");
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
        }
    }

    /**
     * Initializes controller and sets In-House Button to true by default
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inHouseRadio.setSelected(true);
    }

}
