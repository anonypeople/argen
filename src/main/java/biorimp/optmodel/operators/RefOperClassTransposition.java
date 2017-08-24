package biorimp.optmodel.operators;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import biorimp.optmodel.space.RefactoringOperationSpace;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.main.MainPredFormulasBIoRIPM;
import unalcol.clone.Clone;
import unalcol.random.integer.IntUniform;
import unalcol.search.space.ArityOne;

import java.util.List;

/**
 * <p>Title: Transposition</p>
 * <p>Description: The simple transposition operator (without flanking)</p>
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */

public class RefOperClassTransposition extends ArityOne<List<RefactoringOperation>> {
    public RefOperClassTransposition() {
    }

    /**
     * Testing function
     */
    public static void main(String[] argv) {
        //Getting the Metaphor
        String userPath = System.getProperty("user.dir");
        String[] args = {"-l", "Java", "-p", userPath + "\\test_data\\code\\optimization\\src", "-s", "java/optmodel/fitness      "};
        MainPredFormulasBIoRIPM init = new MainPredFormulasBIoRIPM();
        init.main(args);
        MetaphorCode metaphor = new MetaphorCode(init, 0);

        System.out.println("*** Generating a genome of 10 genes randomly ***");

        //Creating the Space
        RefactoringOperationSpace refactorSpace = new RefactoringOperationSpace(3);
        List<RefactoringOperation> parent1 = refactorSpace.get();
        System.out.println(parent1.toString());

        RefOperClassTransposition trans = new RefOperClassTransposition();

        System.out.println("*** Applying the Tranposition ***");
        List<RefactoringOperation> kids = trans.apply(parent1);

        System.out.println("*** Child 1 ***");
        System.out.println(kids.toString());


    }

    /**
     * Interchange the bits between two positions randomly chosen
     * Example:      genome = 100011001110
     * Transposition 2-10:    101100110010
     *
     * @param _genome Genome to be modified
     */
    @Override
    public List<RefactoringOperation> apply(List<RefactoringOperation> _genome) {
        try {
            List<RefactoringOperation> genome = (List<RefactoringOperation>) Clone.create(_genome);

            IntUniform gen = new IntUniform(genome.size());
            int start = gen.next();
            RefactoringOperation tr;
            tr = genome.get(start);
            tr.setParamsTrans();
            return genome;

        } catch (Exception e) {
        }
        return null;
    }
}
