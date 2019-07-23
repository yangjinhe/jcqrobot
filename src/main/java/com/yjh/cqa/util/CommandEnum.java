package com.yjh.cqa.util;

/**
 * Created by yangjh on 2019/7/20.
 */
public enum CommandEnum {

    JENKINS(1, "#jenkins", "JenkinsCommand");

    private Integer value;
    private String name;
    private String className;

    CommandEnum(Integer value, String name, String className) {
        this.value = value;
        this.name = name;
        this.className = className;
    }

    public static CommandEnum get(Integer value) {
        for (CommandEnum enumType : CommandEnum.values()) {
            if (value.equals(enumType.getValue())) {
                return enumType;
            }
        }
        return null;
    }

    public static CommandEnum getFromName(String name) {
        for (CommandEnum enumType : CommandEnum.values()) {
            if (name.equals(enumType.getName())) {
                return enumType;
            }
        }
        return null;
    }



    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }
}
