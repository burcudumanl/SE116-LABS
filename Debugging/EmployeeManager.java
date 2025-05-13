import java.io.*;
import java.util.Scanner;

abstract class Employee {
    private String name;
    private long id; //int changed to long

    public Employee(String name, long id) { //int changed to long
        this.name = name; //name changed to this.name
        this.id = id; //id changed to this.id
    }
    //setters added for name
    public void setName(String name) {
        this.name = name;
    }
    //setters added for id
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() { //int changed to long
        return id;
    }
    public abstract double calculateSalary();
}


class FullTimeEmployee extends Employee {
    private double monthlySalary;
    //getters added for monthlySalary
    public double getMonthlySalary() {
        return monthlySalary;
    }
    //setters added for monthlySalary
    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public FullTimeEmployee(String name, long id, double salary) { //int changed to long
        super(name, id);
        this.monthlySalary = salary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }
}


class PartTimeEmployee extends Employee {
    private double hoursWorked; //int changed to double
    private double hourlyRate;
    //getters added for hoursWorked
    public double getHoursWorked() {
        return hoursWorked;
    }
    //setters added for hoursWorked
    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
    //getters added for hourlyRate
    public double getHourlyRate() {
        return hourlyRate;
    }
    //setters added for hourlyRate
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public PartTimeEmployee(String name, long id, double hours, double rate) { //int changed to long and int changed to double
        super(name, id);
        hoursWorked = hours;
        hourlyRate = rate;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

public class EmployeeManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter employee type (full/part): ");
        String type = scanner.nextLine();

        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        System.out.print("Enter employee ID: ");
        long id = scanner.nextLong(); //scanner.nextInt() changed to scanner.nextLong()

        Employee emp = null;

        if (type.equalsIgnoreCase("full")) { // " == " changed to equalsIgnoreCase
            System.out.print("Enter monthly salary: ");
            double salary = scanner.nextDouble();
            emp = new FullTimeEmployee(name, id, salary);
        } else if (type.equalsIgnoreCase("part")) { // " == " changed to equalsIgnoreCase
            System.out.print("Enter hours worked: ");
            double hours = scanner.nextInt(); //scanner.nextInt() changed to scanner.nextDouble()

            System.out.print("Enter hourly rate: ");
            double rate = scanner.nextDouble();

            emp = new PartTimeEmployee(name, id, hours, rate);
        }
        double salary=0.0;
        salary = emp.calculateSalary();

        try {
            FileWriter fw = new FileWriter("employees.txt", true);
            fw.write(emp.getName() + "," + emp.getId() + "," + salary + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Failed to write to file");
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader("employees.txt"));
            String line;
            System.out.println("Saved employees:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("Name: " + parts[0] + ", ID: " + parts[1] + ", Salary: $" + parts[2]);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        scanner.close();
    }
}

