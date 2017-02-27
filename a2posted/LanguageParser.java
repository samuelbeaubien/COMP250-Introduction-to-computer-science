// Student name : Samuel Beaubien
// ID : 260691877
// Date : 14/10/16


package a2posted;

/**
 * This static class provides the isStatement() method to parse a sequence 
 * of tokens and to decide if it forms a valid statement
 * You are provided with the helper methods isBoolean() and isAssignment().
 * 
 * - You may add other methods as you deem necessary.
 * - You may NOT add any class fields.
 */
public class LanguageParser {
	
	/**
	 * Returns true if the given token is a boolean value, i.e.
	 * if the token is "true" or "false".
	 * 
	 * DO NOT MODIFY THIS METHOD.
	 */
	
	private static boolean isBoolean (String token) {
		
		return (token.equals("true") || token.equals("false"));
		
	}
	
	/**
	 * Returns true if the given token is an assignment statement of the
	 * type "variable=value", where the value is a non-negative integer.
	 * 
	 * DO NOT MODIFY THIS METHOD.
	 */
	private static boolean isAssignment (String token) {
		
		// The code below uses Java regular expressions. You are NOT required to
		// understand Java regular expressions, but if you are curious, see:
		// <http://java.sun.com/javase/6/docs/api/java/util/regex/Pattern.html>
		//
		//   This method returns true if 
		//   the token matches the following structure:
		//   one or more letters (a variable), followed by
		//   an equal sign '=', followed by
		//   one or more digits.
		//   However, the variable cannot be a keyword ("if", "then", "else", 
		//   "true", "false")
		
		if (token.matches("if=\\d+") || token.matches("then=\\d+") ||
				token.matches("else=\\d+") || token.matches("end=\\d+") ||
				token.matches("true=\\d+") || token.matches("false=\\d+"))
			return false;
		else
			return token.matches("[a-zA-Z]+=\\d+");
		
	}

	/**
	 * Given a sequence of tokens through a StringSplitter object,
	 * this method returns true if the tokens can be parsed according
	 * to the rules of the language as specified in the assignment.
     */
				
	public static boolean  isStatement(StringSplitter splitter) {

		StringStack stack = new StringStack();
		int count = splitter.countTokens();
		String token;	
		String newToken;

		if (count == 0)
			return false;
		

		/*Overview : 
		 * This code verifies is a given statement respects the pre-determined rules of syntax
		 * It uses a stack and a while loop. 
		 * 
		 * At first, I assign the number of element in the statement (a.k.a number of "tokens"
		 * to the variable "count" this will be the upper bound of the while loop 
		 * 
		 * While loop : At each iteration of the while loop, the programs takes the next 
		 * token of the statement and compares it with each possible tokens (if, bool,
		 * then, assignment, statement, end etc...). 
		 * 
		 * 	For "if" the programs pushes a list a String in the stack. This list is the 
		 * 	inverse of what should usually appear in the statement. Then it increment the 
		 * 	counter
		 * 
		 * 	For all others, the program compares the tokens to the top of the stack, and if
		 *  it matches, pulls one object of the stack. Then it increments the counter
		 *  
		 *  If the token does not correspond to any possible string in the statement, or 
		 *  the string does not correspond to the top of the stack (therefore should not be 
		 *  there), the programs go to the "else" statement and returns false.
		 *  
		 *  If when the while loop ends, the stack if empty, it returns true. 
		 *  
		 *  For the case of a single assignment without if, there is one if statement that 
		 *  check for assignment && if the stack is empty. If the conditions are respected, 
		 *  it returns true. 
		 */
		
		
		
		int counter = 0;  // the counter will be incremented each time there is one match in the while loop
		// If the conditions for a single assignment are respected, the isThereSingleAssignment 
		// will be true. But an object will also be pushed in the stack, therefore making sure 
		// that, in the eventuality that there is two assignment (ex a=21 b=23) the programs 
		// returns false 
		boolean isThereSingleAssignment = false;   
									
		while (counter < count ) {   // The while loop stops when all the elements of the statement have been checked

			token = splitter.nextToken();

			if (token.equals("if") == true) { 
				
				// If there if nested "if" in a statement, it is necessary to pop the 
				// string "statement".
				if (stack.peek().equals("statement") == true) {
					stack.pop();
				}
				
				stack.push("end");
				stack.push("statement");   // In the statement section, there can either be an assignment or an other "if"
				stack.push("else");
				stack.push("statement");
				stack.push("then");
				stack.push("bool");
				counter++;
			}
			
			// At each else if, is the token is good, an element is removed from the stack

			else if (isBoolean(token) == true && stack.peek() == "bool") {
				stack.pop();
				counter++;
			}

			else if (token.equals("then") == true && stack.peek().equals("then") == true) {
				stack.pop();
				counter++;
			}

			else if (isAssignment(token) == true && stack.peek() == "statement") {
				stack.pop();
				counter++;
			}

			else if (token.equals("else") == true && stack.peek().equals("else") == true) {
				stack.pop();
				counter++;
			}

			else if (token.equals("end") == true && stack.peek().equals("end") == true) {
				stack.pop();
				counter++;
			}
			
			else if (isAssignment(token) == true && stack.isEmpty() == true) {
				isThereSingleAssignment = true;
				stack.push("thereIsSingleAssignment");
				counter++;
			}
			
			else { // return false is a incorrect token is placed in an otherwise correct statement
				
				return false;
			}			

		} // while loop ends 
		
		
		// returns true for a single assignment
		if (isThereSingleAssignment == true && stack.peek() == "thereIsSingleAssignment") {
			return true;
		}
		
		// returns true for a if statement that respects the syntax
		if (stack.isEmpty() == true) {
			return true;
		}

		return false;
		
	}

}