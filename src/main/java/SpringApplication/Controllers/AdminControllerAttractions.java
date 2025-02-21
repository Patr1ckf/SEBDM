package SpringApplication.Controllers;
import SpringApplication.Entities.Attractions;
import SpringApplication.Entities.AttractionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminControllerAttractions {

    @Autowired
    private AttractionsDAO dao;

    @RequestMapping("/admin/attractions_data")
    public String showAdminUserData(Model model) {
        List<Attractions> attractionsList = dao.list();
        model.addAttribute("attractionsList", attractionsList);
        return "admin/AttractionsAdminEntity/attractions_data";
    }

    @RequestMapping(value = "/newA")
    public String showNewForm(Model model) {
        Attractions attractions = new Attractions();
        model.addAttribute("attractions", attractions);
        return "admin/AttractionsAdminEntity/new_form_attractions";
    }

    @RequestMapping(value="/saveA", method = RequestMethod.POST)
    public String save(@ModelAttribute("attractions") Attractions attractions){
        dao.save(attractions);
        return "redirect:/admin/attractions_data";
    }

    @RequestMapping("/editA/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("admin/AttractionsAdminEntity/edit_form_attractions");
        Attractions attractions = dao.get(id);
        mav.addObject("attractions", attractions);
        return mav;
    }

    @RequestMapping(value = "/updateA", method = RequestMethod.POST)
    public String update(@ModelAttribute("attractions") Attractions attractions){
        dao.update(attractions);

        return "redirect:/admin/attractions_data";
    }

    @RequestMapping("/deleteA/{id}")
    public String delete(@PathVariable(name = "id") int id){
        dao.delete(id);

        return "redirect:/admin/attractions_data";
    }

}
