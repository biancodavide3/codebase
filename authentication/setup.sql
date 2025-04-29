-- docker cp ./setup.sql authentication_db:/
-- docker exec -it authentication_db bash
-- psql -U postgres
-- \i /setup.sql

-- Create db
CREATE DATABASE authenticationdb;
CREATE DATABASE authenticationdb_test;

-- Create flyway user
CREATE USER flyway WITH PASSWORD 'password' SUPERUSER;