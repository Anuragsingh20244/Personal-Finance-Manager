package net.javaguides.pfm;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private int id;
    private String type;
    private double amount;
    private String description;
    private LocalDate date;

    public Transaction(int id, String type, double amount, String description, LocalDate date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }

    public void setType(String type) { this.type = type; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return String.format("ID: %d | Date: %s | Type: %-7s | Amount: %10.2f | Description: %s",
                id, date.format(DATE_FORMATTER), type, amount, description);
    }
}