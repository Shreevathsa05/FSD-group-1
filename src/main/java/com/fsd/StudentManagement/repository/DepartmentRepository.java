package com.fsd.StudentManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsd.StudentManagement.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}