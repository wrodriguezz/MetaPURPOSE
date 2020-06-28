import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class processor_CTD_chemical_disease_files {

	public static void main(String[] args) {
		
		boolean run_process_CTD_chemicals_diseases_tsv_gz_using_MESHID = true;
		
		
		//liver, lung, kidney, plasma or serum
		String query_term_1 = null;
		String query_term_2 = null;
		String query_term_3 = null;	
		
		
		query_term_1 = "cardiovascular";//2018-02-06
		query_term_1 = "liver";//2018-02-06
		query_term_1 = "lung";//2018-02-06
		query_term_1 = "cancer";//2018-02-06
		query_term_1 = "kidney";//2018-02-06
		
		
		
		for(int query_disease = 1; query_disease <= 5; query_disease++) {
			
			
			if(query_disease == 1) {
				query_term_1 = "liver";//2018-02-06
				
			}
			if(query_disease == 2) {
				query_term_1 = "lung";//2018-02-06
				
			}
			if(query_disease == 3) {
				query_term_1 = "kidney";//2018-02-06
				
			}
			if(query_disease == 4) {
				query_term_1 = "cardiovascular";//2018-02-06
				
			}
			if(query_disease == 5) {
				query_term_1 = "cancer";//2018-02-06
			}
		
		
			System.out.println("------------[Query term = " + query_term_1 + "]------------------");
			String directoryAndName_MESH_mtrees2018 = "./data/myMESH/mtrees2018.bin";
			String directoryAndName_CTD_chemicals_diseases_tsv_gz = "./data/myCTD/CTD_chemicals_diseases.tsv.gz";

			String directoryAndName_CTD_UNIQUE_ChemicalName_of_disease  = "./data/myCTD/CTD_UniqueChemicalName_DirectEvidence_MESHID_" + query_term_1;
			String directoryAndName_CTD_UNIQUE_ChemicalID_of_disease  = "./data/myCTD/CTD_UniqueChemicalID_DirectEvidence_MESHID_" + query_term_1;
			
			if(run_process_CTD_chemicals_diseases_tsv_gz_using_MESHID) {
				process_CTD_chemicals_diseases_tsv_gz_select_DirectEvidence_MESHID
				(query_term_1, query_term_2, directoryAndName_MESH_mtrees2018, 
						directoryAndName_CTD_chemicals_diseases_tsv_gz, 
						directoryAndName_CTD_UNIQUE_ChemicalName_of_disease,
						directoryAndName_CTD_UNIQUE_ChemicalID_of_disease);
			}
			

		}
		
		
		
		String directoryAndName_CTD_chemicals_diseases_tsv_gz = "./data/myCTD/CTD_chemicals_diseases.tsv.gz";
		String directoryAndName_CTD_UNIQUE_chemicals_of_disease = "./data/myCTD/CTD_UniqueChemicalList_DirectEvidence_MESHID_" + query_term_1;
		
		
		boolean run = false;
		if(run){
			
			for(int query_disease = 1; query_disease <= 5; query_disease++) {
			
				
				if(query_disease == 1) {
					query_term_1 = "liver";//2018-02-06
					
				}
				if(query_disease == 2) {
					query_term_1 = "lung";//2018-02-06
					
				}
				if(query_disease == 3) {
					query_term_1 = "kidney";//2018-02-06
					
				}
				if(query_disease == 4) {
					query_term_1 = "cardiovascular";//2018-02-06
					
				}
				if(query_disease == 5) {
					query_term_1 = "cancer";//2018-02-06
				}
				
				
				directoryAndName_CTD_chemicals_diseases_tsv_gz = "./data/myCTD/CTD_chemicals_diseases.tsv.gz";
				String directoryAndName_CTD_chemicals2diseases = null;
				if(query_term_1 != null &&  query_term_2 == null && query_term_3 == null){
					System.out.println(query_term_1);
					directoryAndName_CTD_chemicals2diseases = "./data/myCTD/CTD_chemical2disease_" + query_term_1;
				}
				else if(query_term_1 != null && query_term_2 != null && query_term_3 == null){
					System.out.println(query_term_1  + "\t" + query_term_2);
					directoryAndName_CTD_chemicals2diseases = "./data/myCTD/CTD_chemical2disease_" + query_term_1 + "_" + query_term_2;
				}
				else if(query_term_1 != null && query_term_2 != null && query_term_3 != null){
					System.out.println(query_term_1  + "\t" + query_term_2 + "\t" + query_term_3);
					directoryAndName_CTD_chemicals2diseases = "./data/myCTD/CTD_chemical2disease_" + query_term_1 + "_" + query_term_2 + "_" + query_term_3;
				}
				
				
				boolean process_CTD_chemical_diseases_tsv_gz = false;
				
				if(process_CTD_chemical_diseases_tsv_gz){
					process_CTD_chemical_diseases_tsv_gz(query_term_1, query_term_2, query_term_3, directoryAndName_CTD_chemicals_diseases_tsv_gz, directoryAndName_CTD_chemicals2diseases);
				}
				
				boolean run_get_unique_Chemical_list_from_CTD_tsv_gz = false;
				//2018-10-18 don't use this one (for uniqye ChemicalID list ONLY not for CasRN ID List
				if(run_get_unique_Chemical_list_from_CTD_tsv_gz) {
					String directoryAndName_CTD_UNIQUE_Chemical = null;
					if(query_term_2 == null){
						directoryAndName_CTD_UNIQUE_Chemical = "./data/myCTD/CTD_UniqueChemicalIDList_" + query_term_1;
					}
					else if(query_term_2 != null && query_term_3 == null){
						directoryAndName_CTD_UNIQUE_Chemical = "./data/myCTD/CTD_UniqueChemicalIDList_" + query_term_1 + "_" + query_term_2;
					}
					else if(query_term_2 != null && query_term_3 != null){
						directoryAndName_CTD_UNIQUE_Chemical = "./data/myCTD/CTD_UniqueChemicalIDList_" + query_term_1 + "_" + query_term_2 + "_" + query_term_3;
					}
					double InferenceScoreFilter = 100.0;
					
					get_unique_Chemical_list_from_CTD_tsv_gz(directoryAndName_CTD_chemicals2diseases,
							directoryAndName_CTD_UNIQUE_Chemical, InferenceScoreFilter);
				
				}
				
				
				boolean get_unique_CasRN_list_and_ChemicalID_from_CTD_tsv_gz = true;
				//2018-01-18 for both CasRN_list_and_ChemicalID
				if(get_unique_CasRN_list_and_ChemicalID_from_CTD_tsv_gz) {
					
					String directoryAndName_CTD_UNIQUE_CasRN = null;
					if(query_term_2 == null){
						directoryAndName_CTD_UNIQUE_CasRN = "./data/myCTD/CTD_UniqueCasRNList_" + query_term_1;
					}
					else if(query_term_2 != null && query_term_3 == null){
						directoryAndName_CTD_UNIQUE_CasRN = "./data/myCTD/CTD_UniqueCasRNList_" + query_term_1 + "_" + query_term_2;
					}
					else if(query_term_2 != null && query_term_3 != null){
						directoryAndName_CTD_UNIQUE_CasRN = "./data/myCTD/CTD_UniqueCasRNList_" + query_term_1 + "_" + query_term_2 + "_" + query_term_3;
					}
					
					
					String directoryAndName_CTD_UNIQUE_ChemicalID = null;
					if(query_term_2 == null){
						directoryAndName_CTD_UNIQUE_ChemicalID = "./data/myCTD/CTD_UniqueChemicalIDList_" + query_term_1;
					}
					else if(query_term_2 != null && query_term_3 == null){
						directoryAndName_CTD_UNIQUE_ChemicalID = "./data/myCTD/CTD_UniqueChemicalIDList_" + query_term_1 + "_" + query_term_2;
					}
					else if(query_term_2 != null && query_term_3 != null){
						directoryAndName_CTD_UNIQUE_ChemicalID = "./data/myCTD/CTD_UniqueChemicalIDList_" + query_term_1 + "_" + query_term_2 + "_" + query_term_3;
					}
					
					
					double InferenceScoreFilter = 100.0;
					
					get_unique_CasRN_list_and_ChemicalID_from_CTD_tsv_gz(
							directoryAndName_CTD_chemicals2diseases,
							directoryAndName_CTD_UNIQUE_ChemicalID,
							directoryAndName_CTD_UNIQUE_CasRN, 
							InferenceScoreFilter);
				
				}
				
				
				System.out.println("---------------------------------------------------------");
			}
				
		}// ENd of for loop ================================================
		
		
	
	}
	
	
	
	
	
	/********************************************************
	 * Date: 2018-01-18
	 * Method: get_unique_CasRN_list_from_CTD_tsv_gz()
	 * 
	 ********************************************************/

	public static void get_unique_CasRN_list_and_ChemicalID_from_CTD_tsv_gz(
    		String directoryAndName_CTD_Chemical2diseases,
    		String directoryAndName_CTD_UNIQUE_ChemicalID,
    		String directoryAndName_CTD_UNIQUE_CasRN,
    		double InferenceScoreFilter){

		
		File CTD_Chemical2diseases_inputFile = 
				new File(directoryAndName_CTD_Chemical2diseases); 
		
		
		ArrayList<String> CTD_UNIQUE_ChemicalID_dataList = new ArrayList<String>();
		ArrayList<String> CTD_UNIQUE_CasRN_dataList = new ArrayList<String>();
		
		
		if (CTD_Chemical2diseases_inputFile.exists()){
			
			try{
				
				BufferedReader in_Chemical2diseases = new BufferedReader(
						new FileReader(CTD_Chemical2diseases_inputFile));
				String temp = null;
				String[] token_array = null;
				int count = 0;
				while ((temp = in_Chemical2diseases.readLine()) != null) {
					count++;
					token_array = temp.split("\t");
					if(token_array.length >= 5){
						
						
						if(token_array[1].length() > 1 && !CTD_UNIQUE_ChemicalID_dataList.contains(token_array[1])){
							CTD_UNIQUE_ChemicalID_dataList.add(token_array[1]);
							CTD_UNIQUE_CasRN_dataList.add(token_array[2]);
						}
						
						
						//if(token_array[2].length() > 1 && !CTD_UNIQUE_CasRN_dataList.contains(token_array[2])){
						//	CTD_UNIQUE_CasRN_dataList.add(token_array[2]);
						//}
						
						
						//if(token_array[2].length() > 1 && !CTD_UNIQUE_ChemicalID_dataList.contains(token_array[1])){
						//	CTD_UNIQUE_CasRN_dataList.add(token_array[2]);
						//	CTD_UNIQUE_ChemicalID_dataList.add(token_array[1]);
						//}
					}
					
					
					//if(count%1000==0){
					//	System.out.println(count);
					//}
				}
				
				
				System.out.println("CTD_UNIQUE_ChemicalID_dataList.size() = "
						+ CTD_UNIQUE_ChemicalID_dataList.size());
				System.out.println("CTD_UNIQUE_CasRN_dataList.size() = "
				+ CTD_UNIQUE_CasRN_dataList.size());
				
				if(in_Chemical2diseases!=null){
					in_Chemical2diseases.close();
				}
				
				BufferedWriter out_UNIQUE_ChemicalID 
				= new BufferedWriter(
						new FileWriter(directoryAndName_CTD_UNIQUE_ChemicalID));

				for(int i = 0; i < CTD_UNIQUE_ChemicalID_dataList.size(); i++){
					out_UNIQUE_ChemicalID.write(CTD_UNIQUE_ChemicalID_dataList.get(i) + "\n");
				}
				
				if(out_UNIQUE_ChemicalID!=null){
					out_UNIQUE_ChemicalID.close();
				}
				
				
				BufferedWriter out_UNIQUE_CasRN  
				= new BufferedWriter(
						new FileWriter(directoryAndName_CTD_UNIQUE_CasRN));

				for(int i = 0; i < CTD_UNIQUE_CasRN_dataList.size(); i++){
					out_UNIQUE_CasRN.write(CTD_UNIQUE_CasRN_dataList.get(i) + "\n");
				}
				
				if(out_UNIQUE_CasRN!=null){
					out_UNIQUE_CasRN.close();
				}
				
			}catch ( IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	public static void get_unique_Chemical_list_from_CTD_tsv_gz(
    		String directoryAndName_CTD_Chemical2diseases,
    		String directoryAndName_CTD_UNIQUE_Chemical,
    		double InferenceScoreFilter){

		
		File CTD_Chemical2diseases_inputFile = 
				new File(directoryAndName_CTD_Chemical2diseases); 
		
		ArrayList<String> CTD_UNIQUE_Chemical_dataList = new ArrayList<String>();
		
		
		if (CTD_Chemical2diseases_inputFile.exists()){
			
			try{
				
				BufferedReader in_Chemical2diseases = new BufferedReader(
						new FileReader(CTD_Chemical2diseases_inputFile));
				String temp = null;
						//in_Chemical2diseases.readLine();//skip first line ? why?
				String[] token_array = null;
				int count = 0;
				while ((temp = in_Chemical2diseases.readLine()) != null) {
					count++;
					token_array = temp.split("\t");
					if(token_array.length >= 5){
						if(!CTD_UNIQUE_Chemical_dataList.contains(token_array[1])){
							CTD_UNIQUE_Chemical_dataList.add(token_array[1]);
						}
					}
					
					
					if(count%1000==0){
						System.out.println(count);
					}
				}
				
				System.out.println("CTD_UNIQUE_Chemical_dataList.size() = "
				+ CTD_UNIQUE_Chemical_dataList.size());
				
				if(in_Chemical2diseases!=null){
					in_Chemical2diseases.close();
				}
				
				BufferedWriter out_UNIQUE_Chemical  
				= new BufferedWriter(
						new FileWriter(directoryAndName_CTD_UNIQUE_Chemical));

				for(int i = 0; i < CTD_UNIQUE_Chemical_dataList.size(); i++){
					out_UNIQUE_Chemical.write(CTD_UNIQUE_Chemical_dataList.get(i) + "\n");
				}
				
				if(out_UNIQUE_Chemical!=null){
					out_UNIQUE_Chemical.close();
				}
				
			}catch ( IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
	
	
	
	/*************************************************************************************
	@method process_CTD_chemicals_diseases_tsv_gz_select_DirectEvidence_MESHID
	@param String query_term_1
    @param String query_term_2
    @param String directoryAndName_MESH_mtrees2018
	@param String directoryAndName_CTD_chemicals_diseases_tsv_gz
    @param String directoryAndName_CTD_chemicals2diseases_DirectEvidence_MESHID
	@version 2018-02-06
	 **************************************************************************************/

	public static void process_CTD_chemicals_diseases_tsv_gz_select_DirectEvidence_MESHID(
			String query_term_1,
    		String query_term_2,
    		String directoryAndName_MESH_mtrees2018,
			String directoryAndName_CTD_chemicals_diseases_tsv_gz,
    		String directoryAndName_CTD_UNIQUE_ChemicalName_of_disease,
    		String directoryAndName_CTD_UNIQUE_ChemicalID_of_disease
    		){

		
		
		try {
			
				String temp = null;
				String[] token_array = null;

		    	File CTD_genes_diseases_tsv_gz_inputFile = 
						new File(directoryAndName_CTD_chemicals_diseases_tsv_gz); 
				
		    	
		    	ArrayList<String> MESH_diseaseName_TargetDisease_dataList = new ArrayList<String>();
		    	ArrayList<String> MESH_diseaseMeSH_treeNumber_TargetDisease_dataList = new ArrayList<String>();
		    	
				
				String[] MESHID_array = new String[10] ;
				String[] exclude_MESHID_array = new String[10] ;
				int filter_MeSHTreeNumber = 0;
				int exclude_MeSHTreeNumber = 0;
				
				if(query_term_1.equals("liver")) {
					//<Method 2>
					//Anything under MeSH C06.552
		
					System.out.println("************************************");
					System.out.println("***********           liver                *******");
					System.out.println("************************************");
					
					MESHID_array[0] = "C06.552";
					filter_MeSHTreeNumber = 1;
				}
				else if(query_term_1.equals("lung")) {
					//<Method 2>
					//Anything under MeSH C08.381

		
					System.out.println("************************************");
					System.out.println("***********           lung                *******");
					System.out.println("************************************");
					
					MESHID_array[0] = "C08.381";
					filter_MeSHTreeNumber = 1;
				}
				else if(query_term_1.equals("kidney")) {
					//<Method 2>
					//Anything under MeSH C12.777.419
					//Anything under MeSH C13.351.968.419


		
					System.out.println("************************************");
					System.out.println("***********           kidney                *******");
					System.out.println("************************************");
					
					MESHID_array[0] = "C12.777.419";
					MESHID_array[1] = "C13.351.968.419";
					filter_MeSHTreeNumber = 2;
				}
				else if(query_term_1.equals("cardiovascular")) {
					//<Method 2>
					//Anything under MeSH C14

					System.out.println("************************************");
					System.out.println("***********           cardiovascular                *******");
					System.out.println("************************************");
					
					MESHID_array[0] = "C14";
					filter_MeSHTreeNumber = 1;
				}
				else if(query_term_1.equals("cancer")) {
					//<Method 2> (caveat: many terms under ¡§neoplasm¡¨ are benign)
					//Anything under MeSH C04 (exclude C04.182, C04.445, C04.834)


					System.out.println("************************************");
					System.out.println("***********           cancer            *******");
					System.out.println("************************************");
					
					MESHID_array[0] = "C04";
					exclude_MESHID_array[0] = "C04.182";
					exclude_MESHID_array[1] = "C04.445";
					exclude_MESHID_array[2] = "C04.834";
					filter_MeSHTreeNumber = 1;
					exclude_MeSHTreeNumber = 3;
				}
				
				
				File inputFile_MESH_mtrees2018  = new File(directoryAndName_MESH_mtrees2018);
				System.out.println(directoryAndName_MESH_mtrees2018);
	
			
				BufferedReader in_genes2diseases = new BufferedReader(
						new FileReader(inputFile_MESH_mtrees2018));
				
				int i,j = 0;
				int exclude_count = 0;
				temp = null;
				token_array = null;
				while ((temp = in_genes2diseases.readLine()) != null) {	
					token_array = temp.split(";");
					exclude_count = 0;
					
					if(token_array.length == 2) {
						
						
						for(i = 0; i < filter_MeSHTreeNumber; i++) {
							
							if(token_array[1].indexOf(MESHID_array[i])!=-1) {
								
								if(exclude_MeSHTreeNumber>0) {
									for(j = 0; j < exclude_MeSHTreeNumber; j++) {
										if(token_array[1].indexOf(exclude_MESHID_array[j])!=-1) {
											exclude_count++;
										}
									}
									
									if(exclude_count == 0) {
										MESH_diseaseName_TargetDisease_dataList.add(token_array[0]);
										MESH_diseaseMeSH_treeNumber_TargetDisease_dataList.add(token_array[1]);
									}
								}
								else {
									MESH_diseaseName_TargetDisease_dataList.add(token_array[0]);
									MESH_diseaseMeSH_treeNumber_TargetDisease_dataList.add(token_array[1]);
								}
							}
						}
					}
					else {
						System.out.println("record size different from 2");
					}
				}
				
				System.out.println("===============================================");
				
				for(i = 0; i < MESH_diseaseName_TargetDisease_dataList.size(); i++) {
					System.out.print(MESH_diseaseName_TargetDisease_dataList.get(i) + "\t");
					System.out.println(MESH_diseaseMeSH_treeNumber_TargetDisease_dataList.get(i));
				}
				
				System.out.println("MESH_diseaseName_TargetDisease_dataList.size() = " + MESH_diseaseName_TargetDisease_dataList.size());
				System.out.println("MESH_diseaseMeSH_treeNumber_TargetDisease_dataList.size() = " + MESH_diseaseMeSH_treeNumber_TargetDisease_dataList.size());
				System.out.println("===============================================");
				
				
				
				
				ArrayList<String> CTD_UNIQUE_ChemicalName_dataList = new ArrayList<String>();
				ArrayList<String> CTD_UNIQUE_ChemicalID_dataList = new ArrayList<String>();
				
				
					
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
								new GZIPInputStream(
										new FileInputStream(CTD_genes_diseases_tsv_gz_inputFile))));
				
				
				
				
				/*********************************************************
				 * http://ctdbase.org/downloads/;jsessionid=73EAA5D8536CE9E9066BA6527C3859C4#cd
				Fields:
					ChemicalName
					ChemicalID (MeSH identifier)
					CasRN (CAS Registry Number, if available)
					DiseaseName
					DiseaseID (MeSH or OMIM identifier)
					DirectEvidence ('|'-delimited list)
					InferenceGeneSymbol
					InferenceScore
					OmimIDs ('|'-delimited list)
					PubMedIDs ('|'-delimited list)
				*********************************************************/
				
				String[] token_string = null;
				temp = null;
				temp = in.readLine();
				boolean pass_header = false;
				
				
				int count = 0;
				int count_DirectEvidence = 0;
				while ((temp = in.readLine()) != null) {	
					if(count <= 10000){
						System.out.println(temp);
					}
					
					count++;
					token_string = temp.split("\t");
					//System.out.println(token_string.length);
					
					
					if(!pass_header && token_string.length == 10) {
						
						if(token_string[7].equals("InferenceScore")) {
							pass_header = true;
						}
					}
					else if(pass_header && token_string.length == 10){

						
						if(token_string[5].length() > 0) {//select DirectEvidence only
							if(MESH_diseaseName_TargetDisease_dataList.contains(token_string[3])){
								if(!CTD_UNIQUE_ChemicalID_dataList.contains(token_string[1])) {
									
									CTD_UNIQUE_ChemicalID_dataList.add(token_string[1]);
									CTD_UNIQUE_ChemicalName_dataList.add(token_string[0]);
									
									//System.out.println(temp);
								}
							}

						}

					}
					
					if(count%1000000==0){
						System.out.println(count);
						
					}
					
				}//end of while loop

				if(in!=null) {
					in.close();
				}
				
				
				System.out.println("CTD_UNIQUE_ChemicalName_dataList.size() = " + CTD_UNIQUE_ChemicalName_dataList.size());
				
				
				
				BufferedWriter out_UNIQUE_ChemicalName_of_disease  
				= new BufferedWriter(
						new FileWriter(directoryAndName_CTD_UNIQUE_ChemicalName_of_disease));

				for(i = 0; i < CTD_UNIQUE_ChemicalName_dataList.size(); i++){
					
					out_UNIQUE_ChemicalName_of_disease.write(CTD_UNIQUE_ChemicalName_dataList.get(i) + "\n");
						System.out.println(CTD_UNIQUE_ChemicalName_dataList.get(i)+ "\t" + CTD_UNIQUE_ChemicalID_dataList.get(i));
				}
				
				if(out_UNIQUE_ChemicalName_of_disease!=null){
					out_UNIQUE_ChemicalName_of_disease.close();
				}
				
				
				
				BufferedWriter out_UNIQUE_ChemicalID_of_disease  
				= new BufferedWriter(
						new FileWriter(directoryAndName_CTD_UNIQUE_ChemicalID_of_disease));

				for(i = 0; i < CTD_UNIQUE_ChemicalName_dataList.size(); i++){
					
					out_UNIQUE_ChemicalID_of_disease.write(CTD_UNIQUE_ChemicalID_dataList.get(i) + "\n");
						System.out.println(CTD_UNIQUE_ChemicalName_dataList.get(i)+ "\t" + CTD_UNIQUE_ChemicalID_dataList.get(i));
				}
				
				if(out_UNIQUE_ChemicalID_of_disease!=null){
					out_UNIQUE_ChemicalID_of_disease.close();
				}
					
					
			}catch ( IOException e) {
				e.printStackTrace();
			}
				
				
				
			
		}//method: process_CTD_chemicals_csv_gz
		
	
	
	
	
	
	

	
	
	
	
	
	public static void process_CTD_chemical_diseases_tsv_gz(
    		String query_term_1,
    		String query_term_2,
    		String query_term_3,
			String directoryAndName_CTD_chemical_diseases_tsv_gz,
    		String directoryAndName_CTD_chemical2diseases){

    	File CTD_chemical_diseases_tsv_gz_inputFile = 
				new File(directoryAndName_CTD_chemical_diseases_tsv_gz); 
		
    	
    	ArrayList<String> CTD_ChemicalName_dataList = new ArrayList<String>();
    	ArrayList<String> CTD_ChemicalID_dataList = new ArrayList<String>();
    	ArrayList<String> CTD_CasRN_dataList = new ArrayList<String>();
		ArrayList<String> CTD_DiseaseName_dataList = new ArrayList<String>();
		ArrayList<String> CTD_DiseaseID_dataList = new ArrayList<String>();
		
		ArrayList<String> CTD_DirectEvidence_dataList = new ArrayList<String>();
		ArrayList<String> CTD_InferenceScore_dataList = new ArrayList<String>();
		//ArrayList<String> CTD_Synonyms_dataList = new ArrayList<String>();
		
		
		if (CTD_chemical_diseases_tsv_gz_inputFile.exists()){
		
			try{
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
								new GZIPInputStream(
										new FileInputStream(CTD_chemical_diseases_tsv_gz_inputFile))));
				
				
				/*********************************************************
				CTD_chemicals_diseases.tsv.gz------------------------------------------------
				Fields:
				ChemicalName
				ChemicalID (MeSH identifier)
				CasRN (CAS Registry Number, if available)
				DiseaseName
				DiseaseID (MeSH or OMIM identifier)
				DirectEvidence ('|'-delimited list)
				InferenceGeneSymbol
				InferenceScore
				OmimIDs ('|'-delimited list)
				PubMedIDs ('|'-delimited list)
				*********************************************************/
				
				String[] token_string = null;
				String temp = null;
				temp = in.readLine();
				
				
				int count = 0;
				while ((temp = in.readLine()) != null) {	
					if(count <= 1000){
						System.out.println("temp = " + temp);
					}
					
					count++;
					token_string = temp.split("\t");
					//System.out.println(token_string.length);
					
					if(token_string.length >= 9){
						
						
						
						if(token_string[7].length() == 0){
							
							System.out.println(token_string[5]);
							
							if(query_term_2 == null && query_term_3 == null){
								if(token_string[3].toLowerCase().indexOf(query_term_1)!=-1){
									CTD_ChemicalName_dataList.add(token_string[0]);
									CTD_ChemicalID_dataList.add(token_string[1]);
									CTD_CasRN_dataList.add(token_string[2]);
									CTD_DiseaseName_dataList.add(token_string[3]);
									CTD_DiseaseID_dataList.add(token_string[4]);
									CTD_DirectEvidence_dataList.add(token_string[5]);
									CTD_InferenceScore_dataList.add(token_string[7]);
								}
							}
							else if(query_term_2 != null && query_term_3 == null){
								if(token_string[3].toLowerCase().indexOf(query_term_1)!=-1
									|| token_string[3].toLowerCase().indexOf(query_term_2)!=-1){
									CTD_ChemicalName_dataList.add(token_string[0]);
									CTD_ChemicalID_dataList.add(token_string[1]);
									CTD_CasRN_dataList.add(token_string[2]);
									CTD_DiseaseName_dataList.add(token_string[3]);
									CTD_DiseaseID_dataList.add(token_string[4]);
									CTD_DirectEvidence_dataList.add(token_string[5]);
									CTD_InferenceScore_dataList.add(token_string[7]);
								}
							}
							else if(query_term_2 != null && query_term_3 != null){
								if(token_string[3].toLowerCase().indexOf(query_term_1)!=-1
									|| token_string[3].toLowerCase().indexOf(query_term_2)!=-1
									|| token_string[3].toLowerCase().indexOf(query_term_3)!=-1){
									CTD_ChemicalName_dataList.add(token_string[0]);
									CTD_ChemicalID_dataList.add(token_string[1]);
									CTD_CasRN_dataList.add(token_string[2]);
									CTD_DiseaseName_dataList.add(token_string[3]);
									CTD_DiseaseID_dataList.add(token_string[4]);
									CTD_DirectEvidence_dataList.add(token_string[5]);
									CTD_InferenceScore_dataList.add(token_string[7]);
								}
							}
						}//
					}
					
					if(count%100000==0){
						System.out.println(count);
					}
					
				}//end of while loop
				
				

				System.out.println("CTD_ChemicalName_dataList.size() = " +  CTD_ChemicalName_dataList.size());
				System.out.println("CTD_DiseaseName_dataList.size() = " + CTD_DiseaseName_dataList.size());
				System.out.println("CTD_InferenceScore_dataList.size() = " + CTD_InferenceScore_dataList.size());
				
				
				
				BufferedWriter out_CTD_chemical2diseases  
				= new BufferedWriter(
						new FileWriter(directoryAndName_CTD_chemical2diseases));
				
				System.out.println("------------------ writing CTD_ChemicalName, ID CasRN DiseaseName and ID to file ----------------");
				
				for(int i = 0; i < CTD_ChemicalName_dataList.size(); i++) {
					
					out_CTD_chemical2diseases.write(
							CTD_ChemicalName_dataList.get(i)
							+ "\t" + CTD_ChemicalID_dataList.get(i)
							+ "\t" + CTD_CasRN_dataList.get(i)
							+ "\t" + CTD_DiseaseName_dataList.get(i)
							+ "\t" + CTD_DiseaseID_dataList.get(i)
							+ "\t" + CTD_InferenceScore_dataList.get(i)
							+ "\t" + CTD_DirectEvidence_dataList.get(i) + "\n");
				}
				
				if(out_CTD_chemical2diseases!=null){
					out_CTD_chemical2diseases.close();//close writer
				}
				
				System.out.println("------------------ finished writing CTD_ChemicalName, ID CasRN DiseaseName and ID to file ----------------");
				
				
				if(in!=null) {
					in.close();
				}
				
				
			}catch ( IOException e) {
				e.printStackTrace();
			}
		}
	}//method: process_CTD_chemicals_csv_gz
		

}
