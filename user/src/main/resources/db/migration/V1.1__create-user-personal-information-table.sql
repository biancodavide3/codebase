-- to add information related to a user create a new table like this one and make
-- relationship with the primary key in user_primary_key table
-- shared primary key model

CREATE TABLE user_personal_information (
    id UUID PRIMARY KEY REFERENCES user_primary_key(id),
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255),
    gender VARCHAR(255),
    date_of_birth DATE
);