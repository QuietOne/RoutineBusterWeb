package mb;

import domain.Answer;
import domain.Client;
import domain.Question;
import domain.Result;
import domain.Test;
import domain.Testitem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import session.answer.SessionAnswerLocal;
import session.question.SessionQuestionLocal;
import session.result.SessionResultLocal;
import session.test.SessionTestLocal;

/**
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
@ManagedBean(name = "mBGame")
@ViewScoped
public class MBGame implements Serializable {

    private Question activeQuestion;
    private Test test;
    private Result result;
    private List<Question> questions;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    @EJB
    private SessionQuestionLocal sQuestion;
    @EJB
    private SessionAnswerLocal sAnswer;
    @EJB
    private SessionTestLocal sTest;
    @EJB
    private SessionResultLocal sResult;

    /**
     * Creates a new instance of MBGame
     */
    public MBGame() {
        activeQuestion = new Question();
        List<Answer> array = new ArrayList<Answer>();
        for (int i = 0; i < 4; i++) {
            array.add(new Answer(i + 1 + ""));
        }
        activeQuestion.setAnswerList(array);
    }

    public void init() {
        System.out.println("napisano: " + mBSession.getGameOn());
        newTest();
        if (mBSession.getGameOn()) {
            loadNewQuestion();
        }

    }

    /**
     * From which category the test should be.
     */
    public void newTest() {
        test = new Test();
        test.setIdCat(mBSession.getChosenCategory());
        result = new Result();
        result.setValue(new Integer(0));
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
        test.setTestitemList(new ArrayList<Testitem>());
        test.getTestitemList().add(testItem);
        testItem.setIdTest(test);
        if (questions.isEmpty()) {
            activeQuestion = null;
        } else {
            testItem.setIdQuestion(questions.get(number));
            List<Answer> answers = generateAnswers(questions.get(number));
            testItem.getIdQuestion().setAnswerList(answers);
            questions.remove(number);
            activeQuestion = testItem.getIdQuestion();
        }
    }

    public void answerQuestion(Answer answer) {
        if (answer.getCorrect()) {
            //every correct answer is one point worth
            result.increaseValue();
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

    public void evaluateAnswer1() {
        answerQuestion(activeQuestion.getAnswerList().get(0));
        loadNewQuestion();
    }

    public String getAnswerText2() {
        return activeQuestion.getAnswerList().get(1).getText();
    }

    public void evaluateAnswer2() {
        answerQuestion(activeQuestion.getAnswerList().get(1));
        loadNewQuestion();
    }

    public String getAnswerText3() {
        return activeQuestion.getAnswerList().get(2).getText();
    }

    public void evaluateAnswer3() {
        answerQuestion(activeQuestion.getAnswerList().get(2));
        loadNewQuestion();
    }

    public String getAnswerText4() {
        return activeQuestion.getAnswerList().get(3).getText();
    }

    public void evaluateAnswer4() {
        answerQuestion(activeQuestion.getAnswerList().get(3));
        loadNewQuestion();
    }

    /**
     * Get text from Question.
     *
     * @return
     */
    public String getQuestionText() {
        String temp = activeQuestion.getText();
        if (temp==null) {
            return "";
        }
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

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }

    public SessionQuestionLocal getsQuestion() {
        return sQuestion;
    }

    public void setsQuestion(SessionQuestionLocal sQuestion) {
        this.sQuestion = sQuestion;
    }

    public SessionAnswerLocal getsAnswer() {
        return sAnswer;
    }

    public void setsAnswer(SessionAnswerLocal sAnswer) {
        this.sAnswer = sAnswer;
    }

    public SessionTestLocal getsTest() {
        return sTest;
    }

    public void setsTest(SessionTestLocal sTest) {
        this.sTest = sTest;
    }

    public SessionResultLocal getsResult() {
        return sResult;
    }

    public void setsResult(SessionResultLocal sResult) {
        this.sResult = sResult;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
