import java.io.*;
import java.util.*;

public class EMS {
    private String companyName;
    private Manager manager;
    private Set<Employee> employees;
    private Map<Employee, Double> salaryMap;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Map<Employee, Double> getSalaryMap() {
        return salaryMap;
    }

    public void setSalaryMap(Map<Employee, Double> salaryMap) {
        this.salaryMap = salaryMap;
    }

    public EMS(String companyName, Manager manager) {
        this.companyName = companyName;
        this.manager = manager;
        this.employees = new HashSet<>();
        this.salaryMap = new HashMap<>();
    }

    public void registerEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            employees.add(employee);
            salaryMap.put(employee, 0.0);
        }
    }

    public void removeEmployee(Employee employee) {
        if (employees.contains(employee)) {
            employees.remove(employee);
            salaryMap.remove(employee);
        }
    }

    public Employee findEmployeeByName(String name) {
        for (Employee emp : employees) {
            if (emp.getName().equalsIgnoreCase(name)) {
                return emp;
            }
        }
        return null;
    }



    public void calculateSalaries(String fileName) {
        Map<String, Double> dailySalaryMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 2) continue;

                String name = parts[0].trim();
                if (name.isEmpty()) {
                    System.err.println("Error: Employee name is missing in the file -> " + line);
                    continue;
                }
                double dailySalary = Double.parseDouble(parts[1].trim());

                dailySalaryMap.put(name, dailySalaryMap.getOrDefault(name, 0.0) + dailySalary);
            }

            for (Map.Entry<String, Double> entry : dailySalaryMap.entrySet()) {
                String name = entry.getKey();
                double totalDailySalary = entry.getValue();
                double monthlySalary = totalDailySalary ;

                Employee emp = findEmployeeByName(name);
                if (emp == null) {
                    emp = new Employee(employees.size() + 1, name);
                    registerEmployee(emp);
                }

                emp.setMonthlySalary(monthlySalary);
                salaryMap.put(emp, monthlySalary);
            }

            askForSalaryLimit();
            displaySalaries();

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error in reading file: " + e.getMessage());
        }
    }

    public void askForSalaryLimit() {
        if (manager == null) return;

        Scanner scanner = new Scanner(System.in);
        String response;
        while(true){
            System.out.println("Would you like to assign a salary limit? ");
            System.out.println("Please enter Yes or No: ");
            response = scanner.nextLine();

            if (response.equalsIgnoreCase("Yes") || response.equalsIgnoreCase("No")) {
                break;
            } else {
                System.out.println("Invalid input! Please enter Yes or No.");
            }
        }

        if (response.equalsIgnoreCase("Yes")) {
            while (true) {
                System.out.println("Enter the salary limit: ");

                while (!scanner.hasNextDouble()) {
                    System.out.println("Please enter a valid number for the salary limit.");
                    scanner.next();
                }
                double salaryLimit = scanner.nextDouble();

                scanner.nextLine();

                manager.setSalaryLimit(salaryLimit);

                if (manager.getSalaryLimit() <= 0) {
                    System.out.println("Error: Salary limit must be a positive number.");
                } else {
                    break;
                }
            }


        }
        double salaryLimit = manager.getSalaryLimit();
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (salaryMap.get(emp) > salaryLimit) {
                System.out.println("WARNING for: " + emp.getName() +
                        "  (the salary limit " + salaryLimit + ") had been exceeded. The employee fired from the company.");
                iterator.remove();
                employees.remove(emp);
                salaryMap.remove(emp);
            }

        }
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter manager name: ");
        System.out.println("Enter manager id: ");
        System.out.println("Enter company name: ");
        String name=sc.next();
        long id =sc.nextLong();
        String companyName=sc.next();
        Manager manager = new Manager(id, "name");
        EMS ems = new EMS("companyName", manager);
        ems.calculateSalaries("src/LAB3.csv");

    }

    public void displaySalaries() {
        System.out.println("Employee Salaries:");
        for (Map.Entry<Employee, Double> entry : salaryMap.entrySet()) {
            Employee emp = entry.getKey();
            double salary = entry.getValue();
            System.out.println("Employee: " + emp.getName() + " - Monthly Salary: " + salary);
        }
    }
}



class Employee {
    private long id;
    private double monthlySalary;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Employee(long id, String name) {
        this.id = id;
        this.name = name;
        this.monthlySalary = 0.0;
    }
}



class Manager {
    private long id;
    private String name;
    private double salaryLimit;

    public double getSalaryLimit() {
        return salaryLimit;
    }

    public void setSalaryLimit(double salaryLimit) {
        this.salaryLimit = salaryLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Manager(long id, String name) {
        this.id = id;
        this.name = name;
        this.salaryLimit = 0.0;
    }
}

