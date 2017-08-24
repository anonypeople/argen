package unalcol.agents.search.classic;

import unalcol.agents.Action;
import unalcol.agents.search.ActionCost;
import unalcol.agents.search.SearchSpace;
import unalcol.agents.search.State;

/**
 * <p>Title: </p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Jonatan Gómez
 * @version 1.0
 */
public abstract class Heuristic implements ActionCost {
    protected SearchSpace space;

    public Heuristic(SearchSpace _space) {
        space = _space;
    }

    public abstract double evaluate(State state, Action action);
}
