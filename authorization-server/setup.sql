-- docker cp ./setup.sql authorization-server-db:/
-- docker exec -it authorization-server-db bash
-- psql -U postgres
-- \i /setup.sql

-- Create db
CREATE DATABASE authorization_db;
CREATE DATABASE authorization_db_test;

-- Create flyway user
CREATE USER flyway WITH PASSWORD 'password' SUPERUSER;