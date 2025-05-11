import java.io.*;
import java.util.*;
public class Test {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        SpellChecker s=new SpellChecker("dictionary.txt");
        CensorModule c=new CensorModule("badwords.txt");
        int choice=-1;
        while(choice!=7){
            System.out.println("----------MENU----------");
            System.out.println("1.Add a word to dictionary");
            System.out.println("2.Remove a word from dictionary");
            System.out.println("3.Check and correct the text file");
            System.out.println("4.Add a word to expletives dictionary");
            System.out.println("5.Remove a word from expletives dictionary");
            System.out.println("6.Censor expletives from a text file");
            System.out.println("7.EXIT");
            choice=Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    System.out.println("Enter a word to add: ");
                    s.addWord(sc.nextLine());
                    break;
                case 2:
                    System.out.println("Enter a word to remove: ");
                    s.removeWord(sc.nextLine());
                    break;
                case 3:
                    System.out.println("Enter input file path: ");
                    String input=sc.nextLine();
                    System.out.println("Enter output file path: ");
                    String output=sc.nextLine();
                    s.processFile(input,output);
                    break;
                case 4:
                    System.out.println("Enter an expletive to add: ");
                    c.addExpletive(sc.nextLine());
                    break;
                case 5:
                    System.out.println("Enter an expletive to remove: ");
                    c.removeExpletive(sc.nextLine());
                    break;
                case 6:
                    System.out.println("Enter input file path: ");
                    String inputPath=sc.nextLine();
                    System.out.println("Enter output file path: ");
                    String outputPath=sc.nextLine();
                    c.censorFile(inputPath,outputPath);
                    break;
                case 7:
                    System.out.println("----------EXITING----------");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }


        }
    }
}
class SpellChecker{
    private HashSet<String> dictionary;
    private String dictionaryFilePath;

    public HashSet<String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashSet<String> dictionary) {
        this.dictionary = dictionary;
    }

    public String getDictionaryFilePath() {
        return dictionaryFilePath;
    }

    public void setDictionaryFilePath(String dictionaryFilePath) {
        this.dictionaryFilePath = dictionaryFilePath;
    }

    public SpellChecker(String dictionaryFilePath) {
        this.dictionaryFilePath = dictionaryFilePath;
        this.dictionary=new HashSet<>();
        try(BufferedReader br=new BufferedReader(new FileReader(dictionaryFilePath))){
            String line;
            while((line=br.readLine())!=null){
                dictionary.add(line.trim());
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void saveDictionary(){
        try(BufferedWriter br=new BufferedWriter(new FileWriter(dictionaryFilePath))){
            for(String s:dictionary){
                br.write(s);
                br.newLine();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void addWord(String word){
        if(!dictionary.contains(word)) {
            dictionary.add(word.toLowerCase());
        }
        saveDictionary();
    }
    public void removeWord(String word){
        if(dictionary.contains(word)) {
            dictionary.remove(word);
        }
        saveDictionary();
    }
    private int getLevenshteinDistance(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]),dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[m][n];
    }
    public List<String> suggestWords(String input){
        List<String>suggestWords=new ArrayList<>();
        for(String s:dictionary) {
            if (getLevenshteinDistance(s,input) <= 1) {
                suggestWords.add(s);
            }
        }
        return suggestWords;
    }
    public void processFile(String inputPath, String outputPath) {
        Scanner sc = new Scanner(System.in);
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath));
             BufferedWriter wr = new BufferedWriter(new FileWriter(outputPath))) {
            String line;
            boolean capitalizeNext = true;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                StringBuilder newLine = new StringBuilder();
                for (String s : parts) {
                    String word = s.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    String punctuation = s.replaceAll("[a-zA-Z]", "");
                    String replacement = word;
                    if (!dictionary.contains(word)) {
                        System.out.println("Misspelled word: " + word);
                        List<String> suggestions = suggestWords(word);
                        if (!suggestions.isEmpty()) {
                            System.out.println("Suggestion: " + suggestions.get(0));
                            System.out.println("Accept suggestion? (yes/no)");
                            String x = sc.nextLine();
                            if (x.equalsIgnoreCase("yes")) {
                                replacement = suggestions.get(0);
                            } else {
                                System.out.println("Enter your own replacement: ");
                                replacement = sc.nextLine();
                            }
                        }
                        else {
                            System.out.println("No suggestions found. Enter replacement: ");
                            replacement=sc.nextLine();
                        }
                    }
                    if (capitalizeNext && replacement.length() > 0) {
                        replacement = replacement.substring(0,1).toUpperCase() + replacement.substring(1);
                        capitalizeNext = false;
                    }

                    newLine.append(replacement).append(punctuation).append(" ");
                    if (punctuation.contains(".") || punctuation.contains("!") || punctuation.contains("?")) {
                        capitalizeNext = true;
                    }
                }
                wr.write(newLine.toString().trim());
                wr.newLine();
            }
            wr.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
class CensorModule{
    private HashSet<String> badWords;
    private String expletiveDictPath;

    public HashSet<String> getBadWords() {
        return badWords;
    }

    public void setBadWords(HashSet<String> badWords) {
        this.badWords = badWords;
    }

    public String getExpletiveDictPath() {
        return expletiveDictPath;
    }

    public void setExpletiveDictPath(String expletiveDictPath) {
        this.expletiveDictPath = expletiveDictPath;
    }

    public CensorModule(String expletiveDictPath) {
        this.expletiveDictPath = expletiveDictPath;
        this.badWords=new HashSet<>();
        try(BufferedReader br=new BufferedReader(new FileReader(expletiveDictPath))){
            String line;
            while((line=br.readLine())!=null){
                badWords.add(line.trim());
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void saveExpletiveDictionary(){
        try(BufferedWriter wr=new BufferedWriter(new FileWriter(expletiveDictPath))){
            for(String s:badWords){
                wr.write(s);
                wr.flush();

            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void addExpletive(String word){
        badWords.add(word);
        saveExpletiveDictionary();
    }
    public void removeExpletive(String word){
        if(badWords.contains(word)){
            badWords.remove(word);
            saveExpletiveDictionary();
        }
    }
    public void censorFile(String inputPath, String outputPath){
        try(BufferedReader br=new BufferedReader(new FileReader(inputPath));
            BufferedWriter wr=new BufferedWriter(new FileWriter(outputPath))){
            String line;
            boolean capitalizeNext = true;
            while((line= br.readLine())!=null){
                String[] parts=line.split("\\s+");
                StringBuilder newLine=new StringBuilder();
                for(String s:parts){
                    String word=s.replaceAll("[^a-zA-Z]","");
                    String punctuation=s.replaceAll("[a-zA-Z]","");
                    String replacement=word;
                    if(badWords.contains(word)){
                        replacement="[CENSORED]";
                    }
                    if (capitalizeNext && replacement.length() > 0) {
                        replacement = replacement.substring(0,1).toUpperCase() + replacement.substring(1);
                        capitalizeNext = false;
                    }
                    newLine.append(replacement).append(punctuation).append(" ");
                    if (punctuation.contains(".") || punctuation.contains("!") || punctuation.contains("?")) {
                        capitalizeNext = true;
                    }
                }
                wr.write(newLine.toString().trim());
                wr.newLine();

            }
            wr.flush();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
