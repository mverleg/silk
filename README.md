Silk
===============================

Silk is format for relational database schemas.

* It is portable across database 'brands'
* It uses JSON (or compatible formats, like MessagePack)
* Structure is described with [json-schema](https://json-schema.org/) and is versioned.

Status
-------------------------------

This project is far from usable!

Uses
-------------------------------

It could be used for:

* Code generation (e.g. create value objects to hold data from the database)
* Test data generation

It is _not_ intended for:

* Data storage - only the schema (structure of data) is stored
* Being completely comprehensive (i.e. no stored procedures)
* Backups

