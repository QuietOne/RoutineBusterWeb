package mb;

import domain.Category;
import domain.Client;
import domain.Question;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jelena Tabas
 */
@ManagedBean
@SessionScoped
public class MBSession {

    private Client activeClient;
    private Category chosenCategory;

    /**
     * Creates a new instance of MBSession
     */
    public MBSession() {
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

 }
