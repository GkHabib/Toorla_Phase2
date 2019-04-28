package toorla.symbolTable.symbolTableItem;

import toorla.types.Type;

import java.util.ArrayList;

public class SymbolTableMethodItem extends SymbolTableItem {

    private ArrayList<Type> argList;

    public SymbolTableMethodItem(String name, ArrayList<Type> argList) {
        this.name = name;
        this.argList = argList;
    }

    @Override
    public String getKey() {
        return (this.name.concat("$").concat("method"));
    }
}
