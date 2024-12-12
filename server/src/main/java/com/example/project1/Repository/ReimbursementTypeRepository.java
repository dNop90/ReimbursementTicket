package com.example.project1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.Entity.ReimbursementType;

public interface ReimbursementTypeRepository extends JpaRepository<ReimbursementType, Integer>{
    ReimbursementType findByTypename(String typename);
}
