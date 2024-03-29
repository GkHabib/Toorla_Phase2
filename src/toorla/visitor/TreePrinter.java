package toorla.visitor;

//import sun.jvm.hotspot.debugger.cdbg.Sym;
import toorla.ast.Program;
import toorla.ast.declaration.VariableDeclaration;
import toorla.ast.declaration.classDecs.ClassDeclaration;
import toorla.ast.declaration.classDecs.EntryClassDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.ClassMemberDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.FieldDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.MethodDeclaration;
import toorla.ast.declaration.localVarDecs.ParameterDeclaration;
import toorla.ast.expression.*;
import toorla.ast.expression.binaryExpression.*;
import toorla.ast.expression.unaryExpression.Neg;
import toorla.ast.expression.unaryExpression.Not;
import toorla.ast.expression.value.BoolValue;
import toorla.ast.expression.value.IntValue;
import toorla.ast.expression.value.StringValue;
import toorla.ast.statement.*;
import toorla.ast.statement.localVarStats.LocalVarDef;
import toorla.ast.statement.localVarStats.LocalVarsDefinitions;
import toorla.ast.statement.returnStatement.Return;
import toorla.symbolTable.SymbolTable;
import toorla.symbolTable.exceptions.ItemAlreadyExistsException;
import toorla.symbolTable.exceptions.ItemNotFoundException;
import toorla.symbolTable.symbolTableItem.SymbolTableClassItem;
import toorla.symbolTable.symbolTableItem.SymbolTableFieldItem;
import toorla.symbolTable.symbolTableItem.SymbolTableMethodItem;
import toorla.symbolTable.symbolTableItem.varItems.LocalVariableSymbolTableItem;
import toorla.types.Type;

import java.util.ArrayList;

public class TreePrinter implements Visitor<Void> {
    @Override
    public Void visit(PrintLine printLine) {
        if(Program.passNum == 1) {
            //do nothing!
        }
        else {
            System.out.print("(print ");
            printLine.getArg().accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(Assign assign) {
        if(Program.passNum == 1) {
            //do nothing!
        }
        else {
            System.out.print("(= ");
            assign.getLvalue().accept(this);
            System.out.print(" ");
            assign.getRvalue().accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(Block block) {

        if(Program.passNum == 1) {
            SymbolTable.push(new SymbolTable(SymbolTable.top));
            for (Statement s : block.body)
                s.accept(this);
            SymbolTable.pop();
        }
        else {
            System.out.println("( ");
            for (Statement s : block.body)
                s.accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(Conditional conditional) {

        if(Program.passNum == 1) {
            SymbolTable.push(new SymbolTable(SymbolTable.top));
            conditional.getThenStatement().accept(this);
            SymbolTable.pop();
            SymbolTable.push(new SymbolTable(SymbolTable.top));
            conditional.getElseStatement().accept(this);
            SymbolTable.pop();
        }
        else {
            System.out.print("(if ");
            conditional.getCondition().accept(this);
            System.out.print(" ");
            conditional.getThenStatement().accept(this);
            System.out.print(" ");
            conditional.getElseStatement().accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(While whileStat) {
        if(Program.passNum == 1) {
            SymbolTable.push(new SymbolTable(SymbolTable.top));
            whileStat.body.accept(this);
            SymbolTable.pop();
        }
        else {
            System.out.print("(while ");
            whileStat.expr.accept(this);
            System.out.print(" ");
            whileStat.body.accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(Return returnStat) {
        System.out.print("(return ");
        returnStat.getReturnedExpr().accept(this);
        System.out.println(")");
        return null;
    }

    @Override
    public Void visit(Plus plusExpr) {
        System.out.print("(+ ");
        plusExpr.getLhs().accept(this);
        System.out.print(" ");
        plusExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Minus minusExpr) {
        System.out.print("(- ");
        minusExpr.getLhs().accept(this);
        System.out.print(" ");
        minusExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Times timesExpr) {
        System.out.print("(* ");
        timesExpr.getLhs().accept(this);
        System.out.print(" ");
        timesExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Division divisionExpr) {
        System.out.print("(/ ");
        divisionExpr.getLhs().accept(this);
        System.out.print(" ");
        divisionExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Modulo moduloExpr) {
        System.out.print("(% ");
        moduloExpr.getLhs().accept(this);
        System.out.print(" ");
        moduloExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Equals equalsExpr) {
        System.out.print("(== ");
        equalsExpr.getLhs().accept(this);
        System.out.print(" ");
        equalsExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(GreaterThan gtExpr) {
        System.out.print("(> ");
        gtExpr.getLhs().accept(this);
        System.out.print(" ");
        gtExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(LessThan ltExpr) {
        System.out.print("(< ");
        ltExpr.getLhs().accept(this);
        System.out.print(" ");
        ltExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(And andExpr) {
        System.out.print("(&& ");
        andExpr.getLhs().accept(this);
        System.out.print(" ");
        andExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Or orExpr) {
        System.out.print("(|| ");
        orExpr.getLhs().accept(this);
        System.out.print(" ");
        orExpr.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Neg negExpr) {
        System.out.print("(- ");
        negExpr.getExpr().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Not notExpr) {
        System.out.print("(! ");
        notExpr.getExpr().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(MethodCall methodCall) {
        System.out.print("(. ");
        methodCall.getInstance().accept(this);
        System.out.print(" ");
        methodCall.getMethodName().accept(this);
        System.out.print(" (");
        for (Expression arg : methodCall.getArgs()) {
            arg.accept(this);
            System.out.print(" ");
        }
        System.out.print(") ");
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(Identifier identifier) {
        System.out.print(identifier);
        return null;
    }

    @Override
    public Void visit(Self self) {
        System.out.print(self);
        return null;
    }

    @Override
    public Void visit(Break breakStat) {
        if(Program.passNum == 1) {
            //do nothing!
        }
        else {
            System.out.println(breakStat);
        }
        return null;
    }

    @Override
    public Void visit(Continue continueStat) {
        if(Program.passNum == 1) {
            //do nothing!
        }
        else {
            System.out.println(continueStat);
        }
        return null;
    }

    @Override
    public Void visit(Skip skip) {
        if(Program.passNum == 1) {
            //do nothing!
        }
        else {
            System.out.println(skip);
        }
        return null;
    }

    @Override
    public Void visit(IntValue intValue) {
        System.out.print(intValue);
        return null;
    }

    @Override
    public Void visit(NewArray newArray) {
        System.out.print("(new arrayof ");
        System.out.print(newArray.getType());
        System.out.print(" ");
        newArray.getLength().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(BoolValue booleanValue) {
        System.out.print(booleanValue);
        return null;
    }

    @Override
    public Void visit(StringValue stringValue) {
        System.out.print(stringValue);
        return null;
    }

    @Override
    public Void visit(NewClassInstance newClassInstance) {
        System.out.print("(new ");
        newClassInstance.getClassName().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(FieldCall fieldCall) {
        System.out.print("(. ");
        fieldCall.getInstance().accept(this);
        System.out.print(" ");
        fieldCall.getField().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(ArrayCall arrayCall) {
        System.out.print("([] ");
        arrayCall.getInstance().accept(this);
        System.out.print(" ");
        arrayCall.getIndex().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(NotEquals notEquals) {
        System.out.print("(<> ");
        notEquals.getLhs().accept(this);
        System.out.print(" ");
        notEquals.getRhs().accept(this);
        System.out.print(")");
        return null;
    }

    @Override
    public Void visit(LocalVarDef localVarDef) {
        Boolean hasError = false;
        if(Program.passNum == 1) {
            Integer index = MethodDeclaration.getNewVarIndex();
            String name = localVarDef.getLocalVarName().getName();
            try {
                SymbolTable.top.put(new LocalVariableSymbolTableItem(name, index));
            } catch (ItemAlreadyExistsException e) {
                String newName = "temp" + "_" + Program.getNewTempVarNumber() + "_" + name;
                hasError = true;
                try {
                    SymbolTable.top.put(new LocalVariableSymbolTableItem(newName, index));
                } catch (ItemAlreadyExistsException e1) {
                    //dige hichi... :(
                }
            }
            if(hasError) {
                Program.addError("Error:Line:" + Integer.toString(localVarDef.getLocalVarName().line)
                        + ":Redefinition of Local Variable " + localVarDef.getLocalVarName().getName() + " in current scope" + "\n");
            }
        }
        else {
            System.out.print("(var ");
            localVarDef.getLocalVarName().accept(this);
            System.out.print(" ");
            localVarDef.getInitialValue().accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(IncStatement incStatement) {
        if(Program.passNum == 1) {
            //do nothing!
        }
        else {
            System.out.print("(++ ");
            incStatement.getOperand().accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(DecStatement decStatement) {
        if(Program.passNum == 1) {
            //do nothing!
        }
        else {
            System.out.print("(-- ");
            decStatement.getOperand().accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
//        Program.addAstPrinterResult("(class ");
        String name = classDeclaration.getName().getName();

        if(Program.passNum == 1){

            try{
                SymbolTable.top.put(new SymbolTableClassItem(name));
            }
            catch(ItemAlreadyExistsException e) {
                try {
                    String new_name = "temp" + "_" + Program.getNewTempVarNumber() + "_" + name;
                    SymbolTable.top.put(new SymbolTableClassItem((new_name)));
                } catch (ItemAlreadyExistsException e1) {
                    // dige kheili bad shansi:))
                }

                Program.addError("Error:Line:" + Integer.toString(classDeclaration.getName().line)
                        + ":Redefinition of Class " + classDeclaration.getName().getName() + "\n");

            }
            SymbolTable.push(new SymbolTable(SymbolTable.top));
            for (ClassMemberDeclaration classmember : classDeclaration.getClassMembers()) {
                classmember.accept(this);
            }
            Program.addClassSymbolTable(name, SymbolTable.top);
            SymbolTable.pop();
        }
        else {
            classDeclaration.getName().accept(this);
            System.out.print(" ");
            if (classDeclaration.getParentName() != null) {
                classDeclaration.getParentName().accept(this);
                System.out.print(" ");
            }

            for (ClassMemberDeclaration md : classDeclaration.getClassMembers())
                md.accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(EntryClassDeclaration entryClassDeclaration) {
        String name = entryClassDeclaration.getName().getName();

        if(Program.passNum == 1) {
            try {
                SymbolTable.top.put(new SymbolTableClassItem(name));
            } catch (ItemAlreadyExistsException e) {
                try {
                    String new_name = "temp" + "_" + Program.getNewTempVarNumber() + "_" + name;
                    SymbolTable.top.put(new SymbolTableClassItem((new_name)));
                } catch (ItemAlreadyExistsException e1) {
                    // dige kheili bad shansi:))
                }
                Program.addError("Error:Line:" + Integer.toString(entryClassDeclaration.getName().line)
                        + ":Redefinition of Class " + entryClassDeclaration.getName().getName() + "\n");
            }
            SymbolTable.push(new SymbolTable(SymbolTable.top));
            for (ClassMemberDeclaration classmember : entryClassDeclaration.getClassMembers()) {
                classmember.accept(this);
            }
            Program.addClassSymbolTable(name, SymbolTable.top);
            SymbolTable.pop();
        }
        else {
            System.out.print("( entry class ");
            entryClassDeclaration.getName().accept(this);
            System.out.print(" ");
            if (entryClassDeclaration.getParentName().getName() != null) {
                entryClassDeclaration.getParentName().accept(this);
                System.out.print(" ");
            }
            for (ClassMemberDeclaration md : entryClassDeclaration.getClassMembers())
                md.accept(this);
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        String name = fieldDeclaration.getName().getName();

        if (Program.passNum == 1){
            try {
                if(name.equals("length"))
                    throw new ItemAlreadyExistsException();
                SymbolTable.top.put(new SymbolTableFieldItem(name));
            }
            catch (ItemAlreadyExistsException e) {
                try {
                    String new_name = "temp" + "_" + Program.getNewTempVarNumber() + "_" + name;
                    SymbolTable.top.put(new SymbolTableFieldItem((new_name)));
                } catch (ItemAlreadyExistsException e1) {
                    // dige kheili bad shansi:))
                }
                if (name.equals("length")) {
                    Program.addError("Error:Line:" + Integer.toString(fieldDeclaration.getName().line)
                            + ":Definition of length as field of a class\n");
                }
                else {
                    Program.addError("Error:Line:" + Integer.toString(fieldDeclaration.getName().line)
                            + ":Redefinition of Field " + fieldDeclaration.getName().getName() + "\n");
                }
            }
        }
        else {
            System.out.print("(");
            System.out.print(fieldDeclaration.getAccessModifier());
            System.out.print(" field ");
            fieldDeclaration.getIdentifier().accept(this);
            System.out.print(" ");
            System.out.print(fieldDeclaration.getType());
            System.out.println(")");
        }
        return null;
    }

    @Override
    public Void visit(ParameterDeclaration parameterDeclaration) {

        String name = parameterDeclaration.getIdentifier().getName();
        Integer index = MethodDeclaration.getNewVarIndex();
        if(Program.passNum == 1) {
            try {
                SymbolTable.top.put(new LocalVariableSymbolTableItem(name, index));
            }
            catch (ItemAlreadyExistsException e) {

                String new_name = "temp" + "_" + Program.getNewTempVarNumber() + "_" + name;
                try {
                    SymbolTable.top.put(new LocalVariableSymbolTableItem(new_name, index));
                }
                catch(ItemAlreadyExistsException e1){
                    // dige kheili bad shansi:))
                }

                Program.addError("Error:Line:" + Integer.toString(parameterDeclaration.getIdentifier().line)
                        + ":Redefinition of Local Variable " + parameterDeclaration.getIdentifier().getName() + " in current scope" + "\n");
            }
        }
        else {
            System.out.print("( ");
            parameterDeclaration.getIdentifier().accept(this);
            System.out.print(" : ");
            System.out.print(parameterDeclaration.getType());
            System.out.print(")");
        }

        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {

        String name = methodDeclaration.getName().getName();

        if(Program.passNum == 1) {
            try {
                SymbolTable.top.put(new SymbolTableMethodItem(name));
            }
            catch (ItemAlreadyExistsException e) {

                try {
                    String new_name = "temp" + "_" + Program.getNewTempVarNumber() + "_" + name;
                    SymbolTable.top.put(new SymbolTableMethodItem((new_name)));
                } catch (ItemAlreadyExistsException e1) {
                    // dige kheili bad shansi:))
                }

                Program.addError("Error:Line:" + Integer.toString(methodDeclaration.getName().line)
                        + ":Redefinition of Method " + methodDeclaration.getName().getName() + "\n");
            }
                SymbolTable.push(new SymbolTable(SymbolTable.top));
                MethodDeclaration.setVarIndex();
                for (ParameterDeclaration pd : methodDeclaration.getArgs()) {
                    pd.accept(this);
                }
                for (Statement stmt : methodDeclaration.getBody()) {
                    stmt.accept(this);
                }
                SymbolTable.pop();
        }
        else {
            System.out.print("(");
            System.out.print(methodDeclaration.getAccessModifier());
            System.out.print(" method ");
            methodDeclaration.getName().accept(this);
            for (ParameterDeclaration pd : methodDeclaration.getArgs()) {
                pd.accept(this);
                System.out.print(" ");
            }
            System.out.print(methodDeclaration.getReturnType());
            System.out.println(" (");
            for (Statement stmt : methodDeclaration.getBody())
                stmt.accept(this);
            System.out.println(" ) ");
            System.out.println(")");
            }
        return null;
    }

    @Override
    public Void visit(LocalVarsDefinitions localVarsDefinitions) {
        for (LocalVarDef lvd : localVarsDefinitions.getVarDefinitions())
            lvd.accept(this);
        return null;
    }

    @Override
    public Void visit(Program program) {
//        System.out.print("(");
//        Program.addAstPrinterResult("(");
        SymbolTable.push(new SymbolTable());
        ClassDeclaration Any = new ClassDeclaration(new Identifier("Any"), null);
        program.addClassFirst(Any);

        Program.passNum = 1;

        for (ClassDeclaration cd : program.getClasses())
            cd.accept(this);

        if(Program.hasError()) {
            Program.printErrors();
            return null;
        }

        Program.passNum = 2;

        System.out.print("(");
        for (ClassDeclaration cd : program.getClasses())
            cd.accept(this);
        System.out.print(")");
//        Program.addAstPrinterResult(")");
        return null;
    }
}