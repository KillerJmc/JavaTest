create table dept
(
    id      int auto_increment
        primary key,
    name    varchar(255) null,
    address varchar(255) null
);

INSERT INTO sorm.dept (id, name, address) VALUES (1, '财务部', '金融街CBD');
INSERT INTO sorm.dept (id, name, address) VALUES (2, '技术部', '西三旗');