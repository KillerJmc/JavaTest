create table emp
(
    id       int auto_increment
        primary key,
    name     varchar(16) null,
    age      int         null,
    birthday date        null,
    bonus    double      null,
    salary   double      null,
    deptId   int         null
);

INSERT INTO sorm.emp (id, name, age, birthday, bonus, salary, deptId) VALUES (1, 'LiDong', 29, '2015-02-26', 100, 21, 1);
INSERT INTO sorm.emp (id, name, age, birthday, bonus, salary, deptId) VALUES (2, 'Tom', 28, '2019-12-01', 200, 30, 1);
INSERT INTO sorm.emp (id, name, age, birthday, bonus, salary, deptId) VALUES (3, '斐新', 18, '2015-04-14', 300, 30000, 2);
INSERT INTO sorm.emp (id, name, age, birthday, bonus, salary, deptId) VALUES (4, 'Lily', 24, '2019-12-01', 400, 100000, 2);
INSERT INTO sorm.emp (id, name, age, birthday, bonus, salary, deptId) VALUES (5, 'LiYang', 35, '2019-12-01', 500, 10000000, 1);