import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class process_FACTA_html_file {

	
	public static void main(String[] args) {
		
		boolean run = true;
		
		
		if(!run){
			//2018-01-10
			String query_term = "liver"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[DRUG]_liver.txt";
			String directoryAndName_FACTAplus_DrugList = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_liver.txt";
			
			
			get_Drug_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_DrugList);
		}
		
		
		if(!run){
			//2018-01-11
			String query_term = "brain"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[DRUG]_brain.txt";
			String directoryAndName_FACTAplus_DrugList = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_brain.txt";
			
			//665
			get_Drug_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_DrugList);
		}
		
		
		if(!run){
			//2018-01-11
			String query_term = "lung"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[DRUG]_lung.txt";
			String directoryAndName_FACTAplus_DrugList = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_lung.txt";
			
			//630
			get_Drug_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_DrugList);
		}
		
		
		if(!run){
			//2018-01-11
			String query_term = "kidney"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[DRUG]_kidney.txt";
			String directoryAndName_FACTAplus_DrugList = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_kidney.txt";
			
			//639
			get_Drug_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_DrugList);
		}
		
		
		
		if(!run){
			//2018-01-11
			String query_term = "neoplasm_cancer_carcinoma"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[DRUG]_neoplasm_cancer_carcinoma.txt";
			String directoryAndName_FACTAplus_DrugList = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_neoplasm_cancer_carcinoma.txt";
			
			//642
			get_Drug_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_DrugList);
		}
		
		
		if(!run){
			//2018-01-11
			String query_term = "cardiovascular_heart"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[DRUG]_cardiovascular_heart.txt";
			String directoryAndName_FACTAplus_DrugList = "./data/FACTAplus/FACTAplus_[DRUG]_DrugList_cardiovascular_heart.txt";
			
			//652
			get_Drug_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_DrugList);
		}
		
		
		
		
		/********************************************************
		 * Date: 2018-01-18
		 * Subject: FACT+ ---> "Compound"
		 * http://www.nactem.ac.uk/facta/cgi-bin/facta.cgi?query=liver|000001|1280|0
		 ********************************************************/
		if(!run){
			//2018-01-18
			String query_term = "liver"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[COMPND]_liver.txt";
			String directoryAndName_FACTAplus_COMPND_CASList = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_liver.txt";
			
			
			get_COMPND_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_COMPND_CASList);
		}
		
		if(!run){
			//2018-01-18
			String query_term = "lung"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[COMPND]_lung.txt";
			String directoryAndName_FACTAplus_COMPND_CASList = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_lung.txt";
			
			
			get_COMPND_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_COMPND_CASList);
		}
		
		
		if(!run){
			//2018-01-18
			String query_term = "kidney"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[COMPND]_kidney.txt";
			String directoryAndName_FACTAplus_COMPND_CASList = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_kidney.txt";
			
			
			get_COMPND_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_COMPND_CASList);
		}
		
		
		if(!run){
			//2018-01-18
			String query_term = "brain"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[COMPND]_brain.txt";
			String directoryAndName_FACTAplus_COMPND_CASList = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_brain.txt";
			
			
			get_COMPND_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_COMPND_CASList);
		}
		
		
		
		if(!run){
			//2018-01-18
			String query_term = "cardiovascular_heart"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[COMPND]_cardiovascular_heart.txt";
			String directoryAndName_FACTAplus_COMPND_CASList = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_cardiovascular_heart.txt";
			
			
			get_COMPND_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_COMPND_CASList);
		}
		
		
		
		if(!run){
			//2018-01-18
			String query_term = "neoplasm_cancer_carcinoma"; 
			String fileDirAndName_query_term = "./data/FACTAplus/FACTAplus_[COMPND]_neoplasm_cancer_carcinoma.txt";
			String directoryAndName_FACTAplus_COMPND_CASList = "./data/FACTAplus/FACTAplus_[COMPND]_CASID_neoplasm_cancer_carcinoma.txt";
			
			
			get_COMPND_FACTAplus_html_result(
					query_term, 
					fileDirAndName_query_term,
					directoryAndName_FACTAplus_COMPND_CASList);
		}
		
		
	}
	
	

	

	/********************************************************
	 * Date: 2018-01-11
	 * Method: get_Drug_FACTAplus_html_result()
	 * 
	 ********************************************************/
	
	public static void get_Drug_FACTAplus_html_result(
		String query_term, 
		String fileDirAndName_query_term,
		String directoryAndName_FACTAplus_DrugList) {

		try {
		
			ArrayList<String> FACTAplus_DrugBankID_list = new ArrayList<String>();
			ArrayList<String> FACTAplus_DrugBank_name_list = new ArrayList<String>();
			
	
			BufferedReader in = new BufferedReader(
					new FileReader(fileDirAndName_query_term));
			
			String temp = null;
			//String[] token_array = null;
			int DrugBankID_begin =-1;
			int DrugBank_name_begin =-1;
			String DrugBankID_temp = null;
			String DrugBank_name_temp = null;
			
			while ((temp = in.readLine()) != null) {					
				
				DrugBankID_begin = temp.indexOf("DrugBank:");
				
				if(DrugBankID_begin!=-1){
					System.out.println(temp);
					DrugBankID_temp = temp.substring(DrugBankID_begin+9, DrugBankID_begin+18);
					System.out.println(DrugBankID_temp);
					FACTAplus_DrugBankID_list.add(DrugBankID_temp);
					DrugBank_name_temp = temp.substring(temp.indexOf(">", DrugBankID_begin)+1, temp.indexOf("</a>", DrugBankID_begin));
					System.out.println(DrugBank_name_temp);
					FACTAplus_DrugBank_name_list.add(DrugBank_name_temp);
				}
				
			}
			
			System.out.println("FACTAplus_DrugBankID_list.size() = " + FACTAplus_DrugBankID_list.size());
			System.out.println("FACTAplus_DrugBank_name_list.size() = " + FACTAplus_DrugBank_name_list.size());
			
			
			if(in!=null){
				in.close();
			}
			
			
			BufferedWriter out_FACTAplus_DrugList  
			= new BufferedWriter(
					new FileWriter(directoryAndName_FACTAplus_DrugList));
			
			
			for(int i =0; i < FACTAplus_DrugBankID_list.size(); i++){
				
				out_FACTAplus_DrugList.write(FACTAplus_DrugBankID_list.get(i) 
						+ "\t" + FACTAplus_DrugBank_name_list.get(i)
						+ "\n");
			}
			
			
			if(out_FACTAplus_DrugList!=null){
				out_FACTAplus_DrugList.close();
			}
			
			
		} catch ( IOException e) {
			e.printStackTrace();
			
		}
	}
	
	
	
	/********************************************************
	 * Date: 2018-01-18
	 * Method: get_COMPND_FACTAplus_html_result()
	 * 
	 ********************************************************/

	public static void get_COMPND_FACTAplus_html_result(
			String query_term, 
			String fileDirAndName_query_term,
			String directoryAndName_FACTAplus_COMPND_CASList) {

			try {
			
				ArrayList<String> FACTAplus_COMPND_CASID_list = new ArrayList<String>();
				ArrayList<String> FACTAplus_COMPND_name_list = new ArrayList<String>();
				
		
				BufferedReader in = new BufferedReader(
						new FileReader(fileDirAndName_query_term));
				
				String temp = null;
				//String[] token_array = null;
				int CASID_begin =-1;
				int COMPND_name_begin =-1;
				String CASID_temp = null;
				String COMPND_name_temp = null;
				
				while ((temp = in.readLine()) != null) {					
					
					CASID_begin = temp.indexOf("CAS:");
					
					if(CASID_begin!=-1){
						System.out.println(temp);
						CASID_temp = temp.substring(CASID_begin+4, temp.indexOf("|", CASID_begin));
						System.out.println(CASID_temp);
						FACTAplus_COMPND_CASID_list.add(CASID_temp);
						COMPND_name_temp = temp.substring(temp.indexOf("\">", CASID_begin)+2, temp.indexOf("</a>", CASID_begin));
						System.out.println(COMPND_name_temp);
						FACTAplus_COMPND_name_list.add(COMPND_name_temp);
					}
					
				}
				
				System.out.println("FACTAplus_COMPND_CASID_list.size() = " + FACTAplus_COMPND_CASID_list.size());
				System.out.println("FACTAplus_COMPND_name_list.size() = " + FACTAplus_COMPND_name_list.size());
				
				
				if(in!=null){
					in.close();
				}
				
				
				BufferedWriter out_FACTAplus_DrugList  
				= new BufferedWriter(
						new FileWriter(directoryAndName_FACTAplus_COMPND_CASList));
				
				
				for(int i =0; i < FACTAplus_COMPND_CASID_list.size(); i++){
					
					out_FACTAplus_DrugList.write(FACTAplus_COMPND_CASID_list.get(i) 
							+ "\t" + FACTAplus_COMPND_name_list.get(i)
							+ "\n");
				}
				
				
				if(out_FACTAplus_DrugList!=null){
					out_FACTAplus_DrugList.close();
				}
				
				
			} catch ( IOException e) {
				e.printStackTrace();
				
			}
		}
		
}
