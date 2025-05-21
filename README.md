# infra-team2



````markdown
# Infra-Team2 문제은행 웹 애플리케이션

Spring Boot 기반의 온라인 문제은행 웹 애플리케이션입니다.  
도메인 중심 구조로 설계되어 유지보수성과 확장성을 고려하였습니다.

---

## Docker 실행 방법

이미지를 Docker Hub에서 pull 받아 실행할 수 있습니다:

```bash
docker pull hjukeboxox/infra-team2:1.0
docker run --name infra-app -p 8080:8080 hjukeboxox/infra-team2:1.0
````

---

### 실행 상태 및 접속 주소 확인

```bash
# 실행 중인 컨테이너 목록 확인
docker ps
```

```bash
# 예시 출력
CONTAINER ID   IMAGE                        PORTS
abcd1234       hjukeboxox/infra-team2:1.0   0.0.0.0:8080->8080/tcp
```

이 경우, 브라우저 접속 주소는 다음과 같습니다:

```
http://localhost:8080
```

만약 VM, 클라우드, WSL 등의 환경이라면 `localhost` 대신 아래 명령어로 **외부 접속용 IP 확인** 가능합니다.

```bash
# 현재 호스트 머신의 IP 확인 (리눅스/WSL/VM 기준)
ip a | grep inet
```

또는

```bash
# WSL 사용 시
hostname -I
```

그리고 외부에서 접속 시엔 아래와 같이 확인한 IP를 이용해서 접속하면 됩니다:

```
http://<your-ip>:8080
```

---

## 프로젝트 구조

```
project-root/
├── docker-compose.yml
├── Dockerfile
├── build/libs/app.jar
├── .env                            # (옵션) 환경변수 설정
└── src/main/java/com/team/infra_team2/
    ├── user/                       # 회원가입, 로그인 등 인증 도메인
    ├── question/                   # 문제 등록, 조회 등 문제 도메인
    ├── choice/                     # 선택지 관리 도메인
    ├── answer/                     # 답안 제출 도메인
    └── solve/                      # 풀이 세션 및 결과 도메인
```

---

## 기술 스택

* Java 17
* Spring Boot
* Spring Security
* MySQL 8
* Docker / Docker Compose
* Thymeleaf (템플릿 엔진)
* Bootstrap (UI 구성)
---

## 주요 기능

* 회원가입 및 로그인 (Spring Security 기반)
* 문제 목록 및 단건 조회
* 문제 출제 및 정답 제출
* 풀이 진행/종료/기록 관리
* 관리자 전용 문제 관리 페이지

---

> 문의 및 피드백은 Issues를 통해 남겨주세요.
