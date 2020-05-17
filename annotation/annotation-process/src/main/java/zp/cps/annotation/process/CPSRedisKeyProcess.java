package zp.cps.annotation.process;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.auto.service.AutoService;
import zp.cps.annotation.annotation.CPSRedisKey;
import zp.cps.annotation.template.Field;
import zp.cps.annotation.template.FieldInfo;
import zp.cps.annotation.template.Parameter;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * @author namtv3
 */
@SupportedAnnotationTypes("zp.cps.annotation.annotation.CPSRedisKey")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class CPSRedisKeyProcess extends AbstractProcessor {

    private final String NAME_CLASS = "RedisKey";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            try {
                Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

                annotatedElements.stream().forEach(element -> {
                    TypeElement typeElement = (TypeElement) element;

                    List<String> mandatoryFields = FieldInfo.get(element);
                    String className = typeElement.getQualifiedName().toString();

                    CPSRedisKey cpsRedisKey = element.getAnnotation(CPSRedisKey.class);
                    String prefixRedis = cpsRedisKey.prefixRedis();

                    try {
                        writeBuilderFile(className, prefixRedis, mandatoryFields);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private void writeBuilderFile(String className, String prefixRedis, List<String> fields) throws IOException {
        String packageName = null;
        boolean haveField = false;

        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        if (!fields.isEmpty()) {
            haveField = true;
        }

        List<Parameter> parameters = new ArrayList<>();
        List<Field> mandatoryfields = new ArrayList<>();
        for (String field : fields) {
            parameters.add(new Parameter("Object " + field));
            parameters.add(new Parameter(","));

            mandatoryfields.add(new Field(field));
        }

        String builderClassName = packageName + "." + NAME_CLASS;
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            MustacheFactory mf = new DefaultMustacheFactory();
            Mustache m = mf.compile("rediskey.mustache");
            StringWriter writer = new StringWriter();

            Map<String, Object> context = new HashMap<>();
            context.put("packageName", packageName);
            context.put("targetClassName", builderSimpleClassName);
            context.put("prefixRedis", prefixRedis);
            context.put("nameMethod", "gen" + className.substring(className.lastIndexOf('.') + 1) + NAME_CLASS);
            if (haveField) {
                context.put("parameters", parameters);
                context.put("mandatoryfields", mandatoryfields);
            }

            m.execute(writer,context).flush();
            out.println(writer.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
