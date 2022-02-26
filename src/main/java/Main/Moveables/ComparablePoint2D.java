package Main.Moveables;


import javafx.geometry.Point2D;

public class ComparablePoint2D extends Point2D implements Comparable{
    public ComparablePoint2D(double x, double y){
        super(x,y);
    }



    @Override
    public int compareTo(Object o) {
        if(o.getClass() == Point2D.class) {
            Point2D object = (Point2D) o;
            if (this.equals(object))
                return 0;
        }
        return -1;
    }
}
