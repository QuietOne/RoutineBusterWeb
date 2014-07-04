package mb;

import domain.Category;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import session.category.SessionCategoryLocal;

/**
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
@ManagedBean(name="categoryTree")
@ViewScoped
public class CategoryTree implements Serializable{
    
    @EJB
    SessionCategoryLocal sCategory;
    
    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;
    
    private Category categoryTree; 
    private TreeNode root;
    
    @PostConstruct
    public void init() {
        categoryTree = sCategory.getCategoryTree();
        root = new DefaultTreeNode(categoryTree, null);
        addChildren(root, categoryTree);
    }
    
    private void addChildren(TreeNode node, Category category){
        if (category.getCategoryList()!=null) {
            for (Category category1 : category.getCategoryList()) {
                TreeNode treeNode = new DefaultTreeNode(category1, node);
                node.getChildren().add(treeNode);
                addChildren(treeNode, category1);
            }
        } else {
            
        }
    }
    
    public String onNodeClick(TreeNode node){
        if (!node.isLeaf()) {
            return "#";
        }
        mBSession.setChosenCategory((Category) node.getData());
        return "/main/game.xhtml";
    }
 
    public TreeNode getRoot() {
        return root;
    }

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }
    
    
}
