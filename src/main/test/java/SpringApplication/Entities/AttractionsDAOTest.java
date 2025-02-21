package SpringApplication.Entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttractionsDAOTest {

    private AttractionsDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres");
        dataSource.setUsername("postgres.wxeseejwtrlkyitubugy");
        dataSource.setPassword("dRWMSfNPgGDv3lxk");
        dataSource.setDriverClassName("org.postgresql.Driver");

        dao = new AttractionsDAO(new JdbcTemplate(dataSource));
    }

    @Test
    void testSave() {
        Attractions attraction = new Attractions(15, "ANOTHER", 5, 5, "Baseniki 5 osobowe", "Dragons Route");
        dao.save(attraction);

        Attractions savedAttraction = dao.get(attraction.getId_attraction());
        assertNotNull(savedAttraction, "Attraction should be saved");
        assertEquals("ANOTHER", savedAttraction.getName(), "Name should match");
    }

    @Test
    void testList() {
        List<Attractions> attractionsList = dao.list();

        assertNotNull(attractionsList, "Attractions list should not be null");
        assertFalse(attractionsList.isEmpty(), "Attractions list should not be empty");
    }

    @Test
    void testGet() {
        Attractions attraction = new Attractions(16, "Roller Coaster", 12, 8, "Fast and thrilling", "Dragons Route");
        dao.save(attraction);

        Attractions retrievedAttraction = dao.get(attraction.getId_attraction());

        assertNotNull(retrievedAttraction, "Attraction should exist");
        assertEquals("Roller Coaster", retrievedAttraction.getName(), "Name should match");
    }

    @Test
    void testUpdate() {
        Attractions attraction = new Attractions(17, "Ferris Wheel", 10, 5, "Great view of the park", "Smurfs Route");
        dao.save(attraction);

        attraction.setName("Giant Ferris Wheel");
        attraction.setDescription("Amazing view of the city");
        dao.update(attraction);

        Attractions updatedAttraction = dao.get(attraction.getId_attraction());

        assertNotNull(updatedAttraction, "Updated attraction should exist");
        assertEquals("Giant Ferris Wheel", updatedAttraction.getName(), "Updated name should match");
        assertEquals("Amazing view of the city", updatedAttraction.getDescription(), "Updated description should match");
    }

    @Test
    void testDelete() {
        Attractions attraction = new Attractions(18, "Haunted House", 5, 8, "Scary and fun", "Dragons Route");
        dao.save(attraction);
        dao.delete(attraction.getId_attraction());

        Attractions deletedAttraction = dao.get(attraction.getId_attraction());
        assertNull(deletedAttraction, "Attraction should be deleted");
    }

    @AfterEach
    void cleanUp() {
        dao.delete(15);
        dao.delete(16);
        dao.delete(17);
        dao.delete(18);
    }
}
