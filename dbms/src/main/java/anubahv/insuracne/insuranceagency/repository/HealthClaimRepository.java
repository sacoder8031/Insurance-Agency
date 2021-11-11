package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.HealthClaim;

import java.util.List;

public interface HealthClaimRepository {
    public HealthClaim findClaim(int id);
    public void save(HealthClaim vehicleClaims);
    public void changeStatus(String status,int id);
    public List<String> getRelatedDocuments(int id);
    public void delete(int id);
    public List<HealthClaim> findAllOfUserByStatus(int userId,String status);
    List<HealthClaim> findAllByStatus(String active);
}
