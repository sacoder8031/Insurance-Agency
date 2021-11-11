package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    JdbcTemplate jdbcTemplate;

    private RowMapper<Transaction> transactionRowMapper = new RowMapper<Transaction>() {
        @Override
        public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
            Transaction transaction = new Transaction();
            transaction.setId(resultSet.getInt("id"));
            transaction.setType(resultSet.getString("type"));
            transaction.setAmount(resultSet.getInt("amount"));
            transaction.setReceiptNumber(resultSet.getString("receipt_no"));
            transaction.setRecordId(resultSet.getInt("record_id"));
            transaction.setUserId(resultSet.getInt("user_id"));
            return transaction;
        }
    };

    @Autowired
    public TransactionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Transaction transaction) {
        String sqlQuery = "insert into transaction(type,amount,receipt_no,record_id,user_id) values(?,?,?,?,?)";
        jdbcTemplate.update(sqlQuery,transaction.getType(),transaction.getAmount(),transaction.getReceiptNumber(),transaction.getRecordId(),transaction.getUserId());
    }

    @Override
    public List<Transaction> finalAll() {
        String sqlQuery = "select * from transaction";
        List<Transaction> transactions = jdbcTemplate.query(sqlQuery,transactionRowMapper);
        return transactions;
    }

    @Override
    public List<Transaction> findByUser(int userId) {
        String sqlQuery = "select * from transaction where user_id='"+userId+"'";
        List<Transaction> transactions = jdbcTemplate.query(sqlQuery,transactionRowMapper);
        return transactions;
    }
}
