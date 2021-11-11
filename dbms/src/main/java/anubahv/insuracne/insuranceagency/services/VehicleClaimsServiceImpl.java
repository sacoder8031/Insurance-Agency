package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.VehicleClaims;
import anubahv.insuracne.insuranceagency.repository.VehicleClaimsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleClaimsServiceImpl implements VehicleClaimsService {
    VehicleClaimsRepository vehicleClaimsRepository;

    @Autowired
    public VehicleClaimsServiceImpl(VehicleClaimsRepository vehicleClaimsRepository) {
        this.vehicleClaimsRepository = vehicleClaimsRepository;
    }

    @Override
    public VehicleClaims getClaim(int id) {
        return vehicleClaimsRepository.findClaim(id);
    }

    @Override
    public void add(VehicleClaims vehicleClaims) {
        vehicleClaimsRepository.save(vehicleClaims);
    }

    @Override
    public void changeStatus(String status, int id) {
        vehicleClaimsRepository.changeStatus(status,id);
    }

    @Override
    public List<String> getRelatedDocuments(int id) {
        return vehicleClaimsRepository.getRelatedDocuments(id);
    }

    @Override
    public void delete(int id) {
        vehicleClaimsRepository.delete(id);
    }

    @Override
    public List<VehicleClaims> allProcessedOfUser(int userId) {
        return vehicleClaimsRepository.findAllOfUserByStatus(userId,"processed");
    }

    @Override
    public List<VehicleClaims> getClaimsByStatus(String status) {
        return vehicleClaimsRepository.getAllByStatus(status);
    }

    @Override
    public List<VehicleClaims> allActiveOfUser(int userId) {
        return vehicleClaimsRepository.findAllOfUserByStatus(userId,"active");
    }
}
