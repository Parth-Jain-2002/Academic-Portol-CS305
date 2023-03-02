-- PSQL Queries
-- CLEANUP: DROP DATABASE
\c postgres;
DROP DATABASE IF EXISTS academicsystemtest;

-- CREATE DATABASE: academicsystemtest
CREATE DATABASE academicsystemtest;
\c academicsystemtest;

-- start_date and end_date = redundant but can be used for future
-- TABLE: EVENT(id, description, start_date, end_date)
CREATE TABLE IF NOT EXISTS EventInfo(
    id VARCHAR(50) PRIMARY KEY,
    description VARCHAR(500) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

-- TABLE: CurrentInfo(field, value)
CREATE TABLE IF NOT EXISTS CurrentInfo(
    field VARCHAR(50) PRIMARY KEY,
    value VARCHAR(50) NOT NULL
);

-- TABLE: Users(username, password, role)
CREATE TABLE IF NOT EXISTS users(
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL
);


-- TABLE: Department(name)
CREATE TABLE IF NOT EXISTS Department(
    name VARCHAR(50) PRIMARY KEY
);

-- TABLE: Course(id, name, l, t, p, credits, department)
CREATE TABLE IF NOT EXISTS Course(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    l INT NOT NULL,
    t INT NOT NULL,
    p INT NOT NULL,
    credits INT NOT NULL,
    department VARCHAR(50) NOT NULL,
    FOREIGN KEY (department) REFERENCES department(name)
);

-- TABLE: Batch(id, semester, year, department)
CREATE TABLE IF NOT EXISTS Batch(
    id VARCHAR(50) PRIMARY KEY,
    semester INT NOT NULL,
    year INT NOT NULL,
    department VARCHAR(50) NOT NULL,
    FOREIGN KEY (department) REFERENCES Department(name)
);

-- TABLE: ProgramCore(course_id, batch_id)
CREATE TABLE IF NOT EXISTS ProgramCore(
    course_id VARCHAR(50) NOT NULL,
    batch_id VARCHAR(50) NOT NULL,
    PRIMARY KEY (course_id, batch_id),
    FOREIGN KEY (course_id) REFERENCES Course(id),
    FOREIGN KEY (batch_id) REFERENCES Batch(id)
);

-- TABLE: ProgramElective(course_id, batch_id, semester, year)
CREATE TABLE IF NOT EXISTS ProgramElective(
    course_id VARCHAR(50) NOT NULL,
    batch_id VARCHAR(50) NOT NULL,
    semester INT NOT NULL,
    year INT NOT NULL,
    PRIMARY KEY (course_id, batch_id),
    FOREIGN KEY (course_id) REFERENCES Course(id),
    FOREIGN KEY (batch_id) REFERENCES Batch(id)
);

-- TABLE: Student(entry_no, name, email, phone, batch)
CREATE TABLE IF NOT EXISTS Student(
    entry_no VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    batch VARCHAR(50) NOT NULL,
    FOREIGN KEY (batch) REFERENCES Batch(id)
);

-- TABLE: Instructor(id, name, email, phone, department)
CREATE TABLE IF NOT EXISTS Instructor(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    department VARCHAR(50) NOT NULL,
    FOREIGN KEY (department) REFERENCES Department(name)
);

-- TABLE: Academic(id, name, email, phone)
CREATE TABLE IF NOT EXISTS Academic(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(10) NOT NULL
);

-- Can add time slot as well
-- TABLE: CourseOffering(course_id, year, semester, instructor, cgpa)
CREATE TABLE IF NOT EXISTS CourseOffering(
    course_id VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    semester VARCHAR(50) NOT NULL,
    instructor VARCHAR(50) NOT NULL,
    cgpa FLOAT(2) NOT NULL,
    PRIMARY KEY (course_id, year, semester),
    FOREIGN KEY (course_id) REFERENCES Course(id),
    FOREIGN KEY (instructor) REFERENCES Instructor(id)
);

-- TABLE: CompletedCourse(entry_no, course_id, year, semester, grade)
CREATE TABLE IF NOT EXISTS CompletedCourse(
    entry_no VARCHAR(50) NOT NULL,
    course_id VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    semester INT NOT NULL,
    grade VARCHAR(2) NOT NULL,
    PRIMARY KEY (entry_no, course_id, year, semester),
    FOREIGN KEY (entry_no) REFERENCES Student(entry_no),
    FOREIGN KEY (course_id) REFERENCES Course(id)
);

-- TABLE: EnrolledCourse(entry_no, course_id, year, semester, grade)
CREATE TABLE IF NOT EXISTS EnrolledCourse(
    entry_no VARCHAR(50) NOT NULL,
    course_id VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    semester INT NOT NULL,
    grade VARCHAR(2),
    PRIMARY KEY (entry_no, course_id, year, semester),
    FOREIGN KEY (entry_no) REFERENCES Student(entry_no),
    FOREIGN KEY (course_id) REFERENCES Course(id)
);

-- Pre-requisites can "and" and "or" conditions as well
-- TABLE: Prerequisite_group_name(group_id)
CREATE TABLE IF NOT EXISTS Prerequisite_group_name(
    group_id VARCHAR(50) PRIMARY KEY
);


-- TABLE: Prerequisite_group(group_id, pre_req_id)
CREATE TABLE IF NOT EXISTS Prerequisite_group(
    group_id VARCHAR(50) NOT NULL,
    pre_req_id VARCHAR(50) NOT NULL,
    PRIMARY KEY (group_id, pre_req_id),
    FOREIGN KEY (pre_req_id) REFERENCES Course(id),
    FOREIGN KEY (group_id) REFERENCES Prerequisite_group_name(group_id)
);

-- TABLE: Prerequisite(course_id, group_id)
CREATE TABLE IF NOT EXISTS Prerequisite(
    course_id VARCHAR(50) NOT NULL,
    group_id VARCHAR(50) NOT NULL,
    PRIMARY KEY (course_id, group_id),
    FOREIGN KEY (course_id) REFERENCES Course(id),
    FOREIGN KEY (group_id) REFERENCES Prerequisite_group_name(group_id)
);


