package mb;

import domain.Question;
import java.awt.event.FocusEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import session.question.SessionQuestionLocal;

/**
 *
 * @author Jelena Tabaš
 */
@ManagedBean(name = "mBQuestion")
@ViewScoped
public class MBQuestion implements Serializable{

    private List<Question> questionList;
    private List<Question> autocomplete;

    @EJB
    private SessionQuestionLocal sQuestion;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    /**
     * Creates a new instance of MBQuestion
     */
    public MBQuestion() {

    }

    @PostConstruct
    public void init() {
        questionList = new ArrayList<>();
        autocomplete = new ArrayList<>();
    }

    public List<Question> autocompleteQuestion(String text) {
        return sQuestion.autocompleteQuestion(text);
    }

    public List<Question> autocompleteApproveQuestion(String text) {
        List<Question> temp = sQuestion.autocompleteApproveQuestion(text);
        if (temp != null && !temp.isEmpty()) {
            autocomplete = temp;
        }
        return temp;
    }
    
    public List<Question> autocompleteDeleteQuestion(String text) {
        List<Question> temp = sQuestion.autocompleteDeleteQuestion(text);
        if (temp != null && !temp.isEmpty()) {
            autocomplete = temp;
        }
        return temp;
    }
    
    public void saveApproveQuestions(Question q) {
        if (questionList == null) {
            return;
        }
        for (Question question1 : questionList) {
            try {
                sQuestion.approveQuestion(question1);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, question1.getText() + " nije se uspesno ubacilo u bazu", ""));
                question1.setApproved(Boolean.FALSE);
            }
        }
        questionList.clear();
    }
    
    public void deleteQuestions(Question q){
        if (questionList == null) {
            return;
        }
        for (Question question1 : questionList) {
            try {
                sQuestion.deleteQuestion(question1);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, question1.getText() + " nije se uspesno ubacilo u bazu", ""));
                question1.setDeleted(Boolean.FALSE);
            }
        }
        questionList.clear();
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public SessionQuestionLocal getsQuestion() {
        return sQuestion;
    }

    public void setsQuestion(SessionQuestionLocal sQuestion) {
        this.sQuestion = sQuestion;
    }

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }

    public List<Question> getAutocomplete() {
        return autocomplete;
    }

    public void setAutocomplete(List<Question> autocomplete) {
        this.autocomplete = autocomplete;
    }
}
