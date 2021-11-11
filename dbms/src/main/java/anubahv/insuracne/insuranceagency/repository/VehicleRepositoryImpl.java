package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.PolicyRecord;
import anubahv.insuracne.insuranceagency.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {
    JdbcTemplate jdbcTemplate;

    private RowMapper<Vehicle> vehicleRowMapper = new RowMapper<Vehicle>() {
        @Override
        public Vehicle mapRow(ResultSet resultSet, int i) throws SQLException {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(resultSet.getInt("id"));
            vehicle.setDocumentLocation(resultSet.getString("document"));
            vehicle.setVehicleNumber(resultSet.getString("vehicle_number"));
            vehicle.setRecordId(resultSet.getInt("record_id"));
            vehicle.setUserId(resultSet.getInt("user"));
            return vehicle;
        }
    };

    @Autowired
    public VehicleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Vehicle> findByUser(int userId) {
        String sqlQuery = "select * from vehicle where user = '"+userId+"'";
        List<Vehicle> vehicles = jdbcTemplate.query(sqlQuery,vehicleRowMapper);
        return vehicles;
    }

    @Override
    public List<Vehicle> findByUserAndRecord(int userId, int record_id) {
        String sqlQuery;
        if(record_id!=0)
            sqlQuery = "select * from vehicle where user='"+userId+"' and record_id = '"+record_id+"'";
        else
            sqlQuery = "select * from vehicle where user='"+userId+"' and record_id is null";
        List<Vehicle> vehicles = jdbcTemplate.query(sqlQuery,vehicleRowMapper);
        System.out.println(vehicles.size());
        System.out.println(userId);
        return vehicles;
    }

    @Override
    public Vehicle findById(int id) {
        String sqlQuery = "select * from vehicle where id='"+id+"'";
        return jdbcTemplate.queryForObject(sqlQuery,vehicleRowMapper);
    }

    @Override
    public int findRecordOnVehicle(int id) {
        String sqlQuery = "select record_id from vehicle where id='"+id+"'";
        return jdbcTemplate.queryForObject(sqlQuery,Integer.class);
    }

    @Override
    public Vehicle findByRecord(int recordId) {
        String sqlQuery = "select * from vehicle where record_id ='"+recordId+"'";
        return jdbcTemplate.queryForObject(sqlQuery,vehicleRowMapper);
    }

    @Override
    public void save(Vehicle vehicle) {
        String sqlQuery = "insert into vehicle(vehicle_number,document,record_id,user) values(?,?,?,?)";
        jdbcTemplate.update(sqlQuery,vehicle.getVehicleNumber(),vehicle.getDocumentLocation(),vehicle.getRecordId()==0?null:vehicle.getRecordId(),vehicle.getUserId());
    }

    @Override
    public void removeExpirationRecord() {
        String sqlQuery = "update vehicle set record_id=NULL where record_id in (select id from policy_record where status='expired')";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void changeRecord(int recordId, int id) {
        String sqlQuery = "update vehicle set record_id='"+recordId+"' where id='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "delete from vehicle where id='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void removeRecord(int id) {
        String sqlQuery = "update vehicle set record_id=NULL where id='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }
}
