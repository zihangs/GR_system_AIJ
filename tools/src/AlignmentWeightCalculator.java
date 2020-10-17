import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math; 

import org.pql.alignment.PQLMove;

public class AlignmentWeightCalculator{
	
	public static int AlignmentCost = 1;
	
	// =========================== all alignments cost = 1 ==============================
	
	//  alignment weight (Omega): only consider MoL
	public static int alignmentWeight(List<PQLMove> alignment, int phi, double lambda, double delta)
	{
		//int phi = 50;
		//double lambda = 1.1;
		//double delta = 1.0;
		
		double cumulatedCost = 0;
		int count_i = 0;
		int consecutive = 0;
		
		for(int i=0; i< alignment.size(); i++)
		{
			
			//System.out.println(alignment.get(i).getMoL());
			//System.out.println(alignment.get(i).getMoM());
			
			if(!alignment.get(i).getMoL().equals("SKIP_STEP"))
			{
				count_i++;  // this is increasing cost (consider the discount factor)
			}
			
			if(alignment.get(i).getMoM().equals("SKIP_STEP"))
			{
				// Math.pow(base, exponent)
				cumulatedCost += Math.pow(count_i, delta);
				consecutive++;		
			} else {
				consecutive = 0;
			}
		}
		double multiplier = Math.pow(lambda, consecutive);
		return (int) (phi + cumulatedCost * multiplier);
	}
	
	
	

	// ================================= with diagonal moves =================================
	// phi is not added
	public int distance(String s) {
		Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);
        ArrayList<Integer> array = new ArrayList<Integer>();
        while(m.find()) {
        	array.add(Integer.parseInt(m.group()));
        }
        double dis = Math.sqrt(Math.pow((array.get(0) - array.get(2)), 2)
				 + Math.pow((array.get(1) - array.get(3)), 2));
        if (dis == 1.0) {
        	return 10;
        } else {
        	return 14;
        }
	}
	
	// flag = IncreasingCost/FlatCost  (whether consider the discount factor or not)
	public int getAlignmentCostForMoLDiagonal(List<PQLMove> alignment, String flag)
	{
		int result = 0;
		int count = 0;
		int consecutive = 0;
		double base = 1.1;
		
		double multiplier = 1.0;
		for(int i=0; i< alignment.size(); i++)
		{

			// count step number
			if(!alignment.get(i).getMoL().equals("SKIP_STEP"))
			{
				if (flag.contentEquals("IncreasingCost")) {
					count++;
				}
				if (flag.contentEquals("FlatCost")) {
					count = 1;
				}
			}
			
			if(alignment.get(i).getMoM().equals("SKIP_STEP"))
			{
				consecutive++;
				multiplier = Math.pow(base, consecutive);
				result += (int) (distance(alignment.get(i).getMoL()) * count );
			} else {
				consecutive = 0;
				multiplier = 1.0;
			}
		}
		return (int) (result * multiplier);
	}
	
	
	// Both MOL and MOM
	public int getAlignmentCostDiagonal(List<PQLMove> alignment)
	{
		int result = 0;
		
		for(int i=0; i< alignment.size(); i++)
		{
			if(alignment.get(i).getMoM().equals("SKIP_STEP"))
			{result += distance(alignment.get(i).getMoL());}
			else {if(!(alignment.get(i).getMoM().equals("INV_TRANS")) && alignment.get(i).getMoL().equals("SKIP_STEP"))
			{result += distance(alignment.get(i).getMoM());}}
		}
		
		return result;
	}
	// ===============================================================================

}
