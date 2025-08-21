package com.fsd.StudentManagement.Controller;

import com.fsd.StudentManagement.Model.Department;
import com.fsd.StudentManagement.AttendenceRepo.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Test Endpoint
    @GetMapping("/")
    public String home() {
        return "Department API is running.";
    }

    // List all departments
    @GetMapping("/all")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Get department by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable int id) {
        Optional<Department> dept = departmentRepository.findById(id);
        return dept.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new department
    @PostMapping("/")
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        if (department.getDepartmentId() != 0 && departmentRepository.existsById(department.getDepartmentId())) {
            return ResponseEntity.badRequest().body("Department with this ID already exists.");
        }
        Department saved = departmentRepository.save(department);
        return ResponseEntity.ok(saved);
    }

    // Update an existing department
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable int id, @RequestBody Department updatedDept) {
        Optional<Department> existing = departmentRepository.findById(id);
        if (existing.isPresent()) {
            Department dept = existing.get();
            dept.setDepartmentName(updatedDept.getDepartmentName());
            dept.setHodName(updatedDept.getHodName());
            dept.setTotalStaff(updatedDept.getTotalStaff());
            dept.setTotalStudents(updatedDept.getTotalStudents());
            departmentRepository.save(dept);
            return ResponseEntity.ok(dept);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a department
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
        if (!departmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        departmentRepository.deleteById(id);
        return ResponseEntity.ok().body("Department deleted successfully.");
    }
}
