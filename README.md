# Java Inventory Management System
Created for Software I (WGU class C482)

## Overview
This project is a desktop-based **Inventory Management System** developed in Java using **JavaFX** for the GUI. The application is designed to manage parts and products for a small manufacturing company. It allows users to add, modify, and delete parts and products, ensuring that business requirements for inventory control are met efficiently.

The software is structured using object-oriented programming (OOP) principles and follows best practices such as inheritance, encapsulation, and abstraction to ensure the application is scalable, maintainable, and efficient.

## Key Features
- **Graphical User Interface**: Built using JavaFX, the application features intuitive forms for managing parts and products, modeled after a provided GUI mock-up.
- **CRUD Operations**: Add, modify, and delete both parts and products.
- **Search Functionality**: Search parts and products by ID or name (partial or full match).
- **Input Validation**: Ensures that users enter valid data, such as checking that minimum stock levels are less than the maximum, and inventory levels fall within acceptable bounds.
- **Non-Persistent Data**: All data is stored in memory, and the application does not require database or file storage.
- **Parts and Products Association**: Users can associate parts with products, and ensure products cannot be deleted if they are linked to parts.
- **Exception Handling**: Robust error-handling mechanisms ensure the application does not crash due to invalid input or actions.

## Technical Overview
- **OOP Principles**: The system incorporates multiple object-oriented programming concepts:
  - **Inheritance**: Base classes are extended by subclasses, particularly in the differentiation between `InHouse` and `Outsourced` parts.
  - **Encapsulation**: Fields are accessed and modified through getter and setter methods.
  - **Abstraction**: Abstract classes and interfaces are used to structure the parts of the system.
- **JavaFX**: The GUI is implemented using JavaFX and supports multiple forms like "Add Part", "Modify Part", "Add Product", and "Modify Product."
- **Validation**: User input is validated, with error messages and feedback displayed using dialog boxes.

## Requirements
- **IDE**: NetBeans 11.1+ or IntelliJ IDEA (Community Edition)
- **Java Version**: JDK 17 (LTS)
- **JavaFX**: JavaFX SDK/Module for GUI components
- **Scene Builder**: For creating and modifying the JavaFX FXML files.

## Future Enhancements
- **Persistent Storage**: Integrating database support to store parts and products data persistently.
- **User Authentication**: Add functionality for user login and role-based access to enhance security.
- **Enhanced Search**: Improve the search functionality with advanced filters or sorting options.

## Error Handling
- **Runtime Error Correction**: One significant runtime error occurred when an invalid product ID was entered. This was fixed by validating user input before proceeding with any action.
- **Logical Error Correction**: There was a logic error in handling the modification of parts that had a mismatch in the `InHouse` and `Outsourced` labels. This was corrected by ensuring that the appropriate label updates when the corresponding radio button is selected.

## How to Run
1. Download and unzip the project files.
2. Open the project in NetBeans or IntelliJ.
3. Run the `Main` class to launch the application.
