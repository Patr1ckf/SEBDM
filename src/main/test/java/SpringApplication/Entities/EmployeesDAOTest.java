package SpringApplication.Entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeesDAOTest {

    private EmployeesDAO dao;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres");
        dataSource.setUsername("postgres.wxeseejwtrlkyitubugy");
        dataSource.setPassword("dRWMSfNPgGDv3lxk");
        dataSource.setDriverClassName("org.postgresql.Driver");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        dao = new EmployeesDAO(jdbcTemplate, namedParameterJdbcTemplate);
    }


    @Test
    void testSave() {
        Employees employee = new Employees(15, "Milena", "Miller", "K",
                LocalDate.of(1990, 1, 15), "03323304522", "milena.koz2@wp.pl", "03344543234323456545454567");

        dao.save(employee);
        Employees savedEmployee = dao.get(15);

        assertNotNull(savedEmployee, "Employee should be saved");
        assertEquals("Milena", savedEmployee.getName(), "Name should match");
    }

    @Test
    void testList() {
        List<Employees> employeesList = dao.list();
        assertNotNull(employeesList, "List of employees should not be null");
        assertFalse(employeesList.isEmpty(), "List of employees should not be empty");
    }

    @Test
    void testGet() {
        Employees employee = new Employees(16, "John", "Doe", "M",
                LocalDate.of(1995, 6, 12), "03456789012", "john.doe@example.com", "12345678901234567890123456");

        dao.save(employee);
        Employees retrievedEmployee = dao.get(16);

        assertNotNull(retrievedEmployee, "Employee should exist");
        assertEquals("John", retrievedEmployee.getName(), "Name should match");
    }

    @Test
    void testUpdate() {
        Employees employee = new Employees(17, "Jane", "Smith", "F",
                LocalDate.of(1992, 3, 18), "03987654321", "jane.smith@example.com", "65432109876543210987654321");

        dao.save(employee);

        employee.setName("Janet");
        employee.setSurname("Doe");
        dao.update(employee);

        Employees updatedEmployee = dao.get(17);
        assertNotNull(updatedEmployee, "Updated employee should exist");
        assertEquals("Janet", updatedEmployee.getName(), "Updated name should match");
        assertEquals("Doe", updatedEmployee.getSurname(), "Updated surname should match");
    }

    @Test
    void testDelete() {
        Employees employee = new Employees(18, "Delete", "Me", "M",
                LocalDate.of(2000, 1, 1), "01122334455", "delete.me@example.com", "99887766554433221100998877");

        dao.save(employee);
        dao.delete(18);

        // Sprawdzamy, czy usunięcie faktycznie nastąpiło
        assertThrows(Exception.class, () -> dao.get(18), "Employee should be deleted");
    }

    @AfterEach
    void cleanUp() {
        for (int id : List.of(15, 16, 17, 18)) {
            if (dao.get(id) != null) {
                dao.delete(id);
            }
        }
    }
}
