/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Category;
import domain.Client;
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

  //  Proizvod izabraniProizvod;
    /**
     * Creates a new instance of MBSesija
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
}
