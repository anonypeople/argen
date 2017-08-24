package unalcol.evolution;

import unalcol.optimization.operators.Operator;
import unalcol.optimization.solution.Solution;
import unalcol.types.collection.vector.*;

import unalcol.reflect.service.*;

/**
 * <p>Title: GrowingFunction</p>
 * <p>Description: An individual coding/encoding function</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class GrowingFunction<G,P> implements Service{
  /**
   * Default constructor
   */
  public GrowingFunction() { }

  public Object owner(){ return Object.class; }

  /**
   * Generates a thing from the given genome
   * @param genome Genome of the thing to be expressed
   * @return A thing expressed from the genome
   */
  public P get(G genome) { return (P)genome; }

  /**
   * Generates a genome from the given thing
   * @param thing A thing expressed from the genome
   * @return Genome of the thing
   */
  public G set(P thing) { return (G)thing; }

  /**
   * Generates a population of individuals from the given collection of genomes
   * @param genomes Genomes used for building the population of individuals
   * @return Population of individuals built upon the given genomes
   */
  public Vector<Solution<P>> build( Vector<G> genomes ){
      G genome;
      Vector<Solution<P>> v = new Vector();
      int n = genomes.size();
      for( int i=0; i<n; i++ ){
          genome = genomes.get(i);
          P thing = get(genome);
          if( thing != null ){
            v.add(new Individual(genome, thing));
          }else{
            v.add(new Individual(genome, this));
          }
      }
      return v;
  }

  /**
   * Obtains the first <i>n</i> candidate solutions genomes of the population
   * @param n Number of candidate solutions genomes to obtain
   * @param population Population used for obtaining the first <i>n</i> genomes
   * @return The first <i>n</i> candidate solutions genomes of the population
   */
  public Vector<G> genomes( int n, Vector<Solution<P>> population ){
      Vector<G> genomes = new Vector();
      for( int i=0; i<n; i++ ){
          genomes.add( ((Individual<G,P>)population.get(i)).genome() );
      }
      return genomes;
  }

  /**
   * Applies the genetic operator over the first <i>n</i> candidate solutions genomes
   * of a given population. Here, <i>n</i> is the number of genomes required by
   * the genetic operator
   * @param operator Genetic Operator to be applied on the first <i>n</i> candidate
   * solutions genomes of a given population. Here, <i>n</i> is the number of
   * genomes required by the genetic operator
   * @param population Source population
   * @return A collection of candidate solutions generated by the genetic operator
   */
  public Vector<Solution<P>> apply( Operator<G> operator, Vector<Solution<P>> population ){
      return build( operator.apply( genomes( operator.getArity(), population ) ) );
  }
}