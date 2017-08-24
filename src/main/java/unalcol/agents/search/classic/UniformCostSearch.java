package unalcol.agents.search.classic;

import unalcol.types.collection.vector.SortedVector;

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
public class UniformCostSearch extends ClassicSearch {
    public UniformCostSearch(int _max_depth) {
        super(_max_depth);
        list = new SortedVector<ClassicSearchNode>(new ClassicSearchNodeOrder());
    }


    public void add(ClassicSearchNode child) {
        list.add(child);
    }

}
