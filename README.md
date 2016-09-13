# Micro Profile - Samples

## Purpose of these samples

Give different samples on the Micro Profile : 

* **Canonical** Simplest sample using JAX-RS 2.0 / CDI 1.2 / JSon-P 1.0
* **Swagger** Adding Swagger to the canonical sample 

## Building

To build these examples you can just :

* `mvn clean install` does not run any test
* `mvn clean install -Plicense,skipTests` checks the licenses and does not run any test

## Testing 

These examples use Arquillian Tests. By default, Arquillian uses WildFly embedded, but other forms of containers can also be plugged in.
 
* `mvn clean test -Pwildfly-swarm`  tests against a WildFly Swarm
* `mvn clean test -Parquillian-wildfly-managed`  tests against an embedded WildFly
* `mvn clean test -Parquillian-wildfly-remote` tests against a running WildFly
* `mvn clean test -Parquillian-tomee-managed`  tests against an embedded Apache TomEE
* `mvn clean test -Parquillian-tomee-remote` tests against a remote Apache TomEE

## Running
 
 You can package a war file and deploy it on your application server. Once deployed and up and running, just go to `http://localhost:<portNumber>/msTopCDs` to invoke the microservice. You should get a list of random "Top Rated CDs" in JSon format, such as :
 
    [
        {
            id: 1101
        },
        {
            id: 1147
        },
        {
            id: 1112
        },
        {
            id: 1132
        },
        {
            id: 1168
        }
     ]
  
  But you can also use other forms of packaging to execute this services
   
### WildFly Swarm
   
Package the samples with WildFly Swarm using the following Maven command :    

* `mvn clean package -Pwildfly-swarm` 

This will create an executable Jar under the `target` directory. Just execute it with `java -jar target/microprofile-sample-canonical-swarm.jar` and invoke the microservice at http://localhost:8081/msTopCDs
