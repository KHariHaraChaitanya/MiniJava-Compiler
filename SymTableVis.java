// Helper for HW2/CS453.
import java.io.*;
import visitor.*;
import syntaxtree.*;
import java.util.*;
// Files are stored in the picojava directory/package.
//import picojava.*;

public class SymTableVis<A> extends GJDepthFirst<String,A> {
   // Auto class visitors--probably don't need to be overridden.
   //
    HashMap<String,ClassName> Symtable;
    String currentClass;
    String currentMethod;
   public SymTableVis(){
      Symtable = new HashMap<String,ClassName>();
   }

   public void printSymtable(){
      for (String name: this.Symtable.keySet()) {
         ClassName c = this.Symtable.get(name);
         System.out.println(name + " -> ");
         if(c.variables!=null){
         for (String memvar: c.variables.keySet()) {
            System.out.println("   memvar:"+memvar+" ");
         }
         }
         if(c.methods!=null){
         for (String memmethod: c.methods.keySet()) {
            System.out.print("   memmethod:"+memmethod+"(");
            MethodDef m = c.methods.get(memmethod);
            if(m.arglist!=null){
               for(int i=0;i<m.arglist.size();i++){
                  System.out.print(m.arglist.get(i).type+" "+m.arglist.get(i).name+" ");
               }
               System.out.println(")");
            }
            if(m.localVariables!=null){
            for (String localvar: m.localVariables.keySet()) {
               System.out.println("   -- localvar:"+localvar);
            }
         }
         }
      }
         
     }
   }
   public String visit(NodeList n, A argu) {
    String _ret=null; 
    int _count=0;
    for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
       e.nextElement().accept(this,argu);
       _count++;
    }
    return _ret;
 }

 public String visit(NodeListOptional n, A argu) {
    if ( n.present() ) {
       String _ret=null;  
       int _count=0;
       for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
          e.nextElement().accept(this,argu);
          _count++;
       }
       return _ret;
    }
    else
       return null;
 }

 public String visit(NodeOptional n, A argu) {
     
    if ( n.present() )
       return n.node.accept(this,argu);
    else
       return null;
 }

 public String visit(NodeSequence n, A argu) {
    String _ret=null;  
    int _count=0;
    for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
       e.nextElement().accept(this,argu);
       _count++;
    }
    return _ret;
 }

 public String visit(NodeToken n, A argu) { return null; }

 //
 // User-generated visitor methods below
 //

 /**
  * f0 -> MainClass()
  * f1 -> ( TypeDeclaration() )*
  * f2 -> <EOF>
  */
 public String visit(Goal n, A argu) {
    String _ret=null; 
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    //printSymtable();
    return _ret;
 }

 /**
  * f0 -> "class"
  * f1 -> Identifier()
  * f2 -> "{"
  * f3 -> "public"
  * f4 -> "static"
  * f5 -> "void"
  * f6 -> "main"
  * f7 -> "("
  * f8 -> "String"
  * f9 -> "["
  * f10 -> "]"
  * f11 -> Identifier()
  * f12 -> ")"
  * f13 -> "{"
  * f14 -> ( VarDeclaration() )*
  * f15 -> ( Statement() )*
  * f16 -> "}"
  * f17 -> "}"
  */
 public String visit(MainClass n, A argu) {
      String _ret=null;  
		n.f0.accept(this, argu);
		String className = n.f1.accept(this, argu);
      this.Symtable.put(className,new ClassName(className));
      this.currentClass=className;
		n.f2.accept(this, argu);
		n.f3.accept(this, argu);
		n.f4.accept(this, argu);
		n.f5.accept(this, argu);
      this.Symtable.get(className).methods.put("main",new MethodDef("main",className, "void"));
      this.currentMethod="main";
		n.f6.accept(this, argu);
		n.f7.accept(this, argu);
		n.f8.accept(this, argu);
		n.f9.accept(this, argu);
		n.f10.accept(this, argu);
		String argsName = n.f11.accept(this, argu);
      this.Symtable.get(className).methods.get("main").localVariables.put(argsName,new LocalVariable(argsName, this.currentClass, "cmdlineargs", this.currentMethod));
		n.f12.accept(this, argu);
		n.f13.accept(this, argu);
		n.f14.accept(this, argu);
      n.f15.accept(this, argu);
		n.f16.accept(this, argu);
      this.currentMethod = null;
		n.f17.accept(this, argu);
		return _ret;
 }

 /**
  * f0 -> ClassDeclaration()
  *       | ClassExtendsDeclaration()
  */
 public String visit(TypeDeclaration n, A argu) {
    String _ret=null; 
    n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "class"
  * f1 -> Identifier()
  * f2 -> "{"
  * f3 -> ( VarDeclaration() )*
  * f4 -> ( MethodDeclaration() )*
  * f5 -> "}"
  */
 public String visit(ClassDeclaration n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    String className = n.f1.accept(this, argu);
    if(this.Symtable.containsKey(className)) throw new RuntimeException("Type error");
    this.currentClass=className;
    this.Symtable.put(className,new ClassName(className));
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    n.f5.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "class"
  * f1 -> Identifier()
  * f2 -> "extends"
  * f3 -> Identifier()
  * f4 -> "{"
  * f5 -> ( VarDeclaration() )*
  * f6 -> ( MethodDeclaration() )*
  * f7 -> "}"
  */
 public String visit(ClassExtendsDeclaration n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    String className = n.f1.accept(this, argu);
    this.currentClass=className;
    if(this.Symtable.containsKey(className)) throw new RuntimeException("Type error");
    this.Symtable.put(className,new ClassName(className));
    n.f2.accept(this, argu);
    String superclassName = n.f3.accept(this, argu);
    if(!this.Symtable.containsKey(superclassName)) throw new RuntimeException("Type error");
    ClassName superclass = this.Symtable.get(superclassName);
    if(superclass.methods!=null){
             for (String memmethod: superclass.methods.keySet()) {
                MethodDef m = superclass.methods.get(memmethod);
                this.Symtable.get(className).methods.put(memmethod,m);
             }
          }
   if(superclass.variables!=null){
      for (String memvar: superclass.variables.keySet()) {
         MemberVariable v = superclass.variables.get(memvar);
         this.Symtable.get(className).variables.put(memvar,v);
      }
   }
   superclass.subClasses.add(className);
   while(superclass.superClassName!=null){ 
      superclassName = this.Symtable.get(superclass.superClassName).name;
      superclass = this.Symtable.get(superclassName);
      superclass.subClasses.add(className);
   }
      
    n.f4.accept(this, argu);
    n.f5.accept(this, argu);
    n.f6.accept(this, argu);
    n.f7.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> Type()
  * f1 -> Identifier()
  * f2 -> ";"
  */
 public String visit(VarDeclaration n, A argu) {
    String _ret=null;
    String variableType = n.f0.accept(this, argu);
    String variableName = n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    ClassName c = this.Symtable.get(this.currentClass);
    if(this.currentMethod==null){
      if(c.variables.containsKey(variableName))  throw new RuntimeException("Type error");
      c.variables.put(variableName,new MemberVariable(variableName,this.currentClass, variableType));
    }
    else{
      if(c.methods.get(this.currentMethod).localVariables.containsKey(variableName))  throw new RuntimeException("Type error");
      c.methods.get(this.currentMethod).localVariables.put(variableName,new LocalVariable(variableName, this.currentClass, variableType, this.currentMethod));
    }
    return _ret;
 }

 /**
  * f0 -> "public"
  * f1 -> Type()
  * f2 -> Identifier()
  * f3 -> "("
  * f4 -> ( FormalParameterList() )?
  * f5 -> ")"
  * f6 -> "{"
  * f7 -> ( VarDeclaration() )*
  * f8 -> ( Statement() )*
  * f9 -> "return"
  * f10 -> Expression()
  * f11 -> ";"
  * f12 -> "}"
  */
 public String visit(MethodDeclaration n, A argu) {
    String _ret=null;  
    
    n.f0.accept(this, argu);
    String returnType = n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    String methodName = n.f2.accept(this, argu);
    MethodDef m = new MethodDef(methodName,this.currentClass, returnType);
    if(this.Symtable.get(this.currentClass).methods.containsKey(methodName)){
       if(this.Symtable.get(this.currentClass).methods.get(methodName).className.equals(this.currentClass)) 
      throw new RuntimeException("Type error");
    }
    this.Symtable.get(this.currentClass).methods.put(methodName,m);
    this.currentMethod=methodName;
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    n.f5.accept(this, argu);
    n.f6.accept(this, argu);
    n.f7.accept(this, argu);
    n.f8.accept(this, argu);
    n.f9.accept(this, argu);
    n.f10.accept(this, argu);
    n.f11.accept(this, argu);
    n.f12.accept(this, argu);
    this.currentMethod=null;
    return _ret;
 }

 /**
  * f0 -> FormalParameter()
  * f1 -> ( FormalParameterRest() )*
  */
 public String visit(FormalParameterList n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> Type()
  * f1 -> Identifier()
  */
 public String visit(FormalParameter n, A argu) {
    String _ret=null;  
    String variableType = n.f0.accept(this, argu);
    String variableName = n.f1.accept(this, argu);
    ClassName c = this.Symtable.get(this.currentClass);
    LocalVariable v = new LocalVariable(variableName, this.currentClass, variableType, this.currentMethod);
   if(c.methods.get(this.currentMethod).localVariables.containsKey(variableName))  throw new RuntimeException("Type error");
   c.methods.get(this.currentMethod).localVariables.put(variableName,v);
   c.methods.get(this.currentMethod).arglist.add(v);
    return _ret;
 }

 /**
  * f0 -> ","
  * f1 -> FormalParameter()
  */
 public String visit(FormalParameterRest n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> ArrayType()
  *       | BooleanType()
  *       | IntegerType()
  *       | Identifier()
  */
 public String visit(Type n, A argu) {
    String _ret=null;  
    _ret = n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "int"
  * f1 -> "["
  * f2 -> "]"
  */
 public String visit(ArrayType n, A argu) {
    String _ret="ArrayType"; 
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "boolean"
  */
 public String visit(BooleanType n, A argu) {
    String _ret="boolean"; 
    n.f0.accept(this, argu);
    //_ret = "boolean";
    return _ret;
 }

 /**
  * f0 -> "int"
  */
 public String visit(IntegerType n, A argu) {
    String _ret="int";  
    n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> Block()
  *       | AssignmentStatement()
  *       | ArrayAssignmentStatement()
  *       | IfStatement()
  *       | WhileStatement()
  *       | PrintStatement()
  */
 public String visit(Statement n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "{"
  * f1 -> ( Statement() )*
  * f2 -> "}"
  */
 public String visit(Block n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> Identifier()
  * f1 -> "="
  * f2 -> Expression()
  * f3 -> ";"
  */
 public String visit(AssignmentStatement n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> Identifier()
  * f1 -> "["
  * f2 -> Expression()
  * f3 -> "]"
  * f4 -> "="
  * f5 -> Expression()
  * f6 -> ";"
  */
 public String visit(ArrayAssignmentStatement n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    n.f5.accept(this, argu);
    n.f6.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "if"
  * f1 -> "("
  * f2 -> Expression()
  * f3 -> ")"
  * f4 -> Statement()
  * f5 -> "else"
  * f6 -> Statement()
  */
 public String visit(IfStatement n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    n.f5.accept(this, argu);
    n.f6.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "while"
  * f1 -> "("
  * f2 -> Expression()
  * f3 -> ")"
  * f4 -> Statement()
  */
 public String visit(WhileStatement n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "System.out.println"
  * f1 -> "("
  * f2 -> Expression()
  * f3 -> ")"
  * f4 -> ";"
  */
 public String visit(PrintStatement n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> AndExpression()
  *       | CompareExpression()
  *       | PlusExpression()
  *       | MinusExpression()
  *       | TimesExpression()
  *       | ArrayLookup()
  *       | ArrayLength()
  *       | MessageSend()
  *       | PrimaryExpression()
  */
 public String visit(Expression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "&&"
  * f2 -> PrimaryExpression()
  */
 public String visit(AndExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "<"
  * f2 -> PrimaryExpression()
  */
 public String visit(CompareExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "+"
  * f2 -> PrimaryExpression()
  */
 public String visit(PlusExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "-"
  * f2 -> PrimaryExpression()
  */
 public String visit(MinusExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "*"
  * f2 -> PrimaryExpression()
  */
 public String visit(TimesExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "["
  * f2 -> PrimaryExpression()
  * f3 -> "]"
  */
 public String visit(ArrayLookup n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "."
  * f2 -> "length"
  */
 public String visit(ArrayLength n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "."
  * f2 -> Identifier()
  * f3 -> "("
  * f4 -> ( ExpressionList() )?
  * f5 -> ")"
  */
 public String visit(MessageSend n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    n.f5.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> Expression()
  * f1 -> ( ExpressionRest() )*
  */
 public String visit(ExpressionList n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> ","
  * f1 -> Expression()
  */
 public String visit(ExpressionRest n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> IntegerLiteral()
  *       | TrueLiteral()
  *       | FalseLiteral()
  *       | Identifier()
  *       | ThisExpression()
  *       | ArrayAllocationExpression()
  *       | AllocationExpression()
  *       | NotExpression()
  *       | BracketExpression()
  */
 public String visit(PrimaryExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> <INTEGER_LITERAL>
  */
  @Override
 public String visit(IntegerLiteral n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "true"
  */
 public String visit(TrueLiteral n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "false"
  */
 public String visit(FalseLiteral n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> <IDENTIFIER>
  */
 public String visit(Identifier n, A argu) {
    String _ret=null; 
    n.f0.accept(this, argu);
    _ret = (n.f0).toString();
    return _ret;
 }

 /**
  * f0 -> "this"
  */
 public String visit(ThisExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "new"
  * f1 -> "int"
  * f2 -> "["
  * f3 -> Expression()
  * f4 -> "]"
  */
 public String visit(ArrayAllocationExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "new"
  * f1 -> Identifier()
  * f2 -> "("
  * f3 -> ")"
  */
 public String visit(AllocationExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "!"
  * f1 -> Expression()
  */
 public String visit(NotExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> "("
  * f1 -> Expression()
  * f2 -> ")"
  */
 public String visit(BracketExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

	}
