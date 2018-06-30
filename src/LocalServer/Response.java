package localServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Response {
	public int clientNumber;
	public String message;
	
	public Response(int clientNumber, String... message) {
		this.clientNumber = clientNumber;
		this.message = buildResponseMessage(message);
	}
	
	public static List<Response> create(int clientNumber, String... response) {
		return Arrays.asList(new Response(clientNumber, response));
	}
	
	public static List<Response> create(Response... responses) {
		return Arrays.asList(responses);
	}	
	
	@SafeVarargs
	public static List<Response> join(List<Response>... responsesList) {
		List<Response> allResponses = new ArrayList<Response>();
		
		for (List<Response> responses : responsesList)
			allResponses.addAll(responses);
			
		return allResponses;
	}		
	
	private static String buildResponseMessage(String... tokens) {
		return String.join("~", tokens) + "#";
	}
	public static final List<Response> EMPTY = Collections.<Response>emptyList();

}
