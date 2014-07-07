package converter;

import domain.Question;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mb.MBQuestion;

/**
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
@FacesConverter(value = "questionConverter")
public class QuestionConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        MBQuestion mBQuestion = (MBQuestion) FacesContext.getCurrentInstance().getViewRoot()
                .getViewMap().get("mBQuestion");
        try {
            List<Question> autocomplete = mBQuestion.getAutocomplete();
            for (Question question : autocomplete) {
                if (question.getText().equals(value)) {
                    return question;
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

}
