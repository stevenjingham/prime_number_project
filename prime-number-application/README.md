# Prime Number Application

This Prime Number Application provides a REST API that allows consumers to retrieve all prime numbers, up to and including, a supplied input value.

## Key features

- Java Spring Boot based API
- Single GET endpoint servicing:
  - A PrimesResponse object containing the initial value and a list of prime numbers
  - Request parameter to select the algorithm utilised to generate the prime numbers 
  - Supports both JSON and XML response formats
- Input parameter validation occurs for the initial value and algorithm, with appropriate error responses provided to the requester.
- Two algorithms to provide the prime numbers have been developed:
  - BASE - low complexity algorithm to iterate over each value below the square root of the input value, seeing if the division has a remainder of 0.
  - SIEVE - more performant algorithm, based on Sieve of Eratosthenes (https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes)

## Live environment

The application is running live with a base url of:
```
https://prime-number-project.onrender.com/
```

## Running Locally

Requires Java 17+ and Maven.

The application will be available at: http://localhost:8080

## API Documentation

The API is documented using OpenAPI.

Swagger UI:

https://prime-number-project.onrender.com/swagger-ui/index.html

http://localhost:8080/swagger-ui/index.html

## API Usage
### Generate prime numbers
#### 1) Endpoint default

Endpoint
```
GET /api/v1/primes/{initialValue}
```
Example:
```
GET https://prime-number-project.onrender.com/api/v1/primes/20
```
Response:

JSON
```JSON
{
"initialValue": 20,
"primeValues": [2,3,5,7,11,13,17,19]
}
```
XML
```XML
<PrimesResponse>
  <initialValue>20</initialValue>
  <primeValue>2</primeValue>
  <primeValue>3</primeValue>
  <primeValue>5</primeValue>
  <primeValue>7</primeValue>
  <primeValue>11</primeValue>
  <primeValue>13</primeValue>
  <primeValue>17</primeValue>
  <primeValue>19</primeValue>
</PrimesResponse>
```


#### 2) Endpoint with optional algorithm 

The algorithm use to generate the prime numbers can be selected using the algorithm request parameter.

If no algorithm is supplied, the default algorithm is BASE.

Example using SIEVE:
```
GET https://prime-number-project.onrender.com/api/v1/primes/20?algorithm=SIEVE
```
The response using the optional algorithm is the same as the response examples above. 


## Configuration

The maximum accepted input value is configurable using application properties.

This allows the maximum value to be adjusted depending on the deployment environment and available resources to protect the API from mis-use.


Example:

```
  maximum-input-value: 50_000_000
```


### Example usage

```curl https://prime-number-project.onrender.com/api/v1/primes/20```

```curl https://prime-number-project.onrender.com/api/v1/primes/5000000?algorithm=SIEVE```

```curl -H "Accept: application/xml" https://prime-number-project.onrender.com/api/v1/primes/20```

```curl -H "Accept: application/json" https://prime-number-project.onrender.com/api/v1/primes/20```

## Design Decisions

### Endpoint configuration

```
@GetMapping("/primes/{initialValue}")
```
- An integer variable type was chosen for the input number within the URL. I chose this, rather than a long value, given the maximum value allowed by the int variable is >2.1billion and meets the use case. 
  - Use of long would have given too high a value given system constraints
- Using an integer value also benefits from Spring configuration to validate the input (for example string inputs are caught)

#### Algorithm parameter 
- An Enum for ```PrimeAlogirthm``` is utilised. 
- Via Spring configuration, again this allows validation of the input
- This requires callers to supply values matching the enum exactly (for example `SIEVE` rather than `sieve`). This is deemed acceptable for this use case given:
  - API Specification and Error messages give detailed use instructions
  - Code to make the input case-insensitive is possible, but adds unnecessary code/complexity vs. using the default Spring validation. 


### Performance Considerations

#### Algorithm Development
- After an initial "brute force" prime number algorithm was created, I researched optimum methods to generate the prime numbers (website link above)
- In terms of package structure, separate classes are created for each of the algorithms - allowing separation of concerns and unit test coverage
- A performance test conducted shows the Sieve of Eratosthenes scales significantly better as the upper limit increases. The results are shared below:

  | Input Value | Base Avg (ms) | Sieve Avg (ms) | Improvement |
  |------------:| ------------: | -------------: |------------:|
  |          10 |          1.10 |           0.32 |    **3.4×** |
  |         100 |          1.96 |           0.22 |    **8.9×** |
  |       1,000 |          1.73 |           0.34 |    **5.1×** |
  |      10,000 |          5.03 |           0.39 |   **12.9×** |
  |     100,000 |          7.81 |           0.89 |    **8.8×** |
  |   1,000,000 |         92.89 |           5.28 |   **17.6×** |
  |  10,000,000 |       1240.81 |          64.40 |   **19.3×** |
  - (N.B. performances test utilise 100 iterations per input value)


#### Cache
- A Caffeine Cache configuration has been introduced for repeated requests for the same initial value. 
- The use of algorithm does not impact the values within the response, therefore it has not been included in the cache. 

#### Further considerations
- The cache will only deliver a performance improvement on calls to the same endpoint value - e.g. calling ```GET /api/v1/primes/20``` twice
  - If a subsequent call came in for ```GET /api/v1/primes/21```, this would mean recalculating the prime values even though the response will be the same (as 21 is not a prime)
- A way to improve the performance further therefore would be to use in-app memory to hold a list of primes up to a certain value (either pre-defined, or growing based on requests) which would avoid the need to recalculate for every request
  - However, this was not implemented during the development as it would negate the other factors of the assessment (e.g. algorithm selection via the endpoint parameter would be less useful)
  - It may be that future developments some validation on the client calling the API could be made to check they have the permissions to utilise the SIEVE algorithm - as per a realistic API. 

### Error messages
- Custom exceptions have been created to support the validation of the input values. 
- A GlobalExceptionHandler has been added - and an ErrorResponse has been created to provide a consistent error message to the user.

Example errors API calls:
```curl https://prime-number-project.onrender.com/api/v1/primes/-1```
```curl https://prime-number-project.onrender.com/api/v1/primes/5000001?algorithm=SIEVE```
```curl https://prime-number-project.onrender.com/api/v1/primes/20?algorithm=abc```

### XML Response support
- I developed this application with JSON in mind at first - as I am not a regular creator of XML endpoints.
- On quick research, I found the implementation is handled well with relevant imports and configuration as per https://www.baeldung.com/spring-xml-requestbody
- Example request: 

```curl -H "Accept: application/xml" https://prime-number-project.onrender.com/api/v1/primes/20```


## Testing

The application includes unit and integration tests covering:

- Prime number generation using both algorithms
- REST endpoint behaviour
- Input validation
- Error responses
- JSON and XML response formats

 