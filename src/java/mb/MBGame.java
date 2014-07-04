package mb;

import domain.Answer;
import domain.Client;
import domain.Question;
import domain.Result;
import domain.Test;
import domain.Testitem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedProperty;
import session.answer.SessionAnswerLocal;
import session.question.SessionQuestionLocal;
import session.result.SessionResultLocal;
import session.test.SessionTestLocal;

/**
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
@Named(value = "mBGame")
@Dependent
public class MBGame {

    private Question activeQuestion;
    private Test test;
    private Result result;
    private List<Question> questions;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    @EJB
    SessionQuestionLocal sQuestion;

    @EJB
    SessionAnswerLocal sAnswer;

    @EJB
    SessionTestLocal sTest;

    @EJB
    SessionResultLocal sResult;

    /**
     * Creates a new instance of MBGame
     */
    public MBGame() {

    }

    /**
     * From which category the test should be.
     */
    public void newTest() {
        test = new Test();
        test.setIdCat(mBSession.getChosenCategory());
        result = new Result();
        result.setIdClient(mBSession.getActiveClient());
        result.setIdTest(test);
        questions = sQuestion.getQuestions(mBSession.getChosenCategory());
    }

    /**
     * Loading new question.
     */
    public void loadNewQuestion() {
        int number = 0;
        while (!questions.isEmpty()) {
            number = new Random().nextInt(questions.size());
            if (!questions.get(number).getDeleted() && questions.get(number).getApproved()) {
                break;
            } else {
                questions.remove(number);
            }
        }
        Testitem testItem = new Testitem();
        test.getTestitemList().add(testItem);
        testItem.setIdTest(test);
        if (questions.isEmpty()) {
            Question question = new Question();
        } else {
            testItem.setIdQuestion(questions.get(number));
            testItem.getIdQuestion().setAnswerList(generateAnswers(questions.get(number)));
            questions.remove(number);
            activeQuestion = testItem.getIdQuestion();
        }
    }

    public void answerQuestion(Answer answer) {
        if (answer.getCorrect()) {
            //every correct answer is one point worth
            result.setValue(result.getValue() + 1);
        }
    }

    public Question getActiveQuestion() {
        return activeQuestion;
    }

    /**
     * Generate 4 answers for one question.
     *
     * @param question
     * @return
     */
    private List<Answer> generateAnswers(Question question) {
        Random random = new Random();
        List<Answer> allAnswers = sAnswer.getAnswers(question);
        List<Answer> answers = new ArrayList<>();
        //adding 3 wrong answers
        int i = 0;
        while (i < 3) {
            int number = random.nextInt(allAnswers.size());
            if (!allAnswers.get(number).getCorrect()) {
                answers.add(allAnswers.get(number));
                allAnswers.remove(number);
            } else {
                i++;
            }
        }
        //adding correct answer
        for (Answer answer : allAnswers) {
            if (answer.getCorrect()) {
                answers.add(answer);
            }
        }
        //shuffle answers so the correct one won't be last
        Collections.shuffle(answers);
        return answers;
    }

    /**
     * Check if question is loaded.
     *
     * @return
     */
    public boolean isThereMoreQuestions() {
        return activeQuestion != null;
    }

    /**
     * Get texts from answers.
     *
     * @return 4 strings
     */
    public String[] getAnswersText() {
        String[] texts = new String[4];
        int i = 0;
        for (Answer answer : activeQuestion.getAnswerList()) {
            texts[i] = answer.getText();
            i++;
        }
        return texts;
    }

    public String getAnswerText1() {
        return activeQuestion.getAnswerList().get(0).getText();
    }

    public String getAnswerText2() {
        return activeQuestion.getAnswerList().get(1).getText();
    }

    public String getAnswerText3() {
        return activeQuestion.getAnswerList().get(2).getText();
    }

    public String getAnswerText4() {
        return activeQuestion.getAnswerList().get(3).getText();
    }

    /**
     * Get text from Question.
     *
     * @return
     */
    public String getQuestionText() {
        String temp = activeQuestion.getText();
        StringBuilder builder = new StringBuilder(temp);
        boolean canDo = true;
        final int NEW_LINE = 70;
        final int RANGE = 50;
        for (int i = 1; i < temp.length() / NEW_LINE; i++) {
            int j = 0;
            for (; j < RANGE; j++) {
                if (i * NEW_LINE + j >= temp.length()) {
                    canDo = false;
                }
                if (temp.charAt(i * NEW_LINE + j) == ' ') {
                    break;
                }
            }
            if (canDo) {
                builder.insert(i * NEW_LINE + j + 1, "\n");
            }
        }
        return builder.toString();
    }

    /**
     * Method for answering question.
     *
     * @param text
     */
    public void answerQuestion(String text) {
        for (Answer answer : activeQuestion.getAnswerList()) {
            if (answer.getText().equals(text)) {
                answerQuestion(answer);
                return;
            }
        }
    }

    /**
     * Was answer correct.
     *
     * @param text
     * @return
     */
    public boolean isCorrect(String text) {
        for (Answer answer : activeQuestion.getAnswerList()) {
            if (answer.getText().equals(text)) {
                return answer.getCorrect();
            }
        }
        return false;
    }

    /**
     * Method for returning test that the client do.
     *
     * @param client
     * @return
     */
    public Test letMeSeeTest(Client client) {
        return test;
    }

    /**
     * Return result that of test that client do.
     *
     * @param client
     * @return
     */
    public Result getResultOfTest(Client client) {
        return result;
    }

    /**
     * Method for saving test into the database.
     */
    public void saveResults() {
        try {
            sTest.saveTest(test);
            sResult.saveResult(result);
        } catch (Exception ex) {
            Logger.getLogger(MBGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
