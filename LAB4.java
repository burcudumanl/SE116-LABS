import java.util.*;

public class Character {
    private String name;
    private double hitPoint;
    private String gender;
    private int level=1;
    private int experience=0;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(double hitPoint) {
        if(hitPoint<0){
            System.out.println("Invalid hitPoint value.");
            return;
        }
        this.hitPoint=hitPoint;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
    public Character(){
        this.name="Undefined";
        this.hitPoint=1;
        this.gender="Undefined";
    }
    public Character(String name, double hitPoint, String gender) {
        this.name = name;
        if(this.hitPoint<0){
            System.out.println("Invalid hitPoint value.");
        } else{
            this.hitPoint=hitPoint;
        }
        this.gender = gender;

    }
    public double calculateDamage() {
        return hitPoint;
    }
    public void levelUp() {
        level++;
        experience = 0;
        System.out.println(name + " leveled up to " + level);
    }
    public void gainExperience(int xp) {
        experience += xp;
        if (experience >= 100) {
            levelUp();
        }
    }
    public void attack() {
        System.out.println("Attacking... Damage is: " + calculateDamage());
        gainExperience(20);
    }
    public void regeneratePower() {
        System.out.println("Regenerating Power");
    }
    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("HitPoint: "+ hitPoint);
        System.out.println("Gender: "+ gender);
        System.out.println("Level: "+ level);
    }
}
class Warrior extends Character{
    private int energy;
    private int defense;
    public Warrior(){
        super();
        this.energy=20;
    }
    public Warrior(String name, double hitPoint, String gender){
        super(name, hitPoint, gender);
        this.energy=20;
    }
    private void rest(){
        this.energy+=20;
        System.out.println("Energy: "+ this.energy);
    }

    @Override
    public double calculateDamage() {
        return getHitPoint()*1.2;
    }

    @Override
    public void attack() {
        if(energy<10){
            System.out.println("Not enough energy.Get rest... ");
        }else{
            energy-=10;
            super.attack();
            System.out.println(energy);
        }
    }

    @Override
    public void regeneratePower() {
        this.rest();
        super.regeneratePower();
    }

    @Override
    public void printInfo() {
        System.out.println("Type: Warrior\n");
        super.printInfo();
        System.out.println("Energy: "+energy);
    }
}


class Mage extends Character{
    private int mana;
    private double criticalChance;
    public Mage(){
        super();
        this.mana=10;
        this.criticalChance=0.1;
    }
    public Mage(String name, double hitPoint, String gender){
        super(name, hitPoint, gender);
        this.mana=10;
        this.criticalChance=0.1;
    }
    private void drinkPotion(){
        this.mana+=10;
        System.out.println("Mana: "+this.mana);
    }
    @Override
    public double calculateDamage(){
        return this.getHitPoint()*0.8;
    }

    @Override
    public void regeneratePower(){
        this.drinkPotion();
    }

    @Override
    public void attack(){
        if(mana<5){
            System.out.println("Not enough mana. Drink potion…");
        } else{
            this.mana-=5;
            super.attack();
            System.out.println("Mana: "+this.mana);
        }
    }

    @Override
    public void printInfo(){
        System.out.println("Type: Warrior\n");
        super.printInfo();
        System.out.println("Mana: "+this.mana);
    }
}
class Player{
    private String name;
    private String password;
    private ArrayList<Character> characters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
    public Player(){
        this.name="Undefined";
        this.password="Password";
        this.characters=new ArrayList<Character>();
    }

    public Player(String name, String password){
        this.name=name;
        this.password=password;
        this.characters=new ArrayList<Character>();
    }

    public Player(String name, String password, ArrayList<Character> characters){
        this.name=name;
        this.password=password;
        this.characters=characters;
    }

    public void printPlayerInfo () {
        System.out.println("Name: " + getName());
        System.out.println("Password: " + getPassword());
        System.out.println("Characters: " + getCharacters());
    }
    public double totalDamage() {
        double totalDamage = 0;
        for (int i= 0;i<characters.size();i++) {
            totalDamage+= characters.get(i).calculateDamage();
        }
        return totalDamage;
    }
    class Achievement {
        List<String > unlockedAchievements;
        void addAchievement(String achievement) {
            unlockedAchievements.add(achievement);
        }
    }
}
class Party {
    String partyName;
    ArrayList<Character> members;
    int powerBalance;
    int reputation;

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public ArrayList<Character> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Character> members) {
        this.members = members;
    }

    public int getPowerBalance() {
        return powerBalance;
    }

    public void setPowerBalance(int powerBalance) {
        this.powerBalance = powerBalance;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public Party(String partyName, ArrayList<Character> members, int powerBalance, int reputation) {
        this.partyName = partyName;
        this.members = members;
        this.powerBalance = powerBalance;
        this.reputation = reputation;
    }
    public Party(){

    }

    public void addMember(Character character){
        if(members.size()<11)  {
            members.add(character);
        } else {
            System.out.println("Party is full !");
        }
    }
    public void removeMember(Character character) {
        if(members.contains(character)) {
            members.remove(character);

        }
    }
    public void calculatePowerBalance(){
        int total = 0 ;
        for(Character character : members){
            total += character.calculateDamage();
        }
        this.powerBalance = total;
    }
    public void calculateReputation(){
        int total = 0;
        for (Character character : members){
            total += character.getLevel();
        }
        this.reputation = total;
    }
    public void printPartyInfo() {
        System.out.println("Party Name: " + partyName);
        System.out.println("Number of Members: " + members.size());
        System.out.println("Power Balance: " + powerBalance);
        System.out.println("Reputation: " + reputation);
        System.out.println("Party Members : ");
        for (Character character : members) {
            character.printInfo();
        }
    }
}
class Battle {
    private Party p1;
    private Party p2;
    private ArrayList<Character>teamA;
    private ArrayList<Character>teamB;
    public Battle(Party p1, Party p2,ArrayList<Character>teamA,ArrayList<Character>teamB){
        this.p1 = p1;
        this.p2 = p2;
        this.teamA = teamA;
        this.teamB = teamB;
    }
    public void formTeams(){
        ArrayList<Character>temp1 = new ArrayList<>(p1.getMembers());
        Collections.shuffle(temp1);
        ArrayList<Character>temp2 = new ArrayList<>(p2.getMembers());
        Collections.shuffle(temp2);
        for(int i = 0 ; i<3 && i<temp1.size() ; i++){
            temp1.add(temp1.get(i));
        }
        for(int i = 0 ; i<3 && i<temp2.size() ; i++){
            temp2.add(temp2.get(i));
        }
        System.out.println(" ");
        System.out.println("Teams formed: ");
        System.out.println("Team 1: " + p1.getPartyName() + " : "+teamA.size()+" members");
        System.out.println("Team 2: " + p2.getPartyName()+" : " + teamB.size()+" members");
    }
    public void startBattle(){
        Random r = new Random();
        System.out.println("Battle is starting..");
        while(!teamA.isEmpty()&& !teamB.isEmpty()){
            int attackingTeam = r.nextInt();
            if(attackingTeam == 0){
                Character attacker = teamA.get(r.nextInt(teamA.size()));
                Character defender = teamB.get(r.nextInt(teamB.size()));

                double damage = attacker.calculateDamage();
                System.out.println(attacker.getName()+" attacks " + defender.getName()+" for " + damage);
                defender.setHitPoint(defender.getHitPoint()-damage);
                if(defender.getHitPoint() <= 0){
                    System.out.println(defender.getName()+" is defeated!");
                    teamB.remove(defender);
                }
            }else{
                Character attacker = teamB.get(r.nextInt(teamB.size()));
                Character defender = teamA.get(r.nextInt(teamA.size()));
                double damage = attacker.calculateDamage();
                System.out.println(attacker.getName()+" attacks " +defender.getName()+" for "+ damage);
                defender.setHitPoint(defender.getHitPoint()-damage);
                if(defender.getHitPoint() <= 0){
                    System.out.println(defender.getName()+" is defeated");
                    teamA.remove(defender);
                }
            }
            System.out.println();
        }
    }
    public Party declareWinner(){
        if(teamA.isEmpty()&& !teamB.isEmpty()){
            System.out.println(p2.getPartyName()+"is winner");
            return p2;
        }else if(teamB.isEmpty()&& !teamA.isEmpty()){
            System.out.println(p1.getPartyName()+"is winner");
            return p1;
        }else{
            System.out.println("it is a tie or no teams left");
            return null;
        }
    }
}
class Game {
    public static void main (String[]args){
        Warrior w1 = new Warrior("Burcu",18.0,"female");
        Mage m1 = new Mage("Şule",15.0,"female");
        Warrior w2 = new Warrior("Ece", 12.0, "female");
        Mage m2 = new Mage("Selen",10.0,"female");

        ArrayList<Character>party1Members = new ArrayList<>();
        party1Members.add(w1);
        party1Members.add(m1);
        Party p1 = new Party("Warrior",party1Members,0,0);
        p1.calculatePowerBalance();
        p1.calculateReputation();

        ArrayList<Character>party2Members = new ArrayList<>();
        party2Members.add(w2);
        party2Members.add(m2);
        Party p2 = new Party("Knights",party2Members,0,0);
        p2.calculatePowerBalance();
        p2.calculateReputation();

        System.out.println("Party 1 info: ");
        p1.printPartyInfo();
        System.out.println(" ");
        System.out.println("Party 2 info: ");
        p2.printPartyInfo();
        ArrayList<Character> teamA = new ArrayList<>();
        ArrayList<Character> teamB = new ArrayList<>();

        Battle battle = new Battle(p1, p2, teamA, teamB);

        battle.formTeams();

        battle.startBattle();

        Party winner = battle.declareWinner();

        if(winner != null){
            System.out.println("Congrats to : " + winner.getPartyName()+" !!");
        }
    }
}
