package com.springboot.employee.controller;


import com.springboot.employee.entity.Employee;
import com.springboot.employee.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {


    private EmployeeService employeeService;

    // by default, with one bean no need to use @Autowired
    public EmployeeController(EmployeeService theEmployeeService){
        employeeService = theEmployeeService;
    }


    @GetMapping("/list")
    public String listEmployees(Model theModel){

        // get employees from db
        List<Employee> theEmployees =  employeeService.findAll();

        // add to the spring model
        theModel.addAttribute("employees", theEmployees);

        // return list page
        return "employees/list-employees";
    }


    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){

        // create model attribute to bind form data
        Employee theEmployee = new Employee();

        // add Employee to model
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel){

        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);

        // set the employee in the model to prepopulate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }

    // Delete employee
    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId){

        // delete the employee from the service
        employeeService.deleteById(theId);

        // send over to our form
        return "redirect:/employees/list";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){

        // save the employee
        employeeService.save(theEmployee);

        // use the redirect to avoid duplicate submissions
        return "redirect:/employees/list";
    }

}
