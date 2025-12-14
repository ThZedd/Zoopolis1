#!/bin/bash
set -e

# 1. Cria utilizador de replicação
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER replicator WITH REPLICATION ENCRYPTED PASSWORD 'replicator_pass';
EOSQL

# 2. Configura pg_hba.conf (USANDO TRUST PARA SIMPLIFICAR)
# "all" no campo de IP cobre qualquer IP do Docker
echo "host replication replicator all trust" >> "$PGDATA/pg_hba.conf"

# 3. Configurações necessárias
echo "wal_level = replica" >> "$PGDATA/postgresql.conf"
echo "max_wal_senders = 10" >> "$PGDATA/postgresql.conf"