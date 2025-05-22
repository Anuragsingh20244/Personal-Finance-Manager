package net.javaguides.pfm;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        FinanceManager manager = new FinanceManager();
        System.out.println("Personal Finance Manager");

        while (true) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1 -> addTransaction(manager);
                    case 2 -> editTransaction(manager);
                    case 3 -> deleteTransaction(manager);
                    case 4 -> manager.listTransactions();
                    case 5 -> manager.summarizeTransactions();
                    case 6 -> {
                        System.out.println("Exiting program. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1-6).");
                scanner.nextLine(); 
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n Main Menu ");
        System.out.println("1. Add Transaction");
        System.out.println("2. Edit Transaction");
        System.out.println("3. Delete Transaction");
        System.out.println("4. List All Transactions");
        System.out.println("5. Financial Summary");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }

    private static void addTransaction(FinanceManager manager) {
        System.out.println("\n Add New Transaction ");
        
        String type = getValidType();
        double amount = getValidAmount();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        LocalDate date = getValidDate();

        manager.addTransaction(new Transaction(
            manager.getNextId(),
            type,
            amount,
            description,
            date
        ));
    }

    private static void editTransaction(FinanceManager manager) {
        System.out.println("\n Edit Transaction ");
        int id = getValidTransactionId();

        String type = getValidType();
        double amount = getValidAmount();
        System.out.print("Enter new description: ");
        String description = scanner.nextLine();
        LocalDate date = getValidDate();

        boolean success = manager.editTransaction(id, type, amount, description, date);
        System.out.println(success ? "Transaction updated successfully!" : "Transaction not found!");
    }

    private static void deleteTransaction(FinanceManager manager) {
        System.out.println("\n Delete Transaction ");
        int id = getValidTransactionId();

        boolean success = manager.deleteTransaction(id);
        System.out.println(success ? "Transaction deleted successfully!" : "Transaction not found!");
    }

    private static String getValidType() {
        while (true) {
            System.out.print("Enter type (Income/Expense): ");
            String type = scanner.nextLine().trim();
            if (type.equalsIgnoreCase("income") || type.equalsIgnoreCase("expense")) {
                return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
            }
            System.out.println("Invalid type! Must be 'Income' or 'Expense'.");
        }
    }

    private static double getValidAmount() {
        while (true) {
            try {
                System.out.print("Enter amount: ₹");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                if (amount >= 0) {
                    return amount;
                }
                System.out.println("Amount cannot be negative!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount! Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static LocalDate getValidDate() {
        while (true) {
            try {
                System.out.print("Enter date (YYYY-MM-DD) [Today if blank]: ");
                String dateStr = scanner.nextLine();
                if (dateStr.isEmpty()) {
                    return LocalDate.now();
                }
                return LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use YYYY-MM-DD.");
            }
        }
    }

    private static int getValidTransactionId() {
        while (true) {
            try {
                System.out.print("Enter transaction ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                if (id > 0) {
                    return id;
                }
                System.out.println("ID must be a positive number!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid ID! Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}