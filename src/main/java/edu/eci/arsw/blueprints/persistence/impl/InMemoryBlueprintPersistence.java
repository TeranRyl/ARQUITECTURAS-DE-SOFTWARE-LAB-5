/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import java.util.*;

import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

/**
 *
 * @author Guardianes de la Galaxia
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

	private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

	public InMemoryBlueprintPersistence() {
		// load stub data
		Point[] pts1 = new Point[] { new Point(140, 140), new Point(115, 115) };
		Point[] pts2 = new Point[] { new Point(140, 140), new Point(115, 115) };
		Point[] pts3 = new Point[] { new Point(140, 140), new Point(115, 115) };

		Blueprint bp1 = new Blueprint("Daniel", "Casa1", pts1);
		Blueprint bp2 = new Blueprint("Juan", "Edificio1", pts2);
		Blueprint bp3 = new Blueprint("Daniel", "Casa2", pts3);

		blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
		blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
		blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);

	}

	@Override
	public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
		Set<Blueprint> bluePrints;
		if (!blueprints.isEmpty())
			bluePrints = new HashSet<>(blueprints.values());
		else
			throw new BlueprintNotFoundException("There are no registered plans.");
		return bluePrints;
	}

	@Override
	public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
		if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
			throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
		} else {
			blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
		}
	}

	@Override
	public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
		Blueprint bluePrint = null;
		boolean flag = false;
		for (Blueprint blueprint : blueprints.values()) {
			if ((blueprint.getName().equals(bprintname)) && (blueprint.getAuthor().equals(author))) {
				bluePrint = blueprint;
				flag = true;
			}
		}
		if (!flag)
			throw new BlueprintNotFoundException("The plan you are looking for cannot be found.");

		return bluePrint;
	}

	@Override
	public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
		Set<Blueprint> blueprintsByAuthor = new HashSet<>();
		for (Tuple tuple : blueprints.keySet()) {
			if (tuple.getElem1().equals(author)) {
				blueprintsByAuthor.add(blueprints.get(tuple));
			}
		}
		if (blueprintsByAuthor.isEmpty())
			throw new BlueprintNotFoundException("No plans have been found in that person's name.");
		return blueprintsByAuthor;
	}
	@Override
	public void setBlueprint(String author, String bprintname, Blueprint bp) throws BlueprintPersistenceException {
		if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
			throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
		} else {
			Blueprint bluePrint = new Blueprint(author, bprintname);
			bluePrint.setPoints(bp.getPoints());
			blueprints.remove(new Tuple<>(bp.getAuthor(), bp.getName()));
			blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bluePrint);
		}
	}
}