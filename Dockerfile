FROM debian:trixie

USER root

RUN apt update && apt install -y openjdk-21-jdk unzip wget curl telnet iproute2 && useradd -m -s /bin/bash usuario

USER usuario
WORKDIR /home/usuario

RUN wget https://public.dhe.ibm.com/ibmdl/export/pub/software/openliberty/runtime/release/26.0.0.2/openliberty-jakartaee10-26.0.0.2.zip \
    && unzip openliberty-jakartaee10-26.0.0.2.zip \
    && wget -O /home/usuario/wlp/lib/postgresql-42.7.9.jar https://jdbc.postgresql.org/download/postgresql-42.7.9.jar \
    && /home/usuario/wlp/bin/server create app --template=jakartaee10 \
    && mkdir -p /home/usuario/wlp/usr/servers/app/configDropins/defaults \
    && echo '<?xml version="1.0"?><server description="Driver JDBC para Postgres"><jdbcDriver id="postgresql-driver" javax.sql.DataSource="org.postgresql.ds.PGSimpleDataSource" javax.sql.ConnectionPoolDataSource="org.postgresql.ds.PGConnectionPoolDataSource" libraryRef="postgresql-library"/><library id="postgresql-library"><fileset id="PostgresqlFileSet" dir="${wlp.install.dir}/lib" includes="postgresql-42.7.9.jar"/></library></server>' > /home/usuario/wlp/usr/servers/app/configDropins/defaults/postgres-driver.xml \
    && echo '<?xml version="1.0" encoding="UTF-8"?><server description="new server"><featureManager><feature>jakartaee-10.0</feature><feature>microProfile-7.1</feature></featureManager><basicRegistry id="basic" realm="BasicRealm"></basicRegistry><httpEndpoint id="defaultHttpEndpoint" host="0.0.0.0" httpPort="9080" httpsPort="9443"/><applicationManager autoExpand="true"/><ssl id="defaultSSLConfig" trustDefaultCerts="true"/><dataSource id="AdmisionDataSource" jndiName="jdbc/admision" jdbcDriverRef="postgresql-driver" type="javax.sql.ConnectionPoolDataSource" transactional="true"><properties serverName="${PGSERVER}" databaseName="${PGDBNAME}" portNumber="${PGPORT}" user="${PGUSER}" password="${PGPASSWORD}" stringType="unspecified"/></dataSource></server>' > /home/usuario/wlp/usr/servers/app/server.xml

EXPOSE 9080
CMD ["/home/usuario/wlp/bin/server", "run", "app"]