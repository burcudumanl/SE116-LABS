import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JournalManager manager = new JournalManager(new ArrayList<>());

        manager.addEntry(new JournalEntry("Bridget Jones's Diary", "It was awesome!", LocalDateTime.now()));
        manager.addEntry(new JournalEntry("Girl Time", "It is cool!", LocalDateTime.now()));

        String fileName = "journalEntries.txt";

        manager.saveEntries(fileName);
        manager.loadEntries(fileName);
        manager.viewEntries();
        manager.exportToBinary("exported.bin");
        manager.importFromBinary("exported.bin");
        manager.flushToDiskUsingBuffer("buffered.txt");
    }
}

class JournalEntry implements Serializable {
    private String title;
    private String content;
    private transient LocalDateTime timestamp;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public JournalEntry(String title, String content, LocalDateTime timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Content: " + content + "\n"+
                "Timestamp: " + timestamp + "\n";
    }
}

class JournalManager {
    private ArrayList<JournalEntry> entries;

    public ArrayList<JournalEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<JournalEntry> entries) {
        this.entries = entries;
    }

    public JournalManager(ArrayList<JournalEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(JournalEntry entry) {
        entries.add(entry);
    }

    public void viewEntries() {
        System.out.println("Entries: ");
        for (JournalEntry e : entries) {
            System.out.println(e);
        }
    }

    public void saveEntries(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(entries);
            System.out.println("Entries saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving entries: " + e.getMessage());
        }
    }

    public void loadEntries(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            entries = (ArrayList<JournalEntry>) in.readObject();
            for (JournalEntry entry : entries) {
                entry.setTimestamp(LocalDateTime.now());
            }
            System.out.println("Entries loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading entries: " + e.getMessage());
        }
    }

    public void exportToBinary(String filename) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(filename))) {
            for (JournalEntry entry : entries) {
                out.writeUTF(entry.getTitle());
                out.writeUTF(entry.getContent());
            }
            System.out.println("Entries exported to binary file: " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting to binary: " + e.getMessage());
        }
    }

    public void importFromBinary(String filename) {
        try (DataInputStream in = new DataInputStream(new FileInputStream(filename))) {
            entries.clear();
            while (in.available() > 0) {
                String title = in.readUTF();
                String content = in.readUTF();
                LocalDateTime now = LocalDateTime.now();
                entries.add(new JournalEntry(title, content, now));
            }
            System.out.println("Entries imported from binary file: " + filename);
        } catch (IOException e) {
            System.out.println("Error importing from binary: " + e.getMessage());
        }
    }

    public void flushToDiskUsingBuffer(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (JournalEntry entry : entries) {
                writer.write(entry.toString());
                writer.write("--------------------------------------\n");
            }
            writer.flush();
            System.out.println("Entries flushed to disk (buffered): " + filename);
        } catch (IOException e) {
            System.out.println("Error flushing to disk: " + e.getMessage());
        }
    }
}
