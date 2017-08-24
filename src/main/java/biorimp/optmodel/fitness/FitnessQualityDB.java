/**
 *
 */
package biorimp.optmodel.fitness;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import biorimp.storage.entities.RefKey;
import biorimp.storage.entities.Register;
import biorimp.storage.repositories.RegisterRepository;
import edu.wayne.cs.severe.redress2.controller.MetricCalculator;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.exception.CompilUnitException;
import edu.wayne.cs.severe.redress2.exception.ReadException;
import edu.wayne.cs.severe.redress2.exception.WritingException;
import unalcol.clone.Clone;
import unalcol.optimization.OptimizationFunction;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

//import finaltest.MainOptimization;
//import test.MainHAEATestBDFi;

/**
 * @author dnader
 */
@Deprecated
public class FitnessQualityDB extends OptimizationFunction<List<RefactoringOperation>> {

    //MetaphorCode metaphor;
    String file;
    //Field for memoization
    LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> predictMetricsMemorizar = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>();
    LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> predictMetricsRecordar = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>();

    public FitnessQualityDB(String file) {
        this.file = file;
    }

    public void memorizar(RefactoringOperation operRef) {

        // Verificaci�n de llaves
        String src = "";
        String tgt = "";
        String fld = "-1", mtd;
        String acronym = operRef.getRefType().getAcronym();
        if (operRef.getParams() != null) {
            // si es un extract class memoriza sub-refs
            if (acronym.equals("EC")) {
                // 1. Extracting src from subrefs
                if (operRef.getSubRefs().get(0).getParams().get("src") != null) {
                    if (!operRef.getSubRefs().get(0).getParams().get("src").isEmpty()) {// valida
                        // si
                        // es
                        // vac�o
                        for (RefactoringParameter obj : operRef.getSubRefs().get(0).getParams().get("src")) {
                            src += ((TypeDeclaration) obj.getCodeObj()).getId() + ",";
                        }
                        src = src.substring(0, src.length() - 1);

                    }
                }

                // 2. Extracting fld from subrefs
                if (operRef.getSubRefs().get(0).getParams().get("fld") != null) {
                    if (!operRef.getSubRefs().get(0).getParams().get("fld").isEmpty()) // valida
                        // si
                        // es
                        // vac�o
                        fld = ((AttributeDeclaration) operRef.getSubRefs().get(0).getParams().get("fld").get(0)
                                .getCodeObj()).getObjName();
                    else
                        fld = "-1";
                } else {
                    fld = "-1";
                }
                // 3. Extracting mtd from subrefs
                if (operRef.getSubRefs().get(1).getParams().get("mtd") != null) {
                    if (!operRef.getSubRefs().get(1).getParams().get("mtd").isEmpty())
                        mtd = ((MethodDeclaration) operRef.getSubRefs().get(1).getParams().get("mtd").get(0)
                                .getCodeObj()).getObjName();
                    else
                        mtd = "-1";
                } else {
                    mtd = "-1";
                }
            } else {
                if (acronym.equals("RMMO")) {
                    // 1. Extracting src from subrefs
                    if (operRef.getParams().get("src") != null) {
                        if (!operRef.getParams().get("src").isEmpty()) {// valida
                            // si es
                            // vac�o
                            for (RefactoringParameter obj : operRef.getParams().get("src")) {
                                src += ((TypeDeclaration) obj.getCodeObj()).getId() + ",";
                            }
                            src = src.substring(0, src.length() - 1);

                        }
                    }

                    // 2. Extracting mtd from subrefs
                    if (operRef.getParams().get("mtd") != null) {
                        if (!operRef.getParams().get("mtd").isEmpty())
                            mtd = ((MethodDeclaration) operRef.getParams().get("mtd").get(0).getCodeObj()).getObjName();
                        else
                            mtd = "-1";
                    } else {
                        mtd = "-1";
                    }
                } else {
                    if (operRef.getParams().get("src") != null) {
                        if (!operRef.getParams().get("src").isEmpty()) {// valida
                            // si es
                            // vac�o
                            for (RefactoringParameter obj : operRef.getParams().get("src")) {
                                src += ((TypeDeclaration) obj.getCodeObj()).getId() + ",";
                            }
                            src = src.substring(0, src.length() - 1);

                        }
                    }
                    if (operRef.getParams().get("tgt") != null) {
                        if (!operRef.getParams().get("tgt").isEmpty()) {// valida
                            // si es
                            // vac�o
                            for (RefactoringParameter obj : operRef.getParams().get("tgt")) {
                                tgt += ((TypeDeclaration) obj.getCodeObj()).getId() + ",";
                            }
                            tgt = tgt.substring(0, tgt.length() - 1);

                        }
                    }

                    if (operRef.getParams().get("fld") != null) {
                        if (!operRef.getParams().get("fld").isEmpty()) // valida
                            // si es
                            // vac�o
                            fld = ((AttributeDeclaration) operRef.getParams().get("fld").get(0).getCodeObj())
                                    .getObjName();
                        else
                            fld = "-1";
                    } else {
                        fld = "-1";
                    }

                    if (operRef.getParams().get("mtd") != null) {
                        if (!operRef.getParams().get("mtd").isEmpty())
                            mtd = ((MethodDeclaration) operRef.getParams().get("mtd").get(0).getCodeObj()).getObjName();
                        else
                            mtd = "-1";
                    } else {
                        mtd = "-1";
                    }
                }
            }
            // Termina Extracci�n

            // Se escribe en el fichero la predicci�n
            for (Entry<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> ref : predictMetricsMemorizar
                    .entrySet()) {
                for (Entry<String, LinkedHashMap<String, Double>> clase : ref.getValue().entrySet()) {
                    // Add metrics per class to SUA_metric
                    for (Entry<String, Double> metric : clase.getValue().entrySet()) {

                        String id_ref = ref.getKey();
                        if (id_ref.contains("-"))
                            id_ref = ref.getKey().substring(0, ref.getKey().indexOf("-"));
                        if (!id_ref.equals(operRef.getRefType().getAcronym()))
                            continue;
                        double val = metric.getValue();

                        Register register = new Register(id_ref, metric.getKey(), val, src, tgt, fld, mtd,
                                clase.getKey());
                        RegisterRepository repo = RegisterRepository.getInstance();
                        repo.insertRegister(register);

                    }
                }
            }//end for

        }//end params verification
        else {
            if (acronym.equals("EC")) {
                // 1. Extracting src from subrefs
                if (operRef.getSubRefs().get(0).getParams().get("src") != null) {
                    if (!operRef.getSubRefs().get(0).getParams().get("src").isEmpty()) {// valida
                        // si
                        // es
                        // vac�o
                        for (RefactoringParameter obj : operRef.getSubRefs().get(0).getParams().get("src")) {
                            src += ((TypeDeclaration) obj.getCodeObj()).getId() + ",";
                        }
                        src = src.substring(0, src.length() - 1);

                    }
                }

                // 2. Extracting fld from subrefs
                if (operRef.getSubRefs().get(0).getParams().get("fld") != null) {
                    if (!operRef.getSubRefs().get(0).getParams().get("fld").isEmpty()) // valida
                        // si
                        // es
                        // vac�o
                        fld = ((AttributeDeclaration) operRef.getSubRefs().get(0).getParams().get("fld").get(0)
                                .getCodeObj()).getObjName();
                    else
                        fld = "-1";
                } else {
                    fld = "-1";
                }
                // 3. Extracting mtd from subrefs
                if (operRef.getSubRefs().get(1).getParams().get("mtd") != null) {
                    if (!operRef.getSubRefs().get(1).getParams().get("mtd").isEmpty())
                        mtd = ((MethodDeclaration) operRef.getSubRefs().get(1).getParams().get("mtd").get(0)
                                .getCodeObj()).getObjName();
                    else
                        mtd = "-1";
                } else {
                    mtd = "-1";
                }

                // Se escribe en el fichero la predicci�n
                for (Entry<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> ref : predictMetricsMemorizar
                        .entrySet()) {
                    for (Entry<String, LinkedHashMap<String, Double>> clase : ref.getValue().entrySet()) {
                        // Add metrics per class to SUA_metric
                        for (Entry<String, Double> metric : clase.getValue().entrySet()) {

                            String id_ref = ref.getKey();
                            if (id_ref.contains("-"))
                                id_ref = ref.getKey().substring(0, ref.getKey().indexOf("-"));
                            if (!id_ref.equals(operRef.getRefType().getAcronym()))
                                continue;
                            double val = metric.getValue();

                            Register register = new Register(id_ref, metric.getKey(), val, src, tgt, fld, mtd,
                                    clase.getKey());
                            RegisterRepository repo = RegisterRepository.getInstance();
                            repo.insertRegister(register);
                        }
                    }
                } // end for
            } // end EC extraction
        }

        predictMetricsMemorizar = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>();
    }
    // End memoization

    public boolean recordar(RefactoringOperation operRef) {
        boolean bandera = true;

        String clase = "";
        //Verificaci�n de llaves
        String src = "";
        String tgt = "";
        String fld, mtd;
        String acronym = operRef.getRefType().getAcronym();
        if (operRef.getParams() != null) {
            // si es un extract class memoriza sub-refs
            if (acronym.equals("EC")) {
                //1.Extracting src from subrefs
                if (operRef.getSubRefs().get(0).getParams().get("src") != null) {
                    if (!operRef.getSubRefs().get(0).getParams().get("src").isEmpty()) { // valida
                        // si es
                        // vac�o
                        for (RefactoringParameter obj : operRef.getSubRefs().get(0).getParams().get("src")) {
                            src += ((TypeDeclaration) obj.getCodeObj()).getId() + ",";// 45,67
                        }
                        src = src.substring(0, src.length() - 1);
                    }
                }
                //2.Extracting fld from subrefs
                if (operRef.getSubRefs().get(0).getParams().get("fld") != null) {
                    if (!operRef.getSubRefs().get(0).getParams().get("fld").isEmpty())
                        fld = ((AttributeDeclaration) operRef.getSubRefs().get(0).getParams().get("fld").get(0).getCodeObj()).getObjName();
                    else
                        fld = "-1";
                } else {
                    fld = "-1";
                }

                //3.Extracting mtd from subrefs
                if (operRef.getSubRefs().get(1).getParams().get("mtd") != null) {
                    if (!operRef.getSubRefs().get(1).getParams().get("mtd").isEmpty())
                        mtd = ((MethodDeclaration) operRef.getSubRefs().get(1).getParams().get("mtd").get(0).getCodeObj()).getObjName();
                    else
                        mtd = "-1";
                } else {
                    mtd = "-1";
                }

            } else {

                if (operRef.getParams().get("src") != null) {
                    if (!operRef.getParams().get("src").isEmpty()) { // valida
                        // si es
                        // vac�o
                        for (RefactoringParameter obj : operRef.getParams().get("src")) {
                            src += ((TypeDeclaration) obj.getCodeObj()).getId() + ",";// 45,67
                        }
                        src = src.substring(0, src.length() - 1);
                    }
                }

                if (operRef.getParams().get("tgt") != null) {
                    if (!operRef.getParams().get("tgt").isEmpty()) {
                        for (RefactoringParameter obj : operRef.getParams().get("tgt")) {
                            tgt += ((TypeDeclaration) obj.getCodeObj()).getId() + ",";
                        }
                        tgt = tgt.substring(0, tgt.length() - 1);
                    }
                }

                if (operRef.getParams().get("fld") != null) {
                    if (!operRef.getParams().get("fld").isEmpty())
                        fld = ((AttributeDeclaration) operRef.getParams().get("fld").get(0).getCodeObj()).getObjName();
                    else
                        fld = "-1";
                } else {
                    fld = "-1";
                }

                if (operRef.getParams().get("mtd") != null) {
                    if (!operRef.getParams().get("mtd").isEmpty())
                        mtd = ((MethodDeclaration) operRef.getParams().get("mtd").get(0).getCodeObj()).getObjName();
                    else
                        mtd = "-1";
                } else {
                    mtd = "-1";
                }
            }

            //DataBase vs Caching Process
            RefKey refKey = null;

            if (acronym.equals("EM") || acronym.equals("IM") || acronym.equals("RMMO")) {//-> Only matters src + mtd
                refKey = new RefKey(acronym, src, "", mtd, "");
                //listMetric = repo.getRegistersByClass(acronym, src, "", mtd, "");
            } else if (acronym.equals("MF") || acronym.equals("PDF") || acronym.equals("PUF")) {//->Only matters src+tgt+fld
                refKey = new RefKey(acronym, src, tgt, "", fld);
                //listMetric = repo.getRegistersByClass(acronym, src, tgt, "", fld);
            } else if (acronym.equals("MM") || acronym.equals("PDM") || acronym.equals("PUM")) {//->Only matters src+mtd+tgt
                refKey = new RefKey(acronym, src, tgt, mtd, "");
                //listMetric = repo.getRegistersByClass(acronym, src, tgt, mtd, "");
            } else if (acronym.equals("RDI") || acronym.equals("RID")) {//->Only matters src+tgt
                refKey = new RefKey(acronym, src, tgt, "", "");
                //listMetric = repo.getRegistersByClass(acronym, src, tgt, "", "");
            } else if (acronym.equals("EC")) {//->Only matters src+fld+mtd
                refKey = new RefKey(acronym, src, "", mtd, fld);
                //listMetric = repo.getRegistersByClass(acronym, src, "", mtd, fld);
            }
            /**
             * Have to decide between cache memory and database for loading
             */
            List<Register> listMetric = new ArrayList<>();

            try {
                listMetric = MetaphorCode.RefactoringCache().get(refKey);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //listMetric = repo.getRegistersByClass(refKey);

            //End catching vs database


            LinkedHashMap<String, Double> metricList = new LinkedHashMap<String, Double>();
            LinkedHashMap<String, LinkedHashMap<String, Double>> clasesList = new
                    LinkedHashMap<String, LinkedHashMap<String, Double>>();
            //clase = ((TypeDeclaration) operRef.getParams().get("src").get(0).getCodeObj()).getQualifiedName();

            bandera = !listMetric.isEmpty();
            if (bandera) {

                clase = listMetric.get(0).getClasss();
                for (Register reg : listMetric) {
                    if (!clase.equals(reg.getClasss())) {
                        clasesList.put(clase, metricList);
                        clase = reg.getClasss();
                        metricList = new LinkedHashMap<String, Double>();
                    }
                    metricList.put(reg.getMetric(), reg.getValue());

                }
                clasesList.put(clase, metricList);
                predictMetricsRecordar.put(operRef.getRefId(), clasesList);
            }


        } else { // if no params, no recall unless EC
            if (acronym.equals("EC")) {
                // 1.Extracting src from subrefs
                if (operRef.getSubRefs().get(0).getParams().get("src") != null) {
                    if (!operRef.getSubRefs().get(0).getParams().get("src").isEmpty()) { // valida
                        // si es
                        // vac�o
                        for (RefactoringParameter obj : operRef.getSubRefs().get(0).getParams().get("src")) {
                            src += ((TypeDeclaration) obj.getCodeObj()).getId() + ",";// 45,67
                        }
                        src = src.substring(0, src.length() - 1);
                    }
                }
                // 2.Extracting fld from subrefs
                if (operRef.getSubRefs().get(0).getParams().get("fld") != null) {
                    if (!operRef.getSubRefs().get(0).getParams().get("fld").isEmpty())
                        fld = ((AttributeDeclaration) operRef.getSubRefs().get(0).getParams().get("fld").get(0)
                                .getCodeObj()).getObjName();
                    else
                        fld = "-1";
                } else {
                    fld = "-1";
                }

                // 3.Extracting mtd from subrefs
                if (operRef.getSubRefs().get(1).getParams().get("mtd") != null) {
                    if (!operRef.getSubRefs().get(1).getParams().get("mtd").isEmpty())
                        mtd = ((MethodDeclaration) operRef.getSubRefs().get(1).getParams().get("mtd").get(0)
                                .getCodeObj()).getObjName();
                    else
                        mtd = "-1";
                } else {
                    mtd = "-1";
                }

                //RegisterRepository repo = new RegisterRepository();
                List<Register> listMetric = new ArrayList<>();
                RefKey refKey = new RefKey(acronym, src, "", mtd, fld);
                //listMetric = repo.getRegistersByClass(acronym, src, "", mtd, fld);
                /**
                 * Have to decide between cache memory and database for loading
                 */

                try {
                    listMetric = MetaphorCode.RefactoringCache().get(refKey);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                LinkedHashMap<String, Double> metricList = new LinkedHashMap<String, Double>();
                LinkedHashMap<String, LinkedHashMap<String, Double>> clasesList = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
                // clase = ((TypeDeclaration)
                // operRef.getParams().get("src").get(0).getCodeObj()).getQualifiedName();

                bandera = !listMetric.isEmpty();
                if (bandera) {

                    clase = listMetric.get(0).getClasss();
                    for (Register reg : listMetric) {
                        if (!clase.equals(reg.getClasss())) {
                            clasesList.put(clase, metricList);
                            clase = reg.getClasss();
                            metricList = new LinkedHashMap<String, Double>();
                        }
                        metricList.put(reg.getMetric(), reg.getValue());

                    }
                    clasesList.put(clase, metricList);
                    predictMetricsRecordar.put(operRef.getRefId(), clasesList);
                }

            } else {
                bandera = false;
            }
        }
        return bandera;

    }

    @Override
    public Double apply(List<RefactoringOperation> x) {
        // TODO Auto-generated method stub
        double GQSm_ = 0;
        try {
            LinkedHashMap<String, LinkedHashMap<String, Double>> predictedMetrics = ActualMetrics(PredictingMetrics(x));
            //printFitness( predictedMetrics );

            //LinkedHashMap<String, Double> bias = TotalActualMetrics( predictedMetrics );
            //printFitness2(bias);
            //GQSm_ = GQSm(bias); //First calculate proneness per metric and then normalize

            GQSm_ = GQSproneness(predictedMetrics); //First normalize and then calculate proneness

        } catch (ReadException | IOException | CompilUnitException | WritingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (x.size() == 1) {
            // GQSm();
        }
        return GQSm_;
    }

    private Double GQSproneness(LinkedHashMap<String, LinkedHashMap<String, Double>> metricActualVector) {
        double generalQuality = 0.0;
        double denominator = 0.0;
        double numerator = 0.0;

        LinkedHashMap<String, Double> SUA_metric = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> SUA_prev_metric = new LinkedHashMap<String, Double>();

        for (Entry<String, LinkedHashMap<String, Double>> clase : metricActualVector.entrySet()) {
            //1. Adding predicting metrics
            for (Entry<String, Double> metric : clase.getValue().entrySet()) {
                // evaluate if the metric is repeat for summing
                if (SUA_metric.containsKey(metric.getKey())) {
                    SUA_metric.replace(metric.getKey(),
                            SUA_metric.get(metric.getKey()),
                            SUA_metric.get(metric.getKey()) + metric.getValue());
                } else {
                    SUA_metric.put(metric.getKey(), metric.getValue());
                }
            }

            //2. Checking the class in prevMetrics
            if (MetaphorCode.getPrevMetrics().containsKey(clase.getKey())) {
                // Extracting prevMetrics
                for (Entry<String, Double> metric : MetaphorCode.getPrevMetrics().get(clase.getKey()).entrySet()) {
                    // Evaluate that the metric is impacted
                    if (metricActualVector.get(clase.getKey()).containsKey(metric.getKey())) {
                        // Evaluate if the metric is repeat for summing
                        if (SUA_prev_metric.containsKey(metric.getKey())) {
                            SUA_prev_metric.replace(metric.getKey(),
                                    SUA_prev_metric.get(metric.getKey()),
                                    SUA_prev_metric.get(metric.getKey()) + metric.getValue());
                        } else {
                            SUA_prev_metric.put(metric.getKey(), metric.getValue());
                        }
                    }
                }
            } else {
                //For new classes
                //Commented cause it is not necessary adding new classes metrics to the vector
                /*
                for ( Entry<String, Double> metric : clase.getValue().entrySet() ) {
					// evaluate if the metric is repeat for summing
					if ( SUA_prev_metric.containsKey( metric.getKey() ) ) {
						SUA_prev_metric.replace( metric.getKey(), 
												SUA_prev_metric.get(metric.getKey()),
												SUA_prev_metric.get(metric.getKey()) + metric.getValue());
					} else {
						SUA_prev_metric.put( metric.getKey(), metric.getValue() );
					}
				}*/
            }
        }//End Loop Clase

        Double min = Collections.min(SUA_metric.values());
        Double max = Collections.max(SUA_metric.values());

        Double minPrev = Collections.min(SUA_prev_metric.values());
        Double maxPrev = Collections.max(SUA_prev_metric.values());

        //Vector weights for metrics
        double W[] = new double[SUA_metric.size()];
        double w = (double) 1 / (double) SUA_metric.size();

        for (Entry<String, Double> metric : SUA_prev_metric.entrySet()) {
            if (SUA_metric.containsKey(metric.getKey())) {
                //Accumulate the metrics
                numerator = numerator + (w * ((SUA_metric.get(metric.getKey()) - min) / (max - min)));
                denominator = denominator + (w * ((metric.getValue() - minPrev) / (maxPrev - minPrev)));

            } else {
                System.out.println("Something is wrong with prev_metrics");
            }
        }
        System.out.println("Numerador: " + numerator);
        System.out.println("Denominador: " + denominator);
        generalQuality = numerator / denominator;
        System.out.println("Proneness[FITNESS]: " + generalQuality);

        escribirTextoArchivo(String.valueOf(generalQuality) + "\r\n");

        return generalQuality;

    }

    //Organized the prediction and reduce the data
    private LinkedHashMap<String, LinkedHashMap<String, Double>> ActualMetrics(
            LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> prediction) {
        // Average of all the metrics per class
        LinkedHashMap<String, LinkedHashMap<String, Double>> SUA = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
        LinkedHashMap<String, Double> SUA_metric = new LinkedHashMap<String, Double>();

        for (Entry<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> ref : prediction.entrySet()) {
            for (Entry<String, LinkedHashMap<String, Double>> clase : ref.getValue().entrySet()) {
                //Add metrics per class to SUA_metric
                for (Entry<String, Double> metric : clase.getValue().entrySet()) {
                    SUA_metric.put(metric.getKey(), metric.getValue());
                }
                // Class Loop
                if (!SUA.containsKey(clase.getKey())) { //Evaluating if SUA contains the class
                    SUA.put(clase.getKey(), SUA_metric);    //Adding to SUA if do not contains the class
                } else {
                    //Metric Loop
                    for (Entry<String, Double> metric_ : clase.getValue().entrySet()) {
                        if (SUA.get(clase.getKey()).containsKey(metric_.getKey())) { //If the SUA class contains already the metric
                            // Deciding the maximum value
                            if (metric_.getValue() >= SUA.get(clase.getKey()).get(metric_.getKey())) {
                                SUA.get(clase.getKey()).replace(metric_.getKey(),
                                        SUA.get(clase.getKey()).get(metric_.getKey()), metric_.getValue());
                            } else {
                                SUA.get(clase.getKey()).replace(metric_.getKey(),
                                        SUA.get(clase.getKey()).get(metric_.getKey()),
                                        SUA.get(clase.getKey()).get(metric_.getKey()));
                            }
                        } else {
                            SUA.get(clase.getKey()).put(metric_.getKey(), metric_.getValue());
                        }

                    } // Metric Loop
                }
                SUA_metric = new LinkedHashMap<String, Double>();
            } // Clase Loop
        } // Ref Loop

        // for(int ref = 0; ref < prediction.size(); ref++){
        // for(int clase = 0; clase < prediction.)
        // }//Ref Loop

        return SUA;
    }

    //Redress is called here for prediction
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> PredictingMetrics(
            List<RefactoringOperation> operations)
            throws ReadException, IOException, CompilUnitException, WritingException {

        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> predictMetrics = new
                LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>();
        List<RefactoringOperation> operationsClone;
        //(List<RefactoringOperation>)Clone.create(operations);
        for (RefactoringOperation operRef : operations) {
            long startTime = System.nanoTime();
            boolean recall = recordar(operRef);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            //Fixme with Logger
            //MainOptimization.escribirTextoArchivo("Recodar tiempo;" + duration / 1000000 + "\n");

            if (recall) {
                //System.out.println("Recalling metrics for: " + operRef.getRefType().getAcronym());
                predictMetrics.putAll((LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>)
                        Clone.create(predictMetricsRecordar));
                predictMetricsRecordar = new
                        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>();

            } else {

                startTime = System.nanoTime();                //start time to prediction calc
                System.out.println("Predicting metrics for: " + operRef.getRefType().getAcronym());
                operationsClone = new ArrayList<RefactoringOperation>();
                operationsClone.add(operRef);
                MetricCalculator calc = new MetricCalculator();
                //predictMetrics = calc.predictMetrics(operations, metaphor.getMetrics(), prevMetrics);
                //predictMetrics = calc.predictMetrics(operationsClone, metaphor.getMetrics(), prevMetrics);
                predictMetricsMemorizar.putAll(calc.predictMetrics(operationsClone, MetaphorCode.getMetrics(), MetaphorCode.getPrevMetrics()));
                predictMetrics.putAll((LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>)
                        Clone.create(predictMetricsMemorizar));
                endTime = System.nanoTime();//stop prediction proccess time
                duration = (endTime - startTime);
                //Fixme with Logger
                //MainOptimization.escribirTextoArchivo("Prediccion tiempo;" + duration / 1000000 + ";" + operRef.getRefType().getAcronym() + "\n");


                //Memoriza en Archivo lo que se encuentra en la predicci�n y vac�s la estructura
                startTime = System.nanoTime();
                memorizar(operRef);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                //Fixme with Logger
                //MainOptimization.escribirTextoArchivo("Memorizar tiempo;" + duration / 1000000 + "\n");

            }
        }

        return predictMetrics;

    }

    private void printFitness(LinkedHashMap<String, LinkedHashMap<String, Double>> structureMetrics) {
        for (Entry<String, LinkedHashMap<String, Double>> clase : structureMetrics.entrySet()) {
            for (Entry<String, Double> metric_ : clase.getValue().entrySet()) {
                System.out.println("[Class: " + clase.getKey() + "] \t" + "[Metric: " + metric_.getKey() + "] \t"
                        + "[Value: " + metric_.getValue());
            }
        }
    }

    private void printFitness2(LinkedHashMap<String, Double> totalActualMetrics) {
        for (Entry<String, Double> metric_ : totalActualMetrics.entrySet()) {
            System.out.println("[Metric: " + metric_.getKey() + "] \t" + "[Value: " + metric_.getValue() + "] \t");
        }

    }

    //This function generates the tracking for the fitness
    //in all possible evaluations
    public void escribirTextoArchivo(String texto) {
        String ruta = file + "_TEST_FITNESS_JAR.txt";
        try (FileWriter fw = new FileWriter(ruta, true);
             FileReader fr = new FileReader(ruta)) {
            //Escribimos en el fichero un String y un caracter 97 (a)
            fw.write(texto);
            //fw.write(97);
            //Guardamos los cambios del fichero
            fw.flush();
        } catch (IOException e) {
            System.out.println("Error E/S: " + e);
        }

    }
/**
 * refactor, clase, metrica, valor, code{src[], trg[], metod, campo}
 * 01,01,PUF,6.7,S,a-z
 *
 * 01,02,PUF,98.6,T,
 * 01,03,PUF,5.6,S
 *
 * 01,01,PUF,4.5,T
 * 01,01,PUF,55,4,S
 *
 */

}
