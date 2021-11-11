package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PolicyRepositoryImpl implements PolicyRepository {
    JdbcTemplate jdbcTemplate;
    private RowMapper<Policy> policyRowMapper = new RowMapper<Policy>() {
        @Override
        public Policy mapRow(ResultSet resultSet, int i) throws SQLException {
            Policy policy = new Policy();
            policy.setId(resultSet.getInt("id"));
            policy.setCategory(resultSet.getString("category"));
            policy.setExpirationStatus(resultSet.getString("expiration_status"));
            policy.setMaxClaimAmount(resultSet.getInt("max_claim_amount"));
            policy.setName(resultSet.getString("name"));
            policy.setPremium(resultSet.getInt("premium"));
            policy.setThingsCovered(resultSet.getString("things_covered"));
            return policy;
        }
    };
    @Autowired
    public PolicyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Policy> findAll() {
        String sqlQuery = "select * from policy";
        List<Policy> policies = jdbcTemplate.query(sqlQuery, policyRowMapper);
        return policies;
    }

    @Override
    public List<Policy> findByExpiration(String status) {
        String sqlQuery = "select * from policy where expiration_status='"+status+"'";
        List<Policy> policies = jdbcTemplate.query(sqlQuery, policyRowMapper);
        return policies;
    }

    @Override
    public List<Policy> findAllByCategory(String category) {
        String sqlQuery = "select * from policy where category='"+category+"'";
        List<Policy> policies = jdbcTemplate.query(sqlQuery,policyRowMapper);
        return policies;
    }

    @Override
    public List<Policy> findActiveByCategory(String category) {
        String sqlQuery = "select * from policy where category ='"+category+"' and expiration_status='active'";
        List<Policy> policies = jdbcTemplate.query(sqlQuery,policyRowMapper);
        return policies;
    }

    @Override
    public Policy findById(int policyId) {
        String sqlQuery = "select * from policy where id='"+policyId+"'";
        return jdbcTemplate.queryForObject(sqlQuery,policyRowMapper);
    }

    @Override
    public void changeExpirationStatus(String expirationStatus,int policyId) {
        String sqlQuery = "update policy set expiration_status='"+expirationStatus+"' where id='"+policyId+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void addPolicy(Policy policy) {
        String sqlQuery = "insert into policy(name,category,premium,max_claim_amount,things_covered,expiration_status) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sqlQuery,policy.getName(),policy.getCategory(),policy.getPremium(),policy.getMaxClaimAmount(),policy.getThingsCovered(),policy.getExpirationStatus());
    }


}
