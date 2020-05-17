package zp.cps.annotation.template;

import zp.cps.annotation.annotation.Mandatory;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author namtv3
 */
public class FieldInfo {

    public final List<String> mandatoryFields;

    public FieldInfo(List<String> mandatoryFields) {
        this.mandatoryFields = mandatoryFields;
    }

    public static List<String> get(Element element) {
        List<String> mandatoryFields = new ArrayList<>();

        List<VariableElement> fields = ElementFilter.fieldsIn(element.getEnclosedElements());
        for (VariableElement field : fields) {
            if (field.getAnnotation(Mandatory.class) != null) {
                mandatoryFields.add(field.toString());
            }
        }

        return mandatoryFields;
    }
}
