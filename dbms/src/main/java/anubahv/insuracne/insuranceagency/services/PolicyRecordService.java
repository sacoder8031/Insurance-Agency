package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.PolicyRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PolicyRecordService {
    public List<PolicyRecord> getAllOfUser(int userId);
    public List<PolicyRecord> getHealthRecordsOfUser(int userId);
    public List<PolicyRecord> getVehicleRecordsOfUser(int userId);
    public List<PolicyRecord> getPropertyRecordsOfUser(int userId);
    public List<PolicyRecord> getLifeRecordsOfUser(int userId);
    public void changeStatus(String status,int id);
    public void changeExpiryDate(Date date,int id);
    public void addRecord(PolicyRecord policyRecord);
    public PolicyRecord getPolicyRecord(int id);
    public PolicyRecord getPolicyRecord(int userId,int policyId);
    public void deleteRecord(int id);

    Map<PolicyRecord,String> getActiveOfUserWithCategory(int Userid);

    void markExpiration();
}
