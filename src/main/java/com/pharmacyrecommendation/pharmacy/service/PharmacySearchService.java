package com.pharmacyrecommendation.pharmacy.service;

import com.pharmacyrecommendation.pharmacy.cache.PharmacyRedisTemplateService;
import com.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import com.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    public List<PharmacyDto> searchPharmacyDtoList(){ // redis 에서 먼저 조회를 한 후 에러가 나면 db로 다시 조회

        //redis
        List<PharmacyDto> pharmacyDtoList = pharmacyRedisTemplateService.findAll();
        if(!pharmacyDtoList.isEmpty()) return pharmacyDtoList;


        //db
       return pharmacyRepositoryService.findAll()
               .stream()
               .map(this::convertToPharmacyDto)
               .collect(Collectors.toList());
    }

    private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy){
    return PharmacyDto.builder()
            .id(pharmacy.getId())
            .pharmacyAddress(pharmacy.getPharmacyAddress())
            .pharmacyName(pharmacy.getPharmacyName())
            .latitude(pharmacy.getLatitude())
            .longitude(pharmacy.getLongitude())
            .build();
    }
}
