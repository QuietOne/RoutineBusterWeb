/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Client;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import session.client.SessionClientLocal;

/**
 *
 * @author Jelena Tabaš
 */
@ManagedBean
@Named(value = "mBChangePassword")
@RequestScoped
public class MBChangePassword {

    private Client client;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    @EJB
    private SessionClientLocal sbClient;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    /**
     * Creates a new instance of MBChangePassword
     */
    public MBChangePassword() {
        client = new Client();
    }

    public void changePassword() {
        try {
         //   client = mBSession.getActiveClient();
         //   System.out.println(client.getUsername());
      //      sbClient.changePassword(mBSession.getActiveClient().getUsername(), oldPassword, newPassword, confirmPassword);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public SessionClientLocal getSbClient() {
        return sbClient;
    }

    public void setSbClient(SessionClientLocal sbClient) {
        this.sbClient = sbClient;
    }

}
