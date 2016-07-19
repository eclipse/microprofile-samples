# Application - CD Book Store MicroServices

* *Author* : [Antonio Goncalves](http://www.antoniogoncalves.org)
* *Level* : Intermediate
* *Technologies* : Java EE 7 (JPA 2.1, CDI 1.1, Bean Validation 1.1, EJB Lite 3.2, JSF 2.2, JAX-RS 2.0), Twitter Bootstrap (Bootstrap 3.x, JQuery 2.x, PrimeFaces 5.x)
* *Application Servers* : WildFly 10, WildFly Swarm
* *Summary* : An e-commerce web application using Java EE 7 and MicroServices

[Download the code from GitHub](https://github.com/agoncal/agoncal-application-cdbookstore-ms)

## Purpose of this application

This e-commerce web app allows you to buy CDs and Books.

The goals of this sample is to :

* use Java EE 7 as back-end microservices 
* give a JSF web user interface (others might come, Angular 2?) 
* make it simple : no complex business algorithm, the point is to bring Java EE 7 technologies together to create an eCommerce website using MicroServices

The only external framework used are [Arquillian](http://arquillian.org/), [Twitter Bootstrap](http://twitter.github.io/bootstrap/) and [PrimeFaces](http://www.primefaces.org/). Arquillian is used for integration testing. Using Maven profile, you can test services, injection, persistence... against different application servers. Twitter Bootstrap and PrimeFaces bring a bit of beauty to the web interface.

To fill up the database, I've used some Amazon Web Services. You will find the raw XML data in the `xml` directory with XSLT transformation (zipped so it's not too big).

## Architecture

The application is divided in several modules: 

* The **CD-Boostore** is the main web app that allows you to buy CDs and Books. It invokes all the following REST services
* **Invoice** is a REST service that creates invoices based on the user's shopping cart
* **TopBooks** is a REST service that calculates the top selling books (JAX-RS + CDI + JSon-P + JPA + Bean Validation)
* **TopCDs** is a REST service that calculates the top selling cds (JAX-RS + CDI + JSon-P)

## Compile, test and package

Being Maven centric, you can compile and package it without tests using `mvn clean compile -Dmaven.test.skip=true`, `mvn clean package -Dmaven.test.skip=true` or `mvn clean install -Dmaven.test.skip=true`. Once you have your war file, you can deploy it.

### Test with Arquillian

Launching tests under [WildFly](http://www.wildfly.org/) is straight forward. You only have to launch WidlFly and execute the tests using the Maven profile :

    mvn clean test -Parquillian-wildfly-remote

## Deploy and execute the application

### War files in a single WildFly

Startup one instance of WildFly :

* `./standalone.sh` (ports 8080 / 9990)

### War files in several WildFly

If you want to execute each application on different WildFly instances, just do :

* `./standalone.sh -c standalone-full.xml -Djboss.socket.binding.port-offset=2` (ports 8082 / 9992)
* `./standalone.sh -c standalone-full.xml -Djboss.socket.binding.port-offset=3` (ports 8083 / 9993)
* `./standalone.sh -c standalone-full.xml -Djboss.socket.binding.port-offset=5` (ports 8085 / 9995)

## Execute the sample

Once deployed go to the following URL and start buying some books and cds: [http://localhost:8080/applicationCDBookStore](http://localhost:8080/applicationCDBookStore).

The admin [REST interface](rs/application.wadl) allows you to create/update/remove items in the catalog, orders or customers. You can run the following [curl](http://curl.haxx.se/) commands :

* `curl -X GET http://localhost:8080/applicationCDBookStore/rs/catalog/categories`
* `curl -X GET http://localhost:8080/applicationCDBookStore/rs/catalog/products`
* `curl -X GET http://localhost:8080/applicationCDBookStore/rs/catalog/items`
* `curl -X GET http://localhost:8085/applicationToprated/toprateditems`

You can also get a JSON representation as follow :

* `curl -X GET -H "accept: application/json" http://localhost:8080/applicationCDBookStore/rs/catalog/items`

## Third Party Tools & Frameworks

### Twitter Bootstrap

When, like me, you have no web designer skills at all and your web pages look ugly, you use [Twitter Bootstrap](http://twitter.github.com/bootstrap/) ;o)

### Silk Icons

I use [Silk Icons](http://www.famfamfam.com/lab/icons/silk/) which are in Creative Commons

### Arquillian

[Arquillian](http://arquillian.org/) for the integration tests.

## Bugs & Workaround


## Licensing

<a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/"><img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.

<div class="footer">
    <span class="footerTitle"><span class="uc">a</span>ntonio <span class="uc">g</span>oncalves</span>
</div>
