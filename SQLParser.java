import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLParser{
    private Map<String , Table> tables;

    private Map<String, Integer> tableIdCounters = new HashMap<>();

    public SQLParser(){
        this.tables = new HashMap<>();
    }

    //create the table & add it to tables Map
    public void createTable(String tableName , List<Column> columns){
        Table table = new Table(tableName , columns);
        tables.put(tableName, table);
        System.out.println("Table '" + tableName + "' created.");
    }

    //insert Row to table
    public void insert(String tableName , Map<String, Object> row){
        Table table = tables.get(tableName);
        if(table != null){
            try {
                table.insert(row);
                System.out.println("Row inserted into table '" + tableName + "'.");
                System.out.println("Rows are : " + row);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    //select
    public void select(String tableName){
        Table table = tables.get(tableName);
        if (table != null) {
            try {
                List<Map<String, Object>> rows = table.select();
                System.out.println("Data from table '" + tableName + "':");
                for (Map<String, Object> row : rows) {
                    System.out.println(row);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    //update Table
    public void update(String tableName , String column , Object value, Map<String, Object> newValues){
        Table table = tables.get(tableName);
        if (table != null) {
            try {
                table.update(column, value, newValues);
                System.out.println("Rows updated in table '" + tableName + "'.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    //delete from Table
    public void delete(String tableName, String column, Object value) {
        Table table = tables.get(tableName);
        if (table != null) {
            table.delete(column, value);
            System.out.println("Rows deleted from table '" + tableName + "'.");
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public int getNextId(String tableName) {
        int nextId = tableIdCounters.getOrDefault(tableName, 0) + 1;
        tableIdCounters.put(tableName, nextId);
        return nextId;
    }

}