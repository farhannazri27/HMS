package com.onsemi.hms.model;

public class Menu {
    String id;
    String parentCode;
    String code;
    String name;
    String urlPath;
    String target;
    String icon;
    String labelKey;
    String classActive;

    public Menu() {
    }

    public Menu(String parentCode, String code, String name, String urlPath, String target, String icon, String labelKey, String classActive) {
        this.parentCode = parentCode;
        this.code = code;
        this.name = name;
        this.urlPath = urlPath;
        this.target = target;
        this.icon = icon;
        this.labelKey = labelKey;
        this.classActive = classActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getClassActive() {
        return classActive;
    }

    public void setClassActive(String classActive) {
        this.classActive = classActive;
    }
    
}
