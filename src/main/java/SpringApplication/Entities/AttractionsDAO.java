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
public class AttractionsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AttractionsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Attractions> list() {
        String sql = "SELECT * FROM ATTRACTIONS";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Attractions.class));
    }

    public void save(Attractions attractions) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ATTRACTIONS")
                .usingGeneratedKeyColumns("ID_ATTRACTION")
                .usingColumns("NAME", "DURATION", "MIN_AGE", "DESCRIPTION", "ROUTE");

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(attractions);
        Number newId = insert.executeAndReturnKey(param);
        attractions.setId_attraction(newId.intValue());
    }

    public Attractions get(int id) {
        String sql = "SELECT * FROM ATTRACTIONS WHERE ID_ATTRACTION = ?";
        List<Attractions> attractionsList = jdbcTemplate.query(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(Attractions.class));
        return attractionsList.isEmpty() ? null : attractionsList.get(0);
    }

    public void update(Attractions attractions) {
        String sql = "UPDATE ATTRACTIONS SET NAME=?, DURATION=?, MIN_AGE=?, DESCRIPTION=?, ROUTE=? WHERE ID_ATTRACTION=?";
        int rowsAffected = jdbcTemplate.update(sql, attractions.getName(), attractions.getDuration(), attractions.getMin_age(),
                attractions.getDescription(), attractions.getRoute(), attractions.getId_attraction());

        if (rowsAffected > 0) {
            System.out.println("Updated attraction with ID: " + attractions.getId_attraction());
        } else {
            System.err.println("No attraction found with ID: " + attractions.getId_attraction());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM ATTRACTIONS WHERE ID_ATTRACTION = ?";
        jdbcTemplate.update(sql, id);
    }
}
