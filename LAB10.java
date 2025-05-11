import java.util.*;
public class Test {
    public static void main(String[] args) {
        InventoryManager inventoryManager=new InventoryManager();
        Product product1=new Product("LipGloss",38.00,100);
        Product product2=new Product("Laptop",900.00,300);
        Product product3=new Product("Ipad",750.00,160);
        Product product4=new Product("Blush",45.00,270);
        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);
        inventoryManager.addProduct(product3);
        inventoryManager.addProduct(product4);
        System.out.println("-----------Inventory-----------");
        inventoryManager.printStock();
        System.out.println();

        Sale<Product>sale1=new Sale<>();
        sale1.addItem(product2,3);
        sale1.addItem(product4,5);
        Sale<Product>sale2=new Sale<>();
        sale2.addItem(product1,2);
        sale2.addItem(product3,4);

        SalesManager<Product>salesManager=new SalesManager<>();
        salesManager.recordSale(sale1);
        salesManager.recordSale(sale2);
        System.out.println("-----------Sale Summary-----------");
        salesManager.printSummary();
        System.out.println();
        System.out.println("-----------Inventory after sale-----------");
        inventoryManager.printStock();
    }
}
class Product{
    private String name;
    private double price;
    private int stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    public void reduceStock(int quantity){
        stock-=quantity;
    }
    public void restock(int quantity){
        stock+=quantity;
    }
    public String toString(){
        return "Name: " + name + "\n" +
                "Stock: " + stock + "\n"+
                "Price: " + price + "\n";
    }
}
class InventoryManager{
    private HashMap<String, Product> inventory;

    public HashMap<String, Product> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Product> inventory) {
        this.inventory = inventory;
    }

    public InventoryManager() {
        this.inventory = new HashMap<>();
    }
    public void addProduct(Product product){
        inventory.put(product.getName(), product);
    }
    public Product getProduct(String name){
        return inventory.get(name);
    }
    public void printStock() {
        if (inventory.isEmpty()) {
            System.out.println("The inventory is empty!");
            return;
        }
        System.out.println("Products:");
        for (Product product : inventory.values()) {
            System.out.println(product);
        }
    }
}
class Sale<T extends Product>{
    private HashMap<T, Integer> items;

    public HashMap<T, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<T, Integer> items) {
        this.items = items;
    }

    public Sale() {
        this.items=new HashMap<>();
    }
    public void addItem(T product, int quantity)throws IllegalArgumentException{
        if(product.getStock()<quantity){
            throw new IllegalArgumentException("There aren’t enough product");
        }
        product.reduceStock(quantity);
        items.put(product,items.getOrDefault(product, 0)+quantity);
    }
    public double getTotalAmount(){
        double totalAmount=0.0;
        for(T product: items.keySet()){
            totalAmount+=product.getPrice()*items.get(product);
        }
        return totalAmount;
    }
}
class SalesManager<T extends Product> {
    private List<Sale<T>> sales;

    public List<Sale<T>> getSales() {
        return sales;
    }

    public void setSales(List<Sale<T>> sales) {
        this.sales = sales;
    }

    public SalesManager() {
        this.sales=new ArrayList<>();
    }
    public void recordSale(Sale<T> sale){
        sales.add(sale);
    }
    public double getDailyTotal(){
        double dailyTotal=0.0;
        for(Sale<T>sale:sales){
            dailyTotal+=sale.getTotalAmount();
        }
        return dailyTotal;
    }
    public void printSummary(){
        System.out.println("Total amount: "+getDailyTotal());
        System.out.println("Total sales: "+sales.size());
    }
}
