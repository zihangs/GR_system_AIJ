import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		System.out.println(sequence);
		Sequence result = new Sequence(sequence, model);
		return result;
	}
	
	
	public Sequence pick_sequence(String directory, int plan_num) throws IOException {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
        
        // model index starting from 1
        int count = 0;
        
        ArrayList<String> sequence = new ArrayList<String>();
		
		for (File file : listOfFiles) {
	    	if (count == plan_num) {
	    		BufferedReader br = new BufferedReader(new FileReader(file));
	    		String st;
	    		while ((st = br.readLine()) != null && st.length() > 0) {
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
		
		System.out.println(sequence);
		// to goal E (number 4) overload method
		Sequence result = new Sequence(sequence, 4);
		return result;
	}
	
	
	
	
	public Sequence pick_sequence_lower_case(String directory, int model, int plan_num) throws IOException {
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
		        		sequence.add(st.toLowerCase());
		        	}
		        }
	    		br.close();
	    		break;
	    		
	    	} else {
	    		count++;
	    	}
		}
		
		System.out.println(sequence);
		Sequence result = new Sequence(sequence, model);
		return result;
	}
}
