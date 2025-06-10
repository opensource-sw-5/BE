# 👤 Vata-BE: 나만의 AI 프로필 이미지 생성기 (Backend)

Stable Diffusion 기반의 SNS 아바타 생성 웹 서비스의 백엔드 레포지토리입니다. 
사용자 인증, AI 프로필 이미지 생성 및 저장, 프로필 목록 조회 및 Stability AI 크레딧 관리 등 핵심 API를 제공합니다.

## 🛠️ 기술 스택

-언어: Java 17
-프레임워크: Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Security 등)
-빌드 도구: Gradle
-데이터베이스: MySQL 8.x
-ORM: Spring Data JPA & Hibernate
-유틸리티: Lombok
-API 문서화: Springdoc OpenAPI (Swagger UI)
-스토리지 연동: Minio S3 Client (S3 호환 스토리지)
-외부 API 연동: Stability AI API

## 📂 프로젝트 주요 구조

```
Vata-BE
├── src/main/java/com/vata
│   ├── auth              # 사용자 인증 및 인가 모듈
│   │   ├── application   # 인증 비즈니스 로직 (AuthFacade)
│   │   ├── controller    # 인증 API (AuthController)
│   │   ├── domain        # 인증 도메인 (Entity, Repository, Service)
│   │   └── dto           # 인증 관련 데이터 전송 객체
│   ├── common            # 공통 유틸리티 및 설정
│   │   ├── annotation    # @LoginUser 등 공통 어노테이션
│   │   ├── exception     # 커스텀 예외 처리
│   │   └── web           # PagingResult 등 웹 관련 공통 유틸리티
│   ├── config            # Spring 설정 클래스 (SecurityConfig, WebClientConfig, MinioConfig 등)
│   ├── profile           # AI 프로필 생성 및 관리 모듈
│   │   ├── application   # 프로필 비즈니스 로직 (ProfileFacade, ProfilePromptFacade)
│   │   ├── controller    # 프로필 API (ProfileController)
│   │   │   └── swagger   # OpenAPI (Swagger) API 문서 인터페이스 (ProfileControllerSpec)
│   │   ├── domain        # 프로필 도메인 (Entity, Repository, Service)
│   │   └── infrastructure# 외부 서비스 연동 (StabilityRestClient, StabilityWebClient)
│   └── TestController.java # 애플리케이션 상태 확인용 기본 컨트롤러
├── src/main/resources    # 애플리케이션 설정 파일 (application.yml)
├── build.gradle          # Gradle 빌드 설정 파일
└── gradlew               # Gradle Wrapper 스크립트
```

## ✅ 구현 기능 요약

- 사용자 인증 및 관리: 회원가입, 로그인, Access Token 발급, 갱신 및 만료 처리
- AI 프로필 이미지 생성: 사용자의 성별, MBTI, 취미 등 입력 값을 기반으로 Stability AI API를 활용하여 개성 있는 프로필 이미지 생성
- 생성 이미지 저장 및 관리: 생성된 AI 프로필 이미지를 Minio 스토리지를 통해 안전하게 저장하고 관리
- 사용자 프로필 목록 조회: 사용자가 생성한 AI 프로필 이미지 목록을 페이지 단위로 조회
- Stability AI 크레딧 조회: 사용자 계정의 Stability AI API 크레딧 잔액 조회
- API 문서화: Springdoc OpenAPI (Swagger UI)를 통한 API 자동 문서화
- 글로벌 예외 처리: 애플리케이션 전반에 걸쳐 일관된 오류 응답을 위한 공통 예외 처리
