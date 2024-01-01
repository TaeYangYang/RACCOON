--grant connect, resource to RACCOON;
--
--alter user RACCOON default tablespace USERS quota unlimited on USERS;

-- MariaDB
CREATE DATABASE RACCOON;
CREATE USER 'RACCOON_USER'@'%' IDENTIFIED BY 'RACCOON123';
grant all on *.* to 'RACCOON_USER'@'localhost' identified by 'RACCOON123';
