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
public class UserRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public void login(UserVO userVO) {
        // Assuming UserVO has a method to get username and password
        String userId = userVO.getUserid();
        String password = userVO.getPassword();

        // Here you would typically query the database to check if the user exists
        // For example:
        UserVO foundUser = entityManager.createQuery("SELECT u FROM UserVO u WHERE u.userid = :userId AND u.password = :password", UserVO.class)
                                         .setParameter("userId", userId)
                                         .setParameter("password", password)
                                         .getSingleResult();    
        if (foundUser != null) {

    }



    
}
