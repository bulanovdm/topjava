DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

ALTER SEQUENCE meals_id_seq RESTART WITH 1;
INSERT INTO meals (user_id, calories, description, date_time)
VALUES (100000, 500, 'userMeal1', '2020-10-19 14:00'),
       (100000, 1000, 'userMeal2', '2020-10-20 15:00'),
       (100000, 600, 'userMeal3', '2020-10-21 16:00'),

       (100001, 500, 'adminMeal1', '2020-10-19 18:00'),
       (100001, 1000, 'adminMeal2', '2020-10-20 19:00'),
       (100001, 500, 'adminMeal3', '2020-10-21 20:00');
