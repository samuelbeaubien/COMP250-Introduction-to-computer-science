package a2posted;

public class test {

	
	public static void main(String[] args) {
	
	/*
		
	String ex = "hola my friend this ";
	
	StringSplitter splitted = new StringSplitter(ex); 
	
	int tCountTokens = splitted.countTokens();
	
	boolean tHasMore = splitted.hasMoreTokens();
	
	//char exChar = ex.charAt(1);

	
	System.out.println(tCountTokens);
	
	System.out.println(tHasMore);
	
	
	String nextToken = splitted.nextToken();
	
	System.out.println(nextToken);
	
	nextToken = splitted.nextToken();
	
	System.out.println(nextToken);
	
	tCountTokens = splitted.countTokens();

	tHasMore = splitted.hasMoreTokens();
	
	System.out.println(tCountTokens);
	
	System.out.println(tHasMore);
	
	
	
	//System.out.println(exChar);
		
	*/	
		
	String ex2 = "a=2";
		
	StringSplitter splitted = new StringSplitter(ex2);	
	
	boolean answer = LanguageParser.isStatement(splitted);
	
	//System.out.println(answer);
	
	
	//StringSplitter splitter = new StringSplitter("if true then a=3 else if false then a=3 else a=3 end end ");
	
	StringSplitter splitter = new StringSplitter("if false then if true then c=5 else d=5 end else b=31 end");
	
	boolean text = LanguageParser.isStatement(splitter);
	
	System.out.println(text);
	
	}
	
}
	
	
	
	
	
	
	