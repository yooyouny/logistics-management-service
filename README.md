# :star: 물류핑 :star:
HUB 기반 B2B 물류 관리 MSA 어플리케이션

<br> 

## 1. 팀원 소개 및 역할
| 이름       | 담당 분야      | 맡은 역할                                               |
|------------|----------------|---------------------------------------------------------|
| 김유연     | 백엔드 , 팀장     | 주문 및 배송 도메인                     |
| 김건우     | 백엔드            | User 및 인증 도메인 , 슬랙 관련 기능                    |
| 박기도     | 백엔드           | 허브, 업체 및 상품 도메인       |

<br> 

## 2. 프로젝트 목적 / 상세
물류핑은 허브 기반의 물류 이동 내역을 효율적으로 관리하고 각 배송 / 관리 담당자들에게 슬랙을 통한 알림 처리를 자동화 하여, 반복 작업을 줄이고 효율적으로 사용할 수 있도록 설계되었습니다.

<br> 

### :computer: Development Environment
`JDK 17`, `SpringBoot 3.3.3`, `JPA Hibernate 6`, `Docker-Compose 3.8`, `Redis 7.4`

### 주요 기능
- :closed_lock_with_key: User 로그인 / 회원 가입 : 각 권한에 따라 서비스에 접근 가능합니다
- :factory: Hub 관리 : 전체 관리자는 수정/삭제/생성 등의 허브 관리의 권한을 가지며 , 이용자들은 허브들의 정보를 확인할 수 있습니다.
- :credit_card: 주문 관리 : 사용자는 공급 업체로부터 상품을 주문하고, 받아볼 수 있습니다. 
- :articulated_lorry: 배송 관리 : 출발지 / 도착지 정보에 따라 허브를 거쳐 배송을 진행하고, 배송 정보를 확인할 수 있습니다. 
- :office: 업체 관리 : 공급업체 / 이용업체에 따라 여러 상품들을 주문하거나 판매할 수 있습니다.
- :moneybag: 상품 관리 : 공급업체는 자신이 판매할 상품들에 대해 관리할 수 있습니다.
- :computer: 검색 기능 : 검색 기능을 제공하여 원하는 정보를 필터링하여 편하게 확인할 수 있습니다.
- :mega: 슬랙 기능 : 담당자들에게 슬랙을 보내 업무 효율을 높일 수 있습니다

  
### 적용 기술
:bulb: Kafka
> 주문 시 Kafka를 이용한 비동기 처리를 통한 책임 분리

:bulb: Scheduler / Gemini
> 매일 정해진 시간에 Gemini를 통해 요약한 배송 정보와 공공 날씨 API를 이용한 날씨 정보를 요약해 담당자에게 슬랙 알람 발송

:bulb: QueryDSL
> 검색 등 동적 쿼리 작성을 위한 QueryDSL 적용

:bulb: Redis
> 자주 변하지 않는 데이터 들의 API 호출 횟수 감소를 위해 Redis 적용


## 3. 서비스 구성 및 실행 방법
### 기본 사항
- Java 17
- Docker-Compose
  - PostgreSQL 15
  - Reids 7.4.0
- application.yml 수정

### 실행 방법
1. docker-compose 실행
```
docker-compose up -d
```
2. 레포지토리 클론
```
git clone https://github.com/yooyouny/logistics-management-service.git
```
3. Eureka Server / GateWay 실행
```
cd eureka
./gradlew clean build  
./gradlew bootRun     

cd gateway
./gradlew clean build 
./gradlew bootRun      
```
4. 서비스 실행
```
cd order-service
./gradlew clean build  
./gradlew bootRun      
```

## 4. InfraStructure
![image](https://github.com/user-attachments/assets/035457c9-51c3-44da-aea1-de3265e9c1c0)

## 5. ERD
![ERD](https://github.com/user-attachments/assets/827557b1-790f-4de6-bdd5-12037f97f16b)

## 6. Tech / Skills
![IntelliJ](https://img.shields.io/badge/IntelliJ-0078D6.svg?style=for-the-badge&logo=intellijidea&logoColor=#000000)
![PostgreSQL](https://img.shields.io/badge/postgresql-0078D6?style=for-the-badge&logo=postgresql&logoColor=white)
![Boot](https://img.shields.io/badge/springboot-0078D6?style=for-the-badge&logo=springboot&logoColor=#)
![Gradle](https://img.shields.io/badge/Gradle-0078D6.svg?style=for-the-badge&logo=Gradle&logoColor=white)

![Security](https://img.shields.io/badge/springsecurity-0078D6?style=for-the-badge&logo=springsecurity&logoColor=#)
![JPA](https://img.shields.io/badge/JPA-0078D6?style=for-the-badge&logo=aseprite&logoColor=#)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-0078D6?style=for-the-badge&logo=apachekafka)
![Redis](https://img.shields.io/badge/redis-0078D6.svg?style=for-the-badge&logo=redis&logoColor=red)
![Slack](https://img.shields.io/badge/Slack-0078D6?style=for-the-badge&logo=slack&logoColor=4A154B)
![Gemini](https://img.shields.io/badge/googlegemini-0078D6?style=for-the-badge&logo=googlegemini&logoColor=white)

![Prometheus](https://img.shields.io/badge/Prometheus-0078D6?style=for-the-badge&logo=Prometheus&logoColor=E6522C)
![Grafana](https://img.shields.io/badge/grafana-%23F46800.svg?style=for-the-badge&logo=grafana&logoColor=E6522C)
![Docker](https://img.shields.io/badge/docker-0078D6.svg?style=for-the-badge&logo=docker&logoColor=white)
![GitHub](https://img.shields.io/badge/Git-0078D6?style=for-the-badge&logo=git&logoColor=#)

## 7. Trouble Shootings

[김유연](https://github.com/yooyouny/logistics-management-service/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-%EA%B9%80%EC%9C%A0%EC%97%B0-(%ED%83%80%EC%9D%B4%ED%8B%80))

[박기도](https://github.com/yooyouny/logistics-management-service/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-%EB%B0%95%EA%B8%B0%EB%8F%84)


## 8. API Design
[API Docs](https://github.com/yooyouny/logistics-management-service/wiki/API-DESIGN)



