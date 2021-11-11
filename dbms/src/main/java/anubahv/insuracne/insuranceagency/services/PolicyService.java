package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.Policy;

import java.util.List;

public interface PolicyService {
    public List<Policy> findAll();
    public List<Policy> findActivePolicies();
    public List<Policy> findExpiredPolicies();
    public List<Policy> findHealthPolicies();
    public List<Policy> findPropertyPolicies();
    public List<Policy> findVehiclePolicies();
    public List<Policy> findLifePolicies();
    public List<Policy> findActiveHealthPolicies();
    public List<Policy> findActivePropertyPolicies();
    public List<Policy> findActiveVehiclePolicies();
    public List<Policy> findActiveLifePolicies();
    public Policy findById(int policyId);
    public void changeExpirationStatus(String status,int policyId);
    public void addPolicy(Policy policy);

}
