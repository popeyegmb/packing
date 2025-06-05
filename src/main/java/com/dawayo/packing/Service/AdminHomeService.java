package com.dawayo.packing.Service;

import org.springframework.stereotype.Service;

import com.dawayo.packing.Repository.AdminHomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminHomeService {

    private final AdminHomeRepository adminHomeRepository;

    
}
