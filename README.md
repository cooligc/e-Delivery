# e-delivery

<a href="https://travis-ci.org/cooligc/e-Delivery"><img src="https://travis-ci.org/cooligc/e-Delivery.svg?branch=master"/></a>
# Technology used :
---
- Spring (Spring-core,Spring-MVC,Spring-Security,Spring-data-mongodb,Spring-Scheduler) 
- Thymeleaf 
- Mongo DB 
- jQuery 
- Bootstrap CSS 
 

# Prerequisite Software to be installed
---
 
- Java 7 or above 
- Maven 3 
- Application Server Like Tomcat or jBoss 
- Mongo DB 2.4 or above 
 
# How to run the application ?
---
 
- Build the Application using maven (mvn clean install) 
- Run mongodb with default port (27017) (We can run, mongo on any port for that we need to change the port in mongo-config.xml file) 
- Deploy the war in the Apllication Server 
 
Now Test the Application by putting <code>http://localhost:8080</code> on your browser

# Dummy Credit Card Details to be used while placing Order
---
<table>
<tr>
  <th>Credit Card Type</th>
  <th>Credit Card Number</th>
</tr>
<tr>
  <td>MasterCard	</td>
  <td>5555555555554444</td>
<tr>
   <td>MasterCard	</td>
   <td>5105105105105100</td>
<tr>
  <td>Visa	</td></td>
  <td>4111111111111111</td>
<tr>
  <td>Visa	</td>
  <td>4012888888881881</td>
</tr>
</table>
