package unalcol.agents.search.classic;

import unalcol.sort.Order;

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
public class ClassicSearchNodeOrder extends Order<ClassicSearchNode> {
    public ClassicSearchNodeOrder() {
    }

    public String getCanonicalName() {
        return ClassicSearchNode.class.getCanonicalName();
    }

    public int compare(ClassicSearchNode one, ClassicSearchNode two) {
        return (int) (one.cost - two.cost);
    }
}
