DB ERD 초기 설계 완료 ( ERD Cloud)
build.gradle 기반 초기 프로젝트 환경 설정 완료
[ stack ]
- spring web
- OAuth2 (적용 예정) - 전체 구현 후 점진적으로 적용 예정 => DB DDL 수정 불가피
- Spring Security(적용 예정) - 전체 구현 후 점진적으로 적용 예정 => DB DDL 수정 불가피
- JPA
- Spring Data JPA
- QueryDsl
- MySQL
- restdocs(적용 예정) - API 자동 문서화 적용

JPA 도메인 구현 완료

기본 DAO 및 Util 클래스 구현 완료

(계획)
우선, 로컬 저장소 회원 관리 기준으로 구현 => Oauth 및 스프링 시큐리티 이용한 클래스로 구현

(전달할 것)
페이징 가능함. UI나 프론트쪽에서 페이징관련 기능 추가할 것 있으면 구현 가능.