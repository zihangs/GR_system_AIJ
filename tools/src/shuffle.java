import java.util.ArrayList;
import java.util.List;

import org.jbpt.petri.NetSystem;
import org.jbpt.petri.Place;
import org.jbpt.petri.Transition;

public class shuffle {
	
	public static void main(String[] args) {
		NetSystem world = new NetSystem();
		
		Place[][] place_matrix = new Place[10][10];
		
		int count_p = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				String name = "p" + count_p;
				String label = "x" + i + "-y" + j;
				Place place = new Place(name,label);
				place_matrix[i][j] = place;
				world.addPlace(place);
				count_p ++;
			}
		}
		
		int count_t = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (i-1>=0 && j-1>=0) {
					String name = "t" + count_t;
					String label = "(move loc-x" + i + "-y" + j + " loc-x" + (i-1) + "-y" + (j-1) + ")";
					Transition transition = new Transition(name,label);
					world.addTransition(transition);
					world.addFlow(place_matrix[i][j], transition);
					count_t ++;
					
					name = "t" + count_t;
					label = "(move loc-x" + i + "-y" + j + " loc-x" + (i) + "-y" + (j-1) + ")";
					transition = new Transition(name,label);
					world.addTransition(transition);
					count_t ++;
					
					name = "t" + count_t;
					label = "(move loc-x" + i + "-y" + j + " loc-x" + (i-1) + "-y" + (j) + ")";
					transition = new Transition(name,label);
					world.addTransition(transition);
					count_t ++;
				}
				
				
				
			}
		}
		System.out.println(world);
	}

}
