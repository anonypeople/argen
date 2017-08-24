/**
 *
 */
package biorimp.optmodel.mappings;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import biorimp.optmodel.mappings.quantum.QubitRefactor;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefactoring;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daavid
 */


public class MappingRefactorEM extends MappingRefactor {

	/* (non-Javadoc)
     * @see entity.MappingRefactor#mappingRefactor(java.lang.String, unalcol.types.collection.bitarray.BitArray, entity.MetaphorCode)
	 */

    protected Refactoring type = Refactoring.extractMethod;

    @Override
    public OBSERVRefactoring mappingRefactor(QubitRefactor genome) {
        // TODO Auto-generated method stub
        boolean feasible = true;

        List<OBSERVRefParam> params = new ArrayList<OBSERVRefParam>();


        //Creating the OBSERVRefParam for the src class
        int numSrcObs = genome.getNumberGenome(genome.getGenSRC());
        TypeDeclaration sysType_src = MetaphorCode.getMapClass().get(numSrcObs %
                MetaphorCode.getMapClass().size());
        List<String> value_src = new ArrayList<String>();
        value_src.add(sysType_src.getQualifiedName());
        params.add(new OBSERVRefParam("src", value_src));

        //Creating the OBSERVRefParam for the mtd class
        List<String> value_mtd = new ArrayList<String>();
        if (!MetaphorCode.getMethodsFromClass(sysType_src).isEmpty()) {
            int numMtdObs = genome.getNumberGenome(genome.getGenMTD());
            value_mtd.add((String) MetaphorCode.getMethodsFromClass(sysType_src).toArray()[numMtdObs
                    % MetaphorCode.getMethodsFromClass(sysType_src).size()]);

            //verification of method not constructor
            if (value_mtd.get(0).equals(sysType_src.getName()))
                feasible = false;

            params.add(new OBSERVRefParam("mtd", value_mtd));
        } else {
            value_mtd.add("");
            params.add(new OBSERVRefParam("mtd", value_mtd));
            feasible = false;
        }
        return new OBSERVRefactoring(type.name(), params, feasible, new ArrayList<Double>());
    }

    /* (non-Javadoc)
     * @see entity.MappingRefactor#mappingParams()
     */
    @Override
    public List<OBSERVRefParam> mappingParams() {
        // TODO Auto-generated method stub
        return null;
    }

}
