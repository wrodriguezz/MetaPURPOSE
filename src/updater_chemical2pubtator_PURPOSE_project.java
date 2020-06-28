
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.zip.GZIPInputStream;


//2017-10-22
//@Author Michael T.-L. Lee @Tainan, Taiwan
//email: michaelee0407@gmail.com
public class updater_chemical2pubtator_PURPOSE_project {

	public static void process00_generate_FTP_download_URL() throws IOException{
	
		File target_directory = new File("./data/FTP_Chem_downloaded_Files/");
		
		if(!target_directory.exists()){
			target_directory.mkdirs();
		}
		
		File data_files_URL = new File("./data/FTP_Chem_downloaded_Files/FTP_Chem_downloaded_URL.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(data_files_URL));
		out.write("ftp://ftp.ncbi.nlm.nih.gov/pub/lu/PubTator/chemical2pubtator.gz");
		out.newLine();
		out.write("http://ctdbase.org/reports/CTD_chemicals.tsv.gz");
		out.newLine();
		out.close();
	}
	
	
	
	//===================================================================
	public static void process00_download_required_files_from_FTP() throws IOException{
		
			String temp = null;
			ArrayList<String> URL_to_be_downloaded_List = new ArrayList<String> ();
			File data_files_URL = new File("./data/FTP_Chem_downloaded_Files/FTP_Chem_downloaded_URL.txt");
			process00_generate_FTP_download_URL();

			System.out.println("================== start downloading the following files =================");
			if(data_files_URL.exists()){
				BufferedReader in = new BufferedReader(new FileReader(data_files_URL));
				while ((temp = in.readLine()) != null) {
					URL_to_be_downloaded_List.add(temp);
					System.out.println(temp);
				}
				if(in!=null){
  					in.close();
  				}
			}
			System.out.println("===========================================================================");
				
			int attempt = 0;
			boolean download_using_FTP_thread = true;
	
			//---------------------- download file 1 --------------------------------------
			String chemical2pubtator_directoryAndName = "./data/FTP_Chem_downloaded_Files/chemical2pubtator.gz";
			File chemical2pubtator_File = new File(chemical2pubtator_directoryAndName);
			chemical2pubtator_File.createNewFile();
			attempt = 1;
			System.out.println("attempt "+attempt+": downloading chemical2pubtator.gz");
					
			try {
				URL url_string = new URL(URL_to_be_downloaded_List.get(0));
				if(download_using_FTP_thread){
					downloadFilefromFTP_Thread download_t1 = new downloadFilefromFTP_Thread(url_string,  
						chemical2pubtator_directoryAndName);
					download_t1.run();
				}
				else{
					download_File_from_URL(URL_to_be_downloaded_List.get(0),  chemical2pubtator_directoryAndName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			while(get_downloaded_file_SIZE(chemical2pubtator_File) < (290000000/1024/1024) && attempt < 10){
				attempt++;
				System.out.println("attempt "+attempt+": downloading chemical2pubtator.gz");
				
				if(download_using_FTP_thread == true){
					download_using_FTP_thread = false;
				}
				else{
					download_using_FTP_thread = true;
				}
				
				try {
					URL url_string = new URL(URL_to_be_downloaded_List.get(0));
					if(download_using_FTP_thread){
						downloadFilefromFTP_Thread download_t1 = new downloadFilefromFTP_Thread(url_string,  
							chemical2pubtator_directoryAndName);
						download_t1.run();
					}
					else{
						download_File_from_URL(URL_to_be_downloaded_List.get(0),  chemical2pubtator_directoryAndName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("chemical2pubtator.gz File size = " + get_downloaded_file_SIZE(chemical2pubtator_File) + " MB");
			//chemical2pubtator_File_size = 

			System.out.println("-----------------------------------------------");
			
			
			
			
			
			//---------------------- download file 2 --------------------------------------
			String CTD_chemicals_tsv_gz_directoryAndName = "./data/myCTD/CTD_chemicals.tsv.gz";
			File CTD_chemicals_tsv_gz_File = new File(CTD_chemicals_tsv_gz_directoryAndName);
			CTD_chemicals_tsv_gz_File.createNewFile();
			attempt = 1;
			System.out.println("attempt "+attempt+": downloading CTD_chemicals.tsv.gz");
					
			try {
				URL url_string = new URL(URL_to_be_downloaded_List.get(1));
				if(download_using_FTP_thread){
					downloadFilefromFTP_Thread download_t1 = new downloadFilefromFTP_Thread(url_string,  
							CTD_chemicals_tsv_gz_directoryAndName);
					download_t1.run();
				}
				else{
					download_File_from_URL(URL_to_be_downloaded_List.get(1),  CTD_chemicals_tsv_gz_directoryAndName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			while(get_downloaded_file_SIZE(chemical2pubtator_File) < (9000000/1024/1024) && attempt < 10){
				attempt++;
				System.out.println("attempt "+attempt+": downloading CTD_chemicals.tsv.gz");
				
				if(download_using_FTP_thread == true){
					download_using_FTP_thread = false;
				}
				else{
					download_using_FTP_thread = true;
				}
				
				try {
					URL url_string = new URL(URL_to_be_downloaded_List.get(1));
					if(download_using_FTP_thread){
						downloadFilefromFTP_Thread download_t1 = new downloadFilefromFTP_Thread(url_string,  
								CTD_chemicals_tsv_gz_directoryAndName);
						download_t1.run();
					}
					else{
						download_File_from_URL(URL_to_be_downloaded_List.get(1),  CTD_chemicals_tsv_gz_directoryAndName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("CTD_chemicals.tsv.gz File size = " + get_downloaded_file_SIZE(CTD_chemicals_tsv_gz_File) + " MB");
			
			System.out.println("-----------------------------------------------");
			
		
			System.out.println("==========================================================================");
			System.out.println("================== Successfully download all files =======================");
			System.out.println("==========================================================================");
			System.out.println("==========================================================================");
			
			System.out.println("\n");
			
			System.out.println("==========================================================================");
			System.out.println("================== Start generating pre-process files ====================");
			System.out.println("==========================================================================");
			System.out.println("==========================================================================");
			
	}
	


	/***************************************
     * 
     * To sort the PMID of file: chemical2pubtator.gz (downloaded from chemical2pubtator FTP)
     * 
     * @param directoryAndName_chemical2pubtator_gz, fileDirAndName_chemical2pubtator_TaxID_PMID_Sort
     */
    public static void process01_sort_PMID_chemical2pubtator_gz_complete(
    		String directoryAndName_chemical2pubtator_gz,  
    		String directoryAndName_CTD_chemicals_tsv_gz,
    		String fileDirAndName_chemical2pubtator_with_assigned_chemicalID,
    		String fileDirAndName_chemical2pubtator_PMID_Sort){
      	
    	
    	File CTD_chemicals_inputFile = 
				new File(directoryAndName_CTD_chemicals_tsv_gz); //  "./data/myCTD/CTD_chemicals.tsv.gz"
		
		ArrayList<String> CTD_ChemicalName_dataList = new ArrayList<String>();
		ArrayList<String> CTD_ChemicalID_dataList = new ArrayList<String>();
		ArrayList<String> CTD_CasRN_dataList = new ArrayList<String>();
		ArrayList<String> CTD_TreeNumbers_dataList = new ArrayList<String>();
		
		if (CTD_chemicals_inputFile.exists()){
		
			try{
				
				BufferedReader in_CTD = new BufferedReader(
						new InputStreamReader(
								new GZIPInputStream(
										new FileInputStream(CTD_chemicals_inputFile))));
    	
				String temp = null;
				String[] token_string = null;
				boolean begin_parsing_records = false;
				
				while ((temp = in_CTD.readLine()) != null) {
					
					if(!begin_parsing_records){
						
						if(temp.indexOf("#") == 0){
							begin_parsing_records = false;
						}
						else{
							begin_parsing_records = true;
						}
					}
					
					
					if(begin_parsing_records){
						
						token_string = temp.split("\t");
						
						if(token_string.length > 7){
							CTD_ChemicalName_dataList.add(token_string[0]);
							CTD_ChemicalID_dataList.add(token_string[1]);
							CTD_CasRN_dataList.add(token_string[2]);
							CTD_TreeNumbers_dataList.add(token_string[5]);
						}
					}
					
				}
				
				
				if(in_CTD!=null){
					in_CTD.close();
				}
				
    	
			} catch ( IOException e) {
				e.printStackTrace();
			}
		}
    	
    	

    	File chemical2pubtator_inputFile = 
				new File(directoryAndName_chemical2pubtator_gz); //"./data/myPubtator/chemical2pubtator.gz"
		
		ArrayList<PMID_to_chemicalID_data> PMID_to_chemicalID_dataList = new ArrayList<PMID_to_chemicalID_data>();
		
		
		ArrayList<String> chemical_MESH_CHEBI_ID_dataList = new ArrayList<String>();
		ArrayList<String> chemical_MESH_CHEBI_name_dataList = new ArrayList<String>();
		
		
		if (chemical2pubtator_inputFile.exists()){
		
			try{
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
								new GZIPInputStream(
										new FileInputStream(chemical2pubtator_inputFile))));
				
			
				String[] token_string = null;
				String temp = null;
				temp = in.readLine();//PMID	MeshID	Mentions	Resource
				
				//10	MESH:D004074	Digitoxin	tmChem|MeSH
				//1000	MESH:D009249	NADP	MeSH
				//1000006	MESH:D004958	estradiol	MeSH|tmChem
				//1000006	MESH:D011374	progesterone	MeSH|tmChem
				
				//System.out.println("temp = " + temp);

				
				int total_lines = 0;
			
				int index_MESH_CHEBI_ID = -1;
				int index_CTD_MESH = -1;
				
				while ((temp = in.readLine()) != null) {
					total_lines++;
					
					if(total_lines%100000==0){
						System.out.println(total_lines);
					}
					
					token_string = temp.split("\t");
  					
					if(token_string.length == 4){

						if(true){
							index_MESH_CHEBI_ID = chemical_MESH_CHEBI_ID_dataList.indexOf(token_string[1]);
							if(index_MESH_CHEBI_ID!=-1){	
								if( total_lines > 32000000){
									
									if( token_string[0].indexOf("hem")!=-1){
										System.out.println("skip this record --- " + temp);
										System.out.println(token_string[0]);
									}
									else{
										chemical_MESH_CHEBI_name_dataList.set(index_MESH_CHEBI_ID, chemical_MESH_CHEBI_name_dataList.get(index_MESH_CHEBI_ID));
										PMID_to_chemicalID_dataList.add(new PMID_to_chemicalID_data(Integer.parseInt(token_string[0]),  (index_MESH_CHEBI_ID+1)) );
									}
								}
								else{
									//update MESH_CHEBI_name
									//chemical_MESH_CHEBI_name_dataList.set(index_MESH_CHEBI_ID, chemical_MESH_CHEBI_name_dataList.get(index_MESH_CHEBI_ID)+"|"+token_string[2]);
									chemical_MESH_CHEBI_name_dataList.set(index_MESH_CHEBI_ID, chemical_MESH_CHEBI_name_dataList.get(index_MESH_CHEBI_ID));
									PMID_to_chemicalID_dataList.add(new PMID_to_chemicalID_data(Integer.parseInt(token_string[0]),  (index_MESH_CHEBI_ID+1)) );
								}
							}
							else{
								chemical_MESH_CHEBI_ID_dataList.add(token_string[1]);
								
								//2018-02-16 updated by Michael Lee
								index_CTD_MESH = CTD_ChemicalID_dataList.indexOf(token_string[1]);
								if(index_CTD_MESH!=-1){
									chemical_MESH_CHEBI_name_dataList.add(CTD_ChemicalName_dataList.get(index_CTD_MESH));
								}
								else{
									chemical_MESH_CHEBI_name_dataList.add(token_string[2]);
								}
								
								PMID_to_chemicalID_dataList.add(new PMID_to_chemicalID_data(Integer.parseInt(token_string[0]),  (chemical_MESH_CHEBI_ID_dataList.size())) );
							}
						}

					}

				}//while
				
				File file_chemical2pubtator_with_assigned_chemicalID = new File(
						fileDirAndName_chemical2pubtator_with_assigned_chemicalID);
			
				BufferedWriter out_assigned_chemicalID= new BufferedWriter(new FileWriter(file_chemical2pubtator_with_assigned_chemicalID));	
				
				out_assigned_chemicalID.write("chemID" + "\t"+ "MeshID" + "\t" +"Mentions" + "\n");
				
				for(int f = 0; f < chemical_MESH_CHEBI_ID_dataList.size(); f++){
					out_assigned_chemicalID.write((f+1) + "\t"+ chemical_MESH_CHEBI_ID_dataList.get(f) + "\t" + chemical_MESH_CHEBI_name_dataList.get(f) + "\n");
				}
				
				
				if(out_assigned_chemicalID!=null){
					out_assigned_chemicalID.close();
				}
				
				if(in!=null){
  					in.close();
  				}
				
				if(PMID_to_chemicalID_dataList.size() > 0){
					Collections.sort(PMID_to_chemicalID_dataList, new Comparator<PMID_to_chemicalID_data>() {
				        @Override
				        public int compare(PMID_to_chemicalID_data data1, PMID_to_chemicalID_data data2)
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
					
					
					File file_chemical2pubtator_PMID_Sort = new File(
							fileDirAndName_chemical2pubtator_PMID_Sort);
				
					BufferedWriter out_pair = new BufferedWriter(new FileWriter(file_chemical2pubtator_PMID_Sort));	
					
					System.out.println("=========>   PMID_to_chemicalID_dataList.size() = " + PMID_to_chemicalID_dataList.size());
					
					
					for(int a = 0 ; a < PMID_to_chemicalID_dataList.size(); a++){
						out_pair.write(PMID_to_chemicalID_dataList.get(a).getPMID()
								+ "\t" + PMID_to_chemicalID_dataList.get(a).getchemical_ID() + "\n");
					}
					
					if(out_pair!=null){
						out_pair.close();
					}
				
					System.out.println("data file 1: <" + fileDirAndName_chemical2pubtator_PMID_Sort + "> has been generated!");
				
				}//	if(PMID_to_chemicalID_dataList.size() > 0){

			} catch ( IOException e) {
				e.printStackTrace();
			}
		
		}//if (gene2pubtator_inputFile.exists()){

    }
    

	public static void process02_sort_chemID_chem2pubtator(
    		String chem2pubtator_with_chemID, String outputFile_Directory){
      	
		File chem2pubtator_TaxID_inputFile = 
					new File(chem2pubtator_with_chemID);
			
		ArrayList<chemicalID_to_PMID_data> matched_Pair_chemID_to_PMID_dataList = new ArrayList<chemicalID_to_PMID_data>();
		
		if (chem2pubtator_TaxID_inputFile.exists()){
		
			try{
				
				BufferedReader in = new BufferedReader(
						new FileReader(chem2pubtator_TaxID_inputFile));

				String[] token_string = null;
				String temp = null;
				//System.out.println(temp);
				
				while ((temp = in.readLine()) != null) {
					token_string = temp.split("\t");
					if(token_string.length == 2){
						matched_Pair_chemID_to_PMID_dataList.add(
								new chemicalID_to_PMID_data(Integer.parseInt(token_string[1]), Integer.parseInt(token_string[0])));
					}
				}//while
				
				if(in!=null){
  					in.close();
  				}
				
				Collections.sort(matched_Pair_chemID_to_PMID_dataList, new Comparator<chemicalID_to_PMID_data>() {
			        @Override
			        public int compare(chemicalID_to_PMID_data data1, chemicalID_to_PMID_data data2)
			        {
			        	if( data1.getchemical_ID() < data2.getchemical_ID()){
			        		return  -1;
			        	}
			        	if( data1.getchemical_ID() > data2.getchemical_ID()){
			        		return 1;
			        	}
			        	return 0;
			        }
			    });
				
				
				String fileDirAndName_chem2pubtator_Sort 
				= outputFile_Directory;
				
				File file_chem2pubtator_Sort = new File(
						fileDirAndName_chem2pubtator_Sort);
			
				BufferedWriter out_pair = new BufferedWriter(new FileWriter(file_chem2pubtator_Sort));	
				
				for(int a = 0 ; a < matched_Pair_chemID_to_PMID_dataList.size(); a++){
					out_pair.write(matched_Pair_chemID_to_PMID_dataList.get(a).getchemical_ID()
							+ "\t" + matched_Pair_chemID_to_PMID_dataList.get(a).getPMID());
					out_pair.newLine();
				}
				if(out_pair!=null){
					out_pair.close();
				}

  			} catch ( IOException e) {
  				e.printStackTrace();
  			}
			
  		}//if (gene2pubtator_inputFile.exists()){

    }
	
	
	
	
	

	/***************************************
	@param 
     */
    public static void process03_chemical2pubtator_ChemName_selection(
    		String fileDirAndName_chemical2pubtator_with_assigned_chemicalID,
    		String fileDirAndName_chemical2pubtator_with_chemicalID_and_SingleChemName
    		){

		try{
				File file_chemical2pubtator_with_assigned_chemicalID = new File(
						fileDirAndName_chemical2pubtator_with_assigned_chemicalID);
				
				
				BufferedReader in = new BufferedReader(
						new FileReader(file_chemical2pubtator_with_assigned_chemicalID));
				
				
				File file_chemical2pubtator_with_chemicalID_and_SingleChemName = new File(
						fileDirAndName_chemical2pubtator_with_chemicalID_and_SingleChemName);
				
				
				BufferedWriter out = new BufferedWriter(new FileWriter(file_chemical2pubtator_with_chemicalID_and_SingleChemName));	
				

				String[] token_string = null;
				String temp = null;
				temp = in.readLine();
				System.out.println(temp);//skip this line
				String chemID = "-";
				String MESHID = "-";
				String chemName = "-";
				String first_chemcial_name = null;
				String second_chemcial_name = null;
				int index_end_of_first_name = -1;
				int index_end_of_second_name = -1;
				
				while ((temp = in.readLine()) != null) {
					
					token_string = temp.split("\t");
					if(token_string.length == 3){
						chemID = token_string[0];
						MESHID = token_string[1];
						chemName = token_string[2];
						
						
						index_end_of_first_name = chemName.indexOf("|"); //get the index of first |
						index_end_of_second_name = chemName.indexOf("|", (index_end_of_first_name+1)); //get the index of second |
						
						if(index_end_of_first_name !=-1 ){//multiple Chemical Names 
							
							first_chemcial_name = null;
					  		second_chemcial_name = null;
							
							//System.out.println("-------------------------------> " + chem_Name);

							if(index_end_of_second_name !=-1){
								first_chemcial_name = chemName.substring(0, index_end_of_first_name);
								second_chemcial_name = chemName.substring((index_end_of_first_name+1), index_end_of_second_name);//select second name as Chemical Name 2018-02-08
							}
							else{
								first_chemcial_name = chemName.substring(0, index_end_of_first_name);
								second_chemcial_name = chemName.substring((index_end_of_first_name+1));
							}
							
							//System.out.println("first_chemcial_name = " + first_chemcial_name);
							//System.out.println("second_chemcial_name = " + second_chemcial_name);
							
							if(second_chemcial_name!=null){
								if(first_chemcial_name.length() > second_chemcial_name.length()){
									chemName = first_chemcial_name;
								}
								else{
									chemName = second_chemcial_name;
								}
								//System.out.println("multiple Chemical Names -> " + chem_Name);
							}
							else{
								chemName = first_chemcial_name;
								//System.out.println(" multiple Chemical Names -> " + chem_Name);
							}
							
						}
						

						out.write(chemID + "\t" + MESHID + "\t" + chemName + "\n");
						
					}
				}//while
				
				if(in!=null){
  					in.close();
  				}

				
				if(out!=null){
					out.close();
				}
			
				System.out.println("data file: <" + fileDirAndName_chemical2pubtator_with_chemicalID_and_SingleChemName + "> has been generated!");

		} catch ( IOException e) {
			e.printStackTrace();
		}
	
	

    }
    
	
	
    
	
    public static void process02_sort_PMID_chemical2pubtator(
    		String chemical2pubtator_Directory, String outputFile_Directory){
      	
		File chemical2pubtator_inputFile = 
					new File(chemical2pubtator_Directory);
			
		ArrayList<PMID_to_chemicalID_data> matched_Pair_gene_to_PMID_dataList = new ArrayList<PMID_to_chemicalID_data>();
		
	
		if (chemical2pubtator_inputFile.exists()){
		
			try{
				
				BufferedReader in = new BufferedReader(
						new FileReader(chemical2pubtator_inputFile));

				String[] token_string = null;
				String temp = null;
				
				while ((temp = in.readLine()) != null) {
					token_string = temp.split("\t");
					if(token_string.length == 2){
						matched_Pair_gene_to_PMID_dataList.add(
								new PMID_to_chemicalID_data(Integer.parseInt(token_string[0]), Integer.parseInt(token_string[1])));
					}
				}//while
				
				if(in!=null){
  					in.close();
  				}
				
				Collections.sort(matched_Pair_gene_to_PMID_dataList, new Comparator<PMID_to_chemicalID_data>() {
			        @Override
			        public int compare(PMID_to_chemicalID_data data1, PMID_to_chemicalID_data data2)
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
				
				
				String fileDirAndName_gene2pubtator_TaxID_PMID_Sort 
				= outputFile_Directory +"_PMID_Sort";
				
				File file_gene2pubtator_TaxID_PMID_Sort = new File(
						fileDirAndName_gene2pubtator_TaxID_PMID_Sort);
				
				BufferedWriter out_pair = new BufferedWriter(new FileWriter(file_gene2pubtator_TaxID_PMID_Sort));	
				
				for(int a = 0 ; a < matched_Pair_gene_to_PMID_dataList.size(); a++){
					out_pair.write(matched_Pair_gene_to_PMID_dataList.get(a).getPMID()
							+ "\t" + matched_Pair_gene_to_PMID_dataList.get(a).getchemical_ID());
					out_pair.newLine();
				}
				
				if(out_pair!=null){
					out_pair.close();
				}
			
			
  			} catch ( IOException e) {
  				e.printStackTrace();
  			}
			
  		}//if (gene2pubtator_inputFile.exists()){
			
		
    
    
    }
	
    
    
    public static void process02_count_chem2pubtator_chemID_Sort(
    		String fileDirAndName_chem2pubtator_chemID_Sort,
    		String fileDirAndName_chem2pubtator_chem2Count){
  		
		
  		String temp= null;
  		//String[] token_string = null;
  		ArrayList<String> total_unique_chemIDsList = new ArrayList<String>();
  		ArrayList<Integer> total_unique_chemIDs_PMIDCountList = new ArrayList<Integer>();
  		
  		File chem2pubtator_chemID_sorted_inputFile = new File(fileDirAndName_chem2pubtator_chemID_Sort);

  		//==================================================================================
  		// 
  		if (chem2pubtator_chemID_sorted_inputFile.exists()){
  			
  			try {
  				BufferedReader in = new BufferedReader(
  						new FileReader(chem2pubtator_chemID_sorted_inputFile));
  				
  				temp = in.readLine();
  				String[] token = temp.split("\t");
  				String current_chemID = token[0];
  				int current_PMID_count = 1;
  				
  				while ((temp = in.readLine()) != null) {
  					token = temp.split("\t");
  					if(current_chemID.equals(token[0])){
  						current_PMID_count++;
  					}
  					else{
  						total_unique_chemIDsList.add(current_chemID);
  						total_unique_chemIDs_PMIDCountList.add(current_PMID_count);
  						current_chemID = token[0];
  						current_PMID_count = 1;
  					}	 
  				}
  				if(in!=null){
  					in.close();
  				}
  				
  				System.out.println("total_unique_chemIDsList.size() = " + total_unique_chemIDsList.size());
  				
  				
				BufferedWriter out_chem2Count = new BufferedWriter(
						new FileWriter(fileDirAndName_chem2pubtator_chem2Count));	
				
				for(int i = 0; i < total_unique_chemIDsList.size(); i++){
					out_chem2Count.write(total_unique_chemIDsList.get(i)
							+"\t" + total_unique_chemIDs_PMIDCountList.get(i));
					out_chem2Count.newLine();
				}
				
				if(out_chem2Count != null){
					out_chem2Count.close();
				}
  				
  			} catch ( IOException e) {
  				e.printStackTrace();
  			}
  			
  		}
    
	}
    

	private static long get_downloaded_file_SIZE(File f) throws IOException{
		 long size = f.length(); 
		 return size/1024/1024; 
	}

	//Code from: https://www.programcreek.com/java-api-examples/java.nio.channels.Channels
	private static String download_File_from_URL(final String url, String path) {
	    try {
	        URL website = new URL(url);
	        long startTime = System.currentTimeMillis(); 
	        System.out.println("Downloading file to " + path);
	        System.out.println(url);
	        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	        FileOutputStream fos = new FileOutputStream(path);
	        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	        if(fos!=null){
	        	fos.close();
	        }
	        long endTime = System.currentTimeMillis(); 
			System.out.println("File downloaded in " + (endTime-startTime)/1000 + " sec.") ; 
	        return path;
	    } catch (Exception e) {
	        System.out.println("Error while downloading file " + url + ": " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}	

	
	private static void deleteDir(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            deleteDir(f);
	        }
	    }
	    file.delete();
	}
	 

    public static void main(String[] args) throws IOException  {
	
    	System.out.println("================================================================");
		System.out.println("================================================================");
		long startTime = System.currentTimeMillis() ; 
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("start time = " + dateFormat.format(date));
		System.out.println("================================================================");
		System.out.println("================================================================");
		
		//run this method to download all the required files from remote FTP site, such as pubtator FTP, and UniProt FTP.
		boolean run_process00_download_required_files_from_FTP = true;
		if(run_process00_download_required_files_from_FTP){
		
			process00_download_required_files_from_FTP();
		}
		
		
		File myPubtator_target_directory = new File("./data/myChem2Pubtator/");
		
		if(!myPubtator_target_directory.exists()){
			myPubtator_target_directory.mkdirs();
		}
		
		
		File myChemTemp_directory = new File("./data/myChemTemp/");
		
		if(!myChemTemp_directory.exists()){
			myChemTemp_directory.mkdirs();
		}
		

		String fileDirAndName_chemical2pubtator_inputFile = "./data/FTP_Chem_downloaded_Files/chemical2pubtator.gz";
		String fileDirAndName_CTD_chemicals_tsv_gz_inputFile = "./data/myCTD/CTD_chemicals.tsv.gz";
		String fileDirAndName_chemical2pubtator_with_assigned_chemicalID = "./data/myChem2Pubtator/chemical2pubtator_with_chemID";
		String fileDirAndName_chemical2pubtator_PMID_Sort 
		= "./data/myChem2Pubtator/chem2pubtator_PMID_Sort";
		
		
		process01_sort_PMID_chemical2pubtator_gz_complete(
					fileDirAndName_chemical2pubtator_inputFile,
					fileDirAndName_CTD_chemicals_tsv_gz_inputFile,
  					fileDirAndName_chemical2pubtator_with_assigned_chemicalID,
  					fileDirAndName_chemical2pubtator_PMID_Sort );
		
	
    	String fileDirAndName_chem2pubtator_chemID_sort = "./data/myChemTemp/chem2pubtator_chemID_sort";
    	process02_sort_chemID_chem2pubtator(fileDirAndName_chemical2pubtator_PMID_Sort,
        			fileDirAndName_chem2pubtator_chemID_sort);
    	
		String fileDirAndName_chem2pubtator_chem2Count = "./data/myChem2Pubtator/chem2pubtator_chem2count";
    	
		
		process02_count_chem2pubtator_chemID_Sort(
    			fileDirAndName_chem2pubtator_chemID_sort,
        		fileDirAndName_chem2pubtator_chem2Count);
		
    	
		boolean run_process03_chemical2pubtator_ChemName_selection = false;
		if(run_process03_chemical2pubtator_ChemName_selection){
	    	fileDirAndName_chemical2pubtator_with_assigned_chemicalID = "./data/myChem2Pubtator/chemical2pubtator_with_chemID";
			String fileDirAndName_chemical2pubtator_with_chemicalID_and_SingleChemName = "./data/myChem2Pubtator/chemical2pubtator_with_chemID_ChemName";
		
	    	process03_chemical2pubtator_ChemName_selection(
	        		fileDirAndName_chemical2pubtator_with_assigned_chemicalID,
	        		fileDirAndName_chemical2pubtator_with_chemicalID_and_SingleChemName);
	    	
		}
    	
    	//delete all the temp files and FTP downloaded files
    	if(true){
	    	File Directory_to_be_deleted_1 = new File("./data/FTP_Chem_downloaded_Files/");
	    	if(Directory_to_be_deleted_1.exists()){
	    		System.out.println("=========================== clean up downloaded temp files =========================");
	    		deleteDir(Directory_to_be_deleted_1);
	    		System.out.println("Files at ./data/FTP_Chem_downloaded_Files/ are deleted to save disk space!");
	    	}
	    	
	    	
	    	File Directory_to_be_deleted_2 = new File("./data/myChemTemp/");
	    	if(Directory_to_be_deleted_2.exists()){
	    		deleteDir(Directory_to_be_deleted_2);
	    		System.out.println("Files at ./data/myChemTemp/ are deleted to save disk space!");
	    	}
    	}
    	
    	
    	
    	System.out.println("========================================================================================");
    	System.out.println("========================================================================================");
    	System.out.println("========================================================================================");
    	System.out.println("========================================================================================");
    	System.out.println("======== Successfully update pre processed data Files for PURPOSE chemical2pubtator project =================");
    	System.out.println("========================================================================================");
    	System.out.println("========================================================================================");
    	System.out.println("========================================================================================");
    	System.out.println("========================================================================================");
    	
    	
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
    	
    }//end of main method

}//end of class


class downloadFilefromFTP_Thread implements Runnable{
	
	private URL url;
	private String path;
	
	public downloadFilefromFTP_Thread(URL url, String path){
		this.url = url;
		this.path = path;
	}
	
	public void run(){
		
		download_from_URL_and_saveTo_path(url, path);
		
	}
	

	public void download_from_URL_and_saveTo_path(URL website, String path) {
	    try {
	    	download_from_URL_and_saveTo_path_WithRetries(website, path);
	    } catch (InterruptedException e) {
	        // Continue execution
	    }
	}

	private void download_from_URL_and_saveTo_path_WithRetries(URL website, String path) throws InterruptedException {
	    while (!establish_channel_and_download(website, path)) {
	        sleep();
	    }
	}

	private boolean establish_channel_and_download(URL website, String path) {
	    try {
	    	long startTime = System.currentTimeMillis(); 
	        System.out.println("Downloading file to " + path);
	        System.out.println(url);
	    	ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	        FileOutputStream fos = new FileOutputStream(path);
	        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	        if(fos!=null){
	        	fos.close();
	        }
	        long endTime = System.currentTimeMillis();
			System.out.println("File downloaded in " + (endTime-startTime)/1000 + " sec.") ; 
	    } catch (Exception e) {
	        return false;
	    }
	    return true;
	}

	private void sleep() throws InterruptedException {
	    Thread.sleep(5000);
	}
	
}



class chemical_data{
	
	private String disease_topic;
	private String chemical_ID;
	private String  chemical_Name;
	private double PS_value;
	private int count_Big_P;
	private int count_T_intersect_P;
	private int total_citation_count;
	private double PartI_value;
	private double PartII_value;
	private double PartIII_value;

	public chemical_data(
			String disease_topic,
			String chemical_ID,
			String  chemical_Name, 
			double PS_value, 
			int count_Big_P,  
			int count_T_intersect_P, 
			int total_citation_count, 
			double PartI_value, 
			double PartII_value, 
			double PartIII_value){
		this.disease_topic= disease_topic;
		this.chemical_ID = chemical_ID;
		this.chemical_Name = chemical_Name;
		this.PS_value = PS_value;
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
	
	public String getchemical_ID() {
		return chemical_ID;
	}

	public void setUniProtKB_ID(String chemical_ID) {
		this.chemical_ID = chemical_ID;
	}

	public String getchemical_Name() {
		return chemical_Name;
	}

	public void setGene_Name(String chemical_Name) {
		this.chemical_Name = chemical_Name;
	}
	
	public double getPS_value() {
		return PS_value;
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






class chemicalID_to_PMID_data{
	
	private int chemical_ID;
	private int PMID;
	
	public chemicalID_to_PMID_data(int chemical_ID, int PMID){
		this.chemical_ID = chemical_ID;
		this.PMID = PMID;
	}
	
	public int getchemical_ID() {
		return chemical_ID;
	}

	public void setGeneID(int chemical_ID) {
		this.chemical_ID = chemical_ID;
	}

	public int getPMID() {
		return PMID;
	}

	public void setPMID(int pMID) {
		PMID = pMID;
	}
	
}


class PMID_to_chemicalID_data{
	
	private int PMID;
	private int chemical_ID;
	
	public PMID_to_chemicalID_data(int PMID, int chemical_ID){
		this.PMID = PMID;
		this.chemical_ID = chemical_ID;
	}
	
	public int getchemical_ID() {
		return chemical_ID;
	}

	public void setGeneID(int chemical_ID) {
		this.chemical_ID = chemical_ID;
	}

	public int getPMID() {
		return PMID;
	}

	public void setPMID(int pMID) {
		PMID = pMID;
	}
	
}


