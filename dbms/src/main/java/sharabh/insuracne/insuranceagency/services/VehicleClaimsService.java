package sharabh.insuracne.insuranceagency.services;

import sharabh.insuracne.insuranceagency.models.VehicleClaims;

import java.util.List;

public interface VehicleClaimsService {
    VehicleClaims getClaim(int id);
    void add(VehicleClaims vehicleClaims);
    void changeStatus(String status,int id);
    List<String> getRelatedDocuments(int id);
    void delete(int id);
    List<VehicleClaims> allActiveOfUser(int userId);

    List<VehicleClaims> allProcessedOfUser(int userId);

    List<VehicleClaims> getClaimsByStatus(String status);
}
