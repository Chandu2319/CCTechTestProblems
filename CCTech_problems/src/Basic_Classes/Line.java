package Basic_Classes;

import java.util.ArrayList;

public class Line extends Point {
	Point pt1;
	Point pt2;
	double angle;
	double slope;
	double length;
	double c; // y-intercept


	Line()
	{
		this.pt1 = new Point();
		this.pt2 = new Point();
	}
	
	Line(Point pt1, Point pt2)
	{
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.length = Point.dist_btw_2_pts(pt1, pt2);
		this.slope = this.find_slope();
		this.angle = this.find_angle(pt1, pt2);
		this.c = find_intercept();
	}
	
	Line ( Point pt1, double angle_d, double length)
	{
		this.pt1 = pt1;
		this.length = length;
		this.angle = get_angle_in_range(angle_d);
		this.slope = Math.tan(this.angle);
		this.pt2 = new Point(pt1.get_x() + length*Math.cos(this.angle),pt1.get_y() + length*Math.sin(this.angle));
		this.c = find_intercept();
	}

	void set_stpt(Point pt)
	{
		this.pt1 = pt;
		this.length = Point.dist_btw_2_pts(pt1, pt2);
		this.slope = this.find_slope();
		this.angle = this.find_angle(pt1, pt2);
		this.c = find_intercept();
	}
	
	void set_edpt(Point pt)
	{
		this.pt2 = pt;
		this.length = Point.dist_btw_2_pts(pt1, pt2);
		this.slope = this.find_slope();
		this.angle = this.find_angle(pt1, pt2);
		this.c = find_intercept();
	}
	
	double find_intercept()
	{
		double c;
		if (Math.abs(this.angle - Math.PI / 2) < Math.pow(10.0, -3) || Math.abs(this.angle + Math.PI / 2) < Math.pow(10.0, -3))
		{
			c = 0;
		}
		else
		{
			c = pt1.get_y() - slope*pt1.get_x();
		}
		return c;
	}
	
	double find_slope()
	{
		double slope;
		if (Math.abs(pt1.x - pt2.x) < Math.pow(10.0, -4))
		{
			slope = Math.pow(10.0, 28);
		}
		else
		{
			slope = (this.pt2.get_y() - this.pt1.get_y()) / (this.pt2.get_x() - this.pt1.get_x());
		}
		return slope;
	}
	
	double find_angle(Point pt1, Point pt2)
	{
		double ang = Math.atan2(pt2.get_y() - pt1.get_y(), pt2.get_x() - pt1.get_x());
		return ang;
	}
	
	double get_angle_in_range(double angle_d)
	{
		if (-180 < angle_d && angle_d <= 180)
		{
		}
		else
		{
			int c = (int) angle_d / 180;
			if (c % 2 == 0){
				angle_d = angle_d - (c / 2) * 360;
			}
			else
			{
				if (angle_d > 0)
					angle_d = angle_d - (c + 1) * 180;
				else
					angle_d = angle_d - (c - 1) * 180;
			}
		}
		return angle_d*Math.PI / 180;
	}
	
	Point get_stpt()
	{
		return this.pt1;
	}
	Point get_edpt()
	{
		return this.pt2;
	}
	double get_slope()
	{
		return this.slope;
	}
	double get_angle()
	{
		return this.angle * 180 / Math.PI;
	}
	double get_length(){
		return this.length;
	}
	Point get_midpt()
	{
		Point mpt;
		mpt = (this.pt1.plus(this.pt2)).divide(2);
		return mpt;
	}

	static ArrayList<Point> find_intsctn_pt_lines(Line l1, Line l2)
	{
		ArrayList<Point> ipt = new ArrayList<Point>();
		Point pt = new Point();
		if (Math.abs(l1.angle - l2.angle) < Math.pow(10.0, -3) || Math.abs(Math.abs(l1.angle - l2.angle) - Math.PI) < Math.pow(10, -3))
		{
			//System.out.println("The given two lines are parallel");
		}
		else if (Math.abs(l1.angle - Math.PI / 2) < Math.pow(10.0, -3) || Math.abs(l1.angle + Math.PI / 2) < Math.pow(10.0, -3))
		{
			pt.x = l1.pt1.x;
			pt.y = l2.slope*pt.x + l2.c;
			ipt.add(new Point(pt.get_x(),pt.get_y()));
		}
		else if (Math.abs(l2.angle - Math.PI / 2) < Math.pow(10.0, -3) || Math.abs(l2.angle + Math.PI / 2) < Math.pow(10.0, -3))
		{
			pt.x = l2.pt1.x;
			pt.y = l1.slope*pt.x + l1.c;
			ipt.add(new Point(pt.get_x(),pt.get_y()));
		}
		else
		{
			pt.x = (l2.c - l1.c) / (l1.slope - l2.slope);
			pt.y = l1.slope*pt.x + l1.c;
			ipt.add(new Point(pt.get_x(),pt.get_y()));
		}
		return ipt;
	}
	
	static ArrayList<Point> find_intsctn_pt_linesegment(Line l1, Line l2)
	{
		ArrayList<Point> in_pt;
		in_pt = find_intsctn_pt_lines(l1, l2);

		if (in_pt.size() == 0)
		{
			if (Line.dist_pt_frm_line(l2.pt1, l1) < Math.pow(10, -3))
			{
				if (l1.is_pt_on_line_seg(l2.pt1))
					in_pt.add(l2.pt1);
				if (l1.is_pt_on_line_seg(l2.pt2))
				{
					if (in_pt.size() == 0)
					{
						in_pt.add(l2.pt2);
					}
					else if (in_pt.get(0) != l2.pt2)
						in_pt.add(l2.pt2);
				}
				if (l2.is_pt_on_line_seg(l1.pt1))
				{
					if (in_pt.size() == 0)
					{
						in_pt.add(l2.pt2);
					}
					else if (in_pt.get(0) != l1.pt1)
						in_pt.add(l1.pt1);
				}
				if (l2.is_pt_on_line_seg(l1.pt2))
				{
					if (in_pt.size() == 0)
					{
						in_pt.add(l2.pt2);
					}
					else if (in_pt.get(0) != l1.pt2)
						in_pt.add(l1.pt2);
				}
			}
			return in_pt;
		}
		if ( (l1.is_pt_on_line_seg(in_pt.get(0))) && (l2.is_pt_on_line_seg(in_pt.get(0))) )	{}
		else {
			in_pt.remove(0);
		}
		return in_pt;
	}
	
	static double dist_pt_frm_line(Point pt, Line l)
	{
		double dis;
		if (Math.abs(l.angle - Math.PI / 2) < Math.pow(10.0, -3) || Math.abs(l.angle + Math.PI / 2) < Math.pow(10.0, -3))
		{
			dis = Math.abs(pt.x - l.pt1.x);
		}
		else
		{
			dis = Math.abs((pt.y - l.slope*pt.x - l.c) / Math.pow((1 + Math.pow(l.slope, 2)),0.5));
		}
		return dis;
	}
	
	boolean is_pt_on_line(Point pt)
	{
		/*if (dist_pt_frm_line( pt, *this) < Math.pow(10, -3))
			return true;
		else
			return false;*/
		if (Math.abs(this.pt1.x - this.pt2.x) < Math.pow(10, -3))
		{
			return (Math.abs(this.pt1.x - pt.x) < Math.pow(10, -3));
		}
		else
		{
			return (Math.abs(pt.y - this.slope*pt.x - c) < Math.pow(10, -3));
		}
	}
	
	boolean is_pt_on_line_seg ( Point pt )
	{
		Point st, ed;
		if (is_pt_on_line(pt))
		{
			if (Math.abs(pt1.y - pt2.y) < Math.pow(10, -3))
			{
				if ((pt1.x - pt2.x) < Math.pow(10, -3))
					return ((pt1.x - pt.x) < Math.pow(10, -3) && (pt.x - pt2.x) < Math.pow(10, -3));
				else
					return ((pt2.x - pt.x) < Math.pow(10, -3) && (pt.x - pt1.x) < Math.pow(10, -3));
			}
			if (this.angle < Math.pow(10, -3))
			{
				st = this.pt2;
				ed = this.pt1;
			}
			else
			{
				st = this.pt1;
				ed = this.pt2;
			}

			if ((st.y <= pt.y) && (pt.y <= ed.y))
				return true;
			else
				return false;
		}
		else
			return false;
	}

	double angle_subtended_at_pt( Point pt) {
		double output_angle = 0;
		Line l1 = new Line(pt,this.get_stpt());
		Line l2 = new Line(pt,this.get_edpt());
		output_angle = l2.angle - l1.angle;
		if(output_angle > Math.PI ){
			output_angle =  output_angle - 2*Math.PI;
		} else if (output_angle < -Math.PI){
			output_angle = output_angle + 2*Math.PI;
		}
		return output_angle*180/Math.PI;
	}
}
