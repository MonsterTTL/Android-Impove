package com.example.annotations_lib;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.example.annotations_lib.annotations.*;

//@SupportedSourceVersion()  下面的两个方法在Java7之后也可以用注解来指定
//@SupportedAnnotationTypes()
public class ClassProcessor extends AbstractProcessor {
    @Override//初始化
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override//
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();//获取日志打印器
        for(Element element: roundEnv.getElementsAnnotatedWith(BindView.class)){//遍历所有被BindView注解修饰的元素
            if(element.getKind() == ElementKind.FIELD){//如果元素类型时字段类型的话
                messager.printMessage(Diagnostic.Kind.NOTE,"printMessage:"+element.toString());
                //打印一条日志
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(BindView.class.getCanonicalName());//用于获取标准名称的
        return annotations;//返回一个String类型的集合，每个String是处理器想要处理的注解类型的合法全称
    }

    @Override//指定Java版本的
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }
}
