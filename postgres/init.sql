CREATE TABLE person
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL CHECK (TRIM(name) != ''),
    age INTEGER NOT NULL CHECK (age >= 0),
    address TEXT NOT NULL CHECK (TRIM(address) != ''),
    work TEXT NOT NULL CHECK (TRIM(work) != '')
);

CREATE PROCEDURE generate_test_data()
LANGUAGE SQL
AS
$$
    ALTER SEQUENCE person_id_seq RESTART WITH 1;
    INSERT INTO person (name, age, address, work)
    VALUES
        ('Andrew', 19, 'Moscow', 'IT'),
        ('Sergey', 23, 'Rostov', 'Engineering'),
        ('Ivan', 35, 'Saint Petersburg', 'Science'),
        ('Anton', 22, 'Moscow', 'IT'),
        ('Elena', 27, 'Kazan', 'IT');
$$;

CREATE PROCEDURE clear_tables()
LANGUAGE SQL
AS
$$
    TRUNCATE TABLE person;
    ALTER SEQUENCE person_id_seq RESTART WITH 1;
$$;
