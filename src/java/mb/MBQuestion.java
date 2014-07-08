package mb;

import domain.Question;
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
import session.question.SessionQuestionLocal;

/**
 *
 * @author Jelena Tabaš
 */
@ManagedBean(name = "mBQuestion")
@ViewScoped
public class MBQuestion implements Serializable {

    private List<Question> questionList;
    private List<Question> autocomplete;
    private List<Question> filteredList;

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
        autocomplete = sQuestion.getQuestions();
    }

    public List<Question> autocompleteQuestion(String text) {
        List<Question> temp = sQuestion.autocompleteQuestion(text);
        if (temp != null && !temp.isEmpty()) {
            autocomplete = temp;
        }
        return temp;
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

    public void updateQuestions(Question q) {
        if (questionList == null) {
            return;
        }
        for (Question question1 : questionList) {
            try {
                sQuestion.updateQuestion(question1);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, question1.getText() + " nije se uspesno ubacilo u bazu", ""));
                question1.setApproved(Boolean.FALSE);
            }
        }
        questionList.clear();
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

    public void deleteQuestions(Question q) {
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

    public List<Question> getQuestions() {
        autocomplete = sQuestion.getQuestions();
        return autocomplete;
    }

    public List<Question> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Question> filteredList) {
        this.filteredList = filteredList;
    }

    public void onCellEdit(CellEditEvent event) {
        String oldValue = (String) event.getOldValue();
        String newValue = (String) event.getNewValue();
        for (Question question : autocomplete) {
            if (question.getText().equals(newValue)) {
                try {
                    sQuestion.updateQuestion(question);
                } catch (Exception ex) {
                    Logger.getLogger(MBQuestion.class.getName()).log(Level.SEVERE, null, ex);
                }
                autocomplete = sQuestion.getQuestions();
                break;
            }
        }
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Promenjen tekst", "Stari: " + oldValue + ", Novi:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
