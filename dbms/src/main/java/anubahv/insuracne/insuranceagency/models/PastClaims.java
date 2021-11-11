package anubahv.insuracne.insuranceagency.models;

import java.util.Date;

public class PastClaims {
    private int recordId;
    private String firstName;
    private String lastName;
    private String email;
    private int amount;
    private String category;
    private Date dateOfClaim;
    private String assetDetails;

    public PastClaims() {
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDateOfClaim() {
        return dateOfClaim;
    }

    public void setDateOfClaim(Date dateOfClaim) {
        this.dateOfClaim = dateOfClaim;
    }

    public String getAssetDetails() {
        return assetDetails;
    }

    public void setAssetDetails(String assetDetails) {
        this.assetDetails = assetDetails;
    }
}
