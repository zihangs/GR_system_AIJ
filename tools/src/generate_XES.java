import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONException;

public class generate_XES {

	public static void main(String[] args) throws IOException, InterruptedException, 
	ClassNotFoundException, SQLException, JSONException{
		
		File dir = new File(args[0]);
		
		XESGenerator xes_generator = new XESGenerator();
		for (File an_item : dir.listFiles()) {
			
		    if (an_item.isDirectory()) {
		    	String input = an_item.getPath();
		    	String index = an_item.getName().split("_")[1];
		    	String output = dir.toString() + "/created" + index + ".xes";

		    	xes_generator.generate(input, output);

		    	System.out.println(output);
		    }
		}
		System.out.println("successfully done");
	}
}
