
----
Design and implement a REST API using Spring-Boot 2.6.7,Spring Data JPA,JWT Authentication,Lombok,H2 ... **without frontend**.

Voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
   - If it is before 11:00 we assume that he changed his mind.
   - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

-----------------------------
### Testing the server
Start the application and open the URL http://localhost:8080/swagger-ui/index.html for the Swagger-UI.

-----------------------------

