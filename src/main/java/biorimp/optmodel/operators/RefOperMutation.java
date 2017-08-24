package biorimp.optmodel.operators;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import biorimp.optmodel.space.Refactoring;
import biorimp.optmodel.space.RefactoringOperationSpace;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.*;
import edu.wayne.cs.severe.redress2.main.MainPredFormulasBIoRIPM;
import unalcol.clone.Clone;
import unalcol.random.integer.IntUniform;
import unalcol.random.util.RandBool;
import unalcol.search.space.ArityOne;

import java.util.List;

/**
 * <p>Title: Mutation</p>
 * <p>Description: The simple bit mutation operator</p>
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author danaderp
 * @version 1.0
 */

public class RefOperMutation extends ArityOne<List<RefactoringOperation>> {
    /**
     * Probability of mutating one single bit
     */
    protected double bit_mutation_rate = 0.0;

    /**
     * Constructor: Creates a mutation with a mutation probability depending on the size of the genome
     */
    public RefOperMutation() {
    }

    /**
     * Constructor: Creates a mutation with the given mutation rate
     *
     * @param bit_mutation_rate Probability of mutating each single bit
     */
    public RefOperMutation(double bit_mutation_rate) {
        this.bit_mutation_rate = bit_mutation_rate;
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
        RefactoringOperationSpace refactorSpace = new RefactoringOperationSpace(10);

        //Visualizing the get() Space
        List<RefactoringOperation> refactor = refactorSpace.get();
        if (refactor != null)
            for (RefactoringOperation refOper : refactor) {
                System.out.println("Random Refactor: " + refOper.toString());
            }


        //ListRefOperMutation mutation = new ListRefOperMutation(0.05);
        RefOperMutation mutation = new RefOperMutation(0.5);

        System.out.println("*** Applying the mutation ***");
        List<RefactoringOperation> mutated = mutation.apply(refactor);

        System.out.println("Mutated array ");
        if (mutated != null)
            for (RefactoringOperation refOper : mutated) {
                System.out.println("Random Refactor: " + refOper.toString());
            }
    }

    /**
     * Flips a bit in the given genome
     *
     * @return Number of mutated bits
     */

    @Override
    public List<RefactoringOperation> apply(List<RefactoringOperation> x) {
        List<RefactoringOperation> genome = (List<RefactoringOperation>) Clone.create(x);

        if (MetaphorCode.getClassesWithInheritance().isEmpty()) {
            if (MetaphorCode.getClassesWithFields().isEmpty()) {
                genome = getRefactoringWithOutInheritanceNoFields(genome);
            } else {
                genome = getRefactoringWithOutInheritance(genome);
            }

        } else {
            if (MetaphorCode.getClassesWithInheritanceAndFields().isEmpty()) {
                genome = getRefactoringWithInheritanceNoFields(genome);
            } else {
                genome = getRefactoringWithInheritance(genome);
            }
        }

        return genome;
    }

    private List<RefactoringOperation> getRefactoringWithInheritance(List<RefactoringOperation> genome) {
        try {
            double rate = 1.0 - ((bit_mutation_rate == 0.0) ? 1.0 / genome.size() : bit_mutation_rate);

            RandBool g = new RandBool(rate);
            RefactoringOperation refOper;

            IntUniform r = new IntUniform(Refactoring.values().length);
            RefactoringType refType = null;

            for (int i = 0; i < genome.size(); i++) {
                if (g.next()) {
                    switch (r.generate()) {
                        case 0:
                            refType = new ExtractClass(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang(), MetaphorCode.getBuilder());
                            break;
                        case 1:
                            refType = new MoveMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 2:
                            refType = new ReplaceMethodObject(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang(), MetaphorCode.getBuilder());
                            break;
                        case 3:
                            refType = new ReplaceDelegationInheritance(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 4:
                            refType = new MoveField(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                        case 5:
                            refType = new ExtractMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                        case 6:
                            refType = new InlineMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                        case 7:
                            refType = new ReplaceInheritanceDelegation(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 8:
                            refType = new PushDownMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 9:
                            refType = new PullUpMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang(), MetaphorCode.getBuilder());
                            break;
                        case 10:
                            refType = new PushDownField(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                        case 11:
                            refType = new PullUpField(MetaphorCode.getSysTypeDcls());
                            break;
                    }//END CASE

                    refOper = new RefactoringOperation(refType, genome.get(i).getParams(),
                            refType.getAcronym(), genome.get(i).getSubRefs(), genome.get(i).isFeasible(),
                            genome.get(i).getPenalty());
                    genome.set(i, refOper);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[Mutation]" + e.getMessage());
        }
        return genome;
    }

    private List<RefactoringOperation> getRefactoringWithInheritanceNoFields(List<RefactoringOperation> genome) {
        try {
            double rate = 1.0 - ((bit_mutation_rate == 0.0) ? 1.0 / genome.size() : bit_mutation_rate);

            RandBool g = new RandBool(rate);
            RefactoringOperation refOper;

            IntUniform r = new IntUniform(Refactoring.values().length - 4);
            RefactoringType refType = null;

            for (int i = 0; i < genome.size(); i++) {
                if (g.next()) {
                    switch (r.generate()) {
                        case 0:
                            refType = new MoveMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 1:
                            refType = new ReplaceMethodObject(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang(), MetaphorCode.getBuilder());
                            break;
                        case 2:
                            refType = new ReplaceDelegationInheritance(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 3:
                            refType = new ExtractMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                        case 4:
                            refType = new InlineMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                        case 5:
                            refType = new ReplaceInheritanceDelegation(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 6:
                            refType = new PushDownMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 7:
                            refType = new PullUpMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang(), MetaphorCode.getBuilder());
                            break;
                    }//END CASE

                    refOper = new RefactoringOperation(refType, genome.get(i).getParams(),
                            refType.getAcronym(), genome.get(i).getSubRefs(), genome.get(i).isFeasible(),
                            genome.get(i).getPenalty());
                    genome.set(i, refOper);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[Mutation]" + e.getMessage());
        }
        return genome;
    }

    private List<RefactoringOperation> getRefactoringWithOutInheritance(List<RefactoringOperation> genome) {
        try {
            double rate = 1.0 - ((bit_mutation_rate == 0.0) ? 1.0 / genome.size() : bit_mutation_rate);

            RandBool g = new RandBool(rate);
            RefactoringOperation refOper;

            IntUniform r = new IntUniform(Refactoring.values().length - 5);
            RefactoringType refType = null;

            for (int i = 0; i < genome.size(); i++) {
                if (g.next()) {
                    switch (r.generate()) {
                        case 0:
                            refType = new ExtractClass(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang(), MetaphorCode.getBuilder());
                            break;
                        case 1:
                            refType = new MoveMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 2:
                            refType = new ReplaceMethodObject(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang(), MetaphorCode.getBuilder());
                            break;
                        case 3:
                            refType = new ReplaceDelegationInheritance(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 4:
                            refType = new MoveField(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                        case 5:
                            refType = new ExtractMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                        case 6:
                            refType = new InlineMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                    }//END CASE

                    refOper = new RefactoringOperation(refType, genome.get(i).getParams(),
                            refType.getAcronym(), genome.get(i).getSubRefs(), genome.get(i).isFeasible(),
                            genome.get(i).getPenalty());
                    genome.set(i, refOper);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[Mutation]" + e.getMessage());
        }
        return genome;
    }

    private List<RefactoringOperation> getRefactoringWithOutInheritanceNoFields(List<RefactoringOperation> genome) {
        try {
            double rate = 1.0 - ((bit_mutation_rate == 0.0) ? 1.0 / genome.size() : bit_mutation_rate);

            RandBool g = new RandBool(rate);
            RefactoringOperation refOper;

            IntUniform r = new IntUniform(Refactoring.values().length - 7);
            RefactoringType refType = null;

            for (int i = 0; i < genome.size(); i++) {
                if (g.next()) {
                    switch (r.generate()) {
                        case 0:
                            refType = new MoveMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 1:
                            refType = new ReplaceMethodObject(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang(), MetaphorCode.getBuilder());
                            break;
                        case 2:
                            refType = new ReplaceDelegationInheritance(MetaphorCode.getSysTypeDcls(), MetaphorCode.getBuilder());
                            break;
                        case 3:
                            refType = new ExtractMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                        case 4:
                            refType = new InlineMethod(MetaphorCode.getSysTypeDcls(), MetaphorCode.getLang());
                            break;
                    }//END CASE

                    refOper = new RefactoringOperation(refType, genome.get(i).getParams(),
                            refType.getAcronym(), genome.get(i).getSubRefs(), genome.get(i).isFeasible(),
                            genome.get(i).getPenalty());
                    genome.set(i, refOper);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[Mutation]" + e.getMessage());
        }
        return genome;
    }

}
