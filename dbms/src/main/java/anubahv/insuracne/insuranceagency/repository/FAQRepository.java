package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.FAQ;

import java.util.List;

public interface FAQRepository {
    public List<FAQ> findAll();
    public List<FAQ> findByTopic(String topic);
    public void save(FAQ faq);
}
