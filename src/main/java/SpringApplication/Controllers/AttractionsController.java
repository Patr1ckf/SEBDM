package SpringApplication.Controllers;

import SpringApplication.Entities.Attractions;
import SpringApplication.Entities.AttractionsDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attractions")
@CrossOrigin(origins = "*")
public class AttractionsController {

    private final AttractionsDAO attractionsDAO;

    public AttractionsController(AttractionsDAO attractionsDAO) {
        this.attractionsDAO = attractionsDAO;
    }

    @GetMapping
    public List<Attractions> getAllAttractions() {
        return attractionsDAO.list();
    }

    @GetMapping("/{id}")
    public Attractions getAttractionById(@PathVariable int id) {
        return attractionsDAO.get(id);
    }
}
