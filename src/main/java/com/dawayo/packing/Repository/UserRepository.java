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

    public UserVO login(UserVO userVO) {
        String userId = userVO.getUserid();
        String password = userVO.getPassword();

        
        UserVO foundUser = entityManager.createQuery("SELECT u FROM UserVO u WHERE u.userid = :userId AND u.password = :password", UserVO.class)
                                         .setParameter("userId", userId)
                                         .setParameter("password", password)
                                         .getSingleResult();    
        if (foundUser != null) {
            return foundUser;
        }
        else{
            System.err.println("User not found with ID: " + userId);
            return null; // Return null if no user found
        }
        

    }



    
}
