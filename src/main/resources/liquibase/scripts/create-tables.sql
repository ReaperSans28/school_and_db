CREATE SEQUENCE IF NOT EXISTS faculty_seq START 1;
CREATE SEQUENCE IF NOT EXISTS student_seq START 1;
CREATE SEQUENCE IF NOT EXISTS avatar_seq START 1;

CREATE TABLE IF NOT EXISTS faculty (
    id BIGINT PRIMARY KEY DEFAULT nextval('faculty_seq'),
    name VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS student (
    id BIGINT PRIMARY KEY DEFAULT nextval('student_seq'),
    name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL,
    faculty_id BIGINT REFERENCES faculty(id)
);

CREATE TABLE IF NOT EXISTS avatar (
    id BIGINT PRIMARY KEY DEFAULT nextval('avatar_seq'),
    file_path VARCHAR(255),
    file_size BIGINT,
    media_type VARCHAR(255),
    data OID,
    student_id BIGINT REFERENCES student(id)
);
