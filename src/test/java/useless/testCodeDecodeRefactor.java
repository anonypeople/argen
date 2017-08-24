package useless;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import biorimp.optmodel.mappings.quantum.CodeDecodeRefactorList;
import biorimp.optmodel.mappings.quantum.QubitRefactor;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.main.MainPredFormulasBIoRIPM;
import unalcol.search.multilevel.CodeDecodeMap;

import java.util.ArrayList;
import java.util.List;

public class testCodeDecodeRefactor {

    public static void main(String[] argss) {
        // TODO Auto-generated method stub
        //Getting the Metaphor
        String userPath = System.getProperty("user.dir");
        String[] args = {"-l", "Java", "-p", userPath + "\\test_data\\code\\optimization\\src", "-s", "java/optmodel/fitness      "};


        MainPredFormulasBIoRIPM init = new MainPredFormulasBIoRIPM();
        init.main(args);
        MetaphorCode metaphor = new MetaphorCode(init, 0);
        //metaphor.setSysTypeDcls(init.getSysTypeDcls());
        //metaphor.setBuilder(init.getBuilder());
        //metaphor.setLang(init.getLang());
        //Creating the individual
        List<QubitRefactor> genome = new ArrayList<QubitRefactor>();
        List<RefactoringOperation> phe = new ArrayList<RefactoringOperation>();
        List<RefactoringOperation> pheCod = new ArrayList<RefactoringOperation>();
        List<QubitRefactor> genomeCod = new ArrayList<QubitRefactor>();

        int QUBITTAM = 4;
        for (int i = 0; i < 10; i++) {
            genome.add(new QubitRefactor(true, QUBITTAM));
            System.out.println(i + " " +
                    genome.get(i).getGenObservation().toString());
        }

        CodeDecodeMap<List<QubitRefactor>, List<RefactoringOperation>> map
                = new CodeDecodeRefactorList();
        //Processing Decode
        phe = map.decode(genome);

        for (int i = 0; i < phe.size(); i++) {
            System.out.println("Decoding: " + i + " " + phe.get(i).toString());
        }

        //Processing Encode
        genomeCod = map.code(phe);

        for (int i = 0; i < genomeCod.size(); i++) {
            System.out.println("Coding[new]: " + i + " " + genomeCod.get(i).getGenObservation().toString());
            System.out.println("Coding[old]: " + i + " " + genome.get(i).getGenObservation().toString());
        }

        //Processing REDecode
        pheCod = map.decode(genomeCod);

        for (int i = 0; i < phe.size(); i++) {
            System.out.println("Decoding[new]: " + i + " " + pheCod.get(i).toString());
            System.out.println("Decoding[old]: " + i + " " + phe.get(i).toString());
        }
    }

}
