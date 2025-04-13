## 그냥 참고용
react연동용 spring boot rest api다.<br>

## endpoints
swagger같은 걸로 자동 생성한다고 하는데, 일단 이런 것들이 있다.<br>
✅ 컬렉션 리소스 (모든 Student)<br>
| 메서드 | URL                | 설명                                               |
|--------|--------------------|----------------------------------------------------|
| GET    | /api/students          | 모든 학생 목록 조회 (페이징 포함)                 |
| POST   | /api/students          | 새로운 학생 생성                                   |
| GET    | /api/students/search   | 사용자 정의 쿼리 메서드 검색 목록 (예: `findByName`) |
✅ 개별 리소스 (단일 Student)<br>
| 메서드 | URL               | 설명                           |
|--------|-------------------|--------------------------------|
| GET    | /api/students/{id}    | 특정 학생 조회                |
| PUT    | /api/students/{id}    | 특정 학생 전체 업데이트       |
| PATCH  | /api/students/{id}    | 특정 학생 부분 업데이트       |
| DELETE | /api/students/{id}    | 특정 학생 삭제                |
선생의 경우는 /teachers 로 비슷한 방식으로 존재<br>
로그인의 경우는 POST /api/login<br>

## 출력 예
```http://localhost:8080/api/students
{
  "_embedded" : {
    "students" : [ {
      "name" : "김영수",
      "classNum" : 1,
      "teacher" : "박용철",
      "korean" : 60,
      "english" : 70,
      "math" : 80,
      "science" : 45,
      "history" : 75,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/students/1"
        },
        "student" : {
          "href" : "http://localhost:8080/api/students/1"
        }
      }
    }, {
      "name" : "조하영",
      "classNum" : 3,
      "teacher" : "이서준",
      "korean" : 67,
      "english" : 73,
      "math" : 80,
      "science" : 48,
      "history" : 70,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/students/15"
        },
        "student" : {
          "href" : "http://localhost:8080/api/students/15"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/api/students?page=0&size=20"
    },
    "profile" : {
      "href" : "http://localhost:8080/api/profile/students"
    },
    "search" : {
      "href" : "http://localhost:8080/api/students/search"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 15,
    "totalPages" : 1,
    "number" : 0
  }
}
```
```http://localhost:8080/api/students/search
{
  "_links" : {
    "findTopByHistory" : {
      "href" : "http://localhost:8080/api/students/search/findTopByHistory"
    },
    "findTopByMath" : {
      "href" : "http://localhost:8080/api/students/search/findTopByMath"
    },
    "findTopByScience" : {
      "href" : "http://localhost:8080/api/students/search/findTopByScience"
    },
    "findTopByAverage" : {
      "href" : "http://localhost:8080/api/students/search/findTopByAverage"
    },
    "findTopByKorean" : {
      "href" : "http://localhost:8080/api/students/search/findTopByKorean"
    },
    "findTopByEnglish" : {
      "href" : "http://localhost:8080/api/students/search/findTopByEnglish"
    },
    "self" : {
      "href" : "http://localhost:8080/api/students/search"
    }
  }
}
```

## CORS
```xxxApplication.java
//이하 추가
@Bean
CorsFilter corsFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.addAllowedOrigin("http://localhost:4173");
    corsConfig.addAllowedOrigin("http://localhost:5173");
    corsConfig.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
    corsConfig.addAllowedHeader("*");
    corsConfig.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", corsConfig);

    return new CorsFilter(source);
}
```

## application.properties
```
spring.application.name=demo
### ========== ✅ Oracle 데이터소스 설정 ==========
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=admin
spring.datasource.password=12345
### ========== ✅ JPA 설정 ==========
# 테이블 자동 생성 X (DB에서 이미 생성돼 있음)
spring.jpa.hibernate.ddl-auto=none
# 실행되는 SQL 출력 (선택)
spring.jpa.show-sql=true
# 콘솔 SQL 보기 좋게 (선택)
spring.jpa.properties.hibernate.format_sql=true
# 방언 수동 지정 (Oracle 사용 시 명시 권장)
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
# @RepositoryRestResource(path = "teachers") 에 전역경로 추가
spring.data.rest.base-path=/api
```

## 실행
gradle bootRun

## DB
oracle xe DB를 docker에서 돌려서 테스트 함. 아래 테이블 정의.  
dorker 이미지는 docker hub의 gvenzl/oracle-xe  
```sql
CREATE TABLE "ADMIN"."STUDENT" (
    "ID"      NUMBER,
    "NAME"    VARCHAR2(100 BYTE),
    "CLASS"   NUMBER,
    "TEACHER" VARCHAR2(100 BYTE),
    "KOREAN"  NUMBER,
    "ENGLISH" NUMBER,
    "MATH"    NUMBER,
    "SCIENCE" NUMBER,
    "HISTORY" NUMBER,
    PRIMARY KEY ("ID")
);
```
```sql
CREATE TABLE "ADMIN"."TEACHER" (
    "ID"    NUMBER,
    "NAME"  VARCHAR2(100 BYTE),
    "CLASS" NUMBER,
    PRIMARY KEY ("ID")
);
```
