import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Table{
    private String name;
    private List<Column> columns;
    private List<Map<String, Object>> rows;

    private int nextId = 1;


    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
        this.rows = new ArrayList<>();
    }

    // insert row
    public void insert(Map<String , Object> row){

        for(Column column : columns){
            if(column.getName().equals("id")){
                row.put("id", nextId++);
                break;
            }
        }
        if(!checkRowType(row)){
            throw new IllegalArgumentException("Row type Mismatch");
        }
        rows.add(row);
    }

    //select
    public List<Map<String, Object>> select(){
        return rows;
    }

    // update
    public void update(String column , Object value , Map<String , Object> newValues){
        if(!checkRowType(newValues)){
            throw new IllegalArgumentException("Row type Mismatch");
        }
        for(Map<String, Object> row : rows){
            if(row.get(column).equals(value)){
                row.putAll(newValues);
            }
        }
    }
    public void delete(String column, Object value){
        rows.removeIf(row -> row.get(column).equals(value));
    }
    
    //getters
    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    //check type of the row
    private boolean checkRowType(Map<String, Object> row) {
        for(Column column: columns){
            Object value = row.get(column.getName());
            if(value != null && !isTypeCompatible(value, column.getType())){
                return false;
            }
        }
        return true;
    }

    private boolean isTypeCompatible(Object value, String type){
        switch(type)
        {
            case "INT":
                return value instanceof Integer;
            case "VARCHAR":
                return value instanceof String;
            case "DATE":
                return value instanceof Date;
            default:
                return false;
        }
    }

}