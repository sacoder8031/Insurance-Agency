package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.Property;

import java.util.Date;
import java.util.List;

public interface PropertyRepository {
    public List<Property> findByUser(int userId);
    public List<Property> findByUSerAndRecord(int userId,int recordId);
    public Property findById(int id);
    public Property findByRecord(int recordId);
    public int findRecordOnProperty(int id);
    public void save(Property property);
    public void changeRecord(int recordId,int id);
    public void delete(int id);

    void removeRecord(int propertyId);

    void removeExpirationRecord();
}
