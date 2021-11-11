package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.Transaction;

import java.util.List;

public interface TransactionRepository {
    public void save(Transaction transaction);
    public List<Transaction> finalAll();
    public List<Transaction> findByUser(int userId);
}
