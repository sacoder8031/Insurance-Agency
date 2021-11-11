package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.PolicyRecord;

import java.util.Date;
import java.util.List;

public interface PolicyRecordRepository {
    public void save(PolicyRecord policyRecord);
    public PolicyRecord getPolicyRecord(int id);
    public PolicyRecord getPolicyRecord(int userId,int policyId);
    public void delete(int id);
    public void changeExpiryDate(Date date,int id);
    public List<PolicyRecord> findAllOfUser(int userId);
    public List<PolicyRecord> findAllOfUserOfCategory(int userId,String category);
    public void changeStatus(String status,int id);
    List<PolicyRecord> findActiveOfUserOfCategory(int userId,String category);

    void markExpiration(Date date);
}
