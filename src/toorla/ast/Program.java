package toorla.ast;

import toorla.ast.declaration.classDecs.ClassDeclaration;
import toorla.symbolTable.SymbolTable;
import toorla.visitor.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Program extends Tree {
    private static ArrayList<ClassDeclaration> classes = new ArrayList<>();
    private static HashMap<String, SymbolTable> classesSymbolTable = new HashMap<>();

    public static ArrayList<String> astPrinterResult = new ArrayList<>();
    public static ArrayList<String> errors = new ArrayList<>();

    private static int tempVarsNumber = 0;
    public static int passNum = 1;

    public void addClass(ClassDeclaration classDeclaration) {
        classes.add(classDeclaration);
    }
    public static void addClassSymbolTable(String name, SymbolTable st){ classesSymbolTable.put(name, st);}
    public void addClassFirst(ClassDeclaration classDeclaration){ classes.add(0, classDeclaration);}

    public List<ClassDeclaration> getClasses() {
        return classes;
    }

    @Override
    public String toString() {
        return "Program";
    }

    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    public static int getNewTempVarNumber() {
        return ++tempVarsNumber;
    }

    public static void addError(String err) {
        errors.add(err);
    }

    public static void addAstPrinterResult(String str) {
        astPrinterResult.add(str);
    }
}
