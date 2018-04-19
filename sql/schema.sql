create DATABASE sensor_db;

CREATE EXTENSION pgcrypto;

CREATE TABLE subjects (
id serial PRIMARY KEY,
name text unique NOT NULL
);

INSERT INTO subjects(name)
VALUES
('floor 1'),
('floor 2'),
('roof');

CREATE TABLE sensors (
id serial PRIMARY KEY,
subject_id INTEGER REFERENCES subjects(id),
name text unique NOT NULL,
min decimal NOT NULL DEFAULT -25.0,
max decimal NOT NULL DEFAULT 50.0,
enabled boolean NOT NULL DEFAULT true
);

INSERT INTO sensors(subject_id, name, min, max, enabled)
VALUES
(1, 'west side', -10.0, 40.0, true),
(1, 'east side', -10.0, 40.0, true),
(2, 'north side', -5.0, 50.0, true),
(2, 'south side', -5.0, 50.0, false),
(3, 'tower 1', -20.0, 50.0, true),
(3, 'tower 2', -20.0, 50.0, false);

CREATE TABLE sensor_events (
id serial PRIMARY KEY,
sensor_id INTEGER REFERENCES sensors(id),
utc TIMESTAMP without time zone NOT NULL,
value decimal NOT NULL DEFAULT 0.0
);

insert into sensor_events(sensor_id, utc, value)
values
(1, now(), 14.8),
(1, now(), 16.5),
(2, now(), 20.1),
(2, now(), 22.4),
(3, now(), 18.3);