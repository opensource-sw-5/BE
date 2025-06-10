# 👤 Vata-BE: 나만의 AI 프로필 이미지 생성기 (Backend)

Stable Diffusion 기반의 SNS 아바타 생성 웹 서비스의 "백엔드 레포지토리"입니다.  

## 🛠️ 기술 스택

- Java + Springboot
- Spring Security, Spring JPA, WebClient, RestTemplate
- Swagger
- MinIo, MySQL
- Nginx, Docker, Dokcer Compose, Docker Registry, Github Actions

## 📂 프로젝트 주요 구조

```
├───main
│   ├───java
│   │   └───com
│   │       └───vata
│   │           │   HealthCheckController.java
│   │           │   TestController.java
│   │           │   VataApplication.java
│   │           │   
│   │           ├───auth
│   │           │   ├───application
│   │           │   │       AuthFacade.java
│   │           │   │       UserFacade.java
│   │           │   │       
│   │           │   ├───controller
│   │           │   │   │   AuthController.java
│   │           │   │   │   UserController.java
│   │           │   │   │   
│   │           │   │   ├───dto
│   │           │   │   │       AccessKeyRequest.java
│   │           │   │   │       LoginRequest.java
│   │           │   │   │       SignupRequest.java
│   │           │   │   │       UserResponse.java
│   │           │   │   │       
│   │           │   │   └───swagger
│   │           │   │           AuthControllerSpec.java
│   │           │   │           UserControllerSpec.java
│   │           │   │           
│   │           │   └───domain
│   │           │       ├───entity
│   │           │       │       AccessKey.java
│   │           │       │       User.java
│   │           │       │       
│   │           │       ├───repository
│   │           │       │       AccessKeyRepository.java
│   │           │       │       UserRepository.java
│   │           │       │       
│   │           │       └───service
│   │           │               AccessKeyService.java
│   │           │               UserDetailService.java
│   │           │               UserService.java
│   │           │               
│   │           ├───common
│   │           │   ├───annotation
│   │           │   │       LoginUser.java
│   │           │   │       LoginUserArgumentResolver.java
│   │           │   │       
│   │           │   ├───configuration
│   │           │   │       MinioConfig.java
│   │           │   │       RestTemplateConfig.java
│   │           │   │       SecurityConfig.java
│   │           │   │       SwaggerConfig.java
│   │           │   │       WebClientConfig.java
│   │           │   │       WebConfig.java
│   │           │   │       
│   │           │   ├───jpa
│   │           │   │   └───shared
│   │           │   │           BaseEntity.java
│   │           │   │           
│   │           │   └───web
│   │           │           PagingResult.java
│   │           │           
│   │           └───profile
│   │               ├───application
│   │               │       ProfileFacade.java
│   │               │       ProfilePromptFacade.java
│   │               │       
│   │               ├───controller
│   │               │   │   ProfileController.java
│   │               │   │   
│   │               │   ├───dto
│   │               │   │       ImageGenerateResponse.java
│   │               │   │       ProfileListResponse.java
│   │               │   │       StabilityCreditsResponse.java
│   │               │   │       UserInputRequest.java
│   │               │   │       
│   │               │   └───swagger
│   │               │           ProfileControllerSpec.java
│   │               │           
│   │               ├───domain
│   │               │   ├───entity
│   │               │   │   │   Profile.java
│   │               │   │   │   UserInput.java
│   │               │   │   │   
│   │               │   │   └───vo
│   │               │   │           CharacterType.java
│   │               │   │           Gender.java
│   │               │   │           Mbti.java
│   │               │   │           NegativePrompt.java
│   │               │   │           StyleType.java
│   │               │   │           
│   │               │   ├───repository
│   │               │   │       ProfileRepository.java
│   │               │   │       
│   │               │   └───service
│   │               │           ProfileService.java
│   │               │           StabilityImageService.java
│   │               │           
│   │               └───infrastructure
│   │                       MinioService.java
│   │                       StabilityRestTemplate.java
│   │                       StabilityWebClient.java
│   │                       
│   └───resources
│           application-prod.yml
│           application.yml
│           
└───test
    ├───java
    └───resources
```

## 🌐 API 명세서 
👉 [API 명세 주소 바로가기](https://www.jhzlo.world/swagger-ui/index.html)

## ✅ 구현 기능 요약

- 회원가입 / 로그인 기능
    - `세션 기반 인증`
    - `@LoginUser Argument Resolver 구현`
- Access Token 등록 및 수정 기능
    - `RestTemplate`
- 유저의 입력 기반 프로필 이미지 생성
    - `WebClient`
- 생성된 이미지 보관함 확인 및 다운로드
- 프롬프트 엔지니어링
