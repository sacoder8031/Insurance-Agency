package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.FAQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FAQRepositoryImpl implements FAQRepository {
    JdbcTemplate jdbcTemplate;

    private RowMapper<FAQ> faqRowMapper = new RowMapper<FAQ>() {
        @Override
        public FAQ mapRow(ResultSet resultSet, int i) throws SQLException {
            FAQ faq = new FAQ();
            faq.setId(resultSet.getInt("id"));
            faq.setQuestion(resultSet.getString("question"));
            faq.setAnswer(resultSet.getString("answer"));
            faq.setOnTopic(resultSet.getString("on_topic"));
            return faq;
        }
    };

    @Autowired
    public FAQRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FAQ> findAll() {
        String sqlQuery = "select * from faq";
        List<FAQ> faqs = jdbcTemplate.query(sqlQuery,faqRowMapper);
        return faqs;
    }

    @Override
    public List<FAQ> findByTopic(String topic) {
        String sqlQuery = "select * from faq where on_topic='"+topic+"'";
        List<FAQ> faqs = jdbcTemplate.query(sqlQuery,faqRowMapper);
        return faqs;
    }

    @Override
    public void save(FAQ faq) {
        String sqlQuery = "insert into faq(question,answer,on_topic) values(?,?,?)";
        jdbcTemplate.update(sqlQuery,faq.getQuestion(),faq.getAnswer(),faq.getOnTopic());
    }
}
