package c482.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/**
 * The java docs are located in a folder labeled "javadocs" this is submitted separately from the zip file
 *
 * FUTURE_ENHANCEMENT A future enhancement could be to add a slider bar to the inventory
 * level (stock) field that only allows the user to input an amount between the min and max
 * values
 *
 * RUNTIME_ERROR When coding the controller for the Modify Part Screen, the save method
 * allowed the Product name field to be left blank when saving the modified product.
 * I fixed this runtime error by adding an if statement to the save method that checked
 * the name field and produced an error screen when it was left blank
 *
 */

/** This class creates an application that manages inventory.
 *
 *@author Ella Upchurch
 */

public class Main extends Application {

    /** The start method.
     Creates the FXML stage and loads initial screen.
     @param stage
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1005, 400);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
/**The main method.
 Creates sample data and launches application.
 @param args
 */
    public static void main(String[] args) {

        //sample parts
        int partId = Inventory.getNewPartId();
        InHouse brakes = new InHouse(partId, "Brakes", 15.00, 10, 1, 35, 101);

        partId = Inventory.getNewPartId();
        InHouse wheel = new InHouse(partId, "Wheel", 11.00, 16, 1, 55, 102);

        partId = Inventory.getNewPartId();
        Outsourced seat = new Outsourced(partId, "Seat", 15.00, 10, 1, 65, "The Seat Company");

        Inventory.addPart(brakes);
        Inventory.addPart(wheel);
        Inventory.addPart(seat);

        //sample product
        int productId = Inventory.getNewProductId();
        Product tricycle = new Product(productId, "Tricycle", 99.99, 3, 1, 200);

        productId = Inventory.getNewProductId();
        Product mBike = new Product(productId, "Mountain Bike", 299.99, 5, 1, 300);

        Inventory.addProduct(tricycle);
        Inventory.addProduct(mBike);

        tricycle.addAssociatedPart(brakes);
        tricycle.addAssociatedPart(wheel);
        tricycle.addAssociatedPart(seat);

        launch(args);
    }
}