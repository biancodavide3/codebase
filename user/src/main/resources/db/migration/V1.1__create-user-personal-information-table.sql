CREATE TABLE user_personal_information (
    id UUID PRIMARY KEY REFERENCES user_primary_key(id),
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255),
    gender VARCHAR(255),
    date_of_birth DATE
);