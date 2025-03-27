import java.util.*;
public class AssistantManager {
    private List<VirtualAssistant>assistants;
    public List<VirtualAssistant> getAssistants() {
        return assistants;
    }
    public void setAssistants(List<VirtualAssistant> assistants) {
        this.assistants = assistants;
    }
    public AssistantManager(List<VirtualAssistant> assistants) {
        this.assistants = new ArrayList<>(assistants);
    }
    public void addAssistant(VirtualAssistant assistant){
        assistants.add(assistant);
    }
    public void removeAssistant(VirtualAssistant assistant){
        assistants.remove(assistant);
    }
    public List<String> interactWithAll(String task){
        List<String> responses = new ArrayList<>();
        for (VirtualAssistant assistant : assistants) {
            String response = assistant.greetUser() + "\n" + assistant.performTask(task);
            responses.add(response);
            System.out.println(response);
        }
        return responses;
    }

    public static void main(String[] args) {
        List<VirtualAssistant> assistants = new ArrayList<>();
        assistants.add(new HomeAssistant("HomeAssistant", 1.0));
        assistants.add(new PersonalFinanceAssistant("FinanceAssistant", 1.0));
        assistants.add(new LanguageTranslatorAssistant("TranslateAssistant", 1.0));

        AssistantManager manager = new AssistantManager(assistants);
        manager.interactWithAll("turn on lights");
        manager.interactWithAll("show balance");
        manager.interactWithAll("translate hello to Spanish");
        manager.interactWithAll("deposit money 100");
        manager.interactWithAll("withdraw 50");
    }




}
abstract class VirtualAssistant{
    private String assistantName;
    private double version;


    public String getAssistantName() {
        return assistantName;
    }

    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
    public VirtualAssistant(String assistantName, double version) {
        this.assistantName = assistantName;
        this.version = version;
    }
    public abstract String greetUser();
    public abstract String performTask(String task);
}

class HomeAssistant extends VirtualAssistant{
    private boolean isLightOn;

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        isLightOn = lightOn;
    }

    public HomeAssistant(String assistantName, double version){
        super(assistantName, version);
        this.isLightOn=false;
    }
    @Override
    public String greetUser(){
        System.out.println(" ");
        return "Hello! I’m your Home Assistant. How can I help to control your home today? ";
    }
    @Override
    public String performTask(String task){
        switch (task) {
            case "turn on lights":
                if (isLightOn) {
                    return "The lights are already turned on.";
                }
                else {
                    isLightOn = true;
                    return "Turning on the lights!";
                }
            case "turn off lights":
                if(!isLightOn){
                    return "The lights are already turned off.";
                }
                else{
                    isLightOn=false;
                    return "Turning off the lights.";
                }
            default:
                return "Sorry,I can't do that.";



        }
    }

}

class PersonalFinanceAssistant extends VirtualAssistant{
    private double currentBalance;
    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
    public PersonalFinanceAssistant(String assistantName, double version) {
        super(assistantName, version);
        this.currentBalance = 500.0;
    }
    @Override
    public String greetUser(){
        System.out.println(" ");
        return "Hi! I’m your Finance Assistant. Let’s manage your money wisely!";
    }
    @Override
    public String performTask(String task){
        switch (task){
            case "show balance":
                return "Your current balance: "+currentBalance+" dollars";
            case "deposit money 100":
                currentBalance+=100;
                return "100 dollars is deposited into your account.Your current balance: "+currentBalance+" dollars";
            case "withdraw 50" :
                if(currentBalance<50){
                    return "Sorry, insufficient balance!";
                }
                else{
                    currentBalance-=50;
                    return "50 dollars is withdrawn from your account. Your current balance: "+currentBalance+" dollars.";

                }
            default:
                return "I don’t know how to do that.";

        }

    }



}

class LanguageTranslatorAssistant extends VirtualAssistant{
    private String lastTranslatedWord;
    public String getLastTranslatedWord() {
        return lastTranslatedWord;
    }
    public void setLastTranslatedWord(String lastTranslatedWord) {
        this.lastTranslatedWord = lastTranslatedWord;
    }
    public LanguageTranslatorAssistant(String assistantName, double version) {
        super(assistantName, version);
        this.lastTranslatedWord ="None";
    }
    @Override
    public String greetUser(){
        System.out.println(" ");
        return "Bonjour! Hola! Hello! I’m your Language Translator AI!";
    }
    @Override
    public String performTask(String task){
        if (task.equalsIgnoreCase("translate hello to Spanish")) {
            lastTranslatedWord = "Hola";
            return "Hello in Spanish is Hola.";
        } else if (task.equalsIgnoreCase("translate thank you to French")) {
            lastTranslatedWord = "Merci";
            return "Thank you in French is Merci.";
        } else {
            return "I don’t know that language yet.";
        }
    }


}


