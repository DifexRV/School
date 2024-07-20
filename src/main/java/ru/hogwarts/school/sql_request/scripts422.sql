CREATE TABLE people
(
    person_id   INTEGER PRIMARY KEY,
    name        text,
    age         INTEGER,
    has_license Boolean
);

CREATE TABLE cars
(
    car_id INTEGER PRIMARY KEY,
    brand  text,
    model  text,
    price  NUMERIC
);

CREATE TABLE people_Car
(
    person_id INTEGER,
    car_id    INTEGER,
    PRIMARY KEY (person_id, car_id),
    foreign key (person_id) REFERENCES people (person_id),
    foreign key (car_id) references cars (car_id)
);

select people.person_id, people.name, people.age, people.has_license
FROM people_Car
         INNER JOIN people ON people_Car.person_id = people.person_id
         INNER JOIN cars ON cars.car_id = people_Car.car_id