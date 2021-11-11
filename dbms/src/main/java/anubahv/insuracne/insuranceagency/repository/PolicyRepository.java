package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.Policy;

import java.util.List;

public interface PolicyRepository {
    public List<Policy> findAll();
    public List<Policy> findByExpiration(String status);
    public List<Policy> findAllByCategory(String category);
    public List<Policy> findActiveByCategory(String category);
    public Policy findById(int policyId);
    public void changeExpirationStatus(String expirationStatus,int policyId);
    public void addPolicy(Policy policy);

}
