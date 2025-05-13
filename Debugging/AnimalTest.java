import java.util.Scanner;
abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name; //name changed to this.name
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; //name changed to this.name
    }

    public abstract void speak();
}

class Dog extends Animal {
    private String breed;
    public Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public void speak() {
        System.out.println(getName() + " barks. Breed: " + breed.toUpperCase());
    }
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void speak() {
        System.out.println(getName() + " meows.");
    }
}

public class AnimalTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter animal type (dog/cat): ");
        String type = scanner.nextLine();

        System.out.print("Enter animal name: ");
        String name = scanner.nextLine();

        Animal animal = null;

        if (type.equalsIgnoreCase("dog")) { // "==" changed to equalsIgnoreCase
            System.out.print("Enter dog breed: ");
            String breed = scanner.nextLine();
            animal = new Dog(name, breed);
        } else if (type.equalsIgnoreCase("cat")) { // "==" changed to equalsIgnoreCase
            animal = new Cat(name);
        } else {
            System.out.println("Unknown animal type.");
            return; //return added
        }
        animal.speak();
        scanner.close();
    }
}
