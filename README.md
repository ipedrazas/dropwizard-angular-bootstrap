# DropAngular: a dropwizard / angular / bootstrap example

Template to create a Dropwizard project that contains:

* REST Api
* Web Aapplication that consumes the REST Api
* [Angularjs](https://angularjs.org/)
* [Bootstrap](http://getbootstrap.com/)
* [Font awesome](http://fortawesome.github.io/Font-Awesome/)
* [Underscore](http://underscorejs.org/)



## Running DropAngular

We run Postgres in Docker

To create your database container execute this command:

        docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres

You will need to create you database, you can use any tool you like to do so, but if you don't have any around... use Docker again:

        docker run -it --link postgres:postgres --rm postgres sh -c 'exec psql -h "$POSTGRES_PORT_5432_TCP_ADDR" -p "$POSTGRES_PORT_5432_TCP_PORT" -U postgres'

Once you're in `PSQL` you just have to create the Database:

        CREATE DATABASE dropangular;

You can verify that the database has been created successfully by typing:

        postgres=# \list
                                  List of databases
            Name     |  Owner   | Encoding |  Collate   |   Ctype    |   Access privileges
        -------------+----------+----------+------------+------------+-----------------------
         dropangular | postgres | UTF8     | en_US.utf8 | en_US.utf8 |
         postgres    | postgres | UTF8     | en_US.utf8 | en_US.utf8 |
         template0   | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
                     |          |          |            |            | postgres=CTc/postgres
         template1   | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
                     |          |          |            |            | postgres=CTc/postgres
        (4 rows)

        postgres=# \quit


Compile your DropAngular app:

        mvn package

Now you have to initialise the database. To do so, we use the `db migrate` command:

    java -jar  target/dropangular-0.0.1-SNAPSHOT.jar db migrate configuration.yml

We should see something like this:

    -> % java -jar  target/dropangular-0.0.1-SNAPSHOT.jar db migrate configuration.yml
        INFO  [2015-01-28 23:00:58,990] liquibase: Successfully acquired change log lock
        INFO  [2015-01-28 23:00:59,498] liquibase: Creating database history table with name: public.databasechangelog
        INFO  [2015-01-28 23:00:59,514] liquibase: Reading from public.databasechangelog
        INFO  [2015-01-28 23:00:59,533] liquibase: migrations.xml: 1::ipedrazas: Table pings created
        INFO  [2015-01-28 23:00:59,536] liquibase: migrations.xml: 1::ipedrazas: ChangeSet migrations.xml::1::ipedrazas ran successfully in 12ms
        INFO  [2015-01-28 23:00:59,551] liquibase: Successfully released change log lock


Now you can run your app by executing the Dropwizard command:

        java -jar  target/dropangular-0.0.1-SNAPSHOT.jar server configuration.yml

