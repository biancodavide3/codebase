CREATE TABLE opaque_refresh_token (
    token UUID PRIMARY KEY,
    expiration DATE NOT NULL,
    subject VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);