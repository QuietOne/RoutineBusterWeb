package converter;

import domain.Category;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mb.MBCategory;

/**
 *
 * @author Jelena Tabas
 * @version 1.0
 */
@FacesConverter(value = "categoryConverter")
public class CategoryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        MBCategory mBCategory = (MBCategory) FacesContext.getCurrentInstance().getViewRoot()
                .getViewMap().get("mBCategory");
        System.out.println("MBCategory properties");
        System.out.println("Autocomplete: " + mBCategory.getAutocomplete());
        System.out.println("Lista odgovora: " + mBCategory.getCategoryList());
        try {
            List<Category> autocomplete = mBCategory.getAutocomplete();
            for (Category category : autocomplete) {
                if (category.getName().equals(value)) {
                    return category;
                }
            }
        } catch (NullPointerException npe) {
            System.out.println("Sorry, sir, but I didn't find penny");
            npe.printStackTrace();
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
