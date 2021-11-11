package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.Transaction;
import anubahv.insuracne.insuranceagency.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void add(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.finalAll();
    }

    @Override
    public List<Transaction> getAllOfUser(int userId) {
        return transactionRepository.findByUser(userId);
    }
}
