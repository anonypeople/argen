package unalcol.agents;

import unalcol.types.collection.vector.Vector;

/**
 * <p>Title: AgentArchitecture </p>
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
public interface AgentArchitecture {
    public Percept sense(Agent agent);

    public boolean act(Agent agent, Action action);

    public void init(Agent agent);

    public Vector<Action> actions();
}
