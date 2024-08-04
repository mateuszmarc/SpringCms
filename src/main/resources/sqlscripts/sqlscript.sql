-- Insert authors

use bookCatalogue;


-- Insert authors
INSERT INTO authors (firstName, lastName) VALUES ('John', 'Doe');
INSERT INTO authors (firstName, lastName) VALUES ('Jane', 'Smith');
INSERT INTO authors (firstName, lastName) VALUES ('Emily', 'Jones');
INSERT INTO authors (firstName, lastName) VALUES ('Michael', 'Brown');
INSERT INTO authors (firstName, lastName) VALUES ('Jessica', 'Davis');

-- Insert categories
INSERT INTO categories (name, description) VALUES ('Technology', 'Articles about technology');
INSERT INTO categories (name, description) VALUES ('Science', 'Articles about science');
INSERT INTO categories (name, description) VALUES ('Health', 'Articles about health');
INSERT INTO categories (name, description) VALUES ('Travel', 'Articles about travel');
INSERT INTO categories (name, description) VALUES ('Education', 'Articles about education');

-- Insert articles
INSERT INTO articles (title, content, created, updated, author_id) VALUES ('Tech Trends 2024', 'Content about tech trends', NOW(), NOW(), 1);
INSERT INTO articles (title, content, created, updated, author_id) VALUES ('The Future of Science', 'Content about future of science', NOW(), NOW(), 2);
INSERT INTO articles (title, content, created, updated, author_id) VALUES ('Health Tips', 'Content about health tips', NOW(), NOW(), 3);
INSERT INTO articles (title, content, created, updated, author_id) VALUES ('Top Travel Destinations', 'Content about travel destinations', NOW(), NOW(), 4);
INSERT INTO articles (title, content, created, updated, author_id) VALUES ('Education in the 21st Century', 'Content about education', NOW(), NOW(), 5);

-- Insert article-category relationships
INSERT INTO articles_categories (article_id, category_id) VALUES (1, 1);
INSERT INTO articles_categories (article_id, category_id) VALUES (2, 2);
INSERT INTO articles_categories (article_id, category_id) VALUES (3, 3);
INSERT INTO articles_categories (article_id, category_id) VALUES (4, 4);
INSERT INTO articles_categories (article_id, category_id) VALUES (5, 5);
INSERT INTO articles_categories (article_id, category_id) VALUES (1, 2);
INSERT INTO articles_categories (article_id, category_id) VALUES (2, 3);
INSERT INTO articles_categories (article_id, category_id) VALUES (3, 4);
INSERT INTO articles_categories (article_id, category_id) VALUES (4, 5);
INSERT INTO articles_categories (article_id, category_id) VALUES (5, 1);