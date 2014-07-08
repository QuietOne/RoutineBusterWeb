package mb;

import domain.Category;
import domain.Question;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import session.category.SessionCategoryLocal;
import session.question.SessionQuestionLocal;

/**
 *
 * @author tihomir
 */
@ManagedBean(name = "mBAddQuestionTree")
@ViewScoped
public class MBAddQuestionTree implements Serializable{

    /**
     * Creates a new instance of MBAddQuestionTree
     */
    public MBAddQuestionTree() {
    }

    private boolean input;
    private Category categoryChosen;
    private String text;
    
    @EJB
    private SessionCategoryLocal sCategory;
    @EJB
    private SessionQuestionLocal sQuestion;

    @ManagedProperty(value = "#{mBQuestion}")
    private MBQuestion mBQuestion;

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

    public MBQuestion getmBQuestion() {
        return mBQuestion;
    }

    public void setmBQuestion(MBQuestion mBQuestion) {
        this.mBQuestion = mBQuestion;
    }

    public Category getCategoryChosen() {
        return categoryChosen;
    }

    public void setCategoryChosen(Category categoryChosen) {
        this.categoryChosen = categoryChosen;
    }
    
    public void addQuestion(){
        if (text==null || text.equals("")) {
            return;
        }
        Question question = new Question();
        question.setIdCat(categoryChosen);
        question.setDeleted(false);
        question.setApproved(false);  
        question.setText(text);
        try {
            sQuestion.addQuestion(question);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodato je pitanje:", '"'+text+'"'+"u kategoriju "+categoryChosen );
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Neuspeh", "Novo pitanje nije dodato");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SessionQuestionLocal getsQuestion() {
        return sQuestion;
    }

    public void setsQuestion(SessionQuestionLocal sQuestion) {
        this.sQuestion = sQuestion;
    }
}
