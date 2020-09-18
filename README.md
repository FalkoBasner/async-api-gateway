Async API Gateway
=================

This project shows how to combine two backend JSON API responses to one combined JSON response.
Processing of the web request and requesting the two API endpoints from the backend is done
in an asynchronous manner.

Building and starting the app
-----------------------------

You will need a Java 8 SDK and at least Maven 3.6.X for building and running the app. 

Go to the projects root folder and build the app with maven.

```
$ mvn clean package
```

Run the app with maven like this
  
```
$ mvn spring-boot:run
```


Usage
-----

This app combines the json of following two api endpoint.

```
http://jsonplaceholder.typicode.com/users/{userId}
http://jsonplaceholder.typicode.com/posts?userId={userId}
```

First api endpoint returns some user data, second returns some posts data an user has written.
 
Have a look at the api result for both endpoints. Lets explore the 
data for one specific user.

```
http://jsonplaceholder.typicode.com/users/1
http://jsonplaceholder.typicode.com/posts?userId=1
```

If you have started the app, use following url to request a 
combined json result for the user.

```
http://localhost:8080/userwithposts/1
````
