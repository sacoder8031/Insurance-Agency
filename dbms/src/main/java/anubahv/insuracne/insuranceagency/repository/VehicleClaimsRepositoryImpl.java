package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.VehicleClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VehicleClaimsRepositoryImpl implements VehicleClaimsRepository {
    JdbcTemplate jdbcTemplate;
    private RowMapper<VehicleClaims> vehicleClaimsRowMapper = new RowMapper<VehicleClaims>() {
        @Override
        public VehicleClaims mapRow(ResultSet resultSet, int i) throws SQLException {
            VehicleClaims vehicleClaims = new VehicleClaims();
            vehicleClaims.setId(resultSet.getInt("id"));
            vehicleClaims.setDamage(resultSet.getInt("damage"));
            vehicleClaims.setAmount(resultSet.getInt("amount"));
            vehicleClaims.setStatus(resultSet.getString("status"));
            vehicleClaims.setDateOfLoss(resultSet.getDate("date_of_loss"));
            vehicleClaims.setVehicleId(resultSet.getInt("vehicle_id"));
            vehicleClaims.setRecordId(resultSet.getInt("record_id"));
            return vehicleClaims;
        }
    };

    @Autowired
    public VehicleClaimsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VehicleClaims findClaim(int id) {
        String sqlQuery = "select * from vehicle_claims where id='"+id+"'";
        VehicleClaims vehicleClaims = jdbcTemplate.queryForObject(sqlQuery,vehicleClaimsRowMapper);
        String sqlQueryForDocs = "select document from vehicle_claim_docs where vehicle_claim_id = '"+id+"'";
        vehicleClaims.setLinkToDocuments(jdbcTemplate.query(sqlQueryForDocs, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("document");
            }
        }));
        return vehicleClaims;
    }

    @Override
    public List<VehicleClaims> findAllOfUserByStatus(int userId, String status) {
        String sqlQuery = "select v.* from vehicle_claims v,policy_record p where v.record_id = p.id and p.user_id = '"+userId+"' and v.status='"+status+"'";
        List<VehicleClaims> vehicleClaims = jdbcTemplate.query(sqlQuery,vehicleClaimsRowMapper);
        for(int i=0;i<vehicleClaims.size();i++){
            String sqlQueryForDocs = "select document from vehicle_claim_docs where vehicle_claim_id='"+vehicleClaims.get(i).getId()+"'";
            vehicleClaims.get(i).setLinkToDocuments(jdbcTemplate.query(sqlQueryForDocs, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("document");
                }
            }));
        }
        return vehicleClaims;
    }

    @Override
    public List<VehicleClaims> getAllByStatus(String status) {
        String sqlQuery = "select * from vehicle_claims where status='"+status+"'";
        List<VehicleClaims> vehicleClaims = jdbcTemplate.query(sqlQuery,vehicleClaimsRowMapper);
        for(int i=0;i<vehicleClaims.size();i++){
            String sqlQueryForDocs = "select document from vehicle_claim_docs where vehicle_claim_id='"+vehicleClaims.get(i).getId()+"'";
            vehicleClaims.get(i).setLinkToDocuments(jdbcTemplate.query(sqlQueryForDocs, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("document");
                }
            }));
        }
        return vehicleClaims;
    }

    @Override
    public void save(VehicleClaims vehicleClaims) {
        String sqlQuery = "insert into vehicle_claims(damage,amount,status,date_of_loss,vehicle_id,record_id) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sqlQuery,vehicleClaims.getDamage(),vehicleClaims.getAmount(),vehicleClaims.getStatus(),vehicleClaims.getDateOfLoss(),vehicleClaims.getVehicleId(),vehicleClaims.getRecordId());
        String sqlQueryForId = "select id from vehicle_claims where vehicle_id = '"+vehicleClaims.getVehicleId()+"'";
        int id = jdbcTemplate.queryForObject(sqlQueryForId,Integer.class);
        String sqlQueryForDocs = "insert into vehicle_claim_docs(document,vehicle_claim_id) values(?,?)";
        for(int i=0;i<vehicleClaims.getLinkToDocuments().size();i++){
            jdbcTemplate.update(sqlQueryForDocs,vehicleClaims.getLinkToDocuments().get(i),id);
        }
    }

    @Override
    public void changeStatus(String status,int id) {
        String sqlQuery = "update vehicle_claims set status='"+status+"' where id ='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public List<String> getRelatedDocuments(int id) {
        String sqlQuery = "select document from vehicle_claim_docs where vehicle_claim_id='"+id+"'";
        return jdbcTemplate.query(sqlQuery, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("document");
            }
        });
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "delete from vehicle_claims where id='"+id+"'";
        String sqlQueryForDocs = "delete from vehicle_claim_docs where vehicle_claim_id ='"+id+"'";
        jdbcTemplate.update(sqlQueryForDocs);
        jdbcTemplate.update(sqlQuery);
    }
}
