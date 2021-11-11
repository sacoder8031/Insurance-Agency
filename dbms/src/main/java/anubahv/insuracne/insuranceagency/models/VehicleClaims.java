package anubahv.insuracne.insuranceagency.models;

import java.util.Date;
import java.util.List;

public class VehicleClaims {
    private int id;
    private int damage;
    private int amount;
    private String status;
    private Date dateOfLoss;
    private int vehicleId;
    private int recordId;
    private List<String> linkToDocuments;

    public VehicleClaims() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateOfLoss() {
        return dateOfLoss;
    }

    public void setDateOfLoss(Date dateOfLoss) {
        this.dateOfLoss = dateOfLoss;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public List<String> getLinkToDocuments() {
        return linkToDocuments;
    }

    public void setLinkToDocuments(List<String> linkToDocuments) {
        this.linkToDocuments = linkToDocuments;
    }
}
