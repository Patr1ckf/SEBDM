package SpringApplication.Controllers;
import SpringApplication.Entities.Employees;
import SpringApplication.Entities.EmployeesDAO;
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
public class AdminControllerEmployees {

    @Autowired
    private EmployeesDAO dao;

    @RequestMapping("/admin/employees_data")
    public String showAdminUserData(Model model) {
        List<Employees> employeesList = dao.list();
        model.addAttribute("employeesList", employeesList);
        return "/admin/employeesEntity/employees_data";
    }

    @RequestMapping(value = "/new")
    public String showNewForm(Model model) {
        Employees employee = new Employees();
        model.addAttribute("employee", employee);
        return "/admin/employeesEntity/new_form_employees";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("employees") Employees employees){
        dao.save(employees);
        return "redirect:/admin/employees_data";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("/admin/employeesEntity/edit_form_employees");
        Employees employees = dao.get(id);
        mav.addObject("employees", employees);
        return mav;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("employees") Employees employees){
        dao.update(employees);

        return "redirect:/admin/employees_data";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") int id){
        dao.delete(id);

        return "redirect:/admin/employees_data";
    }

}


