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
import javax.faces.bean.ManagedProperty;
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
