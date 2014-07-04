package mb;

import domain.Category;
import domain.Client;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jelena Tabas
 */
@ManagedBean
@SessionScoped
public class MBSession implements Serializable{

    private Client activeClient;
    private Category chosenCategory;
    private boolean gameOn;

    /**
     * Creates a new instance of MBSession
     */
    public MBSession() {
        gameOn = false;
    }
    
    public Client getActiveClient() {
        return activeClient;
    }

    public void setActiveClient(Client activeClient) {
        this.activeClient = activeClient;
    }

    public Category getChosenCategory() {
        return chosenCategory;
    }

    public void setChosenCategory(Category chosenCategory) {
        this.chosenCategory = chosenCategory;
    }

    @Override
    public String toString() {
        return "activeClient=" + activeClient + "\nchosenCategory=" + chosenCategory;
    }

    public boolean getGameOn() {
        return gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }
 
 }
