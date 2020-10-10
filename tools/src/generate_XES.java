import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONException;

public class generate_XES {

	public static void main(String[] args) throws IOException, InterruptedException, 
	ClassNotFoundException, SQLException, JSONException{
		
		File dir = new File(args[0]);
		File listDir[] = dir.listFiles();
		
		// folder for output and store the XES event logs
		File new_folder = new File(args[0]);
		new_folder.mkdir();
		
		XESGenerator xes_generator = new XESGenerator();
		for (int i = 0; i < listDir.length; i++) {
		    if (listDir[i].isDirectory()) {
		    	xes_generator.generate(listDir[i].toString(), new_folder.toString() + "/created" + i + ".xes");

		    	System.out.println(listDir[i]);
		    }
		}
		System.out.println("successfully done");
	}
}
