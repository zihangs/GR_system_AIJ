import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MotivatingExample {

	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		
		// =============================== variables need to config =======================================
		int plan_num = Integer.parseInt(args[0]); // 0 is irrational;  1 is rational
		String dir_prefix = "../training_examples/";
		
		String traces_dir = dir_prefix + "fig1/traces_in_plan_format/";
		String models_dir = dir_prefix + "fig2/";
		
		
		// index the petri net models, using "XES" parameter for .xes.pnml files
		@SuppressWarnings("rawtypes")
		alignmentTool alignmentTool = new alignmentTool(6, models_dir.toString(), "XES");
		
		// output file name
		FileWriter csvWriter = null;
		if (plan_num == 0) {
			csvWriter = new FileWriter(dir_prefix + "irrational.csv");
			
		}
		
		if (plan_num == 1) {
			csvWriter = new FileWriter(dir_prefix + "rational.csv");
		}
		
		
		// =================================================================================================
		String flag_cost = "IncreasingCost"; //IncreasingCost or FlatCost
		XESGenerator xes_generator = new XESGenerator();
		
		System.out.println(traces_dir.toString());
		Sequence s = xes_generator.pick_sequence(traces_dir.toString(), plan_num);
		int steps = s.sequence.size();
		
		// csvWriter.append("MoL_Boltzmann_Probability\n");
		csvWriter.append(",Goal0,Goal1,Goal2,Goal3,Goal4,Goal5\n");
		for (int step = 0; step < steps; step++) {
			ArrayList<Integer> costs = null;
			ArrayList<Double> probabilities = null;
			
			costs = alignmentTool.allCosts(s, step, "MoLDiagonal", flag_cost);
			probabilities = alignmentTool.probabilities(costs);
			System.out.println(costs);
			System.out.println(probabilities);
			
			if (step == steps-1) {
				// the last step reach to the goal state, which is goal E (the fifth goal)
				csvWriter.append(step+",0,0,0,0,1,0");
			}else {
				csvWriter.append(step+","+ probabilities.get(0)+","+probabilities.get(1)+","+probabilities.get(2)+","+probabilities.get(3)+","+probabilities.get(4)+","+probabilities.get(5)+"\n");
			}
		}
		
		csvWriter.flush();
		csvWriter.close();
	}
}
