package com.orque.ims.Invoice.InvoiceEntity;

import jakarta.persistence.Embeddable;

@Embeddable
public class InvoiceItem {
    private String description;
    private double cost;

    public InvoiceItem() {}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}