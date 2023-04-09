package com.digdes.schhol;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Where {

    public List<Map<String, Object>> whereMethod(String whereString, List<Map<String, Object>> list) {
        Pattern pattern = Pattern.compile("[wW].{3}[eE]");
        Matcher matcher = pattern.matcher(whereString);
        String valuesString = whereString.trim();
        String whereWord = "where";
        if (matcher.find()) {
            valuesString = whereString.substring(whereWord.length());
        }
        List<Map<String, Object>> whereList = new ArrayList<>();
        List<Map<String, Object>> whereAndList = new ArrayList<>();
        List<Map<String, Object>> tempAndList = new ArrayList<>();
        String conditionsString = valuesString.replaceAll("'|\\b", " ")
                .replaceAll("\\s\\.\\s", "\\.")
                .trim();
        String[] orArr = conditionsString.split("\\s[oO][rR]\\s");
        for (String or : orArr) {
            String[] andArr = or.trim().split("\\s[aA][nN][dD]\\s");
            if (andArr.length == 0) {
                continue;
            }
            int count = 0;
            for (String and : andArr) {
                String[] values = and.trim().split("\\s+");
                if (values.length < 3) {
                    throw new InputFormatException("Wrong syntax");
                }
                String valName = values[0].toLowerCase();
                String comp = values[1].toLowerCase();
                String value = values[2];
                if (count < 1) {
                    tempAndList = getWhereList(valName, comp, value, list);
                } else {
                    whereAndList = getWhereList(valName, comp, value, tempAndList);
                }
                count++;
            }
            if (count <= 1) {
                whereList.addAll(tempAndList);
            } else {
                whereList.addAll(whereAndList);
            }
        }
        return whereList;
    }

    public List<Map<String, Object>> getWhereList(String valName, String comp, String value, List<Map<String, Object>> list) {
        Compare compare = new Compare();
        List<Map<String, Object>> tempWhereList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            for (String key : map.keySet()) {
                Map<String, Object> selectMap = new LinkedHashMap<>();
                if (key.equals(valName) && valName.equals("id")) {
                    if (!value.matches("\\d+")) {
                        throw new InputFormatException("Id value must be an integer number");
                    }
                    if (compare.longCompare((Long) map.get(valName), comp, Long.parseLong(value))) {
                        selectMap.putAll(map);
                        tempWhereList.add(selectMap);
                    }
                } else if (key.equals(valName) && valName.equals("lastname")) {
                    if (!value.matches("%?[а-яА-Яa-zA-Z]+%?")) {
                        throw new InputFormatException("Last name value must be a letter sequence");
                    }
                    if (compare.stringsCompare((String) map.get(valName), comp, value)) {
                        selectMap.putAll(map);
                        tempWhereList.add(selectMap);
                    }
                } else if (key.equals(valName) && valName.equals("age")) {
                    if (!value.matches("\\d+")) {
                        throw new InputFormatException("Age value must be a integer number");
                    }
                    if (compare.longCompare((Long) map.get(valName), comp, Long.parseLong(value))) {
                        selectMap.putAll(map);
                        tempWhereList.add(selectMap);
                    }
                } else if (key.equals(valName) && valName.equals("cost")) {
                    if (!value.matches("\\d+\\.\\d+")) {
                        throw new InputFormatException("Cost value must be a point number");
                    }
                    if (compare.doubleCompare((Double) map.get(valName), comp, Double.parseDouble(value))) {
                        selectMap.putAll(map);
                        tempWhereList.add(selectMap);
                    }
                } else if (key.equals(valName) && valName.equals("active")) {
                    if (!value.matches("false|true")) {
                        throw new InputFormatException("Active value must be a boolean");
                    }
                    if (compare.booleanCompare((Boolean) map.get(valName), comp, Boolean.parseBoolean(value))) {
                        selectMap.putAll(map);
                        tempWhereList.add(selectMap);
                    }
                } else if (!valName.equals("active") && !valName.equals("age")
                        && !valName.equals("lastname") && !valName.equals("id") && !valName.equals("cost")) {
                    throw new InputFormatException("'" + valName + "' field does not exist");
                }
            }
        }
        return tempWhereList;
    }

}