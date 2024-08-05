create database ntd;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    status VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(255),
    deleted BIT(1)
);

CREATE TABLE operation (
    id bigint PRIMARY KEY, 
    type varchar(255), 
    cost double
)

CREATE TABLE operation_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE,
    date DATETIME(6),
    deleted BOOLEAN,
    operation_id BIGINT,
    operation_response VARCHAR(255),
    user_balance DOUBLE,
    user_id BIGINT,
    CONSTRAINT fk_operation
        FOREIGN KEY (operation_id) REFERENCES operation(id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    operation_record_id BIGINT,
    amount DECIMAL(10, 2),
    user_balance DECIMAL(10, 2),
    operation_response VARCHAR(255),
    date TIMESTAMP,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_operation_record
        FOREIGN KEY (operation_record_id) REFERENCES operation_record(id)
);

CREATE TABLE roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    PRIMARY KEY ( id )
) ENGINE = InnoDB CHARACTER SET utf8;
 
CREATE TABLE users_roles (
    user_id BIGINT NOT NULL ,
    role_id BIGINT NOT NULL
) ENGINE = InnoDB CHARACTER SET utf8;
 
ALTER TABLE roles
ADD CONSTRAINT UK_roles_name
UNIQUE (name);
 
ALTER TABLE users_roles
ADD CONSTRAINT UK_user_id_roles_id
UNIQUE (user_id, role_id);
 
ALTER TABLE users_roles
ADD CONSTRAINT FK_users_roles_roles_id
FOREIGN KEY (role_id) REFERENCES roles(id);
 
ALTER TABLE users_roles
ADD CONSTRAINT FK_users_roles_user_id
FOREIGN KEY (user_id) REFERENCES users(id);


INSERT INTO operation (id, type, cost) VALUES
(1, 'addition', 1),
(2, 'subtraction', 1),
(3, 'multiplication', 1.5),
(4, 'division', 1.5),
(5, 'square_root', 2),
(6, 'random_string', 2.5);

INSERT INTO users (id, username, password, status, email, role, deleted) VALUES
(1, 'user1@example.com', 'password1', 'active', 'user1@example.com', 'ROLE_USER', 0),
(2, 'user2@example.com', 'password2', 'active', 'user2@example.com', 'ROLE_USER', 0),
(3, 'user3@example.com', 'password3', 'inactive', 'user3@example.com', 'ROLE_USER', 0),
(4, 'user4@example.com', 'password4', 'active', 'user4@example.com', 'ROLE_USER', 0),
(5, 'user5@example.com', 'password5', 'inactive', 'user5@example.com', 'ROLE_USER', 0),
(6, 'admin', '$2a$10$hGqoCGDECgR5M4fOH8pXD.6iuErce1ys/TpjQ9sWgC0zQA0epQjE2', 'active', 'test@test.com', 'ROLE_ADMIN', 0),
(7, 'roman', '$2a$10$7PsCE52KJJd9O3Tat.w6ceMMXz5S3OWgV8wjO6E44MqZ8em58iJK.', 'active', 'roman@test.com', 'ROLE_ADMIN', 0);