DROP TABLE Users;
DROP TABLE Projects;
DROP TABLE Fields;
DROP TABLE Batches;
DROP TABLE Records;
DROP TABLE RecordValues;

CREATE TABLE Users(
	UserID integer PRIMARY KEY AUTOINCREMENT,
	UserName varchar(255) NOT NULL,
	Password varchar(255) NOT NULL,
	LastName varchar(255) NOT NULL,
	FirstName varchar(255) NOT NULL,
	Email varchar(255),
	CurrentBatch integer,
	IndexedRecords integer DEFAULT 0
);

CREATE TABLE Projects(
	ID integer PRIMARY KEY AUTOINCREMENT,
	Title varchar(255) NOT NULL,
	RecordsPerImage integer,
	FirstYCoordinate integer,
	RecordHeight integer
);

CREATE TABLE Fields(
	ID integer PRIMARY KEY AUTOINCREMENT,
	ProjectID integer,
	OrderID integer,
	Title varchar(255),
	XCoordinate integer,
	Width integer,
	HelpFile varchar(255),
	KnownData varchar(255)
);

CREATE TABLE Batches(
	ID integer PRIMARY KEY AUTOINCREMENT,
	ImageURL varchar(255),
	ProjectID integer,
	Complete boolean DEFAULT false,
	AssignedUserID integer,
	RecordNum integer
);

CREATE TABLE Records(
	ID integer PRIMARY KEY AUTOINCREMENT,
	BatchID integer DEFAULT false,
	OrderID integer
);

CREATE TABLE RecordValues(
	ID integer PRIMARY KEY AUTOINCREMENT,
	FieldID integer,
	RecordID integer,
	Value varchar(255)
); 