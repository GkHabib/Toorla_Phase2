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

    private static ArrayList<String> astPrinterResult = new ArrayList<>();
    private static ArrayList<String> errors = new ArrayList<>();
    private static ArrayList<String> inheritanceErrors = new ArrayList<>();

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
    public static Boolean hasError() { return (errors.size() + inheritanceErrors.size() > 0); }
    public static ArrayList<String> getErrors() { return errors; }

    public static void addInheritanceError(String err) { inheritanceErrors.add(err); }

    public static void printErrors() {
        while(!errors.isEmpty() && !inheritanceErrors.isEmpty()) {
            if(errors.get(0).compareTo(inheritanceErrors.get(0)) > 0) {
                //print str2
                System.out.print(inheritanceErrors.get(0));
                inheritanceErrors.remove(0);
            }
            else if(errors.get(0).compareTo(inheritanceErrors.get(0)) < 0) {
                //print str1
                System.out.print(errors.get(0));
                errors.remove(0);
            }
        }
        if(errors.isEmpty()) {
            for (String str: inheritanceErrors) {
                System.out.print(str);
            }
        }
        else {
            for (String str: errors) {
                System.out.print(str);
            }
        }
    }

    public static void addAstPrinterResult(String str) {
        astPrinterResult.add(str);
    }
}
