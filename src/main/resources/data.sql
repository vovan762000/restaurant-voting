INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@github.com', 'User_First', 'User_Last', 'password'),
       ('vovan762000@github.com', 'Admin_First', 'Admin_Last', 'admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('ROLE_USER', 1),
       ('ROLE_ADMIN', 2),
       ('ROLE_USER', 2);

INSERT INTO RESTAURANT (RESTAURANT_NAME)
VALUES ('First restaurant'),
       ('Second restaurant');

INSERT INTO DISH (DISH_NAME, PRICE, RESTAURANT_ID)
VALUES ('first restaurant dish 1', 10.99, 1),
       ('first restaurant dish 2', 5.00, 1),
       ('first restaurant dish 3', 88.00, 1),
       ('first restaurant dish 4', 145.00, 1),
       ('second restaurant dish 1', 5.00, 2),
       ('second restaurant dish 2', 8.45, 2),
       ('second restaurant dish 3', 5.99, 2);

INSERT INTO VOTE (DATE_TIME, RESTAURANT_ID, USER_ID)
VALUES ('2022-01-01 11:00', 1, 1),
       ('2022-02-01 11:00', 2, 1),
       ('2022-01-01 14:00', 1, 2),
       ('2022-06-01 17:00', 1, 2),
       ('2022-08-01 11:00', 1, 1);