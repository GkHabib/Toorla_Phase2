package toorla.symbolTable.symbolTableItem;

import toorla.types.Type;

import java.util.ArrayList;

public class SymbolTableMethodItem extends SymbolTableItem {


    public SymbolTableMethodItem(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return (this.name.concat("$").concat("method"));
    }
}
