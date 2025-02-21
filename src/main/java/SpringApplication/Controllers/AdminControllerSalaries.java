package SpringApplication.Controllers;

import SpringApplication.Entities.Salaries;
import SpringApplication.Entities.SalariesDAO;
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
public class AdminControllerSalaries {

    @Autowired
    private SalariesDAO dao;

    @RequestMapping("/admin/salaries")
    public String showAdminUserData(Model model) {
        List<Salaries> salariesList = dao.list();
        model.addAttribute("salariesList", salariesList);
        return "/admin/salariesEntity/salaries";
    }

    @RequestMapping(value = "/newW")
    public String showNewForm(Model model) {
        Salaries salaries = new Salaries();
        model.addAttribute("salaries", salaries);
        return "admin/salariesEntity/new_form_salaries";
    }

    @RequestMapping(value="/saveW", method = RequestMethod.POST)
    public String save(@ModelAttribute("salaries") Salaries salaries){
        dao.save(salaries);
        return "redirect:/admin/salaries";
    }

    @RequestMapping("/editW/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("admin/salariesEntity/edit_form_salaries");
        Salaries salaries = dao.get(id);
        mav.addObject("salaries", salaries);
        return mav;
    }

    @RequestMapping(value = "/updateW", method = RequestMethod.POST)
    public String update(@ModelAttribute("salaries") Salaries salaries){
        dao.update(salaries);

        return "redirect:/admin/salaries";
    }

    @RequestMapping("/deleteW/{id}")
    public String delete(@PathVariable(name = "id") int id){
        dao.delete(id);

        return "redirect:/admin/salaries";
    }

}
