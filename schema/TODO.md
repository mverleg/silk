
Plans for extensions
===============================

To run: ``(cd generate/java/ && mvn install exec:java) && (cd parse/java/ && mvn install exec:java)``

Known issues
-------------------------------

* Maven does not run tests

Before v1.0
-------------------------------

* Only allow one of 'default_value' and 'auto_value'
* Nullable defaults to false when 'default_value' or 'auto_value' is used
* Literals should match the column type

Non-breaking
-------------------------------

* v0.2.0

  - Support for reference data
  - Support for indices

* Later

  - Make a semi-stable stucture that 1) does validation 2) adds more data 3) is more stable for new schema versions

Breaking
-------------------------------

* Re-introduce short-column format (short_column) when pojo generation works.
* Upgrade to newer json-schema version, when all tools used support it.

