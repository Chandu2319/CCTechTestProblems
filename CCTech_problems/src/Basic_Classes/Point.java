package Basic_Classes;

public class Point {

		protected double x;
		protected double y;

		Point()
		{
			x = 0;
			y = 0;
		}
		
		Point(double x, double y)
		{
			set_x(x);
			set_y(y);
		}

		void set_x(double x)
		{
			this.x = x;
		}
		
		void set_y(double y)
		{
			this.y = y;
		}
		
		double get_x()
		{
			return this.x;
		}
		
		double get_y()
		{
			return this.y;
		}

		Point plus(Point pt)
		{
			Point sum = new Point();
			sum.set_x(this.x + pt.get_x());
			sum.set_y(this.y + pt.get_y());
			return sum;
		}
		
		Point minus(Point pt)
		{
			Point sum = new Point();
			sum.set_x(this.x - pt.get_x());
			sum.set_y(this.y - pt.get_y());
			return sum;
		}
		Point into(double f) 
		{
			Point product = new Point();
			product.x = this.x * f;
			product.y = this.y * f;
			return product;
		}
		Point divide(double f)
		{
			Point product = new Point();
			product.x = this.x / f;
			product.y = this.y / f;
			return product;
		}
		void assign(Point pt)
		{
			this.x = pt.get_x();
			this.y = pt.get_y();
		}
		
		boolean isEquals(Point pt) 
		{
			if (Math.abs(this.x - pt.get_x()) <Math.pow(10.0, -3) && Math.abs(this.y - pt.get_y()) < Math.pow(10.0, -3))
				return true;
			else
				return false;
		}
		
		boolean isNotEquals(Point pt)
		{
			if (Math.abs(this.x - pt.get_x()) < Math.pow(10.0, -3) && Math.abs(this.y - pt.get_y()) < Math.pow(10.0, -3))
				return false;
			else
				return true;
		}
		
		static double dist_btw_2_pts(Point pt1, Point pt2)
		{
			double distance;
			distance = Math.sqrt(Math.pow(pt1.get_x() - pt2.get_x(), 2) + Math.pow(pt1.get_y() - pt2.get_y(), 2));
			return distance;
		}
}
