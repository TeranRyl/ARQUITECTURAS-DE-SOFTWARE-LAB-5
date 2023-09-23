/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices bs;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoXX(){
        try {
            //obtener datos que se enviarán a través del API
            Set<Blueprint> data = bs.getAllBlueprints();
            return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            //Logger.getLogger(XXController.class.getName()).log(Level.SEVERE, (String) null);
            return new ResponseEntity<>("HTTP ERROR 404", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getByAuthor(@PathVariable String author){
        try {
            Set<Blueprint> data = bs.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
            //return new ResponseEntity<>(bs.getBlueprintsByAuthor(author),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("HTTP ERROR 404", HttpStatus.NOT_FOUND);

        }
    }

    @RequestMapping(value="/{author}/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getByNameAuthor(@PathVariable String author, @PathVariable String name){
        try {
            Blueprint data = bs.getBlueprint(author, name);
            return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
            //return new ResponseEntity<>(bs.getBlueprintsByAuthor(author),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("HTTP ERROR 404", HttpStatus.NOT_FOUND);

        }
    }

    @RequestMapping(value="/planos", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecursoXX(@RequestBody Blueprint o){
        try {
            //registrar dato
            bs.addNewBlueprint(o);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            //Logger.getLogger(XXController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP ERROR 404",HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value="/{author}/{name}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBluePrint(@PathVariable String author, @PathVariable String name, @RequestBody Blueprint bp) throws BlueprintPersistenceException {
        bs.setBlueprint(author, name, bp);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
        //return new ResponseEntity<>(bs.getBlueprintsByAuthor(author),HttpStatus.ACCEPTED);
    }


    
    
    
    
    
}

