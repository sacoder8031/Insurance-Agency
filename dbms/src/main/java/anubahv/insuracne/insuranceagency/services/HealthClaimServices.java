package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.HealthClaim;

import java.util.List;

public interface HealthClaimServices {
    public HealthClaim getClaim(int id);
    public void add(HealthClaim vehicleClaims);
    public void changeStatus(String status,int id);
    public List<String> getRelatedDocuments(int id);
    public void delete(int id);
    public List<HealthClaim> allActiveOfUser(int userId);

    List<HealthClaim> allProcessedOfUser(int userId);

    List<HealthClaim> getClaimsByStatus(String active);
}
