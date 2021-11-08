package FinalProject;
import Part.*;
import Product.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Locale;

/**
  * This class controls the inventory by maintaining a lists Parts and Products.
  */
public class Inventory {
    private static Inventory instance = new Inventory();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private int nextPartID = 1;
    private int nextProductID = 1;

    /**
     * This is the get instance method for the inventory.
     * @return returns current inventory instance
     */
    public static Inventory getInstance(){
        return instance;
    }

    /**
     * The get next part id method for the inventory.
     * @return This method returns the next part id.
     */
    public int getNextPartID() {
        return nextPartID;
    }

    /**
     * The get next product id method for the inventory.
     * @return This method returns the next product id.
     */
    public int getNextProductID() {
        return nextProductID;
    }

    /**
     * This is the in house add method part for the inventory.
     * This method generates a new in house part and adds it to the parts list and increments the next part id.
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the current value for part inventory
     * @param max the maximum value for part inventory
     * @param min the minimum value for part inventory
     * @param machineId the id of the machine used to make the part
     */
    public void addPart(String name, double price, int stock, int min, int max, int machineId) {
        InHouse part = new InHouse(nextPartID, name, price, stock, min, max, machineId);
        nextPartID++; //increments the part id so that it is not reused
        allParts.add(part);
    }

    /**
     * This is the outsourced add part method for the inventory.
     * This method generates a new outsourced part and adds it to the parts list and increments the next part id.
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the current value for part inventory
     * @param max the maximum value for part inventory
     * @param min the minimum value for part inventory
     * @param companyName the name of the company for the outsourced part
     */
    public void addPart(String name, double price, int stock, int min, int max, String companyName) {
        Outsourced part = new Outsourced(nextPartID, name, price, stock, min, max, companyName);
        nextPartID++; //increments the part id so that it is not reused
        allParts.add(part);
    }

    /**
     * This is the add product method for the inventory.
     * This method generates a new product and adds it to the products list and increments the next product id.
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the current value for product inventory
     * @param max the maximum value for product inventory
     * @param min the minimum value for product inventory
     * @param associatedParts the new list of parts to be used
     */
    public void addProduct(String name,Double price,int stock,int min,int max,ObservableList associatedParts) {
        Product newProduct;
        newProduct = new Product(nextProductID, name, price, stock, min, max, associatedParts);
        nextProductID +=1; //increments the product id so that it is not reused
        allProducts.add(newProduct);
    }

    /**
     * This is the update in house part method.
     * This method generates a new in house part using the input parameters and deletes the old.
     * @param part the part to be modified
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the current value for part inventory
     * @param max the maximum value for part inventory
     * @param min the minimum value for part inventory
     * @param machineId the id of the machine used to make the part
     */
    public void updatePart(Part part, int iD, String name, Double price,int stock,int min,int max, int machineId) {
        allParts.remove(part);
        InHouse updatedPart = new InHouse(iD, name, price, stock, min, max, machineId);
        allParts.add(updatedPart);
    }

    /**
     * This is the update outsourced part method.
     * This method generates a new outsourced part using the input parameters and deletes the old.
     * @param part the part to be modified
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the current value for part inventory
     * @param max the maximum value for part inventory
     * @param min the minimum value for part inventory
     * @param companyName the name of the company for the outsourced part
     */
    public void updatePart(Part part, int iD, String name, Double price,int stock,int min,int max, String companyName) {
        allParts.remove(part);
        Outsourced updatedPart = new Outsourced(iD, name, price, stock, min, max, companyName);
        allParts.add(updatedPart);
    }

    /**
     * This is the update product method.
     * This method updates a product based on the input parameters.
     * @param product the product to be modified
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the current value for product inventory
     * @param max the maximum value for product inventory
     * @param min the minimum value for product inventory
     * @param associatedParts the new list of parts to be used
     */
    public void updateProduct(Product product, String name, Double price,int stock,int min,int max,ObservableList associatedParts) {
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setMin(min);
        product.setMax(max);
        product.deleteAssociatedParts();
        product.addAssociatedParts(associatedParts);
    }

    /**
     * This is the update product's associated parts method.
     * This method updates a product's associated parts list by checking associated parts' ids against the inventory parts list and updates or removes parts that are outdated.
     * @param product the product who's parts list will be updated
     */
    public void updateAssociatedPartsList(Product product){
        ObservableList<Part> newParts = FXCollections.observableArrayList();
        ObservableList<Part> oldParts = FXCollections.observableArrayList();
        oldParts.addAll(product.getAllAssociatedParts());

        //take each part id from a product's associated part, and add the matching part from the parts list to a new list if there is one.
        for(Part tempPart : oldParts) {
            if(lookupPart(tempPart.getId()) != null) {
                newParts.add(lookupPart(tempPart.getId()));
            }
        }
        //delete the old associated parts and add the list of updated parts with matching ids.
        product.deleteAssociatedParts();
        product.addAssociatedParts(newParts);
    }

    /**
     * The delete part method.
     * This method deletes the selected part from the parts list.
     * @param part the part to be deleted
     */
    public void deletePart(Part part){
        allParts.remove(part);
    }

    /**
     * The delete product method.
     * This method deletes the selected product from the products list.
     * @param product the product to be deleted
     */
    public void deleteProduct(Product product){
        allProducts.remove(product);
    }

    /**
     * This is the lookup part by id method.
     * This method returns a part from the parts list using an input integer.
     * @return returns a matching part or returns null.
     * @param id the number input by user to be searched
     */
    public Part lookupPart(int id) {
        //goes through the parts list and checks if there is a matching id
        for(Part tempPart : allParts) {
            if(tempPart.getId() == (id)) {
                return tempPart;
            }
        }
        return null;
    }

    /**
     * This is the lookup part by name method.
     * This method returns a part from the parts list using a partial match from an input string.
     * @return returns a matching part or returns null.
     * @param name the characters input by user to be searched
     */
    public Part lookupPart(String name) {
        //goes through the parts list and checks if there is a matching string for name
        for(Part tempPart : allParts) {
            if (tempPart.getName().toLowerCase(Locale.ROOT).contains(name.trim().toLowerCase(Locale.ROOT))){
                return tempPart;
            }
        }
        return null;
    }

    /**
     * This is the lookup product by id method.
     * This method returns a product from the products list using an input integer.
     * @return returns a matching product or returns null.
     * @param id the number input by user to be searched
     */
    public Product lookupProduct(int id) {
        //goes through the products list and checks if there is a matching id
        for(Product tempProd : allProducts) {
            if(tempProd.getId() == (id)) {
                return tempProd;
            }
        }
        return null;
    }

    /**
     * This is the lookup product by name method.
     * This method returns a product from the products list using a partial match from an input string.
     * @return returns a matching product or returns null.
     * @param name the characters input by user to be searched
     */
    public Product lookupProduct(String name) {
        //goes through the parts list and checks if there is a matching string for name
        for(Product tempProd : allProducts) {
            if (tempProd.getName().toLowerCase(Locale.ROOT).contains(name.trim().toLowerCase(Locale.ROOT))){
                return tempProd;
            }
        }
        return null;
    }

    /**
     * The get all parts method.
     * @return returns the full observable list of parts
     */
    public ObservableList getAllParts(){
        return allParts;
    }

    /**
     * The get all products method.
     * @return returns the full observable list of products
     */
    public ObservableList getAllProducts() {
        return allProducts;
    }
}
