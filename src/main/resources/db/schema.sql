/*�û���*/
INSERT INTO sys_user(id,login_name,password,salt,user_name,created_at,status) VALUES (1,'admin','tcGdcIcpfTV/3rcnCjSX7Q==','f6383540c625a6cb9e1906f727dc52ed','��������Ա',now(),1);

/*��ɫ��*/
INSERT INTO sys_role(id,name,code,status,created_at) VALUES (1,'����Ա','admin',1,now());

/*�û�-��ɫ��*/
INSERT INTO sys_user_role(user_id,role_id,created_at) VALUES (1,1,now());