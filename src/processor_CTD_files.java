import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class processor_CTD_files {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String directoryAndName_CTD_chemicals_tsv_gz = "./data/myCTD/CTD_chemicals.tsv.gz";
		String directoryAndName_CTD_ChemicalName = "./data/myCTD/CTD_chemicalName2ID";
		process_CTD_chemicals_tsv_gz(directoryAndName_CTD_chemicals_tsv_gz, directoryAndName_CTD_ChemicalName);
	}
	
	
	public static void process_CTD_chemicals_tsv_gz(
    		String directoryAndName_CTD_chemicals_tsv_gz,
    		String directoryAndName_CTD_ChemicalName){

    	File CTD_chemicals_tsv_gz_inputFile = 
				new File(directoryAndName_CTD_chemicals_tsv_gz); 
		
    	
    	ArrayList<String> CTD_ChemicalName_dataList = new ArrayList<String>();
		ArrayList<String> CTD_ChemicalID_dataList = new ArrayList<String>();
		ArrayList<String> CTD_ParentTreeNumbers_dataList = new ArrayList<String>();
		ArrayList<String> CTD_Synonyms_dataList = new ArrayList<String>();
		
		
		if (CTD_chemicals_tsv_gz_inputFile.exists()){
		
			try{
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
								new GZIPInputStream(
										new FileInputStream(CTD_chemicals_tsv_gz_inputFile))));
				
				
				/*********************************************************
				Fields:
				ChemicalName
				ChemicalID (MeSH identifier)
				CasRN (CAS Registry Number, if available)
				Definition
				ParentIDs (identifiers of the parent terms; '|'-delimited list)
				TreeNumbers (identifiers of the chemical's nodes; '|'-delimited list)
				ParentTreeNumbers (identifiers of the parent nodes; '|'-delimited list)
				Synonyms ('|'-delimited list)
				DrugBankIDs ('|'-delimited list)
				*********************************************************/
				
				String[] token_string = null;
				String temp = null;
				temp = in.readLine();
				//System.out.println("temp = " + temp);

				while ((temp = in.readLine()) != null) {	
					//System.out.println(temp);
					token_string = temp.split("\t");
					
					if(token_string.length == 7) {
						if(true) {
							//System.out.println(token_string.length);
							CTD_ChemicalName_dataList.add(token_string[0]);
							CTD_ChemicalID_dataList.add(token_string[1]);
							CTD_ParentTreeNumbers_dataList.add(token_string[6]);
							CTD_Synonyms_dataList.add("");
							//System.out.println(token_string[0]);
							//System.out.println(token_string[1]);
							//System.out.println("");
							//System.out.println(temp);
							//System.out.println("--------------------------------------------");
						}
					}
					else if(token_string.length == 8) {
						if(true) {
							//System.out.println(token_string.length);
							CTD_ChemicalName_dataList.add(token_string[0]);
							CTD_ChemicalID_dataList.add(token_string[1]);
							CTD_ParentTreeNumbers_dataList.add(token_string[6]);
							CTD_Synonyms_dataList.add(token_string[7]);
							//System.out.println(token_string[0]);
							//System.out.println(token_string[1]);
							//System.out.println(token_string[7]);
							//System.out.println("--------------------------------------------");
						}
					}
					else if(token_string.length == 9) {
						if(true) {
							//System.out.println(token_string.length);
							CTD_ChemicalName_dataList.add(token_string[0]);
							CTD_ChemicalID_dataList.add(token_string[1]);
							CTD_ParentTreeNumbers_dataList.add(token_string[6]);
							CTD_Synonyms_dataList.add(token_string[7]);
							//System.out.println(token_string[0]);
							//System.out.println(token_string[1]);
							//System.out.println(token_string[7]);
							//System.out.println("--------------------------------------------");
						}
					}
					else {
						//System.out.println("====================================");
						//System.out.println(token_string.length);
						//System.out.println(temp);
						//System.out.println("====================================");
					}
					
				}//end of while loop
				
				

				System.out.println("CTD_ChemicalName_dataList.size() = " +  CTD_ChemicalName_dataList.size());
				System.out.println("CTD_ChemicalID_dataList.size() = " + CTD_ChemicalID_dataList.size());
				System.out.println("CTD_ParentTreeNumbers_dataList.size() = " + CTD_ParentTreeNumbers_dataList.size());
				System.out.println("CTD_Synonyms_dataList.size() = " + CTD_Synonyms_dataList.size());
				
				
				
				BufferedWriter out_CTD_ChemicalName  
				= new BufferedWriter(
						new FileWriter(directoryAndName_CTD_ChemicalName));
				
				System.out.println("------------------ writing CTD_ChemicalName and CTD_ChemicalID to file ----------------");
				boolean print_output = false;
				for(int i = 0; i < CTD_ChemicalName_dataList.size(); i++) {
					
					out_CTD_ChemicalName.write(CTD_ChemicalName_dataList.get(i)
							+ "\t" + CTD_ChemicalID_dataList.get(i)
							+ "\t" + CTD_ParentTreeNumbers_dataList.get(i)
							+ "\t" + CTD_Synonyms_dataList.get(i) + "\n");
					if(print_output){
						System.out.println(CTD_ChemicalName_dataList.get(i));
						System.out.println(CTD_ChemicalID_dataList.get(i));
						System.out.println(CTD_ParentTreeNumbers_dataList.get(i));
						System.out.println(CTD_Synonyms_dataList.get(i));
						System.out.println("--------------------------------------------");
					}
				}
				
				if(out_CTD_ChemicalName!=null){
					out_CTD_ChemicalName.close();//close writer
				}
				
				System.out.println("------------------ finished writing CTD_ChemicalName and CTD_ChemicalID to file ----------------");
				
				
				if(in!=null) {
					in.close();
				}
				
				
			}catch ( IOException e) {
				e.printStackTrace();
			}
		}
	}//method: process_CTD_chemicals_csv_gz
		

}
