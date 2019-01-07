package Shapes;

import Graph.*;
import java.io.File;
import java.util.Set;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class SVGParser {

    public static List<Shape> fileToShapes(String path) throws FileNotFoundException{
        File file = new File(path);
        Scanner sc = new Scanner(file);
        List<Shape> shapes = new LinkedList<Shape>();
        // we just need to use \\Z as delimiter
        sc.useDelimiter("<");
        while(sc.hasNext()){
            String line = sc.next();
            //System.out.println(line);
            if(line.length()>6){
                String shape_name = line.substring(0,7);
                if(shape_name.equals("ellipse")){
                    //System.out.println(line.substring(8,40));
                    double x = Double.parseDouble(line.substring(line.indexOf("cx=\"")+4,line.indexOf("\"",line.indexOf("cx=\"")+4)));
                    double y = Double.parseDouble(line.substring(line.indexOf("cy=\"")+4,line.indexOf("\"",line.indexOf("cy=\"")+4)));
                    double r = Double.parseDouble(line.substring(line.indexOf("rx=\"")+4,line.indexOf("\"",line.indexOf("rx=\"")+4)));
                    Shape ellipse = new Circle(x,y,r);
                    //System.out.println(ellipse);
                    shapes.add(ellipse);
                }
            }
        }
        sc.close();
        return shapes;
    }

    public static Boolean decide(String path1,String path2) throws FileNotFoundException{
        List<Shape> shapes1 = fileToShapes(path1);
        List<Shape> shapes2 = fileToShapes(path2);
        Graph g1 = CheckingAlgorithm.createGraph(shapes1);
        Graph g2 = CheckingAlgorithm.createGraph(shapes2);
        System.out.println("Graph 1:");
        System.out.println(g1);
        System.out.println("Graph 2:");
        System.out.println(g2);
        Pair<Set<List<Edge>>,Set<List<Edge>>> p=CheckingAlgorithm.checkAlgorithem(g1, g2);
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
		/*
		String path1_Yes = "C:\\Users\\sapir\\Desktop\\SVG\\Diagram1.svg";
		String path2_Yes = "C:\\Users\\sapir\\Desktop\\SVG\\Diagram2.svg";
		if(decide(path1_Yes,path2_Yes))
			System.out.println("yes!!!!");
		else
			System.out.println("no");*/
        String path1_No = "C:\\Users\\sapir\\Desktop\\SVG\\threeConnected.svg";
        String path2_No = "C:\\Users\\sapir\\Desktop\\SVG\\threeSeperate.svg";
        if(decide(path1_No,path2_No))
            System.out.println("yes!!!!");
        else
            System.out.println("no");
		/*
		String path1_No = "C:\\Users\\sapir\\Desktop\\SVG\\1.svg";
		String path2_No = "C:\\Users\\sapir\\Desktop\\SVG\\2.svg";
		if(decide(path1_No,path2_No))
			System.out.println("yes!!!!");
		else
			System.out.println("no");*/

    }
}