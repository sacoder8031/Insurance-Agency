package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.FAQ;

import java.util.List;

public interface FAQService {
    public List<FAQ> getAll();
    public List<FAQ> getHeathFaq();
    public List<FAQ> getPropertyFaq();
    public List<FAQ> getVehicleFaq();
    public List<FAQ> getLifeFaq();
    public void addFaq(FAQ faq);
}
