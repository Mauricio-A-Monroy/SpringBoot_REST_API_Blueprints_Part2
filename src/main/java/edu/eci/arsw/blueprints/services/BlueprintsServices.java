/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.BluePrinterFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.*;

import edu.eci.arsw.blueprints.persistence.impl.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
   
    @Autowired
    BlueprintsPersistence bpp;

    @Autowired
    BluePrinterFilter bpf;
    
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }
    
    public Set<Blueprint> getAllBlueprints(){
        return bpf.filterBlueprints(bpp.getAllBluePrints());
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException{
        return bpf.filterBlueprint(bpp.getBlueprint(author, name));
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpf.filterBlueprints(bpp.getBlueprintsByAuthor(author));
    }

    public void updateBlueprint(String author, String name, List<Point> newPoints) throws BlueprintNotFoundException {
        Blueprint bp = bpp.getBlueprint(author, name);
        bp.setPoints(newPoints);
    }

    public int testThreads(){
        ArrayList<BPThread> threads = new ArrayList<>();
        for(int i = 3; i < 103; i++){
            Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
            threads.add(new BPThread(new Blueprint("Carlos", "plano" + i, pts), this));
            threads.get(i-3).start();
        }
        for(int i = 0; i < 100; i++){
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return getAllBlueprints().size();
    }

}
