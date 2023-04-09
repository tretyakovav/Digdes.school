package com.digdes.schhol;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaSchoolStarter {

    private List<Map<String, Object>> list = new ArrayList<>();

    public JavaSchoolStarter() {
    }

    public List<Map<String, Object>> execute(String request) throws Exception {
        Command command = new Command(list);
        String[] tokens = request.split("\\s+", 2);
        int length = tokens.length;
        if (tokens.length == 0) {
            return list;
        }
        if (tokens[0].equalsIgnoreCase("insert") && length > 1) {
            list.add(command.insert(tokens[1]));
        } else if (tokens[0].equalsIgnoreCase("select") && length == 1) {
            return list;
        } else if (tokens[0].equalsIgnoreCase("select") && length > 1) {
            return command.select(tokens[1]);
        } else if (tokens[0].equalsIgnoreCase("delete") && length == 1) {
            list.clear();
        } else if (tokens[0].equalsIgnoreCase("delete") && length > 1) {
            return command.delete(tokens[1]);
        } else if (tokens[0].equalsIgnoreCase("update") && length == 1) {
            throw new InputFormatException("Missing fields for update");
        } else if (tokens[0].equalsIgnoreCase("update") && length > 1) {
            return command.update(tokens[1]);
        }
        return list;
    }
}