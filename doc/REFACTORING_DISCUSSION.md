## Refactoring Lab Discussion
### Team 9
### Oliver Rodas

### Issues in Current Code


#### Program Parser
* We had nested switch statements. 
* The loop has conditionals on what kind of command it is.
* Exceptions are not utilized as they should be.

#### Method or Class
 * Design issues

 * Design issue


### Refactoring Plan

 * What are the code's biggest issues?
We have switch statements which is a big code smell. We don't use polymorphism to its fullest. There are back channels that need to be removed

 * Which issues are easy to fix and which are hard?
    * Easy: Removing Back channels and using exceptions instead
    * Hard: Removing the switch statements

 * What are good ways to implement the changes "in place"?
    * Using reflection to remove the switch statement
    * Throwing exceptions

### Refactoring Work

 * Issue chosen: Fix and Alternatives
    I used reflection to remove the large switch statement from the program parser. The code is much cleaner, and it is much easier to add new features.
   
    The alternative was to continue using a switch statement. It was a tough decision, but reflection ultimately led to cleaner code.

 * Issue chosen: Fix and Alternatives

