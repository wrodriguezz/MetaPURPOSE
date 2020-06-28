



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class evaluate_precision_recall_f1measure_chemical2disease  {

	
	
	public static void main(String[] args) {
		
	
	int filter_count = 0;
	String query_term_1 = "";
	String query_term_2 = "";
	String query_term_3 = "";
	String fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/";
	String fileDirAndName_FACTAplus = "./data/FACTAplus/";
	String fileDirAndName_FACTAplus_CasRN  = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_liver.txt";

	int i = 0;
	int ROC_gap = 500; //5 or 10 or 20 or 500
	int ROC_for_begin = 1;
	int ROC_for_end = 1; // 1 or 100
	boolean run_PURPOSE_liver = false;
	boolean run_PURPOSE_lung = false;
	boolean run_PURPOSE_kidney =false;
	boolean run_PURPOSE_heart = false;
	boolean run_PURPOSE_cancer = false;
	boolean run_PURPOSE_brain = false;
	
	boolean run_PURPOSE_no_citation_liver = false;
	boolean run_PURPOSE_no_citation_lung = false;
	boolean run_PURPOSE_no_citation_kidney =false;
	boolean run_PURPOSE_no_citation_heart = false;
	boolean run_PURPOSE_no_citation_cancer = false;
	boolean run_PURPOSE_no_citation_brain = false;

	boolean run_FACTAplus_CasRN_liver = false;
	boolean run_FACTAplus_CasRN_lung = false;
	boolean run_FACTAplus_CasRN_kidney = false;
	boolean run_FACTAplus_CasRN_heart = false;
	boolean run_FACTAplus_CasRN_cancer = false;
	boolean run_FACTAplus_CasRN_brain = false;
	
	boolean run_FACTAplus = false;	
	
	boolean run_liver = true;
	if(!run_liver) {
		run_PURPOSE_liver = true;
		run_PURPOSE_no_citation_liver = true;
		run_FACTAplus_CasRN_liver = true;
	}
	boolean run_lung = true;
	if(!run_lung) {
		run_PURPOSE_lung = true;
		run_PURPOSE_no_citation_lung = true;
		run_FACTAplus_CasRN_lung = true;
	}
	boolean run_kidney = true;
	if(!run_kidney) {
		run_PURPOSE_kidney = true;
		run_PURPOSE_no_citation_kidney = true;
		run_FACTAplus_CasRN_kidney = true;
	}
	boolean run_heart = true;
	if(run_heart) {
		run_PURPOSE_heart = true;
		run_PURPOSE_no_citation_heart = true;
		run_FACTAplus_CasRN_heart = true;
	}
	boolean run_cancer = true;
	if(!run_cancer) {
		run_PURPOSE_cancer = true;
		run_PURPOSE_no_citation_cancer = true;
		run_FACTAplus_CasRN_cancer = true;
	}
	boolean run_brain = true;
	if(!run_brain) {
		run_PURPOSE_brain = true;
		run_PURPOSE_no_citation_brain = true;
		run_FACTAplus_CasRN_brain = true;
	}
	
	boolean print_chemical_list = true; //2018-02-07 
	String fileDirAndName_CTD_ground_truth = "";
	
	
	

/*************************************************************************************
 * 
 * PURPOSE Evaluation
 *
 **************************************************************************************/
		


	//===========================================================================================
	// neoplasm or cancer
	//===========================================================================================
	if(run_PURPOSE_cancer){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		neoplasm or cancer                                                                  \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_neoplasm_cancer_carcinoma";//2018-01-05
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_cancer";//2018-02-06
		
		
		query_term_1 = "cancer";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/cancer+human_PURPOSE[chemical]";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	
		
		
	
	//===========================================================================================
	// liver
	//===========================================================================================
	if(run_PURPOSE_liver){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		liver                                                                \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_liver";//2018-01-10
		 
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_liver";//2018-02-07
		
		
		query_term_1 = "liver";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/liver+human_PURPOSE[chemical]";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	
		
		
	
	
	//===========================================================================================
	// lung
	//===========================================================================================
	if(run_PURPOSE_lung){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		lung                                                                \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_lung";//2018-01-10
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_lung";//2018-02-07
		

		query_term_1 = "lung";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/lung+human_PURPOSE[chemical]";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	
	
	
	//===========================================================================================
	// kidney
	//===========================================================================================
	if(run_PURPOSE_kidney){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		kidney                                                              \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_kidney";//2018-01-10,18
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_kidney";//2018-02-07
		
		
		query_term_1 = "kidney";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/kidney+human_PURPOSE[chemical]";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	

	
	
	

	
	//===========================================================================================
	// brain
	//===========================================================================================
	if(run_PURPOSE_brain){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		brain                                                             \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_brain";//2018-01-11
		
		
		query_term_1 = "brain";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/brain+human_PURPOSE[chemical]";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	

	
	
	
	//===========================================================================================
	// cardiovascular
	//===========================================================================================
	if(run_PURPOSE_heart){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		cardiovascular                                                             \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_cardiovascular_heart";//2018-01-10
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_cardiovascular";//2018-02-07
		
		
		
		query_term_1 = "cardiovascular";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/cardiovascular+human_PURPOSE[chemical]";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	
	
	
System.out.println("\n\n\n\n\n\n");
	
	

/*************************************************************************************
 * 
 * PURPOSE --- (no citation) --- Evaluation
 *
 **************************************************************************************/

	//===========================================================================================
	// liver
	//===========================================================================================
	if(run_PURPOSE_no_citation_liver){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		liver                                                                \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_liver";//2018-01-10
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalList_DirectEvidence_MESHID_liver";//2018-02-06
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_liver";//2018-02-07
		
		query_term_1 = "liver";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/liver+human_PURPOSE[chemical]_no_citation";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	

	
	
	
	

	//===========================================================================================
	// lung
	//===========================================================================================
	if(run_PURPOSE_no_citation_lung){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		lung                                                              \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_lung";//2018-01-11
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalList_DirectEvidence_MESHID_lung";//2018-02-06
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_lung";//2018-02-07
		
		
		query_term_1 = "lung";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/lung+human_PURPOSE[chemical]_no_citation";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	

	
	

	//===========================================================================================
	// kidney
	//===========================================================================================
	if(run_PURPOSE_no_citation_kidney){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		kidney                                                              \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_kidney";//2018-01-11
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalList_DirectEvidence_MESHID_kidney";//2018-02-06
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_kidney";//2018-02-07
		
		
		query_term_1 = "kidney";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/kidney+human_PURPOSE[chemical]_no_citation";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	


	

	//===========================================================================================
	// brain
	//===========================================================================================
	if(run_PURPOSE_no_citation_brain){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		brain                                                              \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_brain";//2018-01-11
		
		query_term_1 = "brain";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/brain+human_PURPOSE[chemical]_no_citation";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	
	
	

	//===========================================================================================
	// cardiovascular
	//===========================================================================================
	if(run_PURPOSE_no_citation_heart){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		cardiovascular                                                              \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_cardiovascular_heart";//2018-01-10
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalList_DirectEvidence_MESHID_cardiovascular";//2018-02-06
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_cardiovascular";//2018-02-07
		
		
		query_term_1 = "cardiovascular";
		query_term_2 = null;
		query_term_3 = null;
		
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/cardiovascular+human_PURPOSE[chemical]_no_citation";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	

	

	//===========================================================================================
	// cancer
	//===========================================================================================
	if(run_PURPOSE_no_citation_cancer){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		cancer                                                              \n"
		+ "==============================================================================\n" 
		);	

	
		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalIDList_neoplasm_cancer_carcinoma";//2018-01-11
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalList_DirectEvidence_MESHID_cancer";//2018-02-06
		fileDirAndName_CTD_ground_truth = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_cancer";//2018-02-07
		
		
		query_term_1 = "cancer";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_PURPOSE  = "./PURPOSE_chemical_Results/cancer+human_PURPOSE[chemical]_no_citation";
		
		
		if(print_precision_recall_for_ROC_only){
			if(fileDirAndName_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_PURPOSE_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_PURPOSE,
					fileDirAndName_CTD_ground_truth);
		}
	}
	


	
	
System.out.println("\n\n\n\n\n\n");
	



/*************************************************************************************
 * 
 * FACTA+ Evaluation
 *
 **************************************************************************************/


	//===========================================================================================
	// liver
	//===========================================================================================
	if(run_FACTAplus){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		liver                                                                \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_chemical2disease_liver";//2018-01-11
		
		
		query_term_1 = "liver";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus  = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_liver.txt";
		String fileDirAndName_drugBank_DrugBank_to_ID  = "./data/drugbank_all_full_database/DrugBankID_to_Name_0112.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_FACTAplus_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus,
					fileDirAndName_CTD_ground_truth,
					fileDirAndName_drugBank_DrugBank_to_ID);
		}
	}
	
		
	


	//===========================================================================================
	// lung
	//===========================================================================================
	if(run_FACTAplus){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		lung                                                               \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_chemical2disease_lung";//2018-01-11
		
		
		query_term_1 = "lung";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus  = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_lung.txt";
		String fileDirAndName_drugBank_DrugBank_to_ID  = "./data/drugbank_all_full_database/DrugBankID_to_Name_0112.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_FACTAplus_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus,
					fileDirAndName_CTD_ground_truth,
					fileDirAndName_drugBank_DrugBank_to_ID);
		}
	}

	




	//===========================================================================================
	// kidney
	//===========================================================================================
	if(run_FACTAplus){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		kidney                                                                \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_chemical2disease_kidney";//2018-01-11
		
		
		query_term_1 = "kidney";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus  = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_kidney.txt";
		String fileDirAndName_drugBank_DrugBank_to_ID  = "./data/drugbank_all_full_database/DrugBankID_to_Name_0112.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_FACTAplus_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus,
					fileDirAndName_CTD_ground_truth,
					fileDirAndName_drugBank_DrugBank_to_ID);
		}
	}

	


	//===========================================================================================
	// brain
	//===========================================================================================
	if(run_FACTAplus){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		brain                                                                \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_chemical2disease_brain";//2018-01-11
		
		
		query_term_1 = "brain";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus  = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_brain.txt";
		String fileDirAndName_drugBank_DrugBank_to_ID  = "./data/drugbank_all_full_database/DrugBankID_to_Name_0112.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_FACTAplus_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus,
					fileDirAndName_CTD_ground_truth,
					fileDirAndName_drugBank_DrugBank_to_ID);
		}
	}
	

	
	


	//===========================================================================================
	// neoplasm or cancer or carcinoma
	//===========================================================================================
	if(run_FACTAplus){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		neoplasm or cancer or carcinoma                                                               \n"
		+ "==============================================================================\n" 
		);	
	
		
		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_chemical2disease_neoplasm_cancer_carcinoma";//2018-01-11
		
		
		query_term_1 = "neoplasm";
		query_term_2 = "cancer";
		query_term_3 = "carcinoma";

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus  = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_neoplasm_cancer_carcinoma.txt";
		String fileDirAndName_drugBank_DrugBank_to_ID  = "./data/drugbank_all_full_database/DrugBankID_to_Name_0112.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_FACTAplus_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus,
					fileDirAndName_CTD_ground_truth,
					fileDirAndName_drugBank_DrugBank_to_ID);
		}
	}
	

	
	
	
	

	//===========================================================================================
	// cardiovascular or heart
	//===========================================================================================
	if(run_FACTAplus){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		cardiovascular or heart                                                              \n"
		+ "==============================================================================\n" 
		);	
	
		
		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_chemical2disease_cardiovascular_heart";//2018-01-11
		
		query_term_1 = "cardiovascular";
		query_term_2 = "heart";
		query_term_3 = null;


		//ROC_for_end = 100;
		fileDirAndName_FACTAplus  = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_cardiovascular_heart.txt";
		String fileDirAndName_drugBank_DrugBank_to_ID  = "./data/drugbank_all_full_database/DrugBankID_to_Name_0112.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			compare_FACTAplus_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus,
					fileDirAndName_CTD_ground_truth,
					fileDirAndName_drugBank_DrugBank_to_ID);
		}
	}
	
	
	
	
	
	
	
	
	

/*************************************************************************************
 * 
 * FACTA+ Evaluation using CasRN ID as matching ID
 *
 **************************************************************************************/


	//===========================================================================================
	// liver
	//===========================================================================================
	if(run_FACTAplus_CasRN_liver){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		liver                                                                \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_UniqueCasRNList_liver";//2018-01-18
		
		
		query_term_1 = "liver";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus_CasRN  = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_liver.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			
			compare_FACTAplus_using_CASID_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus_CasRN,
					fileDirAndName_CTD_ground_truth);
			
		}
	}
	
			
	
	
	
	
	

	//===========================================================================================
	// lung
	//===========================================================================================
	if(run_FACTAplus_CasRN_lung){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		lung                                                                \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_UniqueCasRNList_lung";//2018-01-18
		
		
		query_term_1 = "lung";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus_CasRN  = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_lung.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			
			compare_FACTAplus_using_CASID_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus_CasRN,
					fileDirAndName_CTD_ground_truth);
			
		}
	}
	
			
	
	
	
	

	//===========================================================================================
	// kidney
	//===========================================================================================
	if(run_FACTAplus_CasRN_kidney){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		kidney                                                              \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_UniqueCasRNList_kidney";//2018-01-18
		
		
		query_term_1 = "kidney";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus_CasRN  = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_kidney.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			
			compare_FACTAplus_using_CASID_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus_CasRN,
					fileDirAndName_CTD_ground_truth);
			
		}
	}
	
			
	
	
	
	
	
	//===========================================================================================
	// neoplasm_cancer_carcinoma
	//===========================================================================================
	if(run_FACTAplus_CasRN_cancer){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		neoplasm_cancer_carcinoma                                                              \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_UniqueCasRNList_neoplasm_cancer_carcinoma";//2018-01-18
		
		
		query_term_1 = "neoplasm";
		query_term_2 = "cancer";
		query_term_3 = "carcinoma";
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus_CasRN  = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_neoplasm_cancer_carcinoma.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			
			compare_FACTAplus_using_CASID_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus_CasRN,
					fileDirAndName_CTD_ground_truth);
			
		}
	}
	


	
	
	//===========================================================================================
	// cardiovascular_heart
	//===========================================================================================
	if(run_FACTAplus_CasRN_heart){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		cardiovascular_heart                                                             \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_UniqueCasRNList_cardiovascular_heart";//2018-01-18
		
		
		query_term_1 = "cardiovascular";
		query_term_2 = "heart";
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus_CasRN  = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_cardiovascular_heart.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			
			compare_FACTAplus_using_CASID_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus_CasRN,
					fileDirAndName_CTD_ground_truth);
			
		}
	}
	

	
	
	

	//===========================================================================================
	// brain
	//===========================================================================================
	if(run_FACTAplus_CasRN_brain){
		
		System.out.println(""
		+ "==============================================================================\n" 
		+ "		brain                                                             \n"
		+ "==============================================================================\n" 
		);	

		//using CTD chemical disease association
		fileDirAndName_CTD_ground_truth 
		= "./data/myCTD/CTD_UniqueCasRNList_brain";//2018-01-18
		
		
		query_term_1 = "brain";
		query_term_2 = null;
		query_term_3 = null;
		

		//ROC_for_end = 100;
		fileDirAndName_FACTAplus_CasRN  = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_brain.txt";
		
		
		if(print_precision_recall_for_ROC_only){
			
			System.out.print("-----------[System = FACTA+] && ");
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " 
			+ query_term_1 + " + " + query_term_2  + " + " + query_term_3 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
			
			System.out.printf("system\tprecision\trecall\n");
			
		}

		
		for(i = ROC_for_begin; i <= ROC_for_end; i++) {
			filter_count = ROC_gap*i;
			
			compare_FACTAplus_using_CASID_vs_CTD_ground_truth(
					print_chemical_list, filter_count, 
					query_term_1, query_term_2, query_term_3, fileDirAndName_FACTAplus_CasRN,
					fileDirAndName_CTD_ground_truth);
			
		}
	}
	
			
	
	System.out.println("\n\n\n\n\n\n");


	
	
}


static boolean print_detail = false;
static boolean print_precision_recall_for_ROC_only = !print_detail;




/*************************************************************************************
 *  
 *  PURPOSE
 *
 **************************************************************************************/

public static void compare_PURPOSE_vs_CTD_ground_truth(
		boolean print_chemical_list,
		int filter_count,
		String query_term_1, String query_term_2, String query_term_3, 
		String fileDirAndName_human_PURPOSE,
		String fileDirAndName_CTD_ground_truth) {

	try {
		
		ArrayList<String> CTD_ground_truth_ChemicalID_list = new ArrayList<String>();//2018-02-07
		//ArrayList<String> CTD_ground_truth_ChemicalName_list = new ArrayList<String>(); //2018-02-06
		

		BufferedReader in = new BufferedReader(
				new FileReader(fileDirAndName_CTD_ground_truth));
		
		String temp = null;
		String[] token_array = null;
		temp = in.readLine();
		temp = in.readLine();
		temp = in.readLine();
		//System.out.println(temp); //
		
		while ((temp = in.readLine()) != null) {					
			token_array = temp.split("\t");

			if(token_array.length == 1){ //CTD ChemicalID list
				CTD_ground_truth_ChemicalID_list.add("MESH:"+token_array[0]);//2018-02-07 using ChemicalID
				//CTD_ground_truth_ChemicalName_list.add(token_array[0].toLowerCase());//2018-02-06 using ChemicalName
			}
		}
		
		//System.out.println("CTD_ground_truth_ChemicalID_list.size() = " + CTD_ground_truth_ChemicalID_list.size());
		
		if(in!=null){
			in.close();
		}
		
		
		BufferedReader in_human_PURPOSE = new BufferedReader(
				new FileReader(fileDirAndName_human_PURPOSE));
		
		
		temp = in_human_PURPOSE.readLine();
		temp = in_human_PURPOSE.readLine();
		temp = in_human_PURPOSE.readLine();
		
		int matched_count = 0;
		int retrieved_count = 0;
		while ((temp = in_human_PURPOSE.readLine()) != null && retrieved_count < filter_count) {
				
			
			retrieved_count++;
			token_array = temp.split("\t");

			
			if(CTD_ground_truth_ChemicalID_list.contains(token_array[0])){ //2018-02-07 using ChemicalID
			//if(CTD_ground_truth_ChemicalName_list.contains(token_array[1].toLowerCase())){//2018-02-06 using ChemicalName
				matched_count++;
				if(print_chemical_list)
					System.out.println(token_array[1]+ "\t" +1);
			}
			else{
				if(print_chemical_list)
					System.out.println(token_array[1]+ "\t" +0);
			}
		
		}
		
		
		if(print_detail) {
			if(fileDirAndName_human_PURPOSE.indexOf("no_citation") !=-1) {
				System.out.print("-----------[System = PURPOSE(w/o citation)] && ");
			}
			else {
				System.out.print("-----------[System = PURPOSE] && ");
			}
			
			
			if(query_term_2 != null) {
				System.out.println("[Query term = " + query_term_1 + " + " + query_term_2 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
		}
		
		int TP = matched_count;
		
		if(print_detail)
			System.out.println("True Positive = " + TP);
		int TP_FP = retrieved_count;
		
		if(print_detail)
			System.out.println("TP + FP = " + TP_FP);
		int TP_FN = CTD_ground_truth_ChemicalID_list.size();//2018-02-07 using ChemicalID
		int FN = CTD_ground_truth_ChemicalID_list.size()-TP;//2018-02-07 using ChemicalID
		//int TP_FN = CTD_ground_truth_ChemicalName_list.size();////2018-02-06 using ChemicalName
		//int FN = CTD_ground_truth_ChemicalName_list.size()-TP;////2018-02-06 using ChemicalName
		int FP = TP_FP - TP;
		double FDR = (FP+0.00)/(FP+TP+0.00);
		if(print_detail)
			System.out.println("TP + FN = " + TP_FN );
		double precision =  (TP+0.00)/(TP_FP+0.00);
		double recall =  (TP+0.00)/(TP_FN+0.00);
		double F1_measure = 2*precision*recall/(precision+recall);
		
		if(fileDirAndName_human_PURPOSE.indexOf("no_citation") !=-1) {
			System.out.printf("%d\t%.3f\t%.3f\n" , 2,  precision , recall) ;
		}
		else {
			System.out.printf("%d\t%.3f\t%.3f\n" , 1,  precision , recall) ;
		}
		
		if(print_detail) {
			System.out.printf("PURPOSE Precision = %.3f \n" , precision ) ;
			System.out.printf("PURPOSE Recall (sensitivity) = %.3f \n" , recall ) ;
			System.out.printf("PURPOSE F1_measure = %.3f \n" ,  F1_measure) ;
			System.out.printf("PURPOSE FDR = %.3f \n" ,  FDR) ;
			System.out.println("--------------------------------------------------------------------\n");
		}
		
	} catch ( IOException e) {
		e.printStackTrace();
		
	}

}








/*************************************************************************************
 *  
 *  FACTA+
 *
 **************************************************************************************/

public static void compare_FACTAplus_vs_CTD_ground_truth(
		boolean print_chemical_list,
		int filter_count,
		String query_term_1, String query_term_2, String query_term_3, 
		String fileDirAndName_FACTAplus,
		String fileDirAndName_CTD_ground_truth,
		String fileDirAndName_drugBank_DrugBank_to_ID) {

	try {
		
		ArrayList<String> CTD_ground_truth_ChemicalID_list = new ArrayList<String>();
		
		ArrayList<String> CTD_ground_truth_to_DrugBank_ID_list = new ArrayList<String>();
		ArrayList<String> CTD_ground_truth_to_DrugBank_name_list = new ArrayList<String>();

		ArrayList<String> other_chemical_disease_associations_to_DrugBank_ID_list = new ArrayList<String>();
		ArrayList<String> other_chemical_disease_associations_to_DrugBank_name_list = new ArrayList<String>();


		BufferedReader in = new BufferedReader(
				new FileReader(fileDirAndName_CTD_ground_truth));
		
		String temp = null;
		String[] token_array = null;
		//temp = in.readLine();
		//System.out.println(temp); //
		
		while ((temp = in.readLine()) != null) {
			
			//System.out.println(temp);
			token_array = temp.split("\t");
			CTD_ground_truth_ChemicalID_list.add(token_array[0].toLowerCase());
			
		}
		
		//System.out.println("CTD_ground_truth_ChemicalID_list.size() = " + CTD_ground_truth_ChemicalID_list.size());
		
		if(in!=null){
			in.close();
		}
		
		
		
		
		BufferedReader in_drugBank_DrugBank_to_ID = new BufferedReader(
				new FileReader(fileDirAndName_drugBank_DrugBank_to_ID));
		
		
		//System.out.println("-------------------------------------------------------------");
		while ((temp = in_drugBank_DrugBank_to_ID.readLine()) != null) {
			
			token_array = temp.split("\t");
			
			if(CTD_ground_truth_ChemicalID_list.contains(token_array[1].toLowerCase())){
				//System.out.println(token_array[0] + "\t" + token_array[1]);
				
				CTD_ground_truth_to_DrugBank_ID_list.add(token_array[0]);
				CTD_ground_truth_to_DrugBank_name_list.add(token_array[1]);
			}
			else{
				other_chemical_disease_associations_to_DrugBank_ID_list.add(token_array[0]);
				other_chemical_disease_associations_to_DrugBank_name_list.add(token_array[1]);
			}
		
		}
		//System.out.println("-------------------------------------------------------------");
		

		if(in_drugBank_DrugBank_to_ID!=null){
			in_drugBank_DrugBank_to_ID.close();
		}
		
		
		
		
		BufferedReader in_FACTAplus = new BufferedReader(
				new FileReader(fileDirAndName_FACTAplus));
		
		
		int matched_count = 0;
		int retrieved_count = 0;
		int index_DrugBankID = -1;
		int index_other = -1;
		while ((temp = in_FACTAplus.readLine()) != null && retrieved_count < filter_count) {

			retrieved_count++;
			
			token_array = temp.split("\t");
			
			index_DrugBankID = CTD_ground_truth_to_DrugBank_ID_list.indexOf(token_array[0]);
			if(index_DrugBankID!=-1){
				matched_count++;
				if(print_chemical_list)
					System.out.println(CTD_ground_truth_to_DrugBank_name_list.get(index_DrugBankID)+ "\t" + token_array[0] + "\t" + token_array[1] + "\t" +1);
			}
			else{
				
				index_other = other_chemical_disease_associations_to_DrugBank_ID_list.indexOf(token_array[0]);
						
				if(print_chemical_list){
					if(index_other!=-1){
						System.out.println(other_chemical_disease_associations_to_DrugBank_name_list.get(index_other)+ "\t" + token_array[0] + "\t" +token_array[1] + "\t" +0);
					}
					else{
						System.out.println(token_array[0] + token_array[1]+ "\t" +0);
					}
				}
			}
		}
		
		
		if(print_detail) {
			
			System.out.print("-----------[System = FACTA+] && ");
			
		
			if(query_term_2 != null) {
				System.out.println("[Query term = " + query_term_1 + " + " + query_term_2 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
		}
		
		int TP = matched_count;
		
		if(print_detail)
			System.out.println("True Positive = " + TP);
		int TP_FP = retrieved_count;
		
		if(print_detail)
			System.out.println("TP + FP = " + TP_FP);
		int TP_FN = CTD_ground_truth_ChemicalID_list.size();
		int FN = CTD_ground_truth_ChemicalID_list.size()-TP;
		int FP = TP_FP - TP;
		double FDR = (FP+0.00)/(FP+TP+0.00);
		if(print_detail)
			System.out.println("TP + FN = " + TP_FN );
		double precision =  (TP+0.00)/(TP_FP+0.00);
		double recall =  (TP+0.00)/(TP_FN+0.00);
		double F1_measure = 2*precision*recall/(precision+recall);
		
		
		System.out.printf("%d\t%.3f\t%.3f\n" , 3,  precision , recall) ;
		
		
		if(print_detail) {
			System.out.printf("FACTA+ Precision = %.3f \n" , precision ) ;
			System.out.printf("FACTA+ Recall (sensitivity) = %.3f \n" , recall ) ;
			System.out.printf("FACTA+ F1_measure = %.3f \n" ,  F1_measure) ;
			System.out.printf("FACTA+ FDR = %.3f \n" ,  FDR) ;
			System.out.println("--------------------------------------------------------------------\n");
		}
		
	} catch ( IOException e) {
		e.printStackTrace();
		
	}

}//===================================< end of method >=================================================










/*************************************************************************************
 *  
 *  FACTA+ using CASID to match
 *
 **************************************************************************************/

public static void compare_FACTAplus_using_CASID_vs_CTD_ground_truth(
		boolean print_chemical_list,
		int filter_count,
		String query_term_1, String query_term_2, String query_term_3, 
		String fileDirAndName_FACTAplus_CasRN,
		String fileDirAndName_CTD_ground_truth) {

	try {
		
		ArrayList<String> CTD_ground_truth_Chemical_CASID_list = new ArrayList<String>();
		
		ArrayList<String> CTD_ground_truth_to_CasRN_ID_list = new ArrayList<String>();
		ArrayList<String> CTD_ground_truth_to_CasRN_name_list = new ArrayList<String>();

		ArrayList<String> other_chemical_disease_associations_to_CasRN_ID_list = new ArrayList<String>();
		ArrayList<String> other_chemical_disease_associations_to_CasRN_name_list = new ArrayList<String>();


		BufferedReader in = new BufferedReader(
				new FileReader(fileDirAndName_CTD_ground_truth));
		
		String temp = null;
		
		while ((temp = in.readLine()) != null) {
				CTD_ground_truth_Chemical_CASID_list.add(temp);
		}
		
		//System.out.println("CTD_ground_truth_ChemicalID_list.size() = " + CTD_ground_truth_ChemicalID_list.size());
		
		if(in!=null){
			in.close();
		}
		
		
		BufferedReader in_FACTAplus_CasRN = new BufferedReader(
				new FileReader(fileDirAndName_FACTAplus_CasRN));
		
		
		int matched_count = 0;
		int retrieved_count = 0;
		int index_CasID = -1;
		int index_other = -1;
		String[] token_array = null;
		while ((temp = in_FACTAplus_CasRN.readLine()) != null && retrieved_count < filter_count) {

			retrieved_count++;
			
			token_array = temp.split("\t");
			
			index_CasID = CTD_ground_truth_Chemical_CASID_list.indexOf(token_array[0]);
			if(index_CasID!=-1){
				matched_count++;
				if(print_chemical_list) {
					System.out.println(CTD_ground_truth_Chemical_CASID_list.get(index_CasID)+ "\t" + token_array[1] + "\t" +1);
				}
			}
			else{
				if(print_chemical_list) {
					System.out.println(token_array[0] + "\t" + token_array[1]+ "\t" +0);
				}
			}
		}
		
		//close reader
		in_FACTAplus_CasRN.close();
		
		
		if(print_detail) {
			
			System.out.print("-----------[System = FACTA+] && ");
			
		
			if(query_term_2 != null) {
				System.out.println("[Query term = " + query_term_1 + " + " + query_term_2 + "]------");
			}
			else {
				System.out.println("[Query term = " + query_term_1 + "]------");
			}
		}
		
		int TP = matched_count;
		
		if(print_detail)
			System.out.println("True Positive = " + TP);
		int TP_FP = retrieved_count;
		
		if(print_detail)
			System.out.println("TP + FP = " + TP_FP);
		int TP_FN = CTD_ground_truth_Chemical_CASID_list.size();
		int FN = CTD_ground_truth_Chemical_CASID_list.size()-TP;
		int FP = TP_FP - TP;
		double FDR = (FP+0.00)/(FP+TP+0.00);
		if(print_detail)
			System.out.println("TP + FN = " + TP_FN );
		double precision =  (TP+0.00)/(TP_FP+0.00);
		double recall =  (TP+0.00)/(TP_FN+0.00);
		double F1_measure = 2*precision*recall/(precision+recall);
		
		
		System.out.printf("%d\t%.3f\t%.3f\n" , 3,  precision , recall) ;
		
		
		if(print_detail) {
			System.out.printf("FACTA+ Precision = %.3f \n" , precision ) ;
			System.out.printf("FACTA+ Recall (sensitivity) = %.3f \n" , recall ) ;
			System.out.printf("FACTA+ F1_measure = %.3f \n" ,  F1_measure) ;
			System.out.printf("FACTA+ FDR = %.3f \n" ,  FDR) ;
			System.out.println("--------------------------------------------------------------------\n");
		}
		
	} catch ( IOException e) {
		e.printStackTrace();
		
	}

}//===================================< end of method >=================================================







	

	
}

