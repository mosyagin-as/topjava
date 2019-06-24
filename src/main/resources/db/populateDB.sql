DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2019-06-22 10:00:00', 'User Завтрак 22', 500),
       (100000, '2019-06-22 13:00:00', 'User Обед 22', 1000),
       (100000, '2019-06-22 20:00:00', 'User Ужин 22', 500),
       (100001, '2019-06-22 10:00:00', 'Admin Завтрак', 1000),
       (100001, '2019-06-22 13:00:00', 'Admin Обед', 500),
       (100001, '2019-06-22 20:00:00', 'Admin Ужин', 510),
       (100000, '2019-06-23 10:00:00', 'User Завтрак 23', 450),
       (100000, '2019-06-23 13:00:00', 'User Обед 23', 900),
       (100000, '2019-06-23 20:00:00', 'User Ужин 23', 650),
       (100000, '2019-06-24 11:00:00', 'User Завтрак 24', 550),
       (100000, '2019-06-24 14:00:00', 'User Обед 24', 950),
       (100000, '2019-06-24 19:00:00', 'User Ужин 24', 600);
