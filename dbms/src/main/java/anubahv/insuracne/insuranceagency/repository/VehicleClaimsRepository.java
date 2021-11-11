package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.VehicleClaims;

import java.util.List;

public interface VehicleClaimsRepository {
    public VehicleClaims findClaim(int id);
    public void save(VehicleClaims vehicleClaims);
    public void changeStatus(String status,int id);
    public List<String> getRelatedDocuments(int id);
    public void delete(int id);

    List<VehicleClaims> findAllOfUserByStatus(int userId, String status);

    List<VehicleClaims> getAllByStatus(String status);
}
