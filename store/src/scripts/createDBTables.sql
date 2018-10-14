SHOW TABLES;
DROP TABLE IF EXISTS products, customers, orders, carts;
SHOW TABLES;
CREATE TABLE customers(id SERIAL, fname VARCHAR(255), lname VARCHAR(255), 
		username VARCHAR(255),email VARCHAR(255), UNIQUE(id, username));

CREATE TABLE products(itemId SERIAL, name VARCHAR(255), msrp DECIMAL(6,2), salePrice DECIMAL(8,2), 
	upc INT, shortDescription VARCHAR(255), brandName VARCHAR(255), 
	size VARCHAR(255), color VARCHAR(255), gender VARCHAR(255), UNIQUE(itemId));

CREATE TABLE orders(orderId SERIAL, itemId VARCHAR(255), cartId DECIMAL(6,2), UNIQUE(orderId));

CREATE TABLE carts(cartId SERIAL, user VARCHAR(255), UNIQUE(cartId));

SELECT "You will need to decide how to persist cart and order information. Will you amend an existing table or create new one(s)?" AS "";
SHOW TABLES;