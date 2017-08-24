package unalcol.agents.search;

import unalcol.agents.Action;
import unalcol.types.collection.vector.Vector;

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
public interface Search {
    public Vector<Action> apply(State initial, SearchSpace space, GoalTest goal,
                                ActionCost cost);
}
