package mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
@ManagedBean(name = "mBTimer")
@ViewScoped
public class MBTimer implements Serializable {

    private boolean gameOver;

    private int number;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    @ManagedProperty(value = "#{mBGame}")
    private MBGame mBGame;

    /**
     * Creates a new instance of MBTimer
     */
    public MBTimer() {
        number = 20;
        gameOver = false;
    }

    public int getNumber() {
        return number;
    }

    public void decrease() {
        number--;
    }

    public String getMessage() {
        return "Time left: " + number;
    }

    public boolean getStop() {
        if (number == 0 || !mBGame.isThereMoreQuestions()) {
            gameOver = true;
            mBSession.setGameOn(false);
            mBGame.saveResults();
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }

    public MBGame getmBGame() {
        return mBGame;
    }

    public void setmBGame(MBGame mBGame) {
        this.mBGame = mBGame;
    }

}
