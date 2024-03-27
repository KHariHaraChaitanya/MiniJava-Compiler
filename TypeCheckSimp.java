// Helper for HW2/CS453.
import java.io.*;
import visitor.*;
import syntaxtree.*;
import java.util.*;
// Files are stored in the picojava directory/package.
//import picojava.*;

public class TypeCheckSimp<A> extends GJDepthFirst<String,A> {
   // Auto class visitors--probably don't need to be overridden.
   //
    HashMap<String,ClassName> SymbolTable;
    String currentClass;
    String currentMethod;
   public TypeCheckSimp(HashMap<String,ClassName> symtable){
      this.SymbolTable = symtable;
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
       StringBuilder _ret=new StringBuilder();  
       for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
          String type = e.nextElement().accept(this,argu);
          _ret.append(type+",");
       }
       return _ret.substring(0,_ret.length()-1).toString();
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
      this.currentClass=className;
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        this.currentMethod="main";
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        n.f10.accept(this, argu);
        n.f11.accept(this, argu);;
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
    this.currentClass=className;
    //this.Symtable.put(className,new ClassName(className));
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
    n.f2.accept(this, argu);
    String superclassName = n.f3.accept(this, argu);
    //if(this.SymbolTable.get(superclassName)==null) throw new RuntimeException("Type error");
    ClassName superclass = this.SymbolTable.get(superclassName);
    // if(superclass.methods!=null){
    //          for (String memmethod: superclass.methods.keySet()) {
    //             MethodDef m = superclass.methods.get(memmethod);
    //             this.Symtable.get(className).methods.put(memmethod,m);
    //          }
    //       }
    //   if(superclass.variables!=null){
//       for (String memvar: superclass.variables.keySet()) {
//          MemberVariable v = superclass.variables.get(memvar);
//          this.Symtable.get(className).variables.put(memvar,v);
//       }
//    }   
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
    ClassName c = this.SymbolTable.get(this.currentClass);
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
    //String _ret=null;  
    
    n.f0.accept(this, argu);
    String expectedReturnType = n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    String methodName = n.f2.accept(this, argu);
   this.currentMethod=methodName;
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    n.f5.accept(this, argu);
    n.f6.accept(this, argu);
    n.f7.accept(this, argu);
    n.f8.accept(this, argu);
    n.f9.accept(this, argu);
    String returnType=n.f10.accept(this, argu);
    if(!expectedReturnType.equals(returnType)) throw new RuntimeException("Type error");
    n.f11.accept(this, argu);
    n.f12.accept(this, argu);
    this.currentMethod=null;
    return returnType;
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
    ClassName c = this.SymbolTable.get(this.currentClass);
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
    String id = n.f0.accept(this, argu);
   //  System.out.println("debug:: "+id+"--");
   //  System.out.println(this.currentMethod);
    String id_type;
    if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(id))
      id_type=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(id).type;
   else if(this.SymbolTable.get(this.currentClass).variables.containsKey(id))
      id_type=this.SymbolTable.get(this.currentClass).variables.get(id).type;
   else throw new RuntimeException("Type error");
    n.f1.accept(this, argu);   
    String e_type = n.f2.accept(this, argu);
    //System.out.println("debug:: "+e_type+"--");
    n.f3.accept(this, argu);
    //System.out.println("debug:: "+id_type+" "+e_type);
    if(!id_type.equals(e_type)) throw new RuntimeException("Type error");
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
    String id = n.f0.accept(this, argu);
    String id_type=id;
    if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(id))
      id_type=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(id).type;
    else if(this.SymbolTable.get(this.currentClass).variables.containsKey(id))
      id_type=this.SymbolTable.get(this.currentClass).variables.get(id).type;
    if(!id_type.equals("ArrayType"))throw new RuntimeException("Type error");
    n.f1.accept(this, argu);
    String etype1 = n.f2.accept(this, argu);
    if(!etype1.equals("int"))throw new RuntimeException("Type error");
    n.f3.accept(this, argu);
    n.f4.accept(this, argu);
    String etype2 = n.f5.accept(this, argu);
    if(!etype2.equals("int"))throw new RuntimeException("Type error");
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
    String etype=n.f2.accept(this, argu);
    if(!etype.equals("boolean"))throw new RuntimeException("Type error");
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
    String etype = n.f2.accept(this, argu);
    if(!etype.equals("boolean"))throw new RuntimeException("Type error");
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
    String rtype=n.f2.accept(this, argu);
    if(!rtype.equals("int")) throw new RuntimeException("Type error");
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
    String rtype=n.f0.accept(this, argu);
    return rtype;
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "&&"
  * f2 -> PrimaryExpression()
  */
 public String visit(AndExpression n, A argu) {
    String rtype1 = n.f0.accept(this, argu);
    if(!rtype1.equals("boolean")){
        if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype1))
            rtype1=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype1).type;           
    }
    if(!rtype1.equals("boolean")) throw new RuntimeException("Type error");
    n.f1.accept(this, argu);
    String rtype2 = n.f2.accept(this, argu);
    if(!rtype2.equals("boolean")){
        if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype2))
        rtype2=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype2).type;                   
    }
    if(!rtype2.equals("boolean")) throw new RuntimeException("Type error");
    return "boolean";
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "<"
  * f2 -> PrimaryExpression()
  */
 public String visit(CompareExpression n, A argu) {  
   String rtype1 = n.f0.accept(this, argu);
       if(!rtype1.equals("int")){
           if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype1))
               rtype1=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype1).type;           
       }
       if(!rtype1.equals("int")) throw new RuntimeException("Type error");
       n.f1.accept(this, argu);
       String rtype2 = n.f2.accept(this, argu);
       if(!rtype2.equals("int")){
                       if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype2))
                           rtype2=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype2).type;
                      
       }
       if(!rtype2.equals("int")) throw new RuntimeException("Type error");
    return "boolean";
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "+"
  * f2 -> PrimaryExpression()
  */
 public String visit(PlusExpression n, A argu) {
    String rtype1 = n.f0.accept(this, argu);
        if(!rtype1.equals("int")){
            if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype1))
                rtype1=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype1).type;           
        }
        if(!rtype1.equals("int")) throw new RuntimeException("Type error");
        n.f1.accept(this, argu);
        String rtype2 = n.f2.accept(this, argu);
        if(!rtype2.equals("int")){
                        if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype2))
                            rtype2=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype2).type;
                       
        }
        if(!rtype2.equals("int")) throw new RuntimeException("Type error");
    return "int";
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "-"
  * f2 -> PrimaryExpression()
  */
 public String visit(MinusExpression n, A argu) {
   String rtype1 = n.f0.accept(this, argu);
       if(!rtype1.equals("int")){
           if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype1))
               rtype1=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype1).type;           
       }
       if(!rtype1.equals("int")) throw new RuntimeException("Type error");
       n.f1.accept(this, argu);
       String rtype2 = n.f2.accept(this, argu);
       if(!rtype2.equals("int")){
                       if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype2))
                           rtype2=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype2).type;
                      
       }
       if(!rtype2.equals("int")) throw new RuntimeException("Type error");
    return "int";
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "*"
  * f2 -> PrimaryExpression()
  */
 public String visit(TimesExpression n, A argu) {
    String rtype1 = n.f0.accept(this, argu);
    if(!rtype1.equals("int")){
        if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype1))
            rtype1=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype1).type;           
    }
    if(!rtype1.equals("int")) throw new RuntimeException("Type error");
    n.f1.accept(this, argu);
    String rtype2 = n.f2.accept(this, argu);
    if(!rtype2.equals("int")){
                    if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(rtype2))
                        rtype2=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(rtype2).type;
                   
    }
    if(!rtype2.equals("int")) throw new RuntimeException("Type error");
    // implement this logic to other operations tbd
    return "int";
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "["
  * f2 -> PrimaryExpression()
  * f3 -> "]"
  */
 public String visit(ArrayLookup n, A argu) {
    String etype1 = n.f0.accept(this, argu);
    if(!etype1.equals("ArrayType"))throw new RuntimeException("Type error");
    n.f1.accept(this, argu);
    String etype2 = n.f2.accept(this, argu);
    if(!etype2.equals("int"))throw new RuntimeException("Type error");
    n.f3.accept(this, argu);
    return "int";
 }

 /**
  * f0 -> PrimaryExpression()
  * f1 -> "."
  * f2 -> "length"
  */
 public String visit(ArrayLength n, A argu) {
    //String _ret=null;  
    String etype=n.f0.accept(this, argu);
    if(!etype.equals("ArrayType"))throw new RuntimeException("Type error");
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return "int";
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
    String classtype = n.f0.accept(this, argu);
    if(classtype.equals("int")||classtype.equals("boolean"))throw new RuntimeException("Type error");
    if(classtype.equals("this"))classtype= this.currentClass;
    n.f1.accept(this, argu);
    String id = n.f2.accept(this, argu);
    MethodDef methodInvoked = this.SymbolTable.get(classtype).methods.get(id);
    if(!this.SymbolTable.get(classtype).methods.containsKey(id))throw new RuntimeException("Type error");
    _ret=this.SymbolTable.get(classtype).methods.get(id).type;
    n.f3.accept(this, argu);
    String params= n.f4.accept(this, argu);
    String paramlist[];
    if(params==null){
      if(methodInvoked.arglist.size()!=0)throw new RuntimeException("Type error");
    }
    else{
      paramlist=params.split(",");
      if(paramlist.length!=methodInvoked.arglist.size())throw new RuntimeException("Type error");
      for(int i=0;i<paramlist.length;i++){
         String expectedParam = methodInvoked.arglist.get(i).type;
         if(!paramlist[i].equals(expectedParam)){
            if(this.SymbolTable.containsKey(expectedParam)){
               boolean paramMatch= false;
               for(String subClass: this.SymbolTable.get(expectedParam).subClasses){
                  if(subClass.equals(paramlist[i])){ 
                     paramMatch = true;
                     break;
                  }
               }
               if(!paramMatch)throw new RuntimeException("Type error");
            }
            else throw new RuntimeException("Type error");
         }
      }  
    }
    n.f5.accept(this, argu);
    return _ret;
 }

 /**
  * f0 -> Expression()
  * f1 -> ( ExpressionRest() )*
  */
 public String visit(ExpressionList n, A argu) {
    //verify if all expected params are present in the same order  
    String type = n.f0.accept(this, argu);
    String paramslist = n.f1.accept(this, argu);
    if(paramslist!=null)
      return type+","+paramslist;
    return type;
 }

 /**
  * f0 -> ","
  * f1 -> Expression()
  */
 public String visit(ExpressionRest n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    String type = n.f1.accept(this, argu);
    return type;
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
    _ret=n.f0.accept(this, argu);
    if(_ret.equals("int")||_ret.equals("boolean")||_ret.equals("ArrayType")||this.SymbolTable.containsKey(_ret))
      return _ret;
   if(this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.containsKey(_ret))
      _ret=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(_ret).type;
   else if(this.SymbolTable.get(this.currentClass).variables.containsKey(_ret))
      _ret=this.SymbolTable.get(this.currentClass).variables.get(_ret).type;
   else throw new RuntimeException("Type error");
   return _ret;
 }

 /**
  * f0 -> <INTEGER_LITERAL>
  */
 public String visit(IntegerLiteral n, A argu) {
    //String _ret=null;  
    n.f0.accept(this, argu);
    return "int";
 }

 /**
  * f0 -> "true"
  */
 public String visit(TrueLiteral n, A argu) {
    //String _ret=null;  
    n.f0.accept(this, argu);
    return "boolean";
 }

 /**
  * f0 -> "false"
  */
 public String visit(FalseLiteral n, A argu) {
   // String _ret=null;  
    n.f0.accept(this, argu);
    return "boolean";
 }

 /**
  * f0 -> <IDENTIFIER>
  */
 public String visit(Identifier n, A argu) {
    String id=null; 
    n.f0.accept(this, argu);
    id = (n.f0).toString();
    // String rtype=null;
    // if(this.currentClass==null) rtype = id;
    // else{
    //     if(this.currentMethod==null)rtype=this.SymbolTable.get(this.currentClass).variables.get(id).type;
    //    // else if(this.currentMethod.equals("main")){}
    //     else rtype=this.SymbolTable.get(this.currentClass).methods.get(this.currentMethod).localVariables.get(id).type;
    // }
    return id;
 }

 /**
  * f0 -> "this"
  */
 public String visit(ThisExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    return this.currentClass;
 }

 /**
  * f0 -> "new"
  * f1 -> "int"
  * f2 -> "["
  * f3 -> Expression()
  * f4 -> "]"
  */
 public String visit(ArrayAllocationExpression n, A argu) {
    //String _ret=null;  
    n.f0.accept(this, argu);
    n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    String sizetype=n.f3.accept(this, argu);
    if(!sizetype.equals("int"))throw new RuntimeException("Type error");
    n.f4.accept(this, argu);
    return "ArrayType";
 }

 /**
  * f0 -> "new"
  * f1 -> Identifier()
  * f2 -> "("
  * f3 -> ")"
  */
 public String visit(AllocationExpression n, A argu) { 
    n.f0.accept(this, argu);
    String otype = n.f1.accept(this, argu);
    //System.out.println("--"+otype+"--");
    if(!this.SymbolTable.containsKey(otype))throw new RuntimeException("Type error");
    n.f2.accept(this, argu);
    n.f3.accept(this, argu);
    return otype;
 }

 /**
  * f0 -> "!"
  * f1 -> Expression()
  */
 public String visit(NotExpression n, A argu) {
    //String _ret=null;  
    n.f0.accept(this, argu);
    String etype=n.f1.accept(this, argu);
    if(!etype.equals("boolean"))throw new RuntimeException("Type error");
    return "boolean";
 }

 /**
  * f0 -> "("
  * f1 -> Expression()
  * f2 -> ")"
  */
 public String visit(BracketExpression n, A argu) {
    String _ret=null;  
    n.f0.accept(this, argu);
    _ret=n.f1.accept(this, argu);
    n.f2.accept(this, argu);
    return _ret;
 }

    }
