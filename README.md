<img src="https://gap.surati.io/img/logo.png" width="64px" height="64px"/>

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/gap-enterprise/admin-base)](http://www.rultor.com/p/gap-enterprise/admin-base)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Javadoc](http://www.javadoc.io/badge/io.surati.gap/admin-base.svg)](http://www.javadoc.io/doc/io.surati.gap/admin-base)
[![License](https://img.shields.io/badge/License-Surati-important.svg)](https://github.com/gap-enterprise/admin-base/blob/master/LICENSE.txt)
[![codecov](https://codecov.io/gh/gap-enterprise/admin-base/branch/master/graph/badge.svg)](https://codecov.io/gh/gap-enterprise/admin-base)
[![Hits-of-Code](https://hitsofcode.com/github/gap-enterprise/admin-base)](https://hitsofcode.com/view/github/gap-enterprise/admin-base)
[![Maven Central](https://img.shields.io/maven-central/v/io.surati.gap/admin-base.svg)](https://maven-badges.herokuapp.com/maven-central/io.surati.gap/admin-base)
[![PDD status](http://www.0pdd.com/svg?name=gap-enterprise/admin-base)](http://www.0pdd.com/p?name=gap-enterprise/admin-base)

Base module for `Administration`
It contains all business objects needed to manage users, profiles, access rights etc.

# Generate jOOQ classes
After adding new files to liquibase folder, we have to run the maven profile `jooqGen` like this:
```shell
mvn clean compile -PjooqGen
```
