package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import edu.wayne.cs.severe.redress2.controller.metric.*;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em.*;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

import java.util.HashMap;
import java.util.List;

public class ExtractMethod extends RefactoringType {

    public ExtractMethod(List<TypeDeclaration> sysTypeDcls, ProgLang lang) {
        super(sysTypeDcls);
        formulas.put(new LOCMetric().getMetricAcronym(),
                new LOCExtractMethodPF(lang));
        formulas.put(new NOMMetric().getMetricAcronym(),
                new NOMExtractMethodPF());
        formulas.put(new RFCMetric().getMetricAcronym(),
                new RFCExtractMethodPF());
        formulas.put(new LCOM5Metric().getMetricAcronym(),
                new LCOM5ExtractMethodPF());
        formulas.put(new LCOM2Metric().getMetricAcronym(),
                new LCOM2ExtractMethodPF());
        formulas.put(new CYCLOMetric().getMetricAcronym(),
                new CYCLOExtractMethodPF());
    }

    @Override
    public String getAcronym() {
        return "EM";
    }

    @Override
    public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
            List<JSONRefParam> jsonParams) throws RefactoringException {

        String srcKey = "src";
        String mtdKey = "mtd";

        HashMap<String, JSONRefParam> idxParams = RefactoringUtils
                .validateJsonParams(jsonParams, 2, new String[]{srcKey,
                        mtdKey}, new int[]{1, 1});

        HashMap<String, List<RefactoringParameter>> params = new HashMap<String, List<RefactoringParameter>>();
        JSONRefParam jsonParam = idxParams.get(srcKey);
        List<RefactoringParameter> refParams = RefactoringUtils
                .getOpersCodeObject(jsonParam, sysTypeDcls,
                        TypeDeclaration.class);
        params.put(srcKey, refParams);

        jsonParam = idxParams.get(mtdKey);
        refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
                MethodDeclaration.class);
        params.put(mtdKey, refParams);

        return params;
    }

    //danaderp vers 1000
    @Override
    public HashMap<String, List<RefactoringParameter>> getOBSERVRefactoringParams(List<OBSERVRefParam> jsonParams)
            throws RefactoringException {
        // TODO Auto-generated method stub
        String srcKey = "src";
        String mtdKey = "mtd";

        HashMap<String, OBSERVRefParam> idxParams = RefactoringUtils
                .validateObservParams(jsonParams, 2, new String[]{srcKey,
                        mtdKey}, new int[]{1, 1});

        HashMap<String, List<RefactoringParameter>> params = new HashMap<String, List<RefactoringParameter>>();
        OBSERVRefParam jsonParam = idxParams.get(srcKey);
        List<RefactoringParameter> refParams = RefactoringUtils
                .getOpersCodeObject(jsonParam, sysTypeDcls,
                        TypeDeclaration.class);
        params.put(srcKey, refParams);

        jsonParam = idxParams.get(mtdKey);
        refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
                MethodDeclaration.class);
        params.put(mtdKey, refParams);

        return params;
    }

}
