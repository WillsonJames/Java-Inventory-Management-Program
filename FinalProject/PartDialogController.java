package FinalProject;
import Part.Part;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
/**
 *
 * @JamesFuller
 */

/**
 * Controls the add/modify part dialog.
 */
public class PartDialogController {
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
    private TextField inOut;
    @FXML
    private ToggleGroup partType;
    @FXML
    private RadioButton outsourced;

    /**
     * Initializes the add part window
     */
    public void initialize(){
        addID.setText(String.valueOf(Inventory.getInstance().getNextPartID()));
    }

    /**
     * Takes the user input to make a part.
     * Checks for errors, then processes the user input into a part object in the inventory.
     */
    public void processResults() {
        //name value
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

        //gets input from inhouse/outsourced radio buttons and takes input text
        RadioButton selectedRadioButton = (RadioButton) partType.getSelectedToggle();
        String inOutSelection = selectedRadioButton.getText();
        String inOutText = inOut.getText().trim();

        //generates error is stock isn't between min and max, and all aren't positive numerals
        if(min > stock || stock > max || min < 0 || stock < 0 || max < 1){
            genericAlert("Stock must be between Min and Max");
            return;
        }

        //checks if the part was outsourced
        if (!inOutSelection.equals("In House")) {

            //generates error if company name blank for outsourced
            if(inOutText.equals("")) {
                genericAlert("Company name may not be blank");
                return;
            }

            //creates outsourced part
            Inventory.getInstance().addPart(name, price, stock, min, max, inOutText);

            //if part was made in house
        } else {

            //generates error if machine id isn't a number
            try {
                int inHouse = parseInt(inOutText);
            } catch(Exception e) {
                genericAlert("In House MachineID must be numeral");
                return;
            }

            //generates in house part
            int inHouse = parseInt(inOutText);
            Inventory.getInstance().addPart(name, price, stock, min, max, inHouse);

        }
    }

    /**
     * Adds the selected part data into the fields for the user to edit.
     */
    public void editPart(Part part) {
        addID.setText(String.valueOf(part.getId()));
        addName.setText(part.getName());
        addPrice.setText(String.valueOf(part.getPrice()));
        addStock.setText(String.valueOf(part.getStock()));
        addMin.setText(String.valueOf(part.getMin()));
        addMax.setText(String.valueOf(part.getMax()));
        inOut.setText(part.getMachineOrCompany());

        //selects in house radio button if the class is inhouse since outsourced is the default
        if (!part.getClass().toString().equals("class Part.InHouse")) {
            outsourced.setSelected(true);
        }
    }

    /**
     * Takes the user input and uses it to update the selected part's data.
     */
    public void updatePart(Part part){
        //grabs name and id from forms
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

        //gets input from inhouse/outsourced radio buttons and takes input text
        RadioButton selectedRadioButton = (RadioButton) partType.getSelectedToggle();
        String inOutSelection = selectedRadioButton.getText();
        String inOutText = inOut.getText().trim();

        //generates error is stock isn't between min and max, and all aren't positive numerals
        if(min > stock || stock > max || min < 0 || stock < 0 || max < 1){
            genericAlert("Stock must be between Min and Max");
            return;
        }

        //checks if the part was outsourced
        if (!inOutSelection.equals("In House")) {

            //generates error if company name blank for outsourced
            if(inOutText.equals("")) {
                genericAlert("Company name may not be blank");
                return;
            }

            //generates outsourced part
            Inventory.getInstance().updatePart(part, iD, name, price, stock, min, max, inOutText);

            //if the part was made in house
        } else {

            //generates error if machine id isn't a number
            try {
                int inHouse = parseInt(inOutText);
            } catch(Exception e) {
                genericAlert("In House MachineID must be numeral");
                }

            //generates in house part
            int inHouse = parseInt(inOutText);
            Inventory.getInstance().updatePart(part, iD, name, price, stock, min, max, inHouse);

        }
    }

    /**
     * Creates a warning whenever an error occurs from user input.
     * @param headerText the text used to fill the alert
     */
    public void genericAlert(String headerText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(headerText);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-font-family: Times New Roman bold, Arial, sans-serif;");
        alert.showAndWait();
    }
}