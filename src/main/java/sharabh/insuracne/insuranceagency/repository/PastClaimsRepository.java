package sharabh.insuracne.insuranceagency.repository;

import sharabh.insuracne.insuranceagency.models.PastClaims;

import java.util.List;

public interface PastClaimsRepository {
    public List<PastClaims> findByUsername(String email);
    public List<PastClaims> findByName(String firstName,String lastName);
    public PastClaims findByRecord(int id);
    public void save(PastClaims pastClaims);
}
