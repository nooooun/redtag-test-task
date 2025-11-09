-- liquibase formatted sql

-- changeset Yura:insert-authors
INSERT INTO authors (full_name, birthdate)
VALUES
    ('J.K. Rowling', '1965-07-31'),
    ('Frank Herbert', '1920-10-08'),
    ('Agatha Christie', '1890-09-15'),
    ('Stephen King', '1947-09-21'),
    ('Walter Isaacson', '1952-05-20'),
    ('George R.R. Martin', '1948-09-20'),
    ('Isaac Asimov', '1920-01-02'),
    ('Ken Follett', '1949-06-05');
-- rollback DELETE FROM authors WHERE id BETWEEN 1 AND 8;


-- changeset Yura:insert-books
INSERT INTO books (title, genre, publish_date, description, author_id)
VALUES
    ('Harry Potter and the Philosopher''s Stone', 'FANTASY', '1997-06-26', 'A boy wizard begins his journey...', 1),
    ('Harry Potter and the Chamber of Secrets', 'FANTASY', '1998-07-02', 'Mysterious whispers haunt the halls of Hogwarts...', 1),
    ('Harry Potter and the Prisoner of Azkaban', 'FANTASY', '1999-07-08', 'An escaped prisoner poses a new threat...', 1),
    ('Harry Potter and the Goblet of Fire', 'FANTASY', '2000-07-08', 'The Triwizard Tournament comes to Hogwarts...', 1),
    ('Harry Potter and the Order of the Phoenix', 'FANTASY', '2003-06-21', 'Harry struggles with a tyrannical new professor...', 1),
    ('Harry Potter and the Half-Blood Prince', 'FANTASY', '2005-07-16', 'Dumbledore''s past is revealed...', 1),

    ('Dune', 'SCIENCE_FICTION', '1965-08-01', 'A noble family battles for control of a desert planet...', 2),
    ('Dune Messiah', 'SCIENCE_FICTION', '1969-10-15', 'The aftermath of Paul Atreides'' rise to power...', 2),
    ('Children of Dune', 'SCIENCE_FICTION', '1976-04-21', 'The story of Paul''s twin children...', 2),
    ('God Emperor of Dune', 'SCIENCE_FICTION', '1981-05-28', 'Leto II rules the universe as a sandworm hybrid...', 2),
    ('Heretics of Dune', 'SCIENCE_FICTION', '1984-04-30', 'Centuries after Leto II, the universe is in turmoil...', 2),

    ('Murder on the Orient Express', 'MYSTERY', '1934-01-01', 'Hercule Poirot investigates a murder on a snowbound train...', 3),
    ('And Then There Were None', 'MYSTERY', '1939-11-06', 'Ten strangers are lured to an isolated island...', 3),
    ('The Murder of Roger Ackroyd', 'MYSTERY', '1926-06-01', 'A classic whodunit with a revolutionary twist...', 3),
    ('Death on the Nile', 'MYSTERY', '1937-11-01', 'Poirot investigates a murder on a Nile cruise...', 3),
    ('The A.B.C. Murders', 'MYSTERY', '1936-01-06', 'A serial killer murders victims in alphabetical order...', 3),
    ('The Mysterious Affair at Styles', 'MYSTERY', '1920-10-01', 'Poirot''s first case, involving a complex poisoning...', 3),

    ('It', 'HORROR', '1986-09-15', 'A group of childhood friends reunite to confront a shape-shifting evil...', 4),
    ('The Shining', 'HORROR', '1977-01-28', 'A family isolated in a haunted hotel over winter...', 4),
    ('Carrie', 'HORROR', '1974-04-05', 'A telekinetic teenager takes revenge on her bullies...', 4),
    ('Pet Sematary', 'HORROR', '1983-11-14', 'A burial ground that brings back the dead, but not as they were...', 4),
    ('Misery', 'HORROR', '1987-06-08', 'A novelist is "rescued" from a car crash by his number one fan...', 4),

    ('Steve Jobs', 'BIOGRAPHY', '2011-10-24', 'The authorized biography of the co-founder of Apple Inc...', 5),
    ('Leonardo da Vinci', 'BIOGRAPHY', '2017-10-17', 'A biography of the ultimate Renaissance man...', 5),
    ('Einstein: His Life and Universe', 'BIOGRAPHY', '2007-04-10', 'Exploring the life and mind of Albert Einstein...', 5),
    ('The Innovators', 'BIOGRAPHY', '2014-10-07', 'How a group of hackers, geniuses, and geeks created the digital revolution...', 5),
    ('Benjamin Franklin: An American Life', 'BIOGRAPHY', '2003-07-01', 'The life of one of America''s founding fathers...', 5),

    ('A Game of Thrones', 'FANTASY', '1996-08-01', 'Noble houses fight for control of the Iron Throne...', 6),
    ('A Clash of Kings', 'FANTASY', '1998-11-16', 'The War of the Five Kings grips Westeros...', 6),
    ('A Storm of Swords', 'FANTASY', '2000-08-08', 'The war continues with new alliances and betrayals...', 6),
    ('A Feast for Crows', 'FANTASY', '2005-10-17', 'The aftermath of the war and the struggle for power...', 6),
    ('A Dance with Dragons', 'FANTASY', '2011-07-12', 'Characters in the North and across the sea...', 6),

    ('Foundation', 'SCIENCE_FICTION', '1951-06-01', 'A scientist tries to save knowledge from a falling galactic empire...', 7),
    ('I, Robot', 'SCIENCE_FICTION', '1950-12-02', 'Nine stories about the Three Laws of Robotics...', 7),
    ('Foundation and Empire', 'SCIENCE_FICTION', '1952-01-01', 'The Foundation faces its greatest test...', 7),
    ('Second Foundation', 'SCIENCE_FICTION', '1953-01-01', 'The search for the mysterious Second Foundation...', 7),
    ('The Caves of Steel', 'SCIENCE_FICTION', '1954-01-01', 'A detective and his robot partner solve a murder...', 7),
    ('The Naked Sun', 'SCIENCE_FICTION', '1957-01-01', 'The detective duo investigates a murder on an isolationist world...', 7),

    ('The Pillars of the Earth', 'HISTORICAL_FICTION', '1989-01-01', 'The building of a cathedral in medieval England...', 8),
    ('World Without End', 'HISTORICAL_FICTION', '2007-01-01', 'Set two centuries after Pillars, following the lives of townspeople...', 8),
    ('A Column of Fire', 'HISTORICAL_FICTION', '2017-09-12', 'The story of espionage and religious conflict in Elizabethan England...', 8),
    ('The Evening and the Morning', 'HISTORICAL_FICTION', '2020-09-15', 'A prequel to The Pillars of the Earth...', 8),
    ('Eye of the Needle', 'HISTORICAL_FICTION', '1978-01-01', 'A German spy tries to escape Britain with vital D-Day plans...', 8),
    ('Fall of Giants', 'HISTORICAL_FICTION', '2010-09-28', 'The first book in the Century Trilogy, following families through WWI...', 8);
-- rollback DELETE FROM books;