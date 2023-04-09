package com.digdes.schhol;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Command {

    private List<Map<String, Object>> list;

    public Command(List<Map<String, Object>> list) {
        this.list = list;
    }

    public Map<String, Object> insert(String insertString) {
        Values values = new Values();
        int methodIndex = 1;
        String[] components = insertString.split("\\s+", 2);
        if (components.length < 2 || !components[0].equalsIgnoreCase("values")) {
            throw new InputFormatException("Missing fields for insert");
        }
        return values.getMap(components[1], methodIndex);
    }

    public List<Map<String, Object>> update(String updateString) {
        Values values = new Values();
        Where where = new Where();
        int methodIndex = 2;
        String[] components = updateString.split("\\s+", 2);
        if (components.length < 2 || !components[0].equalsIgnoreCase("values")) {
            throw new InputFormatException("Missing fields for insert");
        }
        String[] update = components[1].trim().split("\\s[wW]\\w{3}[eE]\\s");
        Map<String, Object> updateMap = values.getMap(update[0].trim(), methodIndex);
        if (update.length == 1) {
            for (Map<String, Object> map : list) {
                map.putAll(updateMap);
            }
        } else {
            List<Map<String, Object>> updateList = where.whereMethod(update[1], list);
            for (Map<String, Object> l : list) {
                for (Map<String, Object> m : updateList) {
                    if (l.equals(m)) {
                        for (String s : updateMap.keySet()) {
                            l.put(s, updateMap.get(s));
                        }
                    }
                }
            }
        }
        for (Map<String, Object> map : list) {
            map.values().removeIf(Objects::isNull);
        }
        return list;
    }

    public List<Map<String, Object>> delete(String deleteString) {
        Where where = new Where();
        List<Map<String, Object>> deleteList = where.whereMethod(deleteString, list);
        for (int i = 0; i < list.size(); i++) {
            for (Map<String, Object> m : deleteList) {
                if (list.get(i).equals(m)) {
                    list.remove(i);
                }
            }
        }
        return list;
    }

    public List<Map<String, Object>> select(String selectString) {
        Where where = new Where();
        return where.whereMethod(selectString, list);
    }
}