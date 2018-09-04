/*用户表*/
INSERT INTO sys_user(id,login_name,password,salt,user_name,created_at,status) VALUES (1,'admin','b7e91c0b3b31885d6b7729b0ad204f97','ea7728320d36488402229bfab49c2f6f','超级管理员',now(),1);

/*角色表*/
INSERT INTO sys_role(id,name,code,status,created_at) VALUES (1,'管理员','admin',1,now());

/*用户-角色表*/
INSERT INTO sys_user_role(user_id,role_id,created_at) VALUES (1,1,now());