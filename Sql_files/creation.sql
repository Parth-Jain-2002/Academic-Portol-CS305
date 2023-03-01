-- PSQL Queries
-- CLEANUP: DROP DATABASE
\c postgres;
DROP DATABASE IF EXISTS academicsystem;

-- CREATE DATABASE: academicsystem
CREATE DATABASE academicsystem;
\c academicsystem;

-- start_date and end_date = redundant but can be used for future
-- TABLE: EVENT(id, description, start_date, end_date)
CREATE TABLE IF NOT EXISTS EventInfo(
    id VARCHAR(50) PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
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

-- TABLE: EnrolledCourse(entry_no, course_id, year, semester)
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



-- DUMMY DATA:
INSERT INTO Eventinfo VALUES('1','Academic Info Change/Update, Course Catalog Modification','2020-07-20','2020-07-25');
INSERT INTO Eventinfo VALUES('2','Course Offering','2020-07-25','2020-07-31');
INSERT INTO Eventinfo VALUES('3','Semester Start and Course Registeration','2020-08-01','2020-08-10');
INSERT INTO Eventinfo VALUES('4','Semester Running','2020-08-11','2020-11-30');
INSERT INTO Eventinfo VALUES('5','Semester End and Grade Submission','2020-12-01','2020-12-10');
INSERT INTO Eventinfo VALUES('6','Grade Finalisation','2020-12-10','2020-12-12');   

INSERT INTO Currentinfo VALUES('current_event_id', '1');
INSERT INTO Currentinfo VALUES('current_year', '2023');
INSERT INTO Currentinfo VALUES('current_semester', '1');

INSERT INTO users VALUES ('admin', 'admin', 'student');
INSERT INTO users VALUES ('admin1', 'admin1', 'instructor');
INSERT INTO users VALUES ('admin2', 'admin2', 'academic');
INSERT INTO users VALUES ('admin3', 'admin3', 'norole');

INSERT INTO Department VALUES ('CSE');
INSERT INTO Department VALUES ('ME');
INSERT INTO Department VALUES ('CE');
INSERT INTO Department VALUES ('EE');
INSERT INTO Department VALUES ('MNC');

INSERT INTO Course VALUES ('CS101', 'Introduction to Computer Science', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS102', 'Data Structures', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS103', 'Algorithms', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS104', 'Operating Systems', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS201', 'Computer Networks', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS202', 'Database Systems', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS203', 'Software Engineering', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS204', 'Compiler Design', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS301', 'Artificial Intelligence', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS302', 'Computer Graphics', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS303', 'Distributed Systems', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS304', 'Machine Learning', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS401', 'Advanced Algorithms', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS402', 'Advanced Database Systems', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS403', 'Advanced Software Engineering', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS404', 'Advanced Compiler Design', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS501', 'Research Project', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS502', 'Research Project', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS503', 'Research Project', 3, 1, 0, 4, 'CSE');
INSERT INTO Course VALUES ('CS504', 'Research Project', 3, 1, 0, 4, 'CSE');


INSERT INTO ProgramCore VALUES ('CS101', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS102', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS103', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS104', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS201', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS202', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS203', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS204', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS301', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS302', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS303', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS304', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS401', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS402', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS403', '2020CSE');
INSERT INTO ProgramCore VALUES ('CS404', '2020CSE');

INSERT INTO ProgramElective VALUES ('CS501', '2020CSE', 1, 2023);
INSERT INTO ProgramElective VALUES ('CS502', '2020CSE', 1, 2023);
INSERT INTO ProgramElective VALUES ('CS503', '2020CSE', 1, 2023);
INSERT INTO ProgramElective VALUES ('CS504', '2020CSE', 1, 2023);

INSERT INTO Batch VALUES ('2020CSE', 5, 2020, 'CSE');

INSERT INTO student VALUES ('admin', 'admin', 'admin', '9999999999', '2020CSE');
INSERT INTO instructor VALUES ('admin1', 'admin1', 'admin1', '9999999999', 'CSE');
INSERT INTO academic VALUES ('admin2', 'admin2', 'admin2', '9999999999');

-- HERE 1 DENOTES ODD SEMESTER/SUMMER/SPRING AND 2 DENOTES EVEN SEMESTER/FALL/WINTER
INSERT INTO CourseOffering VALUES ('CS101', 2023, 1, 'admin1', 1);
INSERT INTO CourseOffering VALUES ('CS102', 2023, 1, 'admin1', 2);
INSERT INTO CourseOffering VALUES ('CS103', 2023, 1, 'admin1', 3);
INSERT INTO CourseOffering VALUES ('CS104', 2023, 1, 'admin1', 4);
INSERT INTO CourseOffering VALUES ('CS201', 2023, 1, 'admin1', 5);
INSERT INTO CourseOffering VALUES ('CS202', 2023, 1, 'admin1', 6);
INSERT INTO CourseOffering VALUES ('CS202', 2022, 1, 'admin1', 6);

INSERT INTO CompletedCourse VALUES ('admin', 'CS101', 2022, 3, 'A');
INSERT INTO CompletedCourse VALUES ('admin', 'CS102', 2022, 3, 'A-');
INSERT INTO CompletedCourse VALUES ('admin', 'CS103', 2022, 4, 'C');

INSERT INTO Prerequisite_group_name VALUES ('CS10X');
INSERT INTO Prerequisite_group_name VALUES ('CS101');
INSERT INTO Prerequisite_group_name VALUES ('CS102');
INSERT INTO Prerequisite_group_name VALUES ('CS103');
INSERT INTO Prerequisite_group_name VALUES ('CS104');

INSERT INTO Prerequisite_group VALUES ('CS101', 'CS101');
INSERT INTO Prerequisite_group VALUES ('CS102', 'CS102');
INSERT INTO Prerequisite_group VALUES ('CS103', 'CS103');
INSERT INTO Prerequisite_group VALUES ('CS104', 'CS104');
INSERT INTO Prerequisite_group VALUES ('CS10X', 'CS101');
INSERT INTO Prerequisite_group VALUES ('CS10X', 'CS102');
INSERT INTO Prerequisite_group VALUES ('CS10X', 'CS103');
INSERT INTO Prerequisite_group VALUES ('CS10X', 'CS104');

INSERT INTO Prerequisite VALUES ('CS201', 'CS10X');
INSERT INTO Prerequisite VALUES ('CS202', 'CS101');
INSERT INTO Prerequisite VALUES ('CS203', 'CS102');
INSERT INTO Prerequisite VALUES ('CS203', 'CS103');
INSERT INTO Prerequisite VALUES ('CS204', 'CS10X');


--INSERT INTO CompletedCourse VALUES ('admin', 'CS104', 2022, 2, 'B');




