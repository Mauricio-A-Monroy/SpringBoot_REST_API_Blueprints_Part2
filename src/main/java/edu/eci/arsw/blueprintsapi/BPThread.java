package edu.eci.arsw.blueprintsapi;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;


public class BPThread extends Thread{
    private Blueprint bp;

    @Autowired
    private BlueprintsServices blueprintsServices;
    public BPThread (Blueprint bp){
        this.bp = bp;
    }

    public void run(){
        try {
            blueprintsServices.addNewBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            throw new RuntimeException(e);
        }
    }
}
