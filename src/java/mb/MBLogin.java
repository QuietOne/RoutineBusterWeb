package mb;

import domain.Client;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.client.SessionClientLocal;

/**
 *
 * @author Jelena Tabas
 */
@ManagedBean
@RequestScoped
public class MBLogin {

    private Client activeClient;
    private boolean isActive;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    /**
     * Creates a new instance of MBLogin
     */
    public MBLogin() {
        activeClient = new Client();
        isActive = false;
    }

    public Client getActiveClient() {
        return activeClient;
    }

    public void setTekuciKorisnik(Client activeClient) {
        this.activeClient = activeClient;
    }

    @EJB
    SessionClientLocal SBClient;

    public String logIn() {
        try {
            isActive = SBClient.validateLogin(activeClient.getUsername(), activeClient.getPassword());
            if (!isActive) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Korisnicko ime ili/i sifra nije dobro unešena", ""));
                return null;
            }
            activeClient = SBClient.getClient(activeClient.getUsername());
            mBSession.setActiveClient(activeClient);
            return "/main/main.xhtml";
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Korisnik nije uspesno ucitan", ""));
        }
        return null;
    }

    public String register() {
        return "/main/registration.xhtml";
    }

    public String returnTOLogin() {
        return "/main/index.xhtml";
    }

    public String logOut() {
        mBSession.setActiveClient(null);
        mBSession.setChosenCategory(null);
        mBSession.setGameOn(false);
        return "/main/index.xhtml";
    }

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        if (isActive) {
            return activeClient + " is active.";
        } else {
            return activeClient + " is not active.";
        }
    }

}
