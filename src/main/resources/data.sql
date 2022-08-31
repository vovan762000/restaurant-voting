
INSERT INTO USERS (NAME, EMAIL, PASSWORD,ROLE)
VALUES ('User', 'user@github.com', '{noop}user','USER'),
       ('Admin', 'admin@github.com', '{noop}admin','ADMIN');


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

INSERT INTO VOTE (DATE, RESTAURANT_ID, USER_ID)
VALUES ('2022-01-01', 1, 1),
       ('2022-02-01', 2, 1),
       ('2022-01-01', 1, 2),
       ('2022-06-01', 1, 2),
       ('2022-08-01', 1, 1);