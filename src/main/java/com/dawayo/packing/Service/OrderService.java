package com.dawayo.packing.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dawayo.packing.Repository.OrderRepository;
import com.dawayo.packing.VO.PackingVO;
import com.dawayo.packing.VO.ScanErrorVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(PackingVO packingVO) {
        orderRepository.save(packingVO);
    }

    public void saveAll(List<PackingVO> packingList) {
        orderRepository.saveAll(packingList);
    }

    public void saveAllError(List<ScanErrorVO> scannedErrorItems) {
        orderRepository.saveAllError(scannedErrorItems);
    }

    



    // Add your service methods here
}
