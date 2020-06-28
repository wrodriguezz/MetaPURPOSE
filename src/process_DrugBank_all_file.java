import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class process_DrugBank_all_file {

	
	public static void main(String[] args) {
			
			boolean run = true;
			
			if(run){
				//2018-01-10
				String fileDirAndName_DrugBank_all_xml = "./data/drugbank_all_full_database/full_database.xml";
				String fileDirAndName_DrugBankID_to_Name = "./data/drugbank_all_full_database/DrugBankID_to_Name_0112.txt";
				process_DrugBank_all_xml(fileDirAndName_DrugBank_all_xml, fileDirAndName_DrugBankID_to_Name);
			}
			
	}
	
	
	
	public static void process_DrugBank_all_xml(
			String fileDirAndName_DrugBank_all_xml,
			String directoryAndName_DrugBankID_to_Name) {

		try {
		
			//ArrayList<String> FACTAplus_Drug_list = new ArrayList<String>();
	
	
			BufferedReader in = new BufferedReader(
					new FileReader(fileDirAndName_DrugBank_all_xml));
		
			String temp = null;
			int num_lines = 0;
			String drugbank_primary_id_string = null;
			String drugbank_id_string = null;
			String drugbank_name_string = null;
			boolean get_drugbank_name = false;
			boolean get_drugbank_id_primary = false;
			
			
			BufferedWriter out_DrugBankID_to_Name  
			= new BufferedWriter(
					new FileWriter(directoryAndName_DrugBankID_to_Name));
			
			String write_to_file_string = "";
			boolean print_line = false;
			boolean drug_interactions_begin = false;
			
			while ((temp = in.readLine()) != null) {					//&& num_lines <= 10000
				
				num_lines++;
				
				if(num_lines >= 7500000 && num_lines <= 7600000){
					System.out.println(temp);
				}
				
				
				if(num_lines%100000==0){
					System.out.println(num_lines);
				}
				
				if(temp.indexOf("<drug type")!=-1){
					get_drugbank_id_primary = true;
					write_to_file_string = "";
				}
				else if(get_drugbank_id_primary && temp.indexOf("<drugbank-id primary=\"true\">")!=-1){
					drugbank_primary_id_string = temp.substring(
					temp.indexOf(">", temp.indexOf("<drugbank-id primary=\"true\">"))+1, temp.indexOf("</drugbank-id>"));
					
					write_to_file_string = "";
					get_drugbank_name = true;
					get_drugbank_id_primary = false;
				}
				else if(temp.indexOf("<drugbank-id>")!=-1){
					drugbank_id_string = temp.substring(
					temp.indexOf(">", temp.indexOf("<drugbank-id>"))+1, temp.indexOf("</drugbank-id>"));
					
					if(drugbank_id_string.indexOf("APRD")!=-1 || drugbank_id_string.indexOf("EXPT")!=-1 || drugbank_id_string.indexOf("BIOD")!=-1){
						//System.out.print(drugbank_id_string + "\t");
						if(write_to_file_string.equals("")){
							write_to_file_string = drugbank_id_string;
						}
						else{
							write_to_file_string = write_to_file_string + "\t" + drugbank_id_string;
						}
					}
				}
				else if(temp.indexOf("<drug-interactions>")!=-1){
					drug_interactions_begin = true;
				}
				else if(temp.indexOf("</drug-interactions>")!=-1){
					drug_interactions_begin = false;
				}
				
				
				
				if(!drug_interactions_begin && temp.indexOf("<name>")!=-1){

					if(temp.indexOf("<name>")!=-1 && temp.indexOf("Trimetrexate")!=-1){
						System.out.println("==============================> " + temp);
					}
				}
				
				
				
				if(get_drugbank_name  && temp.indexOf("<name>")!=-1){
					drugbank_name_string = temp.substring(
					temp.indexOf(">", temp.indexOf("<name>"))+1, temp.indexOf("</name>"));
					//System.out.println(drugbank_name_string);
					
					if(write_to_file_string.equals("")){
						
					}
					else{
						write_to_file_string = write_to_file_string + "\t" + drugbank_name_string + "\n";
						out_DrugBankID_to_Name.write(write_to_file_string);
					}
					
					
					get_drugbank_name = false;
				}
				
				
				
				
				
				/****************************************************
				 
					<drug type="biotech" created="2005-06-13" updated="2017-11-06">
					  <drugbank-id primary="true">DB00001</drugbank-id>
					  <drugbank-id>BTD00024</drugbank-id>
					  <drugbank-id>BIOD00024</drugbank-id>
					  <name>Lepirudin</name>
					  
				  	<synthesis-reference/>
  					<indication>For the treatment of heparin-induced thrombocytopenia</indication>
				  
				  	<metabolism>Lepirudin is thought to be metabolized by release of amino acids via catabolic hydrolysis of the parent drug. However, con-clusive data are not available. About 48% of the administration dose is excreted in the urine which consists of unchanged drug (35%) and other fragments of the parent drug.</metabolism>
  
				  	<classification>
				    <description/>
				    <direct-parent>Peptides</direct-parent>
				    <kingdom>Organic Compounds</kingdom>
				    <superclass>Organic Acids</superclass>
				    <class>Carboxylic Acids and Derivatives</class>
				    <subclass>Amino Acids, Peptides, and Analogues</subclass>
				  	</classification>
  
				 * 	<drug-interactions>
				 * 
						<drug-interaction>
					      <drugbank-id>DB00604</drugbank-id>
					      <name>Cisapride</name>
					      <description>The serum concentration of Cisapride can be increased when it is combined with Lepirudin.</description>
					    </drug-interaction>
					    
				   	</drug-interactions>    
			    ****************************/
				
			}

			if(in!=null){
				in.close();
			}
			
			if(out_DrugBankID_to_Name!=null){
				out_DrugBankID_to_Name.close();
			}

		} catch ( IOException e) {
			e.printStackTrace();
			
		}
	}
		
	
	
}
