Rabobank receives monthly deliveries of customer statement records. This information is delivered in JSON Format.

For building and running the application you need at least:

JDK 1.8
Maven 3
DB H2
Lombok

Rest Service Demonstrations:

Implemented REST Service which receives the customer statement as JSON as a POST data and validation is place as below:
1.	All Transactions will be unique.
2.	The end balance validated (start balance +/- mutation = end balance)

Validation Logic:

((Start Balance + Mutation = End Balance) || (Start Balance - Mutation = End Balance)) && !hasDuplicateTxn  ---> Save Transaction Reference Record

((Start Balance + Mutation = End Balance) || (Start Balance - Mutation = End Balance)) && hasDuplicateTxn  ---> Throw error and show custom error response

((Start Balance + Mutation != End Balance) || (Start Balance - Mutation != End Balance)) && !hasDuplicateTxn  ---> Throw error and show custom error response

((Start Balance + Mutation != End Balance) || (Start Balance - Mutation != End Balance)) && hasDuplicateTxn  ---> Throw error and show custom error response along with duplicate reference


I've used ControllerAdvice for exception handling.



Post End Point URL: http://localhost:8080/transaction/save

Case 1 : When NO Duplicate reference & Correct End Balance
![Successful_case](https://user-images.githubusercontent.com/41251660/129365153-2bb515cc-9eee-4e1d-8e3d-0d89e5cd3ede.png)

Case 2: When Duplicate reference (11111111) and Correct End Balance 
![When Duplicate reference (11111111) and Correct End Balance](https://user-images.githubusercontent.com/41251660/129365332-5379d2e7-a300-4f24-9f43-4e9d4532ebfe.png)

Case 3: When No Duplicate and Incorrect end Balance
![When No Duplicate and Incorrect end Balance](https://user-images.githubusercontent.com/41251660/129365426-0ff8207a-c20e-4717-b8d3-e4c50f816f3c.png)

Case 4: When Duplicate reference and Incorrect end balance

![When Duplicate reference and Incorrect end balance](https://user-images.githubusercontent.com/41251660/129365496-f9fa80d2-2364-48d9-94b1-5cc237ac31f1.png)

Case 5: Error during parsing JSON (BAD Request)
![bad_request](https://user-images.githubusercontent.com/41251660/129365567-b1f14134-2867-43ee-ada1-74e609238ebc.png)

Case 6: Any other situation (Internal server error)
Eg. Trying to fetch a transaction reference(1000) which is not exist
![internal_server_error](https://user-images.githubusercontent.com/41251660/129365643-98e0c4e7-1174-41dc-8c91-2d7cc8e1bdf0.png)

Integration Test Results:

![IntegrationTestResult](https://user-images.githubusercontent.com/41251660/129392382-3c99099c-448d-4f2e-a17c-13af589b64b1.JPG)

Unit Test Result:

![JunitTestResult](https://user-images.githubusercontent.com/41251660/129392510-9bc4c59d-2600-4a2a-a517-d79d34f43c40.JPG)


