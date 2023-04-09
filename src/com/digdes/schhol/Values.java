package com.digdes.schhol;

import java.util.HashMap;
import java.util.Map;

public class Values {

    private Map<String, Object> map = new HashMap<>();

    public Map<String, Object> getMap(String targetValues, int methodIndex) {
        String[] values = targetValues.split(",");
        if (values.length == 0) {
            return map;
        }
        for (String s : values) {
            String[] valuesArr = s.split("=");
            String valueName = valuesArr[0].replaceAll("'", "").trim();
            String value = valuesArr[1];
            if (valueName.equalsIgnoreCase("id")) {
                setId(value, methodIndex);
            } else if (valueName.equalsIgnoreCase("lastname")) {
                setLastName(value, methodIndex);
            } else if (valueName.equalsIgnoreCase("age")) {
                setAge(value, methodIndex);
            } else if (valueName.equalsIgnoreCase("cost")) {
                setCost(value, methodIndex);
            } else if (valueName.equalsIgnoreCase("active")) {
                setActive(value, methodIndex);
            }
        }
        return map;
    }

    public void setId(String id, int methodIndex) {
        if (id.trim().matches("\\d+")) {
            map.put("id", Long.parseLong(id.trim()));
        } else if (id.trim().matches("null") && methodIndex == 1) {
            map.remove("id");
        } else if (id.trim().matches("null") && methodIndex == 2) {
            map.put("id", null);
        } else {
            throw new InputFormatException("'id' value is not a digit");
        }
    }

    public void setLastName(String lastName, int methodIndex) {
        if (lastName.trim().matches("null") && methodIndex == 2) {
            map.put("lastname", null);
        } else {
            map.put("lastname", lastName.replaceAll("'", "").trim());
        }
    }

    public void setAge(String age, int methodIndex) {
        if (age.trim().matches("\\d+|null")) {
            map.put("age", Long.parseLong(age.trim()));
        } else if (age.trim().matches("null") && methodIndex == 2) {
            map.put("age", null);
        } else {
            throw new InputFormatException("'age' value is not a digit");
        }
    }

    public void setCost(String cost, int methodIndex) {
        if (cost.trim().matches("\\d+\\.\\d+|null")) {
            map.put("cost", Double.parseDouble(cost.trim()));
        } else if (cost.trim().matches("null") && methodIndex == 2) {
            map.put("cost", null);
        } else {
            throw new InputFormatException("'cost' value is not a digit");
        }
    }

    public void setActive(String active, int methodIndex) {
        if (active.trim().matches("true")) {
            map.put("active", true);
        } else if (active.trim().matches("false")) {
            map.put("active", false);
        } else if (active.trim().matches("null") && methodIndex == 2) {
            map.put("active", null);
        } else {
            throw new InputFormatException("'active' value is not a boolean");
        }
    }
}