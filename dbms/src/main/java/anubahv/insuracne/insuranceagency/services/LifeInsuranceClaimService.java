package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.LifeInsuranceClaim;

import java.util.List;

public interface LifeInsuranceClaimService {
    public void add(LifeInsuranceClaim lifeInsuranceClaim);
    public void delete(int id);
    public void updateStatus(String status,int id);
    public LifeInsuranceClaim get(int id);
    public String getDeathCertificate(int id);

    List<LifeInsuranceClaim> getClaimsByStatus(String active);
}
