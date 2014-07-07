/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Category;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import session.category.SessionCategoryLocal;

/**
 *
 * @author Jelena Tabaš
 */
@Named(value = "mBCategory")
@Dependent
public class MBCategory {

    private String categoryName;
    private List<Category> categoryList;
    private List<Category> autocomplete;

    @EJB
    private SessionCategoryLocal sCategory;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    /**
     * Creates a new instance of MBQuestion
     */
    public MBCategory() {
        categoryList = new ArrayList<>();
    }

    public List<Category> autoCompleteCategory(String name) {
        //    categoryList = sCategory.autocompleteCategory(name);        
        return sCategory.autocompleteCategory(name);
    }

    public List<Category> autocompleteApproveCategory(String text) {
        List<Category> temp = sCategory.autocompleteApproveCategory(text);
        if (temp != null && !temp.isEmpty()) {
            autocomplete = temp;
        }
        System.out.println("Autocomplete load: " + temp);;
        return temp;
    }

    public void saveApproveCategories(Category c) {
        if (categoryList == null) {
            return;
        }
        System.out.println("Save " + categoryList);
        for (Category category1 : categoryList) {
            try {
                category1.setApproved(Boolean.TRUE);
                sCategory.updateCategory(category1);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, category1.getName()+ " nije se uspesno ubacila u bazu", ""));
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

}
