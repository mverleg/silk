Silk
===============================

Silk is format for relational database schemas.

* It is portable across database 'brands'
* It uses JSON (or compatible formats, like MessagePack)
* Structure is described with [json-schema](https://json-schema.org/) and is versioned.

Status
-------------------------------

Version 0.1 of the schema, and accompanying Java gson objects, are available. However, this is still experimental, and there is little tooling support yet.

Uses
-------------------------------

It could be used for:

* Query building and checking
* Code generation (e.g. create value src.objects to hold data from the database)
* Test data generation

It is _not_ intended for:

* Data storage - only the schema (structure of data) is stored
* Being completely comprehensive (i.e. no triggers or stored procedures)
* Backups

Tools
-------------------------------

* [silk_sql_gen](https://github.com/mverleg/silk_sql_gen) - Generate the sql to construct the Silk schema for different databases


