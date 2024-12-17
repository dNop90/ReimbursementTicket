package com.example.project1.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Entity.ReimbursementType;
import com.example.project1.Service.ReimbursementTypeService;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin
@RestController
@RequestMapping("/api/reimbursement")
public class ReimbursementTypeController {
    private final ReimbursementTypeService reimbursementTypeService;

    public ReimbursementTypeController(ReimbursementTypeService reimbursementTypeService)
    {
        this.reimbursementTypeService = reimbursementTypeService;
    }

    /**
     * Get a list of all imbursement types
     * @return A list of imbursement types
     */
    @GetMapping("/types")
    public ResponseEntity<Object> getAllTypes() {
        List<ReimbursementType> AllTypes = reimbursementTypeService.getAll();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("types", AllTypes);
        return ResponseEntity.ok(data);
    }
    
}
