import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        ShippingSystem shippingSystem=new ShippingSystem();

        StandardPackage standardPackage1=new StandardPackage("John Doe","Daniel White",3.5,"Los Angeles","USA","Air","New York","2025-04-01 10:00 AM");
        StandardPackage standardPackage2=new StandardPackage("Alice Johnson","Emily Harris",2.2,"Toronto","Canada","Ground","Chicago","2025-03-29 03:00 PM");
        StandardPackage standardPackage3=new StandardPackage("Michael Smith","Christopher Lee",5.8,"Sydney","Australia","Ground","Melbourne","2025-04-02 01:15 PM");
        ExpressPackage expressPackage1=new ExpressPackage("Emma Brown","Isabella Clark",6.2,"Tokyo","Japan",3,"San Francisco","2025-03-28 08:45 AM",500.0);
        ExpressPackage expressPackage2=new ExpressPackage("Robert Wilson","Matthew Lewis",4.8,"Berlin","Germany",1,"Paris","2025-04-02 03:30 PM",300.0);
        FragilePackage fragilePackage1=new FragilePackage("Sophia Martinez","Ava Walker",1.5,"Amsterdam","Netherlands",true,false,"Brussels","2025-04-05 10:00 AM",150.0,90.0);

        standardPackage1.updateLocation("Munich");
        standardPackage1.setEstimatedDeliveryTime("2025-04-02 02:00 PM");
        System.out.println("New "+standardPackage1.getTrackingInfo());
        System.out.println("Updated delivery time: "+standardPackage1.getEstimatedDeliveryTime());

        expressPackage1.insurePackage(800);
        expressPackage2.insurePackage(400);
        fragilePackage1.insurePackage(180);
        boolean claimApproved=expressPackage2.claimInsurance("damaged");
        System.out.println("Insurance Claim Approved: "+claimApproved);

        boolean refundApproved;
        double refundAmount;
        refundApproved=fragilePackage1.requestRefund("wanted");
        refundAmount= fragilePackage1.getRefundAmount();
        System.out.println("Refund Approved: "+refundApproved);
        System.out.println("Refund Amount: "+refundAmount);

        refundApproved=fragilePackage1.requestRefund("damaged");
        System.out.println("Refund Approved: "+refundApproved);
        System.out.println("Refund Amount: "+refundAmount);

        shippingSystem.addPackage(standardPackage1);
        shippingSystem.addPackage(standardPackage2);
        shippingSystem.addPackage(standardPackage3);
        shippingSystem.addPackage(expressPackage1);
        shippingSystem.addPackage(expressPackage2);
        shippingSystem.addPackage(fragilePackage1);

        shippingSystem.printAllPackages();

        shippingSystem.generateReport();

        standardPackage2.markDelivered();
        expressPackage1.markDelivered();
        fragilePackage1.markDelivered();
        shippingSystem.generateReport();
    }
}


abstract class Package{
    private String senderName;
    private String recipientName;
    private double weight;
    private boolean isDelivered=false;
    private String destinationCity;
    private String destinationCountry;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }
    public Package(){
        this.senderName=" ";
        this.recipientName=" ";
        this.weight=0.0;
        this.destinationCity=" ";
        this.destinationCountry=" ";
    }
    public Package(String senderName, String recipientName, double weight, String destinationCity, String destinationCountry) {
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.weight = weight;
        this.destinationCity = destinationCity;
        this.destinationCountry = destinationCountry;
    }

    public abstract double calculateShippingCost();
    public void markDelivered(){
        isDelivered=true;

    }

    public void printInfo(){
        System.out.println("Sender Name: "+senderName);
        System.out.println("Recipient Name: "+recipientName);
        System.out.println("Weight: "+weight);
        System.out.println("Is delivered: "+isDelivered);
    }
}


class StandardPackage extends Package implements Trackable{
    private String shippingType;
    private String currentLocation;
    private String estimatedDeliveryTime;

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
    public StandardPackage(String senderName, String recipientName, double weight, String destinationCity, String destinationCountry, String shippingType, String currentLocation, String estimatedDeliveryTime) {
        super(senderName, recipientName, weight,  destinationCity, destinationCountry);
        this.shippingType = "Ground";
        this.currentLocation = currentLocation;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
    @Override
    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }
    @Override
    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
    @Override
    public double calculateShippingCost() {
        return getWeight()*2.0;
    }

    @Override
    public String getTrackingInfo() {
        return "Current location: "+currentLocation;
    }

    @Override
    public void updateLocation(String newLocation) {
      currentLocation=newLocation;
    }
    @Override
    public void printInfo(){
        super.printInfo();
        System.out.println("Shipping Type: " + shippingType);
        System.out.println("Location: " + currentLocation);
        System.out.println("Delivery Time: "+estimatedDeliveryTime);

    }
}


class ExpressPackage extends Package implements Trackable,Insurable{
    private int priorityLevel;
    private String currentLocation;
    private String estimatedDeliveryTime;
    private double insuredValue;
    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setInsuredValue(double insuredValue) {
        this.insuredValue = insuredValue;
    }
    public ExpressPackage(String senderName, String recipientName, double weight, String destinationCity, String destinationCountry,int priorityLevel,String currentLocation,String estimatedDeliveryTime,double insuredValue){
        super(senderName, recipientName, weight, destinationCity, destinationCountry);
        this.priorityLevel=priorityLevel;
        this.currentLocation=currentLocation;
        this.estimatedDeliveryTime=estimatedDeliveryTime;
        this.insuredValue=insuredValue;
    }
    @Override
    public double calculateShippingCost(){
        return (getWeight()*5.0)+10;
    }
    @Override
    public String getTrackingInfo(){
        return "Current location: "+currentLocation;
    }
    @Override
    public void updateLocation(String newLocation) {
        currentLocation=newLocation;
    }
    @Override
    public void setEstimatedDeliveryTime(String dateTime){
        estimatedDeliveryTime=dateTime;
    }
    @Override
    public String getEstimatedDeliveryTime(){
        return estimatedDeliveryTime;
    }
    @Override
    public void insurePackage(double insuredValue){
        this.insuredValue=insuredValue;
    }
    @Override
    public double getInsuredValue(){
        return insuredValue;
    }
    @Override
    public boolean claimInsurance(String claimReason){
        if (claimReason.equalsIgnoreCase("lost")||claimReason.equalsIgnoreCase("damaged")){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Priority Level: " + priorityLevel);
        System.out.println("Location: " + currentLocation);
        System.out.println("Estimated Delivery Time: " + estimatedDeliveryTime);
        System.out.println("Insured Value: " + insuredValue);
    }
}


class FragilePackage extends Package implements Trackable, Insurable, Refundable{
    boolean requiresReinforcedBox;
    boolean requiresTemperatureControl;
    String currentLocation;
    String estimatedDeliveryTime;
    double insuredValue;
    double refundAmount;
    public FragilePackage(String senderName, String recipientName, double weight, String destinationCity, String destinationCountry,boolean requiresReinforcedBox,boolean requiresTemperatureControl,String currentLocation,String estimatedDeliveryTime,double insuredValue, double refundAmount){
        super(senderName, recipientName, weight, destinationCity, destinationCountry);
        this.requiresReinforcedBox=requiresReinforcedBox;
        this.requiresTemperatureControl=requiresTemperatureControl;
        this.currentLocation=currentLocation;
        this.estimatedDeliveryTime=estimatedDeliveryTime;
        this.insuredValue=insuredValue;
        this.refundAmount=refundAmount;
    }

    public boolean isRequiresReinforcedBox() {
        return requiresReinforcedBox;
    }

    public void setRequiresReinforcedBox(boolean requiresReinforcedBox) {
        this.requiresReinforcedBox = requiresReinforcedBox;
    }

    public boolean isRequiresTemperatureControl() {
        return requiresTemperatureControl;
    }

    public void setRequiresTemperatureControl(boolean requiresTemperatureControl) {
        this.requiresTemperatureControl = requiresTemperatureControl;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    @Override
    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    @Override
    public double getInsuredValue() {
        return insuredValue;
    }

    public void setInsuredValue(double insuredValue) {
        this.insuredValue = insuredValue;
    }

    @Override
    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }
    @Override
    public boolean requestRefund(String reason){
        if("damaged".equalsIgnoreCase(reason)){
            refundAmount = getWeight() * 0.6;
            return true;
        }
        else if("lost".equalsIgnoreCase(reason)){
            refundAmount=getWeight();
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void markDelivered() {
        super.markDelivered();
        System.out.println("Handle with care – Fragile item delivered!");
    }
    @Override
    public double calculateShippingCost(){
        return (getWeight() *2.0)+8.0;
    }
    @Override
    public String getTrackingInfo(){
        return "Tracking Info: " + currentLocation + " | Estimated Delivery: " + estimatedDeliveryTime;

    }
    @Override
    public void updateLocation(String newLocation) {
        currentLocation = newLocation;
    }
    @Override
    public void insurePackage(double insuredValue) {
        this.insuredValue = insuredValue;
    }
    @Override
    public boolean claimInsurance(String claimReason) {
        if ("lost".equalsIgnoreCase(claimReason) || "damaged".equalsIgnoreCase(claimReason)) {
            return true;
        }
        return false;
    }
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Reinforced Box Required: " + requiresReinforcedBox);
        System.out.println("Temperature Control Required: " + requiresTemperatureControl);
        System.out.println("Insured Value: " + insuredValue);
    }

}


interface Refundable{
    boolean requestRefund(String reason);
    double getRefundAmount();
    default void logRefundRequest(String packageIdentifier){
        System.out.println("[Default Refund Log] Refund request logged for package ID: " + packageIdentifier);
    }
}


interface Trackable{
     String getTrackingInfo();
     void updateLocation(String newLocation );
     void setEstimatedDeliveryTime(String dateTime);
     String getEstimatedDeliveryTime();
}


interface Insurable{
    void insurePackage(double insuredValue);
    double getInsuredValue();
    boolean claimInsurance(String claimReason);
    default void logInsuranceClaim(String packageIdentifier,String reason){
        System.out.println("[Default Insurance Log] Insurance claim for package: " + packageIdentifier + " | Reason: " + reason);
    }
}


class ShippingSystem{
    private ArrayList<Package>packages;

    public ShippingSystem() {
        this.packages = new ArrayList<>();
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<Package> packages) {
        this.packages = packages;
    }

    public void addPackage(Package p) {
        if (p == null) {
            System.out.println("Error: Cannot add the package.");
            return;
        }

        if (packages.contains(p)) {
            System.out.println("Warning: This package is already in the system.");
        } else {
            packages.add(p);
            System.out.println("Package successfully added.");
        }
    }

    public void removePackage(Package p) {
        if (p == null) {
            System.out.println("Error: Cannot remove the package.");
            return;
        }

        if (packages.remove(p)) {
            System.out.println("Package successfully removed.");
        } else {
            System.out.println("Warning: Package not found in the system.");
        }
    }

    public void printAllPackages(){
        System.out.println();
        for(Package p:packages){
            p.printInfo();
            System.out.println();
        }
    }
    public void generateReport(){
        int deliveredPackage=0;
        double shippingCost=0.0;
        for(Package p:packages){
            if(p.isDelivered()){
                deliveredPackage++;
            }
            shippingCost+= p.calculateShippingCost();
        }
        System.out.println("Stored Package: "+packages.size());
        System.out.println("Delivered Package: "+deliveredPackage);
        System.out.println("Average Shipping Cost: "+(shippingCost/packages.size()));
    }
}

