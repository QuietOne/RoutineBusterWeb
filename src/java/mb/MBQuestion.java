/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Question;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import session.question.SessionQuestionLocal;

/**
 *
 * @author Jelena Tabaš
 */
@Named(value = "mBQuestion")
@Dependent
public class MBQuestion {

    private String questionText;
    private List<Question> questionList;

    @EJB
    SessionQuestionLocal sQuestion;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    /**
     * Creates a new instance of MBQuestion
     */
    public MBQuestion() {
        questionList = new ArrayList<>();
    }

    public List<Question> autoCompleteQuestion(String text) {
        questionList = sQuestion.autocompleteQuestion(text);
        //   return sQuestion.autocompleteQuestion(text);
        return questionList;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

}
