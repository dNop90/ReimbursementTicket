package com.example.project1.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Entity.ReimbursementType;
import com.example.project1.Repository.ReimbursementTypeRepository;

@Service
public class ReimbursementTypeService {
    private final ReimbursementTypeRepository reimbursementTypeRepository;

    public ReimbursementTypeService(ReimbursementTypeRepository reimbursementTypeRepository)
    {
        this.reimbursementTypeRepository = reimbursementTypeRepository;
    }

    /**
     * Get reimbursement type data based on TypeID
     * @param id The ID for the reimbursement type to search for
     * @return The ReimbursementType
     */
    public ReimbursementType getType(Integer id)
    {
        return reimbursementTypeRepository.findById(id).orElse(null);
    }

    /**
     * Get all reimbursement type and order them by typename (ascending)
     * @return All reimbursement types
     */
    public List<ReimbursementType> getAll()
    {
        return reimbursementTypeRepository.findAllByOrderByTypenameAsc();
    }
}
