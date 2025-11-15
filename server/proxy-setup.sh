#!/bin/sh

# Instalar socat e netcat-openbsd no container Alpine
apk add --no-cache socat netcat-openbsd

# Função para verificar se PostgreSQL está respondendo
check_postgres() {
    nc -z $1 5432 >/dev/null 2>&1
    return $?
}

# Aguardar os PostgreSQL estarem prontos
echo 'Aguardando PostgreSQL primary ficar pronto...'
while ! check_postgres postgres_primary; do
    sleep 2
done

echo 'Aguardando PostgreSQL replica ficar pronto...'  
while ! check_postgres postgres_replica; do
    sleep 2
done

echo 'Ambos PostgreSQL estão prontos. Iniciando proxy...'

# Iniciar proxy com socat - encaminha para primary, se falhar vai para replica
socat TCP-LISTEN:5432,fork,reuseaddr \
      SYSTEM:'
        if nc -z postgres_primary 5432; then
          echo \"Usando PostgreSQL Primary\"
          nc postgres_primary 5432
        else
          echo \"Usando PostgreSQL Replica (failover)\"
          nc postgres_replica 5432
        fi
      ' &

# Manter o container rodando
wait
