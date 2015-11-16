# http4j
Abstraction layer project for HTTP client calls and two adapters for external projects.

If you have multiple adapter projects in your classpath you can select a specific one by setting the Java system propert `jochor.servicefactory.de.jochor.lib.http4j.StaticHTTPClientBinder` to the full qualified name of the service class.

```java
System.setProperty("jochor.servicefactory.de.jochor.lib.http4j.StaticHTTPClientBinder", "de.jochor.lib.http4j.junit.HTTPClientJUnit");
```

Normally this is only necessary if you need different implementation during testing and runtime.

## Maven

### Dependency Tag

#### Facade project

```xml
<dependency>
    <groupId>de.jochor.lib.http</groupId>
    <artifactId>http4j</artifactId>
    <version>0.3.1</version>
</dependency>
```

#### Adapter implementation for Apache HttpComponents v4.5

```xml
<dependency>
    <groupId>de.jochor.lib.http</groupId>
    <artifactId>http4j-apache</artifactId>
    <version>0.3.1</version>
</dependency>
```

#### Adapter implementation for jUnit v4.12

```xml
<dependency>
    <groupId>de.jochor.lib.http</groupId>
    <artifactId>http4j-junit</artifactId>
    <version>0.3.1</version>
</dependency>
```

### Repository Tag

#### Releases

```xml
<repository>
	<id>jochor-public-releases</id>
	<url>http://maven.jochor.de/content/repositories/public-releases/</url>
</repository>
```

#### Snapshots

```xml
<repository>
	<id>jochor-public-releases</id>
	<url>http://maven.jochor.de/content/repositories/public-snapshots/</url>
</repository>
```
