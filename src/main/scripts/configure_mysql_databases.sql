# connect to mysql and run as root user
#Create Databases
CREATE DATABASE spring_recipes_dev;
CREATE DATABASE spring_recipes_prod;

#Create database service accounts
CREATE USER 'spring_recipes_dev_user'@'localhost' IDENTIFIED BY 'create-your-own-password';
CREATE USER 'spring_recipes_prod_user'@'localhost' IDENTIFIED BY 'create-your-own-password';
# wildcard for host allows these credentials to work when running MySQL in Docker since connection won't be seen as localhost
CREATE USER 'spring_recipes_dev_user'@'%' IDENTIFIED BY 'create-your-own-password';
CREATE USER 'spring_recipes_prod_user'@'%' IDENTIFIED BY 'create-your-own-password';

#Database grants
GRANT SELECT ON spring_recipes_dev.*  to 'spring_recipes_dev_user'@'localhost';
GRANT INSERT ON spring_recipes_dev.*  to 'spring_recipes_dev_user'@'localhost';
GRANT DELETE ON spring_recipes_dev.*  to 'spring_recipes_dev_user'@'localhost';
GRANT UPDATE ON spring_recipes_dev.*  to 'spring_recipes_dev_user'@'localhost';
GRANT SELECT ON spring_recipes_prod.* to 'spring_recipes_prod_user'@'localhost';
GRANT INSERT ON spring_recipes_prod.* to 'spring_recipes_prod_user'@'localhost';
GRANT DELETE ON spring_recipes_prod.* to 'spring_recipes_prod_user'@'localhost';
GRANT UPDATE ON spring_recipes_prod.* to 'spring_recipes_prod_user'@'localhost';
# same for wildcard host
GRANT SELECT ON spring_recipes_dev.*  to 'spring_recipes_dev_user'@'%';
GRANT INSERT ON spring_recipes_dev.*  to 'spring_recipes_dev_user'@'%';
GRANT DELETE ON spring_recipes_dev.*  to 'spring_recipes_dev_user'@'%';
GRANT UPDATE ON spring_recipes_dev.*  to 'spring_recipes_dev_user'@'%';
GRANT SELECT ON spring_recipes_prod.* to 'spring_recipes_prod_user'@'%';
GRANT INSERT ON spring_recipes_prod.* to 'spring_recipes_prod_user'@'%';
GRANT DELETE ON spring_recipes_prod.* to 'spring_recipes_prod_user'@'%';
GRANT UPDATE ON spring_recipes_prod.* to 'spring_recipes_prod_user'@'%';