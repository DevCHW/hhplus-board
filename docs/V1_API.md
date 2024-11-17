# V1 API 명세

---

## 기본 응답 구조 예시

```json
{
    "result": "SUCCESS",
    "data": {
      "message": "Hello, World!"
    }
}
```

| 이름 | 타입 | 설명 |
| --- | --- | --- |
| result | String | 응답 결과 (SUCCESS: 성공 / ERROR: 실패) |
| data | Object / Array | 데이터 |

## 에러 응답 구조 예시

```json
{
    "result": "ERROR",
    "error": {
      "code": "ERROR_001",
      "message": "예상치 못한 서버 에러가 발생했습니다.",
      "data": null
    }
}
```

## 에러코드
| 코드 | 설명 | 상태 코드 |
| --- | --- | --- |
| ERROR_001 | 서버 에러가 발생한 경우 | 500 |
| ERROR_002 | 사용자 인증에 실패한 경우 | 401 |
| ERROR_003 | 사용자 권한이 없는 경우 | 403 |
| ERROR_004 | 요청 값 검증에 실패한 경우 | 400 |
| ERROR_005 | 리소스를 찾을 수 없는 경우 | 404 |
| ERROR_006 | 잘못된 요청인 경우 | 400 |
| ERROR_007 | 서버가 요청을 처리할 수 없는 경우 | 422 |


### 회원가입 API
**Endpoint**  
POST /api/v1/users

**Request Body Example**
```json
{
	"username": "user001",
	"password": "qwer1234$"
}
```

**Request Fields**

| 필드명 | 타입 | 필수 | 설명 | 조건 |
| --- | --- | --- | --- | --- |
| username | String | Y | 이름 | 최소 4자 이상, 10자 이하, 알파벳 소문자(a~z), 숫자(0~9) |
| password | String | Y | 비밀번호  | 8자 이상, 15자 이하,  알파벳 대소문자(a~z, A~Z), 숫자(0~9) |

**Response Body Example**
```json
{
  "result": "SUCCESS",
  "data": {
	  "id": "1",
	  "username": "최현우",
	  "message": "회원 가입에 성공하였습니다!"
  }
}
```

**Response Fields**

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| result | String | 응답 결과 (SUCCESS: 성공 / ERROR: 실패) |
| data | Object | 데이터 |
| data.id | Number | 회원 ID |
| data.username | String | 회원 이름 |
| data.message | String | 메세지 |

### 로그인 API
**Endpoint**  
`POST /api/v1/auth/login`

**Request Body Example**
```json
{
    "username": "user001",
    "password": "qwer1234$"
}
```

**Request Fields**

| 필드명 | 타입 | 필수 | 설명 | 조건 |
| --- | --- | --- | --- | --- |
| username | String | Y | 이름 |  |
| password | String | Y | 비밀번호  |  |


**Response Body Example**
```json
{
    "result": "SUCCESS",
    "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
        "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
        "expiresIn": 1700180745000,
        "tokenType": "Bearer",
        "message": "로그인에 성공하였습니다."
    }
}
```

**Response Fields**

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| result | String | 응답 결과 (SUCCESS: 성공 / ERROR: 실패) |
| data | Object | 데이터 |
| data.accessToken | String | 엑세스 토큰 |
| data.refreshToken | String | 리프레쉬 토큰 |
| data.expiresIn | Number | 만료 시간 |
| data.tokenType | String | 토큰 타입 |
| data.message | String | 메세지 |

### 게시글 작성 API

**EndPoint**  
`POST /api/v1/boards`

**Request Header**

| 이름 | 설명 |
| --- | --- |
| Authorization | Bearer Token |

**Request Body Example**
```json
{
    "title": "제목",
    "content": "내용",
    "password": "1234"
}
```

**Request Fields**

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| title | String | Y | 제목 |
| content | String | Y | 내용 |
| password | String | Y | 비밀번호 |

**Response Body Example**
```json
{
  "result": "SUCCESS",
  "data": {
    "id": 1,
    "username": "user001",
    "title": "제목",
    "content": "내용"
  }
}
```

**Response Fields**

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| result | String | 응답 결과 (SUCCESS: 성공 / ERROR: 실패) |
| data | Object | 데이터 |
| data.id | Number | 게시글 ID |
| data.username | String | 작성자명 |
| data.title | String | 게시글 제목 |
| data.content | String | 게시글 내용 |


#### 게시글 수정 API

**Endpoint**  
`PUT /api/v1/boards/{boardId}`

**Request Header**

| 이름 | 설명 |
| --- | --- |
| Authorization | Bearer Token |

**Path Parameter**

| 이름 | 설명 |
| --- | --- |
| boardId | 게시글 ID |

**Request Body Example**

```json
{
    "username": "user001",
    "title": "수정 제목",
    "content": "수정 내용",
    "password": 1234
}
```

**Request Fields**

| 필드명 | 타입 | 필수 | 설명 | 조건 |
| --- | --- | --- | --- | --- |
| username | String | Y | 작성자명 |  |
| title | String | Y | 제목 |  |
| content | String | Y | 내용 |  |
| password | String | Y | 비밀번호 |  |


**Response Body Example**

```json
{
  "result": "SUCCESS",
  "data": {
      "id": 1,
      "username": "최현우",
      "title": "수정 제목",
      "content": "수정 내용"
  }
}
```

**Response Fields**

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| result | String | 응답 결과 (SUCCESS: 성공 / ERROR: 실패) |
| data | Object | 데이터 |
| data.id | Number | 게시글 ID |
| data.username | String | 작성자명 |
| data.title | String | 게시글 제목 |
| data.content | String | 게시글 내용 |


### 게시글 삭제 API

**Endpoint**
`DELETE /api/v1/boards/{boardId}`

**Request Header**

| 이름 | 설명 |
| --- | --- |
| Authorization | Bearer Token |

**Path Parameter**

| 이름 | 설명 |
| --- | --- |
| boardId | 게시글 ID |


**Response Body Example**
```json
{
    "result": "SUCCESS",
    "data": {}
}
```

**Response Fields**

| 필드명 | 타입 | 설명 |
| --- | --- |  -- |
| result | String | 응답 결과 (SUCCESS: 성공 / ERROR: 실패) |
| data | Object | 데이터 |


### 게시글 목록 조회 API

**Endpoint**

`GET /api/v1/boards`

**Response Body Example**

```json
{
    "result": "SUCCESS",
    "data": [
        {
          "id": 1,
          "username": "user001",
          "title": "제목1",
          "content": "내용1",
          "createdAt": "2024-11-17T12:33:32.123432",
          "updatedAt": "2024-11-17T12:33:32.123432"
        },
        {
          "id": 2,
          "username": "user001",
          "title": "제목2",
          "content": "내용2",
          "createdAt": "2024-11-16T12:33:32.123432",
          "updatedAt": "2024-11-16T12:33:32.123432"
        }
    ]
}
```

**Response Fields**

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| result | String | 응답 결과 (SUCCESS: 성공 / ERROR: 실패) |
| data[] | Array | 데이터 |
| data[].id | Number | 게시글 ID |
| data[].username | String | 작성자명 |
| data[].title | String | 제목 |
| data[].content | String | 본문 |
| data[].createdAt | String | 생성 시점 |
| data[].updatedAt | String | 마지막 수정 시점 |


### 게시글 단건 조회 API

**Endpoint**

`GET /api/v1/boards/{boardId}`

**Path Parameter**

| 이름 | 설명 |
| --- | --- |
| boardId | 게시글 ID |

**Response Body Example**
```json
{
    "result": "SUCCESS",
    "data": {
        "id": 1,
        "username": "user001",
        "title": "제목1",
        "content": "내용1",
        "createdAt": "2024-11-17T12:33:32.123432",
        "updatedAt": "2024-11-17T12:33:32.123432"
    }
}
```

**Response Fields**

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| result | String | 응답 결과 (SUCCESS: 성공 / ERROR: 실패) |
| data | Object | 데이터 |
| data.id | Number | 게시글 ID |
| data.username | String | 작성자명 |
| data.title | String | 제목 |
| data.content | String | 본문 |
| data.createdAt | String | 생성 시점 |
| data.updatedAt | String | 마지막 수정 시점 |
