

INSERT INTO category (name) VALUES ('Drama');
INSERT INTO category (name) VALUES ('Romance');
INSERT INTO category (name) VALUES ('Fiction');
INSERT INTO category (name) VALUES ('Horror-Gothic');
INSERT INTO category (name) VALUES ('Humor');
INSERT INTO category (name) VALUES ('Mystery');
INSERT INTO category (name) VALUES ('Poetry');
INSERT INTO category (name) VALUES ('Sci-fi Fantasy');
INSERT INTO category (name) VALUES ('Biography');
INSERT INTO category (name) VALUES ('History');
INSERT INTO category (name) VALUES ('Erotic');
INSERT INTO category (name) VALUES ('Science');
INSERT INTO category (name) VALUES ('Philosophy');
INSERT INTO category (name) VALUES ('Novel');


INSERT INTO language (name) VALUES ('English');
INSERT INTO language (name) VALUES ('Serbian');
INSERT INTO language (name) VALUES ('Hungarian');
INSERT INTO language (name) VALUES ('Ruthenian');

INSERT INTO users (first_name, last_name, password, user_type, username, category) VALUES ('Petar', 'Petrovic', 'petar', 'administrator', 'petar', 1);
INSERT INTO users (first_name, last_name, password, user_type, username, category) VALUES ('Ana', 'Antic', 'ana', 'administrator', 'ana', 1);
INSERT INTO users (first_name, last_name, password, user_type, username, category) VALUES ('Marko', 'Markovic', 'marko', 'administrator', 'marko', 2);
INSERT INTO users (first_name, last_name, password, user_type, username, category) VALUES ('Vesna', 'Vlasic', 'vesna', 'administrator', 'vesna', 3);

INSERT INTO ebook (author, file_name, keywords, mime, publication_year, title, category, language, users) VALUES ('Friedrich Nietzsche', 'Nietzsche', 'Friedrich Nietzsche German philosopher good evil', NULL, 1886, 'Beyond Good and Evil', 1, 1, 1);
INSERT INTO ebook (author, file_name, keywords, mime, publication_year, title, category, language, users) VALUES ('Friedrich Nietzsche', 'Nietzsche2', 'Friedrich Nietzsche German philosopher Zarathustra', NULL, 1883, 'Thus Spoke Zarathustra', 13, 2, 1);
INSERT INTO ebook (author, file_name, keywords, mime, publication_year, title, category, language, users) VALUES ('Plato', 'Plato', 'Plato the republic', NULL, 1938, 'The Republic', 1, 1, 1);
INSERT INTO ebook (author, file_name, keywords, mime, publication_year, title, category, language, users) VALUES ('Charles Dickens', 'Charles', 'English writer and social critic Oliver Twist', NULL, 1837, 'The Adventures of Oliver Twist', 14, 1, 2);
INSERT INTO ebook (author, file_name, keywords, mime, publication_year, title, category, language, users) VALUES ('Charles Dickens', 'Charles2', 'English writer and social critic David Copperfield', NULL, 1849, 'David Copperfield', 14, 1, 1);
