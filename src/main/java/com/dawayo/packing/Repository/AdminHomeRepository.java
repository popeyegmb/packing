package com.dawayo.packing.Repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class AdminHomeRepository {
    @PersistenceContext
    private EntityManager entityManager;
}
