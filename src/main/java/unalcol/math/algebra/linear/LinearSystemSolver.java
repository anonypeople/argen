package unalcol.math.algebra.linear;

/**
 * <p>Title: </p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class LinearSystemSolver {
    public abstract double[] solve(double[][] A, double[] b);
}
