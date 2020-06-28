/*************************************************************************************
@Author Michael T.-L. Lee @Tainan, Taiwan
@email: michaelee0407@gmail.com
@version 2018-03-25
 *************************************************************************************/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;
 
//2017-11-21
//@Author Michael T.-L. Lee @Tainan, Taiwan
//email: michaelee0407@gmail.com
public class PURPOSE_chem2pubtator {


	private ArrayList<Integer> matched_Pair_chemIDList_TOP100 = null;
	private ArrayList<Integer> matched_Pair_PMIDList_TOP100 = null;
	private int count_Big_F = 0;
	private int count_Big_T = 0;
	private int count_Big_P = 0;
	private int count_T_intersect_P = 0;
	
	
	private File total_citation_Count_per_query_topic = null;
	private BufferedWriter out_total_citation_Count_per_query_topic = null;

	

	public  PURPOSE_chem2pubtator(String QueryTerm, String species){
        
		try{

			
			species = species.toLowerCase();
			String[] species_array = {"human","mouse","rat","fly","worm","yeast"};
			int num_of_speices = 6;
			
			if(species.toLowerCase().equals("all")){
				num_of_speices = 6;
			}
			else{
				num_of_speices = 1;
				species_array[0] = species;
			}

			for(int sp = 0; sp < num_of_speices; sp++){
			
				boolean computePURPOSE = true;

				QueryTerm = QueryTerm.toLowerCase();
				String root_directory = "./PURPOSE_chemical_Results/" + species_array[sp] + "/";
				
				File statistic_directory =  new File("./PURPOSE_chemical_Results/" + species_array[sp] + "/statistic/");
				if(!statistic_directory.exists()){
					statistic_directory.mkdirs();
				}
				
				total_citation_Count_per_query_topic = new File("./PURPOSE_chemical_Results/" + species_array[sp] + "/statistic/"+"Total_Citation_Count_"+QueryTerm);
				out_total_citation_Count_per_query_topic = new BufferedWriter(
						new FileWriter(total_citation_Count_per_query_topic));
				
			
				
				String fileDirAndName_total_unique_PMIDs_CitationYear = "";
				String fileDirAndName_chem2pubtator_chem2Count = "";
				String fileDirAndName_chem2pubtator_PMID_Sort = "";
				String fileDirAndName_chemical2pubtator_with_chemID = "";
				
				String entrez_esearch_db = "pubmed";
		    	String entrez_esearch_retmax = "5000000";
		    	int  entrez_esearch_retstart = 0;
				
				URL url = new URL("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?"
						+"db="+entrez_esearch_db
						+"&term="+species_array[sp]
						+"&retmax="+entrez_esearch_retmax
						+"&retstart="+ entrez_esearch_retstart
						);
				
				URLConnection connect = url.openConnection();
				BufferedReader in_F = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
				
				String temp = null;
				boolean done_parse_PMID_Count = false;
				int PMID_count = 0;
				
				while ((temp = in_F.readLine()) != null && !done_parse_PMID_Count) {
					if(!done_parse_PMID_Count){
						if(temp.indexOf("<Count>") != -1){
							PMID_count = Integer.parseInt(temp.substring(temp.indexOf(">")+1, temp.indexOf("</Count>")));
							
							done_parse_PMID_Count = true;
						}
					}
				}
				
				if(in_F!= null){
					in_F.close();
				}
				
				count_Big_F = PMID_count;
				System.out.println("species="+species_array[sp] + "-> count_Big_F=" + count_Big_F);
				out_total_citation_Count_per_query_topic.write("F=" + count_Big_F + "\n");
				

				fileDirAndName_total_unique_PMIDs_CitationYear = "./data/myPubtator/total_unique_PMIDs_9606_CitationYear";
				fileDirAndName_chem2pubtator_chem2Count = "./data/myChem2Pubtator/chem2pubtator_chem2count";
				fileDirAndName_chem2pubtator_PMID_Sort = "./data/myChem2Pubtator/chem2pubtator_PMID_sort";
				//fileDirAndName_chemical2pubtator_with_chemID = "./data/myChem2Pubtator/chemical2pubtator_with_chemID_ChemName"; //updated-2018-02-15
				fileDirAndName_chemical2pubtator_with_chemID = "./data/myChem2Pubtator/chemical2pubtator_with_chemID"; //updated-2018-02-16
				
				
				if(species_array[sp].equals("human")){
					fileDirAndName_total_unique_PMIDs_CitationYear = "./data/myPubtator/total_unique_PMIDs_chem2Pubtator_9606_CitationYear";
				}
				else if(species_array[sp].equals("mouse")){
					fileDirAndName_total_unique_PMIDs_CitationYear = "./data/myPubtator/total_unique_PMIDs_chem2Pubtator_10090_CitationYear";
				}
				else if(species_array[sp].equals("fly")){
					fileDirAndName_total_unique_PMIDs_CitationYear = "./data/myPubtator/total_unique_PMIDs_chem2Pubtator_7227_CitationYear";		
				}
				else if(species_array[sp].equals("worm")){
					fileDirAndName_total_unique_PMIDs_CitationYear = "./data/myPubtator/total_unique_PMIDs_chem2Pubtator_6239_CitationYear";
				}
				else if(species_array[sp].equals("yeast")){
					fileDirAndName_total_unique_PMIDs_CitationYear = "./data/myPubtator/total_unique_PMIDs_chem2Pubtator_4932_CitationYear";
				}
				else if(species_array[sp].equals("rat")){
					fileDirAndName_total_unique_PMIDs_CitationYear = "./data/myPubtator/total_unique_PMIDs_chem2Pubtator_10116_CitationYear";
				}
				else{
					computePURPOSE = false;
					System.out.println("Sorry, current platform is not handling selected species");
				}
				
				
				
				

				if(computePURPOSE) {
		
					if(QueryTerm.toLowerCase().equals("hpp22")){
				
						String[] hpp22_topics = {
								"brain",
								"cancer", 
								"cardiovascular", 
								"diabetes",
								"hot+OR+cold+OR+alkaline+condition+OR+acidic+condition+OR+hypersaline+OR+radiation",
								"eye+OR+ocular",
								"food+OR+nutrition+OR+nutrients",
								"glycoproteins", 
								"immune+OR+immune+system",
								"infectious+OR+infection",
								"kidney+OR+urine",
								"liver+OR+hepatic",
								"mitochondria",
								"rat+OR+mouse",
								"muscle+OR+bone+OR+musculoskeletal",
								"pathology",
								"pediatric+OR+newborn+OR+infant+OR+toddler+OR+child+OR+adolescent",
								"plasma+OR+serum",
								"protein+aggregation",
								"rheumatic",
								"respiratory",
								"stem+cells",
								"toxicology+OR+toxic+OR+toxin"
						};	
						
						
						
						


						for(int i = 0; i < hpp22_topics.length; i++){
							
							out_total_citation_Count_per_query_topic.write(hpp22_topics[i] + "\t");
							
							String query_term_and_species = null;
							if(hpp22_topics[i].equals("rat+OR+mouse")) {
								query_term_and_species = hpp22_topics[i];
							}
							else {
								query_term_and_species = hpp22_topics[i]+"+AND+"+species_array[sp];
							}
							
							ComputePURPOSE_from_extractAbstractUsingEntrez_Esearch(
									query_term_and_species,  //
				        			root_directory, 
				        			fileDirAndName_total_unique_PMIDs_CitationYear,
				        			fileDirAndName_chem2pubtator_chem2Count,
				        			fileDirAndName_chem2pubtator_PMID_Sort,
				        			fileDirAndName_chemical2pubtator_with_chemID,
				        			count_Big_F);
							
							
							
						}//for(int i = 0; i < hpp22_topics.length; i++){
						
					}//if(QueryTerm.equals("hpp22")){
					else if(QueryTerm.toLowerCase().equals("hpp5")){
						String[] hpp5_topics = {
								"cancer", 
								"cardiovascular", 
								"kidney",
								"liver",
								"lung"
						};	
						
						for(int i = 0; i < hpp5_topics.length; i++){
							
							out_total_citation_Count_per_query_topic.write(hpp5_topics[i] + "\t");

							ComputePURPOSE_from_extractAbstractUsingEntrez_Esearch(
									hpp5_topics[i]+"+AND+"+species_array[sp],  //
				        			root_directory, 
				        			fileDirAndName_total_unique_PMIDs_CitationYear,
				        			fileDirAndName_chem2pubtator_chem2Count,
				        			fileDirAndName_chem2pubtator_PMID_Sort,
				        			fileDirAndName_chemical2pubtator_with_chemID,
				        			count_Big_F);
							
						}//for(int i = 0; i < hpp5_topics.length; i++){
					}//else if(QueryTerm.toLowerCase().equals("hpp5")){
					else{
						
						String query_term_and_species = null;
						if(QueryTerm.equals("rat+OR+mouse")) {
							query_term_and_species = QueryTerm;
						}
						else {
							query_term_and_species = QueryTerm+"+AND+"+species_array[sp];
						}

			        	ComputePURPOSE_from_extractAbstractUsingEntrez_Esearch(
			        			query_term_and_species,  //
			        			root_directory, 
			        			fileDirAndName_total_unique_PMIDs_CitationYear,
			        			fileDirAndName_chem2pubtator_chem2Count,
			        			fileDirAndName_chem2pubtator_PMID_Sort,
			        			fileDirAndName_chemical2pubtator_with_chemID,
			        			count_Big_F);
			        	
					}//else
						
				
				if(out_total_citation_Count_per_query_topic!=null){
					out_total_citation_Count_per_query_topic.close();
				}
			
        		
	
			}//for(int sp = 0; sp < num_of_speices; sp++){
			
				
			}//if(computePURPOSE)
			
			
        }catch(Exception e){
            e.printStackTrace();
        }
    }
	
	

	public void ComputePURPOSE_from_extractAbstractUsingEntrez_Esearch(
			String QueryTerm, 
			String root_directory, 
			String fileDirAndName_total_unique_PMIDs_CitationYear,
			String fileDirAndName_chem2pubtator_chem2Count,
			String fileDirAndName_chem2pubtator_PMID_Sort,
			String fileDirAndName_chemical2pubtator_with_chemID,
			int count_Big_F
	){
	
		
		long startTime = System.currentTimeMillis();
		
		matched_Pair_chemIDList_TOP100 = new ArrayList<Integer>();
		matched_Pair_PMIDList_TOP100 = new ArrayList<Integer>();
		
		
		File input_directory = new File(root_directory);
		if(!input_directory.exists()){       
			 input_directory.mkdirs();
	    }

		System.out.print("Calculating PURPOSE[chem2pubtator] Score [query="+QueryTerm+ "]");
		
		String temp = null;
		
		ArrayList<Integer> unique_queried_topic_pmidList = new ArrayList<Integer>();

		ArrayList<Integer> matched_PMIDList = new ArrayList<Integer>();
		ArrayList<Integer> matched_PMID_CitationList = new ArrayList<Integer>();
		ArrayList<Integer> matched_PMID_PubDateList = new ArrayList<Integer>();
		ArrayList<chem_data> chem_result_List = new ArrayList<chem_data>();
	
		
		
		//String query_url 
		//= "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=cancer+AND+human&retmax=1000000&retstart=2486433";
		
		String entrez_esearch_db = "pubmed";
    	String entrez_esearch_retmax = "5000000";
    	int  entrez_esearch_retstart = 0;

		//System.out.println("URL = " + "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?"
		//		+"\ndb="+entrez_esearch_db
		//		+"\n&term="+QueryTerm
		//		+"\n&retmax="+entrez_esearch_retmax
		//		+"\n&retstart="+ entrez_esearch_retstart);

		try{
			
			System.out.print(".");
			
			URL url = new URL("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?"
					+"db="+entrez_esearch_db
					+"&term="+QueryTerm
					+"&retmax="+entrez_esearch_retmax
					+"&retstart="+ entrez_esearch_retstart
					);
			
			//System.out.println(url.toString());
			
			URLConnection connect = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
			

			String pmid = null;
			boolean IdList_parsing_begin = false;
			boolean done_parse_PMID_Count = false;
			int IdList_index = -1;
			int PMID_count = 0;
			int current_PMID = 0;

			while ((temp = in.readLine()) != null) {
				
				//System.out.println(temp);

				if(!done_parse_PMID_Count){
					if(temp.indexOf("<Count>") != -1){
						PMID_count = Integer.parseInt(temp.substring(temp.indexOf(">")+1, temp.indexOf("</Count>")));
						//System.out.println("PMID_count =" + PMID_count);
						done_parse_PMID_Count = true;
					}
				}
				else if(!IdList_parsing_begin){
					IdList_index =  temp.indexOf("<IdList>");
					if(IdList_index != -1){
						IdList_parsing_begin = true;
					}
				}
				//To save total unique PMID list results into ArrayList
				else if(current_PMID < PMID_count){
					pmid = temp.substring(temp.indexOf(">")+1, temp.indexOf("</Id>"));	
					unique_queried_topic_pmidList.add(Integer.parseInt(pmid));
					current_PMID++;
				}
				
				
				//if(unique_queried_topic_pmidList.size()%10000 == 0){
				//	System.out.println(unique_queried_topic_pmidList.size());
				//}
				
			}//while
			
	
			if(in!=null){	
				in.close();
			}
				

		} catch (Exception e) {
			e.printStackTrace();
		}
	

		//=====================================================================================================================
		
		// This section is to compute the intersected PMID list between gene2pubtator PMID list and queried topic returned PMID lists
		// The trick to increase the computation time is take advantage of BIG array which was set to the size of the LARGEST PMID number.
		// The size need to be adjusted if LARGEST PMID is greater than the current setting 28753741(Date: 2017-08-19). 
				
		//=====================================================================================================================
		
		temp= null;
		String[] token_string = null;
		matched_PMIDList = new ArrayList<Integer>();
		matched_PMID_CitationList = new ArrayList<Integer>();
		matched_PMID_PubDateList = new ArrayList<Integer>();
		
		ArrayList<String> chem2pubtator_chemIDList = new ArrayList<String>();
		ArrayList<Integer> chem2pubtator_CountList = new ArrayList<Integer>();
		
		
final int TOTAL_PMID_ARRAY_SIZE = 40000001;
		
		int[] total_unique_PMID_Array = new int[TOTAL_PMID_ARRAY_SIZE]; //need to change accordingly
		int[] total_unique_PMID_Citation_Array = new int[TOTAL_PMID_ARRAY_SIZE]; //need to change accordingly
		int[] total_unique_PMID_PubDate_Array = new int[TOTAL_PMID_ARRAY_SIZE]; //need to change accordingly
		
		
		//File gene2pubtator_inputFile 
		//= new File(fileDirAndName_gene2pubtator);
		
		File total_unique_PMIDs_CitationYear_inputFile 
		= new File(fileDirAndName_total_unique_PMIDs_CitationYear);
		
		//System.out.println(gene2pubtator_total_unique_PMIDs_CitationYear_inputFile.getName()
		//	+" is exists()? = " + gene2pubtator_total_unique_PMIDs_CitationYear_inputFile.exists());

		if (total_unique_PMIDs_CitationYear_inputFile.exists()){
			try {
				BufferedReader in_total_unique_PMIDs_CitationYear = new BufferedReader(
						new FileReader(total_unique_PMIDs_CitationYear_inputFile));
				String[] token = null;
				while ((temp = in_total_unique_PMIDs_CitationYear.readLine()) != null) {
					token = temp.split("\t");
					total_unique_PMID_Array[Integer.parseInt(token[0])] = 1; //save a lot of Execution time this way
					total_unique_PMID_Citation_Array[Integer.parseInt(token[0])] = Integer.parseInt(token[1]); //save a lot of Execution time this way
					total_unique_PMID_PubDate_Array[Integer.parseInt(token[0])] = Integer.parseInt(token[2]); //save a lot of Execution time this way
				}
				
				if(in_total_unique_PMIDs_CitationYear!=null){
					in_total_unique_PMIDs_CitationYear.close();
				}

				//System.out.println("total_unique_PMID_Array.length = " + total_unique_PMID_Array.length);
				//System.out.println("--- ("+QueryTerm+") ===> "
				//+"================== start matching process ==============================");
				
				int current_count_T = 0;
				for(int t = 0; t < unique_queried_topic_pmidList.size(); t++) {	
					if(total_unique_PMID_Array[unique_queried_topic_pmidList.get(t)] == 1) {
						current_count_T++;
					}
				}
				count_Big_T = current_count_T;
				System.out.print(".count_Big_T="+count_Big_T);
				out_total_citation_Count_per_query_topic.write("T=" + count_Big_T + "\t");

				
				int count_pmid_oversize = 0;
				for(int m = 0; m < unique_queried_topic_pmidList.size(); m++){
					
					if(unique_queried_topic_pmidList.get(m) <= TOTAL_PMID_ARRAY_SIZE){
						if(total_unique_PMID_Array[unique_queried_topic_pmidList.get(m)] == 1){
							matched_PMIDList.add( unique_queried_topic_pmidList.get(m));
							matched_PMID_CitationList.add(total_unique_PMID_Citation_Array[unique_queried_topic_pmidList.get(m)]);
							matched_PMID_PubDateList.add(total_unique_PMID_PubDate_Array[unique_queried_topic_pmidList.get(m)]);
							//num_of_matched++;
							
							//if(num_of_matched%1000==0){ //print out the progress of matching process
							//	System.out.println(" ("+QueryTerm+")===> "
							//			+" num_of_matched = "+ num_of_matched);
							//}
						}
					}
					else{
						count_pmid_oversize++;
					}
				}
				
				
				//System.out.println("count_pmid_over array size = " + count_pmid_oversize);
				
				Collections.sort(matched_PMIDList, new Comparator<Integer>() {
			        @Override
			        public int compare(Integer data1, Integer data2)
			        {
			        	if( data1.intValue() < data2.intValue()){
			        		return  -1;
			        	}
			        	if( data1.intValue() > data2.intValue()){
			        		return 1;
			        	}
			        	return 0;
			        }
			    });
				
				//System.out.println("matched_PMIDList.size() = "+ matched_PMIDList.size());
				System.out.print(".");
				
				if(matched_PMIDList.size() == 0) {
					System.out.println("--> No matched PMIDs for calculating PURPOSE Score!!!");
				}
				
			} catch ( IOException e) {
				e.printStackTrace();
			}
						
		}//if (chem2pubtator_inputFile.exists()){
		
	
	
		
		
		//***********************************************************************************************************
		// Updated - 2018-01-11 by Michael Lee @Tainan
		// Evaluation of with or without citation count
		//***********************************************************************************************************
		boolean remove_citation_count = false;
		
		ArrayList<chemID_to_PMID_data> matched_Pair_chem_to_PMID_dataList = new ArrayList<chemID_to_PMID_data>();
		ArrayList<Integer> matched_Pair_chem_IDList = new ArrayList<Integer>();
		ArrayList<Integer> matched_Pair_PMIDList = new ArrayList<Integer>();
		
		ArrayList<String> chem2pubtator_matched_Pair_Citation_chemIDList = new ArrayList<String>();
		ArrayList<Integer>chem2pubtator_matched_Pair_Citation_PMIDCountList  = new ArrayList<Integer>();
		ArrayList<Integer> chem2pubtator_matched_Pair_Citation_CitationCountList  = new ArrayList<Integer>();
			
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//=================================================================================
		//2018-03-09 updated by Michael Lee -------> TO SKIP the rest of the programs for those query terms without ANY matched PMID
		
		
		
		if(matched_PMIDList.size() > 0) {
		
		
		File chem2pubtator_topic_gene_Count_inputFile 
		= new File(fileDirAndName_chem2pubtator_chem2Count);
		
		if(chem2pubtator_topic_gene_Count_inputFile.exists()){
			
			try{
			
				BufferedReader in_chem2Count = new BufferedReader(
						new FileReader(chem2pubtator_topic_gene_Count_inputFile));
				
				while ((temp = in_chem2Count.readLine()) != null) {
					token_string = temp.split("\t");
					if(token_string.length == 2){	
						chem2pubtator_chemIDList.add(token_string[0]);
						chem2pubtator_CountList.add(Integer.parseInt(token_string[1]));
					}
				}
				
				if(in_chem2Count!=null){
					in_chem2Count.close();
				}
				
				System.out.print(".");
				
			} catch ( IOException e) {
				e.printStackTrace();
			}
		}
		
		
		//=====================================================================================================================
		
		// This section is to compute chemID to PMID pair of T+P, we need this data to further compute citation count for each chemID
		// Use the pre-computed [chem2pubtator_tax_id_PMID_Sort] which is sorted version of chem2pubtator(downloaded from pubtator)
		// The format of the chem2pubtator_tax_id_PMID_Sort file is a tab-delimited text file while 
		// first term is PMID and second term is chemID, since the PMID is sorted so that matching process does not need to search back
		// save a lot of execution time.
				
		//=====================================================================================================================
				
		
		
		File chem2pubtator_PMID_Sort_inputFile = 
  				new File(fileDirAndName_chem2pubtator_PMID_Sort);
  		
	
		//if(!file_gene2pubtator_matched_Pair.exists()){
  		
		if (chem2pubtator_PMID_Sort_inputFile.exists()){
		
			try{
				
				BufferedReader in = new BufferedReader(
						new FileReader(chem2pubtator_PMID_Sort_inputFile));

				int current_index = 0;
				int current_PMID = matched_PMIDList.get(current_index);
				
				while ((temp = in.readLine()) != null) {
					
					token_string = temp.split("\t");
					
					if(token_string.length == 2){
						
						//System.out.println(Integer.parseInt(token_string[0]) + "\t" + current_PMID);
						
						if(current_PMID  < Integer.parseInt(token_string[0])){
							if(current_index < matched_PMIDList.size()-1){
								current_index++;
								current_PMID = matched_PMIDList.get(current_index);
							}
						}
						
						if(Integer.parseInt(token_string[0]) == current_PMID){

							matched_Pair_chem_to_PMID_dataList.add(
									new chemID_to_PMID_data(
									Integer.parseInt(token_string[1]), 
									Integer.parseInt(token_string[0])));
							
							//System.out.println( token_string[1] + "\t" + token_string[0]);
							
							if(current_index < matched_PMIDList.size()-1){
								current_index++;
								current_PMID = matched_PMIDList.get(current_index);
							}
						}	
					}
		
				}//while
				
				
				//System.out.println("matched_Pair_chem_to_PMID_dataList.size() = " + matched_Pair_chem_to_PMID_dataList.size());

				Collections.sort(matched_Pair_chem_to_PMID_dataList, new Comparator<chemID_to_PMID_data>() {
			        @Override
			        public int compare(chemID_to_PMID_data data1, chemID_to_PMID_data data2)
			        {
			        	if( data1.getchem_ID() < data2.getchem_ID()){
			        		return  -1;
			        	}
			        	if( data1.getchem_ID() > data2.getchem_ID()){
			        		return 1;
			        	}
			        	return 0;
			        }
			    });

				for(int a = 0 ; a < matched_Pair_chem_to_PMID_dataList.size(); a++){
					matched_Pair_chem_IDList.add(matched_Pair_chem_to_PMID_dataList.get(a).getchem_ID());
					matched_Pair_PMIDList.add(matched_Pair_chem_to_PMID_dataList.get(a).getPMID());
					
					//System.out.println( matched_Pair_chem_to_PMID_dataList.get(a).getchem_ID() + "\t" + matched_Pair_chem_to_PMID_dataList.get(a).getPMID());
				}
				System.out.print(".");

  			} catch ( IOException e) {
  				e.printStackTrace();
  			}
			
  		}//if (gene2pubtator_inputFile.exists()){
			
		
  		
		//=====================================================================================================================
		
		//For each matched chemID to PubMedID pair ----> calculate the sum of citation/yr of each GeneID(protein)
		
		//=====================================================================================================================
		
		
		
		// ArrayList for chemical2pubtator chemical names and MESH ID
		ArrayList<String> chem2pubtator_chem_ID_dataList = new ArrayList<String>();
		ArrayList<String> chem2pubtator_MESH_ID_dataList = new ArrayList<String>();
		ArrayList<String> chem2pubtator_chem_name_dataList = new ArrayList<String>();
		

		// ArrayList for CTD chemical names and MESH ID
		ArrayList<String> CTD_chemicalName_dataList = new ArrayList<String>();
		ArrayList<String> CTD_chemicalMESHID_dataList = new ArrayList<String>();
		ArrayList<String> CTD_ParentTreeNumbers_dataList = new ArrayList<String>();
		ArrayList<String> CTD_chemicalSynonyms_dataList = new ArrayList<String>();
	
		
		int total_citation_count_per_query_topic = 0;
		
		try{
		
			
			
			String current_chemID = null;
			int PMID_count = 0;
			int total_citation_count_per_year = 0;
			int pub_year = 0;
			
			current_chemID = matched_Pair_chem_IDList.get(0)+"";
			PMID_count = 1;
			
			pub_year = total_unique_PMID_PubDate_Array[matched_Pair_PMIDList.get(0)];
			if(pub_year >= 2018){
				pub_year = 2017;
			}
			total_citation_count_per_year = total_unique_PMID_Citation_Array[matched_Pair_PMIDList.get(0)]/(2018-pub_year);
			total_citation_count_per_query_topic = total_unique_PMID_Citation_Array[matched_Pair_PMIDList.get(0)];
		
			
			for(int a = 1; a < matched_Pair_chem_IDList.size(); a++){
				
				if(current_chemID.equals(matched_Pair_chem_IDList.get(a)+"")){
					PMID_count++;
					pub_year = total_unique_PMID_PubDate_Array[matched_Pair_PMIDList.get(a)];
					if(pub_year >= 2018){
						pub_year = 2017;
					}
					total_citation_count_per_year = total_citation_count_per_year + total_unique_PMID_Citation_Array[matched_Pair_PMIDList.get(a)]/(2018-pub_year);
					total_citation_count_per_query_topic = total_citation_count_per_query_topic + total_unique_PMID_Citation_Array[matched_Pair_PMIDList.get(a)];
				
					//System.out.println(total_citation_count_per_year);
				}
				else{
					//save current data
					chem2pubtator_matched_Pair_Citation_chemIDList .add(current_chemID);
					chem2pubtator_matched_Pair_Citation_PMIDCountList.add(PMID_count);
					chem2pubtator_matched_Pair_Citation_CitationCountList.add(total_citation_count_per_year);
					current_chemID = matched_Pair_chem_IDList.get(a)+"";
					PMID_count = 1;	
				
					pub_year = total_unique_PMID_PubDate_Array[matched_Pair_PMIDList.get(a)];
					if(pub_year >= 2018){
						pub_year = 2017;
					}
					
					total_citation_count_per_year = total_unique_PMID_Citation_Array[matched_Pair_PMIDList.get(a)]/(2018-pub_year);
					total_citation_count_per_query_topic = total_citation_count_per_query_topic + total_unique_PMID_Citation_Array[matched_Pair_PMIDList.get(a)];
				}

			}//
			

			System.out.print(".[total citaion]="+total_citation_count_per_query_topic+"..");
			out_total_citation_Count_per_query_topic.write("total_citaion=" + total_citation_count_per_query_topic);
			out_total_citation_Count_per_query_topic.newLine();
			

			//prepare file ---- chemID to chemical name mentions	 			
	  		File chemical2pubtator_with_chemID_inputFile = new File(fileDirAndName_chemical2pubtator_with_chemID);

	  		String fileDirAndName_CTDchemicalName2ID = "./data/myCTD/CTD_chemicalName2ID";
	  		File CTDchemicalName2ID_inputFile = new File(fileDirAndName_CTDchemicalName2ID);
	  		
	  		temp = null;
	  		token_string = null;
	  		if (chemical2pubtator_with_chemID_inputFile.exists()){
				
				BufferedReader in_c = new BufferedReader(new FileReader(chemical2pubtator_with_chemID_inputFile));

				while ((temp = in_c.readLine()) != null) {
					token_string = temp.split("\t");
					if(token_string.length == 3){	
						chem2pubtator_chem_ID_dataList.add(token_string[0]);
						chem2pubtator_MESH_ID_dataList.add(token_string[1]);
						chem2pubtator_chem_name_dataList.add(token_string[2]);
					}
				}
				
				if(in_c!=null){
		  			in_c.close();
		  		}

	  		}
	  		
	  		
	  		
	  		
	  		if (CTDchemicalName2ID_inputFile.exists()){
				
				BufferedReader in_ctd = new BufferedReader(new FileReader(CTDchemicalName2ID_inputFile));

				while ((temp = in_ctd.readLine()) != null) {
					token_string = temp.split("\t");
					if(token_string.length == 4){	//don't need this filter because not all records has 4 columns
						CTD_chemicalName_dataList.add(token_string[0]);
						CTD_chemicalMESHID_dataList.add(token_string[1]);
						CTD_ParentTreeNumbers_dataList.add(token_string[2]);
						CTD_chemicalSynonyms_dataList.add(token_string[3]);
					}
					else if(token_string.length == 3){	//don't need this filter because not all records has 4 columns
						CTD_chemicalName_dataList.add(token_string[0]);
						CTD_chemicalMESHID_dataList.add(token_string[1]);
						CTD_ParentTreeNumbers_dataList.add(token_string[2]);
						CTD_chemicalSynonyms_dataList.add("-");
					}
					else if(token_string.length == 2){	//don't need this filter because not all records has 4 columns
						CTD_chemicalName_dataList.add(token_string[0]);
						CTD_chemicalMESHID_dataList.add(token_string[1]);
						CTD_ParentTreeNumbers_dataList.add("-");
						CTD_chemicalSynonyms_dataList.add("-");
					}
				}
				
				
				//System.out.println("CTD_chemicalName_dataList.size() = " + CTD_chemicalName_dataList.size());
				//System.out.println("CTD_chemicalMESHID_dataList.size() = " + CTD_chemicalMESHID_dataList.size());
				//System.out.println("CTD_ParentTreeNumbers_dataList.size() = " + CTD_ParentTreeNumbers_dataList.size());
				
				
				if(in_ctd!=null){
		  			in_ctd.close();
		  		}

	  		}
	  			
			
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
		
		int index_chemID = -1;
		int index_chem_name = -1;
		int index_meshID = -1;
		int index_CTD_mesh = -1;
		
		temp= null;
		String chem_ID = "-";
		String ParentTree = "-";
		String MESH_ID = "-";
		String chem_Name = "-";
		
		
		//P is the set of publications linked to a particular
		//protein in all studies
		
		//F is the set of all publications linked to any
		//proteins in any topics, containing all PMIDs in the
		//Gene2PubMed file within a particular taxonomy 
		
		//T+P is the set of publications linked to a
		//particular protein within a queried topic.
		
		//calculate Big T, where T is the set of publications that are linked to any protein
		//within a particular taxonomy and that are retrieved from a
		//queried topic
		
		
		double PartI = 0.00;
		double PartII = 0.00;
		double PartIII = 0.00;
		
		
		try{	
			//prepare file ---- chemID to chemical name mentions	 			
	  		File chemical2pubtator_with_chemID_inputFile = new File(fileDirAndName_chemical2pubtator_with_chemID);
	
	  		temp = null;
	  		token_string = null;
	  		if (chemical2pubtator_with_chemID_inputFile.exists()){
				
				BufferedReader in_c = new BufferedReader(new FileReader(chemical2pubtator_with_chemID_inputFile));
				temp = in_c.readLine();//skip title line
				while ((temp = in_c.readLine()) != null) {
					token_string = temp.split("\t");
					if(token_string.length == 3){	
						chem2pubtator_chem_ID_dataList.add(token_string[0]);
						chem2pubtator_MESH_ID_dataList.add(token_string[1]);
						chem2pubtator_chem_name_dataList.add(token_string[2]);
					}
				}
				
				if(in_c!=null){
		  			in_c.close();
		  		}
	
	  		}
			
	  		
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		for(int i =0; i < chem2pubtator_matched_Pair_Citation_chemIDList.size(); i++){
	
			index_chem_name = chem2pubtator_chem_ID_dataList.indexOf(chem2pubtator_matched_Pair_Citation_chemIDList.get(i));
			
			//System.out.println("----------------------- " + chem2pubtator_matched_Pair_Citation_chemIDList.get(i) + "-------------------------");
			
			MESH_ID = chem2pubtator_MESH_ID_dataList.get(index_chem_name);
			
			if(index_chem_name != -1){
				chem_ID = chem2pubtator_chem_ID_dataList.get(index_chem_name);
				MESH_ID = chem2pubtator_MESH_ID_dataList.get(index_chem_name);
				index_CTD_mesh = CTD_chemicalMESHID_dataList.indexOf(MESH_ID);
				
				if(index_CTD_mesh!=-1){
					//System.out.println("MESH_ID->" + MESH_ID + "\t" + index_CTD_mesh);
					ParentTree = CTD_ParentTreeNumbers_dataList.get(index_CTD_mesh);
				}
				else{
					//System.out.println("MESH_ID=>" + MESH_ID + "\t" + index_CTD_mesh);
					ParentTree = "-";
				}
				
				
				index_meshID = chem2pubtator_MESH_ID_dataList.indexOf(MESH_ID);
				
				if(index_meshID!=-1){
					chem_Name = chem2pubtator_chem_name_dataList.get(index_meshID);
				}
				else{
					chem_Name = "-";
				}
			}
			else{
				chem_ID = "-";
				MESH_ID = "-";
				ParentTree = "-";
				chem_Name = "-";
			}
			
			
			
			
			//System.out.println(chem_ID + "\t" + MESH_ID + "\t" + chem_Name);
			
			
			
			index_chemID = chem2pubtator_chemIDList.indexOf(chem2pubtator_matched_Pair_Citation_chemIDList .get(i));
			if(index_chemID != -1){
				count_Big_P = chem2pubtator_CountList.get(index_chemID);		
			}
			else{
				System.out.println(chem2pubtator_matched_Pair_Citation_chemIDList .get(i));
			}
			count_T_intersect_P = chem2pubtator_matched_Pair_Citation_PMIDCountList.get(i);
			
			
			//PartI = 1+log10(TP)
			if(count_T_intersect_P == 1){
				PartI = 1;
			}
			else{
				PartI = 1+Math.log10(count_T_intersect_P);
			}
			
			//PartII = log10((Cit/yr+1)/10)
			PartII = (chem2pubtator_matched_Pair_Citation_CitationCountList.get(i)+1)*0.1;
			if(PartII <= 1.0){
				PartII = 0.0;
			}
			else{
				PartII = Math.log10(PartII);
			}
			
			
			
			//***********************************************************************************************************
			// Michael - 2018-01-11 @Tainan
			//Evaluation of with or without citation count
			//***********************************************************************************************************
			if(remove_citation_count) {
				PartII = 0.0;
			}
			
			
			

			//PartIII = (1+log10(F/T)+log10(F/P))
			if(count_Big_F > count_Big_P && count_Big_F > count_Big_T){
				//System.out.println(count_Big_P);
				PartIII = 1 + Math.log10(count_Big_F/count_Big_T)+Math.log10(count_Big_F/count_Big_P);
			}
			else{
				PartIII = 1;
			}
			

			double PURPOSE = (PartI+PartII)*PartIII;	
				
			String PURPOSE_s = new DecimalFormat("#0.000").format(PURPOSE);
			String PartI_s = new DecimalFormat("#0.000").format(PartI);
			String PartII_s = new DecimalFormat("#0.000").format(PartII);
			String PartIII_s = new DecimalFormat("#0.000").format(PartIII);
			

			
			/********************************************
			 * Date: 2018-01-18
			 * By: Michael Lee
			 * Modified to filter CHEBI data
			 * Modified to filter ParentTree under D23.529 and D23.050
			 * Dont need this filter here ------------> 2018-02-23, 27
			 ********************************************/
			if(MESH_ID.indexOf("CHEBI")==-1){
				
				boolean Remove_ParentTree_of_selected = true;
				
				if(Remove_ParentTree_of_selected) {
					if(	ParentTree.indexOf("D23")!=-1	){  
							/************************************
							if(ParentTree.equals("D23")
							|| ParentTree.indexOf("D23.529")!=-1
							|| ParentTree.indexOf("D23.050")!=-1 
							|| ParentTree.indexOf("D23.101")!=-1
							|| ParentTree.indexOf("D23.113")!=-1) {
								//skip this record
							}
							**************************************/
							
							//skip this record
					}
					
					/*********************************************
					else if(	ParentTree.indexOf("D12.644")!=-1	){  
							//skip this record
					}
					else if(ParentTree.length() == 1){
						if(	ParentTree.equals("-")
						|| ParentTree.equals("D")) {
								//skip this record
						}
					}
					else if(ParentTree.length() == 3){
						if(	ParentTree.equals("D01")
								|| ParentTree.equals("D03")
								|| ParentTree.equals("D12")
								|| ParentTree.equals("D13")) {
								//skip this record
								}
					}
					***********************************************/
					else{
						
						chem_result_List.add(
								new chem_data( 
										QueryTerm,
										chem_ID,
										MESH_ID,
										chem_Name,
										ParentTree,
										Double.parseDouble(PURPOSE_s),
										count_Big_P,
										count_T_intersect_P,
										chem2pubtator_matched_Pair_Citation_CitationCountList.get(i),
										Double.parseDouble(PartI_s),
										Double.parseDouble(PartII_s),
										Double.parseDouble(PartIII_s))
						);
					}
				}
				else{
					//skip this record
				}
					
			
			}
		

		}//for loop
		
		
		System.out.print(".");
		
		Collections.sort(chem_result_List, new Comparator<chem_data>() {
	        @Override
	        public int compare(chem_data PS1, chem_data PS2)
	        {
	        	if( PS1.getPURPOSE_Score() > PS2.getPURPOSE_Score()){
	        		return  -1;
	        	}
	        	if( PS1.getPURPOSE_Score() < PS2.getPURPOSE_Score()){
	        		return 1;
	        	}
	        	return 0;
	        }
	    });
		
		

		}//if(matched_PMIDList.size() > 0) {
		
		
		
		if(matched_PMIDList.size() > 0) {
		
		
		Vector<Integer> PURPOSE_TOP100_chem_vector = new Vector();
		Vector<String> PURPOSE_TOP100_total_unique_PMIDs_vector = new Vector();
		Vector<Integer> PURPOSE_TOP100_chem_Sorted_vector = new Vector();
		
		
		try{
		
			//write to File
			String file_Output_DirAndName 
			= root_directory+QueryTerm+"_PURPOSE[chemical]";
			
			//***********************************************************************************************************
			// Michael - 2018-01-11 @Tainan
			// Run 2018-01-18
			//Evaluation of with or without citation count
			//***********************************************************************************************************
			if(remove_citation_count) {
				file_Output_DirAndName 
				= root_directory+QueryTerm+"_PURPOSE[chemical]_no_citation";
			}
			
			File outputFile = new File(file_Output_DirAndName);
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
			
			
			out.write("Big_T" + "\t" + count_Big_T + "\n");
			out.write("Big_F" + "\t" + count_Big_F + "\n");
			
			out.write(
					"MESH_ID" + "\t" +
					"Chemical_Name" + "\t" +
					"MESH_ParentTree" + "\t" +
					"PURPOSE_Score" + "\t" +
					"Big_P" + "\t" +
					"T+P" + "\t" +
					"Citation" + "\t" +
					"1+Log(T+P)" + "\t" +
					"log10((Cit/yr+1)/10)" + "\t" +
					"1+log10(F/T)+log10(F/P)"
					);
			out.newLine();
			
			int count100 = 0;
			int index_chemID = -1;
			for(int j = 0; j < chem_result_List.size(); j++){
	
				//input_chem_ID_vector.add(chem_result_List.get(j).getChem_ID());
				//input_chem_Name_vector.add(chem_result_List.get(j).getChem_Name());
				
				//input_chem_PS_vector.add(chem_result_List.get(j).getPURPOSE_Score());
				//input_chem_PS_bigP_vector.add(chem_result_List.get(j).getCount_Big_P());
				//input_chem_PS_intsT_P_vector.add(chem_result_List.get(j).getCount_T_intersect_P() );
				//input_chem_Citation_vector.add(chem_result_List.get(j).getTotal_citation_count());
				//input_chem_LOG_intsT_P_vector.add(chem_result_List.get(j).getPartI_value());
				//input_chem_LOG_T_div_P_vector.add(chem_result_List.get(j).getPartII_value()); 
				//input_chem_LOG_Citation_vector.add(chem_result_List.get(j).getPartIII_value()); 

				
				if(!chem_result_List.get(j).getChem_ID().equals("-")){
					
					if(count100 < 100){
						count100++;
						PURPOSE_TOP100_chem_vector.add(Integer.parseInt(chem_result_List.get(j).getChem_ID()));
						PURPOSE_TOP100_chem_Sorted_vector.add(Integer.parseInt(chem_result_List.get(j).getChem_ID()));
					}
					
					out.write(
							chem_result_List.get(j).getMESH_ID()
							+ "\t" + chem_result_List.get(j).getChem_Name()
							+ "\t" + chem_result_List.get(j).getParentTree()
							+ "\t" + chem_result_List.get(j).getPURPOSE_Score() 
							+ "\t" + chem_result_List.get(j).getCount_Big_P() 
							+ "\t" + chem_result_List.get(j).getCount_T_intersect_P()
							+ "\t" + chem_result_List.get(j).getTotal_citation_count()
							+ "\t" + chem_result_List.get(j).getPartI_value()
							+ "\t" + chem_result_List.get(j).getPartII_value()
							+ "\t" + chem_result_List.get(j).getPartIII_value()
							);
					out.newLine();
				}
			}
			
			if(out != null){
				out.close();
			}
			
			
			
			
			long endTime = System.currentTimeMillis();
			long duration_in_ms = (endTime -  startTime);  
			
			long sec =  duration_in_ms/1000;
			
			String exection_time_string = "Finished!---> Execucution:"
					+ sec + " seconds"; // and " + duration_in_ms%1000 + " milliseconds.";
			System.out.println(exection_time_string);
			

			// Sort TOP100 proteins in PURPOSE score
			
			Collections.sort(PURPOSE_TOP100_chem_Sorted_vector, new Comparator<Integer>() {
		        @Override
		        public int compare(Integer chem_ID1, Integer chem_ID2)
		        {
		        	if( chem_ID1 < chem_ID2){
		        		return  -1;
		        	}
		        	if( chem_ID1 > chem_ID2){
		        		return 1;
		        	}
		        	return 0;
		        }
		    });
			
			
			//write to File
			double total_citation_count_per_year_Filter_Value = 5.0;
			
			//System.out.println("PURPOSE_TOP100_chem_Sorted_vector.size()  = " + PURPOSE_TOP100_chem_Sorted_vector.size()); 
	
			int current_index = 0;
			int curent_chemID = 0;
			if(current_index < PURPOSE_TOP100_chem_Sorted_vector.size()){
				PURPOSE_TOP100_chem_Sorted_vector.get(current_index);
			}
			int pub_year = 0;
			double total_citation_count_per_year = 0;
		
			//System.out.println("matched_Pair_chem_IDList.size() = " + matched_Pair_chem_IDList.size());
			
			for(int g = 0; g < matched_Pair_chem_IDList.size(); g++){	
				
				if(curent_chemID == matched_Pair_chem_IDList.get(g) ){
					// filter Citation/Yr here

					pub_year = total_unique_PMID_PubDate_Array[matched_Pair_PMIDList.get(g)];
					if(pub_year >= 2018){
						pub_year = 2017;
					}
					total_citation_count_per_year = total_unique_PMID_Citation_Array[matched_Pair_PMIDList.get(g)]/(2018-pub_year);
					
					//System.out.println("curent_chemID = " + curent_chemID + "\t" + "total_citation_count_per_year = " + total_citation_count_per_year);
					
					if(total_citation_count_per_year >= total_citation_count_per_year_Filter_Value){
						matched_Pair_chemIDList_TOP100.add(matched_Pair_chem_IDList.get(g));
						matched_Pair_PMIDList_TOP100.add(matched_Pair_PMIDList.get(g));
						
						//System.out.println(matched_Pair_chem_IDList.get(g) + "\t" + matched_Pair_PMIDList.get(g));
					}
				}
				else if( (matched_Pair_chem_IDList.get(g) > curent_chemID) && current_index < 99){
					current_index++;//pick the next chemID of TOP100
					if(current_index < PURPOSE_TOP100_chem_Sorted_vector.size()){
						curent_chemID = PURPOSE_TOP100_chem_Sorted_vector.get(current_index);
					}
					//System.out.println("current_index #" + current_index + "\t" + curent_chemID);
				}
				
			}//for
			
			//System.out.println("***** Run Extract TOP100 PMID for view publication *********");

			long startTime_pmid = System.currentTimeMillis();
			
			ExtractTOP100PMIDCitation(root_directory, QueryTerm, fileDirAndName_chemical2pubtator_with_chemID);
			
			long endTime_pmid = System.currentTimeMillis();
			long duration_in_ms_pmid = (endTime_pmid -  startTime_pmid);  
			
			long sec_pmid =  duration_in_ms_pmid/1000;
			
			String exection_time_string_ExtractTOP100PMIDCitation = "Finished!---> Execucution:" 
					+ sec_pmid + " seconds"; //+ duration_in_ms_pmid%1000 + " milliseconds.";
			System.out.println(exection_time_string_ExtractTOP100PMIDCitation);
			
			//free memory
			total_unique_PMID_Array = new int[0];//free memory of Array
			total_unique_PMID_Citation_Array = new int[0];//free memory of Array
			total_unique_PMID_PubDate_Array = new int[0];//free memory of Array
			
			
			
		
			

		} catch ( IOException e) {
			e.printStackTrace();
		}	
		
		
		}//if(matched_PMIDList.size() > 0) {
		

  }
	
	
	public void ExtractTOP100PMIDCitation(String root_directory, String QueryTerm, 
			String fileDirAndName_chemical2pubtator_with_chemID){
		
		ArrayList<Integer> loop_unique_PMIDsList = new ArrayList<Integer>();
		ArrayList<Integer> ESumm_citationList = new ArrayList<Integer>();
		ArrayList<Integer> ESumm_PubDateList = new ArrayList<Integer>();
		ArrayList<String> ESumm_AuthorList = new ArrayList<String>();
		ArrayList<String> ESumm_TitleList = new ArrayList<String>();
		ArrayList<String> ESumm_FullJournalNameList = new ArrayList<String>();
	
		try {
			
			String uid_query_string = "";
			
			int total_PMID = matched_Pair_PMIDList_TOP100.size();
			System.out.print("Extracting PMIDs [query="+QueryTerm+ "]..PMID#="+total_PMID);
			
			//System.out.println("total_PMID = " + total_PMID);
			
			int loop_count = 0;
			int num_query_size = 200; //<----2018-03-06 Changed from 800 to 200 by Michael Lee
			
			
			//System.out.println("num_query_size = " + num_query_size);
			
			if(total_PMID%num_query_size == 0){
				loop_count = total_PMID/num_query_size;
			}
			else{
				loop_count = (total_PMID/num_query_size)+1;
			}
					
			//System.out.println("loop_count = " + loop_count);
			
			
			int end_number = -1;
			URL obj  = null;
			HttpURLConnection con = null;
			int start_i = 1;
			BufferedReader in = null;
			
			for(int i = start_i; i <= loop_count; i++){
				
				uid_query_string = "";
				
				if(i*num_query_size > total_PMID){
					end_number = total_PMID;
				}
				else{
					end_number = i*num_query_size;
				}
				
				System.out.print(".");
				//System.out.println("(#"+i+") --- number_PMIDs = " + end_number);
				
				for(int j = (i-1)*num_query_size; j < end_number ; j++){

					//loop_unique_PMIDsList.add(total_unique_PMIDsList.get(j));
					if(uid_query_string.equals("")){
						uid_query_string = matched_Pair_PMIDList_TOP100.get(j)+"";
					}
					else{
						uid_query_string = uid_query_string + "," + matched_Pair_PMIDList_TOP100.get(j)+"";
					}
				}

				//uid_query_string = "20467650";
				
				
				if(uid_query_string.length() > 0 ){
					
					String query_url 
							= "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?"+
									"db=pubmed"+ "&id=" + uid_query_string;
			
					obj = new URL(query_url);
					con = (HttpURLConnection) obj.openConnection();
			
					// optional default is GET
					con.setRequestMethod("POST");
			
					//add request header
					con.setRequestProperty("User-Agent", "UTF-8");
					
					
					in = new BufferedReader(
					        new InputStreamReader(con.getInputStream()));
					
					String inputLine = "";
					String temp_PMID = null;
					String temp_year = null;
					String temp_author = null;
					String temp_title = null;
					String escape_string = "";
					int escape_start = -1;
					int escape_end = -1;

	        	  	char c = ' ';
	        	  	
	        	  	final int BUFFER_SIZE = 2048;
	        	  	CharBuffer buffer = CharBuffer.allocate(BUFFER_SIZE);
	        	  	
	        	  	while ( in.read(buffer) > 0) {
						
					    buffer.flip();
					    
					    while (buffer.hasRemaining()) {	  
					    	c = buffer.get();
					    	//System.out.print(c);

				    		  if(c=='\n'){
				        	  	//System.out.println(inputLine);
				    			escape_string = "";
								
								if(inputLine.indexOf("&") > 0){
									
									//System.out.println(inputLine);
									
									escape_start = inputLine.indexOf("&", -1);
									escape_end = inputLine.indexOf(";", escape_start);
									
									while(escape_start > 0 && escape_end > 0 && (escape_end-escape_start < 5)){
										
										escape_string = inputLine.substring(escape_start, escape_end+1);
										
										if(escape_string.equals("&amp;")){
											inputLine = inputLine.replaceAll("&amp;", "&");
										}
										else if(escape_string.equals("&lt;")){
											escape_end = inputLine.indexOf("&gt;", escape_start);
											if(escape_end > 0){
												inputLine = inputLine.substring(0, escape_start) + inputLine.substring(escape_end+4);
											}
											else{
												inputLine = inputLine.replaceAll("&lt;", "<");
											}
										}
										else if(escape_string.equals("&gt;")){
											inputLine = inputLine.replaceAll("&gt;", ">");
										}
										else{
											inputLine = inputLine.substring(0, escape_start) + inputLine.substring(escape_end);
										}

										escape_start = inputLine.indexOf("&", -1);
										escape_end = inputLine.indexOf(";", escape_start);
									}
								} 
			    			  
								//System.out.println(inputLine);
				        	  	
				        	  	if(inputLine.indexOf("<Id>")!=-1){
									temp_PMID = inputLine.substring(inputLine.indexOf(">")+1, inputLine.indexOf("</Id>"));
									loop_unique_PMIDsList.add(Integer.parseInt(temp_PMID));
									temp_author= null;
									temp_year = null;
									//System.out.println(temp_PMID);
								}
								
								else if(inputLine.indexOf("<Item Name=\"PubDate\"")!=-1){
									temp_year = inputLine.substring(inputLine.indexOf(">")+1, inputLine.indexOf(">")+5);
									try{
									
										ESumm_PubDateList.add(Integer.parseInt(temp_year));
										
									} catch ( NumberFormatException e) {
										inputLine = inputLine.substring(inputLine.indexOf(">")+1, inputLine.indexOf("</Item>"));
										temp_year = inputLine.substring(inputLine.indexOf(" ")+1, inputLine.indexOf(" ")+5);
										//System.out.println(temp_year);
										ESumm_PubDateList.add(Integer.parseInt(temp_year));
									}
									
								}
								else if(inputLine.indexOf("<Item Name=\"Author\"") != -1 ){
									if(temp_author == null){
										temp_author = inputLine.substring(inputLine.indexOf(">")+1, inputLine.indexOf("</Item>"));
										ESumm_AuthorList.add(temp_author);
									}
								}
								else if(inputLine.indexOf("<Item Name=\"CollectiveName\"") != -1 ){
									if(temp_author == null){
										temp_author = inputLine.substring(inputLine.indexOf(">")+1, inputLine.indexOf("</Item>"));
										ESumm_AuthorList.add(temp_author);
									}
								}
								else if(inputLine.indexOf("<Item Name=\"Title\"") != -1 ){
									
									if(temp_author == null){
										ESumm_AuthorList.add("-");
									}
									
									if(inputLine.indexOf("</Item>") != -1 ){
										inputLine = inputLine.substring(inputLine.indexOf(">")+1, inputLine.indexOf("</Item>"));
									}
									else{
										inputLine = in.readLine();
									}
									inputLine = inputLine.replaceAll("\\[", "");
									inputLine = inputLine.replaceAll("\\]", "");
									ESumm_TitleList.add(inputLine);
								}
								else if(inputLine.indexOf("<Item Name=\"FullJournalName\"") != -1 ){
									if(inputLine.indexOf("</Item>") != -1 ){
										inputLine = inputLine.substring(inputLine.indexOf(">")+1, inputLine.indexOf("</Item>"));
									}
									else{
										inputLine = in.readLine();
									}
									
									if(inputLine.indexOf(":") != -1 ){
										inputLine = inputLine.substring(0, inputLine.indexOf(":"));
									}
									if(inputLine.indexOf("(") != -1 ){
										inputLine = inputLine.substring(0, inputLine.indexOf("(")-1);
									}
									if(inputLine.indexOf("&amp;") != -1 ){
										inputLine = inputLine.replaceAll("&amp;", "&");
									}
									ESumm_FullJournalNameList.add(inputLine);
								
								}
								else if(inputLine.indexOf("<Item Name=\"PmcRefCount\"")!=-1){
									inputLine = inputLine.substring(inputLine.indexOf(">")+1, inputLine.indexOf("</Item>"));
									ESumm_citationList.add(Integer.parseInt(inputLine));
									//System.out.println(inputLine);
								}
								
				        	  	inputLine = "";
				        	  	
				          }//if(c=='\n'){
				          else{
				        	  inputLine = inputLine + c;
				          }  
					    }//while (buffer.hasRemaining()) {	
					    buffer.clear();
	        	  	}//while ((in.read(buffer) > 0) {
	        	  	
	        	  	if(in!=null){
	        	  		in.close();
	        	  	}
					

				}//if(uid_query_string.length() > 0 )
				
				
			}//for loop
			//======================================================
			
			
			//writer
			File file_TOP100_view_publication 
			= new File(root_directory+QueryTerm+"_TOP100[chemical]");
			BufferedWriter out_TOP100_view_publication = new BufferedWriter(new FileWriter(file_TOP100_view_publication));
			
			int index_PMID = -1;
			int index_chem_name = -1;
			
			
			
			// ArrayList for chemical2pubtator chemical names and MESH ID
			ArrayList<Integer> chem2pubtator_chemID_dataList = new ArrayList<Integer>();
			ArrayList<String> chem2pubtator_chem_name_dataList = new ArrayList<String>();
			ArrayList<String> chem2pubtator_MESH_ID_dataList = new ArrayList<String>();
			
			//prepare file ---- chemID to chemical name mentions	 			
	  		File chemical2pubtator_with_chemID_inputFile = new File(fileDirAndName_chemical2pubtator_with_chemID);

	  		String temp = null;
	  		String[] token_string = null;
	  		if (chemical2pubtator_with_chemID_inputFile.exists()){
				
				BufferedReader in_c = new BufferedReader(new FileReader(chemical2pubtator_with_chemID_inputFile));
				temp = in_c.readLine();//skip title line
				while ((temp = in_c.readLine()) != null) {
					token_string = temp.split("\t");
					if(token_string.length == 3){	
						chem2pubtator_chemID_dataList.add(Integer.parseInt(token_string[0]));
						chem2pubtator_MESH_ID_dataList.add(token_string[1]);
						chem2pubtator_chem_name_dataList.add(token_string[2]);
					}
				}
				
				if(in_c!=null){
		  			in_c.close();
		  		}

	  		}
	  			
	  		//String MESH_ID = "-";
	  		String chem_Name = "-";
	  		//String [] chem_name_token = null;
	  		
	  		
			for(int m = 0; m < matched_Pair_chemIDList_TOP100.size(); m++){
				index_chem_name = chem2pubtator_chemID_dataList.indexOf(matched_Pair_chemIDList_TOP100.get(m));
				
				
				if(index_chem_name != -1){
					//MESH_ID = chem2pubtator_MESH_ID_dataList.get(index_chem_name);
					chem_Name = chem2pubtator_chem_name_dataList.get(index_chem_name);
				}

				index_PMID = loop_unique_PMIDsList.indexOf(matched_Pair_PMIDList_TOP100.get(m));
				if(index_PMID!=-1){
					//System.out.println(token_string[1]);
					out_TOP100_view_publication.write(
						matched_Pair_PMIDList_TOP100.get(m) + "\t" +
								chem_Name + "\t" +
						ESumm_AuthorList.get(index_PMID) + "\t" +
						ESumm_TitleList.get(index_PMID) + "\t" +
						ESumm_FullJournalNameList.get(index_PMID) + "\t" +
						ESumm_PubDateList.get(index_PMID) + "\t" +
						ESumm_citationList.get(index_PMID) 
						+ "\n");
				}

			}
			
			if(out_TOP100_view_publication!= null){
				out_TOP100_view_publication.close();
			}
			

		} catch ( IOException e) {
			e.printStackTrace();
			
		}
		

	}
	

    
    
	
	public static void main(String[] args) throws IOException  {
		
		String query_string = "";
		String species = "";
		if(args.length > 0){
			
			species = args[args.length-1];
			
			for(int i = 0; i < args.length-1; i++){
				if(i==0){
					query_string = args[i];
				}
				else{
					if(args[i].toLowerCase().equals("or")){
						query_string = query_string + "+" + species + "+" + args[i];
					}
					else{
						query_string = query_string + "+" + args[i];
					}
				}
			}
			
			
			if(query_string.equals("") || query_string == null || query_string.isEmpty()){
				System.out.println("Sorry, the query term is empty!");
			}
			else{
				PURPOSE_chem2pubtator obj = new PURPOSE_chem2pubtator(query_string, species);
			}
			
		}
		else{
			Scanner in = new Scanner(System.in);
			System.out.print("Query Term + space + species(ex. brain human/mouse/rat/fly/worm/yeast) [-1 to exit]:");
			query_string = in.nextLine();
			
			String[] query_array = null;
			String species_s = "";
			
			while(!query_string.equals("-1")){
				if(query_string.equals("") || query_string == null || query_string.isEmpty()){
					System.out.println("Sorry, the query term is empty!");
				}
				else{
					query_array = query_string.split(" ");
					species_s = query_array[query_array.length-1];
					
					for(int i = 0; i < query_array.length-1; i++){
						if(i==0){
							query_string = query_array[i];
						}
						else{
							if(query_array[i].toLowerCase().equals("or")){
								query_string = query_string + "+" + species_s + "+" + query_array[i];
							}
							else{
								query_string = query_string + "+" + query_array[i];
							}
							
						}
					}
					PURPOSE_chem2pubtator obj = new PURPOSE_chem2pubtator(query_string, species_s);
				}
				
				System.out.print("Query Term + space + species(ex. brain human/mouse/rat/fly/worm/yeast) [-1 to exit]:");
				query_string = in.nextLine();
			}
		}
		
		

	}//end of main method

	


}//end of class









	










class chemID_to_PMID_data{
	
	private int chem_ID;
	private int PMID;
	
	public chemID_to_PMID_data(int chem_ID, int PMID){
		this.chem_ID = chem_ID;
		this.PMID = PMID;
	}
	
	public int getchem_ID() {
		return chem_ID;
	}

	public void setGeneID(int chem_ID) {
		this.chem_ID = chem_ID;
	}

	public int getPMID() {
		return PMID;
	}

	public void setPMID(int pMID) {
		PMID = pMID;
	}
	
}


class PMID_to_chem_ID_data{
	
	private int PMID;
	private int chem_ID;
	
	public PMID_to_chem_ID_data(int PMID, int chem_ID){
		this.PMID = PMID;
		this.chem_ID = chem_ID;
	}
	
	public int getchem_ID() {
		return chem_ID;
	}

	public void setGeneID(int chem_ID) {
		this.chem_ID = chem_ID;
	}

	public int getPMID() {
		return PMID;
	}

	public void setPMID(int PMID) {
		PMID = PMID;
	}
	
}


class chem_data{
	
	private String disease_topic;
	private String chem_ID;
	private String MESH_ID;
	private String  chem_Name;
	private String ParentTree;
	private double PURPOSE_score;
	private int count_Big_P;
	private int count_T_intersect_P;
	private int total_citation_count;
	private double PartI_value;
	private double PartII_value;
	private double PartIII_value;

	public chem_data(
			String disease_topic,
			String chem_ID,
			String MESH_ID,
			String  chem_Name, 
			String ParentTree,
			double PURPOSE_score, 
			int count_Big_P,  
			int count_T_intersect_P, 
			int total_citation_count, 
			double PartI_value, 
			double PartII_value, 
			double PartIII_value){
		this.disease_topic= disease_topic;
		this.chem_ID = chem_ID;
		this.MESH_ID = MESH_ID;
		this.chem_Name = chem_Name;
		this.ParentTree = ParentTree;
		this.PURPOSE_score = PURPOSE_score;
		this.count_Big_P = count_Big_P;
		this.count_T_intersect_P = count_T_intersect_P;
		this.total_citation_count = total_citation_count;
		this.PartI_value = PartI_value;
		this.PartII_value = PartII_value;
		this.PartIII_value = PartIII_value;
	}

	
	public String getDisease_topic() {
		return disease_topic;
	}

	public void setDisease_topic(String disease_topic) {
		this.disease_topic = disease_topic;
	}
	
	public String getChem_ID() {
		return chem_ID;
	}

	public void setChem_ID(String chem_ID) {
		this.chem_ID = chem_ID;
	}
	
	public String getMESH_ID() {
		return MESH_ID;
	}

	public void setMESH_ID(String MESH_ID) {
		this.MESH_ID = MESH_ID;
	}

	public String getChem_Name() {
		return chem_Name;
	}

	public void setChem_Name(String chem_Name) {
		this.chem_Name = chem_Name;
	}
	
	public String getParentTree() {
		return ParentTree;
	}


	public void setParentTree(String parentTree) {
		ParentTree = parentTree;
	}
	
	public double getPURPOSE_Score() {
		return PURPOSE_score;
	}

	public int getCount_Big_P() {
		return count_Big_P;
	}

	public void setCount_Big_P(int count_Big_P) {
		this.count_Big_P = count_Big_P;
	}
	
	public int getCount_T_intersect_P() {
		return count_T_intersect_P;
	}

	public void setCount_T_intersect_P(int count_T_intersect_P) {
		this.count_T_intersect_P = count_T_intersect_P;
	}

	public int getTotal_citation_count() {
		return total_citation_count;
	}

	public void setTotal_citation_count(int total_citation_count) {
		this.total_citation_count = total_citation_count;
	}
	
	public double getPartI_value() {
		return PartI_value;
	}

	public void setPartI_value(double partI_value) {
		PartI_value = partI_value;
	}

	public double getPartII_value() {
		return PartII_value;
	}

	public void setPartII_value(double partII_value) {
		PartII_value = partII_value;
	}

	public double getPartIII_value() {
		return PartIII_value;
	}

	public void setPartIII_value(double partIII_value) {
		PartIII_value = partIII_value;
	}

	
}
