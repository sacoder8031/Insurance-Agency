package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class PropertyRepositoryImpl implements PropertyRepository {
    JdbcTemplate jdbcTemplate;
    private RowMapper<Property> propertyRowMapper = new RowMapper<Property>() {
        @Override
        public Property mapRow(ResultSet resultSet, int i) throws SQLException {
            Property property = new Property();
            property.setId(resultSet.getInt("id"));
            property.setName(resultSet.getString("name"));
            property.setRecordId(resultSet.getInt("record_id"));
            property.setUserId(resultSet.getInt("user"));
            property.setDocumentLocation(resultSet.getString("document"));
            return property;
        }
    };

    @Autowired
    public PropertyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Property> findByUser(int userId) {
        String sqlQuery = "select * from property where user = '"+userId+"'";
        List<Property> properties = jdbcTemplate.query(sqlQuery,propertyRowMapper);
        return properties;
    }

    @Override
    public List<Property> findByUSerAndRecord(int userId, int recordId) {
        String sqlQuery;
        if(recordId==0){
            sqlQuery="select * from property where user = '"+userId+"' and record_id is null";
        }else{
            sqlQuery = "select * from property where user = '"+userId+"' and record_id = '"+recordId+"'";
        }
        List<Property> properties = jdbcTemplate.query(sqlQuery,propertyRowMapper);
        return properties;
    }

    @Override
    public Property findById(int id) {
        String sqlQuery = "select * from property where id='"+id+"'";
        return jdbcTemplate.queryForObject(sqlQuery,propertyRowMapper);
    }

    @Override
    public int findRecordOnProperty(int id) {
        String sqlQuery = "select record_id from property where id='"+id+"'";
        return jdbcTemplate.queryForObject(sqlQuery,Integer.class);
    }

    @Override
    public Property findByRecord(int recordId) {
        String sqlQuery = "select * from property where record_id='"+recordId+"'";
        return jdbcTemplate.queryForObject(sqlQuery,propertyRowMapper);
    }

    @Override
    public void save(Property property) {
        String sqlQuery = "insert into property(name,record_id,user,document) values(?,?,?,?)";
        jdbcTemplate.update(sqlQuery,property.getName(),property.getRecordId()==0?null:property.getRecordId(),property.getUserId(),property.getDocumentLocation());
    }

    @Override
    public void changeRecord(int recordId, int id) {
        String sqlQuery = "update property set record_id = '"+recordId+"' where id='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "delete from property where id ='"+id+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void removeExpirationRecord() {
        String sqlQuery = "update property set record_id=NULL where record_id in (select id from policy_record where status='expired')";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void removeRecord(int propertyId) {
        String sqlQuery = "update property set record_id=NULL where id='"+propertyId+"'";
        jdbcTemplate.update(sqlQuery);
    }
}
