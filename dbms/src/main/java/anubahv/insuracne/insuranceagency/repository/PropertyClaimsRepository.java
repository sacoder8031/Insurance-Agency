package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.PropertyClaim;

import java.util.List;

public interface PropertyClaimsRepository {
    public PropertyClaim findClaim(int id);
    public void save(PropertyClaim propertyClaim);
    public void changeStatus(String status,int id);
    public List<String> getRelatedDocuments(int id);
    public void delete(int id);

    List<PropertyClaim> findAllOfUserByStatus(int userId, String status);

    List<PropertyClaim> findAllByStatus(String status);
}
