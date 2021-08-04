Feature: Operation Controller Tests

Background:
	Given the list of valid stockId on StockManager
	And previous operations for those stockIds	


Scenario Outline: Getting operations from QuoteManager
	When trying to list operations of stockId "<stockId>"
	Then the  HTTP response status for the get request should be <statusCode>
	And the number of operations return should be <nOperations>

Examples:
			|stockId	|statusCode	|nOperations	|
			|petr4		|200		|3				|
			|vale5		|200		|4				|
			|error6		|404		|0				|			
			|error7		|404		|0				|
			|			|200		|7				|
	

Scenario Outline: Posting an operation to QuoteManager
	Given an operation with stockId equal to "<stockId>"
	And with a list of quotes of size <size>
	When trying to register the operation on to QuoteManager
	Then the  HTTP response status for the post request should be <statusCode>
 
 Examples:
			|stockId	|size	|statusCode	|
			|petr4		|3		|201		|
			|vale5		|4		|201		|
			|error6		|3		|404		|
			|error7		|4		|404		|
			|petr4		|0		|400		|
			|vale5		|0		|400		|
			
 