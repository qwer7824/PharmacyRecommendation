package com.pharmacyrecommendation.pharmacy.service;

import com.pharmacyrecommendation.api.dto.DocumentDto;
import com.pharmacyrecommendation.api.dto.KakaoApiResponseDto;
import com.pharmacyrecommendation.api.service.KakaoAddressSearchService;
import com.pharmacyrecommendation.direction.dto.OutputDto;
import com.pharmacyrecommendation.direction.entity.Direction;
import com.pharmacyrecommendation.direction.service.Base62Service;
import com.pharmacyrecommendation.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    @Value("${pharmacy.recommendation.base.url}")
    private String baseUri;

    public List<OutputDto> recommendPharmacyList(String address){

       KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

       if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())){
           log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address : {}",address);
           return Collections.emptyList(); // 조건에 맞지 않으면 엠티리스트 리턴
       }

       DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        //List<Direction> directionList = directionService.buildDirectionList(documentDto);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());


    }

    private OutputDto convertToOutputDto(Direction direction){

        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl(baseUri + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km",direction.getDistance()))
                .build();
    }
}
