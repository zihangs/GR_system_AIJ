import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class XESGenerator {
	
	public XESGenerator() {
		
	}
	
	public void generate(String directory, String output) throws IOException {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		
		String head = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
		"<log xes.version=\"1.0\" xes.features=\"nested-attributes\" openxes.version=\"1.0RC7\" " +
		"xmlns=\"http://www.xes-standard.org/\">\n";
		
		String tail = "</log>";
		
		File newTextFile = new File(output);
        FileWriter fw = new FileWriter(newTextFile);
        fw.write(head);

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	fw.write("<trace>\n");
		        BufferedReader br = new BufferedReader(new FileReader(file));
		        String st;
		        while ((st = br.readLine()) != null) {
		        	char firstChar = st.charAt(0);
		        	if (firstChar != ';') {
		        		fw.write("<event>\n");
			        	fw.write("<string key=\"concept:name\" value=\""+ st + "\"/>" + "\n");
			        	fw.write("</event>\n");
		        	} 
		        }
		        fw.write("</trace>\n");
		        br.close();
		    }
		}
		
		fw.write(tail);
		fw.close();
	}
	
	public Sequence ramdom_sequence(String directory, int model) throws IOException {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
        
        // model index starting from 1
        int count = 1;
        
        ArrayList<String> sequence = new ArrayList<String>();
		
		for (File file : listOfFiles) {
	    	if (count == model) {
	    		BufferedReader br = new BufferedReader(new FileReader(file));
	    		String st;
	    		while ((st = br.readLine()) != null) {
		        	char firstChar = st.charAt(0);
		        	if (firstChar != ';') {
		        		sequence.add(st);
		        	}
		        }
	    		br.close();
	    		break;
	    		
	    	} else {
	    		count++;
	    	}
		}
		Sequence result = new Sequence(sequence, model);
		return result;
	}
	
	
	public Sequence pick_sequence(String directory, int model, int plan_num) throws IOException {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
        
        // model index starting from 0
        int count = 0;
        
        ArrayList<String> sequence = new ArrayList<String>();
		
		for (File file : listOfFiles) {
	    	if (count == plan_num) {
	    		BufferedReader br = new BufferedReader(new FileReader(file));
	    		String st;
	    		while ((st = br.readLine()) != null) {
		        	char firstChar = st.charAt(0);
		        	if (firstChar != ';') {
		        		sequence.add(st);
		        	}
		        }
	    		br.close();
	    		break;
	    		
	    	} else {
	    		count++;
	    	}
		}
		
		Sequence result = new Sequence(sequence, model);
		return result;
	}
	
	
	
	
	public Sequence pick_sequence_lower_case(String directory, int model, String seq_file_name) throws IOException {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
        
        ArrayList<String> sequence = new ArrayList<String>();
		
		for (File file : listOfFiles) {
	    	if (file.getName().equals(seq_file_name)) {
	    		BufferedReader br = new BufferedReader(new FileReader(file));
	    		String st;
	    		while ((st = br.readLine()) != null) {
		        	char firstChar = st.charAt(0);
		        	if (firstChar != ';') {
		        		sequence.add(st.toLowerCase());
		        	}
		        }
	    		br.close();
	    		break;
	    	}
		}
		
		Sequence result = new Sequence(sequence, model);
		return result;
	}
}
