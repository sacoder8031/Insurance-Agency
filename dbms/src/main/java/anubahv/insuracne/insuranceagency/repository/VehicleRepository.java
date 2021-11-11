package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.PolicyRecord;
import anubahv.insuracne.insuranceagency.models.Vehicle;
import org.springframework.lang.Nullable;

import java.util.List;

public interface VehicleRepository {
    public List<Vehicle> findByUser(int userId);
    public List<Vehicle> findByUserAndRecord(int userId,@Nullable int record_id);
    public Vehicle findById(int id);
    public int findRecordOnVehicle(int id);
    public void save(Vehicle vehicle);
    public void changeRecord(int recordId,int id);
    public void delete(int id);
    void removeRecord(int id);
    public Vehicle findByRecord(int recordId);

    void removeExpirationRecord();
}
