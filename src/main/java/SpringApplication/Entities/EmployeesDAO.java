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
public class EmployeesDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public EmployeesDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Employees> list() {
        String sql = "SELECT * FROM EMPLOYEES";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Employees.class));
    }

    public void save(Employees employees) {
        SimpleJdbcInsert insertEmployee = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("employees")
                .usingGeneratedKeyColumns("id_employee")
                .usingColumns("name", "surname", "gender", "birth_date", "personal_id", "email", "bank_account");

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(employees);
        Number newId = insertEmployee.executeAndReturnKey(param);
        employees.setId_employee(newId.intValue());
    }



    public Employees get(int id) {
        String sql = "SELECT * FROM EMPLOYEES WHERE ID_EMPLOYEE = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(Employees.class));
        } catch (Exception e) {
            return null; // Handle exception or log it (e.g., employee not found)
        }
    }

    public void update(Employees employees) {
        String sql = "UPDATE EMPLOYEES SET NAME=:name, SURNAME=:surname, GENDER=:gender, " +
                "BIRTH_DATE=:birth_date, PERSONAL_ID=:personal_id, EMAIL=:email, BANK_ACCOUNT=:bank_account " +
                "WHERE ID_EMPLOYEE=:id_employee";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(employees);
        namedParameterJdbcTemplate.update(sql, param);
    }

    public void delete(int id) {
        String sqlSalaries = "DELETE FROM SALARIES WHERE ID_EMPLOYEE = ?";
        jdbcTemplate.update(sqlSalaries, id);

        String sqlEmployee = "DELETE FROM EMPLOYEES WHERE ID_EMPLOYEE = ?";
        jdbcTemplate.update(sqlEmployee, id);
    }
}
