CREATE TABLE People
(
    person_id   INTEGER PRIMARY KEY,
    name        text,
    age         INTEGER,
    has_license Boolean
);

CREATE TABLE Cars
(
    car_id INTEGER PRIMARY KEY,
    brand  text,
    model  text,
    price  NUMERIC
);

CREATE TABLE People_Car
(
    person_id INTEGER,
    car_id    INTEGER,
    PRIMARY KEY (person_id, car_id),
    foreign key (person_id) REFERENCES People (person_id),
    foreign key (car_id) references Cars (car_id)
);

select People.person_id, People.name, People.age, People.has_license
FROM People_Car
         INNER JOIN People ON People_Car.person_id = People.person_id
         INNER JOIN Cars ON Cars.car_id = People_Car.car_id