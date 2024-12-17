package com.example.project1.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.Entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
    List<Ticket> findBySubmitterIDOrderByCreatedAtDesc(Integer submitterID);
    List<Ticket> findAllByOrderByCreatedAtDesc();
}
