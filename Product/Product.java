package Product;
import Part.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @JamesFuller
 */

/**
 * Controls all variables for a Product and maintains the associated Parts list.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Class constructor
     * @param id product id generated by inventory
     * @param name  product name
     * @param price product price
     * @param stock the current stock
     * @param min minimum stock value
     * @param max maximum stock value
     * @param parts the list of parts to be associated
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList parts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = parts;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param parts the parts list to add
     */
    public void addAssociatedParts(ObservableList parts) {
        associatedParts.addAll(parts);
    }

    /**
     * @return returns the parts list
     */
    public ObservableList getAllAssociatedParts(){
       return associatedParts;
    }

    /**
     * Deletes all parts from the parts list
     */
    public void deleteAssociatedParts(){
        associatedParts.setAll();
    }

    /**
     * @return returns the formatted string for the product
     */
    @Override
    public String toString() {
        return "#" + String.valueOf(id) + " " + name +" $" +  String.valueOf(price) + " q:" + String.valueOf(stock) + " (" + String.valueOf(min) + "/" + String.valueOf(max) +")";
    }
}
