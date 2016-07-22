# Micro Profile - Samples

## Purpose of these samples

Give different samples on the Micro Profile : 

* **Canonical** Simplest sample using JAX-RS / CDI / JSon-P 
* **Swagger** Adding Swagger to the canonical sample 

## Testing

These examples use Arquillian Tests. By default, Arquillian uses WildFly embedded, but other forms of containers can also be plugged in.
 
* `mvn clean test` (or `mvn clean test -Parquillian-wildfly-managed`)  tests against an embedded WildFly 
* `mvn clean test -Parquillian-wildfly-remote` tests against a running WildFly 
