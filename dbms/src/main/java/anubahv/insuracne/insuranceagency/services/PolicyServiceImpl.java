package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.Policy;
import anubahv.insuracne.insuranceagency.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyServiceImpl implements PolicyService {
    PolicyRepository policyRepository;

    @Autowired
    public PolicyServiceImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public List<Policy> findAll() {
        return policyRepository.findAll();
    }

    @Override
    public List<Policy> findActivePolicies() {
        return policyRepository.findByExpiration("active");
    }

    @Override
    public List<Policy> findExpiredPolicies() {
        return policyRepository.findByExpiration("expired");
    }

    @Override
    public List<Policy> findHealthPolicies() {
        return policyRepository.findAllByCategory("health");
    }

    @Override
    public List<Policy> findPropertyPolicies() {
        return policyRepository.findAllByCategory("property");
    }

    @Override
    public List<Policy> findVehiclePolicies() {
        return policyRepository.findAllByCategory("vehicle");
    }

    @Override
    public List<Policy> findLifePolicies() {
        return policyRepository.findAllByCategory("life");
    }

    @Override
    public List<Policy> findActiveHealthPolicies() {
        return policyRepository.findActiveByCategory("health");
    }

    @Override
    public List<Policy> findActivePropertyPolicies() {
        return policyRepository.findActiveByCategory("Property");
    }

    @Override
    public List<Policy> findActiveVehiclePolicies() {
        return policyRepository.findActiveByCategory("vehicle");
    }

    @Override
    public List<Policy> findActiveLifePolicies() {
        return policyRepository.findActiveByCategory("life");
    }

    @Override
    public Policy findById(int policyId) {
        return policyRepository.findById(policyId);
    }

    @Override
    public void changeExpirationStatus(String status,int policyId) {
        policyRepository.changeExpirationStatus(status,policyId);
    }

    @Override
    public void addPolicy(Policy policy) {
        policyRepository.addPolicy(policy);
    }


}
