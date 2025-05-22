package net.javaguides.pfm;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinanceManager {
    private List<Transaction> transactions;
    private final String filePath;

    public FinanceManager() {
        this.filePath = System.getProperty("user.home") + File.separator + "pfm_transactions.dat";
        this.transactions = new ArrayList<>();
        loadTransactions();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveTransactions();
        System.out.println("Transaction added successfully!");
    }

    public boolean editTransaction(int id, String type, double amount, String description, LocalDate date) {
        for (Transaction t : transactions) {
            if (t.getId() == id) {
                t.setType(type);
                t.setAmount(amount);
                t.setDescription(description);
                t.setDate(date);
                saveTransactions();
                return true;
            }
        }
        return false;
    }

    public boolean deleteTransaction(int id) {
        boolean removed = transactions.removeIf(t -> t.getId() == id);
        if (removed) {
            saveTransactions();
        }
        return removed;
    }

    public void listTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("\n--- All Transactions ---");
        transactions.forEach(System.out::println);
    }

    public void summarizeTransactions() {
        double income = 0, expense = 0;
        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("income")) {
                income += t.getAmount();
            } else if (t.getType().equalsIgnoreCase("expense")) {
                expense += t.getAmount();
            }
        }
        System.out.println("\n--- Financial Summary ---");
        System.out.printf("Total Income:  ₹%,.2f%n", income);
        System.out.printf("Total Expense: ₹%,.2f%n", expense);
        System.out.printf("Net Balance:   ₹%,.2f%n", (income - expense));
    }

    public int getNextId() {
        return transactions.isEmpty() ? 1 : transactions.get(transactions.size() - 1).getId() + 1;
    }

    private void saveTransactions() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(transactions);
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }

    private void loadTransactions() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            transactions = (List<Transaction>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
            transactions = new ArrayList<>();
        }
    }
}