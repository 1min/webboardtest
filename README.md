# webboardtest

게시판데이터베이스쿼리문

create table mvc_board(
bId NUMBER(4) PRIMARY KEY,// id값
bName VARCHAR2(20),// 작성자
bTitle VARCHAR2(100),// 제목
bContent VARCHAR2(300),// 내용
bDate DATE DEFAULT SYSDATE,// 작성시간
bHit NUMBER(4) DEFAULT 0,//조회수
bGroup NUMBER(4),                  //3줄은 답변글용 들여쓰기
bStep NUMBER(4),
bIndent Number(4)
);

commit;

시퀀스: 자동 값 증가
create sequence mvc_board_seq;//mvc_board_seq란 시퀀스 생성 시퀀스도 커밋해줘야함
commit;
