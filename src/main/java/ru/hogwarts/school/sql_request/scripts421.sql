ALTER TABLE student ADD CONSTRAINT age check ( age >= 16 );

ALTER TABLE student add CONSTRAINT name unique (name);

ALTER TABLE student ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty add constraint name_and_color_unique_require unique (name, color);

ALTER TABLE student alter column age set default 20;
