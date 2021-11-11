package anubahv.insuracne.insuranceagency.models;

public class LifeInsuranceClaim {
    private int id;
    private String deathCertificateLocation;
    private int recordId;
    private int amount;
    private String status;

    public LifeInsuranceClaim() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeathCertificateLocation() {
        return deathCertificateLocation;
    }

    public void setDeathCertificateLocation(String deathCertificateLocation) {
        this.deathCertificateLocation = deathCertificateLocation;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
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
}
