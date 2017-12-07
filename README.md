# Scrubber - vertx-event-bus

Sample application built in Vertx Reactivex, Event Bus, Ok HttpClient and Mongo to test the APIs on the fly. Future releases will have mongo support and health check of the apis.

## Getting Started

Git clone the project on your local machine and import it to your favorite ide.

### Prerequisites

For runnning this, you will need
- Java 1.8
- Gradle support - In Eclipse editor, goto help -> eclipse marketplace -> search for buildship (buildship gradle integration) and install it.
- Mongo should be running

## Brief

This application tests the API that you want to test and pass the response back with the api result.
- **ScrubberLauncher**        -> The starting point of the application. It is used to set the app configuration.
- **MainVerticle**            -> Main verticle deploys the HTTPServerVerticle, DatabaseVerticle and RequestVerticle.
- **RequestHandler**          -> Rest Handler which receives the input, calls the actual API service and returns the Json response.

Event bus is used to pass messages between HTTPServerVerticle and RequestVerticle;

## Running the app

For running the app, (IDE used here is Eclipse)
- Open **scrubberConfig.json** file and set the "http_server_port" as per your choice. No need to change other values.
- Once, changes are done in **scrubberConfig.json**, right click on the project("scrubber"), <br />select "Run As" -> "Run Configurations". Set:
  * **Main**: com.api.scrubber.launcher.ScrubberLauncher
  * **Program arguments**: <br />run com.api.scrubber.verticle.MainVerticle -conf ../vertx-event-bus/src/main/resources/scrubberConfig.json
  * **VM arguments**: -Dlogback.configurationFile=file:../vertx-event-bus/src/main/resources/logback.xml <br />
After setting the variables, click "Run".
- If app starts successfully, goto **http://localhost:8080/scrubber/api/ping/do**. Status json {"code":200,"message":"success","hasError":false,"data":{"status":"OK"}} will be served as response.
- To call the request handler, do <br />
**Type:** *POST http://localhost:8080/scrubber/api/request/do* <br />
**Headers:** *Content-Type: application/json* <br />
             *X-CORRELATION-ID: anystring* <br />
**Data to send:** *json data below*
```
{
  "url": "https://reqres.in/api/users?page=2",
  "httpMethod": "GET",
  "headers": {
    "Cache-Control": "no-cache",
    "Content-Type": "application/json",
    "cookie": "__cfduid=d203e2f438bbf4a363e7bde97e932b3fc1511333689; _ga=GA1.2.1456264098.1511333540; _gid=GA1.2.1900156045.1511333540; _gat=1"
  }
}
```
* Response would be: <br />
```
{
    "code": 200,
    "message": "success",
    "hasError": false,
    "data": {
        "page": 2,
        "per_page": 3,
        "total": 12,
        "total_pages": 4,
        "data": [
            {
                "id": 4,
                "first_name": "Eve",
                "last_name": "Holt",
                "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg"
            },
            {
                "id": 5,
                "first_name": "Charles",
                "last_name": "Morris",
                "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/stephenmoon/128.jpg"
            },
            {
                "id": 6,
                "first_name": "Tracey",
                "last_name": "Ramos",
                "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/bigmancho/128.jpg"
            }
        ],
        "status": 200,
        "timeTakenMS": 632,
        "Content-Type": "application/json; charset=utf-8",
        "response_headers": {
            "date": "Wed, 06 Dec 2017 12:29:04 GMT",
            "access-control-allow-origin": "*",
            "server": "cloudflare-nginx",
            "cf-ray": "3c8f3fa65ef52828-SJC",
            "x-powered-by": "Express",
            "content-type": "application/json; charset=utf-8",
            "etag": "W/\"1bb-wM9a6JWbwt3JpLNfwoQwxnqaC3Y\""
        }
    }
}
```
## Built With

* [Vertx](http://vertx.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
