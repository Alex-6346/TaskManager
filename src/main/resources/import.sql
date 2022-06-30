-- CREATE TABLE users(id LONG PRIMARY KEY AUTO_INCREMENT,login VARCHAR(30) NOT NULL,full_name VARCHAR(40) NOT NULL,password VARCHAR(40) NOT NULL, UNIQUE(login));

INSERT INTO users(full_name, email, password) VALUES('David Seaman', 'firstuser@gmail.com', 'first123');
INSERT INTO users(full_name, email, password) VALUES('Peter Schmeichel', 'seconduser@gmail.com', 'second123');
INSERT INTO users(full_name, email, password) VALUES('Edwin Van der Sar', 'thirduser@gmail.com', 'third123');
-- INSERT INTO categories(category_name, description) values('Undefined', 'a');

-- INSERT INTO categories_to_user(user_id, category_id) values(1, 1);
