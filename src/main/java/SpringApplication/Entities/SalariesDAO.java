package SpringApplication.Entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SalariesDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SalariesDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Salaries> list(){
        String sql = "SELECT * FROM SALARIES";
        List<Salaries> salariesList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Salaries.class));

        return salariesList;
    }
    // Insert
    public void save(Salaries salaries) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("salaries").
                usingColumns("DATE", "GROSS_SALARY", "NET_SALARY", "ID_EMPLOYEE");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(salaries);
        insertActor.execute(param);
    }

    // Read
    public Salaries get(int id) {
        String sql = "SELECT * FROM SALARIES WHERE ID_SALARY = ?";
        Object[] args = {id};

        List<Salaries> salaries = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Salaries.class));

        // Return the first result if available, otherwise return null
        return salaries.isEmpty() ? null : salaries.get(0);
    }
    public Salaries get1(int id) {
        Object[] args = {id};
        String sql = "SELECT * FROM SALARIES WHERE ID_SALARY = " +args[0];
        Salaries salaries = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Salaries.class));
        return salaries;
    }

    // Update
    public void update(Salaries salaries) {
        String sql = "UPDATE SALARIES SET DATE=:date, GROSS_SALARY=:gross_salary, NET_SALARY=:net_salary, ID_EMPLOYEE=:id_employee WHERE ID_SALARY=:id_salary";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(salaries);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM SALARIES WHERE ID_SALARY = ?";
        jdbcTemplate.update(sql,id);
    }
}
