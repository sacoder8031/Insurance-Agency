package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.PropertyClaim;

import java.util.List;

public interface PropertyClaimsServices {
    public PropertyClaim getClaim(int id);
    public void add(PropertyClaim propertyClaim);
    public void changeStatus(String status,int id);
    public List<String> getRelatedDocuments(int id);
    public void delete(int id);
    List<PropertyClaim> allActiveOfUser(int userId);

    List<PropertyClaim> allProcessedOfUser(int userId);

    List<PropertyClaim> getClaimsByStatus(String active);
}
