package anubahv.insuracne.insuranceagency.models;

public class Policy {
    int id;
    String name;
    String Category;
    int premium;
    int maxClaimAmount;
    String thingsCovered;
    String expirationStatus;

    public Policy() {
    }

    public Policy(String name, String category, int premium, int maxClaimAmount, String thingsCovered, String expirationStatus) {
        this.name = name;
        Category = category;
        this.premium = premium;
        this.maxClaimAmount = maxClaimAmount;
        this.thingsCovered = thingsCovered;
        this.expirationStatus = expirationStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public int getMaxClaimAmount() {
        return maxClaimAmount;
    }

    public void setMaxClaimAmount(int maxClaimAmount) {
        this.maxClaimAmount = maxClaimAmount;
    }

    public String getThingsCovered() {
        return thingsCovered;
    }

    public void setThingsCovered(String thingsCovered) {
        this.thingsCovered = thingsCovered;
    }

    public String getExpirationStatus() {
        return expirationStatus;
    }

    public void setExpirationStatus(String expirationStatus) {
        this.expirationStatus = expirationStatus;
    }
}
