# http4j
Abstraction layer project for HTTP client calls and two adapters for external projects.

## Motivation
In a current project I need an abstraction layer for HTTP client calls. I also want to easily replace the client implementation to unit test my project.

## But why?
The project where this is needed itself is a library. So it should be easily reusable and bring no extra baggage.

Say you have a project and use library A for HTTP client calls. Now you add a new dependency to a library for a REST service you want to use - and this library depends on HTTP client library B. You end up with two dependencies who do exactly the same thing. Who needs that?

If the REST library would just depend on an API project you could use an existing adapter implementation for library A or quickly write one yourself. Problem solved.

## Usage
Since this project is currently intended for my *java-lib-for-wunderlist* project, I keep the usage chapter very short. It basicly works like *log4j* or *slf4j*. You depend on the facade project (see **Maven**) and only use the `HTTPClient` interface for HTTP calls.

The field initialization looks like this:

```java
private HTTPClient httpClient = HTTPClientFactory.create();
```

During compile time this code is perfectly fine. When running the program a concrete implementation of the `HTTPClient` interface is needed. For that you additionally depend on an adapter implementation (e.g. *http4j-apache*). In your library project you do not want to have a *compile* dependency to an adapter implementation, but with a *test* dependency you actually can run and test your code.

If you have multiple adapter projects in your classpath you can select a specific one by setting the Java system property `jochor.servicefactory.de.jochor.lib.http4j.StaticHTTPClientBinder` to the full qualified name of the service class.

```java
System.setProperty("jochor.servicefactory.de.jochor.lib.json4j.StaticHTTPClientBinder", "de.jochor.lib.http4j.junit.HTTPClientJUnit");
```

Normally this is only necessary if you need different implementation during testing and runtime.

## Maven

### Dependency Tag

#### Facade project

```xml
<dependency>
    <groupId>de.jochor.lib.http</groupId>
    <artifactId>http4j</artifactId>
    <version>0.3.2</version>
</dependency>
```

#### Adapter implementation for Apache HttpComponents v4.5

```xml
<dependency>
    <groupId>de.jochor.lib.http</groupId>
    <artifactId>http4j-apache</artifactId>
    <version>0.3.2</version>
</dependency>
```

#### Adapter implementation for jUnit v4.12

```xml
<dependency>
    <groupId>de.jochor.lib.http</groupId>
    <artifactId>http4j-junit</artifactId>
    <version>0.3.2</version>
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
