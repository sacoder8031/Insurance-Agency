package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.HealthClaim;
import anubahv.insuracne.insuranceagency.repository.HealthClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthClaimServicesImpl implements HealthClaimServices {
    HealthClaimRepository healthClaimRepository;

    @Autowired
    public HealthClaimServicesImpl(HealthClaimRepository healthClaimRepository) {
        this.healthClaimRepository = healthClaimRepository;
    }

    @Override
    public HealthClaim getClaim(int id) {
        return healthClaimRepository.findClaim(id);
    }

    @Override
    public void add(HealthClaim vehicleClaims) {
        healthClaimRepository.save(vehicleClaims);
    }

    @Override
    public void changeStatus(String status, int id) {
        healthClaimRepository.changeStatus(status, id);
    }

    @Override
    public List<String> getRelatedDocuments(int id) {
        return healthClaimRepository.getRelatedDocuments(id);
    }

    @Override
    public void delete(int id) {
        healthClaimRepository.delete(id);
    }

    @Override
    public List<HealthClaim> allProcessedOfUser(int userId) {
        return healthClaimRepository.findAllOfUserByStatus(userId,"processed");
    }

    @Override
    public List<HealthClaim> getClaimsByStatus(String active) {
        return healthClaimRepository.findAllByStatus("active");
    }

    @Override
    public List<HealthClaim> allActiveOfUser(int userId) {
        return healthClaimRepository.findAllOfUserByStatus(userId,"active");
    }
}
