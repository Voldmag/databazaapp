CREATE DATABASE IF NOT EXISTS todo_db;

USE todo_db;
CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    status ENUM('NEW', 'IN_PROGRESS', 'DONE') DEFAULT 'NEW',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE USER IF NOT EXISTS 'your_username'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON todo_db.* TO 'your_username'@'localhost';
FLUSH PRIVILEGES;



SELECT * FROM tasks;
SHOW GRANTS FOR 'your_username'@'localhost';
