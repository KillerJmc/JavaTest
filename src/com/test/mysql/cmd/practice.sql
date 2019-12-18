/*
create table dept (
	id int primary key,
	dname varchar(50),
	loc varchar(50)
);

insert into dept values
(10, '教研部', '北京'),
(20, '学工部', '上海'),
(30, '销售部', '广州'),
(40, '财务部', '深圳');

create table job (
	id int primary key,
	jname varchar(20),
	description varchar(50)
);

insert into job values
(1, '董事长', '管理整个公司、接单'),
(2, '经理', '管理部门员工'),
(3, '销售员', '向客人推销产品'),
(4, '文员', '使用办公软件');

create table emp (
	id int primary key,
	ename varchar(50),
	job_id int,
	mgr int,
	joindate date,
	salary decimal(7, 2),
	bonus	decimal(7, 2),
	dept_id int,
	foreign key(job_id) references job(id),
	foreign key(dept_id) references dept(id)
);

insert into emp values
(1001, '孙悟空', 4, 1004, '2000-12-17', '8000.00',null, 20),
(1002, '卢俊义', 3, 1006, '2001-02-20', '16000.00', '3000.00' ,30),
(1003, '林冲', 3, 1006, '2001-02-22', '12500.00','5000.00' ,30),
(1004, '唐僧', 2, 1009, '2001-04-02', '29750.00',null ,20),
(1005, '李逵', 4, 1006, '2001-09-28', '12500.00','14000.00' ,30),
(1006, '宋江', 2, 1009, '2001-05-01', '28500.00',null ,30),
(1007, '刘备', 2, 1009, '2001-09-01', '24500.00',null ,10),
(1008, '猪八戒', 4, 1004, '2007-04-19', '30000.00',null ,20),
(1009, '罗贯中', 1, null, '2001-11-17', '50000.00',null ,10),
(1010, '吴用', 3, 1006, '2001-09-08', '15000.00','0.00' ,30),
(1011, '沙僧', 4, 1004, '2007-05-03', '11000.00',null , 20),
(1012, '李逵', 4, 1006, '2001-12-03', '9500.00',null ,30),
(1013, '小白龙', 4, 1004, '2001-12-23', '30000.00',null ,20),
(1014, '关羽', 4, 1007, '2002-01-23', '13000.00', null, 10);

create table salarygrade (
	grade int primary key,
	losalary int,
	hisalary int
);

insert into salarygrade values
(1, 7000, 12000),
(2, 12010, 14000),
(3, 14010, 20000),
(4, 20010, 30000),
(5, 30010, 99990);
*/

select
        e.id,
        e.ename,
        e.salary,
        j.jname,
        j.description
from
        emp e,
        job j
where
        e.job_id = j.id;

select
        e.id,
        e.ename,
        e.salary,
        j.jname,
        j.description,
        d.dname,
        d.loc
from
        emp e,
        job j,
        dept d
where
        e.job_id = j.id and
        e.dept_id = d.id;

select
        e.ename,
        e.salary,
        s.grade
from
        emp e,
        salarygrade s
where
        e.salary between s.losalary and s.hisalary;

select
        e.ename,
        e.salary,
        s.grade,
        j.jname,
        j.description,
        d.dname,
        d.loc
from
        emp e,
        salarygrade s,
        job j,
        dept d
where
        e.salary between s.losalary and s.hisalary
        and e.job_id = j.id
        and e.dept_id = d.id;

select
        d.id,
        d.dname,
        d.loc,
        e.amount
from
        dept d,
        (select
            dept_id,
            count(*) amount
        from
            emp
        group by
            dept_id
        ) e
where
        d.id = e.dept_id;

select
        e1.ename,
        e2.ename mgr
from
        emp e1
left join
        emp e2
on
        e2.id = e1.mgr;


