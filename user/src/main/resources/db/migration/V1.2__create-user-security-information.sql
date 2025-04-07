CREATE TABLE user_security_information (
    id UUID PRIMARY KEY REFERENCES user_primary_key(id),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);