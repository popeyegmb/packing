package com.dawayo.packing.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dawayo.packing.Repository.OrderRepository;
import com.dawayo.packing.Repository.UserRepository;
import com.dawayo.packing.VO.PackingVO;
import com.dawayo.packing.VO.ScanErrorVO;
import com.dawayo.packing.VO.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void login(UserVO userVO) {
     
       userRepository.login(userVO);

    }

  



    // Add your service methods here
}
