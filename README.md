Restlet-Guice Connector
=======================

This simple set of connector classes allows Guice dependency injection to be used for Restlet resources and allows those resources to be bound to URI routing patterns within Guice modules.  This helps in the creation of very modular (and loosely coupled) server components.

Example
-------
When creating Restlet ServerResources I really want to do something like this:
```java
public class HelloResource extends ServerResource {
	
	@Inject
	public HelloResource(IHelloServiceProvider service, @Named("Greeting") String greeting) {
	...
	}
	
	...
}
```
and have Guice inject my dependencies on an IHelloServiceProvider implementation and a configuration string tagged "Greeting" for me.

Moreover, I want to bind my resources to URIs using the same Guice modules that I use to bind dependencies (just like the guice-servlet extension lets me do with plain old javax servlets) like this:
```java
public class MyRoutingModule extends AbstractModule {
	protected void configure() {
		...
		resourceBinder.addBinding("/greet/{name}").toInstance(HelloResource.class);
		...
	}
}
```

This small set of classes handles the boilerplate to do so.  More to come.