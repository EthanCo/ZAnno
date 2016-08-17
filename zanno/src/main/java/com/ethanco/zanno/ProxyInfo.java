package com.ethanco.zanno;


import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;

public class ProxyInfo {
    private String packageName;
    private String targetClassName;
    private String proxyClassName;
    private TypeElement typeElement;
    private List<FieldBean> fieldList = new ArrayList<>();

    public void addFiled(FieldBean fieldBean) {
        fieldList.add(fieldBean);
    }

    public List<FieldBean> getFieldList() {
        return fieldList;
    }

    public static final String PROXY_SUFFIX = "ZANNOPROXY";

    public ProxyInfo(String packageName, String className) {
        this.packageName = packageName;
        this.targetClassName = className;
        this.proxyClassName = className + "$$" + PROXY_SUFFIX;
    }

    public String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n\n");

        builder.append("import com.ethanco.zanno.Finder;\n");
        builder.append("import com.ethanco.zanno.AbstractInjector;\n");
        builder.append("import android.os.Build;\n");
        builder.append("import android.os.Bundle;\n");

        builder.append('\n');

        builder.append("public class ").append(proxyClassName);
        builder.append("<T extends ").append(getTargetClassName()).append(">");
        builder.append(" implements AbstractInjector<T>");
        builder.append(" {\n");

        generateInjectMethod(builder);
        builder.append('\n');

        builder.append("}\n");
        return builder.toString();

    }

    private String getTargetClassName() {
        return targetClassName.replace("$", ".");
    }

    private void generateInjectMethod(StringBuilder builder) {
        builder.append("  @Override ").append("public void inject(final Finder finder, final T target, Object source) {\n");
        builder.append("if(finder.equals(Finder.SUBSCRIPTION)){").append("");
        builder = generateActivityFinderCode(builder);
        builder.append("}");
        builder.append("  }\n");
    }

    private StringBuilder generateActivityFinderCode(StringBuilder builder) {
        builder.append(Finder.SUBSCRIPTION.destory(fieldList));

        return builder;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }
}
