# X - Men Mutant Detector

### How to run

To run the application in a local environment you can use the following command in the main folder, a MongoDB replica set should be set up before

````
./gradlew bootRun
````

### Coverage report

To generate a coverage report you can use the command

````
./gradlew jacocoTestReport
````

A report will be generated in the folder ```/build/jacocoHtml/index.html```

### Resources

The following resources are available:

* [Swagger documentation](http://localhost:8080/mercadolibre/swagger-ui.html)
* [OpenApi JSON documentation](http://localhost:8080/mercadolibre/v3/api-docs/)
  
A postman collection with the endpoints can be found in the ```postman``` folder

