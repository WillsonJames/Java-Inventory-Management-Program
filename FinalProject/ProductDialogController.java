package FinalProject;

import Part.Part;
import Product.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Locale;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
/**
 *
 * @JamesFuller
 */

/**
 * Controls the add/modify product dialog.
 */
public class ProductDialogController {
    @FXML
    private TextField addID;
    @FXML
    private TextField addName;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addStock;
    @FXML
    private TextField addMin;
    @FXML
    private TextField addMax;
    @FXML
    private TextField searchAssociated;
    @FXML
    private TextField searchUnadded;
    @FXML
    private TableView<Part> associatedPartsList;
    @FXML
    private TableView<Part> unaddedPartsList;

    /**
     * Initializes the add product window
     */
    public void initialize(){
        //initializes ids, all parts list, and creates a blank list for associated parts to be added
        ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        unaddedPartsList.setItems(Inventory.getInstance().getAllParts());
        associatedPartsList.setItems(associatedParts);
        addID.setText(String.valueOf(Inventory.getInstance().getNextProductID()));
    }

    /**
     * Takes the user input to make a product.
     * Checks for errors, then processes the user input into a product object in the inventory.
     */
    public void processResults() {
        //gets id and name values
        int iD = parseInt(addID.getText().trim());
        String name = addName.getText().trim();

        //generates error if name is blank
        if(name.equals("")) {
            genericAlert("Name may not be blank");
            return;
        }

        //generates error if price isn't in correct format
        try {
            double price = parseDouble(addPrice.getText().trim());
        } catch(Exception e) {
            genericAlert("Price must be in format 'X.XX'");
            return;
        }

        //generates error if stock, min and max aren't numerals
        try {
            int stock = parseInt(addStock.getText().trim());
            int min = parseInt(addMin.getText().trim());
            int max = parseInt(addMax.getText().trim());
        } catch(Exception e) {
            genericAlert("Stock, Min and Max must be numerals");
            return;
        }

        //gets price, stock, min and max values
        double price = parseDouble(addPrice.getText().trim());
        int stock = parseInt(addStock.getText().trim());
        int min = parseInt(addMin.getText().trim());
        int max = parseInt(addMax.getText().trim());

        //generates error is stock isn't between min and max, and all aren't positive numerals
        if(min > stock || stock > max || min < 0 || stock < 0 || max < 1 || max < min){
            genericAlert("Stock must be between Min and Max");
            return;
        }

        //add associated parts from the selected table into the temporary parts list, and runs the add product with user results
        ObservableList associatedParts = associatedPartsList.getItems();
        Inventory.getInstance().addProduct(name, price, stock, min, max, associatedParts);
    }

    /**
     * Adds selected part to the associated parts list.
     */
    @FXML
    public void addAssociatedPart() {
        //takes selected part and generates error if none selected
        Part part = unaddedPartsList.getSelectionModel().getSelectedItem();
        if(part == null) {
            genericAlert("No part selected");
            return;
        }

        //creates temporary list of parts for the associated parts to be modified
        ObservableList<Part> temporaryList = FXCollections.observableArrayList();
        temporaryList.setAll(associatedPartsList.getItems());

        //generates error if the part to be added is a duplicate
        for(Part checkPart : temporaryList){
            if(checkPart == part){
                genericAlert("No duplicate parts permitted");
                return;
            }
        }

        //adds the part to the temporary list of parts in the right table
        temporaryList.add(part);
        associatedPartsList.setItems(temporaryList);
    }

    /**
     * Removes the selected part to the associated parts list.
     */
    @FXML
    public void removeAssociatedPart(){
        //takes selected part and generates error if none selected
        Part part = associatedPartsList.getSelectionModel().getSelectedItem();
        if(part == null) {
            genericAlert("No part selected");
            return;
        }

        //creates temporary list of parts for the associated parts to be modified
        ObservableList temporaryList = FXCollections.observableArrayList();
        temporaryList.setAll(associatedPartsList.getItems());

        //removes the part from the temporary list of parts in the right table
        temporaryList.remove(part);
        associatedPartsList.setItems(temporaryList);
    }

    /**
     * Adds the selected product data into the fields for the user to edit.
     */
    public void editProduct(Product product){
        addID.setText(String.valueOf(product.getId()));
        addName.setText(product.getName());
        addPrice.setText(String.valueOf(product.getPrice()));
        addStock.setText(String.valueOf(product.getStock()));
        addMin.setText(String.valueOf(product.getMin()));
        addMax.setText(String.valueOf(product.getMax()));
        associatedPartsList.setItems(product.getAllAssociatedParts());
    }

    /**
     * Takes the user input and uses it to update the selected product's data.
     */
    public void updateProduct(Product product){
        //grabs name from user input
        String name = addName.getText().trim();

        //generates error if name is blank
        if(name.equals("")) {
            genericAlert("Name may not be blank");
            return;
        }

        //generates error if price isn't in correct format
        try {
            double price = parseDouble(addPrice.getText().trim());
        } catch(Exception e) {
            genericAlert("Price must be in format 'X.XX'");
            return;
        }

        //generates error if stock, min and max aren't numerals
        try {
            int stock = parseInt(addStock.getText().trim());
            int min = parseInt(addMin.getText().trim());
            int max = parseInt(addMax.getText().trim());
        } catch(Exception e) {
            genericAlert("Stock, Min and Max must be numerals");
            return;
        }

        //gets price, stock, min and max values
        double price = parseDouble(addPrice.getText().trim());
        int stock = parseInt(addStock.getText().trim());
        int min = parseInt(addMin.getText().trim());
        int max = parseInt(addMax.getText().trim());

        //generates error is stock isn't between min and max, and all aren't positive numerals
        if(min > stock || stock > max || min < 0 || stock < 0 || max < 1 || max < min){
            genericAlert("Stock must be between Min and Max");
            return;
        }

        //add associated parts from the selected table into the temporary parts list, and runs the modify product with user results
        ObservableList associatedParts = FXCollections.observableArrayList();
        associatedParts.setAll(associatedPartsList.getItems());
        Inventory.getInstance().updateProduct(product, name, price, stock, min, max, associatedParts);
    }

    /**
     * Searches for a part.
     * Searches for the intended part by deciding if the user entered an int or string, then searching for and highlighting potential match, or showing warning.
     */
    public void searchUnaddedPart(){
        //checks if you entered an int
        try {
            int searchNumber = parseInt(searchUnadded.getText());
        }

        //if it was a string entered, checks for a matching name and generates error if none matched
        catch(Exception e) {
            if(Inventory.getInstance().lookupPart(searchUnadded.getText()) == null) {
                genericAlert("No Part with this name");
                return;
            }

            //highlights matching product found and exits
            unaddedPartsList.getSelectionModel().select(Inventory.getInstance().lookupPart(searchUnadded.getText()));
            return;
        }

        //if you entered and int for ID
        int searchNumber = parseInt(searchUnadded.getText());

        //checks if a matching id is found and generates error if none matched
        if(Inventory.getInstance().lookupPart(searchNumber) == null) {
            genericAlert("No Part with this ID");
            return;
        }

        //highlights matching product found
        unaddedPartsList.getSelectionModel().select(Inventory.getInstance().lookupPart(searchNumber));
        return;
    }

    /**
     * Searches for a part.
     * Searches for the intended part by deciding if the user entered an int or string, then searching for and highlighting potential match, or showing warning.
     */
    public void searchAssociatedPart(){
        //checks if you entered an int
        try {
            int searchNumber = parseInt(searchAssociated.getText());
        }

        //if it was a string entered, checks for a matching name and generates error if none matched
        catch(Exception e) {
            for (Part tempPart : associatedPartsList.getItems()) {
                if (tempPart.getName().toLowerCase(Locale.ROOT).contains(searchAssociated.getText().trim().toLowerCase(Locale.ROOT))) {

                    //highlights matching product found and exits
                    associatedPartsList.getSelectionModel().select(tempPart);
                    return;

                    //If no matching part is counf
                }
            }
            //if no matching part is found
            genericAlert("No Part with this name");
            return;
        }

        //if you entered and int for ID goes through the parts list and checks if there is a matching id
        int searchNumber = parseInt(searchAssociated.getText());
        for(Part tempPart : associatedPartsList.getItems()) {
            if(tempPart.getId() == (searchNumber)) {
                associatedPartsList.getSelectionModel().select(tempPart);
                return;
            }
        }
        //checks if a matching id is not found
        genericAlert("No Part with this ID");
        return;
        }

    /**
     * Creates a warning whenever an error occurs from user input.
     * @param title the text used to fill the alert
     */
    public void genericAlert(String title){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");
        alert.showAndWait();
    }
}
