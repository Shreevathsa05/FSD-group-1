package com.fsd.StudentManagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fsd.StudentManagement.model.Department;
import com.fsd.StudentManagement.repository.DepartmentRepository;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Test Endpoint
    @GetMapping("/test")
    public String home() {
        return "Department API is running.";
    }

    // List all departments
    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartments() {
        try {
            List<Department> departments = departmentRepository.findAll();
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching departments: " + e.getMessage());
        }
    }

    // Get department by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable int id) {
        try {
            Optional<Department> dept = departmentRepository.findById(id);
            return dept.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving department: " + e.getMessage());
        }
    }

    // Create a new department
    @PostMapping("/")
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        try {
            if (department.getDepartmentId() != 0 && departmentRepository.existsById(department.getDepartmentId())) {
                return ResponseEntity.badRequest().body("Department with this ID already exists.");
            }
            Department saved = departmentRepository.save(department);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating department: " + e.getMessage());
        }
    }

    // Update an existing department
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable int id, @RequestBody Department updatedDept) {
        try {
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
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating department: " + e.getMessage());
        }
    }

    // Delete a department
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
        try {
            if (!departmentRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            departmentRepository.deleteById(id);
            return ResponseEntity.ok().body("Department deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting department: " + e.getMessage());
        }
    }
}
