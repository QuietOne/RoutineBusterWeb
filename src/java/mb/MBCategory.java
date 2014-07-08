/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Category;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import session.category.SessionCategoryLocal;

/**
 *
 * @author Jelena Tabaš
 */
@ManagedBean(name = "mBCategory")
@ViewScoped
public class MBCategory implements Serializable {

    private String categoryName;
    private List<Category> categoryList;
    private List<Category> autocomplete;
    private List<Category> filteredList;

    @EJB
    private SessionCategoryLocal sCategory;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    /**
     * Creates a new instance of MBQuestion
     */
    public MBCategory() {

    }

    @PostConstruct
    public void init() {
        categoryList = new ArrayList<>();
        autocomplete = sCategory.getCategories();
        filteredList = new ArrayList<>();
    }

    public List<Category> autoCompleteCategory(String name) {
        List<Category> temp = sCategory.autocompleteCategory(name);
        if (temp != null && !temp.isEmpty()) {
            autocomplete = temp;
        }
        return temp;
    }

    public List<Category> autocompleteApproveCategory(String text) {
        List<Category> temp = sCategory.autocompleteApproveCategory(text);
        if (temp != null && !temp.isEmpty()) {
            autocomplete = temp;
        }
        return temp;
    }

    public void updateCategories(Category c) {
        if (categoryList == null) {
            return;
        }
        for (Category category1 : categoryList) {
            try {
                sCategory.updateCategory(category1);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, category1.getName() + " nije se uspesno ubacila u bazu", ""));
                category1.setApproved(Boolean.FALSE);
            }
        }
        categoryList.clear();
    }

    public void saveApproveCategories(Category c) {
        if (categoryList == null) {
            return;
        }
        System.out.println("Save " + categoryList);
        for (Category category1 : categoryList) {
            try {
                sCategory.approveCategory(category1);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, category1.getName() + " nije se uspesno ubacila u bazu", ""));
                category1.setApproved(Boolean.FALSE);
            }
        }
        categoryList.clear();
    }

    public List<Category> autocompleteDeleteCategory(String text) {
        List<Category> temp = sCategory.autocompleteDeleteCategory(text);
        if (temp != null && !temp.isEmpty()) {
            autocomplete = temp;
        }
        return temp;
    }

    public void deleteCategory(Category q) {
        if (categoryList == null) {
            return;
        }
        for (Category category1 : categoryList) {
            try {
                sCategory.deleteCategory(category1);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, category1.getName() + " nije se uspesno ubacila u bazu", ""));
                category1.setApproved(Boolean.FALSE);
            }
        }
        categoryList.clear();
    }

    public void addToTable() {
    }

    public void resetTable() {
    }

    public void saveCategories() {
    }

    public SessionCategoryLocal getsCategory() {
        return sCategory;
    }

    public void setsCategory(SessionCategoryLocal sCategory) {
        this.sCategory = sCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }

    public List<Category> getAutocomplete() {
        return autocomplete;
    }

    public void setAutocomplete(List<Category> autocomplete) {
        this.autocomplete = autocomplete;
    }

    public List<Category> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Category> filteredList) {
        this.filteredList = filteredList;
    }

    public void onCellEdit(CellEditEvent event) {
        String oldValue = (String) event.getOldValue();
        String newValue = (String) event.getNewValue();
        for (Category category : autocomplete) {
            if (category.getName().equals(newValue)) {
                try {
                    sCategory.updateCategory(category);
                } catch (Exception ex) {
                    Logger.getLogger(MBQuestion.class.getName()).log(Level.SEVERE, null, ex);
                }
                autocomplete = sCategory.getCategories();
                break;
            }
        }
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Promenjen tekst", "Stari: " + oldValue + ", Novi:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public List<Category> getCategories() {
        autocomplete = sCategory.getCategories();
        return autocomplete;
    }

}
