package javachip;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Firstly we setup the Lexer class and main method
 */
public class Lexer {
	public static enum TokenType {
		/*
		 *  Next, we must define the types of the tokens that we are extracting 
		 *  and the regular expression that they match
		 */
		// Token types cannot have underscores
        NUMBER("-?[0-9]+"), BINARYOP("[*|/|+|-]"), WHITESPACE("[ \t\f\r\n]+");
        public final String pattern;
        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }
	
	
	/*
	 *  Finally, we declare a data structure for holding the token data. 
	 *  Additionally, I will override the toString() method for printing out 
	 *  the token’s contextual data at the end of this tutorial in the format 
	 *  I have mentioned earlier: (<TYPE> <DATA>)
	 */
	public static class Token {
        public TokenType type;
        public String data;

        public Token(TokenType type, String data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format("(%s %s)", type.name(), data);
        }
    }
	
	/*
	 * We begin by framing our lexical analysis method as lex(), a function 
	 * which returns a list of Token objects. Additionally, we will need to 
	 * import ArrayList in order to store the Token objects into the list.
	 * 
	 * Now, we need to encode all of the regular expression patterns for each 
	 * of the token types into a single pattern in the algorithm shown below. 
	 * This is the case where we use named capturing groups in regular 
	 * expressions as (?<TYPE> PATTERN) so that once a pattern is matched, 
	 * we can retrieve the token by calling its group name, the TYPE.
	 * 
	 * Additionally, we import the Pattern class to compile regular expression 
	 * patterns.
	 * 
	 * Next, we begin tokenizing by creating a Matcher object from the compiled pattern, 
	 * tokenPatterns, from earlier. The matcher will return any token matched with any of 
	 * the corresponding token type patterns. Note that we must also import the Matcher class here. 
	 * 
	 * We will iterate through the list of token types and ask if the token type was matched. 
	 * If the token returns a match, we will add it to our list of tokens with the corresponding 
	 * token type and continue parsing the input.
	 */
	public static ArrayList<Token> lex(String input) {
        // The tokens to return
        ArrayList<Token> tokens = new ArrayList<Token>();

        // Lexer logic begins here
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values()) // enum TokenType.values() gets an array of values eg. NUMBER, BINARYOP, WHITESPACE
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));
        
        // Begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            if (matcher.group(TokenType.NUMBER.name()) != null) {
                tokens.add(new Token(TokenType.NUMBER, matcher.group(TokenType.NUMBER.name())));
                continue;
            } else if (matcher.group(TokenType.BINARYOP.name()) != null) {
                tokens.add(new Token(TokenType.BINARYOP, matcher.group(TokenType.BINARYOP.name())));
                continue;
            } else if (matcher.group(TokenType.WHITESPACE.name()) != null)
                continue;
        }

        return tokens;
    }
	
	
	public static void main(String[] args) { 
        String input = "11 + 22 - 33";
        
        // Create tokens and print them
        ArrayList<Token> tokens = lex(input);
        for (Token token : tokens)
            System.out.println(token);
    }
}
