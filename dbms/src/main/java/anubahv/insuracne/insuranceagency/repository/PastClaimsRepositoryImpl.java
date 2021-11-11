package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.PastClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PastClaimsRepositoryImpl implements PastClaimsRepository {
    JdbcTemplate jdbcTemplate;
    RowMapper<PastClaims> pastClaimsRowMapper = new RowMapper<PastClaims>() {
        @Override
        public PastClaims mapRow(ResultSet resultSet, int i) throws SQLException {
            PastClaims pastClaims = new PastClaims();
            pastClaims.setRecordId(resultSet.getInt("record_id"));
            pastClaims.setFirstName(resultSet.getString("first_name"));
            pastClaims.setLastName(resultSet.getString("last_name"));
            pastClaims.setEmail(resultSet.getString("email"));
            pastClaims.setAmount(resultSet.getInt("amount"));
            pastClaims.setCategory(resultSet.getString("category"));
            pastClaims.setDateOfClaim(resultSet.getDate("date_of_claim"));
            pastClaims.setAssetDetails(resultSet.getString("asset_details"));
            return pastClaims;
        }
    };

    @Autowired
    public PastClaimsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PastClaims> findByUsername(String email) {
        String sqlQuery = "select * from past_claims where email = '"+email+"'";
        List<PastClaims> pastClaims = jdbcTemplate.query(sqlQuery,pastClaimsRowMapper);
        return pastClaims;
    }

    @Override
    public List<PastClaims> findByName(String firstName, String lastName) {
        String sqlQuery = "select * from past_claims where first_name = '"+firstName+"' and last_name='"+lastName+"'";
        List<PastClaims> pastClaims = jdbcTemplate.query(sqlQuery,pastClaimsRowMapper);
        return pastClaims;
    }

    @Override
    public PastClaims findByRecord(int id) {
        String sqlQuery = "select * from past_claims where record_id='"+id+"'";
        return jdbcTemplate.queryForObject(sqlQuery,pastClaimsRowMapper);
    }

    @Override
    public void save(PastClaims pastClaims) {
        String sqlQuery = "insert into past_claims values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sqlQuery,pastClaims.getRecordId(),pastClaims.getFirstName(),pastClaims.getLastName(),pastClaims.getEmail(),
                pastClaims.getAmount(),pastClaims.getCategory(),pastClaims.getDateOfClaim(),pastClaims.getAssetDetails());
    }
}
