ALTER TABLE Student ADD CONSTRAINT age check ( age > 16 );

ALTER Table student add CONSTRAINT name unique (name);

ALTER TABLE faculty add constraint facultyNameColor unique (name, color);

ALTER TABLE student alter column Age set default 20;
