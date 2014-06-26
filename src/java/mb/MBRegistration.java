/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Client;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.user.SessionClientLocal;

/**
 *
 * @author Jelena Tabas
 */
@ManagedBean
@RequestScoped
public class MBRegistration {

    Client client;

    @EJB
    SessionClientLocal sbClient;

    @ManagedProperty(value = "#{mBSession}")
    private MBSession mBSession;

    /**
     * Creates a new instance of MBUnosProizvoda
     */
    public MBRegistration() {

    }

    @PostConstruct
    public void initialization() {

        client = mBSession.getActiveClient();
        //  proizvod = mbSesija.getIzabraniProizvod();

        if (client == null) {
            client = new Client();
                       
            /*   proizvod = new Proizvod();
             tipProizvoda = "Prehrambeni";
            
             proizvod.setPrehrambeniproizvod(new Prehrambeniproizvod());
             proizvod.getPrehrambeniproizvod().setProizvod(proizvod);
             proizvod.setTehnickiproizvod(null);
             } else {
             if (proizvod.getPrehrambeniproizvod() != null) {
             tipProizvoda = "Prehrambeni";
             } else {
             tipProizvoda = "Tehnicki";
             }*/
        }

        mBSession.setActiveClient(null);
    }

    /*  public boolean daLiProPrehrambeni() {

     if (tipProizvoda.equals("Prehrambeni")) {
     return true;
     }
     return false;
     }

     public boolean daLiProTehnicki() {

     if (tipProizvoda.equals("Tehnicki")) {
     return true;
     }
     return false;
     }
     public void izabranTipProizvoda() {

     System.out.println("izbran radio!!!");
     if (tipProizvoda.equals("Tehnicki")) {
     proizvod.setTehnickiproizvod(new Tehnickiproizvod());
     proizvod.getTehnickiproizvod().setProizvod(proizvod);
     proizvod.setPrehrambeniproizvod(null);
     }
     if (tipProizvoda.equals("Prehrambeni")) {
     System.out.println("napravio prehrambeni");
     proizvod.setPrehrambeniproizvod(new Prehrambeniproizvod());
     proizvod.getPrehrambeniproizvod().setProizvod(proizvod);
     proizvod.setTehnickiproizvod(null);
     }

     }


     */
    public void addClient() {
        try {
            sbClient.addClient(client);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je sacuvan korisnik!", ""));
        } catch (Exception e) {
            Logger.getLogger(MBRegistration.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:" + e.toString(), ""));
        }

        /*   try {
         if (tipProizvoda.equals("Tehnicki")) {
         proizvod.getTehnickiproizvod().setProizvodID(proizvod.getProizvodID());
         } else {
         proizvod.getPrehrambeniproizvod().setProizvodID(proizvod.getProizvodID());
         }

         sbProizvod.sacuvajProizvod(proizvod);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je sacuvan proizvod!!!", ""));

         } catch (Exception ex) {
         Logger.getLogger(MBUnosProizvoda.class.getName()).log(Level.SEVERE, null, ex);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:" + ex.toString(), ""));
         }
         */
    }

    public MBSession getmBSession() {
        return mBSession;
    }

    public void setmBSession(MBSession mBSession) {
        this.mBSession = mBSession;
    }
}
