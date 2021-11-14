package sharabh.insuracne.insuranceagency.services;

import sharabh.insuracne.insuranceagency.models.FAQ;
import sharabh.insuracne.insuranceagency.repository.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQServiceImpl implements FAQService {
    FAQRepository faqRepository;

    @Autowired
    public FAQServiceImpl(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Override
    public List<FAQ> getAll() {
        return faqRepository.findAll();
    }

    @Override
    public List<FAQ> getHeathFaq() {
        return faqRepository.findByTopic("health");
    }

    @Override
    public List<FAQ> getPropertyFaq() {
        return faqRepository.findByTopic("property");
    }

    @Override
    public List<FAQ> getVehicleFaq() {
        return faqRepository.findByTopic("vehicle");
    }

    @Override
    public List<FAQ> getLifeFaq() {
        return faqRepository.findByTopic("life");
    }

    @Override
    public void addFaq(FAQ faq) {
        faqRepository.save(faq);
    }
}
