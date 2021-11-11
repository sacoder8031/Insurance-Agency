package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.PolicyRecord;
import anubahv.insuracne.insuranceagency.models.Vehicle;
import anubahv.insuracne.insuranceagency.repository.PolicyRecordRepository;
import anubahv.insuracne.insuranceagency.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.type.NullType;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    VehicleRepository vehicleRepository;
    PolicyRecordRepository policyRecordRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, PolicyRecordRepository policyRecordRepository) {
        this.vehicleRepository = vehicleRepository;
        this.policyRecordRepository = policyRecordRepository;
    }

    @Override
    public List<Vehicle> getByUser(int userId) {
        return vehicleRepository.findByUser(userId);
    }

    @Override
    public Vehicle getById(int id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public PolicyRecord getRecordOnVehicle(int id) {
        return policyRecordRepository.getPolicyRecord(vehicleRepository.findRecordOnVehicle(id));
    }

    @Override
    public Vehicle getByRecord(int recordId) {
        return vehicleRepository.findByRecord(recordId);
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        vehicle.setRecordId(0);
        vehicleRepository.save(vehicle);
    }

    @Override
    public void changeRecord(int recordId, int id) {
        vehicleRepository.changeRecord(recordId,id);
    }

    @Override
    public void delete(int id) {
        vehicleRepository.delete(id);
    }

    @Override
    public void removeExpirationRecord() {
        vehicleRepository.removeExpirationRecord();
    }

    @Override
    public void removeRecord(int id) {
        vehicleRepository.removeRecord(id);
    }

    @Override
    public List<Vehicle> getVehicleForBuyingPolicy(int userId) {
        return vehicleRepository.findByUserAndRecord(userId, 0);
    }
}
