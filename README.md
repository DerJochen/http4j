# http4j
Abstraction layer project for HTTP client calls and two adapters for external projects.

## Maven

### Dependency Tag

#### Facade project

```xml
<dependency>
    <groupId>de.jochor.lib.http</groupId>
    <artifactId>http4j</artifactId>
    <version>0.2.2</version>
</dependency>
```

#### Adapter implementation for Apache HttpComponents v4.5

```xml
<dependency>
    <groupId>de.jochor.lib.http</groupId>
    <artifactId>http4j-apache</artifactId>
    <version>0.2.2</version>
</dependency>
```

#### Adapter implementation for jUnit v4.12

```xml
<dependency>
    <groupId>de.jochor.lib.http</groupId>
    <artifactId>http4j-junit</artifactId>
    <version>0.2.2</version>
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
