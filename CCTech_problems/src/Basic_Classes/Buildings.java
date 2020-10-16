package Basic_Classes;

import java.util.ArrayList;
import java.util.Scanner;

public class Buildings {

	protected ArrayList<Polygon> buildings = new ArrayList<Polygon>();

	Buildings() {
		// default constructor
	}
	
	Buildings(int number_of_buildings, Scanner scan) {
		//Polygon p = new Polygon();
		for(int i = 0; i < number_of_buildings; i++) {
			buildings.add(new Polygon(4, scan));
		}
	}
	
	double findExposedLength(Point source){
		double output = 0;
		Buildings exposed_sides = new Buildings();
		for (int i=0; i<buildings.size() ; i++){
			exposed_sides.buildings.add(buildings.get(i).findExposedsides(source));
			exposed_sides.buildings.get(i).RearrangeExposedsides();
		}
		// put all the exposed sides in a single arrayList
		ArrayList<Line> exposed_edges = new ArrayList<Line>();
		for(int i = 0; i < exposed_sides.buildings.size() ;  i++) {
			for(int j = 0; j < exposed_sides.buildings.get(i).v_lines.size(); j++){
				output = output + exposed_sides.buildings.get(i).v_lines.get(j).get_length();
				exposed_edges.addAll(exposed_sides.buildings.get(i).v_lines);
			}
		}
		
		return output;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the number of buildings present");
		Buildings b = new Buildings(scan.nextInt(),scan);
		System.out.println("Enter the co-ordinates of the source(sun):");
		Point source = new Point(scan.nextDouble(),scan.nextDouble());
		System.out.println("The expposed length is "+b.findExposedLength(source));
		
		scan.close();
	}

}
