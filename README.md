# MyBatis DB연결 세팅

### 페이징 개수 변경법
- boards.xml 에 id=paging 부분에 ceil(count(*)/5) totalPage
- boards.xml 에 id=findAll 부분에 FETCH NEXT 5 ROWS ONLY
- BoardsService 에 게시글목록보기() 메서드에 int startNum = page * 5;

### 설정방법
- MyBatisConfig 파일 필요
- resources/mapper/*.xml 파일 필요
- Users 엔티티 필요
- UsersDao 인터페이스 생성 필요

### MariaDB 사용자 생성 및 권한 주기
```sql
CREATE USER 'green'@'%' IDENTIFIED BY 'green1234';
CREATE DATABASE greendb;
GRANT ALL PRIVILEGES ON greendb.* TO 'green'@'%';
```

### 테이블 생성
```sql
USE greendb;

create table users(
    id int primary KEY auto_increment,
    username varchar(20),
    password varchar(20),
    email varchar(50),
    createdAt TIMESTAMP
);

create table boards(
    id int primary KEY auto_increment,
    title varchar(150),
    content longtext,
    usersId int,
    createdAt TIMESTAMP
);

create table loves(
    id int primary KEY auto_increment,
    usersId int,
    boardsId INT,
    createdAt TIMESTAMP,
    UNIQUE uk_loves (usersId,boardsId)
);
```

### 더미데이터 추가
```sql
insert into users(username, password, email, createdAt) values('ssar', '1234', 'ssar@nate.com', NOW());
insert into users(username, password, email, createdAt) values('cos', '1234', 'cos@nate.com', NOW());
insert into users(username, password, email, createdAt) values('hong', '1234', 'hong@nate.com', NOW());
COMMIT;
```

### 좋아요 + 상세보기 쿼리
```sql
SELECT bo.*,
lo.id lovesId,
if(lo.id IS NULL, 0, 1) isLoved,
(SELECT COUNT(*) FROM loves WHERE boardsId = 3) loveCount
FROM boards bo
LEFT OUTER JOIN (SELECT * FROM loves WHERE usersId = 3) lo
ON bo.id = lo.boardsId
WHERE bo.id = 3

SELECT
b.*,
(SELECT id FROM loves WHERE usersId = 1 AND boardsId = 3) lovesId,
(SELECT 1 FROM loves WHERE usersId = 1 AND boardsId = 3) isLoved,
(SELECT COUNT(*) FROM loves WHERE boardsId = 3) loveCount
FROM boards b
WHERE b.id = 3
```