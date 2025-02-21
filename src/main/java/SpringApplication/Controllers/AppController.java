package SpringApplication.Controllers;
import SpringApplication.Entities.AttractionsDAO;
import SpringApplication.Entities.EmployeesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppController implements WebMvcConfigurer {

    @Autowired
    private EmployeesDAO dao;
    private AttractionsDAO dao1;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main_admin").setViewName("admin/main_admin");
        registry.addViewController("/new").setViewName("admin/employeesEntity/new_form_employees");
        registry.addViewController("/save").setViewName("admin/employeesEntity/employees_data");
        registry.addViewController("/edit{id}").setViewName("admin/employeesEntity/edit_form_employees");
        registry.addViewController("/delete").setViewName("admin/employeesEntity/employees_data");
        registry.addViewController("/newW").setViewName("admin/salariesEntity/new_form_salaries");
        registry.addViewController("/saveW").setViewName("admin/salariesEntity/new_form_salaries");
        registry.addViewController("/newA").setViewName("admin/AttractionsAdminEntity/new_form_attractions");
        registry.addViewController("/saveA").setViewName("admin/AttractionsAdminEntity/attractions_data");

    }

    @RequestMapping(value = {"/main_admin"})
    public String showAdminPage(Model model) {
        return "admin/main_admin";
    }

}




