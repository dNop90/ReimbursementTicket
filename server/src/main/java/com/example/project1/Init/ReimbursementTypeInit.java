package com.example.project1.Init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.project1.Entity.ReimbursementType;
import com.example.project1.Repository.ReimbursementTypeRepository;

@Component
public class ReimbursementTypeInit implements ApplicationRunner{
    private final ReimbursementTypeRepository reimbursementTypeRepository;

    public ReimbursementTypeInit(ReimbursementTypeRepository reimbursementTypeRepository)
    {
        this.reimbursementTypeRepository = reimbursementTypeRepository;
    }

    public void run(final ApplicationArguments args)
    {
        if(reimbursementTypeRepository.count() != 0)
        {
            return;
        }

        reimbursementTypeRepository.save(new ReimbursementType("Other"));
    }
}
