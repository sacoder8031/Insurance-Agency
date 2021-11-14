package sharabh.insuracne.insuranceagency.services;

import sharabh.insuracne.insuranceagency.models.PolicyRecord;
import sharabh.insuracne.insuranceagency.models.Property;

import java.util.List;

public interface PropertyService {
    public List<Property> getByUser(int userId);
    public List<Property> getByUserForBuyingPolicy(int userId);
    public Property getById(int id);
    public Property getByRecord(int recordId);
    public PolicyRecord getRecordOnProperty(int id);
    public void addProperty(Property property);
    public void changeRecord(int recordId,int id);
    public void delete(int id);

    void removeRecord(int propertyId);

    void removeExpirationRecord();
}
