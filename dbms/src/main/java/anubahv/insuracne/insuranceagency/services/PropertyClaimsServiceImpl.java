package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.PropertyClaim;
import anubahv.insuracne.insuranceagency.repository.PropertyClaimsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyClaimsServiceImpl implements PropertyClaimsServices {
    PropertyClaimsRepository propertyClaimsRepository;

    @Autowired
    public PropertyClaimsServiceImpl(PropertyClaimsRepository propertyClaimsRepository) {
        this.propertyClaimsRepository = propertyClaimsRepository;
    }

    @Override
    public PropertyClaim getClaim(int id) {
        return propertyClaimsRepository.findClaim(id);
    }

    @Override
    public void add(PropertyClaim propertyClaim) {
        propertyClaimsRepository.save(propertyClaim);
    }

    @Override
    public void changeStatus(String status, int id) {
        propertyClaimsRepository.changeStatus(status,id);
    }

    @Override
    public List<String> getRelatedDocuments(int id) {
        return propertyClaimsRepository.getRelatedDocuments(id);
    }

    @Override
    public void delete(int id) {
        propertyClaimsRepository.delete(id);
    }

    @Override
    public List<PropertyClaim> allProcessedOfUser(int userId) {
        return propertyClaimsRepository.findAllOfUserByStatus(userId,"processed");
    }

    @Override
    public List<PropertyClaim> getClaimsByStatus(String status) {
        return propertyClaimsRepository.findAllByStatus(status);
    }

    @Override
    public List<PropertyClaim> allActiveOfUser(int userId) {
        return propertyClaimsRepository.findAllOfUserByStatus(userId,"active");
    }
}