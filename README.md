## 허브기반 B2B 물류 관리 서비스 물류핑
업체간의 상품 주문 및 배송 서비스를 대행하는 플랫폼을 MSA 기반으로 구현
<br> 

## Team
| <img src="https://avatars.githubusercontent.com/u/35358294?v=4" width="130" height="130"> | <img src ="https://avatars.githubusercontent.com/u/96743351?v=4" width="130" height="130"> | <img src="https://avatars.githubusercontent.com/u/120196095?v=4 " width="130" height="130"> |
|:-----------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|  
|                          [yooyouny](https://github.com/yooyouny)                          |                            [Kimgunwooo](https://github.com/Kimgunwooo)                         |                             [gidopa](https://github.com/gidopa)                             
|                                           주문/배송                                           |                            유저/인증/알림                            |                                          허브/업체/상품                                           

[Convention](https://github.com/yooyouny/logistics-management-service/wiki/Convention)

 
## Skill
![Java 17](https://img.shields.io/badge/java_17-0078D6?style=for-the-badge&logo=java&logoColor=white)
![Boot](https://img.shields.io/badge/springboot-0078D6?style=for-the-badge&logo=springboot&logoColor=#)
![Gradle](https://img.shields.io/badge/Gradle-0078D6.svg?style=for-the-badge&logo=Gradle&logoColor=white)

![PostgreSQL](https://img.shields.io/badge/postgresql-0078D6?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-0078D6.svg?style=for-the-badge&logo=redis&logoColor=red)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-0078D6?style=for-the-badge&logo=apachekafka)

![Spring Cloud Gateway](https://img.shields.io/badge/SpringCloudGateway-0078D6?style=for-the-badge&logo=spring&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-0078D6?style=for-the-badge&logo=hibernate&logoColor=white)
![Security](https://img.shields.io/badge/SPRING_SECURITY-0078D6?style=for-the-badge&logo=springsecurity&logoColor=#)
![Slack](https://img.shields.io/badge/Slack-0078D6?style=for-the-badge&logo=slack&logoColor=4A154B)
![Gemini](https://img.shields.io/badge/googlegemini-0078D6?style=for-the-badge&logo=googlegemini&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-0078D6?style=for-the-badge&logo=Prometheus&logoColor=E6522C)

![Grafana](https://img.shields.io/badge/grafana-%23F46800.svg?style=for-the-badge&logo=grafana&logoColor=E6522C)
![Docker](https://img.shields.io/badge/docker-0078D6.svg?style=for-the-badge&logo=docker&logoColor=white)
![GitHub](https://img.shields.io/badge/Git-0078D6?style=for-the-badge&logo=git&logoColor=#)

:bulb: Kafka
> 주문/배송 도메인에서 서비스간 호출을 메시징 시스템으로 전환, 상태변경을 비동기로 처리할 때 적용

:bulb: Scheduler / Gemini
> Gemini를 통해 요약한 배송 정보와 공공 날씨 API를 이용한 날씨 정보를 매일 배송담당자에게 슬랙 알림으로 발송

:bulb: QueryDSL
> 검색시 복잡한 조건 및 동적 쿼리 작성을 위해 적용

:bulb: Redis
> 자주 사용하는 허브간 이동내역을 저장하기 위해 캐시 사용

## Installation and Getting Started
1. Clone the repository:
```
git clone https://github.com/yooyouny/logistics-management-service.git
```
2. Run docker-compose 
```
docker-compose up -d
```
[Postman WorkFlow Test](https://winter-equinox-1079.postman.co/workspace/Team-Workspace~777f04e4-057d-4881-818e-d0a5634c8662/flow/66ebfb79a874ef003d4b19e5)

## Technical Decisions
[인증과정 워크플로우](https://github.com/yooyouny/logistics-management-service/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-%EA%B9%80%EC%9C%A0%EC%97%B0-(%ED%83%80%EC%9D%B4%ED%8B%80))

[주문/배송 도메인에 이벤트 기반 메시징 도입하기](https://github.com/yooyouny/logistics-management-service/wiki/%EC%A3%BC%EB%AC%B8-%EB%B0%B0%EC%86%A1-%EB%8F%84%EB%A9%94%EC%9D%B8%EC%97%90-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EA%B8%B0%EB%B0%98-%EB%A9%94%EC%8B%9C%EC%A7%95-%EB%8F%84%EC%9E%85%ED%95%98%EA%B8%B0)


## Infra Structure
![image](https://github.com/user-attachments/assets/035457c9-51c3-44da-aea1-de3265e9c1c0)

## ERD
![ERD](https://github.com/user-attachments/assets/827557b1-790f-4de6-bdd5-12037f97f16b)

## Database Schema
[테이블 명세서](https://github.com/yooyouny/logistics-management-service/wiki/Table)

## Project structure
- 멀티모듈로 각 서비스 분리 관리
- 모노레포에서 코드 관리
```
project
├── common                             # 공통 모듈
│   └── domain
│
├── service                            
│   ├── dto                            # 외부서비스 통신을 위한 dto 모듈
│   │   └── OrderDTO.java             
│   ├── server                         # 서비스 서버 모듈
│   │   ├── service
│   │   │   └── OrderService.java     
│   │   ├── domain
│   │   │   ├── model                 
│   │   │   ├── repository
│   │   │   │   └── OrderRepository.java   
│   │   │   └── service
│   │   │       └── OrderDomainService.java
│   │   ├── infrastructure
│   │   │   ├── repository
│   │   │   ├── configuration
│   │   │   └── messaging
│   │   │       └── KafkaMessageProducer.java
│   │   └── presentation
│   │       ├── controller
│   │       └── request
```

## API Design
- :closed_lock_with_key: User 로그인 / 회원 가입
    - 회원 가입: 새로운 사용자는 회원 가입을 통해 계정을 생성할 수 있습니다
    - 권한 관리: 사용자는 일반 사용자, 공급 업체 관리자, 허브 관리자, 시스템 관리자 등 다양한 권한을 부여받을 수 있으며, 권한에 따라 접근할 수 있는 서비스가 다릅니다.
    - [User API 명세서](https://github.com/yooyouny/logistics-management-service/wiki/API-DESIGN#user-apis)

- :factory: Hub 관리 : 전체 관리자는 수정/삭제/생성 등의 허브 관리의 권한을 가지며 , 이용자들은 허브들의 정보를 확인할 수 있습니다.
    - 허브 생성: 시스템 관리자는 새로운 허브(물류 거점)를 시스템에 추가할 수 있습니다.
    - 허브 수정 및 삭제: 관리자 권한을 가진 사용자는 기존 허브의 정보를 수정하거나, 필요하지 않은 허브를 삭제할 수 있습니다.
    - 허브 조회: 일반 사용자와 업체 관리자는 허브들의 정보를 조회할 수 있습니다.
    - [Hub API 명세서](https://github.com/yooyouny/logistics-management-service/wiki/API-DESIGN#hub-apis)


- :credit_card: 주문 관리 : 사용자는 공급 업체로부터 상품을 주문하고, 받아볼 수 있습니다.
    - 주문 생성: 사용자는 공급 업체로부터 상품을 주문할 수 있습니다.
    - 주문 관리: 공급 업체 관리자는 받은 주문을 확인하고, 주문을 처리합니다.
    - [Order API 명세서](https://github.com/yooyouny/logistics-management-service/wiki/API-DESIGN#order-apis)


- :articulated_lorry: 배송 관리 : 출발지 / 도착지 정보에 따라 허브를 거쳐 배송을 진행하고, 배송 정보를 확인할 수 있습니다.
    - 배송 경로 추적: 사용자는 주문한 상품이 어느 허브에 위치해 있는지 또는 어느 지역을 지나고 있는지 실시간으로 추적할 수 있습니다
    - 배송 완료 처리: 최종 도착지에 상품이 도착하면 배송 완료로 처리되며, 사용자는 이를 확인하고 상품 수령 여부를 확인할 수 있습니다.
    - [Delivery API 명세서](https://github.com/yooyouny/logistics-management-service/wiki/API-DESIGN#delivery-apis)


- :office: 업체 관리 : 공급업체 / 이용업체에 따라 여러 상품들을 주문하거나 판매할 수 있습니다.
    - [Company API 명세서](https://github.com/yooyouny/logistics-management-service/wiki/API-DESIGN#company-apis)


- :moneybag: 상품 관리 : 공급업체는 자신이 판매할 상품들에 대해 관리할 수 있습니다.
    - [Prodcut API 명세서](https://github.com/yooyouny/logistics-management-service/wiki/API-DESIGN#product-apis)


- :mega: 슬랙 기능 : 담당자들에게 슬랙을 보내 업무 효율을 높일 수 있습니다
    - 자동 알림 전송: 업무 효율성을 높이기 위해 특정 조건이 발생하면 담당자에게 슬랙 메시지로 알림을 보낼 수 있습니다.
    - [Slack API 명세서](https://github.com/yooyouny/logistics-management-service/wiki/API-DESIGN#slack-apis)
