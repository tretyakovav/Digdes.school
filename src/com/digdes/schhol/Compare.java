package com.digdes.schhol;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compare {

    public boolean longCompare(Long val1, String comp, Long val2) {
        return switch (comp) {
            case ">" -> val1 > val2;
            case ">=" -> val1 >= val2;
            case "<" -> val1 < val2;
            case "<=" -> val1 <= val2;
            case "!=" -> !Objects.equals(val1, val2);
            case "=" -> Objects.equals(val1, val2);
            default -> throw new InputFormatException(comp + " is not a comparator");
        };
    }

    public boolean doubleCompare(Double val1, String comp, Double val2) {
        return switch (comp) {
            case ">" -> val1 > val2;
            case ">=" -> val1 >= val2;
            case "<" -> val1 < val2;
            case "<=" -> val1 <= val2;
            case "!=" -> !Objects.equals(val1, val2);
            case "=" -> Objects.equals(val1, val2);
            default -> throw new InputFormatException(comp + " is not a comparator");
        };
    }

    public boolean stringsCompare(String val1, String comp, String val2) {
        String value2clean = val2.replaceAll("%", "");
        switch (comp.toLowerCase()) {
            case "=":
                return val1.equals(val2);
            case "!=":
                return !val1.equals(val2);
            case "like":
                if (val2.matches("[а-яА-Я\\w]+")) {
                    return val1.equals(val2);
                } else if (val2.matches("[а-яА-Я\\w]+%")) {
                    Pattern pattern = Pattern.compile("^" + value2clean);
                    Matcher matcher = pattern.matcher(val1);
                    return matcher.find();
                } else if (val2.matches("%[а-яА-Я\\w]+")) {
                    Pattern pattern = Pattern.compile(value2clean + "$");
                    Matcher matcher = pattern.matcher(val1);
                    return matcher.find();
                } else if (val2.matches("%[а-яА-Я\\w]+%")) {
                    Pattern pattern = Pattern.compile(value2clean);
                    Matcher matcher = pattern.matcher(val1);
                    return matcher.find();
                }
            case "ilike":
                if (val2.matches("[а-яА-Я\\w]+")) {
                    return val1.equalsIgnoreCase(val2);
                } else if (val2.matches("[а-яА-Я\\w]+%")) {
                    Pattern pattern = Pattern.compile("^" + value2clean.toLowerCase());
                    Matcher matcher = pattern.matcher(val1.toLowerCase());
                    return matcher.find();
                } else if (val2.matches("%[а-яА-Я\\w]+")) {
                    Pattern pattern = Pattern.compile(value2clean.toLowerCase() + "$");
                    Matcher matcher = pattern.matcher(val1.toLowerCase());
                    return matcher.find();
                } else if (val2.matches("%[а-яА-Я\\w]+%")) {
                    Pattern pattern = Pattern.compile(value2clean.toLowerCase());
                    Matcher matcher = pattern.matcher(val1.toLowerCase());
                    return matcher.find();
                }
        }
        throw new InputFormatException(comp + " is not a comparator for string");
    }

    public boolean booleanCompare(Boolean value1, String comp, Boolean value2) {
        return switch (comp.toLowerCase()) {
            case "=" -> value1 == value2;
            case "!=" -> value1 != value2;
            default -> throw new InputFormatException(comp + " is not a comparator for boolean");
        };
    }

}
