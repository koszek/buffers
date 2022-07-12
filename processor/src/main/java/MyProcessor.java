import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({"annotation.GenerateSerializer", "annotation.GenerateDeserializer", "annotation.GenerateHelper"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class MyProcessor extends AbstractProcessor {

    private static final String GENERATE_HELPER_ANNOTATION = "GenerateHelper";

    private static final String GENERATE_SERIALIZER_ANNOTATION = "GenerateSerializer";

    private static final String GENERATE_DESERIALIZER_ANNOTATION = "GenerateDeserializer";

    private static final String HELPERS_PACKAGE = "helper";

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
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                if (enclosedElement.asType().toString().equals("java.lang.String")) {
                    stringFields.add(enclosedElement);
                } else {
                    scalarFields.add(enclosedElement);
                }
            }
        }

        String entityClassName = element.getSimpleName().toString();
        String helperClassName = entityClassName + "Helper";
        String entityVar = firstLetterToLowerCase(entityClassName);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(HELPERS_PACKAGE + "." + helperClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            println(out, "package {0};", HELPERS_PACKAGE);
            println(out, "import com.example.flatbuffers.*;");
            println(out, "import com.google.flatbuffers.FlatBufferBuilder;");
            println(out, "import model.*;");

            println(out, "public class {0} '{'\n", helperClassName);

            println(out, 1, "public static int serialize{0}({0} {1}, FlatBufferBuilder builder) '{'", entityClassName, entityVar);

            for (Element field : stringFields) {
                String var = getFieldName(field);
                String getter = getGetterName(var);
                println(out, 2, "int {0} = builder.createString({1}.{2}());", var, entityVar, getter);
            }

            String fbClassName = "Fb" + entityClassName;
            println(out, 2, "{0}.start{0}(builder);", fbClassName);

            for (Element field : stringFields) {
                String var = getFieldName(field);
                String methodName = "add" + firstLetterToUpperCase(var);
                println(out, 2, "{0}.{1}(builder, {2});", fbClassName, methodName, var);
            }

            for (Element field : scalarFields) {
                String var = getFieldName(field);
                String addMethodName = "add" + firstLetterToUpperCase(var);
                String getter = getGetterName(var);
                if (isEnum(field)) {
                    println(out, 2, "{0}.{1}(builder, (byte) {2}.{3}().ordinal());", fbClassName, addMethodName, entityVar, getter);
                } else {
                    println(out, 2, "{0}.{1}(builder, {2}.{3}());", fbClassName, addMethodName, entityVar, getter);
                }
            }

            println(out, 2, "return {0}.end{0}(builder);", fbClassName);
            println(out, 1, "'}'\n");

            String fbVar = "fb" + entityClassName;

            println(out, 1, "public static {0} deserialize{0}(Fb{0} {1}) '{'", entityClassName, fbVar);
            println(out, 2, "{0} {1} = new {0}();", entityClassName, entityVar);
            for (Element stringField : stringFields) {
                String var = stringField.toString();
                String setter = "set" + var.substring(0, 1).toUpperCase() + var.substring(1);
                println(out, 2, "{0}.{1}({2}.{3}());", entityVar, setter, fbVar, var);
            }
            for (Element field : scalarFields) {
                String var = field.toString();
                String setter = "set" + var.substring(0, 1).toUpperCase() + var.substring(1);
                if (isEnum(field)) {
                    println(out, 2, "{0}.{1}({2}.values()[{3}.{4}()]);", entityVar, setter, field.asType(), fbVar, var);
                } else {
                    println(out, 2, "{0}.{1}({2}.{3}());", entityVar, setter, fbVar, var);
                }
            }
            println(out, 2, "return {0};", entityVar);
            println(out, 1, "'}'");
            println(out, "'}'");
        }
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
        String className = element.getSimpleName().toString();
        String serializerClassName = className + "Serializer";

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(HELPERS_PACKAGE + "." + serializerClassName);
        List<Element> fields = new ArrayList<>();
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                fields.add(enclosedElement);
            }
        }

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            println(out, "package {0};", HELPERS_PACKAGE);
            println(out, "import org.apache.kafka.common.serialization.Serializer;");
            println(out, "import {0}.*;", HELPERS_PACKAGE);
            println(out, "import {0};", element.asType());
            println(out, "import com.example.flatbuffers.FbUsStreetExecution;");
            println(out, "import com.google.flatbuffers.FlatBufferBuilder;\n");
            println(out, "public class {0} implements Serializer<{1}> '{'\n", serializerClassName, className);
            println(out, 1, "@Override");
            println(out, 1, "public byte[] serialize(String topic, {0} var) '{'", className);
            println(out, 2, "FlatBufferBuilder builder = new FlatBufferBuilder();");

            for (Element field : fields) {
                String var = field.toString();
                String varWithCapital = firstLetterToUpperCase(var);
                println(out, 2, "int {0} = {1}Helper.serialize{1}(var.get{1}(), builder);", var, varWithCapital);
            }

            println(out, 2, "Fb{0}.startFb{0}(builder);", className);

            for (Element field : fields) {
                String var = field.toString();
                String varWithCapital = firstLetterToUpperCase(var);
                println(out, 2, "Fb{0}.add{1}(builder, {2});", className, varWithCapital, var);
            }
            println(out, 2, "int fb = Fb{0}.endFb{0}(builder);", className);
            println(out, 2, "builder.finish(fb);");
            println(out, 2, "return builder.sizedByteArray();");
            println(out, 1, "'}'");
            println(out, "}");
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
        String className = element.getSimpleName().toString();
        String deserializerClassName = className + "Deserializer";

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(HELPERS_PACKAGE + "." + deserializerClassName);
        List<Element> fields = new ArrayList<>();
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                fields.add(enclosedElement);
            }
        }

        String fbClassName = "Fb" + className;
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            println(out, "package {0};", HELPERS_PACKAGE);
            println(out, "import com.example.flatbuffers.FbUsStreetExecution;");
            println(out, "import {0}.*;", HELPERS_PACKAGE);
            println(out, "import model.UsStreetExecution;");
            println(out, "import org.apache.kafka.common.serialization.Deserializer;");
            println(out, "import java.nio.ByteBuffer;");
            println(out, "public class {0} implements Deserializer<{1}> '{'\n", deserializerClassName, className);
            println(out, 1, "@Override");
            println(out, 1, "public {0} deserialize(String topic, byte[] data) '{'", className);
            println(out, 2, "ByteBuffer byteBuffer = ByteBuffer.wrap(data);");
            println(out, 2, "{0} fbEntity = {0}.getRootAs{0}(byteBuffer);", fbClassName);
            println(out, 2, "{0} entity = new {0}();", className);
            for (Element field : fields) {
                String fieldName = getFieldName(field);
                String cap = firstLetterToUpperCase(fieldName);
                println(out, 2, "entity.set{0}({0}Helper.deserialize{0}(fbEntity.{1}()));", cap, fieldName);
            }
            println(out, 2, "return entity;");
            println(out, 1, "'}'");
            println(out, "'}'");
        }
    }

    private String firstLetterToLowerCase(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
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

    private void println(PrintWriter out, String pattern, Object... params) {
        println(out, 0, pattern, params);
    }

    private void println(PrintWriter out, int nestingLevel, String pattern, Object... params) {
        char[] tabs = new char[nestingLevel];
        for (int i = 0; i < nestingLevel; i++) {
            tabs[i] = '\t';
        }
        String string = MessageFormat.format(pattern, params);
        out.println(new String(tabs) + string);
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