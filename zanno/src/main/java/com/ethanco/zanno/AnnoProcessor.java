package com.ethanco.zanno;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by YOLANDA on 2016-01-21.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.ethanco.zanno.AutoDestory"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AnnoProcessor extends AbstractProcessor {

    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        String fqClassName, className, packageName;
        Map<String, ProxyInfo> mProxyMap = new HashMap<>();


        for (Element ele : roundEnv.getElementsAnnotatedWith(AutoDestory.class)) {
            if (ele.getKind() == ElementKind.CLASS) {
                //Class
            } else if (ele.getKind() == ElementKind.FIELD) {
                //FIELD
                VariableElement varElement = (VariableElement) ele;
                TypeElement classElement = (TypeElement) ele.getEnclosingElement();

                fqClassName = classElement.getQualifiedName().toString();
                PackageElement packageElement = elementUtils.getPackageOf(classElement);

                packageName = packageElement.getQualifiedName().toString();
                className = classElement.getSimpleName().toString();

                //className = getClassName(classElement, packageName);

                //int id = varElement.getAnnotation(AutoDestory.class)
                String fieldName = varElement.getSimpleName().toString();
                String fieldType = varElement.asType().toString();
                //Object constantValue = varElement.getConstantValue();

                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "annatated field : fieldName = " + varElement.getSimpleName().toString() + " , fileType = " + fieldType);

                ProxyInfo proxyInfo = mProxyMap.get(fqClassName);
                if (proxyInfo == null) {
                    proxyInfo = new ProxyInfo(packageName, className);
                    mProxyMap.put(fqClassName, proxyInfo);
                    proxyInfo.setTypeElement(classElement);
                }

                FieldBean fieldBean = new FieldBean(fieldName, fieldType);
                proxyInfo.addFiled(fieldBean);
            }
        }

        //遍历mProxyMap
        for (String key : mProxyMap.keySet()) {
            ProxyInfo proxyInfo = mProxyMap.get(key);
            try {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(proxyInfo.getProxyClassFullName(), proxyInfo.getTypeElement());
                Writer writer = jfo.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }

        return true;
    }

    /*@Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }*/
}
