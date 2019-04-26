-- Table: employee

CREATE TABLE employee
(
    id IDENTITY NOT NULL PRIMARY KEY,
    lastname CHARACTER VARYING(255) NOT NULL,
    firstname CHARACTER VARYING(255) NOT NULL,
    middlename CHARACTER VARYING(255),
    position CHARACTER VARYING(255) NOT NULL
);

-- Table: project

CREATE TABLE project
(
    id IDENTITY NOT NULL PRIMARY KEY,
    name CHARACTER VARYING(255) NOT NULL,
    shortname CHARACTER VARYING(255) UNIQUE,
    description CHARACTER VARYING(4000)
);

-- Table: task

CREATE TABLE task
(
    id IDENTITY NOT NULL PRIMARY KEY,
    name CHARACTER VARYING(255) NOT NULL,
    begindate DATE NOT NULL,
    enddate DATE NOT NULL,
    work INTEGER NOT NULL,
    projectid INTEGER NOT NULL,
    status CHARACTER VARYING(255) NOT NULL,
    employeeid INTEGER NOT NULL,
    CONSTRAINT fk_projectid FOREIGN KEY (projectid)
        REFERENCES project (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_employeeid FOREIGN KEY (employeeid)
        REFERENCES employee (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);