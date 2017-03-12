# TodoList

There are two projects, but one of them (TodoList_FirsOne) isn't finished - don't bother with it.

Let's concentrate on the primefaces project.

Build and prepare war:

```
mvn clean install
```

Run wildfly 10.1.0.Final:

```
mvn wildfly:run
```

Primefaces project uses Wildfly's default preconfigured H2 in memory datasource.

Navigate to [http://localhost:8080/todolist/] (http://localhost:8080/todolist/)

That's it.
