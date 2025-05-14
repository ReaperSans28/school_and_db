ALTER TABLE student
    ADD CONSTRAINT student_age_check CHECK (age >= 16),
    ADD CONSTRAINT student_name_unique UNIQUE (name),
    ADD CONSTRAINT student_name_not_null CHECK (name IS NOT NULL),
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT faculty_name_color_unique UNIQUE (name, color); 