# 항해 플로스 백엔드 코스 6기
## 프로젝트 소개
TDD와 클린 아키텍처를 적용 및 트래픽을 제어와 장애 상황 대안 방안을 고민 및 구현을 목표로 학습한 프로젝트입니다.

## 작업
- 기능별 단위 테스트 및 통합 테스트 작성
- 글로벌 에러 핸들러, 토큰 검증 인터셉터 구현
- 동시성 이슈 분석 작업과 동시성 제어 고도화
- 쿼리 캐싱를 통해 성능 개선
- Redis를 활용한 대기열 시스템 구축
- 인덱스 추가 및 보고서 작성
- MSA 관점에서 트랜잭션 관리 보고서 작성 및 event 구현
- kafka consumer, producer 작성 및 통합 테스트
- kafka 메시지 발행 적용 및 Transactional Outbox Pattern 적용
- 부하 테스트 대상 선정 및 분석 보고서 작성
- 부하 테스트 결과 분석 및 가상 장애 대응 문서 작성
  
## 시스템 설계 문서
- [시퀀스 다이어그램](https://github.com/seungwontech/hhplus-concert-week3-5/blob/main/docs/%EC%8B%9C%ED%80%80%EC%8A%A4%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.md)
- [ERD](https://github.com/seungwontech/hhplus-concert-week3-5/blob/main/docs/ERD.md)

## 기술 스택
- java17
- Spring Boot 3.3.4
- Spring Data JPA 
- Gradle build
- MariaDB 11.3.2
- k6
- grafana
- InfluxDB

<img src="https://github.com/user-attachments/assets/f7ddb170-5653-4f20-a33c-4f9243e6b3a6" width="300" height="400"/>


