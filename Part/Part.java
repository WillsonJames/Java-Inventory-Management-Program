package Part;
/**
 *
 * @JamesFuller
 */

/**
 * Controls all variables for the part object.
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Class constructor
     * @param id part id generated by inventory
     * @param name  part name
     * @param price part price
     * @param stock the current stock
     * @param min minimum stock value
     * @param max maximum stock value
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
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
     * @return returns the formatted string for the part
     */
    @Override
    public String toString() {
        return "#" + String.valueOf(id) + " " + name +" $" +  String.valueOf(price) + " q:" + String.valueOf(stock) + " (" + String.valueOf(min) + "/" + String.valueOf(max) +")" + " " + getMachineOrCompany();
    }

    /**
     * Returns a string from machineId or Company name.
     * Abstract class which works whether the part was in house or outsourced.
     */
    public abstract String getMachineOrCompany();
}