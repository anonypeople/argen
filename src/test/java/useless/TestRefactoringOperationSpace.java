/**
 *
 */
package useless;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import biorimp.optmodel.space.RefactoringOperationSpace;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.main.MainPredFormulasBIoRIPM;

import java.util.List;

/**
 * @author dnader
 */
public class TestRefactoringOperationSpace {

    /**
     *
     */
    public static void main(String[] argss) {
        //Getting the Metaphor
        String userPath = System.getProperty("user.dir");
        String[] args = {"-l", "Java", "-p", userPath + "\\test_data\\code\\optimization\\src", "-s", "java/optmodel/fitness      "};
        MainPredFormulasBIoRIPM init = new MainPredFormulasBIoRIPM();
        init.main(args);
        MetaphorCode metaphor = new MetaphorCode(init, 0);

        //Creating the Space
        RefactoringOperationSpace refactorSpace = new RefactoringOperationSpace(1000);

        //Visualizing the get() Space
        List<RefactoringOperation> refactorSpaceG = refactorSpace.get();

        if (refactorSpaceG != null)
            for (RefactoringOperation refOper : refactorSpaceG) {
                System.out.println("Random Refactor: " + refOper.toString());
            }

        //Visualizing feasible individual

        System.out.println("Feasible Refactor: " + "[" + refactorSpace.feasible(refactorSpaceG) + "]");

    }

}
