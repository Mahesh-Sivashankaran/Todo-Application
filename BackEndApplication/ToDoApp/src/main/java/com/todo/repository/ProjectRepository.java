package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.modal.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}