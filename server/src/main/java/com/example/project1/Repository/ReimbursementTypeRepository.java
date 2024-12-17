package com.example.project1.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.Entity.ReimbursementType;

public interface ReimbursementTypeRepository extends JpaRepository<ReimbursementType, Integer>{
    ReimbursementType findFirstByTypename(String typename);
    List<ReimbursementType> findAllByOrderByTypenameAsc();
}
