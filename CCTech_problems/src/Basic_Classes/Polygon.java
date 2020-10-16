package Basic_Classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Polygon {
	
	ArrayList<Point> v_points = new ArrayList<Point>();
	ArrayList<Line> v_lines = new ArrayList<Line>();
	
	Polygon() {
		// default constructor
	}
	
	Polygon(int n, Scanner scan) {
		Point pt = new Point();
		//Scanner scan = new Scanner(System.in);
		for(int i=0 ; i < n ; i++ ){
			System.out.println("Enter the co-ordinates of the point: ");
			pt.set_x(scan.nextDouble());
			pt.set_y(scan.nextDouble());
			v_points.add(new Point(pt.get_x(),pt.get_y()));
			if(i>0){
				v_lines.add(new Line(v_points.get(i-1),v_points.get(i)));
			}
		}
		v_lines.add(new Line(v_points.get(v_points.size()-1),v_points.get(0))); 	//closing the polygon by joining first and last points
	}
	
	void readPolygon(int n , Scanner scan){
		Point pt = new Point();
		
		for(int i=0 ; i < n ; i++ ){
			System.out.println("Enter the co-ordinates of the point: ");
			pt.set_x(scan.nextDouble());
			pt.set_y(scan.nextDouble());
			v_points.add(new Point(pt.get_x(),pt.get_y()));
			if(i>0){
				v_lines.add(new Line(v_points.get(i-1),v_points.get(i)));
			}
		}
		v_lines.add(new Line(v_points.get(v_points.size()-1),v_points.get(0))); 	//closing the polygon by joining first and last points
		
	}
	
	int PtInsidePolygon(Point pt)
	{
		ArrayList<Point> inpt = new ArrayList<Point>();
		Iterator<Line> itr = this.v_lines.iterator();
		int i = 0;
		Line l = new Line(pt, 0, 1000 * Point.dist_btw_2_pts(pt, this.v_lines.get(i).get_stpt()));
		while (!(isLineValid(l, i - 1)))
		{
			l = new Line(pt, this.v_lines.get(i).get_midpt());
			l = new Line(pt, l.get_angle(), 1000 * Point.dist_btw_2_pts(pt, this.v_lines.get(i).get_stpt()));
			i++;
		}
		Line curr_line;// = v_lines.get(0);
		while ( itr.hasNext() ) 
		{
			curr_line = itr.next();
			if (curr_line.is_pt_on_line_seg(pt))
				return 0;
			ArrayList<Point> pts = Line.find_intsctn_pt_linesegment(l, curr_line);
			int j = 0;
			while (j<pts.size())
			{
				if (pts.get(j) != curr_line.get_stpt() && pts.get(j) != curr_line.get_edpt())
				{
					inpt.add(pts.get(j));
					
				}
				j++;
			}
		}

		if (inpt.size() % 2 == 1)
		{
			System.out.println("The given point is inside the polygon");
			return -1; // inside the polygon
		}
		else
		{
			System.out.println("The given point is outside the polygon");
			return 1;// outside the polygon
		}
	}
	
	boolean isLineValid(Line l,  int i_0)
	{
		int i = 0;
		if (i_0 == 0)
			i_0 = 1;
		while (!(l.is_pt_on_line(v_lines.get(i).get_stpt())))
		{
			i++;
			if (i == i_0)
				i++;
			if (i == v_lines.size())
				return true;
		}
		return false;

	}

	ArrayList<Point> findNearestVertex(Point source_pt) {
		
		ArrayList<Point> n_pt = new ArrayList<Point>();
		Point vertex = this.v_points.get(0);
		double d,min_distance = 1E10;
		for(int i = 0 ; i < this.v_points.size(); i++){
			
			d = Point.dist_btw_2_pts(source_pt, this.v_points.get(i));
			if( Math.abs(d-min_distance) < Math.pow(10, -3) || d < min_distance ){
				min_distance = d;
				vertex = this.v_points.get(i);
			}
		}
		n_pt.add(new Point(vertex.get_x(),vertex.get_y()));
		n_pt.trimToSize();
		return n_pt;
	}
	
	Polygon findExposedsides(Point source_pt) {
		Polygon p = new Polygon();
		ArrayList<Line> sides = new ArrayList<Line>();
		ArrayList<Point> n_pt = this.findNearestVertex(source_pt);
		if(n_pt.size() == 2){
			sides.add(new Line(n_pt.get(0),n_pt.get(1)));
		} else if (n_pt.size() == 1) {
			for(int i = 0; i < this.v_lines.size(); i++){
				Line l = v_lines.get(i);
				if(l.is_pt_on_line_seg(n_pt.get(0))){
					if(l.pt1.isEquals(n_pt.get(0))){
						sides.add(new Line(l.get_edpt(),n_pt.get(0)));  // now the end point in both lines is the nearest point 
					} else {
						sides.add(new Line(l.get_stpt(),n_pt.get(0)));
					}
				}
			}
		}
		p.v_lines.addAll(sides);
		p.v_points.addAll(n_pt);
		return p;
	}
	
	void RearrangeExposedsides() {
		Point bottom = findBottomPoint();
		Line l1,l2;
		l1 = v_lines.get(0); l2 = v_lines.get(1);
		v_lines.clear();
		if(v_points.get(0).isEquals(bottom)) {			// or if(l1.is_pt_on_line_seg(bottom) && l2.is_pt_on_line_seg(bottom)){
			if(l1.get_slope() < Math.pow(10, -3)){
				v_lines.add(new Line(l1.get_stpt(),bottom));
				v_lines.add(new Line(bottom,l2.get_stpt()));
			} else if (l2.get_slope() < Math.pow(10,-3)){
				v_lines.add(new Line(l1.get_stpt(),bottom));
				v_lines.add(new Line(bottom,l2.get_stpt()));
			}
		} else {
			if(l1.get_slope() < Math.pow(10, -3)){
				v_lines.add(new Line(bottom,v_points.get(0)));
				v_lines.add(new Line(v_points.get(0),l1.get_stpt()));
			} else if (l2.get_slope() < Math.pow(10,-3)){
				v_lines.add(new Line(bottom,v_points.get(0)));
				v_lines.add(new Line(v_points.get(0),l2.get_stpt()));
			}
		}
		
		
	}
	
	Point findBottomPoint() {
		Point pt = v_lines.get(0).get_edpt();
		for(int i=0; i<v_lines.size();i++){
			if( (pt.get_y() - v_lines.get(i).get_stpt().get_y()) > Math.pow(10,-3)){
				pt = v_lines.get(i).get_stpt();
			}
		}
		return pt;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter number of points in the polygon: ");
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		Polygon p = new Polygon();
		p.readPolygon(n,scan);
		int result = 0;
		System.out.println("Enter the point to be checked: ");
		result = p.PtInsidePolygon(new Point(scan.nextDouble(),scan.nextDouble()));
		//System.out.println(result);		//result is -1 for inside, 1 for outside and 0 for on the polygon 
		if(result == -1 || result == 0 ){
			System.out.println("True");
		} else {
			System.out.println("False");
		}
		scan.close();
	}

}
