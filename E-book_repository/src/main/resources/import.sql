

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
INSERT INTO users (first_name, last_name, password, user_type, username, category) VALUES ('Marko', 'Markovic', 'marko', 'subscriber', 'marko', 2);
INSERT INTO users (first_name, last_name, password, user_type, username, category) VALUES ('Vesna', 'Vlasic', 'vesna', 'administrator', 'vesna', NULL);
INSERT INTO users (first_name, last_name, password, user_type, username, category) VALUES ('Aca', 'Artic', 'aca', 'subscriber', 'aca', NULL);

