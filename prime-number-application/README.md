## Design Decisions

### Endpoint configuration

@GetMapping("/primes/{inputNumber}")
- An integer variable type was chosen for the input number within the URL. I chose this, rather than a long value, given the maximum value allowed by the int variable is >2.1billion and meets the use case. 
  - Use of long would have given too high a value
  - 