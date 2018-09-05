/*用户表*/
INSERT INTO sys_user(id,login_name,password,salt,user_name,created_at,status) VALUES (1,'admin','tcGdcIcpfTV/3rcnCjSX7Q==','f6383540c625a6cb9e1906f727dc52ed','超级管理员',now(),1);

/*角色表*/
INSERT INTO sys_role(id,name,code,status,created_at) VALUES (1,'管理员','admin',1,now());

/*用户-角色表*/
INSERT INTO sys_user_role(user_id,role_id,created_at) VALUES (1,1,now());