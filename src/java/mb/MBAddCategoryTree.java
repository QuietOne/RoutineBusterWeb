package mb;

import domain.Category;
import domain.Question;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.event.ChangeEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import session.category.SessionCategoryLocal;
import session.question.SessionQuestionLocal;

/**
 *
 * @author tihomir
 */
@ManagedBean(name = "mBAddCategoryTree")
@ViewScoped
public class MBAddCategoryTree implements Serializable {

    /**
     * Creates a new instance of MBAddQuestionTree
     */
    public MBAddCategoryTree() {
    }

    private boolean input;
    private Category categoryChosen;
    private String name;

    @EJB
    private SessionCategoryLocal sCategory;

    @ManagedProperty(value = "#{mBCategory}")
    private MBCategory mBCategory;

    private Category categoryTree;
    private TreeNode root;

    @PostConstruct
    public void init() {
        categoryTree = sCategory.getCategoryTree();
        root = new DefaultTreeNode(categoryTree, null);
        addChildren(root, categoryTree);
        input = false;
    }

    private void addChildren(TreeNode node, Category category) {
        if (category.getCategoryList() != null) {
            for (Category category1 : category.getCategoryList()) {
                TreeNode treeNode = new DefaultTreeNode(category1, node);
                node.getChildren().add(treeNode);
                addChildren(treeNode, category1);
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        if (event.getTreeNode().isExpanded()) {
            event.getTreeNode().setExpanded(false);
        } else {
            event.getTreeNode().setExpanded(true);
        }
        event.getTreeNode().setSelected(false);
        input = true;
        categoryChosen = (Category) event.getTreeNode().getData();
    }

    public TreeNode getRoot() {
        return root;
    }

    public SessionCategoryLocal getsCategory() {
        return sCategory;
    }

    public void setsCategory(SessionCategoryLocal sCategory) {
        this.sCategory = sCategory;
    }

    public Category getCategoryTree() {
        return categoryTree;
    }

    public void setCategoryTree(Category categoryTree) {
        this.categoryTree = categoryTree;
    }

    public boolean getInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public MBCategory getmBCategory() {
        return mBCategory;
    }

    public void setmBCategory(MBCategory mBCategory) {
        this.mBCategory = mBCategory;
    }

    public Category getCategoryChosen() {
        return categoryChosen;
    }

    public void setCategoryChosen(Category categoryChosen) {
        this.categoryChosen = categoryChosen;
    }

    public void addCategory() {
        if (name == null || name.equals("")) {
            return;
        }
        Category category = new Category();
        category.setName(name);
        category.setApproved(false);
        category.setIdCate(categoryChosen);

        try {
            sCategory.addCategory(category);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodata je kategorija:", '"' + name + '"' + "u kategoriju " + categoryChosen);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Neuspeh", "Nov kategorija nije dodata");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
