CREATE TABLE person
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL CHECK (TRIM(name) != ''),
    age INTEGER NOT NULL CHECK (age >= 0),
    address TEXT NOT NULL CHECK (TRIM(address) != ''),
    work TEXT NOT NULL CHECK (TRIM(work) != '')
);
