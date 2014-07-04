package mb;

import domain.Category;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import session.category.SessionCategoryLocal;

/**
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
@ManagedBean(name = "categoryTree")
@ViewScoped
public class CategoryTree implements Serializable {

    @EJB
    private SessionCategoryLocal sCategory;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;
    
    @ManagedProperty(value = "#{mBGame}")
    private MBGame mBGame;

    private Category categoryTree;
    private TreeNode root;

    @PostConstruct
    public void init() {
        categoryTree = sCategory.getCategoryTree();
        root = new DefaultTreeNode(categoryTree, null);
        addChildren(root, categoryTree);
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
        if (event.getTreeNode().isLeaf()) {
            mBSession.setChosenCategory((Category) event.getTreeNode().getData());
            mBSession.setGameOn(true);
            mBGame.init();
        } else {
            if (event.getTreeNode().isExpanded()) {
                event.getTreeNode().setExpanded(false);
            } else {
                event.getTreeNode().setExpanded(true);
            }
        }
        event.getTreeNode().setSelected(false);
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

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }

    public Category getCategoryTree() {
        return categoryTree;
    }

    public void setCategoryTree(Category categoryTree) {
        this.categoryTree = categoryTree;
    }

    public MBGame getmBGame() {
        return mBGame;
    }

    public void setmBGame(MBGame mBGame) {
        this.mBGame = mBGame;
    }

    
}
