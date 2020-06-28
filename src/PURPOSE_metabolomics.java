
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class PURPOSE_metabolomics {

	public static void main(String[] args) {
		
		boolean process_metabolomics_File = false;
		if(process_metabolomics_File){
			String directoryAndName_metabolomics = "./data/metabolomics/hmdb_metabolites.xml";//last execution: 20180304
			process_metabolomics_File_20180304(directoryAndName_metabolomics);
		}

		boolean process_pubtator_File  = true;
		if(process_pubtator_File){
			
			System.out.println("================================================================");
			System.out.println("================================================================");
			long startTime = System.currentTimeMillis() ; 
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println("start time = " + dateFormat.format(date));
			System.out.println("================================================================");
			System.out.println("================================================================");

			String directoryAndName_pubtator = "./data/myPubtator/chemical2pubtator.gz";
			//directoryAndName_pubtator = "./data/pubtator/bioconcepts2pubtator";
			process_pubtator_File_v20180303(directoryAndName_pubtator);

			System.out.println("================================================================");
			System.out.println("================================================================");
			Date date1 = new Date();
			System.out.println("Finish time = " + dateFormat.format(date1));
			long endTime = System.currentTimeMillis(); 
			long min = (endTime-startTime)/1000/60;
			long sec = ((endTime-startTime)/1000)%60;
			System.out.println("Total Execution time in " + min + " min. and " + sec + " sec.") ; 
			System.out.println("================================================================");
			System.out.println("================================================================");
		}
    	
		int divide_size = 100000;
		String directoryAndName_pubtator_chemical2PMID_pairs = "./data/pubtator/pubtator_chemical2PMID_pairs_"+divide_size+"/";
		String directoryAndName_metabolomics2PMID_pair = "./data/metabolomics/metabolomics2PMID_pair_"+divide_size+"/";
		
		boolean divide_Files = false;
		if(divide_Files){

			String directoryAndName = "./data/pubtator/pubtator_chemical2PMID_pairs_1019.txt";
			divide_pubtator_chemical2PMID_pairs_Files(divide_size, directoryAndName, directoryAndName_pubtator_chemical2PMID_pairs);
		}
		
		
		
		//run @2017-11-15
		boolean match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs = false; 
		if(match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs){
			match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs(
					directoryAndName_pubtator_chemical2PMID_pairs, 
					directoryAndName_metabolomics2PMID_pair);
		}
		
		
		boolean match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs_using_Thread = false;
		if(match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs_using_Thread){
			match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs_using_Thread(
					directoryAndName_pubtator_chemical2PMID_pairs, 
					directoryAndName_metabolomics2PMID_pair);
		}
		
		
		//last execution: 2017-11-25, 2018-03-01, 2018-03-03, 2018-03-04
		boolean combine_metabolomics2PMID_pair_combined_Files = false;
		if(combine_metabolomics2PMID_pair_combined_Files){
			String metabolomics2PMID_pair_combined_Directory = "./data/metabolomics/metabolomics2PMID_pair_100000_Combined/";
			String myMeta2Pubtator_Directory = "./data/myMeta2Pubtator/metabolomics2PMID_pair_100000_Combined_file.txt";
			String meta2Pubtator_with_metaID_Directory = "./data/myMeta2Pubtator/meta2Pubtator_with_metaID";
			String meta2Count_Directory = "./data/myMeta2Pubtator/meta2Pubtator_meta2count";
			String meta2Pubtator_metaID_not_sorted = "./data/myMeta2Pubtator/meta2Pubtator_metaID_not_sorted";//<------------- check multiple HMDB_IDs
			
			combine_metabolomics2PMID_pair_combined_Files_v20180303( 
					metabolomics2PMID_pair_combined_Directory,
					myMeta2Pubtator_Directory,
					meta2Pubtator_with_metaID_Directory,
					meta2Count_Directory,
					meta2Pubtator_metaID_not_sorted);
			
			
			String meta2Pubtator_metaID_sort = "./data/myMeta2Pubtator/meta2Pubtator_metaID_sort";
			
			sort_MetaID_meta2pubtator(
					meta2Pubtator_metaID_not_sorted, meta2Pubtator_metaID_sort);
			
			
			String meta2Pubtator_PMID_sort_Directory = "./data/myMeta2Pubtator/meta2Pubtator_PMID_sort";
			sort_PMID_meta2pubtator(
					meta2Pubtator_metaID_sort, meta2Pubtator_PMID_sort_Directory);
			
		}
		
		
		//last executions: 2018-03-01, 03 , 2018-03-04
		boolean replace_Mentions_IN_meta2Pubtator_with_metaID_using_HMDB = false;
		if(replace_Mentions_IN_meta2Pubtator_with_metaID_using_HMDB){
			
			boolean write_hmdb_metabolomics_name_and_synonym_into_unique_File = true;
			String hmdb_metabolomics_name_and_synonym_Directory = "./data/metabolomics/hmdb_metabolomics_name_and_synonym.txt";
			String hmdb_metabolomics_name_and_synonym_Directory_unique_20180303 =  "./data/metabolomics/hmdb_metabolomics_name_and_synonym_unique.txt";
			String meta2Pubtator_with_metaID_Directory= "./data/myMeta2Pubtator/meta2Pubtator_with_metaID";
			String meta2Pubtator_with_metaID_HMDB_CommonName_Directory= "./data/myMeta2Pubtator/meta2Pubtator_with_metaID_HMDB_name";
			replace_Mentions_IN_meta2Pubtator_with_metaID_using_HMDB(
					write_hmdb_metabolomics_name_and_synonym_into_unique_File,
					hmdb_metabolomics_name_and_synonym_Directory,
					hmdb_metabolomics_name_and_synonym_Directory_unique_20180303,
					meta2Pubtator_with_metaID_Directory,
					meta2Pubtator_with_metaID_HMDB_CommonName_Directory);
		}
		
		//last execution: 2018-03-03
		replace_Mentions_IN_meta2Pubtator_with_metaID_using_HMDB = false;
		if(replace_Mentions_IN_meta2Pubtator_with_metaID_using_HMDB){
			
			boolean write_hmdb_metabolomics_name_and_synonym_into_unique_File = false;
			String hmdb_metabolomics_name_and_synonym_Directory = "./data/metabolomics/hmdb_metabolomics_name_and_synonym_unique.txt";
			String hmdb_metabolomics_name_and_synonym_Directory_unique_20180303 =  "File (hmdb_metabolomics_name_and_synonym_unique.txt) is Already exist";
			String meta2Pubtator_with_metaID_Directory= "./data/myMeta2Pubtator/meta2Pubtator_with_metaID";
			String meta2Pubtator_with_metaID_HMDB_CommonName_Directory= "./data/myMeta2Pubtator/meta2Pubtator_with_metaID_HMDB_name";
			replace_Mentions_IN_meta2Pubtator_with_metaID_using_HMDB(
					write_hmdb_metabolomics_name_and_synonym_into_unique_File,
					hmdb_metabolomics_name_and_synonym_Directory,
					hmdb_metabolomics_name_and_synonym_Directory_unique_20180303,
					meta2Pubtator_with_metaID_Directory,
					meta2Pubtator_with_metaID_HMDB_CommonName_Directory);
		}
		
		
		
		//last execution: 2017-11-25, last execution: 2018-03-04
		boolean sort_PMID_meta2pubtator = false;
		if(sort_PMID_meta2pubtator) {
			String meta2Pubtator_metaID_sort_Directory = "./data/myMeta2Pubtator/meta2Pubtator_metaID_sort";
			String meta2Pubtator_PMID_sort_Directory = "./data/myMeta2Pubtator/meta2Pubtator_PMID_sort";
			sort_PMID_meta2pubtator(
					meta2Pubtator_metaID_sort_Directory, meta2Pubtator_PMID_sort_Directory);
		}

	}
	
	
	
	
	public static void combine_metabolomics2PMID_pair_combined_Files_v20180303(
			String metabolomics2PMID_pair_combined_Directory, 
			String myMeta2Pubtator_Directory,
			String meta2Pubtator_with_metaID_Directory,
			String meta2Count_Directory,
			String meta2Pubtator_metaID_sort){
	
		
		ArrayList<String> unique_combined_HMDB_ID_dataList = new ArrayList<String>();
		ArrayList<String> unique_combined_Mentions_dataList = new ArrayList<String>();
		ArrayList<Integer> unique_combined_PMID_Count_dataList = new ArrayList<Integer>();
		
		File directory_metabolomics2PMID_pair_combined = new File(metabolomics2PMID_pair_combined_Directory);
	
		try {
			BufferedWriter out_myMeta2Pubtator  
					= new BufferedWriter(
							new FileWriter(myMeta2Pubtator_Directory));
			
			BufferedWriter out_meta2Pubtator_with_metaID  
			= new BufferedWriter(
					new FileWriter(meta2Pubtator_with_metaID_Directory));
			out_meta2Pubtator_with_metaID.write("metaID" + "\t" + "HMDB_ID" + "\t" + "Mentions" + "\n");
	
			BufferedWriter out_meta2Count  
			= new BufferedWriter(
					new FileWriter(meta2Count_Directory));
			
			BufferedWriter out_meta2Pubtator_metaID_sort  
			= new BufferedWriter(
					new FileWriter(meta2Pubtator_metaID_sort));
			
			String[] file_Name_Array = directory_metabolomics2PMID_pair_combined.list();
			System.out.println(directory_metabolomics2PMID_pair_combined.getName() + " has number of files = " + file_Name_Array.length);
			
			File file_metabolomics2PMID_pair_combined = null;
			int metaboliteID = 0;
			int j = 0;
			String[] token_array = null;
			String[] token_PMID_array = null;
			
			for(int i = 0; i < file_Name_Array.length; i++){
				
				file_metabolomics2PMID_pair_combined = new File(metabolomics2PMID_pair_combined_Directory + file_Name_Array[i]);
				//check if the citation file is exist?
				System.out.println("==================================" + file_metabolomics2PMID_pair_combined.getName()
						+" is exists()? = " + file_metabolomics2PMID_pair_combined.exists() + "================================\n\n");
				
				if(file_metabolomics2PMID_pair_combined.exists()){
	
					BufferedReader in_metabolomics2PMID_pair_combined = new BufferedReader(
							new FileReader(file_metabolomics2PMID_pair_combined));
		
					String inputLine = null;
					int count_redundant = 0;
					int index_unique_combined_HMDB_ID = -1;
					int temp_PMID_count = 0;
					String temp_Mentions_string = null;
					boolean print_redundant = true;
					while ((inputLine = in_metabolomics2PMID_pair_combined.readLine()) != null) {
						
						token_array = inputLine.split("\t");
						
						index_unique_combined_HMDB_ID = unique_combined_HMDB_ID_dataList.indexOf(token_array[2]);
						if(index_unique_combined_HMDB_ID != -1) {//check if HMDB_ID is exist in the list
							count_redundant++;
							
							out_myMeta2Pubtator.write((index_unique_combined_HMDB_ID+1) + "\t" + inputLine+"\n");//write to complete file
							//System.out.println((index_unique_combined_HMDB_ID+1) + "\t" + inputLine);
							
							//out_meta2Pubtator_with_metaID.write((index_unique_combined_HMDB_ID+1) + "\t" + token_array[2] + "\t" + token_array[0] + "\n");//write to meta2Pubtator metaID to PubMedID file
							//[NOTICE 2018-03-03] token_array[0] is the name from chemical2pubtator and may not be CONSISTENT with HDMB common name
							
							//update Mentions
							temp_Mentions_string = unique_combined_Mentions_dataList.get(index_unique_combined_HMDB_ID) + " | " + token_array[0];
							unique_combined_Mentions_dataList.set(index_unique_combined_HMDB_ID, temp_Mentions_string);
							
							//update PMID count
							temp_PMID_count = unique_combined_PMID_Count_dataList.get(index_unique_combined_HMDB_ID) + Integer.parseInt(token_array[1]);
							unique_combined_PMID_Count_dataList.set(index_unique_combined_HMDB_ID, temp_PMID_count);
							
							//update meta2Pubtator
							token_PMID_array = token_array[3].split(",");
							for(j = 0; j < token_PMID_array.length; j++) {
								out_meta2Pubtator_metaID_sort.write((index_unique_combined_HMDB_ID+1) + "\t" + token_PMID_array[j] + "\n");
							}

							if(!print_redundant) {
								System.out.println(count_redundant  + "-------------> " + 
								token_array[2] + "\t" + token_array[0]  + "\t" +  " ------  already exist  --------------------- " +  "\t"+
								unique_combined_HMDB_ID_dataList.get(index_unique_combined_HMDB_ID) + "\t"+ 
								unique_combined_Mentions_dataList.get(index_unique_combined_HMDB_ID));
							}
							
						}//if(index_unique_combined_HMDB_ID != -1) 
						else {
							
							//if not exist in the unique_combined_HMDB_ID_dataList
							unique_combined_HMDB_ID_dataList.add(token_array[2]);//add the unique HMDB_ID to the ArrayList
							unique_combined_Mentions_dataList.add(token_array[0]);
							unique_combined_PMID_Count_dataList.add(Integer.parseInt(token_array[1]));
							metaboliteID++;
							
							out_myMeta2Pubtator.write(metaboliteID + "\t" + inputLine+"\n");//write to complete file

							//out_meta2Pubtator_with_metaID.write(metaboliteID + "\t" + token_array[2] + "\t" + token_array[0] + "\n");//write to meta2Pubtator metaID to PubMedID file
							//[NOTICE 2018-03-03] token_array[0] is the name from chemical2pubtator and may not be CONSISTENT with HDMB common name
							
							//out_meta2Count.write(metaboliteID + "\t" + token_array[1] + "\n");
							
							token_PMID_array = token_array[3].split(",");
							for(j = 0; j < token_PMID_array.length; j++) {
								out_meta2Pubtator_metaID_sort.write(metaboliteID + "\t" + token_PMID_array[j] + "\n");
							}
							
							
						}
					}				
					
					if(in_metabolomics2PMID_pair_combined!=null){
						in_metabolomics2PMID_pair_combined.close();
					}
	
				}
			}
			
			
			
			System.out.println("\n\n\n\n\n\n\n\n\n");
			System.out.println("-------------------------------------->>>>> unique_combined_HMDB_ID_dataList.size() = " + unique_combined_HMDB_ID_dataList.size());
			int mid = 0;
			for(mid = 0; mid < unique_combined_HMDB_ID_dataList.size(); mid++) {						
				
				out_meta2Pubtator_with_metaID.write((mid+1) + "\t" 
				+ unique_combined_HMDB_ID_dataList.get(mid) + "\t" + unique_combined_Mentions_dataList.get(mid) + "\n");
				
				out_meta2Count.write((mid+1) + "\t" + unique_combined_PMID_Count_dataList.get(mid) + "\n");
			}
			
			
			//close combined_citation writer
			if(out_myMeta2Pubtator!=null){
				out_myMeta2Pubtator.close();
			}
			
			
			if(out_meta2Pubtator_with_metaID!=null){
				out_meta2Pubtator_with_metaID.close();
			}
			
			
			if(out_meta2Count!=null){
				out_meta2Count.close();
			}
			
			if(out_meta2Count!=null){
				out_meta2Pubtator_metaID_sort.close();
			}

				
		} catch ( IOException e) {
				e.printStackTrace();
				
		}

	}
	
	
	
	
	
	
	
	public static void replace_Mentions_IN_meta2Pubtator_with_metaID_using_HMDB(
			boolean write_hmdb_metabolomics_name_and_synonym_into_unique_File,
			String hmdb_metabolomics_name_and_synonym_Directory,
			String hmdb_metabolomics_name_and_synonym_Directory_unique_20180303,
			String meta2Pubtator_with_metaID_Directory,
			String meta2Pubtator_with_metaID_HMDB_CommonName_Directory
			){
	
		
		File hmdb_metabolomics_name_and_synonym_unique_File = null;
		BufferedWriter out_hmdb_metabolomics_name_and_synonym_unique= null;
		if(write_hmdb_metabolomics_name_and_synonym_into_unique_File) {
			hmdb_metabolomics_name_and_synonym_unique_File = new File(hmdb_metabolomics_name_and_synonym_Directory_unique_20180303);
		}
		
		File hmdb_metabolomics_name_and_synonym_File = new File(hmdb_metabolomics_name_and_synonym_Directory);
		File meta2Pubtator_with_metaID_File = new File(meta2Pubtator_with_metaID_Directory);
		
		
		try {
			
			ArrayList<String> HMDB_ID_dataList = new ArrayList<String>();
			ArrayList<String> HMDB_MetaName_dataList = new ArrayList<String>();
			
			
			if(write_hmdb_metabolomics_name_and_synonym_into_unique_File) {
				//prepare writer
				out_hmdb_metabolomics_name_and_synonym_unique
						= new BufferedWriter(
								new FileWriter(hmdb_metabolomics_name_and_synonym_unique_File));
			}
			
			//prepare writer
			BufferedWriter out_meta2Pubtator_with_metaID_HMDB_CommonName
					= new BufferedWriter(
							new FileWriter(meta2Pubtator_with_metaID_HMDB_CommonName_Directory));
			
			
			if(hmdb_metabolomics_name_and_synonym_File.exists()){

				BufferedReader in_hmdb_metabolomics_name_and_synonym = new BufferedReader(
						new FileReader(hmdb_metabolomics_name_and_synonym_File));
	

				String temp = null;
				String[]  token_array = null;
				while ((temp = in_hmdb_metabolomics_name_and_synonym.readLine()) != null) {

					token_array = temp.split("\t");
					
					if(token_array.length == 2) {
					
						if(!HMDB_ID_dataList.contains(token_array[1])) {//save the FIRST appeared HMDB ID and name (Common Name of HMDB)
							HMDB_ID_dataList.add(token_array[1]);
							HMDB_MetaName_dataList.add(token_array[0]);
						}
					}
					else {
						
						System.out.println(temp);
					}
					
				}
				
				System.out.println("HMDB_ID_dataList.size() = " + HMDB_ID_dataList.size());
				System.out.println("HMDB_MetaName_dataList.size() = " + HMDB_MetaName_dataList.size());
				
				
				if(write_hmdb_metabolomics_name_and_synonym_into_unique_File) {
					for(int i = 0; i < HMDB_ID_dataList.size(); i++ ) {
						out_hmdb_metabolomics_name_and_synonym_unique.write( HMDB_MetaName_dataList.get(i) + "\t" + HMDB_ID_dataList.get(i) + "\n");
					}
				}
				
				if(in_hmdb_metabolomics_name_and_synonym!=null){
					in_hmdb_metabolomics_name_and_synonym.close();
				}

			}//if(hmdb_metabolomics_name_and_synonym_File.exists()){
			
			
			
			
			if(meta2Pubtator_with_metaID_File.exists()){

				BufferedReader in_meta2Pubtator_with_metaID = new BufferedReader(
						new FileReader(meta2Pubtator_with_metaID_File));
	
				String temp = in_meta2Pubtator_with_metaID.readLine();//skip first line
				String[]  token_array = null;
				int index_HMDB = -1; 
				
				
				out_meta2Pubtator_with_metaID_HMDB_CommonName.write("metaID" + "\t" + "HMDB_ID" + "\t" + "HMDB_Name" + "\n");
				
				
				while ((temp = in_meta2Pubtator_with_metaID.readLine()) != null) {

					token_array = temp.split("\t");
					
					index_HMDB = HMDB_ID_dataList.indexOf(token_array[1]);
							
					if(index_HMDB!=-1) {
						out_meta2Pubtator_with_metaID_HMDB_CommonName.write(token_array[0] + "\t" + token_array[1] + "\t" + HMDB_MetaName_dataList.get(index_HMDB)  + "\n");
					}
					else {
						System.out.println(temp);
						
					}
					
					
				}
				
				
				if(in_meta2Pubtator_with_metaID!=null){
					in_meta2Pubtator_with_metaID.close();
				}

			}//if(meta2Pubtator_with_metaID_File.exists()){
			
			
			//close writer to complete file
			if(out_hmdb_metabolomics_name_and_synonym_unique!=null){
				out_hmdb_metabolomics_name_and_synonym_unique.close();
			}
			
			
			
			//close writer to complete file
			if(out_meta2Pubtator_with_metaID_HMDB_CommonName!=null){
				out_meta2Pubtator_with_metaID_HMDB_CommonName.close();
			}
			
	
		} catch ( IOException e) {
				e.printStackTrace();
				
		}

	}
	
	
	
	public static void sort_MetaID_meta2pubtator(
    		String meta2Pubtator_metaID_not_sorted_Directory, String meta2Pubtator_metaID_sort_Directory){
      	
		File meta2pubtator_inputFile = 
					new File(meta2Pubtator_metaID_not_sorted_Directory);
			
		ArrayList<metaID_to_PMID_data> matched_Pair_metaID_to_PMID_dataList = new ArrayList<metaID_to_PMID_data>();
		
	
		if (meta2pubtator_inputFile.exists()){
		
			try{
				
				BufferedReader in = new BufferedReader(
						new FileReader(meta2pubtator_inputFile));

				String[] token_string = null;
				String temp = null;
				
				while ((temp = in.readLine()) != null) {
					token_string = temp.split("\t");
					if(token_string.length == 2){
						matched_Pair_metaID_to_PMID_dataList.add(
								//token_string[1] --> PMID , token_string[0] ---> metaID
								new metaID_to_PMID_data(Integer.parseInt(token_string[0]), Integer.parseInt(token_string[1])));
					}
				}//while
				
				if(in!=null){
  					in.close();
  				}
				
				Collections.sort(matched_Pair_metaID_to_PMID_dataList, new Comparator<metaID_to_PMID_data>() {
			        @Override
			        public int compare(metaID_to_PMID_data data1, metaID_to_PMID_data data2)
			        {
			        	if( data1.getMetaID() < data2.getMetaID()){
			        		return  -1;
			        	}
			        	if( data1.getMetaID() > data2.getMetaID()){
			        		return 1;
			        	}
			        	return 0;
			        }
			    });
				
				

				File file_meta2pubtator_metaID_Sort = new File(meta2Pubtator_metaID_sort_Directory);
				
				BufferedWriter out_metaID_to_PMID_sort_pair = new BufferedWriter(new FileWriter(file_meta2pubtator_metaID_Sort));	
				
				for(int a = 0 ; a < matched_Pair_metaID_to_PMID_dataList.size(); a++){
					out_metaID_to_PMID_sort_pair.write(matched_Pair_metaID_to_PMID_dataList.get(a).getMetaID()
							+ "\t" + matched_Pair_metaID_to_PMID_dataList.get(a).getPMID());
					out_metaID_to_PMID_sort_pair.newLine();
				}
				
				if(out_metaID_to_PMID_sort_pair!=null){
					out_metaID_to_PMID_sort_pair.close();
				}
			
			
  			} catch ( IOException e) {
  				e.printStackTrace();
  			}
			
  		}//if (meta2pubtator_inputFile.exists()){

    }
    
	
	
	


    public static void sort_PMID_meta2pubtator(
    		String meta2Pubtator_metaID_sort_Directory, String meta2Pubtator_PMID_sort_Directory){
      	
		File meta2pubtator_inputFile = 
					new File(meta2Pubtator_metaID_sort_Directory);
			
		ArrayList<PMID_to_metaID_data> matched_Pair_meta_to_PMID_dataList = new ArrayList<PMID_to_metaID_data>();
		
	
		if (meta2pubtator_inputFile.exists()){
		
			try{
				
				BufferedReader in = new BufferedReader(
						new FileReader(meta2pubtator_inputFile));

				String[] token_string = null;
				String temp = null;
				
				while ((temp = in.readLine()) != null) {
					token_string = temp.split("\t");
					if(token_string.length == 2){
						matched_Pair_meta_to_PMID_dataList.add(
								//token_string[1] --> PMID , token_string[0] ---> metaID
								new PMID_to_metaID_data(Integer.parseInt(token_string[1]), Integer.parseInt(token_string[0])));
					}
				}//while
				
				if(in!=null){
  					in.close();
  				}
				
				Collections.sort(matched_Pair_meta_to_PMID_dataList, new Comparator<PMID_to_metaID_data>() {
			        @Override
			        public int compare(PMID_to_metaID_data data1, PMID_to_metaID_data data2)
			        {
			        	if( data1.getPMID() < data2.getPMID()){
			        		return  -1;
			        	}
			        	if( data1.getPMID() > data2.getPMID()){
			        		return 1;
			        	}
			        	return 0;
			        }
			    });
				
				

				File file_meta2pubtator_PMID_Sort = new File(meta2Pubtator_PMID_sort_Directory);
				
				BufferedWriter out_pair = new BufferedWriter(new FileWriter(file_meta2pubtator_PMID_Sort));	
				
				for(int a = 0 ; a < matched_Pair_meta_to_PMID_dataList.size(); a++){
					out_pair.write(matched_Pair_meta_to_PMID_dataList.get(a).getPMID()
							+ "\t" + matched_Pair_meta_to_PMID_dataList.get(a).getmeta_ID());
					out_pair.newLine();
				}
				
				if(out_pair!=null){
					out_pair.close();
				}
			
			
  			} catch ( IOException e) {
  				e.printStackTrace();
  			}
			
  		}//if (meta2pubtator_inputFile.exists()){

    }
    
    
	

	
	
	public static void divide_pubtator_chemical2PMID_pairs_Files(int divide_size, String directoryAndName_pubtator, 
			String directoryAndName_pubtator_chemical2PMID_pairs){
		
		try{	
			
			
			File outputFile_directory = new File(directoryAndName_pubtator_chemical2PMID_pairs);
			if(!outputFile_directory.exists()){
				outputFile_directory.mkdirs();
			}
			
			
			int processed_entry = 0;
			File inputFile2 = new File(directoryAndName_pubtator);
			BufferedReader in2 = new BufferedReader(
					new FileReader(inputFile2));
			
			File outputFile = null;
			BufferedWriter out = null;
			String temp = null;
			int file_count = 0;
			
			temp = in2.readLine();
			System.out.println("skip first line of File:" + inputFile2.getName() + "----->" + temp);
			
			while ((temp = in2.readLine()) != null) {
				processed_entry++;
				if(processed_entry%divide_size==1){
					file_count++;
					if(out!=null){
						out.close();
					}
					outputFile = new File(directoryAndName_pubtator_chemical2PMID_pairs
							+"pubtator_chemical2PMID_pairs_"+file_count+".txt");
					out = new BufferedWriter(new FileWriter(outputFile));
				}
				out.write(temp + "\n");
			}
			
			if(in2!= null){
				in2.close();
			}
			
			
			if(out!=null){
				out.close();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	

	public static void match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs_using_Thread(
			String directoryAndName_pubtator_chemical2PMID_pairs,
			String directoryAndName_metabolomics2PMID_pair){
		
		try {
			
			String temp = null;
			String[] token_array = null;
			int greater_than = 0; 
			int less_than = 0;
			//int length_others = 0;
			
			for(int round = 9; round <= 9; round++){
				
				if(round == 1){
					greater_than = 30; 
					less_than = 60;
					//hmdb_metabolomics_names_List.size() = 95160
					//other metabolomics length = 282330
				}
				else if(round == 2){
					//hmdb_metabolomics_names_List.size() = 22340
					//other metabolomics length = 355150
					greater_than = 25; 
					less_than = 30;
				}
				else if(round == 3){
					greater_than = 20; 
					less_than = 25;
				}
				else if(round == 4){
					greater_than = 15; 
					less_than = 20;
				}
				else if(round == 5){
					greater_than = 10; 
					less_than = 11;
				}
				else if(round == 6){
					greater_than = 11; 
					less_than = 12;
				}
				else if(round == 7){
					greater_than = 12; 
					less_than = 13;
				}
				else if(round == 8){
					greater_than = 13; 
					less_than = 14;
				}
				else if(round == 9){
					greater_than = 14; 
					less_than = 15;
				}
	
				System.out.println("=========================== round "+ round +"========================");
				System.out.println("filter: greater_than = " + greater_than);
				System.out.println("filter: less_than = " + less_than);
				System.out.println("=====================================================================");
				

				File inputFile1 = new File("./data/metabolomics/hmdb_metabolomics_name_and_synonym.txt");
				
				BufferedReader in1 = new BufferedReader(
						new FileReader(inputFile1));
				
				ArrayList<String> hmdb_metabolomics_names_List = new ArrayList<String>();
				ArrayList<String> hmdb_metabolomics_accession_List = new ArrayList<String>();
				
				
				while ((temp = in1.readLine()) != null) {
					
					token_array = temp.split("\t");
					if(token_array[0].length() >= greater_than && token_array[0].length() < less_than){
						hmdb_metabolomics_names_List.add(token_array[0].toLowerCase());
						hmdb_metabolomics_accession_List.add(token_array[1]);
					}
					else{
						//length_others++;
					}
			}
				
				if(in1!=null){
					in1.close();
				}
				
			//System.out.println("======================================================================");
			//System.out.println("hmdb_metabolomics_names_List.size() = " + hmdb_metabolomics_names_List.size());
			//System.out.println("other metabolomics length = " + length_others);
			//System.out.println("======================================================================");
				
				
				//call Thread
				dispatch_number_of_matching_Thread dispatch_thread = new dispatch_number_of_matching_Thread(
						directoryAndName_pubtator_chemical2PMID_pairs,
						directoryAndName_metabolomics2PMID_pair,
						hmdb_metabolomics_names_List,
						hmdb_metabolomics_accession_List,
						greater_than,
						less_than
					);
				dispatch_thread.run();

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs(
			String directoryAndName_pubtator_chemical2PMID_pairs,
			String directoryAndName_metabolomics2PMID_pair){
		
		try {
			
			File outputFile = null;
			BufferedWriter out = null;
			
			File inputFile1 = new File("./data/metabolomics/hmdb_metabolomics_name_and_synonym.txt");
			
			BufferedReader in1 = new BufferedReader(
					new FileReader(inputFile1));
			
			ArrayList<String> hmdb_metabolomics_names_List = new ArrayList<String>();
			ArrayList<String> hmdb_metabolomics_accession_List = new ArrayList<String>();
			
			
			ArrayList<String> matched_hmdb_to_chem2pubtator_HMDB_name_List = new ArrayList<String>();
			ArrayList<String> matched_hmdb_to_chem2pubtator_HMDB_accession_List = new ArrayList<String>();
			ArrayList<Integer> matched_hmdb_to_chem2pubtator_HMDB_accession_count_List = new ArrayList<Integer>();
			ArrayList<String> matched_hmdb_to_chem2pubtator_PMID_List = new ArrayList<String>();
			
			
			String temp = null;
			String[] token_array = null;
			int greater_than = 0; 
			int less_than = 0;
			int length_others = 0;
			
			for(int round = 1; round <= 5; round++){
				
				if(round == 1){
					greater_than = 30; 
					less_than = 60;
					//hmdb_metabolomics_names_List.size() = 95160
					//other metabolomics length = 282330
				}
				else if(round == 2){
					//hmdb_metabolomics_names_List.size() = 22340
					//other metabolomics length = 355150
					greater_than = 25; 
					less_than = 30;
				}
				else if(round == 3){
					greater_than = 20; 
					less_than = 25;
				}
				else if(round == 4){
					greater_than = 15; 
					less_than = 20;
				}
				else if(round == 5){
					greater_than = 10; 
					less_than = 15;
				}
				else if(round == 6){
					greater_than = 5; 
					less_than = 10;
				}
				else if(round == 7){
					greater_than = 1; 
					less_than = 5;
				}
				
	
				System.out.println("=========================== round "+ round +"========================");
				System.out.println("filter: greater_than = " + greater_than);
				System.out.println("filter: less_than = " + less_than);
				System.out.println("=====================================================================");
				
				
				
			
				
				while ((temp = in1.readLine()) != null) {
					
					token_array = temp.split("\t");
					if(token_array[0].length() >= greater_than && token_array[0].length() < less_than){
						hmdb_metabolomics_names_List.add(token_array[0].toLowerCase());
						hmdb_metabolomics_accession_List.add(token_array[1]);
					}
					else{
						length_others++;
					}
				}
				
				if(in1!=null){
					in1.close();
				}
				
				System.out.println("======================================================================");
				System.out.println("hmdb_metabolomics_names_List.size() = " + hmdb_metabolomics_names_List.size());
				System.out.println("other metabolomics length = " + length_others);
				System.out.println("======================================================================");
				
				File inputFile2 = null;
				BufferedReader in2 = null;
				
				String[] token_string = null;
				String [] name_token = null;
				int processed_lines = 0;
				
				
				File directory_pubtator_chemical2PMID_pairs = new File(directoryAndName_pubtator_chemical2PMID_pairs);
				String[] file_Name_Array = directory_pubtator_chemical2PMID_pairs.list();
				
				System.out.println(directory_pubtator_chemical2PMID_pairs.getName() 
						+ " has " + file_Name_Array.length + " divided files");
				
				
				String outputFile_Path = directoryAndName_metabolomics2PMID_pair 
						+ "metabolomics2PMID_pair_gl"+greater_than+"/";
				
				File outputFile_directory = new File(outputFile_Path);
				if(!outputFile_directory.exists()){
					outputFile_directory.mkdirs();
				}
				
				File directory_metabolomics2PMID_pair = new File(outputFile_Path);
				String[] file_array_of_metabolomics2PMID_pair = directory_metabolomics2PMID_pair.list();
				
				int number_of_files = file_array_of_metabolomics2PMID_pair.length;
				
				for(int file = (number_of_files+1); file <= file_Name_Array.length; file++){
					
					
					
					
					
					inputFile2 = new File(directoryAndName_pubtator_chemical2PMID_pairs + "pubtator_chemical2PMID_pairs_"+file+".txt");
					in2 = new BufferedReader(
							new FileReader(inputFile2));
					
					System.out.println("================================================================");
					
					System.out.println("===========================" + inputFile2.getName() + "===================================");
	
					long startTime = System.currentTimeMillis() ; 
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					System.out.println("start time = " + dateFormat.format(date));
					System.out.println("================================================================");
					
					int i = 0;
					int n = 0;
					int index_hmdb = -1;
					int updated_count = -1;
					String updated_accession = null;
					String updated_PMID = null;
					boolean print_result_while_running = false;

					
					while ((temp = in2.readLine()) != null) {
						
						temp = temp.toLowerCase();
						processed_lines++;
						token_string = temp.split("\t");
	
						if(token_string[0].indexOf("|")!=-1){
							name_token = token_string[0].split("|");
						
							for(n = 0; n < name_token.length; n++){
								
								for(i = 0; i < hmdb_metabolomics_names_List.size(); i++){
									
									if(name_token[n].length() > 1){
										
										if(hmdb_metabolomics_names_List.get(i).equals(name_token[n])){	
											
											if(!matched_hmdb_to_chem2pubtator_HMDB_name_List.contains(name_token[n])){
												
												matched_hmdb_to_chem2pubtator_HMDB_name_List.add(name_token[n]);
												matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.add(1);
												matched_hmdb_to_chem2pubtator_HMDB_accession_List.add(hmdb_metabolomics_accession_List.get(i));
												matched_hmdb_to_chem2pubtator_PMID_List.add(token_string[1]);//PMID
												if(print_result_while_running){
													System.out.println(temp + "--->\t" 
													+ name_token[n] + "\t" 
													+ token_string[1] + "\t" 
													+ hmdb_metabolomics_accession_List.get(i));
												}
											}
											else{
												index_hmdb = matched_hmdb_to_chem2pubtator_HMDB_name_List.indexOf(name_token[n]);
												updated_count = matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.get(index_hmdb)+1;
												matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.set(index_hmdb, updated_count);
												
												updated_accession = matched_hmdb_to_chem2pubtator_HMDB_accession_List.get(index_hmdb)+","+hmdb_metabolomics_accession_List.get(i);
												matched_hmdb_to_chem2pubtator_HMDB_accession_List.set(index_hmdb, updated_accession);
												
												updated_PMID = matched_hmdb_to_chem2pubtator_PMID_List.get(index_hmdb)+","+token_string[1];
												matched_hmdb_to_chem2pubtator_PMID_List.set(index_hmdb, updated_PMID);
											}
										}
									}
								}
							}
						}
						else{
							
							
							for(i = 0; i < hmdb_metabolomics_names_List.size(); i++){
								
								if(token_string[0].length() > 1){
									
									if(hmdb_metabolomics_names_List.get(i).equals(token_string[0])){	
										
										if(token_string[0].length() > 1){
										
											if(!matched_hmdb_to_chem2pubtator_HMDB_name_List.contains(token_string[0])){
												
												matched_hmdb_to_chem2pubtator_HMDB_name_List.add(token_string[0]);
												matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.add(1);
												matched_hmdb_to_chem2pubtator_HMDB_accession_List.add(hmdb_metabolomics_accession_List.get(i));
												matched_hmdb_to_chem2pubtator_PMID_List.add(token_string[1]);//PMID
												if(print_result_while_running){
													System.out.println(temp + "--->\t" 
													+ token_string[0] + "\t" 
													+ token_string[1] + "\t" 
													+ hmdb_metabolomics_accession_List.get(i));
												}
											}
											else{
												index_hmdb = matched_hmdb_to_chem2pubtator_HMDB_name_List.indexOf(token_string[0]);
												updated_count = matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.get(index_hmdb)+1;
												matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.set(index_hmdb, updated_count);
												
												//updated_accession = matched_hmdb_to_chem2pubtator_HMDB_accession_List.get(index_hmdb)+","+hmdb_metabolomics_accession_List.get(i);
												//matched_hmdb_to_chem2pubtator_HMDB_accession_List.set(index_hmdb, updated_accession);
												
												updated_PMID = matched_hmdb_to_chem2pubtator_PMID_List.get(index_hmdb)+","+token_string[1];
												matched_hmdb_to_chem2pubtator_PMID_List.set(index_hmdb, updated_PMID);
											}
										}//if(name_token[n].length() > 1){
									}
								}
							}
						}
						
						if(true){
							if(processed_lines%10000==0){
								
								System.out.println(processed_lines);
								//System.out.println("----------------------------------------------------------------------------\n" + processed_lines 
								//		+ "\n----------------------------------------------------------------------------");
							}
						}
					}
					
					
					outputFile = new File(outputFile_Path + "metabolomics2PMID_pairs_"+file+".txt");
					out = new BufferedWriter(new FileWriter(outputFile));
					
					
					for(int m = 0; m < matched_hmdb_to_chem2pubtator_HMDB_name_List.size(); m++){
						
						out.write(matched_hmdb_to_chem2pubtator_HMDB_name_List.get(m)+ "\t" +
								matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.get(m)+ "\t" +
								matched_hmdb_to_chem2pubtator_HMDB_accession_List.get(m)+ "\t" +
								matched_hmdb_to_chem2pubtator_PMID_List.get(m)+ "\n"
						);
					}
					
					processed_lines= 0;
					matched_hmdb_to_chem2pubtator_HMDB_name_List.clear();
					matched_hmdb_to_chem2pubtator_HMDB_accession_List.clear();
					matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.clear();
					matched_hmdb_to_chem2pubtator_PMID_List.clear();
					
					//Date date1 = new Date();
					//System.out.println("Finish time = " + dateFormat.format(date1));
					long endTime = System.currentTimeMillis(); 
					long min = (endTime-startTime)/1000/60;
					long sec = ((endTime-startTime)/1000)%60;
					System.out.println("Execution time in " + min + " min. and " + sec + " sec.") ; 
					
					System.out.println("=========================== write to file ----> " + outputFile.getName() + "===========================");
					
					if(out!=null){
						out.close();
					}
					
					
				}//for(int round = 1..............
				
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public static void process_metabolomics_File_20180304(
    		String directoryAndName_input){
      	

    	File inputFile = 
				new File(directoryAndName_input); 
		
		System.out.println(inputFile.getName()
					+" is exists()? = " + inputFile.exists());
		
		
		String temp = null;
		BufferedReader in;
		int count = 0;
		String accession_ID = "";
		String metabolomics_name = "";

		String synonym_name = "";
		boolean isMetaboliteName = false;
		boolean foundAccession = false;
		try {
			in = new BufferedReader(
					new FileReader(inputFile));
			File outputFile = new File("./data/metabolomics/hmdb_metabolomics_name_and_synonym_0304.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
			
			while ((temp = in.readLine()) != null && count <= 1000000) { //&& count <= 1000000
				
				System.out.println(temp);
				count++;
				
				if(temp.indexOf("<metabolite>") !=-1){
					isMetaboliteName = true;
				}
				
				
				if(isMetaboliteName && !foundAccession && temp.indexOf("<accession>") !=-1){
					accession_ID = temp.substring(temp.indexOf("<accession>")+11, temp.indexOf("</accession>"));
					foundAccession = true;
				}
				
				
				if(isMetaboliteName && temp.indexOf("<name>") !=-1){
					metabolomics_name = temp.substring(temp.indexOf("<name>")+6, temp.indexOf("</name>"));
					out.write(metabolomics_name + "\t" + accession_ID + "\n");
					//System.out.println("==============================================>" + metabolomics_name);
					isMetaboliteName = false;
					foundAccession = false;
				}
				
				
				
				//[DISABLE the following code ---- 2018-03-04 by Michael Lee
				//if(temp.indexOf("<synonym>") !=-1 && temp.indexOf("</synonym>") !=-1){
				//	synonym_name = temp.substring(temp.indexOf("<synonym>")+9, temp.indexOf("</synonym>"));
				//	out.write(synonym_name + "\t" + accession_ID + "\n");
					//System.out.println("==============================================>" + synonym_name);
				//}
				
				
			}
			
			if(out!= null){
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
					
  		
	}
  		
	
	
	
	public static void process_pubtator_File_v20180303(
    		String directoryAndName_input){
      	

    	File inputFile = 
				new File(directoryAndName_input); 
		
		System.out.println(inputFile.getName()
					+" is exists()? = " + inputFile.exists());
		
		
		String temp = null;
		BufferedReader in;
		ArrayList<String> chemical2PMID_unique_name_List = new ArrayList<String>();
		
		try {
			in = new BufferedReader(
					new InputStreamReader(
							new GZIPInputStream(
									new FileInputStream(inputFile))));
			
			
			File outputFile = new File("./data/myPubtator/pubtator_chemical2PMID_pairs.txt");
			//File outputFile = new File("./data/pubtator/pubtator_chemical2PMID_unique_name.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
			String[] token_string = null;
			String[] name_token = null;
			
			int line = 0;
			int i = 0;
			while ((temp = in.readLine()) != null  && line <= 20000 ) { //&& line <= 20000
				
				System.out.println(temp);
				
				token_string = temp.split("\t");
				line++;
				
				if(token_string[2].indexOf("|")!=-1){
					name_token = token_string[2].split("|");
				
					for(i = 0; i < name_token.length; i++){
						if(!chemical2PMID_unique_name_List.contains(name_token[i])){
							chemical2PMID_unique_name_List.add(name_token[i]);
							
							if(name_token[i].length() > 1){
								out.write(name_token[i] + "\t" + token_string[0] + "\n");
							}
						}
					}
				}
				else{
					if(!chemical2PMID_unique_name_List.contains(token_string[2])){
						chemical2PMID_unique_name_List.add(token_string[2]);
						out.write(token_string[2] + "\t" + token_string[0] + "\n");
					}
				}
				
				
				
				if(line%1000000==0){
					System.out.println(line + " of lines processed!");
				}
				
				//out.write(token_string[2] + "\t" + token_string[0] + "\n");
			}
			
			if(out!=null){
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
					
  		
	}
	
	
	
	public static int count_pubtator_File(
    		String directoryAndName_input){
      	
		int count_total_lines = 0;
    	File inputFile = 
				new File(directoryAndName_input); 
		
		System.out.println(inputFile.getName()
					+" is exists()? = " + inputFile.exists());
		
		
		String temp = null;
		BufferedReader in;
		ArrayList<String> chemical2PMID_unique_name_List = new ArrayList<String>();
		
		try {
			in = new BufferedReader(
					new FileReader(inputFile));
			
			
			while ((temp = in.readLine()) != null) {
				count_total_lines++;
			}
			System.out.println(count_total_lines);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
  		
		return count_total_lines;
	}
}



class dispatch_number_of_matching_Thread implements Runnable{
	private static int running_thread = 0;
	
	
	public static int getRunning_thread() {
		return running_thread;
	}

	public static void setRunning_thread(int running_thread) {
		dispatch_number_of_matching_Thread.running_thread = running_thread;
	}
	private String directoryAndName_pubtator_chemical2PMID_pairs;
	private String directoryAndName_metabolomics2PMID_pair;
	private ArrayList<String> hmdb_metabolomics_names_List;
	private ArrayList<String> hmdb_metabolomics_accession_List;
	private int greater_than;
	private int less_than;
	
	public dispatch_number_of_matching_Thread(
			String directoryAndName_pubtator_chemical2PMID_pairs,
			String directoryAndName_metabolomics2PMID_pair,
			ArrayList<String> hmdb_metabolomics_names_List,
			ArrayList<String> hmdb_metabolomics_accession_List,
			int greater_than,
			int less_than
			){
		this.directoryAndName_metabolomics2PMID_pair = directoryAndName_metabolomics2PMID_pair;
		this.directoryAndName_pubtator_chemical2PMID_pairs = directoryAndName_pubtator_chemical2PMID_pairs;
		this.hmdb_metabolomics_names_List = hmdb_metabolomics_names_List;
		this.hmdb_metabolomics_accession_List = hmdb_metabolomics_accession_List;
		this.greater_than = greater_than;
		this.less_than = less_than;
	}
	
	
	public void run(){
		
		try {
			
			File directory_pubtator_chemical2PMID_pairs = new File(directoryAndName_pubtator_chemical2PMID_pairs);
			String[] file_Name_Array = directory_pubtator_chemical2PMID_pairs.list();
			
			//System.out.println(directory_pubtator_chemical2PMID_pairs.getName() 
			//		+ " has " + file_Name_Array.length + " divided files");
			int totalThread = 20;
			for(int file = 1; file <= file_Name_Array.length; file++){
				
					while(running_thread >= 20){
						Thread.sleep(600000);
						System.out.println(file + "--- wait for 10 mins");
					}

					//check the file
					String outputFile_Path = directoryAndName_metabolomics2PMID_pair 
							+ "metabolomics2PMID_pair_gl"+greater_than+"/";
			
					File outputFile_Path_Directory = new File(outputFile_Path);
					if( !outputFile_Path_Directory.exists()){
						 outputFile_Path_Directory.mkdirs();
					}

					File outputFile = new File(outputFile_Path + "metabolomics2PMID_pairs_"+file+".txt");
						
					if(!outputFile.exists()){
					
							match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs_Thread thread_match =
									new match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs_Thread(
											file,
											totalThread,
											directoryAndName_pubtator_chemical2PMID_pairs,
											directoryAndName_metabolomics2PMID_pair,
											hmdb_metabolomics_names_List,
											hmdb_metabolomics_accession_List,
											file,
											greater_than,
											less_than);
							thread_match.start();
							running_thread++;
					
					}
				
			}//for
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}



class match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs_Thread implements Runnable{
	private Thread t;
	private int partNum;
	private int totalThread;
	
	private String directoryAndName_pubtator_chemical2PMID_pairs;
	private String directoryAndName_metabolomics2PMID_pair;
	
	private ArrayList<String> hmdb_metabolomics_names_List;
	private ArrayList<String> hmdb_metabolomics_accession_List;
	private int file;
	private int greater_than;
	private int less_than;

	public match_hmdb_metabolomics_name_and_synonym_IN_pubtator_chemical2PMID_pairs_Thread(
			int partNum,
			int totalThread,
			String directoryAndName_pubtator_chemical2PMID_pairs,
			String directoryAndName_metabolomics2PMID_pair,
			ArrayList<String> hmdb_metabolomics_names_List,
			ArrayList<String> hmdb_metabolomics_accession_List,
			int file,
			int greater_than,
			int less_than
			){
		
		this.directoryAndName_metabolomics2PMID_pair = directoryAndName_metabolomics2PMID_pair;
		this.directoryAndName_pubtator_chemical2PMID_pairs = directoryAndName_pubtator_chemical2PMID_pairs;
		this.hmdb_metabolomics_names_List = hmdb_metabolomics_names_List;
		this.hmdb_metabolomics_accession_List = hmdb_metabolomics_accession_List;
		this.file = file;
		this.greater_than = greater_than;
		this.less_than = less_than;
	}

	
	public void start(){
		if(t==null){
			t = new Thread(this, Integer.toString(partNum));
			t.start();
		}
	}
	
	
	public void run(){

		try {
					
					ArrayList<String> matched_hmdb_to_chem2pubtator_HMDB_name_List = new ArrayList<String>();
					ArrayList<String> matched_hmdb_to_chem2pubtator_HMDB_accession_List = new ArrayList<String>();
					ArrayList<Integer> matched_hmdb_to_chem2pubtator_HMDB_accession_count_List = new ArrayList<Integer>();
					ArrayList<String> matched_hmdb_to_chem2pubtator_PMID_List = new ArrayList<String>();
					
					String temp = null;
					//String[] token_array = null;
					File outputFile = null;
					BufferedWriter out = null;
						
					File inputFile_pubtator_chemical2PMID_pairs = null;
					BufferedReader in_pubtator_chemical2PMID_pairs = null;
						
					String[] token_string = null;
					String [] name_token = null;
					int processed_lines = 0;
						
					inputFile_pubtator_chemical2PMID_pairs = new File(directoryAndName_pubtator_chemical2PMID_pairs + "pubtator_chemical2PMID_pairs_"+file+".txt");
					in_pubtator_chemical2PMID_pairs = new BufferedReader( new FileReader(inputFile_pubtator_chemical2PMID_pairs));
							
					System.out.println("===========================" 
					+ inputFile_pubtator_chemical2PMID_pairs.getName() 
					+  " ( hmdb_metabolomics_names_List.size() = " + hmdb_metabolomics_names_List.size()
					+ " ) ===================================");
				
						int i = 0;
						int n = 0;
						int index_hmdb = -1;
						int updated_count = -1;
						String updated_accession = null;
						String updated_PMID = null;
						boolean print_result_while_running = false;
		
						while ((temp = in_pubtator_chemical2PMID_pairs.readLine()) != null) {
								
								temp = temp.toLowerCase();
								processed_lines++;
								token_string = temp.split("\t");
			
								if(token_string[0].indexOf("|")!=-1){
									name_token = token_string[0].split("|");
								
									for(n = 0; n < name_token.length; n++){
										
										for(i = 0; i < hmdb_metabolomics_names_List.size(); i++){
											
											if(name_token[n].length() > 1){
												
												if(hmdb_metabolomics_names_List.get(i).equals(name_token[n])){	
													
													if(!matched_hmdb_to_chem2pubtator_HMDB_name_List.contains(name_token[n])){
														
														matched_hmdb_to_chem2pubtator_HMDB_name_List.add(name_token[n]);
														matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.add(1);
														matched_hmdb_to_chem2pubtator_HMDB_accession_List.add(hmdb_metabolomics_accession_List.get(i));
														matched_hmdb_to_chem2pubtator_PMID_List.add(token_string[1]);//PMID
														if(print_result_while_running){
															System.out.println(temp + "--->\t" 
															+ name_token[n] + "\t" 
															+ token_string[1] + "\t" 
															+ hmdb_metabolomics_accession_List.get(i));
														}
													}
													else{
														index_hmdb = matched_hmdb_to_chem2pubtator_HMDB_name_List.indexOf(name_token[n]);
														updated_count = matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.get(index_hmdb)+1;
														matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.set(index_hmdb, updated_count);
														
														updated_accession = matched_hmdb_to_chem2pubtator_HMDB_accession_List.get(index_hmdb)+","+hmdb_metabolomics_accession_List.get(i);
														matched_hmdb_to_chem2pubtator_HMDB_accession_List.set(index_hmdb, updated_accession);
														
														updated_PMID = matched_hmdb_to_chem2pubtator_PMID_List.get(index_hmdb)+","+token_string[1];
														matched_hmdb_to_chem2pubtator_PMID_List.set(index_hmdb, updated_PMID);
													}
												}
											}
										}
									}
								}
								else{
									
									
									for(i = 0; i < hmdb_metabolomics_names_List.size(); i++){
										
										if(token_string[0].length() > 1){
											
											if(hmdb_metabolomics_names_List.get(i).equals(token_string[0])){	
												
												if(token_string[0].length() > 1){
												
													if(!matched_hmdb_to_chem2pubtator_HMDB_name_List.contains(token_string[0])){
														
														matched_hmdb_to_chem2pubtator_HMDB_name_List.add(token_string[0]);
														matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.add(1);
														matched_hmdb_to_chem2pubtator_HMDB_accession_List.add(hmdb_metabolomics_accession_List.get(i));
														matched_hmdb_to_chem2pubtator_PMID_List.add(token_string[1]);//PMID
														if(print_result_while_running){
															System.out.println(temp + "--->\t" 
															+ token_string[0] + "\t" 
															+ token_string[1] + "\t" 
															+ hmdb_metabolomics_accession_List.get(i));
														}
													}
													else{
														index_hmdb = matched_hmdb_to_chem2pubtator_HMDB_name_List.indexOf(token_string[0]);
														updated_count = matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.get(index_hmdb)+1;
														matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.set(index_hmdb, updated_count);
														
														//updated_accession = matched_hmdb_to_chem2pubtator_HMDB_accession_List.get(index_hmdb)+","+hmdb_metabolomics_accession_List.get(i);
														//matched_hmdb_to_chem2pubtator_HMDB_accession_List.set(index_hmdb, updated_accession);
														
														updated_PMID = matched_hmdb_to_chem2pubtator_PMID_List.get(index_hmdb)+","+token_string[1];
														matched_hmdb_to_chem2pubtator_PMID_List.set(index_hmdb, updated_PMID);
													}
												}//if(name_token[n].length() > 1){
											}
										}
									}
								}
								
								if(true){
									if(processed_lines%10000==0){
										System.out.println("file=" + file + "----" + processed_lines);
										//System.out.println("----------------------------------------------------------------------------\n" + processed_lines 
										//		+ "\n----------------------------------------------------------------------------");
									}
								}
							}
						
						if(in_pubtator_chemical2PMID_pairs!=null){
							in_pubtator_chemical2PMID_pairs.close();
						}
							
							
						
						String outputFile_Path = directoryAndName_metabolomics2PMID_pair 
						+ "metabolomics2PMID_pair_gl"+greater_than+"/";
		
						File outputFile_Path_Directory = new File(outputFile_Path);
						if( !outputFile_Path_Directory.exists()){
							 outputFile_Path_Directory.mkdirs();
						}
						
						outputFile = new File(outputFile_Path + "metabolomics2PMID_pairs_"+file+".txt");
						out = new BufferedWriter(new FileWriter(outputFile));
							
							
							for(int m = 0; m < matched_hmdb_to_chem2pubtator_HMDB_name_List.size(); m++){
								
								out.write(matched_hmdb_to_chem2pubtator_HMDB_name_List.get(m)+ "\t" +
										matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.get(m)+ "\t" +
										matched_hmdb_to_chem2pubtator_HMDB_accession_List.get(m)+ "\t" +
										matched_hmdb_to_chem2pubtator_PMID_List.get(m)+ "\n"
								);
							}
							
							processed_lines= 0;
							matched_hmdb_to_chem2pubtator_HMDB_name_List.clear();
							matched_hmdb_to_chem2pubtator_HMDB_accession_List.clear();
							matched_hmdb_to_chem2pubtator_HMDB_accession_count_List.clear();
							matched_hmdb_to_chem2pubtator_PMID_List.clear();
								
							System.out.println("=========================== write to file ----> " + outputFile.getName() + "===========================");
							
							if(out!=null){
								out.close();
							}

							//
							int num_running_threads = dispatch_number_of_matching_Thread.getRunning_thread();
							dispatch_number_of_matching_Thread.setRunning_thread(num_running_threads-1);
							
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	
}













