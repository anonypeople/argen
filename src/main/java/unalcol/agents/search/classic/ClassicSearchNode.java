package unalcol.agents.search.classic;

import unalcol.agents.Action;
import unalcol.agents.search.State;
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
public class ClassicSearchNode {
    protected State state;
    protected Vector<Action> path;
    protected double cost;

    public ClassicSearchNode(State _state, Vector<Action> _path, double _cost) {
        state = _state;
        path = _path;
        cost = _cost;
    }
}
