-- docker cp ./setup.sql user_db:/
-- docker exec -it user_db bash
-- psql -U postgres
-- \i /setup.sql

-- Create db
CREATE DATABASE userdb;

-- TODO these 2 should have fine-grained permissions
-- Create flyway user
CREATE USER flyway WITH PASSWORD 'password' SUPERUSER;

-- Create vault user
CREATE USER vaultuser WITH PASSWORD 'vaultpass' SUPERUSER;