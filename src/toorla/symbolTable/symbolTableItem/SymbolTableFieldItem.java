package toorla.symbolTable.symbolTableItem;

public class SymbolTableFieldItem extends SymbolTableItem {

    public String getKey() {
        return name.concat("$").concat("field");
    }

    public SymbolTableFieldItem(String _name) {
        this.name = _name;
    }

}
