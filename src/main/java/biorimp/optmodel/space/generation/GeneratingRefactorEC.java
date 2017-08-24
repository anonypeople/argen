/**
 *
 */
package biorimp.optmodel.space.generation;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import biorimp.optmodel.space.Refactoring;
import biorimp.optmodel.space.feasibility.InspectRefactor;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefactoring;
import unalcol.random.integer.IntUniform;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daavid
 */
public class GeneratingRefactorEC extends GeneratingRefactor {

	/* (non-Javadoc)
     * @see entity.MappingRefactor#mappingRefactor(java.lang.String, unalcol.types.collection.bitarray.BitArray, entity.MetaphorCode)
	 */

    protected Refactoring type = Refactoring.extractClass;

    @Override
    public OBSERVRefactoring generatingRefactor(ArrayList<Double> penalty) {
        // TODO Auto-generated method stub
        return mappingRefactorMFMM("TgtClassEC", penalty);
    }

    public OBSERVRefactoring mappingRefactorMFMM(String newClass, ArrayList<Double> penalty) {
        // TODO Auto-generated method stub
        boolean feasible;
        List<OBSERVRefactoring> subRefs = new ArrayList<OBSERVRefactoring>();
        List<OBSERVRefParam> paramsMF;
        List<OBSERVRefParam> paramsMM;
        TypeDeclaration sysType_src;
        IntUniform g = new IntUniform(MetaphorCode.getMapClass().size());

        do {
            feasible = true;
            paramsMF = new ArrayList<OBSERVRefParam>();
            paramsMM = new ArrayList<OBSERVRefParam>();

            //2. Creating the OBSERVRefParam for the src class
            sysType_src = MetaphorCode.getMapClass().get(g.generate());
            List<String> value_src = new ArrayList<String>();
            value_src.add(sysType_src.getQualifiedName());
            paramsMF.add(new OBSERVRefParam("src", value_src));
            paramsMM.add(new OBSERVRefParam("src", value_src));

            //3. Creating the OBSERVRefParam for the mtd class
            List<String> value_mtd = new ArrayList<String>();
            if (!MetaphorCode.getMethodsFromClass(sysType_src).isEmpty()) {
                IntUniform numMtdObs = new IntUniform(MetaphorCode.getMethodsFromClass(sysType_src).size());

                value_mtd.add((String) MetaphorCode.getMethodsFromClass(sysType_src).toArray()
                        [numMtdObs.generate()]);

                //Verification of method not constructor
                feasible = InspectRefactor.inspectMethodNotConstructor(value_mtd, sysType_src);

                if (feasible) {
                    //Override verification parents
                    feasible = InspectRefactor.inspectOverrideParents(value_mtd, sysType_src);
                    if (feasible) {
                        //Override verification children
                        feasible = InspectRefactor.inspectOverrideChildren(value_mtd, sysType_src);
                    }
                }

                paramsMM.add(new OBSERVRefParam("mtd", value_mtd));
            } else {
                feasible = false;
            }

            //4. Creating the OBSERVRefParam for the fld field
            List<String> value_fld = new ArrayList<String>();
            if (!MetaphorCode.getFieldsFromClass(sysType_src).isEmpty()) {
                IntUniform numFldObs = new IntUniform(MetaphorCode.getFieldsFromClass(sysType_src).size());

                String fldName = (String) MetaphorCode.getFieldsFromClass(sysType_src).toArray()
                        [numFldObs.generate()];
                value_fld.add(fldName);
                paramsMF.add(new OBSERVRefParam("fld", value_fld));
            } else {
                feasible = false;
            }

        } while (!feasible); //Generate Just feasible individuals

        //5. Creating the OBSERVRefParam for the tgt
        //This Target Class is not inside metaphor
        List<String> value_tgt = new ArrayList<String>();
        value_tgt.add(sysType_src.getPack() + newClass + "|N");
        paramsMF.add(new OBSERVRefParam("tgt", value_tgt));
        paramsMM.add(new OBSERVRefParam("tgt", value_tgt));
        //Fixme
        //MetaphorCode.addClasstoHash(sysType_src.getPack(), newClass + "|N");

        subRefs.add(new OBSERVRefactoring(Refactoring.moveField.name(), paramsMF, feasible, penalty));
        subRefs.add(new OBSERVRefactoring(Refactoring.moveMethod.name(), paramsMM, feasible, penalty));

        return new OBSERVRefactoring(type.name(), null, subRefs, feasible, penalty);
    }

    @Override
    public OBSERVRefactoring repairRefactor(RefactoringOperation ref) {
        // TODO Auto-generated method stub
        OBSERVRefactoring refRepair;

        String newClass = "TgtClassEC";

        boolean feasible;
        List<OBSERVRefactoring> subRefs = new ArrayList<OBSERVRefactoring>();
        List<OBSERVRefParam> paramsMF;
        List<OBSERVRefParam> paramsMM;
        TypeDeclaration sysType_src;
        IntUniform g = new IntUniform(MetaphorCode.getMapClass().size());

        feasible = true;
        paramsMF = new ArrayList<OBSERVRefParam>();
        paramsMM = new ArrayList<OBSERVRefParam>();

        //1. Creating the OBSERVRefParam for the src class
        if (ref.getSubRefs() != null) {
            if (!ref.getSubRefs().get(0).getParams().get("src").isEmpty()) {

                sysType_src = (TypeDeclaration) ref.getSubRefs().get(0).getParams().get("src").get(0).getCodeObj();
            } else {
                if (!ref.getParams().get("src").isEmpty())
                    sysType_src = (TypeDeclaration) ref.getParams().get("src").get(0).getCodeObj();
                else
                    sysType_src = MetaphorCode.getMapClass().get(g.generate());
            }

        } else {
            sysType_src = MetaphorCode.getMapClass().get(g.generate());
        }

        List<String> value_src = new ArrayList<String>();
        value_src.add(sysType_src.getQualifiedName());
        paramsMF.add(new OBSERVRefParam("src", value_src));
        paramsMM.add(new OBSERVRefParam("src", value_src));


        //2. Creating the OBSERVRefParam for the mtd class
        List<String> value_mtd = new ArrayList<String>();
        if (!MetaphorCode.getMethodsFromClass(sysType_src).isEmpty()) {
            IntUniform numMtdObs = new IntUniform(MetaphorCode.getMethodsFromClass(sysType_src).size());

            value_mtd.add((String) MetaphorCode.getMethodsFromClass(sysType_src).toArray()
                    [numMtdObs.generate()]);

            //verification of method not constructor
            if (value_mtd.get(0).equals(sysType_src.getName()))
                feasible = false;

            if (feasible) {
                //Override verification parents
                feasible = InspectRefactor.inspectOverrideParents(value_mtd, sysType_src);
                if (feasible) {
                    //Override verification children
                    feasible = InspectRefactor.inspectOverrideChildren(value_mtd, sysType_src);
                }
            }


            paramsMM.add(new OBSERVRefParam("mtd", value_mtd));
        } else {
            feasible = false;
        }

        if (feasible) {
            //3. Creating the OBSERVRefParam for the fld field
            List<String> value_fld = new ArrayList<String>();
            if (!MetaphorCode.getFieldsFromClass(sysType_src).isEmpty()) {
                IntUniform numFldObs = new IntUniform(MetaphorCode.getFieldsFromClass(sysType_src).size());

                String fldName = (String) MetaphorCode.getFieldsFromClass(sysType_src).toArray()
                        [numFldObs.generate()];
                value_fld.add(fldName);
                paramsMF.add(new OBSERVRefParam("fld", value_fld));
            } else {
                feasible = false;
            }
        }

        if (!feasible) {
            //Penalty
            ref.getPenalty().add(penaltyReGeneration);
            refRepair = generatingRefactor(ref.getPenalty());
        } else {
            //Penalty
            ref.getPenalty().add(penaltyRepair);
            //4. Creating the OBSERVRefParam for the tgt
            //This Target Class is not inside metaphor
            List<String> value_tgt = new ArrayList<String>();
            value_tgt.add(sysType_src.getPack() + newClass + "|N");
            paramsMF.add(new OBSERVRefParam("tgt", value_tgt));
            paramsMM.add(new OBSERVRefParam("tgt", value_tgt));
            //Fixme
            //MetaphorCode.addClasstoHash(sysType_src.getPack(), newClass + "|N");

            subRefs.add(new OBSERVRefactoring(Refactoring.moveField.name(), paramsMF, feasible, ref.getPenalty()));
            subRefs.add(new OBSERVRefactoring(Refactoring.moveMethod.name(), paramsMM, feasible, ref.getPenalty()));

            refRepair = new OBSERVRefactoring(type.name(), null, subRefs, feasible, ref.getPenalty());
        }


        return refRepair;
    }

}
