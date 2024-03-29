import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class IPC_domains {
	public static XESGenerator xes_generator = new XESGenerator();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		
		// configurable parameters
		//String input_dataset = "../gene_data/";
		//String domain = "blocks-world";
		//int phi = 50;
		//double lambda = 1.5;
		//double delta = 1.0;
		//double threshold = 0.8
		
		String input_dataset = args[0];
		String domain = args[1];
		int phi = Integer.parseInt(args[2]);
		double lambda = Double.parseDouble(args[3]);
		double delta = Double.parseDouble(args[4]);
		double threshold = Double.parseDouble(args[5]);
		
		// =========================================================== //
		String output_path = "../outputs/";
		Files.createDirectories(Paths.get("../outputs/"));
		input_dataset = input_dataset + domain + "/";
		output_path = output_path + domain + ".csv";
		
		// better to detact the percentages
		int[] percent_list = new int[]{10,30,50,70,100};
		
		FileWriter csvWriter = new FileWriter(output_path);
		csvWriter.append("Percent,Real_Goal,Time,Cost,Prob,Results\n");
		
		alignmentTool alignmentTool = null;
		for (int percent : percent_list) {
			
			// find each problem set
			ArrayList<Integer> prob_numbers = getProblemsNum(input_dataset + "problems/" + Integer.toString(percent));
			
			for (int i : prob_numbers) {
				String model_lib = input_dataset + "problems/" + Integer.toString(percent) +"/"+ Integer.toString(i) + "/train/";


				String problem_id = Integer.toString(i);
				
				// count number of models
				int model_count = modelCounter(model_lib);			
				if (model_count > 0) {
					// index all models once
					
					alignmentTool = new alignmentTool(model_count, model_lib);
					// set parameters
					alignmentTool.set_phi(phi);
					alignmentTool.set_lambda(lambda);
					alignmentTool.set_delta(delta);
				}
				
				// get the target sequence need to be tested
				Sequence s = getTestSequence(input_dataset, percent, problem_id);
				int step = s.sequence.size();
				
				
				// GR and counting time
				long begin = System.nanoTime();
				ArrayList<Integer> costs = alignmentTool.allCosts(s, step);
				ArrayList<Double> probabilities = alignmentTool.probabilities(costs);
				// ArrayList<Integer> results = alignmentTool.best_match(probabilities);
				ArrayList<Integer> results = alignmentTool.best_match_fraction(probabilities, threshold);
				long end = System.nanoTime();
				long time = end - begin;
			
				
				// re-formating to avoid CSV output be split by comma
				String output_cost = "";
				for (int c : costs) {
					output_cost = output_cost + c + "/";
				}
				String output_prob = "";
				for (double p : probabilities) {
					output_prob = output_prob + p + "/";
				}
				String output_results = "";
				for (int r : results) {
					output_results = output_results + r + "/";
				}
				
				System.out.println("Costs: " + costs);
				System.out.println("Probabilities: " + probabilities);
				System.out.println("Results " + results);
				System.out.println("Real goal: " + s.model);
				System.out.println("Steps " + (step) + ": " + s.sequence);
				
				// csvWriter.append("Percent,Real_Goal,Time,Cost,Prob,Results\n");
				csvWriter.append(percent + "," + s.model + "," + time + "," + output_cost + "," + output_prob + "," + output_results + "\n");
			}
		}
		csvWriter.flush();
		csvWriter.close();
	}
	
	public static ArrayList<Integer> getProblemsNum(String dir_name) {
		File dir = new File(dir_name);
		File[] problem_list = dir.listFiles();
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (File p : problem_list) {
			// skip the hidden files (.DS_store)
			if (!p.isHidden()) {
				String[] l = p.toString().split("/");
				numbers.add(Integer.parseInt(l[l.length-1]));
			}
		}
		Collections.sort(numbers);
		return numbers;
	}
	
	
	// get the sequence for testing
	public static Sequence getTestSequence(String folder_name, int percent, String problem_id) throws IOException {
		String test_dir = folder_name + "/test/" + Integer.toString(percent) + "/" + problem_id + "/";
		BufferedReader br = new BufferedReader(new FileReader(test_dir + "goal.txt"));
		String goal = br.readLine().toString();
		Sequence s = xes_generator.pick_sequence_lower_case(test_dir, Integer.parseInt(goal), "obs.dat");
		br.close();
		return s;
	}
	
	
	
	// count the number of Petri Nets (models) in the given directory
	public static int modelCounter(String dir) {
		
		File models = new File(dir);
		File[] model_list = models.listFiles();
		int model_count = 0;
		if (model_list != null) {
			for (File model : model_list) {
				if (model.isFile() && !model.isHidden() && model.getName().endsWith(".pnml")) {
					model_count++;
				}
			}
		}
		return model_count;
	}
}
