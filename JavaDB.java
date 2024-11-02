import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class JavaDB{
    public static void main(String[] args){
        SQLParser parser = new SQLParser();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Senshi Java Database In-Memory !");
        
        while(true)
        {
            System.out.print("JavaDB> ");
            String command = scanner.nextLine();

            if (command.startsWith("CREATE TABLE")) {
                String[] parts = command.split(" ");
                if (parts.length < 4) {
                    System.out.println("Invalid CREATE TABLE command.");
                    continue;
                }
                String tableName = parts[2];

                if (!command.contains("(") || !command.contains(")")) {
                    System.out.println("Table definition must include parentheses.");
                    continue;
                }

                String columnDefinitions = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
                String[] columnParts = columnDefinitions.split(",");

                List<Column> columns = new ArrayList<>();
                for (String colDef : columnParts) {
                    String[] colParts = colDef.trim().split(" ");
                    if (colParts.length != 2) {
                        System.out.println("Invalid column definition: " + colDef);
                        continue;
                    }
                    String colName = colParts[0];
                    String colType = colParts[1];
                    columns.add(new Column(colName, colType));
                }
                parser.createTable(tableName, columns);
            } else if (command.startsWith("INSERT INTO")) {
                String[] parts = command.split(" ");
                String tableName = parts[2];
                String[] values = parts[4].replace("(", "").replace(")", "").split(",");
                List<Column> columns = parser.getTables().get(tableName).getColumns();
                Map<String, Object> row = new HashMap<>();

                for (int i = 0; i < values.length; i++) {
                    String type = columns.get(i).getType();
                    if (i == 0 && values[i].trim().equals("null")) {
                        row.put(columns.get(i).getName(), parser.getNextId(tableName));
                    } else {
                        if ("INT".equals(type)) {
                            row.put(columns.get(i).getName(), Integer.parseInt(values[i].trim()));
                        } else if ("DATE".equals(type)) {
                            row.put(columns.get(i).getName(), new Date()); // In a real scenario, parse the date string
                        } else {
                            row.put(columns.get(i).getName(), values[i].trim());
                        }
                    }
                }
                parser.insert(tableName, row);
            } else if (command.startsWith("SELECT * FROM")) {
                String[] parts = command.split(" ");
                String tableName = parts[3];
                parser.select(tableName);

            } else if (command.startsWith("UPDATE")) {
                String[] parts = command.split(" ");
                String tableName = parts[1];
                String column = parts[3];
                String value = parts[5];
                String[] setParts = parts[7].replace("(", "").replace(")", "").split(",");
                Map<String, Object> newValues = new HashMap<>();
                for (String part : setParts) {
                    String[] keyValue = part.split("=");
                    String key = keyValue[0].trim();
                    Object newValue = keyValue[1].trim();
                    newValues.put(key, newValue);
                }
                parser.update(tableName, column, value, newValues);
            } else if (command.startsWith("DELETE FROM")) {
                String[] parts = command.split(" ");
                String tableName = parts[2];
                String column = parts[4];
                String value = parts[6];
                parser.delete(tableName, column, value);

            } else if (command.equalsIgnoreCase("EXIT")) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }
        scanner.close();

    }
}