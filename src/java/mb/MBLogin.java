package mb;

import domain.Client;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.user.SessionClientLocal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     * Creates a new instance of MBLogovanje
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
    //    return "/main/main.xhtml";
    //    System.out.println("Username: " + activeClient.getUsername() + "; password: " + activeClient.getPassword());

        try {
            isActive = SBClient.validateLogin(activeClient.getUsername(), activeClient.getPassword());
            activeClient = SBClient.getClient(activeClient.getUsername());
         //   System.out.println("redirect!!");
            mBSession.setActiveClient(activeClient);
            return "/main/main.xhtml"; //"/radSaProizvodima/prikazProizvoda.xhtml";
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Korisnik nije uspesno ucitan", ""));
        }
        return null;
   }

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
