
Plans for extensions
===============================

To run: ``(cd generate/java/ && mvn install exec:java -Dexec.mainClass=nl.markv.silk.generate.Generate) && (cd parse/java/ && mvn install exec:java -Dexec.mainClass=nl.markv.silk.parse.GsonSilkParser)``

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

  - ?

Breaking
-------------------------------

* Re-introduce short-column format (short_column) when pojo generation works.
* Upgrade to newer json-schema version, when all tools used support it.

