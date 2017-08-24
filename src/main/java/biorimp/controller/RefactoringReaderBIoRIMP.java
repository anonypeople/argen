package biorimp.controller;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefactoring;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefactorings;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.*;
import edu.wayne.cs.severe.redress2.exception.ReadException;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author danaderp
 * @version 1.0
 * @created 09.08.015 17:27:28
 */
public class RefactoringReaderBIoRIMP {

    private static Logger LOGGER = LoggerFactory
            .getLogger(RefactoringReaderBIoRIMP.class);
    private HashMap<String, RefactoringType> refMappings = null;

    public RefactoringReaderBIoRIMP() {

        List<TypeDeclaration> sysTypeDcls = MetaphorCode.getSysTypeDcls();
        ProgLang lang = MetaphorCode.getLang();
        HierarchyBuilder builder = MetaphorCode.getBuilder();

        refMappings = new HashMap<String, RefactoringType>();

        String[] types = {"pullUpField", "moveMethod", "replaceMethodObject",
                "moveField", "extractClass", "replaceDelegationInheritance",
                "extractMethod", "pushDownMethod",
                "replaceInheritanceDelegation", "inlineMethod", "pullUpMethod",
                "pushDownField"};
        RefactoringType[] typeObjs = {new PullUpField(sysTypeDcls),
                new MoveMethod(sysTypeDcls, builder),
                new ReplaceMethodObject(sysTypeDcls, lang, builder),
                new MoveField(sysTypeDcls, lang),
                new ExtractClass(sysTypeDcls, lang, builder),
                new ReplaceDelegationInheritance(sysTypeDcls, builder),
                new ExtractMethod(sysTypeDcls, lang),
                new PushDownMethod(sysTypeDcls, builder),
                new ReplaceInheritanceDelegation(sysTypeDcls, builder),
                new InlineMethod(sysTypeDcls, lang),
                new PullUpMethod(sysTypeDcls, lang, builder),
                new PushDownField(sysTypeDcls, lang)};

        for (int i = 0; i < types.length; i++) {
            String type = types[i];
            refMappings.put(type, typeObjs[i]);
        }
    }

    /**
     * @param jsonParams
     * @throws FileNotFoundException
     * @throws JsonIOException
     * @throws JsonSyntaxException
     */
    public List<RefactoringOperation> getRefactOperations(OBSERVRefactorings jsonParams)
            throws ReadException {

        try {

            List<OBSERVRefactoring> refs = jsonParams.getRefactorings();
            List<RefactoringOperation> opers = new ArrayList<RefactoringOperation>();

            int id = 1;
            for (OBSERVRefactoring ref : refs) {
                RefactoringOperation oper = getRefactoringOper(ref,
                        String.valueOf((id++)));
                if (oper != null) {
                    opers.add(oper);
                }
            }

            return opers;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            ReadException ex = new ReadException(e.getMessage());
            ExceptionUtils.addStackTrace(e, ex);
            throw ex;
        }
    }

    private RefactoringOperation getRefactoringOper(OBSERVRefactoring ref,
                                                    String id) throws RefactoringException {
        RefactoringType refType = refMappings.get(ref.getType());

        if (refType == null) {
            throw new RefactoringException("The refactoring \"" + ref.getType()
                    + "\" is not supported");
        }

        HashMap<String, List<RefactoringParameter>> params = refType
                .getOBSERVRefactoringParams(ref.getParams());
        List<RefactoringOperation> subRefs = getSubRefs(ref.getSubRefs(), id);

        //danaderp 1001 added feasibility and penalty in the constructor
        return new RefactoringOperation(refType, params,
                refType.getAcronym() + "-" + id, subRefs, ref.isFeasible(), ref.getPenalty());
    }

    private List<RefactoringOperation> getSubRefs(
            List<OBSERVRefactoring> jsonSubRefs, String id)
            throws RefactoringException {

        if (jsonSubRefs == null) {
            return null;
        }

        List<RefactoringOperation> subRefs = new ArrayList<RefactoringOperation>();
        int i = 0;
        for (OBSERVRefactoring jSubRef : jsonSubRefs) {
            subRefs.add(getRefactoringOper(jSubRef, id + "-" + (i++)));
        }

        return subRefs;
    }

}// end RefactoringReader