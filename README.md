# bookpub-batch

## Getting Start
```java

./mvnw spring-boot:run 

``` 
## 서비스 소개

클라우드 서비스를 적용한 온라인 책 쇼핑몰 서비스

## 아키텍처 구조
![Book_Pub_ Architecture](https://user-images.githubusercontent.com/76966467/221409819-e28ffb39-6776-41ae-b258-8575184a629a.png)

## CD/CD 
![CI:CD](https://user-images.githubusercontent.com/87689191/220251384-aef3b943-bd11-4582-8e63-c11f88d5d81c.png)

## 프로젝트 관리(공통)

### WBS
- 구글 sheet 에서 version 별 관리.
<img width="1280" alt="image" src="https://user-images.githubusercontent.com/88470887/220016525-eecd7f3a-913d-47ad-ba2a-0fd2baaa829b.png">

### 칸반보드
- 두레이에서 제공하는 칸반보드사용.
<img width="1314" alt="image" src="https://user-images.githubusercontent.com/88470887/220016688-0d7c198a-ac63-43eb-bc15-2010fcdb79cf.png">

## 주요기능

### 쿠폰
#### [김서현](https://github.com/seohyeon07)
##### 회원 생일자 쿠폰 발급
1. 생일기준 +7 일을 하여 회원조회.
2. 해당회원에 쿠폰 발급.

### 배송
#### [유호철](https://github.com/HoFe-U)
##### 배송 중간지역추가
+ 회원의 도착일자 +7일 기준으로 확인.
1. 배송중이 배송상태 받기
2. 배송지역 테이블에 옥천허브인 값 추가
3. 배송의 배송준비 -> 배송중으로 변경
4. 주문의 배송준비 -> 배송중으로 변경
5. 주문상품의 배송준비 -> 배송중으로 변경

##### 배송 도착완료
+ 회원의 도착일자 +1일 기준으로 도착
1. 배송지역 테이블에 도착지역 값 추가
2. 배송의 배송중 -> 배송완료로 변경
3. 주문의 배송중 -> 배송완료로 변경
4. 주문상품의 배송중 -> 구매확정 대기 상태로 변경

### 주문
#### [유호철](https://github.com/HoFe-U)
##### 주문취소
+ 주문의 생성일자를 기준으로 하루초과시 실행
1. '결제대기' 상태의 주문에관한 쿠폰을 반환
2. '주문상태' 정보를 조회
3.  조회된 주문상태를 통해 주문의 상태값 주문취소로 변경

##### 구매확정
+ 주문이 배송완료나 구매확정일때 (경과시간 +7일) 주문상품을 구매확정으로 변경.
1. 포인트내역에 회원이 적용할포인트 추가
2. 회원의 포인트 업데이트
3. 해당 주문상품의 상태를 구매확정으로 변경
4. 해당 주문의 상태를 구매확정으로 변경

### 등급
#### [유호철](https://github.com/HoFe-U)
##### 회원 등급 승급
+ 회원의 등급이 기준금액에 맞을경우 승급시킵니다.
1. 등급을 조회
2. 금액을 기준으로 회원의 등급 변경

<link src="https://github.com/HoFe-U"/>

## 사용기술

  <div align=center> 
    <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
    <img src="https://img.shields.io/badge/springbatch-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white">
    <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
    <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
    <img src="https://img.shields.io/badge/maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white">
  </div>

## 팀원

<br>
<a href="https://github.com/NHN-BookPub/bookpub-batch/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=NHN-BookPub/bookpub-batch" />
</a>
