INSERT INTO categories (name)
VALUES
('java'),('javascript'), ('c++'), ('kotlin'), ('python')
ON CONFLICT DO NOTHING;

INSERT INTO tags (name)
VALUES
('QT'),
('Spring Boot'),
('RESTful APIs'),
('Hibernate'),
('Java 11'),
('Spring Security'),
('JPA'),
('Microservices'),
('JUnit 5'),
('Docker')
ON CONFLICT DO NOTHING;

INSERT INTO authors (name)
VALUES
('Jimmy'), ('Onur'), ('Dilek'), ('Emre'), ('Serdar')
ON  CONFLICT DO NOTHING;

--INSERT INTO articles (title, brief, content, created_at, stars, author_id, category_id) VALUES
--('Introduction to Spring Boot','breif of post', 'Spring Boot is a powerful framework for building Java applications.', NOW(), 4, 1, 1),
--('RESTful API Design Best Practices','breif of post', 'Learn about the best practices for designing RESTful APIs.', NOW(), 5, 2, 2),
--('Getting Started with Hibernate','breif of post', 'Hibernate is a popular Java ORM framework for database interaction.', NOW(), 3, 3, 1),
--('Java 11 Features and Enhancements','breif of post', 'Explore the new features and enhancements introduced in Java 11.', NOW(), 4, 1, 3),
--('Spring Security Tutorial','breif of post', 'Learn how to secure your Spring Boot applications with Spring Security.', NOW(), 5, 2, 2),
--('Working with JPA in Spring Boot','breif of post', 'Learn how to use Java Persistence API (JPA) with Spring Boot.', NOW(), 4, 3, 1),
--('Microservices Architecture Design Patterns','breif of post', 'Explore different design patterns for building microservices architecture.', NOW(), 5, 1, 4),
--('Unit Testing with JUnit 5','breif of post', 'Learn how to write unit tests for your Java applications using JUnit 5.', NOW(), 4, 2, 3),
--('Building RESTful APIs with Spring Boot','breif of post', 'A comprehensive guide to building RESTful APIs using Spring Boot.', NOW(), 5, 3, 2),
--('Building RESTful APIs with Spring Boot2','breif of post', 'A comprehensive guide to building RESTful APIs using Spring Boot.', NOW(), 5, 3, 2)
--ON CONFLICT DO NOTHING;


--INSERT INTO article_tags (article_id, tag_id) VALUES
--(1, 1), (1,3),
--(2, 2), (2,5),
--(3, 3), (3,8),
--(4, 4), (4,7),
--(5, 5), (5,1),
--(6, 6),
--(7, 7), (7,1), (7,2),
--(8, 8),
--(9, 2),
--(10, 9)
--ON CONFLICT DO NOTHING;
