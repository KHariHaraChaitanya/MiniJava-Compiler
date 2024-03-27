// Helper for HW2/CS453.

import java.io.*;
import visitor.*;
import syntaxtree.*;
import java.util.*;
//import CS453.*;

public class Typecheck {
    public static void main(String[] args) {
		MiniJavaParser parser = new MiniJavaParser(System.in);
	Goal root = null;
	try {
        root = MiniJavaParser.Goal();
	   
        SymTableVis<String> pv =	new SymTableVis<String>();
	    root.accept(pv,"");
	    HashMap<String, ClassName> symt = pv.Symtable;
       TypeCheckSimp<String> ts = new TypeCheckSimp<String>(symt);
	   root.accept(ts, ""); 
	}
	catch (ParseException e) {
	    
		System.out.println(e.toString());
	    System.exit(1);
	}
	 catch(Exception e){
	 	System.out.println(e.toString().split(": ")[1]);
		System.exit(1);
	 }
	System.out.println("Program type checked successfully");
    }
}
