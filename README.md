# Reactive GraphQL with Spring [![CI](https://github.com/daggerok/spring-graphql-example/actions/workflows/ci.yaml/badge.svg)](https://github.com/daggerok/spring-graphql-example/actions/workflows/ci.yaml)
This repository demonstrates Spring GraphQL + RSocket + WebFlux + R2DBC + H2

```

 O__                       +-----------+                     +--------+
/|    <~- REST GraphQL -~> | customers | <~- TCP RSocket -~> | orders |
/ \                        +-----------+                     +--------+
```

Start apps:

```bash
./mvnw -f orders compile spring-boot:start
./mvnw -f customers compile spring-boot:start
```

Open http://127.0.0.1:8080/graphiql in a browser

And execute GraphQL query:

```graphql
query {
  customers {
    id
    name
    orders {
      id
      customerId
    }
  }
}
```

Tear down:

```bash
./mvnw -f orders spring-boot:stop
./mvnw -f customers spring-boot:stop
```

## rtfm

For further reference, please consider the following sections:

* [Spring Boot GraphQL](https://docs.spring.io/spring-graphql/docs/1.0.0-M3/reference/html/#boot-graphql)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.0/maven-plugin/reference/html/#build-image)
* [Coroutines section of the Spring Framework Documentation](https://docs.spring.io/spring/docs/5.3.13/spring-framework-reference/languages.html#coroutines)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/2.6.0/reference/html/spring-boot-features.html#boot-features-r2dbc)
* [Acessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)
* [R2DBC Homepage](https://r2dbc.io)
