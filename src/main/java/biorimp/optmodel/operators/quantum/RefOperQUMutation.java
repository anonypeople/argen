package biorimp.optmodel.operators.quantum;

import biorimp.optmodel.mappings.quantum.QubitRefactor;
import unalcol.clone.Clone;
import unalcol.random.util.RandBool;
import unalcol.search.space.ArityOne;

/**
 * <p>Title: Mutation</p>
 * <p>Description: The simple bit mutation operator</p>
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author danaderp
 * @version 1.0
 */

public class RefOperQUMutation extends ArityOne<QubitRefactor> {
    /**
     * Probability of mutating one single bit
     */
    protected double bit_mutation_rate = 0.0;

    /**
     * Constructor: Creates a mutation with a mutation probability depending on the size of the genome
     */
    public RefOperQUMutation() {
    }

    /**
     * Constructor: Creates a mutation with the given mutation rate
     *
     * @param bit_mutation_rate Probability of mutating each single bit
     */
    public RefOperQUMutation(double bit_mutation_rate) {
        this.bit_mutation_rate = bit_mutation_rate;
    }

    /**
     * Testing function
     */
    public static void main(String[] argv) {
        System.out.println("*** Generating a genome of 21 genes randomly ***");
        QubitRefactor genome = new QubitRefactor(true, 4); //for 4tam of qubits
        System.out.println(genome.getGenObservation().toString());

        RefOperQUMutation mutation = new RefOperQUMutation(0.05);

        System.out.println("*** Applying the mutation ***");
        QubitRefactor mutated = mutation.apply(genome);
        System.out.println("Mutated array ");
        System.out.println(mutated.getGenObservation().toString());
    }

    /**
     * Flips a bit in the given genome
     *
     * @param gen Genome to be modified
     * @return Number of mutated bits
     */
    @Override
    public QubitRefactor apply(QubitRefactor gen) {
        try {
            QubitRefactor genome = (QubitRefactor) Clone.create(gen);
            double rate = 1.0 - ((bit_mutation_rate == 0.0) ? 1.0 / genome.size() : bit_mutation_rate);
            RandBool g = new RandBool(rate);
            for (int i = 0; i < genome.size(); i++) {
                if (g.next()) {
                    genome.not(i);
                }
            }
            return genome;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[Mutation]" + e.getMessage());
        }
        return null;
    }


}
