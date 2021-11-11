package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.LifeInsuranceClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class LifeInsuranceClaimRepositoryImpl implements LifeInsuranceClaimsRepository {
    JdbcTemplate jdbcTemplate;
    private RowMapper<LifeInsuranceClaim> lifeInsuranceClaimRowMapper = new RowMapper<LifeInsuranceClaim>() {
        @Override
        public LifeInsuranceClaim mapRow(ResultSet resultSet, int i) throws SQLException {
            LifeInsuranceClaim lifeInsuranceClaim = new LifeInsuranceClaim();
            lifeInsuranceClaim.setId(resultSet.getInt("id"));
            lifeInsuranceClaim.setAmount(resultSet.getInt("amount"));
            lifeInsuranceClaim.setRecordId(resultSet.getInt("record_id"));
            lifeInsuranceClaim.setStatus(resultSet.getString("status"));
            lifeInsuranceClaim.setDeathCertificateLocation(resultSet.getString("death_certificate"));
            return lifeInsuranceClaim;
        }
    };

    @Autowired
    public LifeInsuranceClaimRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(LifeInsuranceClaim lifeInsuranceClaim) {
        String sqlQuery = "insert into life_insurance_claims(death_certificate,record_id,amount,status) values(?,?,?,?)";
        jdbcTemplate.update(sqlQuery,lifeInsuranceClaim.getDeathCertificateLocation(),lifeInsuranceClaim.getRecordId(),lifeInsuranceClaim.getAmount(),lifeInsuranceClaim.getStatus());
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "delete from life_insurance_claims where id='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void updateStatus(String status, int id) {
        String sqlQuery = "update life_insurance_claims set status='"+status+"' where id='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public LifeInsuranceClaim findById(int id) {
        String sqlQuery = "select * from life_insurance_claims where id='"+id+"'";
        return jdbcTemplate.queryForObject(sqlQuery,lifeInsuranceClaimRowMapper);
    }

    @Override
    public String findDocuments(int id) {
        String sqlQuery = "select death_certificate from life_insurance_claims where id = '"+id+"'";
        return jdbcTemplate.queryForObject(sqlQuery,String.class);
    }

    @Override
    public List<LifeInsuranceClaim> findAllByStatus(String status) {
        String sqlQuery = "select * from life_insurance_claims where status='"+status+"'";
        List<LifeInsuranceClaim> lifeInsuranceClaims = jdbcTemplate.query(sqlQuery,lifeInsuranceClaimRowMapper);
        return lifeInsuranceClaims;
    }
}
