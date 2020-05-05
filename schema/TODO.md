
Plans for extensions
===============================

Known issues
-------------------------------

Before v1.0
-------------------------------

* Only allow one of 'default_value' and 'auto_value'
* Nullable defaults to false when 'default_value' or 'auto_value' is used
* Literals should match the column type

Non-breaking
-------------------------------

* Support for reference data
* Support for indices

Breaking
-------------------------------

* Re-introduce short-column format (short_column) when pojo generation works.
* Upgrade to newer json-schema version, when all tools used support it.

