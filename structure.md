# Code structure

The architecture of the Books project is loosely baseed on the Onion Architecture.
See the following articles for a description of this architecture:
- [Onion Architecture](https://medium.com/expedia-group-tech/onion-architecture-deed8a554423)
- [Peeling Back the Onion](https://blog.knoldus.com/onion-architecture/)

This project contains:
1. Domain/Domain Services layer 
2. Application Services layer
3. Common database setup (not part of the application)

## Domain & Domain Services
The _domain_ module represents the domain and business logic layers.  It is database agnostic.
While it does depend on 3rd party libraries (cats, circe, etc.) these dependencies do no bleed out of the module.  For example it uses circe for json encode/decode, but the result is just json.
### Domain Model
The model is split into 4 parts:
- _items_
- _fields_
- _validation_
- _query_

#### Items
The _item_ package in the model holds top level objects such as a book, an author, a publisher etc.  
Each of these top level objects are comprised of _field_ objects, such as an ID, firstName, lastName, price etc.  


#### Fields
The _field_ package in the model holds the fields that comprise the different _item_ objects.  A _field_ can be represented as in json with a _fieldName_ and _value_.  
The _fieldName_ is the json name and the _value_ is the underlying type such as a string, integer, UUID, LocalDate,  etc.  

In addition, each _field_ has validation parameters such as maxLength, minLength for a string.  These validations should also be enforced in the corresponding database layer.
This means that you can't have a FirstName with more characters than the business model has specified or that the underlying datastore can hold.

Each field is typed.  For example a FirstName is a different type as a LastName even though the underlying data type is a string.  This allows for compile-time checking when creating objects.  For example you can't inadvertently switch firstName and lastName when creating an Author.

#### Validation
_Validation_ reads and writes _Item_ objects with their corresponding _fields_ to and from json.  As part of reading from json, validation checks:
1. that the json is valid
2. all required fields in an item are present
3. each field is valid based on the business definitions (firstName does not exceed maxLength for example)

_Validation_ is split out based on the underlying type of the _fieldName_.  For example the same validation code is used for firstName and LastName and a different validation is used for UUID's etc.

#### Query
The _query_ package holds the data objects returned from dao queries.  These objects are also comprised of the same _fields_ as would be found in an _item_,  but since they are only written to json and never read, there is no validation for query objects.

### Domain Services
The _services_ module represents data access for the _items_.  Basic _create, read, update, delete, operations (aka _CRUD) are modeled in the _CrudDao_ and queries specific to a specific item are found in the Dao for each item.

### Domain Config
The _config_ package contains generic configuration code.  This is _not_ contain logic for configuration of specific external services but only contains common code such as Exception definitions.


## db
The _db_ project is used for setting up the database.  
It assumes the database is SQL based and as such contains scripts written in vanilla sql.

It consists of 2 parts:
1. database creation
2. database seeding

### Database Structure
_Database creation/migration_
This is implemented by sql scripts that are run in order by the the flyway library.  
These scripts must have the format Vxxxx__someName.sql where 'xxxx' is the order in which the script should be run.  
This means that once a Vxxxx__someName.sql file has been committed, it should never be changed.  If changes are needed, an additional Vxxxx__someOtherName.sql should be written to make changes.

The flyway library adds an extra table in the database called 'flyway_schema_history'.  This is used to manage which scripts have been run against a particular database instance.

The sbt target is 'flywayMigrate' 

### Database Seed Data
If desired, seed data for any given environment (local, dev, qa, etc.) can be added using sql scripts to insert data.

By convention, these scripts have are named 'xxxx_someName' where 'xxxx' is the order in which the scripts should be run on a new database.
There is no specialized management of these scripts to make sure they are run in the correct order or that they are not run more than once.  This is out of scope for this project.
