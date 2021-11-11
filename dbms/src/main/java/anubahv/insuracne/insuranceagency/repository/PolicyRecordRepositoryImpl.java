package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.PolicyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class PolicyRecordRepositoryImpl implements PolicyRecordRepository {
    JdbcTemplate jdbcTemplate;

    private RowMapper<PolicyRecord> policyRecordRowMapper = new RowMapper<PolicyRecord>() {
        @Override
        public PolicyRecord mapRow(ResultSet resultSet, int i) throws SQLException {
            PolicyRecord policyRecord = new PolicyRecord();
            policyRecord.setId(resultSet.getInt("id"));
            policyRecord.setExpiryDate(resultSet.getDate("expiry_date"));
            policyRecord.setPolicyId(resultSet.getInt("policy"));
            policyRecord.setStatus(resultSet.getString("status"));
            policyRecord.setUserId(resultSet.getInt("user_id"));
            return policyRecord;
        }
    };

    @Override
    public List<PolicyRecord> findActiveOfUserOfCategory(int userId, String category) {
        String sqlQuery = "select pr.* from policy_record pr,policy p where p.id=pr.policy and pr.user_id = '"+userId+"' and p.category = '"+category+"' and pr.status='active'";
        List<PolicyRecord> policyRecords = jdbcTemplate.query(sqlQuery,policyRecordRowMapper);
        return policyRecords;
    }

    @Autowired
    public PolicyRecordRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(PolicyRecord policyRecord) {
        String sqlQuery = "insert into policy_record(policy,user_id,expiry_date,status) values(?,?,?,?)";
        jdbcTemplate.update(sqlQuery,policyRecord.getPolicyId(),policyRecord.getUserId(),policyRecord.getExpiryDate(),policyRecord.getStatus());
    }

    @Override
    public PolicyRecord getPolicyRecord(int id) {
        String sqlQuery = "select * from policy_record where id='"+id+"'";
        return jdbcTemplate.queryForObject(sqlQuery,policyRecordRowMapper);
    }

    @Override
    public PolicyRecord getPolicyRecord(int userId, int policyId) {
        String sqlQuery = "select * from policy_record where user_id = '"+userId+"' and policy = '"+policyId+"' and id=(select max(id) from policy_record where user_id = '"+userId+"' and policy='"+policyId+"') ";
        return jdbcTemplate.queryForObject(sqlQuery,policyRecordRowMapper);
    }

    @Override
    public void markExpiration(Date date) {
        String sqlQuery = "update policy_record set status='expired' where expiry_date='"+new java.sql.Date(date.getTime())+"' and status='active'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "delete from policy_record where id='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void changeExpiryDate(Date date,int id) {
        String sqlQuery = "update policy_record set expiry_date='"+date+"' where id='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public List<PolicyRecord> findAllOfUser(int userId) {
        String sqlQuery = "select * from policy_record where user_id = '"+userId+"' and status='active'";
        List<PolicyRecord> policyRecords = jdbcTemplate.query(sqlQuery,policyRecordRowMapper);
        return policyRecords;
    }

    @Override
    public List<PolicyRecord> findAllOfUserOfCategory(int userId, String category) {
        String sqlQuery = "select pr.* from policy_record pr,policy p where p.id=pr.policy and pr.user_id = '"+userId+"' and p.category = '"+category+"'";
        List<PolicyRecord> policyRecords = jdbcTemplate.query(sqlQuery,policyRecordRowMapper);
        return policyRecords;
    }

    @Override
    public void changeStatus(String status,int id) {

        String sqlQuery = "update policy_record set status='"+status+"' where id='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }
}
