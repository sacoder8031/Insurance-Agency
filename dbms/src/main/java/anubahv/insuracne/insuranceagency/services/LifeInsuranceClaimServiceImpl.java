package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.LifeInsuranceClaim;
import anubahv.insuracne.insuranceagency.repository.LifeInsuranceClaimsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LifeInsuranceClaimServiceImpl implements LifeInsuranceClaimService {
    LifeInsuranceClaimsRepository lifeInsuranceClaimsRepository;

    @Autowired
    public LifeInsuranceClaimServiceImpl(LifeInsuranceClaimsRepository lifeInsuranceClaimsRepository) {
        this.lifeInsuranceClaimsRepository = lifeInsuranceClaimsRepository;
    }

    @Override
    public void add(LifeInsuranceClaim lifeInsuranceClaim) {
        lifeInsuranceClaimsRepository.save(lifeInsuranceClaim);
    }

    @Override
    public void delete(int id) {
        lifeInsuranceClaimsRepository.delete(id);
    }

    @Override
    public void updateStatus(String status, int id) {
        lifeInsuranceClaimsRepository.updateStatus(status, id);
    }

    @Override
    public LifeInsuranceClaim get(int id) {
        return lifeInsuranceClaimsRepository.findById(id);
    }

    @Override
    public String getDeathCertificate(int id) {
        return lifeInsuranceClaimsRepository.findDocuments(id);
    }

    @Override
    public List<LifeInsuranceClaim> getClaimsByStatus(String status) {
        return lifeInsuranceClaimsRepository.findAllByStatus(status);
    }
}
