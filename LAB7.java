import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem(new ArrayList<>(), new ArrayList<>());
        try {
            LibraryMember libraryMember1 = new LibraryMember("Burcu", 205);
            LibraryMember libraryMember2 = new LibraryMember("Begüm", 102);
            LibraryMember libraryMember3 = new LibraryMember("Burcu", 203);

            librarySystem.registerMember(libraryMember1);
            librarySystem.registerMember(libraryMember2);
            librarySystem.registerMember(libraryMember3);

            librarySystem.registerMember(new LibraryMember("Burcu", 203));
        } catch (DuplicateMemberException e) {
            System.out.println(e.getMessage());
        }
        try {
            LibraryItem libraryItem1 = new LibraryItem("Inception", "D001");
            LibraryItem libraryItem2 = new LibraryItem("National Geographic", "M002");
            LibraryItem libraryItem3 = new LibraryItem("Scientific American", "D001");

            librarySystem.addNewItem(libraryItem1);
            librarySystem.addNewItem(libraryItem2);
            librarySystem.addNewItem(libraryItem3);

            librarySystem.addNewItem(new LibraryItem("Inception", "D001"));
        } catch (DuplicateItemException e) {
            System.out.println(e.getMessage());
        }
        try {
            librarySystem.borrowItem(204, "M002");
        } catch (UserNotFoundException | ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            librarySystem.borrowItem(102, "C201");
        } catch (ItemNotFoundException | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println();
            librarySystem.addNewItem(new LibraryItem("Book 1", "B001"));
            librarySystem.addNewItem(new LibraryItem("Book 2", "B002"));
            librarySystem.addNewItem(new LibraryItem("Book 3", "B003"));
            librarySystem.addNewItem(new LibraryItem("Book 4", "B004"));
            librarySystem.addNewItem(new LibraryItem("Book 5", "B005"));

            librarySystem.borrowItem(102, "B001");
            librarySystem.borrowItem(102, "B002");
            librarySystem.borrowItem(102, "B003");
            librarySystem.borrowItem(102, "B004");
            librarySystem.borrowItem(102, "B005");
        } catch (OverLimitException | DuplicateItemException |UserNotFoundException|ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println();
            librarySystem.registerMember(new LibraryMember(" ", 209));
        } catch (InvalidMemberNameException | DuplicateMemberException e) {
            System.out.println(e.getMessage());
        }
        try {
            librarySystem.addNewItem(new LibraryItem(" ", "D009"));
        } catch (InvalidItemTitleException | DuplicateItemException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nLibrary Members:");
        librarySystem.printAllMembers();
        System.out.println("\nLibrary Items:");
        librarySystem.printAllItems();
    }
}
class LibrarySystem {
    private ArrayList<LibraryMember>libraryMembers;
    private ArrayList<LibraryItem>libraryItems;
    public ArrayList<LibraryMember> getLibraryMembers() {
        return libraryMembers;
    }
    public void setLibraryMembers(ArrayList<LibraryMember> libraryMembers) {
        this.libraryMembers = libraryMembers;
    }

    public ArrayList<LibraryItem> getLibraryItems() {
        return libraryItems;
    }

    public void setLibraryItems(ArrayList<LibraryItem> libraryItems) {
        this.libraryItems = libraryItems;
    }
    public LibrarySystem(ArrayList<LibraryMember> libraryMembers, ArrayList<LibraryItem> libraryItems) {
        this.libraryMembers = libraryMembers;
        this.libraryItems = libraryItems;
    }
    public void registerMember(LibraryMember libraryMember) throws DuplicateMemberException{
        for(LibraryMember m:libraryMembers) {
            if (m.getMemberId() == libraryMember.getMemberId()) {
                throw new DuplicateMemberException("Member with this ID already exists!");
            }
        }
            libraryMembers.add(libraryMember);

    }
    public void addNewItem(LibraryItem libraryItem) throws DuplicateItemException{
        for(LibraryItem i:libraryItems) {
            if (i.getItemId() == libraryItem.getItemId()) {
                throw new DuplicateItemException("Item with this ID already exists!");
            }
        }
        libraryItems.add(libraryItem);

    }
    public LibraryMember findMemberById(int memberId) throws UserNotFoundException{
        for(LibraryMember m:libraryMembers){
            if(m.getMemberId()==memberId){
                return m;
            }
        }
        throw new UserNotFoundException("User not found!");
    }
    public LibraryItem findItemById(String itemId) throws ItemNotFoundException{
        for(LibraryItem i:libraryItems){
            if(i.getItemId()==itemId){
                return i;
            }
        }
        throw new ItemNotFoundException("Item not found!");
    }
    public void borrowItem(int memberId,String itemId) throws UserNotFoundException,ItemNotFoundException,OverLimitException{
        LibraryMember libraryMember=findMemberById(memberId);
        LibraryItem libraryItem=findItemById(itemId);
        if (libraryMember.getBorrowedCount() >= libraryMember.getBorrowLimit()) {
            throw new OverLimitException("User with ID: " + memberId + " has reached the borrow limit!");
        }
        libraryItem.borrowItem();
        libraryMember.setBorrowedCount(libraryMember.getBorrowedCount() + 1);
    }
    public void returnItem(int memberId,String itemId)throws UserNotFoundException,ItemNotFoundException{
        LibraryMember libraryMember=findMemberById(memberId);
        LibraryItem libraryItem=findItemById(itemId);
        libraryItem.returnItem();
        libraryMember.setBorrowedCount(libraryMember.getBorrowedCount() - 1);
    }
    public void printAllMembers(){
        for(LibraryMember m:libraryMembers){
            System.out.println("Library Member ID: "+m.getMemberId()+", Library Member Name: "+m.getName());
        }
    }
    public void printAllItems(){
        for(LibraryItem i:libraryItems){
            System.out.println("Item ID: "+i.getItemId()+", Item Name: "+i.getTitle());
        }
    }

}
class LibraryMember {
    private String name;
    private int memberId;
    private int borrowedCount;
    private int borrowLimit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name==null||name.trim().isEmpty()){
            throw new InvalidMemberNameException("Empty name!");
        }
        for (int i = 0; i < name.length(); i++) {
            char b = name.charAt(i);
            if (!Character.isLetter(b) && !Character.isWhitespace(b)) {
                throw new InvalidMemberNameException("Invalid name format!");
            }
        }
        this.name = name;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    public int getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(int borrowLimit) {
        this.borrowLimit = borrowLimit;
    }
    public LibraryMember(String name,int memberId){
        this.borrowedCount=0;
        this.borrowLimit=5;
        setName(name);
        this.memberId=memberId;
    }

}
class LibraryItem{
    private String title;
    private String itemId;
    private boolean isBorrowed;
    public void setTitle(String title){
        if(title==null||title.isEmpty()){
            throw new InvalidItemTitleException("Empty title!");
        }
        for(int i=0;i<title.length();i++){
            char b=title.charAt(i);
            if(!Character.isLetterOrDigit(b)&&!Character.isWhitespace(b)) {
                throw new InvalidItemTitleException("Invalid title!");
            }
        }
        this.title=title;
    }
    public String getTitle(){
        return title;
    }
    public void setItemId(String itemId){
        this.itemId=itemId;
    }
    public String getItemId(){
        return itemId;
    }
    public void setBorrowed(Boolean isBorrowed){
        this.isBorrowed=isBorrowed;
    }
    public boolean isBorrowed(){
        return isBorrowed;
    }
    public LibraryItem(String title,String itemId){
        setTitle(title);
        this.itemId=itemId;
        this.isBorrowed=false;
    }
    public void borrowItem(){
        if(isBorrowed){
            System.out.println("Title: "+title+" ItemID: "+itemId+" is already borrowed!");
        }
        else{
            isBorrowed=true;
            System.out.println("Title: "+title+" ItemID: "+itemId+" has been borrowed.");
        }
    }
    public void returnItem(){
        if(!isBorrowed){
            System.out.println("Title: "+title+" ItemID: "+itemId+" was not borrowed.");
        }
        else{
            isBorrowed=false;
            System.out.println("Title: "+title+" ItemID: "+itemId+" has been returned.");
        }
    }
    public void displayInfo(){
        System.out.println("Title: "+title);
        System.out.println("ItemID: "+itemId);
        System.out.println("Is borrowed: "+(isBorrowed ? "Yes" : "No"));
    }
}
class UserNotFoundException extends Exception{
    public UserNotFoundException(String message){
        super(message);
    }
}
class ItemNotFoundException extends Exception{
    public ItemNotFoundException(String message){
        super(message);
    }
}
class DuplicateMemberException extends Exception{
    public DuplicateMemberException(String message){
        super(message);
    }
}
class DuplicateItemException extends Exception{
    public DuplicateItemException(String message){
        super(message);
    }
}
class OverLimitException extends RuntimeException{
    public OverLimitException(String message){
        super(message);
    }
}
class InvalidMemberNameException extends RuntimeException{
    public InvalidMemberNameException(String message){
        super(message);
    }
}
class InvalidItemTitleException extends RuntimeException{
    public InvalidItemTitleException(String message){
        super(message);
    }
}



