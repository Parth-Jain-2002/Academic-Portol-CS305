-- CLEANUP TABLES:
TRUNCATE TABLE Prerequisite;
TRUNCATE TABLE Prerequisite_group;
TRUNCATE TABLE Prerequisite_group_name CASCADE;
TRUNCATE TABLE EnrolledCourse;
TRUNCATE TABLE CompletedCourse;
TRUNCATE TABLE CourseOffering;
TRUNCATE TABLE ProgramElective;
TRUNCATE TABLE ProgramCore;
TRUNCATE TABLE Batch CASCADE;
TRUNCATE TABLE Course CASCADE;
TRUNCATE TABLE student CASCADE;
TRUNCATE TABLE instructor CASCADE;
TRUNCATE TABLE academic;
TRUNCATE TABLE Department CASCADE;
TRUNCATE TABLE users;
TRUNCATE TABLE Currentinfo;
TRUNCATE TABLE Eventinfo;

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

INSERT INTO Batch VALUES ('2020CSE', 5, 2020, 'CSE');

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

INSERT INTO student VALUES ('admin', 'admin', 'admin', '9999999999', '2020CSE');
INSERT INTO instructor VALUES ('admin1', 'admin1', 'admin1', '9999999999', 'CSE');
INSERT INTO academic VALUES ('admin2', 'admin2', 'admin2', '9999999999');

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

INSERT INTO EnrolledCourse VALUES('admin', 'CS101', 2023, 5, '');
INSERT INTO CompletedCourse VALUES ('admin', 'CS104', 2022, 2, 'B');