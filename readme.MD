# Market Place Spring boot Rest API

This project uses:
- Postgress as DB, if you don't have it installed, please check how to install it


## Steps to use the project
1. Make sure you have postgress installed.
2. Open Postgress (pgAdmin), create a DB named `market-place`
3. Run the schema.sql and data.sql scripts
4. DB set!


## How to install Postgress
Check the official website --> https://www.postgresql.org/
Download the installer and follow instructions.
Check that it is installed by:
looking for PgAdmin and SQL Shell in programs


### What's Spring?

- Structure that let us work in a better and simpler way
    - Less code
    - Best practices
    - Less time
- It delegates to the framework repetitive tasks
- Spring is modular and flexible

###  Used dependencies

- Spring framework --> let us create business applications, it is kind of the core
- Spring boot --> Let us work with self-contained and auto-configurable applications
- Spring Data (JPA) --> Let us work easily with DBs. it is an Umbrella that has other projects. (spring Data Kassandra,
  Spring Data MongoDB)
- Spring Security --> Self explanatory

###  Concepts

####  Spring is Self-contained

- It has its own server and configuration
  <img src="./self-contained-app.png" alt="self-contained"/>

- Advantages:
- We can forget about the infrastructure and focus on development.
    - We delegate dependency configuration
    - Application server
- It works with several embbeded application servers Tomcat(by default), Jetty or Undertow.
- Includes initial dependencies using maven or gradle.


####  What's JPA?
- It is an ORM specification.
- `ORM` A set of rules that Java defines, so that, any framework that wants to interact with a
DB has to follow.
  Examples:
    - Hibernate
    - EplipseLink
    - TopLink
    - ObjectDB
  
JPA Annotation to connect classes to tables:
  - @Entity  -> represents a table
  - @Table  -> receives the name of the table
  - @Column  -> Not required, only when the name of the column is different in DB
  - @Id & @EmbededId  -> primary key, @Id for simple keys, @EmbededId Compound keys
  - @GeneratedValue  -> generates values for primary keys
  - @OneToMany & @ManyToOne  -> To create Entity relations

### What's Spring Data
- A project that uses JPA.
- Used to optimize repetitive tasks
- Repositories without code as
    - CrudRepository 
    - PagingAndSortingRepository = CRUD + Pagination and Sorting
    - JPARepository = PagingAndSortingRepository + some additional features such as Flush.
- Let us do CRUD operations without one single line of code.
- Let us save a lot of coding.
- They generate operations to the DB without code.

### Query Methods
- Used when Spring Data doesn't provide what we need.
- They can return `Optional<T>`
- They follow a special syntax  
- Example **findBy**IdCategoria**OrderBy**Nombre**Asc**(int idCategoria);

## @Repository and @Component
- Let Spring know that this is the layer that interacts with the DB.
- @Repository is a specific type of @Component

## Data Mapper pattern
- It consists in translate 2 object that can do the same job.
- Benefits:
  - It doesn't expose the DB in the API
  - It decouples the API from a specific DB
  - It prevents unnecessary fields in the API
  - IT prevents mixing languages in the app.

In this project we use MapStruct to achieve the goal of orienting our API to domain

    implementation 'org.mapstruct:mapstruct:1.4.2.Final' 
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'

## Dependency Inyection
- One of the 5 SOLID principles.
- Single Responsability
- Open / Closed
- Liskov substitution
- Interface segregation 
- Dependency inversion --> It is to pass the dependency to the class that needs it rather than creating it inside the class.
  That way you decouple your classes to the implementation it is using.
- Inversion of Control (IoC)--> A framework has the control over the objects. In Spring they are called `beans`

@Autowired is the annotation SpringBoot uses to inject dependencies.

## Architecture of the project
- N-layer Domain oriented.
  <img src="./architecture.png" alt="architecture"/>

### Layer 0 (Domain)
- DTO (Domain Objects) --> Objects of a specific domain, in this case a market
- Services --> They are a bridge between the controllers and the repository
- Repository specification --> They define the rules (contracts) that the persistence should follow 

### Layer 1 (Web)
- It has the @Controllers

### Layer 2 (Persistence)
- Entity repositories

## Services in Spring
- They mediate between the controller of the API and the repository


## Notes

Maven --> handles dependencies with xml. Gradle --> handles dependencies with groovy.


#Useful links

(Reference for application.properties)[https://docs.spring.io/spring-boot/docs/1.1.6.RELEASE/reference/html/common-application-properties.html]

## Notes in Spanish
Podríamos incluir Flyway para versionar la base de datos y mantener la consistencia sin importar si ejecutamos la aplicación en desarollo o producción.

Con solo incluir la dependencia de flyway en el proyecto, el starter de spring boot data jpa se encarga de autoconfigurarlo.

    dependencies {
    
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
        implementation 'org.springframework.boot:spring-boot-starter-web'
        implementation 'org.flywaydb:flyway-core:7.0.0'
    
        runtimeOnly 'org.postgresql:postgresql'
    
        testImplementation('org.springframework.boot:spring-boot-starter-test') {
            exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
        }
    }

Dentro de resources creamos la estructura de carpetas como se muestra en la imagen y un archivo donde pegaremos el contenido de schema.sql

flyway.PNG
Ya solo bastaría agregar una clase para decirle a flyway que ejecute todos los scripts que encuentre en la carpeta db/migration

    @Configuration
    public class DatabaseConfig {
    
        @Bean
        public FlywayMigrationStrategy migrationStrategy() {
            return flyway -> {
                flyway.repair();
                flyway.migrate();
            };
        }
    }

Al correr la aplicación veremos que se genera una tabla que lleva el control de las versiones de la base de datos llamada flyway_schema_history.

Si necesitamos cambiar la estructura de la base de datos ya solo agregaremos archivos .sql en la carpeta db/migration y flyway se encargará de ejecutarlos automáticamente. Así mantenemos la consistencia en todos los ambientes donde ejecutemos la aplicación sin tener que correr los scripts manualmente.