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
    description VARCHAR(150) NOT NULL,
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


-- TABLE: Department(code, name)
CREATE TABLE IF NOT EXISTS Department(
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
    );

-- TABLE: ProgramType(name)
CREATE TABLE IF NOT EXISTS ProgramType(
    name VARCHAR(50) PRIMARY KEY
    );

-- L= No. of lecture ‘hours’(actually 50 min.) per week
-- T= No. of tutorial ‘hours’= L/3, by default.
-- P= No. of laboratory ‘hours’.
-- S = Total preparation ‘hours’by students including assignments and self-study.
-- C = Total credit-terms.
-- TABLE: Course(id, name, l, t, p, s, credits, department)
CREATE TABLE IF NOT EXISTS Course(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    l INT NOT NULL,
    t INT NOT NULL,
    p INT NOT NULL,
    s INT NOT NULL,
    credits FLOAT NOT NULL,
    department VARCHAR(50) NOT NULL,
    FOREIGN KEY (department) REFERENCES department(code)
    );

-- TABLE: Batch(id, semester, year, department)
CREATE TABLE IF NOT EXISTS Batch(
    id VARCHAR(50) PRIMARY KEY,
    semester INT NOT NULL,
    year INT NOT NULL,
    department VARCHAR(50) NOT NULL,
    FOREIGN KEY (department) REFERENCES Department(code)
    );

-- TABLE: ProgramCore(course_id, batch_id, type)
CREATE TABLE IF NOT EXISTS ProgramCore(
    course_id VARCHAR(50) NOT NULL,
    batch_id VARCHAR(50) NOT NULL,
    PRIMARY KEY (course_id, batch_id),
    FOREIGN KEY (course_id) REFERENCES Course(id),
    FOREIGN KEY (batch_id) REFERENCES Batch(id)
    );

-- TABLE: ProgramElective(course_id, batch_id, type)
CREATE TABLE IF NOT EXISTS ProgramElective(
    course_id VARCHAR(50) NOT NULL,
    batch_id VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    PRIMARY KEY (course_id, batch_id),
    FOREIGN KEY (course_id) REFERENCES Course(id),
    FOREIGN KEY (batch_id) REFERENCES Batch(id),
    FOREIGN KEY (type) REFERENCES ProgramType(name)
    );

-- TABLE: Student(entry_no, name, email, phone, batch)
CREATE TABLE IF NOT EXISTS Student(
    entry_no VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    batch VARCHAR(50) NOT NULL,
    FOREIGN KEY (batch) REFERENCES Batch(id),
    FOREIGN KEY (entry_no) REFERENCES users(username)
    );

-- TABLE: Instructor(id, name, email, phone, department)
CREATE TABLE IF NOT EXISTS Instructor(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    department VARCHAR(50) NOT NULL,
    FOREIGN KEY (department) REFERENCES Department(code),
    FOREIGN KEY (id) REFERENCES users(username)
    );

-- TABLE: Academic(id, name, email, phone)
CREATE TABLE IF NOT EXISTS Academic(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    FOREIGN KEY (id) REFERENCES users(username)
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
-- EVENT INFO TABLE:
INSERT INTO Eventinfo VALUES('1','Academic Info Change/Update, Course Catalog Modification','2020-07-20','2020-07-25');
INSERT INTO Eventinfo VALUES('2','Course Offering','2020-07-25','2020-07-31');
INSERT INTO Eventinfo VALUES('3','Semester Start and Course Registeration','2020-08-01','2020-08-10');
INSERT INTO Eventinfo VALUES('4','Semester Running','2020-08-11','2020-11-30');
INSERT INTO Eventinfo VALUES('5','Semester End and Grade Submission','2020-12-01','2020-12-10');
INSERT INTO Eventinfo VALUES('6','Grade Finalisation','2020-12-10','2020-12-12');

-- CURRENT INFO TABLE:
INSERT INTO Currentinfo VALUES('current_event_id', '1');
INSERT INTO Currentinfo VALUES('current_year', '2023');
INSERT INTO Currentinfo VALUES('current_semester', '1');

-- USER TABLE:
INSERT INTO users VALUES ('student1', 'student1', 'student');
INSERT INTO users VALUES ('student2', 'student2', 'student');
INSERT INTO users VALUES ('student3', 'student3', 'student');
INSERT INTO users VALUES ('student4', 'student4', 'student');
INSERT INTO users VALUES ('student5', 'student5', 'student');
INSERT INTO users VALUES ('student6', 'student6', 'student');

INSERT INTO users VALUES ('faculty1', 'faculty1', 'instructor');
INSERT INTO users VALUES ('faculty2', 'faculty2', 'instructor');
INSERT INTO users VALUES ('faculty3', 'faculty3', 'instructor');
INSERT INTO users VALUES ('faculty4', 'faculty4', 'instructor');
INSERT INTO users VALUES ('faculty5', 'faculty5', 'instructor');

INSERT INTO users VALUES ('academic1', 'academic1', 'academic');
INSERT INTO users VALUES ('academic2', 'academic2', 'academic');
INSERT INTO users VALUES ('academic3', 'academic3', 'academic');

INSERT INTO users VALUES ('admin3', 'admin3', 'norole');

-- DEPARTMENT TABLE:
INSERT INTO Department VALUES ('CS', 'Computer Science and Engineering');
INSERT INTO Department VALUES ('CE', 'Civil Engineering');
INSERT INTO Department VALUES ('EE', 'Electrical Engineering');
INSERT INTO Department VALUES ('ME', 'Mechanical Engineering');
INSERT INTO Department VALUES ('CH', 'Chemical Engineering');
INSERT INTO Department VALUES ('BM', 'Biomedical Engineering');
INSERT INTO Department VALUES ('MM', 'Metallurgical and Materials Engineering');
INSERT INTO Department VALUES ('CY', 'Chemistry');
INSERT INTO Department VALUES ('MA', 'Mathematics');
INSERT INTO Department VALUES ('PH', 'Physics');
INSERT INTO Department VALUES ('HU', 'Humanities and Social Sciences');

INSERT INTO ProgramType VALUES ('HS');
INSERT INTO ProgramType VALUES ('Science');
INSERT INTO ProgramType VALUES ('Open');
INSERT INTO ProgramType VALUES ('Core');

-- BATCH TABLE:
INSERT INTO Batch VALUES ('2020CSB', 5, 2020, 'CS');
INSERT INTO Batch VALUES ('2020CEB', 5, 2020, 'CE');
INSERT INTO Batch VALUES ('2021CSB', 3, 2021, 'CS');
INSERT INTO Batch VALUES ('2019CSB', 8, 2019, 'CS');
INSERT INTO Batch VALUES ('2022CSB', 1, 2022, 'CS');
INSERT INTO Batch VALUES ('2022CEB', 1, 2022, 'CE');

-- STUDENT TABLE (entry_no, name, email, phone, batch)
INSERT INTO student VALUES ('student1', 'student1', 'student1', '9999999999', '2020CSB');
INSERT INTO student VALUES ('student2', 'student2', 'student2', '9999999999', '2020CEB');
INSERT INTO student VALUES ('student3', 'student3', 'student3', '9999999999', '2021CSB');
INSERT INTO student VALUES ('student4', 'student4', 'student4', '9999999999', '2019CSB');
INSERT INTO student VALUES ('student5', 'student5', 'student5', '9999999999', '2022CSB');
INSERT INTO student VALUES ('student6', 'student6', 'student6', '9999999999', '2020CEB');

-- INSTRUCTOR TABLE (id, name, email, phone, department)
INSERT INTO instructor VALUES ('faculty1', 'faculty1', 'faculty1', '9999999999', 'CS');
INSERT INTO instructor VALUES ('faculty2', 'faculty2', 'faculty2', '9999999999', 'CS');
INSERT INTO instructor VALUES ('faculty3', 'faculty3', 'faculty3', '9999999999', 'CS');
INSERT INTO instructor VALUES ('faculty4', 'faculty4', 'faculty4', '9999999999', 'CE');
INSERT INTO instructor VALUES ('faculty5', 'faculty5', 'faculty5', '9999999999', 'CE');

-- ACADEMIC TABLE (id, name, email, phone)
INSERT INTO academic VALUES ('academic1', 'academic1', 'academic1', '9999999999');
INSERT INTO academic VALUES ('academic2', 'academic2', 'academic2', '9999999999');
INSERT INTO academic VALUES ('academic3', 'academic3', 'academic3', '9999999999');

-- L= No. of lecture ‘hours’(actually 50 min.) per week
-- T= No. of tutorial ‘hours’= L/3, by default.
-- P= No. of laboratory ‘hours’.
-- S = Total preparation ‘hours’by students including assignments and self-study.
-- C = Total credit-terms.
-- COURSE TABLE (id, name, l, t, p, s, credits, department)
-- First semester:
Insert into Course values('MA101','Calculus',3,1,0,5,3,'MA');
Insert into Course values('HS103','Professional English Communication',3,2,2,3,3,'HU');
Insert into Course values('HS102','English Language Skills',3,2,2,3,3,'HU');
Insert into Course values('NC101','NCC I',0,0,2,11,1,'MA');
Insert into Course values('NS101','NSS I',0,0,2,11,1,'MA');
Insert into Course values('NO101','NSO I',0,0,2,11,1,'MA');
Insert into Course values('PH101','Physics for Engineers',3,1,0,5,3,'PH');
Insert into Course values('PH102','Physics for Engineers Lab',0,0,4,2,2,'PH');
Insert into Course values('CY101','Chemistry for Engineers',3,1,2,6,4,'CY');
Insert into Course values('GE104','Introduction to Electrical Engineering',2,2,2,13,3,'EE');
Insert into Course values('GE103','Introduction to Computer Programming & Data Structure',3,0,3,15,4.5,'CS');
Insert into Course values('GE102','Workshop Practice',0,0,4,2,2,'ME');
Insert into Course values('GE105','Engineering Drawing',0,0,3,3,1.5,'ME');
Insert into Course values('HS101','History of Technology',3,1,0,5,3,'HU');
Insert into Course values('GE101','Technology Museum Lab',0,0,2,1,1,'CE');
-- Second Semester:
Insert into Course values('MA102','Linear Algebra, Integral Transforms and Special Functions',3,1,0,5,3,'MA');
Insert into Course values('ME101','Engineering Mechanics',3,1,0,5,3,'ME');
Insert into Course values('CE101','Engineering Mechanics',3,1,0,5,3,'CE');
Insert into Course values('CH101','Introduction to Chemical Engineering',3,1,0,5,3,'CH');
Insert into Course values('CS101','Discrete Mathematical Structures',3,1,0,5,3,'CS');
Insert into Course values('GE106','Materials Science for Electrical and Electronics Engineers',3,1,0,5,3,'EE');
Insert into Course values('GE110','Introduction to Metallurgical and Materials Engineering',3,1,0,5,3,'MM');
Insert into Course values('NC102','NCC II',0,0,2,11,1,'MA');
Insert into Course values('NS102','NSS II',0,0,2,11,1,'MA');
Insert into Course values('NO102','NSO II',0,0,2,11,1,'MA');
-- Third Semester:
INSERT INTO Course VALUES ('CS201', 'Data Structures', 3, 1, 2, 6, 4, 'CS');
INSERT INTO Course VALUES ('CS203', 'Digital Logic Design', 3, 1, 2, 6, 4, 'CS');
INSERT INTO Course VALUES ('MA201', 'Differential Equations', 3, 1, 0, 5, 3, 'MA');
INSERT INTO Course VALUES ('EE201', 'Signals and Systems', 3, 1, 0, 5, 3, 'EE');
INSERT INTO Course VALUES ('NCIII', 'NCC', 0, 0, 2, 1, 1, 'MA');
INSERT INTO Course VALUES ('NOIII', 'NSO', 0, 0, 2, 1, 1, 'MA');
INSERT INTO Course VALUES ('NSIII', 'NSS', 0, 0, 2, 1, 1, 'MA');
INSERT INTO Course VALUES ('HS201', 'Economics', 3, 1, 0, 5, 3, 'HU');
INSERT INTO Course VALUES ('GE108', 'Basic Electronics', 3, 1, 0, 5, 3, 'EE');
INSERT INTO Course VALUES ('GE107', 'Tinkering Lab', 0, 0, 3, 3, 1.5, 'CS');
INSERT INTO Course VALUES ('GE109', 'Introduction to Engineering Products', 0, 0, 2, 1, 1, 'CE');
-- Fourth Semester:
INSERT INTO Course VALUES ('CS202', 'Programming Paradigms and Pragmatics', 3, 1, 2, 6, 4, 'CS');
INSERT INTO Course VALUES ('CS204', 'Computer Architecture', 3, 1, 2, 6, 4, 'CS');
INSERT INTO Course VALUES ('MA202', 'Probability and Statistics', 3, 1, 0, 5, 3, 'CS');
INSERT INTO Course VALUES ('NCIV', 'NCC', 0, 0, 2, 1, 1, 'MA');
INSERT INTO Course VALUES ('NOIV', 'NSO', 0, 0, 2, 1, 1, 'MA');
INSERT INTO Course VALUES ('NSIV', 'NSS', 0, 0, 2, 1, 1, 'MA');
-- Fifth Semester:
INSERT INTO Course VALUES ('CS301', 'Introduction to Databases Systems', 3, 1, 2, 6, 4, 'CS');
INSERT INTO Course VALUES ('CS302', 'Analysis and Design of Algorithms', 3, 1, 0, 5, 3, 'CS');
INSERT INTO Course VALUES ('CS303', 'Operating Systems', 3, 1, 2, 6, 4, 'CS');
INSERT INTO Course VALUES ('HS301', 'Industrial Management', 3, 1, 0, 5, 3, 'HU');
INSERT INTO Course VALUES ('GE111', 'Introduction to Environmental Science & Engineering', 3, 1, 0, 5, 3, 'CH');
INSERT INTO Course VALUES ('HS104', 'Professional Ethics', 1, 3, 1, 6, 1.5, 'HU');
Insert into Course values('HS202','Human Geography and Societal Needs',1,1,4,3,3,'HU');
Insert into Course values('BM101','Biology for Engineers',3,1,0,5,3,'BM');
-- Sixth Semester:
Insert into Course values('CS304','Computer Networks',3,1,2,6,4,'CS');
Insert into Course values('CS305','Software Engineering',3,1,2,6,4,'CS');
Insert into Course values('CS306','Theory of Computation',3,1,0,5,3,'CS');
Insert into Course values('CP301','Development Engineering Project',0,0,6,3,3,'CS');

-- Remaining Required Courses:
Insert into Course values('II301','Industrial Internship and Comprehensive Viva Voce',0,0,7,3.5,3.5,'CS');
Insert into Course values('CP302','Capstone Project I',0,0,6,3,3,'CS');
Insert into Course values('CP303','Capstone Project II',0,0,6,3,3,'CS');

-- Elective Courses:
Insert into Course values('HS647','ISSUES IN INTERNATIONAL FINANCE',3,0,0,6,3,'HU');
Insert into Course values('HS621','APPLIED ECONOMETRICS',3,0,0,6,3,'HU');
Insert into Course values('CS616','ADVANCED COMPUTER VISION',2,0,2,5,3,'CS');
Insert into Course values('CS503','MACHINE LEARNING',3,0,2,7,4,'CS');
Insert into Course values('MA513','OPTIMIZATION TECHNIQUES',3,1,0,5,3,'MA');
Insert into Course values('MA516','MATHEMATICAL IMAGE PROCESSING',3,1,0,5,3,'MA');
INSERT into Course values('CS533','REINFORCEMENT LEARNING',3,0,0,6,3,'CS');
INSERT into Course values('CS539','Internet of Things',3,0,0,6,3,'CS');


-- HERE 1 DENOTES ODD SEMESTER/SUMMER/SPRING AND 2 DENOTES EVEN SEMESTER/FALL/WINTER
INSERT INTO CourseOffering VALUES ('MA101', 2020, 1, 'faculty1', 0);
INSERT INTO CourseOffering VALUES ('MA101', 2023, 1, 'faculty1', 0);
INSERT INTO CourseOffering VALUES ('CS101', 2023, 1, 'faculty1', 0);
INSERT INTO CourseOffering VALUES ('HS102', 2023, 1, 'faculty1', 5);
INSERT INTO CourseOffering VALUES ('CS201', 2023, 1, 'faculty2', 5);
INSERT INTO CourseOffering VALUES ('CS202', 2023, 1, 'faculty2', 5);
INSERT INTO CourseOffering VALUES ('CS203', 2023, 1, 'faculty2', 5);
INSERT INTO CourseOffering VALUES ('CS204', 2023, 1, 'faculty2', 5);
INSERT INTO CourseOffering VALUES ('CS301', 2023, 1, 'faculty3', 5);
INSERT INTO CourseOffering VALUES ('CS302', 2023, 1, 'faculty3', 5);
INSERT INTO CourseOffering VALUES ('CS303', 2023, 1, 'faculty3', 5);
INSERT INTO CourseOffering VALUES ('CS304', 2023, 1, 'faculty3', 5);


INSERT INTO Prerequisite_group_name VALUES ('CS101');
INSERT INTO Prerequisite_group_name VALUES ('CS20X');
INSERT INTO Prerequisite_group_name VALUES ('CS201');
INSERT INTO Prerequisite_group_name VALUES ('CS202');
INSERT INTO Prerequisite_group_name VALUES ('CS203');
INSERT INTO Prerequisite_group_name VALUES ('CS204');

INSERT INTO Prerequisite_group VALUES ('CS101', 'CS101');
INSERT INTO Prerequisite_group VALUES ('CS201', 'CS201');
INSERT INTO Prerequisite_group VALUES ('CS20X', 'CS201');
INSERT INTO Prerequisite_group VALUES ('CS20X', 'CS202');
INSERT INTO Prerequisite_group VALUES ('CS20X', 'CS203');
INSERT INTO Prerequisite_group VALUES ('CS20X', 'CS204');

INSERT INTO Prerequisite VALUES ('CS201', 'CS101');
INSERT INTO Prerequisite VALUES ('CS202', 'CS101');
INSERT INTO Prerequisite VALUES ('CS203', 'CS101');
INSERT INTO Prerequisite VALUES ('CS204', 'CS101');
INSERT INTO Prerequisite VALUES ('CS301', 'CS20X');
INSERT INTO Prerequisite VALUES ('CS302', 'CS201');

INSERT INTO EnrolledCourse VALUES('student5', 'MA101', 2023, 1, '');
INSERT INTO EnrolledCourse VALUES('student6', 'MA101', 2023, 1, 'A');

INSERT INTO EnrolledCourse VALUES ('student3', 'CS201', 2023, 1, '');
INSERT INTO EnrolledCourse VALUES ('student3', 'CS203', 2023, 1, '');
INSERT INTO EnrolledCourse VALUES ('student3', 'MA201', 2023, 1, '');
INSERT INTO EnrolledCourse VALUES ('student3', 'EE201', 2023, 1, '');
INSERT INTO EnrolledCourse VALUES ('student3', 'NCIII', 2023, 1, '');
INSERT INTO EnrolledCourse VALUES ('student3', 'HS201', 2023, 1, '');
INSERT INTO EnrolledCourse VALUES ('student3', 'GE108', 2023, 1, '');
INSERT INTO EnrolledCourse VALUES ('student3', 'GE107', 2023, 1, '');


-- Student 1 has completed all courses in first 4 semesters, with different grades
INSERT INTO CompletedCourse VALUES ('student1', 'MA101', 2020, 1, 'A');
INSERT INTO CompletedCourse VALUES ('student1', 'HS103', 2020, 1, 'A-');
INSERT INTO CompletedCourse VALUES ('student1', 'HS102', 2020, 1, 'B');
INSERT INTO CompletedCourse VALUES ('student1', 'NC101', 2020, 1, 'B-');
INSERT INTO CompletedCourse VALUES ('student1', 'PH101', 2020, 1, 'C');
INSERT INTO CompletedCourse VALUES ('student1', 'PH102', 2020, 1, 'C-');
INSERT INTO CompletedCourse VALUES ('student1', 'CY101', 2020, 1, 'D');
INSERT INTO CompletedCourse VALUES ('student1', 'GE104', 2020, 1, 'E');
INSERT INTO CompletedCourse VALUES ('student1', 'GE103', 2020, 2, 'A');
INSERT INTO CompletedCourse VALUES ('student1', 'GE102', 2020, 2, 'A-');
INSERT INTO CompletedCourse VALUES ('student1', 'GE105', 2020, 2, 'B');
INSERT INTO CompletedCourse VALUES ('student1', 'HS101', 2020, 2, 'B-');
INSERT INTO CompletedCourse VALUES ('student1', 'GE101', 2020, 2, 'A');
INSERT INTO CompletedCourse VALUES ('student1', 'MA102', 2020, 2, 'A-');
INSERT INTO CompletedCourse VALUES ('student1', 'CS101', 2020, 2, 'B');
INSERT INTO CompletedCourse VALUES ('student1', 'NC102', 2020, 2, 'B-');
INSERT INTO CompletedCourse VALUES ('student1', 'CS201', 2020, 3, 'A');
INSERT INTO CompletedCourse VALUES ('student1', 'CS203', 2020, 3, 'A-');
INSERT INTO CompletedCourse VALUES ('student1', 'MA201', 2020, 3, 'B');
INSERT INTO CompletedCourse VALUES ('student1', 'EE201', 2020, 3, 'B-');
INSERT INTO CompletedCourse VALUES ('student1', 'NCIII', 2020, 3, 'C');
INSERT INTO CompletedCourse VALUES ('student1', 'HS201', 2020, 3, 'C-');
INSERT INTO CompletedCourse VALUES ('student1', 'GE108', 2020, 4, 'D');
INSERT INTO CompletedCourse VALUES ('student1', 'GE107', 2020, 4, 'E');
INSERT INTO CompletedCourse VALUES ('student1', 'GE109', 2020, 4, 'A');
INSERT INTO CompletedCourse VALUES ('student1', 'CS202', 2020, 4, 'A');
INSERT INTO CompletedCourse VALUES ('student1', 'CS204', 2020, 4, 'A');
INSERT INTO CompletedCourse VALUES ('student1', 'MA202', 2020, 4, 'A');
INSERT INTO CompletedCourse VALUES ('student1', 'NCIV', 2020, 4, 'A');

-- Student 3 has completed all courses in first 2 semesters, with different grades
INSERT INTO CompletedCourse VALUES ('student3', 'MA101', 2020, 1, 'A');
INSERT INTO CompletedCourse VALUES ('student3', 'HS103', 2020, 1, 'A-');
INSERT INTO CompletedCourse VALUES ('student3', 'HS102', 2020, 1, 'B');
INSERT INTO CompletedCourse VALUES ('student3', 'NC101', 2020, 1, 'B-');
INSERT INTO CompletedCourse VALUES ('student3', 'PH101', 2020, 1, 'C');
INSERT INTO CompletedCourse VALUES ('student3', 'PH102', 2020, 1, 'C-');
INSERT INTO CompletedCourse VALUES ('student3', 'CY101', 2020, 1, 'D');
INSERT INTO CompletedCourse VALUES ('student3', 'GE104', 2020, 1, 'E');
INSERT INTO CompletedCourse VALUES ('student3', 'GE103', 2020, 1, 'F');
INSERT INTO CompletedCourse VALUES ('student3', 'GE102', 2020, 2, 'A-');
INSERT INTO CompletedCourse VALUES ('student3', 'GE105', 2020, 2, 'B');
INSERT INTO CompletedCourse VALUES ('student3', 'HS101', 2020, 2, 'B-');
INSERT INTO CompletedCourse VALUES ('student3', 'GE101', 2020, 2, 'A');
INSERT INTO CompletedCourse VALUES ('student3', 'MA102', 2020, 2, 'A-');
INSERT INTO CompletedCourse VALUES ('student3', 'CS101', 2020, 2, 'B');
INSERT INTO CompletedCourse VALUES ('student3', 'NC102', 2020, 2, 'B-');

-- Student 4 has completed all courses in first 8 semesters, with different grades
INSERT INTO CompletedCourse VALUES ('student4', 'MA101', 2019, 1, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'HS103', 2019, 1, 'A-');
INSERT INTO CompletedCourse VALUES ('student4', 'HS102', 2019, 1, 'B');
INSERT INTO CompletedCourse VALUES ('student4', 'NC101', 2019, 1, 'B-');
INSERT INTO CompletedCourse VALUES ('student4', 'PH101', 2019, 1, 'C');
INSERT INTO CompletedCourse VALUES ('student4', 'PH102', 2019, 1, 'C-');
INSERT INTO CompletedCourse VALUES ('student4', 'CY101', 2019, 1, 'D');
INSERT INTO CompletedCourse VALUES ('student4', 'GE104', 2019, 1, 'E');
INSERT INTO CompletedCourse VALUES ('student4', 'GE103', 2020, 2, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'GE102', 2020, 2, 'A-');
INSERT INTO CompletedCourse VALUES ('student4', 'GE105', 2020, 2, 'B');
INSERT INTO CompletedCourse VALUES ('student4', 'HS101', 2020, 2, 'B-');
INSERT INTO CompletedCourse VALUES ('student4', 'GE101', 2020, 2, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'MA102', 2020, 2, 'A-');
INSERT INTO CompletedCourse VALUES ('student4', 'CS101', 2020, 2, 'B');
INSERT INTO CompletedCourse VALUES ('student4', 'NC102', 2020, 2, 'B-');
INSERT INTO CompletedCourse VALUES ('student4', 'CS201', 2020, 3, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS203', 2020, 3, 'A-');
INSERT INTO CompletedCourse VALUES ('student4', 'MA201', 2020, 3, 'B');
INSERT INTO CompletedCourse VALUES ('student4', 'EE201', 2020, 3, 'B-');
INSERT INTO CompletedCourse VALUES ('student4', 'NCIII', 2020, 3, 'C');
INSERT INTO CompletedCourse VALUES ('student4', 'HS201', 2020, 3, 'C-');
INSERT INTO CompletedCourse VALUES ('student4', 'GE108', 2021, 4, 'D');
INSERT INTO CompletedCourse VALUES ('student4', 'GE107', 2021, 4, 'E');
INSERT INTO CompletedCourse VALUES ('student4', 'GE109', 2021, 4, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS202', 2021, 4, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS204', 2021, 4, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'MA202', 2021, 4, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'NCIV', 2021, 4, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS301', 2021, 5, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS302', 2021, 5, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS303', 2021, 5, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'HS301', 2021, 5, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'GE111', 2021, 5, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'HS104', 2021, 5, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'HS202', 2021, 5, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'BM101', 2021, 5, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS304', 2022, 6, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS305', 2022, 6, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS306', 2022, 6, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CP301', 2022, 6, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'II301', 2022, 7, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CP302', 2022, 7, 'A');
insert into CompletedCourse values ('student4', 'MA516', 2022, 7, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS616', 2022, 7, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'HS647', 2022, 7, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS539', 2022, 7, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CP303', 2023, 8, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'MA513', 2023, 8, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS503', 2023, 8, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'HS621', 2023, 8, 'A');
INSERT INTO CompletedCourse VALUES ('student4', 'CS533', 2023, 8, 'A');

-- All the courses that student 4 has completed, enter them as ProgramCore(course_id, batch_id)
INSERT INTO ProgramCore VALUES ('MA101', '2019CSB');
INSERT INTO ProgramCore VALUES ('HS103', '2019CSB');
INSERT INTO ProgramCore VALUES ('HS102', '2019CSB');
INSERT INTO ProgramCore VALUES ('NC101', '2019CSB');
INSERT INTO ProgramCore VALUES ('PH101', '2019CSB');
INSERT INTO ProgramCore VALUES ('PH102', '2019CSB');
INSERT INTO ProgramCore VALUES ('CY101', '2019CSB');
INSERT INTO ProgramCore VALUES ('GE104', '2019CSB');
INSERT INTO ProgramCore VALUES ('GE103', '2019CSB');
INSERT INTO ProgramCore VALUES ('GE102', '2019CSB');
INSERT INTO ProgramCore VALUES ('GE105', '2019CSB');
INSERT INTO ProgramCore VALUES ('HS101', '2019CSB');
INSERT INTO ProgramCore VALUES ('GE101', '2019CSB');
INSERT INTO ProgramCore VALUES ('MA102', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS101', '2019CSB');
INSERT INTO ProgramCore VALUES ('NC102', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS201', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS203', '2019CSB');
INSERT INTO ProgramCore VALUES ('MA201', '2019CSB');
INSERT INTO ProgramCore VALUES ('EE201', '2019CSB');
INSERT INTO ProgramCore VALUES ('NCIII', '2019CSB');
INSERT INTO ProgramCore VALUES ('HS201', '2019CSB');
INSERT INTO ProgramCore VALUES ('GE108', '2019CSB');
INSERT INTO ProgramCore VALUES ('GE107', '2019CSB');
INSERT INTO ProgramCore VALUES ('GE109', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS202', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS204', '2019CSB');
INSERT INTO ProgramCore VALUES ('MA202', '2019CSB');
INSERT INTO ProgramCore VALUES ('NCIV', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS301', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS302', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS303', '2019CSB');
INSERT INTO ProgramCore VALUES ('HS301', '2019CSB');
INSERT INTO ProgramCore VALUES ('GE111', '2019CSB');
INSERT INTO ProgramCore VALUES ('HS104', '2019CSB');
INSERT INTO ProgramCore VALUES ('HS202', '2019CSB');
INSERT INTO ProgramCore VALUES ('BM101', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS304', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS305', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS306', '2019CSB');
INSERT INTO ProgramCore VALUES ('CP301', '2019CSB');
INSERT INTO ProgramCore VALUES ('II301', '2019CSB');
INSERT INTO ProgramCore VALUES ('CP302', '2019CSB');
INSERT INTO ProgramCore VALUES ('CP303', '2019CSB');
INSERT INTO ProgramCore VALUES ('CS101', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS201', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS203', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS202', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS204', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS301', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS302', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS303', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS304', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS305', '2020CSB');
INSERT INTO ProgramCore VALUES ('CS306', '2020CSB');

-- ProgramElective(course_id, batch_id, semester, year, type)
INSERT INTO ProgramElective VALUES ('HS647', '2019CSB', 'HS');
INSERT INTO ProgramElective VALUES ('HS621', '2019CSB', 'HS');
INSERT INTO ProgramElective VALUES ('CS616', '2019CSB', 'Core');
INSERT INTO ProgramElective VALUES ('CS503', '2019CSB', 'Core');
INSERT INTO ProgramElective VALUES ('MA513', '2019CSB', 'Science');
INSERT INTO ProgramElective VALUES ('MA516', '2019CSB', 'Science');
INSERT INTO ProgramElective VALUES ('CS533', '2019CSB', 'Open');
INSERT INTO ProgramElective VALUES ('CS539', '2019CSB', 'Open');







