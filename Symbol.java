import java.util.*;
public class Symbol {
    String name;
}
class ClassName extends Symbol{
    HashMap<String, MemberVariable> variables;
    HashMap<String, MethodDef> methods ;
    ArrayList<String> subClasses;
    String superClassName;
    ClassName(String name){
        this.name = name;
        variables = new HashMap<>();
        methods = new HashMap<>();
        subClasses = new ArrayList<String>();
    }
}
class MemberVariable extends Symbol{
    String className;
    String type;
    MemberVariable(String name, String className, String type){
        this.name = name;
        this.className = className;
        this.type = type;
    }
}
class LocalVariable extends MemberVariable{
    String methodName;
    LocalVariable(String name, String className, String type, String methodName){
        super(name,className,type);
        this.methodName = methodName;
    }
}
class MethodDef extends MemberVariable{
    HashMap<String,LocalVariable> localVariables; 
    ArrayList<LocalVariable> arglist;
    MethodDef(String name, String className, String type){
        super(name,className,type);
        localVariables = new HashMap<>();
        arglist = new ArrayList<>();
    }
} 


