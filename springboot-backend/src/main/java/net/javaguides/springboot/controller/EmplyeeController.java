package net.javaguides.springboot.controller;

import net.javaguides.springboot.excpetion.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmplyeeController {
    @Autowired
    private EmployeeRespository employeeRespository;
    //GET ALL EMPLOYEE
    @GetMapping("/empoyees")
    public List<Employee> getAllEmployees(){
        return employeeRespository.findAll();
    };

    //create an employee
    @PostMapping("/empoyees")
    public Employee createEmployees(@RequestBody Employee employee){
        return  employeeRespository.save(employee);
    }

    @GetMapping("/empoyees/{id}")
    public ResponseEntity <Employee> getEmployee(@PathVariable Long id){
        Employee employee =  employeeRespository.findById(id)
        .orElseThrow(()->new ResourceNotFoundException("user  not exist with id "+id));
         return  ResponseEntity.ok(employee);
    }

    //UPDATE EMPLOYEE
    @PutMapping("/empoyees/{id}")
    public  ResponseEntity<Employee>updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
        Employee employee =  employeeRespository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("user  not exist with id "+id));
            employee.setFistName(employeeDetails.getFistName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setEmailId(employeeDetails.getEmailId());
          Employee updatedEmployee=employeeRespository.save(employee);
        return  ResponseEntity.ok(updatedEmployee);
    }
    @DeleteMapping("/empoyees/{id}")
    public ResponseEntity <Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee =  employeeRespository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("user  not exist with id "+id));

        employeeRespository.delete(employee);
        Map<String,Boolean> response=new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);


    }
}
