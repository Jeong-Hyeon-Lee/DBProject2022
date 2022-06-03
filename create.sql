CREATE DATABASE DB2022Team03;

use mysql;
#select user, host from user;
#drop user 'DB2022Team03'@localhost;
create user 'DB2022Team03'@localhost identified by 'DB2022Team03';
grant all privileges on *.* to 'DB2022Team03'@localhost;
use DB2022Team03;

create table DB2022_헬스장(
    	헬스장번호 char(6),
    	이름 varchar(25) not null,
    	도시 varchar(6) not null,
    	지역 varchar(6) not null,
    	도로명주소 varchar(20) not null,
    	전체회원수 int default 0,
    	전체트레이너수 int default 0,
    	비밀번호 varchar(20) not null,
    	primary key(헬스장번호)
);

create table DB2022_트레이너(
    	헬스장번호 char(6),
    	강사번호 char(6),
    	이름 varchar(10) not null,
    	담당회원수 int default 0,
    	총근무시간 int default 0,
    	비밀번호 varchar(20) not null,
    	primary key(강사번호),
    	foreign key(헬스장번호) references DB2022_헬스장(헬스장번호)
);

CREATE TABLE DB2022_회원(
	소속헬스장 char(6),
	회원번호 char(6) NOT NULL,
	이름 varchar(10) NOT NULL,
	지역 varchar(10) NOT NULL,
	전체횟수 INT DEFAULT 0,
	남은횟수 INT DEFAULT 0,
	담당트레이너 char(6),
	현재회원권 varchar(5) DEFAULT '없음' CHECK (현재회원권 IN ('1회권', '10회권', '20회권', '없음')),
	비밀번호 varchar(20) NOT NULL,
	PRIMARY KEY (회원번호),
	FOREIGN KEY (담당트레이너) REFERENCES DB2022_트레이너(강사번호) ON DELETE CASCADE,
	FOREIGN KEY (소속헬스장) REFERENCES DB2022_헬스장(헬스장번호) ON DELETE CASCADE
);

CREATE TABLE DB2022_수업(
	회원번호 char(6) NOT NULL,
    	강사번호 char(6),
	수업시간 datetime,
    	수업진행현황 char(6),
	PRIMARY KEY (회원번호, 수업시간),
	FOREIGN KEY (회원번호) REFERENCES DB2022_회원(회원번호) ON DELETE CASCADE,
	FOREIGN KEY (강사번호) REFERENCES DB2022_트레이너(강사번호) ON DELETE CASCADE
);

CREATE TABLE DB2022_가격(
	헬스장번호 char(6),
	1회가격 int NOT NULL,
	10회가격 int NOT NULL,
	20회가격 int NOT NULL,
	기타프로모션설명 varchar(200),
	PRIMARY KEY(헬스장번호),
	FOREIGN KEY (헬스장번호) REFERENCES DB2022_헬스장(헬스장번호) ON DELETE CASCADE
);

/*DB2022_헬스장 insert*/
insert into DB2022_헬스장 values ('G10230','EE health','서울', '서대문구','이화여대길 52',1,1,'ee10235');
insert into DB2022_헬스장 values ('G05910','Good GYM','서울', '은평구','은평로 195',2,1,'gg05910');
insert into DB2022_헬스장 values ('G28100','함께운동','서울', '마포구','마포나루길 467',0,1,'uu28100');

insert into DB2022_헬스장 values ('G29980','2X fitness','서울', '강남구','언주로 101',2,1,'xxfitxx');
insert into DB2022_헬스장 values ('G10340','Able gym','서울', '서초구','사평대로 371',1,2,'able0816');
insert into DB2022_헬스장 values ('G52390','더건강한피티','서울','마포구','마포대로 11길 7-18',0,0,'tgp1111');

insert into DB2022_헬스장 values ('G43240','Bally Total Fitness','서울','서초구','방배로 4길',3,3,'btf1234');
insert into DB2022_헬스장 values ('G45620','The Gym','서울','서대문구','신촌로 33',0,0,'thegym00');
insert into DB2022_헬스장 values ('G19320','데일리 짐','서울','동작구','동작대로 98',0,0,'daily2gym');

insert into DB2022_헬스장 values ('G18340','스포짐','서울','양천구','오목로 344',3,3,'health');
insert into DB2022_헬스장 values ('G12030','스포애니','서울','양천구','목동로 189',0,0,'anybody');
insert into DB2022_헬스장 values ('G34510','피트니스포애버','서울','양천구','오목로 279',0,0,'forever');

insert into DB2022_헬스장 values ('G18860','이대로헬스','서울','서대문구','이화여대길 34',2,1,'ewha1886');
insert into DB2022_헬스장 values ('G95610','May Gym','서울','관악구','관악로 168',1,2,'may05');
insert into DB2022_헬스장 values ('G54360','무브짐','서울','송파구','백제고분로 275',0,0,'move2');

select * from DB2022_헬스장;

/*DB2022_트레이너 insert*/
insert into DB2022_트레이너 values ('G10230','T35220','다니엘',1,0,'dani35');
insert into DB2022_트레이너 values ('G05910','T16210','리암',2,1,'lliam0');
insert into DB2022_트레이너 values ('G28100','T73460','메이슨',0,0,'isonma');

insert into DB2022_트레이너 values ('G10340','T20330','지나',1,0,'ginnygina');
insert into DB2022_트레이너 values ('G10340','T44790','리키',0,0,'ricvic98');
insert into DB2022_트레이너 values ('G29980','T12570','미나',2,1,'mina721');

insert into DB2022_트레이너 values ('G43240','T12340','토니',3,4,'tonyisfree');
insert into DB2022_트레이너 values ('G43240','T43240','찰리',0,0,'chapline00');
insert into DB2022_트레이너 values ('G43240','T88560','벤',0,0,'123cookie');

insert into DB2022_트레이너 values ('G18340','T45770','세라',2,3,'lovehealth');
insert into DB2022_트레이너 values ('G18340','T05100','티파니',1,0,'smile');
insert into DB2022_트레이너 values ('G18340','T51010','셀리나',0,0,'fitnessCelina');

insert into DB2022_트레이너 values ('G18860','T02140','래미',2,3,'Remy2007');
insert into DB2022_트레이너 values ('G95610','T31570','메이',1,1,'youmaygo');
insert into DB2022_트레이너 values ('G95610','T20860','마치',0,0,'March03');

select * from DB2022_트레이너;

/*DB2022_회원 insert*/
INSERT INTO DB2022_회원(소속헬스장, 회원번호, 이름, 지역, 전체횟수, 남은횟수, 담당트레이너, 현재회원권, 비밀번호) VALUES 
	('G10230', 'M82510', '김다혜', '서대문구', 3, 3, 'T35220', '1회권', 'dh8251'),
	('G05910', 'M22380', '최혜인', '은평구', 20, 19,   'T16210',	'20회권', 'hye2238'),
	('G05910', 'M22381', '최혜림', '은평구', 10, 9,   'T16210',	'10회권','choiS2'),
	('G10340', 'M84020',	'이수현','서초구',	10, 9,	  'T20330',	'10회권','lsh8402'),
	('G29980', 'M57130',	'유지원','강남구',	10, 9,	  'T12570',	'10회권','youjw123'),
	('G29980', 'M57131',	'유은수','강남구',	20, 19,   'T12570',	'20회권','ueunsu'),
	('G43240', 'M47170',	'조용찬','서초구',	10, 7,	  'T12340',	'10회권','jyc0321'),
	('G43240', 'M42460',	'백제우','서초구',	10, 9,	  'T12340',	'10회권','jaewoo'),
	('G43240', 'M92880',	'박채린','서초구',	20, 20,	  'T12340',	'20회권','ch93'),
	('G18340', 'M87830',	'김솔희','양천구',30, 9,   'T45770',	'10회권','solhee00'),
	('G18340', 'M82511',	'정민정','양천구',20,8,	  'T45770',	'20회권','2JMJ2'),
	('G18340', 'M72920',	'박제찬','양천구',	1,1,	  'T05100',	'1회권','qlalf11'),
	('G18860', 'M09560','정은상','서대문구',20,18,'T02140',	'20회권','eunsang98'),
	('G18860', 'M95610','김지연','서대문구',10,9, 'T02140',	'10회권','jykim29'),
	('G95610', 'M61590',	'유다원','관악구',5, 4,	  'T31570',	'1회권','allone6');

select * from DB2022_회원;

/*DB2022_수업 insert*/
INSERT INTO DB2022_수업
VALUES
('M47170',	'T12340',	'2022-05-02 9:00:00', '완료'),
('M47170',	'T12340',	'2022-05-07 19:00:00',	'완료'),
('M47170',	'T12340',	'2022-05-12 9:00:00',	'완료'),
('M42460',	'T12340',	'2022-05-15 14:00:00',	'완료'),
('M22380',	'T16210',	'2022-04-30 15:00:00',	'완료'),
('M82510',	'T35220',	'2022-06-20 15:00:00',	'예약확인중'),
('M22381',	'T16210',	'2022-06-20 16:00:00',	'예약완료'),
('M84020',	'T20330',	'2022-05-15 20:00:00', '불참'),
('M84020',	'T20330',	'2022-06-18 20:00:00', '예약확인중'),
('M57130',	'T12570',	'2022-06-18 9:00:00',	'예약완료'),
('M57131',	'T12570',	'2022-05-13 10:00:00','완료'),
('M87830',	'T45770',	'2022-05-01 10:00:00',	'완료'),
('M82511',	'T45770',	'2022-05-03 17:00:00',	'완료'),
('M82511',	'T45770',	'2022-05-06 19:00:00',	'완료'),
('M09560',	'T02140',	'2022-05-08 14:00:00',	'완료'),
('M09560',	'T02140',	'2022-05-15 14:00:00',	'완료'),
('M95610',	'T02140',	'2022-05-09 20:00:00',	'완료'),
('M61590',	'T31570',	'2022-05-12 9:00:00',	'완료'),
('M09560',	'T02140',	'2022-06-22 17:00:00',	'예약확인중');

select * from DB2022_수업;

/*DB2022_가격 insert*/
INSERT INTO DB2022_가격 VALUES
('G10230', 50000, 480000, 930000, '사물함무료'),
('G05910', 55000, 500000, 950000, '없음'),
('G28100', 45000, 450000, 900000, '없음'),
('G29980', 60000, 580000, 115000, '없음'),
('G10340', 65000, 600000, 115000, '지인 소개 시 1회 추가'),
('G52390', 50000, 470000, 920000, '없음'),
('G43240', 60000, 55000, 45000, '20회 등록시 헬스장 무료 이용'),
('G45620', 70000, 60000, 50000, '20회 등록시 첫 10회 30% 할인'),
('G19320', 35000, 35000, 35000, '없음'),
('G18340', 65000, 600000, 1100000, '지인소개시 둘다 2회 추가'),
('G12030', 70000, 650000, 1100000, '없음'),
('G34510', 50000, 500000, 1000000, '없음'),
('G18860', 60000, 580000, 1100000, '상담 시 2회 무료 체험권'),
('G95610', 55000, 520000, 990000, '없음'),
('G54360', 50000, 460000, 1000000, '없음');

select * from DB2022_가격;

create view searchTrainer as
	( select G.이름 as 헬스장이름 ,T.이름 as 트레이너이름 ,G.지역, T.담당회원수, G.헬스장번호 
	from db2022_트레이너 as T,db2022_헬스장 as G 
	where T.헬스장번호 = G.헬스장번호) ;

create view searchGYM as 
	( select 이름,지역,1회가격,10회가격,20회가격,기타프로모션설명 
	from db2022_헬스장 natural join db2022_가격 );
