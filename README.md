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
-외부 API 연동: Stability AI API, RestTemplate, WebClient

## 📂 프로젝트 주요 구조

```
├───main
│   ├───java
│   │   └───com
│   │       └───vata                    # 메인 패키지
│   │           │   HealthCheckController.java     # 헬스 체크용 컨트롤러
│   │           │   TestController.java            # 테스트용 컨트롤러
│   │           │   VataApplication.java           # 스프링 부트 애플리케이션 실행 진입점
│   │           ├───auth                          # 인증/인가 관련 기능 패키지
│   │           │   ├───application               # 인증 관련 도메인 로직을 조합한 서비스 계층 (파사드)
│   │           │   │       AuthFacade.java
│   │           │   │       UserFacade.java
│   │           │   ├───controller                # 인증 관련 API 컨트롤러
│   │           │   │   │   AuthController.java
│   │           │   │   │   UserController.java
│   │           │   │   ├───dto                   # 요청/응답 DTO 클래스
│   │           │   │   │       AccessKeyRequest.java
│   │           │   │   │       LoginRequest.java
│   │           │   │   │       SignupRequest.java
│   │           │   │   │       UserResponse.java
│   │           │   │   └───swagger               # Swagger 명세 정의
│   │           │   │           AuthControllerSpec.java
│   │           │   │           UserControllerSpec.java
│   │           │   └───domain                   # 인증 도메인 (엔티티, 리포지토리, 서비스)
│   │           │       ├───entity               # JPA 엔티티 클래스
│   │           │       │       AccessKey.java
│   │           │       │       User.java
│   │           │       ├───repository           # JPA 리포지토리 인터페이스
│   │           │       │       AccessKeyRepository.java
│   │           │       │       UserRepository.java
│   │           │       └───service              # 인증 도메인 서비스 클래스
│   │           │               AccessKeyService.java
│   │           │               UserDetailService.java
│   │           │               UserService.java
│   │           ├───common                       # 공통 유틸, 설정 등
│   │           │   ├───annotation               # 커스텀 어노테이션 및 리졸버
│   │           │   │       LoginUser.java
│   │           │   │       LoginUserArgumentResolver.java
│   │           │   ├───configuration            # 스프링 관련 설정 클래스
│   │           │   │       MinioConfig.java
│   │           │   │       RestTemplateConfig.java
│   │           │   │       SecurityConfig.java
│   │           │   │       SwaggerConfig.java
│   │           │   │       WebClientConfig.java
│   │           │   │       WebConfig.java
│   │           │   ├───jpa
│   │           │   │   └───shared               # JPA 엔티티 공통 베이스 클래스
│   │           │   │           BaseEntity.java
│   │           │   └───web                      # 공통 웹 관련 유틸
│   │           │           PagingResult.java    # 페이징 응답 포맷
│   │           └───profile                      # 프로필 생성/관리 기능
│   │               ├───application              # 프로필 관련 파사드 계층
│   │               │       ProfileFacade.java
│   │               │       ProfilePromptFacade.java
│   │               ├───controller               # 프로필 관련 API 컨트롤러
│   │               │   │   ProfileController.java
│   │               │   ├───dto                  # 요청/응답 DTO 클래스
│   │               │   │       ImageGenerateResponse.java
│   │               │   │       ProfileListResponse.java
│   │               │   │       StabilityCreditsResponse.java
│   │               │   │       UserInputRequest.java
│   │               │   └───swagger              # Swagger 명세 정의
│   │               │           ProfileControllerSpec.java
│   │               ├───domain                  # 프로필 도메인 구성
│   │               │   ├───entity              # 프로필 관련 엔티티
│   │               │   │   │   Profile.java
│   │               │   │   │   UserInput.java
│   │               │   │   └───vo              # 값 객체 (Enum 등)
│   │               │   │           CharacterType.java
│   │               │   │           Gender.java
│   │               │   │           Mbti.java
│   │               │   │           NegativePrompt.java
│   │               │   │           StyleType.java
│   │               │   ├───repository          # JPA 리포지토리
│   │               │   │       ProfileRepository.java
│   │               │   └───service             # 도메인 서비스 클래스
│   │               │           ProfileService.java
│   │               │           StabilityImageService.java
│   │               └───infrastructure          # 외부 서비스 연동
│   │                       MinioService.java            # Minio 연동 서비스
│   │                       StabilityRestTemplate.java   # Stability AI API (RestTemplate 기반)
│   │                       StabilityWebClient.java      # Stability AI API (WebClient 기반)
│   └───resources
│           application-prod.yml               # 프로덕션 설정
│           application.yml                    # 기본 설정
└───test
    ├───java                                  # 테스트용 Java 클래스 위치
    └───resources                             # 테스트 리소스
```

## 🌐 API 명세서 
👉 [API 명세 주소 바로가기](https://www.jhzlo.world/swagger-ui/index.html)

## ✅ 구현 기능 요약

- 사용자 인증 및 관리: 회원가입, 로그인, Access Token 발급, 갱신 및 만료 처리
    - @LoginUser, ArgumentResolver 적용
- AI 프로필 이미지 생성: 사용자의 성별, MBTI, 취미 등 입력 값을 기반으로 Stability AI API를 활용하여 개성 있는 프로필 이미지 생성
    - WebClient, RestTemplate 활용
- 생성 이미지 저장 및 관리: 생성된 AI 프로필 이미지를 Minio 스토리지를 통해 안전하게 저장하고 관리
- 사용자 프로필 목록 조회: 사용자가 생성한 AI 프로필 이미지 목록을 페이지 단위로 조회
- Stability AI 크레딧 조회: 사용자 계정의 Stability AI API 크레딧 잔액 조회
- API 문서화: Springdoc OpenAPI (Swagger UI)를 통한 API 자동 문서화
- 글로벌 예외 처리: 애플리케이션 전반에 걸쳐 일관된 오류 응답을 위한 공통 예외 처리
