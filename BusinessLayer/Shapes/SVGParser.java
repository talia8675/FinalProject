package Shapes;

import Graph.*;
import java.io.File;
import java.util.Set;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class SVGParser {
    public static void zero(List<Shape> shapes){
        //find min Y
        double minY = shapes.get(0).getMinY();
        for(Shape s : shapes){
            if (s.getMinY() < minY){
                minY = s.getMinY();
            }
        }
        for(Shape s : shapes){
            s.moveYbyH(minY);
        }

        //find min X
        double minX = shapes.get(0).getMinX();
        for(Shape s : shapes){
            if (s.getMinX() < minX){
                minX = s.getMinX();
            }
        }
        for(Shape s : shapes){
            s.moveXbyH(minX);
        }
    }

    public static Pair<List<Shape>,List<Shape>> fileToShapes(String path) throws FileNotFoundException{
        File file = new File(path);
        Scanner sc1 = new Scanner(file);
        Scanner sc2 = new Scanner(file);
        List<Shape> shapes1 = new LinkedList<Shape>();
        List<Shape> shapes2 = new LinkedList<Shape>();
        //Find the line in the middle
        double middle = 0;
        sc1.useDelimiter("<");
        while(sc1.hasNext()) {
            String line = sc1.next();
            if(line.length()>6){
                String shape_name = line.substring(0,line.indexOf(" "));
                if(shape_name.equals("path")) {
                    String d = line.substring(line.indexOf("d=\"")+3,line.indexOf("\"",line.indexOf("d=\"")+3));
                    String[] vals = d.split(" ");
                    double xStart = Double.parseDouble(vals[1]);
                    double yStart = Double.parseDouble(vals[2]);
                    double xEnd = Double.parseDouble(vals[4]);
                    //double yEnd = Double.parseDouble(vals[5]);
                    if(xStart == 1 && xEnd == 825){
                        middle = yStart;
                    }
                }
            }
        }
        sc1.close();
        // we just need to use \\Z as delimiter
        sc2.useDelimiter("<");
        while(sc2.hasNext()){
            String line = sc2.next();
            System.out.println(line);
            if(line.length()>6){
                String shape_name = line.substring(0,line.indexOf(" "));
                if(shape_name.equals("ellipse")){
                    //System.out.println(line.substring(8,40));
                    double x = Double.parseDouble(line.substring(line.indexOf("cx=\"")+4,line.indexOf("\"",line.indexOf("cx=\"")+4)));
                    double y = Double.parseDouble(line.substring(line.indexOf("cy=\"")+4,line.indexOf("\"",line.indexOf("cy=\"")+4)));
                    double r = Double.parseDouble(line.substring(line.indexOf("rx=\"")+4,line.indexOf("\"",line.indexOf("rx=\"")+4)));
                    Shape ellipse = new Circle(x,y,r);
                    if(y<middle)
                        shapes1.add(ellipse);
                    else
                        shapes2.add(ellipse);
                }
                else{
                    if(shape_name.equals("path")) {
                        String d = line.substring(line.indexOf("d=\"")+3,line.indexOf("\"",line.indexOf("d=\"")+3));
                        String[] vals = d.split(" ");
                        double xStart = Double.parseDouble(vals[1]);
                        double yStart = Double.parseDouble(vals[2]);
                        double xEnd = Double.parseDouble(vals[4]);
                        double yEnd = Double.parseDouble(vals[5]);
                        Line l = new Line(xStart, yStart, xEnd, yEnd);
                        if(yStart<middle)
                            shapes1.add(l);
                        else {
                            if(yStart!=middle)
                                shapes2.add(l);
                        }
                    }
                }
            }
        }
        sc2.close();
        zero(shapes1);
        zero(shapes2);
        Pair<List<Shape>,List<Shape>> p = new Pair<>(shapes1,shapes2);
        return p;
    }

    public static Boolean decide(String path1) throws FileNotFoundException{
        Pair<List<Shape>,List<Shape>> shapes = fileToShapes(path1);
        Graph g1 = CheckingAlgorithm.createGraph(shapes.getFirst());
        Graph g2 = CheckingAlgorithm.createGraph(shapes.getSecond());
        System.out.println("Graph 1:");
        System.out.println(g1);
        System.out.println("Graph 2:");
        System.out.println(g2);
        Pair<Set<List<Edge>>,Set<List<Edge>>> p =CheckingAlgorithm.checkAlgorithem(g1, g2);
        System.out.println("ans :");
        System.out.println(p);
        if(p!=null){
            return true;
        }
        else
            return false;
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        String path1_No = "C:\\Users\\hp\\Downloads\\DiagramCircles2.svg";
        if(decide(path1_No))
            System.out.println("yes!!!!");
        else
            System.out.println("no");
    }
}