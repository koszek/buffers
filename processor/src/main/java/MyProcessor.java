import com.squareup.javapoet.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SupportedAnnotationTypes({"annotation.GenerateSerializer", "annotation.GenerateDeserializer", "annotation.GenerateHelper"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class MyProcessor extends AbstractProcessor {

    private static final String GENERATE_HELPER_ANNOTATION = "GenerateHelper";

    private static final String GENERATE_SERIALIZER_ANNOTATION = "GenerateSerializer";

    private static final String GENERATE_DESERIALIZER_ANNOTATION = "GenerateDeserializer";

    private static final String HELPERS_PACKAGE = "helper";

    private static final String KAFKA_PACKAGE = "org.apache.kafka.common.serialization";

    private static final String FLAT_BUFFERS_PACKAGE = "com.example.flatbuffers";

    private static final String GOOGLE_FLAT_BUFFERS_PACKAGE = "com.google.flatbuffers";

    private static final ClassName BUILDER_CLASS_NAME = ClassName.get(GOOGLE_FLAT_BUFFERS_PACKAGE, "FlatBufferBuilder");

    private static final String FB_PREFIX = "Fb";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            String annotationName = annotation.getSimpleName().toString();
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            switch (annotationName) {
                case GENERATE_HELPER_ANNOTATION:
                    generateHelperClasses(elements);
                    break;
                case GENERATE_SERIALIZER_ANNOTATION:
                    generateSerializerClasses(elements);
                    break;
                case GENERATE_DESERIALIZER_ANNOTATION:
                    generateDeserializerClasses(elements);
                    break;
            }
        }
        return true;
    }

    private void generateHelperClasses(Set<? extends Element> elements) {
        for (Element element : elements) {
            try {
                generateHelperClass(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void generateHelperClass(Element element) throws Exception {
        List<Element> stringFields = new ArrayList<>();
        List<Element> scalarFields = new ArrayList<>();
        List<Element> nestedFields = new ArrayList<>();
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                if (enclosedElement.asType().toString().equals("java.lang.String")) {
                    stringFields.add(enclosedElement);
                } else if (isAnnotatedWithGenerateHelper(enclosedElement)) {
                    nestedFields.add(enclosedElement);
                } else {
                    scalarFields.add(enclosedElement);
                }
            }
        }

        String entityClassName = element.getSimpleName().toString();
        String helperClassName = entityClassName + "Helper";
        ClassName elementClassName = getClassName(element);

        MethodSpec.Builder serializeMethodBuilder = MethodSpec.methodBuilder("serialize")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(int.class)
                .addParameter(elementClassName, "var")
                .addParameter(BUILDER_CLASS_NAME, "builder");
        for (Element field : stringFields) {
            String fieldName = getFieldName(field);
            String getter = getGetterName(fieldName);
            serializeMethodBuilder.addStatement("int $L = builder.createString(var.$L())", fieldName, getter);
        }

        for (Element field : nestedFields) {
            String fieldName = getFieldName(field);
            String FieldName = firstLetterToUpperCase(fieldName);
            String getter = getGetterName(fieldName);
            ClassName helper = ClassName.get(HELPERS_PACKAGE, FieldName + "Helper");
            serializeMethodBuilder.addStatement("int $L = $T.serialize(var.$L(), builder)", fieldName, helper, getter);
        }

        String fbClassName = FB_PREFIX + entityClassName;
        ClassName cn = ClassName.get(FLAT_BUFFERS_PACKAGE, fbClassName);
        serializeMethodBuilder.addStatement("$T.start$L(builder)", cn, fbClassName);

        for (Element field : stringFields) {
            String fieldName = getFieldName(field);
            String methodName = "add" + firstLetterToUpperCase(fieldName);
            serializeMethodBuilder.addStatement("$T.$L(builder, $L)", cn, methodName, fieldName);
        }

        for (Element field : scalarFields) {
            String fieldName = getFieldName(field);
            String addMethodName = "add" + firstLetterToUpperCase(fieldName);
            String getter = getGetterName(fieldName);
            if (isEnum(field)) {
                serializeMethodBuilder.addStatement("$T.$L(builder, (byte) var.$L().ordinal())", cn, addMethodName, getter);
            } else {
                serializeMethodBuilder.addStatement("$T.$L(builder, var.$L())", cn, addMethodName, getter);
            }
        }

        for (Element field : nestedFields) {
            String var = getFieldName(field);
            String addMethodName = "add" + firstLetterToUpperCase(var);
            serializeMethodBuilder.addStatement("$T.$L(builder, $L);", cn, addMethodName, var);
        }

        serializeMethodBuilder.addStatement("return $T.end$L(builder)", cn, fbClassName);

        String fbVar = FB_PREFIX + entityClassName;
        MethodSpec.Builder deserializeMethodBuilder = MethodSpec.methodBuilder("deserialize")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(elementClassName)
                .addParameter(cn, fbVar);
        deserializeMethodBuilder.addStatement("$T var = new $T()", elementClassName, elementClassName);

        for (Element stringField : stringFields) {
            String var = stringField.toString();
            String setter = "set" + var.substring(0, 1).toUpperCase() + var.substring(1);
            deserializeMethodBuilder.addStatement("var.$L($L.$L())", setter, fbVar, var);
        }

        for (Element field : scalarFields) {
            String fieldName = field.toString();
            String FieldName = firstLetterToUpperCase(fieldName);
            String setter = "set" + FieldName;
            if (isEnum(field)) {
                deserializeMethodBuilder.addStatement("var.$L($T.values()[$L.$L()])",
                        setter, ClassName.get(field.asType()), fbVar, fieldName);
            } else {
                deserializeMethodBuilder.addStatement("var.$L($L.$L())", setter, fbVar, fieldName);
            }
        }

        for (Element field : nestedFields) {
            String fieldName = getFieldName(field);
            String FieldName = firstLetterToUpperCase(fieldName);
            ClassName fieldHelper = ClassName.get(HELPERS_PACKAGE, FieldName + "Helper");
            String setMethodName = "set" + FieldName;
            deserializeMethodBuilder.addStatement("var.$L($T.deserialize($L.$L()))", setMethodName, fieldHelper, fbVar, fieldName);
        }

        deserializeMethodBuilder.addStatement("return var");

        JavaFileObject builderFile1 = processingEnv.getFiler().createSourceFile(HELPERS_PACKAGE + "." + helperClassName);
        TypeSpec serializer = TypeSpec.classBuilder(helperClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(serializeMethodBuilder.build())
                .addMethod(deserializeMethodBuilder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(HELPERS_PACKAGE, serializer)
                .build();

        try (PrintWriter out = new PrintWriter(builderFile1.openWriter())) {
            javaFile.writeTo(out);
        }
    }

    private boolean isAnnotatedWithGenerateHelper(Element element) {
        if (element.asType() instanceof DeclaredType) {
            return ((DeclaredType) element.asType()).asElement().getAnnotationMirrors()
                    .stream()
                    .map(Objects::toString)
                    .anyMatch(it -> it.equals("@annotation.GenerateHelper"));
        }
        return false;
    }

    private void generateSerializerClasses(Set<? extends Element> elements) {
        for (Element element : elements) {
            try {
                generateSerializerClass(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void generateSerializerClass(Element element) throws Exception {
        String elementClass = element.getSimpleName().toString();
        String serializerClassName = elementClass + "Serializer";

        List<Element> fields = new ArrayList<>();
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                fields.add(enclosedElement);
            }
        }

        String fbClass = FB_PREFIX + elementClass;
        ClassName elementClassName = (ClassName) ClassName.get(element.asType());
        ClassName fbClassName = ClassName.get(FLAT_BUFFERS_PACKAGE, fbClass);
        MethodSpec.Builder builder = MethodSpec.methodBuilder("serialize")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(byte[].class)
                .addParameter(String.class, "topic")
                .addParameter(elementClassName, "var")
                .addStatement("$T builder = new $T()", BUILDER_CLASS_NAME, BUILDER_CLASS_NAME);

        for (Element field : fields) {
            String fieldName = field.toString();
            String FieldName = firstLetterToUpperCase(fieldName);
            ClassName helperClassName = ClassName.get(HELPERS_PACKAGE, FieldName + "Helper");
            builder.addStatement("int $L = $T.serialize(var.get$L(), builder)", fieldName, helperClassName, FieldName);
        }

        builder.addStatement("$T.start$L(builder)", fbClassName, fbClass);

        for (Element field : fields) {
            String fieldName = field.toString();
            String FieldName = firstLetterToUpperCase(fieldName);
            builder.addStatement("$T.add$L(builder, $L)", fbClassName, FieldName, fieldName);
        }
        builder.addStatement("int fb = $L.end$L(builder)", fbClass, fbClass);
        builder.addStatement("builder.finish(fb)");
        builder.addStatement("return builder.sizedByteArray()");

        TypeName kafkaDeserializerInterface = ParameterizedTypeName.get(ClassName.get(KAFKA_PACKAGE, "Serializer"),
                elementClassName);
        TypeSpec serializer = TypeSpec.classBuilder(serializerClassName)
                .addSuperinterface(kafkaDeserializerInterface)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(builder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(HELPERS_PACKAGE, serializer)
                .build();

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(HELPERS_PACKAGE + "." + serializerClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            javaFile.writeTo(out);
        }
    }

    private void generateDeserializerClasses(Set<? extends Element> elements) {
        for (Element element : elements) {
            try {
                generateDeserializerClass(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void generateDeserializerClass(Element element) throws Exception {
        String elementClass = element.getSimpleName().toString();
        String deserializerClass = elementClass + "Deserializer";

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(HELPERS_PACKAGE + "." + deserializerClass);
        List<Element> fields = new ArrayList<>();
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                fields.add(enclosedElement);
            }
        }

        String fbClass = FB_PREFIX + elementClass;
        ClassName elementClassName = (ClassName) ClassName.get(element.asType());
        ClassName fbClassName = ClassName.get(FLAT_BUFFERS_PACKAGE, fbClass);
        MethodSpec.Builder builder = MethodSpec.methodBuilder("deserialize")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(elementClassName)
                .addParameter(String.class, "topic")
                .addParameter(byte[].class, "data")
                .addStatement("$T byteBuffer = $T.wrap(data)", ByteBuffer.class, ByteBuffer.class)
                .addStatement("$T fbEntity = $T.$L(byteBuffer)", fbClassName, fbClassName, "getRootAs" + fbClass)
                .addStatement("$T entity = new $T()", elementClassName, elementClassName);

        for (Element field : fields) {
            String fieldName = getFieldName(field);
            String FieldName = firstLetterToUpperCase(fieldName);
            ClassName helperClassName = ClassName.get(HELPERS_PACKAGE, FieldName + "Helper");
            builder.addStatement("entity.set$L($T.deserialize(fbEntity.$L()))", FieldName, helperClassName, fieldName);
        }
        builder.addStatement("return entity");

        TypeName kafkaDeserializerInterface = ParameterizedTypeName.get(ClassName.get(KAFKA_PACKAGE, "Deserializer"),
                elementClassName);

        TypeSpec deserializer = TypeSpec.classBuilder(deserializerClass)
                .addSuperinterface(kafkaDeserializerInterface)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(builder.build())
                .build();

        JavaFile javaFile = JavaFile.builder(HELPERS_PACKAGE, deserializer)
                .build();

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            javaFile.writeTo(out);
        }
    }

    private String firstLetterToUpperCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private String getFieldName(Element element) {
        return element.toString();
    }

    private String getGetterName(String field) {
        return "get" + firstLetterToUpperCase(field);
    }

    private ClassName getClassName(Element element) {
        return (ClassName) ClassName.get(element.asType());
    }

    private boolean isEnum(Element field) {
        if (field.asType().getKind() == TypeKind.DECLARED) {
            TypeElement t = (TypeElement) ((DeclaredType) field.asType()).asElement();
            if (t.getKind() == ElementKind.ENUM) {
                return true;
            }
        }
        return false;
    }
}