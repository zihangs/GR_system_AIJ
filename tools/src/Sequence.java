import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.jbpt.petri.*;
import org.jbpt.petri.io.PNMLSerializer;

public class Sequence {
	
	public int model;
	public ArrayList<String> sequence;
	
	public Sequence(ArrayList<String> seq, int model) {
		this.model = model;
		this.sequence = seq;
	}
	
	private int selectModel() {
		Random rand = new Random();
		// if select pnml file from a folder known the number of pnml files
		int modelIndex = rand.nextInt(6) + 1;
		return modelIndex;
	}
	
	// steps by steps matched queries
	// fully matched
	public ArrayList<String> full_matched_with_star() {
		int size = this.sequence.size();
		ArrayList<String> queries = new ArrayList<String>();
		for (int j = 1; j <= size; j++) {
			String query = "SELECT * FROM * WHERE Executes(<*,";
			for (int i = 0; i < j; i++) {
				query = query + "\"" + this.sequence.get(i) + "\",*,";
			}
			int endIndex = query.length()-1;
			int beginIndex = 0;
			query = query.substring(beginIndex, endIndex);
			query = query + ",*>);";
			queries.add(query);
		}
		return queries;
	}
	
	public ArrayList<String> exactly_matched_without_star() {
		int size = this.sequence.size();
		ArrayList<String> queries = new ArrayList<String>();
		for (int j = 1; j <= size; j++) {
			String query = "SELECT * FROM * WHERE Executes(<";
			for (int i = 0; i < j; i++) {
				query = query + "\"" + this.sequence.get(i) + "\",";
			}
			int endIndex = query.length()-1;
			int beginIndex = 0;
			query = query.substring(beginIndex, endIndex);
			query = query + ",*>);";
			queries.add(query);
		}
		return queries;
	}
	
	// partially matched
	public ArrayList<String> partial_matched_replaced_with_star(float partial_rate) {
		int size = this.sequence.size();
		ArrayList<String> queries = new ArrayList<String>();
		ArrayList<String> partial_sequence = new ArrayList<String>();
		
		Random r = new Random();
		for (int k = 0; k < size; k++) {
			int random = r.nextInt((int) (partial_rate*10));
			if (random != 0) {
				partial_sequence.add(this.sequence.get(k));
			}
			else {
				partial_sequence.add(null);
			}
		}
		
		for (int j = 1; j <= size; j++) {
			String query = "SELECT * FROM * WHERE Executes(<";
			for (int i = 0; i < j; i++) {
				String action = partial_sequence.get(i);
				if (action != null) {
					query = query + "\"" + action + "\",";
				} else {
					query = query + "*,";
				}
			}
			int endIndex = query.length()-1;
			int beginIndex = 0;
			query = query.substring(beginIndex, endIndex);
			query = query + ",*>);";
			queries.add(query);
		}
		return queries;
	}
	

}
