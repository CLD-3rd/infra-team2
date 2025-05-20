

-- user 테이블
INSERT INTO user (created_at, updated_at, password, username, user_role_type)
VALUES ('2025-05-01 10:00:00', '2025-05-01 10:00:00', 'pass123', 'user1', 'USER'),
       ('2025-05-02 12:00:00', '2025-05-02 12:00:00', 'pass456', 'user2', 'ADMIN');

-- question 테이블
INSERT INTO question (correct_answer, created_at, updated_at, user_id, question_text)
VALUES (2, '2025-05-03 09:00:00', '2025-05-03 09:00:00', 1, '2 + 2 = ?'),
       (4, '2025-05-04 11:00:00', '2025-05-04 11:00:00', 2, 'What is 2 * 2?');

-- choice 테이블
INSERT INTO choice (choice_number, is_correct, created_at, updated_at, question_id, choice_text)
VALUES (1, 0, '2025-05-03 09:05:00', '2025-05-03 09:05:00', 1, '1'),
       (2, 1, '2025-05-03 09:05:00', '2025-05-03 09:05:00', 1, '4'),
       (3, 0, '2025-05-03 09:05:00', '2025-05-03 09:05:00', 1, '3'),
       (1, 0, '2025-05-04 11:05:00', '2025-05-04 11:05:00', 2, '2'),
       (2, 1, '2025-05-04 11:05:00', '2025-05-04 11:05:00', 2, '4'),
       (3, 0, '2025-05-04 11:05:00', '2025-05-04 11:05:00', 2, '6');

-- solve 테이블
INSERT INTO solve (created_at, finished_at, updated_at, user_id, status)
VALUES ('2025-05-05 13:00:00', '2025-05-05 14:00:00', '2025-05-05 14:00:00', 1, 'FINISHED'),
       ('2025-05-05 14:30:00', '2025-05-05 15:00:00', '2025-05-05 15:00:00', 1, 'IN_PROGRESS');

-- answer 테이블
INSERT INTO answer (is_correct, selected_answer, created_at, updated_at, question_id, user_id, solve_id)
VALUES (1, 2, '2025-05-05 14:00:00', '2025-05-05 14:00:00', 1, 1, 1),
       (0, 3, '2025-05-05 15:00:00', '2025-05-05 15:00:00', 2, 1, 2);


ALTER TABLE user AUTO_INCREMENT = 1;
ALTER TABLE question AUTO_INCREMENT = 1;
ALTER TABLE choice AUTO_INCREMENT = 1;
ALTER TABLE solve AUTO_INCREMENT = 1;
ALTER TABLE answer AUTO_INCREMENT = 1;