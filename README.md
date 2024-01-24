
# Restful API + @RestController + @PathVariable + @RequestBody
https://loda.me/articles/sb14-restful-api-restcontroller-pathvariable-requestbody


OS
Maven: 2.7.3
JDK 17
Database : PostgreSQL

Lưu ý:
	⁃	DB cần tạo jwt_security.

	CREATE DATABASE jwt_security;

	⁃	Tạo bảng users.

	CREATE TABLE users (
    		id INT PRIMARY KEY AUTO_INCREMENT,
		firstname VARCHAR(255),
    		lastname VARCHAR(255),
    		email VARCHAR(255) UNIQUE,
	   	password VARCHAR(255),
		role VARCHAR(255),
    		mfa_enabled BOOLEAN,
    		secret VARCHAR(255)
	);

	⁃	Cung cấp quyền cho người dùng trong jwt_security

	GRANT ALL PRIVILEGES ON DATABASE jwt_security TO your_username;
