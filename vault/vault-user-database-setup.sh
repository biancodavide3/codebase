# https://developer.hashicorp.com/vault/docs/secrets/databases/postgresql
# https://www.postgresql.org/docs/current/sql-createuser.html

# docker cp ./vault-user-database-setup.sh vault_server:/
# docker exec -it vault_server sh
# chmod u+x /vault-user-database-setup.sh
# /vault-user-database-setup.sh

# Login
export VAULT_ADDR=http://localhost:8200
vault login myroot

# Enable database backend (if it's not enabled)
vault secrets enable database

# Create role for userdb
vault write database/config/userdb \
    plugin_name="postgresql-database-plugin" \
    allowed_roles="userdb" \
    connection_url="postgresql://{{username}}:{{password}}@user_db:5432/userdb" \
    username="vaultuser" \
    password="vaultpass" \
    password_authentication="scram-sha-256"

# Configure the user creation statement for userdb role (select, insert, update permissions)
vault write database/roles/userdb \
    db_name="userdb" \
    creation_statements="CREATE USER \"{{name}}\" PASSWORD '{{password}}' VALID UNTIL '{{expiration}}'; \
        GRANT SELECT, INSERT, UPDATE ON ALL TABLES IN SCHEMA public TO \"{{name}}\"; \
        GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO \"{{name}}\";" \
    default_ttl="1h" \
    max_ttl="24h"

# NOTE with flyway there is actually no need to generate dynamic credentials as flyway's connection is ephemeral