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
	    // Pretty-print the tree. PPrinter inherits from
	    // GJDepthFirst<R,A>. R=Void, A=String.
        //PPrinter<String> pp = new PPrinter<>();
	    //root.accept(pp, "");
        

	    // Build the symbol table. Top-down visitor, inherits from
	    // GJDepthFirst<R,A>. R=Void, A=Integer.
	   
        SymTableVis<String> pv =	new SymTableVis<String>();
	    root.accept(pv,"");
	    HashMap<String, ClassName> symt = pv.Symtable;
        
	    // Do type checking. Bottom-up visitor, also inherits from
	    // GJDepthFirst. Visit functions return MyTpe (=R), and
	    // take a symbol table (HashMap<String,String>) as
	    // argument (=A). You may implement things differently of
	    // course!

        
       TypeCheckSimp<String> ts = new TypeCheckSimp<String>(symt);
	   root.accept(ts, ""); 
        
	    

	    // Ugly code not to be inspired from: "my" way of storing
	    // type info / typecheck property: if some of my internal
	    // structure is empty, then things don't typecheck for
	    // me. This is specific to my own implementation.

	    // if (res != null && res.type_array.size() > 0)
	    // 	System.out.println("Code typechecks");
	    // else
	    // 	System.out.println("Type error");
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
