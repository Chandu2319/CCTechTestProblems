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
	
	double findExposedLength( Point source) {
		double exposedLength = 0;		//exposed_Length
		double curr_angle = 0;
		Point curr_point = new Point();
		ArrayList<Line> exposed_sides = this.findExposedSides(source);
		Line l_start = new Line(source,exposed_sides.get(0).get_stpt());
		Line l_end = new Line(source,exposed_sides.get(0).get_edpt());
		curr_angle = l_start.get_angle();
		curr_point = l_start.get_edpt();
		// to determine the sun/source is present Left or Right
		if(exposed_sides.get(0).angle_subtended_at_pt(source) > 0) {		//Sun is on the left side
			for(int i = 0; i < exposed_sides.size(); i++) {
				double angle_subt = exposed_sides.get(i).angle_subtended_at_pt(source);
				l_start = new Line(source,exposed_sides.get(i).get_stpt());
				l_end = new Line(source,exposed_sides.get(i).get_edpt());
				if(angle_subt > 0) {
					if(l_start.get_angle() < curr_angle && curr_angle < l_end.get_angle() ) {
						Line l = new Line(source,curr_point);
						ArrayList<Point> int_pt = Line.find_intsctn_pt_lines(l, exposed_sides.get(i));
						if(exposed_sides.get(i).is_pt_on_line_seg(int_pt.get(0))) {
							l = new Line(int_pt.get(0),exposed_sides.get(i).get_edpt());
							angle_subt = l.angle_subtended_at_pt(source);
						}
						exposedLength = exposedLength + l.get_length();
						curr_angle = curr_angle + angle_subt;
						curr_point = l_end.get_edpt();
					} else if(l_start.get_angle() >= curr_angle) {
						exposedLength = exposedLength + exposed_sides.get(i).get_length();
						curr_angle = curr_angle + angle_subt;
						curr_point = l_end.get_edpt();
					}
					
				}	
			}
		} else {			// Sun is on the right side
			
		}
			
		return exposedLength;
	}
	
	ArrayList<Line> findExposedSides(Point source) {
		
		Buildings exposed_sides = new Buildings();
		for (int i=0; i<buildings.size() ; i++){
			exposed_sides.buildings.add(buildings.get(i).findExposedsides(source));
			exposed_sides.buildings.get(i).RearrangeExposedsides();
		}
		
		exposed_sides.sortNearestBuilding(source);
		// put all the exposed sides in a single arrayList
		ArrayList<Line> exposed_edges = new ArrayList<Line>();
		for(int i = 0; i < exposed_sides.buildings.size() ;  i++) {
			/*for(int j = 0; j < exposed_sides.buildings.get(i).v_lines.size(); j++){
				output = output + exposed_sides.buildings.get(i).v_lines.get(j).get_length();
			}*/
			exposed_edges.addAll(exposed_sides.buildings.get(i).v_lines);
		}
		//this.buildings.clear();
		//this.buildings.addAll(exposed_sides.buildings);
		return exposed_edges;
		
	}
	
	void sortNearestBuilding(Point source) {
		double d_i,d_j;
		for(int i = 0; i < this.buildings.size()-1; i++ ) {			
			for(int j = i+1; j < this.buildings.size(); j++){
				d_j = this.buildings.get(j).distFromAPoint(source);
				d_i = this.buildings.get(i).distFromAPoint(source);
				if(d_j < d_i){
					shift(this.buildings.get(j),i);
					//swap(this.buildings.get(i),this.buildings.get(j));
					
				}
			}
		}
	}
	
	void shift (Polygon p1, int index) {
		if(this.buildings.remove(p1)){
			this.buildings.add(index, p1);
		}
	}
	
	void swap(Polygon p1, Polygon p2) {
		int i1,i2;
		i1 = this.buildings.indexOf(p1);
		i2 = this.buildings.indexOf(p2);
		this.buildings.set(i1, p2);
		this.buildings.set(i2, p1);
	} 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the number of buildings present");
		Buildings b = new Buildings(scan.nextInt(),scan);
		System.out.println("Enter the co-ordinates of the source(sun):");
		Point source = new Point(scan.nextDouble(),scan.nextDouble());
		
		System.out.println("The exposed length is "+b.findExposedLength(source));
		
		scan.close();
	}	

}
