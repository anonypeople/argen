package unalcol.evolution.haea;

import unalcol.ca.CambrianExtinctionCA;
import unalcol.search.Goal;
import unalcol.search.population.PopulationSolution;
import unalcol.search.selection.Selection;
import unalcol.search.space.Space;
import unalcol.types.collection.vector.Vector;

/**
 * <p>Title: CAHAEAStrategy</p>
 * <p>Description: The Cellular Automata based Hybrid Adaptive Evolutionary Algorithm Offspring Generation strategy as
 * proposed by Cantor and Gomez in , Proceedings of WCCI 2010.</p>
 * <p>Copyright:    Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class CellularHaeaStep<T> extends HaeaStep<T> {
    /**
     * CambrianExtiction population resizing mechanism.
     */
    protected CambrianExtinctionCA ca = null;

    /**
     * Constructor: Creates a CAHaea offspring generation strategy
     *
     * @param operators Genetic operators used to evolve the solution
     * @param grow      Growing function
     * @param selection Extra parent selection mechanism
     */
    public CellularHaeaStep(int n, HaeaReplacement<T> replacement, Selection<T> selection) {
        super(n, replacement, selection);
    }

    public CellularHaeaStep(int n, HaeaOperators<T> operators, Selection<T> selection) {
        super(n, operators, selection);
    }

    /**
     * Gets a subpopulation that can be used for selecting a second parent
     *
     * @param id         First parent
     * @param population Full Population
     * @return A subpopulation that can be used for selecting a second parent
     */
    public Vector<Integer> select(int id, PopulationSolution<T> population) {
        Vector<Integer> pop = new Vector<Integer>();
        int[][] neighboor = ca.neighborhood(id);
        int i = 0;
        while (neighboor[i][0] >= 0) {
            pop.add(ca.id(neighboor[i][0], neighboor[i][1]) % n);
            i++;
        }
        return pop;
    }

    /**
     * Determines if the individual can be selected as firts parent
     *
     * @param id Individuals's id
     * @return <i>true</i> if the individual can be selected as first parent, <i>false</i> otherwise
     */
    public boolean available(int id) {
        return ca.state(id);
    }


    /**
     * Generates a population of offspring individuals following cahaea rules.
     *
     * @param population The population to be transformed
     * @param replace    Replacement mechanism
     * @param f          Function to be optimized
     */
    @Override
    public PopulationSolution<T> apply(PopulationSolution<T> population, Space<T> space, Goal<T> goal) {
        if (ca == null) {
            int rows = (int) Math.sqrt(population.size());
            int columns = population.size() / rows;
            columns = (rows * columns < population.size()) ? columns + 1 : columns;
            ca = new CambrianExtinctionCA(rows, columns, 0.33);
        }
        ca.simulate();
        return super.apply(population, space, goal);
    }

}