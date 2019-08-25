#create databases
CREATE DATABASE IF NOT EXISTS inteca_db_credit;
CREATE DATABASE IF NOT EXISTS inteca_db_customer;
CREATE DATABASE IF NOT EXISTS inteca_db_product;

#create users
CREATE USER IF NOT EXISTS 'inteca_user_credit'@'%' IDENTIFIED BY 'pw';
ALTER USER 'inteca_user_credit'@'%' IDENTIFIED WITH mysql_native_password BY 'pw';
GRANT ALL ON inteca_db_credit.* TO 'inteca_user_credit'@'%';

CREATE USER IF NOT EXISTS 'inteca_user_customer'@'%' IDENTIFIED BY 'pw';
ALTER USER 'inteca_user_customer'@'%' IDENTIFIED WITH mysql_native_password BY 'pw';
GRANT ALL ON inteca_db_customer.* TO 'inteca_user_customer'@'%';

CREATE USER IF NOT EXISTS 'inteca_user_product'@'%' IDENTIFIED BY 'pw';
ALTER USER 'inteca_user_product'@'%' IDENTIFIED WITH mysql_native_password BY 'pw';
GRANT ALL ON inteca_db_product.* TO 'inteca_user_product'@'%';

