package com.dawayo.packing.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dawayo.packing.VO.PackingVO;
import com.dawayo.packing.VO.ScanErrorVO;
import com.dawayo.packing.VO.UserVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
@Repository
public class OrderRepository {
    
    @PersistenceContext
    private EntityManager entityManager;



    public void save(PackingVO packingVO) {
       entityManager.persist(packingVO);
    }



    public void saveAll(List<PackingVO> packingList) {
        for (PackingVO packingVO : packingList) {
            entityManager.persist(packingVO);
        }
    }



    public void saveAllError(List<ScanErrorVO> scannedErrorItems) {
        for (ScanErrorVO scanErrorVO : scannedErrorItems) {
            entityManager.persist(scanErrorVO);
        }
    }



    public void login(UserVO userVO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }



    public boolean existsByOrderNumber(String orderNumber) {
        Long count = entityManager.createQuery("SELECT COUNT(p) FROM PackingVO p WHERE p.orderNumber = :orderNumber", Long.class)
                                  .setParameter("orderNumber", orderNumber)
                                  .getSingleResult();
        return count > 0;
    }
    
}
