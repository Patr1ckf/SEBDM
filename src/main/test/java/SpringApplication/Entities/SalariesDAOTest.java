package SpringApplication.Entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalariesDAOTest {

    private SalariesDAO dao;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres");
        dataSource.setUsername("postgres.wxeseejwtrlkyitubugy");
        dataSource.setPassword("dRWMSfNPgGDv3lxk");
        dataSource.setDriverClassName("org.postgresql.Driver");

        dao = new SalariesDAO(new JdbcTemplate(dataSource));
    }

    @Test
    void testSave() {
        Salaries salary = new Salaries(11, LocalDate.of(2023, 12, 23), 5400, 3500, 1);
        dao.save(salary);

        Salaries savedSalary = dao.get(11);
        assertNotNull(savedSalary, "Salary should be saved");
        assertEquals(5400, savedSalary.getGross_salary(), "Gross salary should match");
    }

    @Test
    void testList() {
        List<Salaries> salariesList = dao.list();
        assertNotNull(salariesList, "Salaries list should not be null");
        assertFalse(salariesList.isEmpty(), "Salaries list should not be empty");
    }

    @Test
    void testGet() {
        Salaries salary = new Salaries(12, LocalDate.of(2024, 1, 6), 6000, 4000, 2);
        dao.save(salary);

        Salaries retrievedSalary = dao.get(12);
        assertNotNull(retrievedSalary, "Salary should exist");
        assertEquals(6000, retrievedSalary.getGross_salary(), "Gross salary should match");
    }

    @Test
    void testUpdate() {
        Salaries salary = new Salaries(13, LocalDate.of(2024, 1, 6), 7000, 5000, 3);
        dao.save(salary);

        salary.setGross_salary(8000);
        salary.setNet_salary(5500);
        dao.update(salary);

        Salaries updatedSalary = dao.get(13);
        assertNotNull(updatedSalary, "Updated salary should exist");
        assertEquals(8000, updatedSalary.getGross_salary(), "Updated gross salary should match");
        assertEquals(5500, updatedSalary.getNet_salary(), "Updated net salary should match");
    }

    @Test
    void testDelete() {
        Salaries salary = new Salaries(14, LocalDate.of(2024, 2, 1), 6500, 4500, 4);
        dao.save(salary);

        assertNotNull(dao.get(14), "Salary should exist before deletion");
        dao.delete(14);
        assertNull(dao.get(14), "Salary should be deleted");
    }

    @AfterEach
    void cleanUp() {
        dao.delete(11);
        dao.delete(12);
        dao.delete(13);
        dao.delete(14);
    }
}
