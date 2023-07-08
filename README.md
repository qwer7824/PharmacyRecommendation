# Pharmacy-Recommendation   

## 요구사항 분석 

- 약국 찾기 서비스 요구사항
  - 약국 현황 데이터(공공 데이터)를 관리하고 있다 라고 가정하고, 약국 현황 데이터는 위도 경도의 위치 정보 데이터를 가지고 있다.
  - 해당 서비스로 주소 정보를 입력하여 요청하면 위치 기준에서 가까운 약국 3 곳을 추출 한다.
  - 주소는 도로명 주소 또는 지번을 입력하여 요청 받는다.
    - 정확한 주소를 입력 받기 위해 [Kakao 우편번호 서비스](https://postcode.map.daum.net/guide) 사용   
  - 주소는 정확한 상세 주소(동, 호수)를 제외한 주소 정보를 이용하여 추천 한다.   
    - ex) 서울 성북구 종암로 10길
  - 입력 받은 주소를 위도, 경도로 변환 하여 기존 약국 데이터와 비교 및 가까운 약국을 찾는다.   
    - 지구는 평면이 아니기 때문에, 구면에서 두 점 사이의 최단 거리 구하는 공식이 필요    
    - 두 위 경도 좌표 사이의 거리를 [haversine formula](https://en.wikipedia.org/wiki/Haversine_formula)로 계산  
    - 지구가 완전한 구형이 아니 므로 아주 조금의 오차가 있다.   
  - 입력한 주소 정보에서 정해진 반경(10km) 내에 있는 약국만 추천 한다.   
  - 추출한 약국 데이터는 길안내 URL 및 로드뷰 URL로 제공 한다.   
    - ex)    
    길안내 URL : https://map.kakao.com/link/map/우리회사,37.402056,127.108212    
    로드뷰 URL : https://map.kakao.com/link/roadview/37.402056,127.108212     

  - 길안내 URL은 고객에게 제공 되기 때문에 가독성을 위해 shorten url로 제공 한다.
  - shorten url에 사용 되는 key값은 인코딩하여 제공 한다.
    - ex) http://localhost:8080/dir/nqxtX
    - base62를 통한 인코딩    
  - shorten url의 유효 기간은 30일로 제한 한다.   
  
## Pharmacy Recommendation Process   

<img width="615" alt="스크린샷 2022-07-07 오후 1 58 39" src="https://user-images.githubusercontent.com/26623547/177694773-b53d1251-652f-41e6-8f19-c32b931d4b5b.png">         

## Direction Shorten Url Process

<img width="615" alt="스크린샷 2022-06-23 오후 9 42 58" src="https://user-images.githubusercontent.com/26623547/175301168-ee35793c-18ff-4a4a-8610-7a9455e9fef7.png">    


## Feature List   

- Kakao Address Search API
- Spock 프레임워크를 이용한 테스트 작성
- Testcontainers 를 이용한 통합테스트 환경 구성
- Kakao 주소검색 api 테스트
- JPA Dirty Checking
- JPA Auditing 으로 생성시간 - 수정시간 자동화 구현
- Spring Transactional 사용시 주의사항
- 약국데이터 셋업
- 거리 계산 알고리즘 구현
- Spring Retry 구현 및 검증
- 추천 결과 저장 기능 구현
- 카카오 키워드 장소 검색 api 적용하기
- 부트스트랩 , Jquery 및 Handlebars 라이브러리 적용
- 최종 길안내 개발
- Redis 로 성능 개선
- 배포하기

## Tech Stack   

- JDK 11
- Spring Boot 2.6.7
- Spring Data JPA
- Gradle
- Handlebars
- Lombok
- Github
- Docker
- AWS EC2
- Redis
- MariaDB
- Spock   
- Testcontainers   

## Result   

[AWS배포 URL](http://15.164.253.121)
