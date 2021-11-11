package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.PolicyRecord;
import anubahv.insuracne.insuranceagency.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.springframework.data.util.Pair.of;

@Repository
public class UserRepositoryImpl implements UserRepository {
    JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));
            user.setPhoneNo(resultSet.getString("phone_no"));
            user.setRole(resultSet.getString("role"));
            user.setStatus(resultSet.getString("status"));
            user.setPassword(resultSet.getString("password"));
            user.setAddress(resultSet.getString("address"));
            return user;
        }
    };

    @Override
    public List<Pair<String, Integer>> getExpiryMailDetails() {
        Date date = Calendar.getInstance().getTime();
        String sqlQuery = "select email,p.id from user, policy_record p where user.id = p.user_id and p.expiry_date = '"+new java.sql.Date(date.getTime())+"'";
        List< Pair<String,Integer> > mailDetails = jdbcTemplate.query(sqlQuery, new RowMapper<Pair<String, Integer>>() {
            @Override
            public Pair<String, Integer> mapRow(ResultSet resultSet, int i) throws SQLException {
                Pair<String, Integer> pair = of(resultSet.getString("email"),resultSet.getInt("p.id"));
                return pair;
            }
        });
        return mailDetails;
    }

    @Override
    public void deadUser(int userId) {
        String sqlQuery = "update user set status='dead' where id='"+userId+"'";
        jdbcTemplate.update(sqlQuery);
    }

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByEmail(String email) {
        String query = "select * from user where email='"+email+"'";
        return jdbcTemplate.queryForObject(query, userRowMapper);
    }

    @Override
    public boolean userExists(String email) {
        String query = "select count(*) from user where email='"+email+"'";
        int cnt = jdbcTemplate.queryForObject(query,Integer.class);
        if(cnt>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void save(User user) {
        String query = "insert into user(first_name,last_name,email,phone_no,password,status,role,address) values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(query,user.getFirstName(),user.getLastName(),user.getEmail(),user.getPhoneNo(),user.getPassword(),user.getStatus(),user.getRole(),user.getAddress());
    }

    @Override
    public void enableUser(User user) {
        String query = "update user set status = 'verified' where email='"+user.getEmail()+"'";
        jdbcTemplate.update(query);
    }

    @Override
    public User findByUserId(int id) {
        String query = "select * from user where id='"+id+"'";
        return jdbcTemplate.queryForObject(query, userRowMapper);
    }

    @Override
    public void addRole(String role, String email) {
        User user = findByEmail(email);
        String sqlQuery = "update user set role = '"+role+" "+user.getRole()+"' where email='"+email+"'";
        jdbcTemplate.update(sqlQuery);
    }
}
