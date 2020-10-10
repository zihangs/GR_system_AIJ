import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class IPC_domains {
	public static XESGenerator xes_generator = new XESGenerator();
	
	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		
		// find the folder of the domain
		String domain = "blocks-world1";
		
		String folder_name = "../datasets/table_4/" + domain + "/";
		int[] percent_list = new int[]{10,30,50,70,100};
		
		FileWriter csvWriter = new FileWriter("../outputs/output.csv");
		csvWriter.append("Percent,Real_Goal,Time,Cost,Prob,Results\n");
		
		alignmentTool alignmentTool = null;
		for (int percent : percent_list) {
			// find each problem set
			String dir_name = folder_name + "problems/" + Integer.toString(percent);
			File dir = new File(dir_name);
			File[] problem_list = dir.listFiles();
			
			for (File a_problem : problem_list) {
				
				// skip the hidden files (.DS_store)
				if (a_problem.isHidden()) {
					continue;
				}
				System.out.println(a_problem.getName());

				String target_folder = a_problem + "/train/";
				
				String problem_id = a_problem.getName();
				
				File models = new File(target_folder);
				File[] model_list = models.listFiles();
				
				
				
				// this blocks of code must be moved !!!!!!!!!!!!!!!!
				// count number of models
				int model_count = 0;
					if (model_list != null) {
					for (File model : model_list) {
						if (model.isFile()) {
							int len = model.getName().length();
							if (model.getName().charAt(len-1) == 's') {
								model_count++;
							}
						}
					}
				}
				
				///////////////////////////////////////////
				////////////////////////////////////////////
				////////////////////////////////////////////
				if (model_count > 0) {
					// index all models once
					// target_folder = model_lib
					alignmentTool = new alignmentTool(model_count, target_folder);
				}
				
				///////////////////
				//////////////////////////////////////////
				////// to here get index of all models
				
				// test for once
				// create sequence for testing

				String test_dir = folder_name + "/test/" + Integer.toString(percent) + "/" + problem_id + "/";
				BufferedReader br = new BufferedReader(new FileReader(test_dir + "goal.txt"));
				String goal = br.readLine().toString();
				
				Sequence s = xes_generator.pick_sequence_lower_case(test_dir, Integer.parseInt(goal), 1);  // only have one trace which is 0
				int step = s.sequence.size();
				
				long begin = System.nanoTime();
				ArrayList<Integer> costs = null;
				ArrayList<Double> probabilities = null;
				//System.out.println(alignmentTool);
				costs = alignmentTool.allCosts(s, step);
				//probabilities = alignmentTool.probabilities_without_beta(costs);
				probabilities = alignmentTool.probabilities(costs);
				
				ArrayList<Integer> results = alignmentTool.best_match(probabilities);
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
				
				System.out.println("Step " + (step) + ": " + costs);
				System.out.println(probabilities);
				System.out.println(results);
				System.out.println("Step " + (step) + ": " + s.sequence);
				
				// csvWriter.append("Percent,Real_Goal,Time,Cost,Prob,Results\n");
				csvWriter.append(percent + "," + s.model + "," + time + "," + output_cost + "," + output_prob + "," + output_results + "\n");
				
			}
			
			
		}
		csvWriter.flush();
		csvWriter.close();
	}
}
