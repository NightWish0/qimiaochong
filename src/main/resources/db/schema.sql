/*�û���*/
INSERT INTO sys_user(id,login_name,password,salt,user_name,created_at,status) VALUES (1,'admin','b7e91c0b3b31885d6b7729b0ad204f97','ea7728320d36488402229bfab49c2f6f','��������Ա',now(),1);

/*��ɫ��*/
INSERT INTO sys_role(id,name,code,status,created_at) VALUES (1,'����Ա','admin',1,now());

/*�û�-��ɫ��*/
INSERT INTO sys_user_role(user_id,role_id,created_at) VALUES (1,1,now());