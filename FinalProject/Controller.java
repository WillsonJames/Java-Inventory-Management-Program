package FinalProject;
import Part.Part;
import Product.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.Optional;
import static java.lang.Integer.parseInt;
/**
 *
 * @JamesFuller
 */

/**
 * Controls the main page FXML program.
 */
public class Controller {
    @FXML
    private TextField searchProductField;
    @FXML
    private TextField searchPartField;
    @FXML
    private TableView<Part> partsList;
    @FXML
    private TableView<Product> productsList;
    @FXML
    private TableView<Part> associatedPartsList;
    @FXML
    private BorderPane mainPage;

    /**
     * Initializes the main page parameters
     */
    public void initialize() {
        //initializes starting data for 4 parts
        Inventory.getInstance().addPart("Screw", 0.05, 100, 0, 1000, 1);
        Inventory.getInstance().addPart("Nut", 0.10, 1000, 0, 10000, 2);
        Inventory.getInstance().addPart("Bolt", 0.25, 1000, 0, 10000, 3);
        Inventory.getInstance().addPart("Engine", 1000.00, 5, 0, 10, "Boeing");

        //creates two observable lists of parts to add to the products
        ObservableList<Part> houseParts = FXCollections.observableArrayList();
        ObservableList<Part> carParts = FXCollections.observableArrayList();

        //adds parts to these lists
        carParts.add(Inventory.getInstance().lookupPart(2));
        carParts.add(Inventory.getInstance().lookupPart(3));
        carParts.add(Inventory.getInstance().lookupPart(4));
        houseParts.addAll(Inventory.getInstance().lookupPart(1));

        //creates two products using these lists
        Inventory.getInstance().addProduct( "Car", 10000.00, 2, 1, 5, carParts);
        Inventory.getInstance().addProduct("House", 200000.00, 1, 0, 2, houseParts);

        //initializes the change listener for the products list
        productsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {

            /**
             * Creates a change listener for the products list table.
             * Overrides the changed parameter for a product in an observable list and fills the middle table with the associated parts list of the selected product from the left side, and runs an update to ensure this list is accurate.
             * @param product the previous product selected
             * @param t1 the new product selected
             */
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product product, Product t1) {
                if(t1 != null){
                    Product item = productsList.getSelectionModel().getSelectedItem();
                    Inventory.getInstance().updateAssociatedPartsList(item);
                    associatedPartsList.setItems(item.getAllAssociatedParts());
                }
                else {
                    associatedPartsList.setItems(null);
                }
            }
        });

        //initializes the change listener for the parts list
        associatedPartsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Part>() {

            /**
             * Creates a change listener for the parts list table.
             * Overrides the changed parameter for a part in an observable list and clears the left and middle tables to ensure they update after part changes occur.
             * @param part the previous part selected
             * @param t1 the new product selected
             */
            @Override
            public void changed(ObservableValue<? extends Part> observableValue, Part part, Part t1) {
                if(t1 != null){
                    Product currItem = productsList.getSelectionModel().getSelectedItem();
                    associatedPartsList.setItems(currItem.getAllAssociatedParts());
                    partsList.getSelectionModel().select(associatedPartsList.getSelectionModel().getSelectedItem());

                }

            }
        });

        //ensures each list can only have one selected item
        partsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //adds all items to visible list
        productsList.setItems(Inventory.getInstance().getAllProducts());
        partsList.setItems(Inventory.getInstance().getAllParts());
    }

    /**
     * Opens the add part window
     * Creates an add part window from a dialog, adding dialog buttons to confirm or cancel the changes
     * RUNTIME ERROR This window initially gave a font error due to an installation issue between java 8 and Mac os Big sur which was fixed by setting the font configuration with a custom css.
     */
    @FXML
    public void addPart() {
        //initializes dialog and gets fxml file
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Part");
        dialog.initOwner(mainPage.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addPartDialog.fxml"));

        //tries loading the dialog pane with the fxml file and gives error if it fails
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("New window error");
            e.printStackTrace();
            return;
        }

        //initializes the ok and cancel buttons for dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");

        //tells dialog to open and wait for user input from buttons
        Optional<ButtonType> result = dialog.showAndWait();

        //processes the results of user input if ok is pressed. If cancel is pressed the dialog exits without saving
        if(result.isPresent() && result.get() == ButtonType.OK) {
            PartDialogController controller = fxmlLoader.getController();
            controller.processResults();
        }
    }

    /**
     * Opens the add product window
     * Creates an add product window from a dialog, adding dialog buttons to confirm or cancel the changes
     */
    @FXML
    public void addProduct() {
        //initializes dialog and gets fxml file
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Product");
        dialog.initOwner(mainPage.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddProductDialog.fxml"));

        //tries loading the dialog pane with the fxml file and gives error if it fails
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("New window error");
            e.printStackTrace();
            return;
        }

        //initializes the ok and cancel buttons for dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");

        //tells dialog to open and wait for user input from buttons
        Optional<ButtonType> result = dialog.showAndWait();

        //processes the results of user input if ok is pressed. If cancel is pressed the dialog exits without saving
        if(result.isPresent() && result.get() == ButtonType.OK) {
            ProductDialogController controller = fxmlLoader.getController();
            controller.processResults();
        }
    }

    /**
     * Opens the modify part window
     * Creates an modify part window from a dialog, loading the data from the part selected in the table and adding dialog buttons to confirm or cancel the changes.
     */
    public void modifyPart() {
        //takes selected product and generates error if none selected
        Part selectedPart = partsList.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            genericAlert("No part selected");
            return;
        }

        //initializes dialog and loads fxml file
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPage.getScene().getWindow());
        dialog.setTitle("Modify Part");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addPartDialog.fxml"));

        //tries loading the dialog pane with the fxml file and gives error if it fails
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("New window error");
            e.printStackTrace();
            return;
        }

        //initializes the ok and cancel buttons for dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");

        //finds the new controller and loads teh data from the selected product
        PartDialogController partController = fxmlLoader.getController();
        partController.editPart(selectedPart);

        //tells dialog to open and wait for user input from buttons
        Optional<ButtonType> result = dialog.showAndWait();

        //processes the results of user input if ok is pressed. If cancel is pressed the dialog exits without saving
        if(result.isPresent() && result.get() == ButtonType.OK) {
            PartDialogController controller = fxmlLoader.getController();
            controller.updatePart(selectedPart);
        }
        //de-selects item from product list after changes to ensure it will be updated before being used
        productsList.getSelectionModel().select(null);
    }

    /**
     * Opens the modify product window
     * Creates an modify product window from a dialog, loading the data from the product selected in the table and adding dialog buttons to confirm or cancel the changes.
     */
    @FXML
    public void modifyProduct() {
        //takes selected product and generates error if none selected
        Product selectedProduct = productsList.getSelectionModel().getSelectedItem();
            if(selectedProduct == null) {
                genericAlert("No product selected");
                return;
            }

        //initializes dialog and loads fxml file
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modify Product");
        dialog.initOwner(mainPage.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddProductDialog.fxml"));

        //tries loading the dialog pane with the fxml file and gives error if it fails
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("New window error");
            e.printStackTrace();
            return;
        }

        //initializes the ok and cancel buttons for dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");

        //finds the new controller and loads teh data from the selected product
        ProductDialogController productController = fxmlLoader.getController();
        productController.editProduct(selectedProduct);

        //tells dialog to open and wait for user input from buttons
        Optional<ButtonType> result = dialog.showAndWait();

        //processes the results of user input if ok is pressed. If cancel is pressed the dialog exits without saving
        if(result.isPresent() && result.get() == ButtonType.OK) {
            ProductDialogController controller = fxmlLoader.getController();
            controller.updateProduct(selectedProduct);
        }
    }

    /**
     * Deletes the selected part.
     * Deletes selected part after confirmation by the user via ok button.
     */
    @FXML
    public void deletePart() {
        //takes selected part and generates error if none selected
        Part selectedPart = partsList.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            genericAlert("No part selected");
            return;
        }

        //initializes the dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPage.getScene().getWindow());
        dialog.setTitle("Confirm your deletion?");

        //initializes the ok and cancel buttons for dialog and restyles them
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");
        dialog.getDialogPane().lookupButton(ButtonType.OK).setAccessibleText("Delete");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");

        //tells dialog to open and wait for user input from buttons
        Optional<ButtonType> result = dialog.showAndWait();

        //processes the delete if ok is pressed. If cancel is pressed the dialog exits without saving
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.getInstance().deletePart((Part) partsList.getSelectionModel().getSelectedItem());
        }
        //de-selects item from product list after changes to ensure it will be updated before being used
        productsList.getSelectionModel().select(null);
    }

    /**
     * Deletes the selected product.
     * Deletes selected product after confirmation by the user via ok button.
     */
    @FXML
    public void deleteProduct() {
        //takes selected product and generates error if none selected
        Product selectedProduct = productsList.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            genericAlert("No product selected");
            return;
        }

        //checks for associated parts in product to be deleted
        if(productsList.getSelectionModel().getSelectedItem().getAllAssociatedParts().size() > 0) {
            genericAlert("Must de-associate parts first");
            return;
        }

        //initializes the dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPage.getScene().getWindow());
        dialog.setTitle("Confirm your deletion?");

        //initializes the ok and cancel buttons for dialog and restyles them
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");
        dialog.getDialogPane().lookupButton(ButtonType.OK).setAccessibleText("Delete");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");

        //tells dialog to open and wait for user input from buttons
        Optional<ButtonType> result = dialog.showAndWait();

        //processes the delete if ok is pressed. If cancel is pressed the dialog exits without saving
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.getInstance().deleteProduct((Product) productsList.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Searches for a part.
     * Searches for the intended part by deciding if the user entered an int or string, then searching for and highlighting potential match, or showing warning.
     * FUTURE ENHANCEMENT In the future this search function could be activated when the user hits enter instead of just clicking the search button.
     */
    public void searchPart(){
        //checks if you entered an int
        try {
            int searchNumber = parseInt(searchPartField.getText());
        }

            //if it was a string entered, checks for a matching name and generates error if none matched
         catch(Exception e) {
            if(Inventory.getInstance().lookupPart(searchPartField.getText()) == null) {
                genericAlert("No Part with this name");
                return;
            }

             //highlights matching product found and exits
            partsList.getSelectionModel().select(Inventory.getInstance().lookupPart(searchPartField.getText()));
            return;
        }

        //if you entered and int for ID
        int searchNumber = parseInt(searchPartField.getText());

        //checks if a matching id is found and generates error if none matched
        if(Inventory.getInstance().lookupPart(searchNumber) == null) {
            genericAlert("No Part with this ID");
            return;
        }

        //highlights matching product found
        partsList.getSelectionModel().select(Inventory.getInstance().lookupPart(searchNumber));
        return;
    }

    /**
     * Searches for a product.
     * Searches for the intended product by deciding if the user entered an int or string, then searching for and highlighting potential match, or showing warning.
     * FUTURE ENHANCEMENT In the future this search function could be activated when the user hits enter instead of just clicking the search button.
     */
    public void searchProduct() {
        //checks if you entered an int
        try {
            int searchNumber = parseInt(searchProductField.getText());
        }

        //if it was a string entered, checks for a matching name and generates error if none matched
        catch (Exception e) {
            if (Inventory.getInstance().lookupProduct(searchProductField.getText()) == null) {
                genericAlert("No Product with this name");
                return;
            }

            //highlights matching product found and exits
            productsList.getSelectionModel().select(Inventory.getInstance().lookupProduct(searchProductField.getText()));
            return;
        }

        //if you entered an int for ID
        int searchNumber = parseInt(searchProductField.getText());

        //checks if a matching id is found and generates error if none matched
        if (Inventory.getInstance().lookupProduct(searchNumber) == null) {
            genericAlert("No Product with this ID");
            return;
        }
        productsList.getSelectionModel().select(Inventory.getInstance().lookupProduct(searchNumber)); //highlights matching product found
        return;
    }

    /**
     * De-selects selected product after user clicks the parts list.
     * This is to ensure that the products are updated when reselected after the parts list changes.
     */
    @FXML
    public void partListClick(){
        productsList.getSelectionModel().select(null);
    }

    /**
     * Creates a warning whenever for an error.
     * Takes input and generates error using input as tile
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

