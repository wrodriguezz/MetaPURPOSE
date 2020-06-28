## server.R
# metabolomics/chemical text mining app server file
#
# Kun-Hsing Yu
# January 4, 2018

library(shinysky)
library(shinyjs)
library(V8)
library(ggplot2)
library(plotly)
options(shiny.reactlog=TRUE, shiny.sanitize.errors = FALSE)


#phewas_codes <- read.csv("data/PheWAS_code_translation_v1_2.txt", header = T, stringsAsFactors = F, sep="\t")
disease_autocomplete_list <- phewas_codes[,2]

#protein_autocomplete_list <- human_protein_names_mapping[,1]

jsOpenURLCode <- "shinyjs.openURL = function(param) {window.open (param);}"

createLink <- function(type,protein,species) {
  if (type=="uniprot") {
    sprintf('<a href="http://www.uniprot.org/uniprot/?query=%s&sort=score" target="_blank" class="btn btn-primary">Uniprot</a>',protein)
  } else if (type=="genecards") {
    sprintf('<a href="http://www.genecards.org/Search/Keyword?queryString=%s" target="_blank" class="btn btn-primary">Genecards</a>',protein)
  } else if (type=="peptideAtlas") {
    sprintf('<a href="https://db.systemsbiology.net/sbeams/cgi/PeptideAtlas/Search?search_key=%s&build_type_name=%s&action=GO" target="_blank" class="btn btn-primary">Peptide Atlas</a>',protein,species)
  } else if (type=="NCBIProtein") {
    sprintf('<a href="https://www.ncbi.nlm.nih.gov/protein/?term=%s" target="_blank" class="btn btn-primary">NCBI Protein</a>',protein)
  } else if (type=="phosphosite") {
    sprintf('<a href="http://www.phosphosite.org/simpleSearchSubmitAction.action?searchStr=%s" target="_blank" class="btn btn-primary">Phosphosite</a>',protein)
  } else if (type=="PubMed") {
    sprintf('<a href="https://www.ncbi.nlm.nih.gov/pubmed/?term=%s" target="_blank" class="btn btn-primary">%s</a>',protein,protein)
  } else if (type=="metabolite") {
    sprintf('<a href="http://www.hmdb.ca/metabolites/%s" target="_blank" class="btn btn-primary">%s</a>',protein,protein)
  } else if (type=="meshID") {
    if (substr(protein,1,4)=="MESH"){
      sprintf('<a href="https://id.nlm.nih.gov/mesh/%s.html" target="_blank" class="btn btn-primary">%s</a>',substr(protein,6,20),protein)
    } else {
      sprintf('<a href="http://www.ebi.ac.uk/chebi/searchId.do?chebiId=%s" target="_blank" class="btn btn-primary">%s</a>',substr(protein,6,20),protein)
    }
  }
}

createLinkMesh <- function(protein,species) {
  if (substr(protein,1,4)=="MESH"){
    sprintf('<a href="https://id.nlm.nih.gov/mesh/%s.html" target="_blank" class="btn btn-primary">%s</a>',substr(protein,6,20),protein)
  } else {
    sprintf('<a href="http://www.ebi.ac.uk/chebi/searchId.do?chebiId=%s" target="_blank" class="btn btn-primary">%s</a>',substr(protein,7,20),protein)
  }
}

aggVotes <- function (geneIds, votes, query, species) {
  return(sum(votes[votes[,1]==query & votes[,2]==species & votes[,3]==geneIds,4]))
}

countVotes <- function (outputTableFileVotes, query, species){
  print("votes")
  votes<<-read.table("data/votes.txt",header=F,stringsAsFactors=F,sep="\t")
  outputTableFileVotes[,dim(outputTableFileVotes)[2]]<-sapply(outputTableFileVotes[,2],aggVotes,votes,query,species)
  print(outputTableFileVotes[1,])
  return(outputTableFileVotes)
}

countVotesC <- function (outputTableFileVotes, query, species){
  print("votesC")
  votes<<-read.table("data/votesC.txt",header=F,stringsAsFactors=F,sep="\t")
  outputTableFileVotes[,dim(outputTableFileVotes)[2]]<-sapply(outputTableFileVotes[,2],aggVotes,votes,query,species)
  print(outputTableFileVotes[1,])
  return(outputTableFileVotes)
}

renderAuthors <- function (authors) {
  if (authors=="-"){
    return("N/A")
  } else {
    return(paste(authors," et al.",sep=""))
  }
}

# unused function findColon
findColon <- function (inputString){
  return(which(strsplit(inputString,"")[[1]]==":"))
}

server <- function(input, output, session) {
  output$selectionORInput = renderUI(
    if (is.null(input$queryType)) {
      return()
    } else if (input$queryType=="hpp") {
      selectInput("query", "Step 2: Select an HPP Targeted Area:",
                  c("Brain" = "brain",
                    "Cancers" = "cancer",
                    "Cardiovascular" = "cardiovascular",
                    "Diabetes" = "diabetes",
                    "Extreme conditions" = "hot+OR+cold+OR+alkaline+condition+OR+acidic+condition+OR+hypersaline+OR+radiation",
                    "EyeOME" = "eye+OR+ocular",
                    "Food and nutrition" = "food+OR+nutrition+OR+nutrients",
                    "Glycoproteomics" = "glycoproteins",
                    "Immune-peptidome" = "immune+OR+immune+system",
                    "Infectious diseases" = "infectious+OR+infection",
                    "Kidney and urine" = "kidney+OR+urine",
                    "Liver" = "liver+OR+hepatic",
                    "Mitochondria" = "mitochondria",
                    "Model organisms" = "rat+OR+mouse",
                    "Musculoskeletal" = "muscle+OR+bone+OR+musculoskeletal",
                    "PediOme" = "pediatric+OR+newborn+OR+infant+OR+toddler+OR+child+OR+adolescent",
                    "Plasma" = "plasma+OR+serum",
                    "Protein aggregation" = "protein+aggregation",
                    "Rheumatic disorders" = "rheumatic"),selected = NULL)
                  #c("Brain","Cancer","Cardiovascular","Diabetes","Epigenetic chromatin",
                  #  "Extreme conditions","Eye","Food and Nutrition","Glycoproteomics",
                  #  "Immune","Infectious diseases","Kidney and Urine",
                  #  "Liver","Mitochondria","Model organisms","Musculoskeletal",
                  #  "PediOme","Plasma","Protein aggregation","Respiratory","Stem cells",
                  #  "Toxicoproteomics"),selected = NULL)
    } else if (input$queryType=="diseases") {
      h3("Disease (PheWAS Code):")
      textInput.typeahead(id="query",
                          #width = '100%',
                          placeholder="Step 2: Enter disease here",
                          local=data.frame(name=c(disease_autocomplete_list)),
                          valueKey = "name",
                          tokens=c(1:length(disease_autocomplete_list)),
                          template = HTML("<p class='repo-language'>{{info}}</p> <p class='repo-name'>{{name}}</p>")
      )
    } else if (input$queryType=="organ") {
      selectInput("query", "Step 2: Select an organ system:",
                  c("Cardiovascular / Circulatory system" = "cardiovascular+system+OR+circulatory+system",
                    "Digestive system / Excretory system"="digestive+system+OR+excretory+system",
                    "Endocrine system"="endocrine+system",
                    "Integumentary system"="integumentary+system", 
                    "Lymphatic system / Immune system"="lymphatic+system+OR+immune+system", 
                    "Muscular system"="muscular+system", 
                    "Nervous system"="nervous+system",
                    "Renal system / Urinary system"="renal+system+OR+urinary+system", 
                    "Reproductive system"="reproductive+system", 
                    "Respiratory system"="respiratory+system", 
                    "Skeletal system"="skeletal+system"
                  ),selected = NULL)
    } else if (input$queryType=="custom") {
      textInput("query", "Step 2: Input custom topic query below:", "", width = "100%")
    }
  )
  renderOutputTable<-function(outputTableFile){
    #print(outputTableFile[1,])
    #outputTableFile[,2]<-substr(outputTableFile[,2],1,6)
    #outputTable<<-outputTableFile[,c(1,4,5,6,7,8,12,2)]
    outputTable<<-outputTableFile[,c(1:7,11)]
    #print(outputTable[1,])
    colnames(protein_names_mappingSelected)[1]<-"Gene_Name"
    #outputTableVotes<<-join(outputTable, protein_names_mappingSelected, by = "Gene_Name")
    outputTableVotes<<-outputTable
    colnames(outputTableVotes)<<-c("Rank","HMDB_ID","Metabolite Name","Score","#P","#TP","#Citation/yr","Votes")
    #print(outputTableVotes[1:2,])
    output$table <- renderDataTable({
      print(outputTableVotes[1:2,])
      outputTableVotes$HMDB_ID <- createLink("metabolite", outputTableVotes$HMDB_ID, input$species)
      #outputTableShow<-outputTableVotes[,c(1:2,9,3:6,10,7)]
      outputTableShow<-outputTableVotes
      outputTableShow[["Actions"]]<-
        paste0('<div class="btn-group" role="group" aria-label="votes">
               <button type="button" class="btn btn-secondary upvote" id=upvote_',1:nrow(outputTableShow),'>Upvote</button>
               <button type="button" class="btn btn-secondary downvote"id=downvote_',1:nrow(outputTableShow),'>Downvote</button></div>')
      return(outputTableShow)}, options=list(rownames = FALSE), escape = FALSE)
  }
  #output$viewPublicationsTable<-renderDataTable({outputTableFile[outputTableFile[,4]==input$viewPublicationsProtein,]})
  output$viewPublicationsTable<-renderDataTable({
    viewPublicationsTop100Show[viewPublicationsTop100Show[,2]==input$viewPublicationsMetabolite,c(8,3:7)]
    }, escape=FALSE)
  output$downloadPublicationData <- downloadHandler(
    filename = function() { paste(input$query, outputTableFile[outputTableFile[,3]==input$viewPublicationsMetabolite,4],'Publications.csv', sep='_') },
    content = function(file) {
      write.csv(viewPublicationsTop100Show[viewPublicationsTop100Show[,2]==input$viewPublicationsMetabolite,c(1,3:7)], file, row.names=F)
    }
  )
  
  observeEvent(input$runPubCit, {
    print(input$pubcitN)
    pubcitPlotTable<-outputTableFile[input$pubcitN[1]:input$pubcitN[2],]
    print(pubcitPlotTable[1:2,])
    yAxis <- list(
      title = "log10(value)"
    )
    pubcitPlotObject<-plot_ly(pubcitPlotTable, x=~Rank, y=~log10(Big_P), name="#P", type="scatter", mode="markers", text=~metabolite_Name) %>%
      add_trace(y=~log10(T.P), name="#TP", mode="markers") %>%
      add_trace(y=~log10(Citation), name="#Citations/year", mode="markers") %>%
      layout(yaxis = yAxis)
    output$pubcitPlot <- renderPlotly({ 
      pubcitPlotObject
    })
  })
  observeEvent(input$runDAVID, {
    print(input$davidN)
    urlPart<-outputTableFile[1,3]
    if (input$davidN>1){
      for (i in 2:input$davidN){
        urlPart<-paste(urlPart,outputTableFile[i,3],sep=",")
      }
    }
    js$openURL(paste("http://david.abcc.ncifcrf.gov/api.jsp?type=ENTREZ_GENE_ID&ids=",urlPart,"&tool=summary",sep=""))
    #browseURL(paste("http://david.abcc.ncifcrf.gov/api.jsp?type=ENTREZ_GENE_ID&ids=",urlPart,"&tool=summary",sep=""))
  })
  observeEvent(input$runPMI, {
    print(input$pmiN)
    urlPart<-outputTableFile[1,3]
    if (input$pmiN==1){
      js$openURL(paste("http://stitch.embl.de/cgi/network.pl?identifier=",urlPart,"&species=",speciesID,sep=""))
      #browseURL(paste("http://stitch.embl.de/cgi/network.pl?identifier=",urlPart,"&species=9606",sep=""))
    } else {
      for (i in 2:input$pmiN){
        urlPart<-paste(urlPart,outputTableFile[i,3],sep="%0D")
      }
      js$openURL(paste("http://stitch.embl.de/cgi/network.pl?identifiers=",urlPart,"&species=",speciesID,sep=""))
      #browseURL(paste("http://stitch.embl.de/cgi/network.pl?identifiers=",urlPart,"&species=9606",sep=""))
    }
  })
  observeEvent(input$lastClick,{
    print ("XXX")
    print(outputTableFileVotes[1:2,])
    if (substr(input$lastClickId,1,6)=="upvote") {
      row=as.numeric(gsub("upvote_","",input$lastClickId))
      write.table(t(c(input$query,input$species,outputTableVotes[row,2],"1",as.character(Sys.time()))),sep="\t",append=T,file="data/votes.txt",quote=F,row.names=F,col.names=F)
    } else if (substr(input$lastClickId,1,6)=="downvo") {
      row=as.numeric(gsub("downvote_","",input$lastClickId))
      write.table(t(c(input$query,input$species,outputTableVotes[row,2],"-1",as.character(Sys.time()))),sep="\t",append=T,file="data/votes.txt",quote=F,row.names=F,col.names=F)
    }
    outputTableFileVotes<<-countVotes(outputTableFileVotes, isolate(input$query), isolate(input$species))
    renderOutputTable(outputTableFileVotes)
  })
  observeEvent(input$run, {
    #dirName<-tolower(paste(isolate(input$query),"+AND+",isolate(input$species),sep=""))
    dirName<-paste(tolower(isolate(input$query)),"+AND+",tolower(isolate(input$species)),sep="")
    dirName<-gsub(" ", "+", dirName)
    #dirName<-tolower(isolate(input$queryC))
    print (dirName)
    outputTableFilename<-paste("PURPOSE_metabolite_Results/",isolate(input$species),"/",dirName,"_PURPOSE[metabolite]",sep="")
    #outputTableFilename<-paste("PURPOSE_metabolite_Results/",dirName,"_PURPOSE[metabolite]",sep="")
    
    if (!file.exists(outputTableFilename)){
      # run query
      print("running query")
      print(paste("java -Xmx2048m PURPOSE_meta2pubtator",isolate(input$query), isolate(input$species), sep=" "))
      #progressMsg<<-""
      #logFile<-reactiveFileReader(500,session,filePath=paste("progressLog/",dirName,".txt",sep=""),readLines)
      #progressMsg<<-"Running query... It might take a few seconds..."
      #print(progressMsg)
      system(paste("java -Xmx2048m PURPOSE_meta2pubtator ", isolate(input$query)," ", isolate(input$species)," | tee progressLog/",dirName,".txt", sep=""))
      #system(paste("java -Xmx2048m PURPOSE_meta2pubtator ", isolate(input$query)," | tee progressLog/",dirName,".txt", sep=""))
    }
    print("Completed running the java code")
    print(outputTableFilename)
    if (file.exists(outputTableFilename)) {
      outputTableSummary<<-read.table(outputTableFilename,stringsAsFactors=F,sep="\t",nrows=2)
      bigT<<-outputTableSummary[1,2]
      bigF<<-outputTableSummary[2,2]
      outputTableFile<<-read.table(outputTableFilename,header=T,comment.char="",stringsAsFactors=F,sep="\t",fill=T,quote="",skip=2)
      # remove blacklisted proteins
      if (input$excludeBlacklisted == T){
        blacklistThisQuery<-blacklist[(blacklist[,1] == isolate(input$query) & blacklist[,2] == isolate(input$species)),]
        outputTableFile<<-outputTableFile[!outputTableFile[,1] %in% blacklistThisQuery[,3],]
      }
      outputTableFile<<-cbind((1:dim(outputTableFile)[1]),outputTableFile)
      colnames(outputTableFile)[1]<<-"Rank"
      # count votes
      outputTableFileVotes<<-cbind(outputTableFile,rep(0,dim(outputTableFile)[1]))
      colnames(outputTableFileVotes)[dim(outputTableFileVotes)[2]]<-"Votes"
      outputTableFileVotes<-countVotes(outputTableFileVotes, isolate(input$query), isolate(input$species))
      print("run...")
      print(outputTableFileVotes[1,])
      if (isolate(input$species)=="human") {
        protein_names_mappingSelected<<-human_protein_names_mapping
        speciesID<<-"9606"
      } else if (isolate(input$species)=="mouse") {
        protein_names_mappingSelected<<-mouse_protein_names_mapping
        speciesID<<-"10090"
      } else if (isolate(input$species)=="rat") {
        protein_names_mappingSelected<<-rat_protein_names_mapping
        speciesID<<-"10116"
      } else if (isolate(input$species)=="fly") {
        protein_names_mappingSelected<<-fly_protein_names_mapping
        speciesID<<-"7227"
      } else if (isolate(input$species)=="worm") {
        protein_names_mappingSelected<<-worm_protein_names_mapping
        speciesID<<-"6239"
      } else if (isolate(input$species)=="yeast") {
        protein_names_mappingSelected<<-yeast_protein_names_mapping
        speciesID<<-"4932"
      }
      output$queryValue <- renderText({ paste("Query term: ",isolate(input$query),"\n",
                                              "Species: ",isolate(input$species),"\n",
                                              "Number of publications on ",isolate(input$query)," in ",isolate(input$species),": ",dim(outputTableFile)[1],"\n",sep="") })
      output$summary <- renderUI({ 
        tagList(
          strong("Query term: "),code(isolate(input$query)),br(),
          strong("Species: "),code(isolate(input$species)),br(),
          #em("***BELOW CONTAINS PLACEHOLDERS***"),br(),code(sample(1:10000,1)),
          strong("Number of publications associated with the query:"),code(bigT),br(),
          strong("Number of publications in the PubMed database associated with any metabolites:"),code(bigF),br(),
          strong("Number of metabolites associated with the retrieved publications:"),code(dim(outputTableFile)[1]),br(),
          #strong("Number of retrieved publications associated with metabolites:"),code(outputTableFile[1,9]),br(),
          #strong("Number of average citations per year of the retrieved publications associated with metabolites:"),code(sample(1:10000,1)),br(),
          #strong("Number of publications in the PubMed database:"),code(sample(1:10000,1)),br(),
          br(),br(),br(),h3("Prioritized List",align="center"),
          h6("Descriptions:"),
          h6("#T = the number of paper on this topic; #TP = the number of paper on this topic and this metabolite; #Citation/yr = the total number of citations per year"),
          h6("Votes: user voting on the relevance of the metabolite to the queried topic. Please note that infrequently accessed topics may have smaller numbers of votes.")
        )
      })
      print(outputTableFile[1:2,])
      output$scoreScatterPlot <- renderPlotly({ 
        yAxis <- list(
          title = "log10(Publication Score)"
        )
        plot_ly(outputTableFile, x=~Rank, y=~log10(PURPOSE_Score), text=~metabolite_Name) %>%
          layout(yaxis = yAxis)
      })
      output$viewScoreDistribution <- renderUI({ 
        tagList(
          h3("View the Distribution of Publication Scores",align = "center"),
          h5("(it may take a few seconds to load the whole figure)",align="center"),
          plotlyOutput("scoreScatterPlot")
        )
      })
      proteinMin<-min(1,dim(outputTableFile)[1])
      proteinMax<-min(1000,dim(outputTableFile)[1])
      proteinDefault<-min(20,dim(outputTableFile)[1])
      output$viewPubCit <- renderUI({ 
        tagList(
          h3("View Publication/Citation Numbers of the Top-ranking Metabolites",align = "center"),br(),
          p("This module visualized the number of publication and the number of citations per year for each of the retrieved metabolite."),
          sliderInput("pubcitN", "View metabolites whose publication score ranking are at the following range:", min=proteinMin, max=proteinMax, step=1, value=c(proteinMin,proteinDefault), width='100%'),br(),
          #sliderInput("pubcitN", "N=", min=1, max=dim(outputTableFile)[1], value=c(1,20)),br(),
          actionButton("runPubCit", "Run", width = "100%"),
          plotlyOutput("pubcitPlot"),
          h6("Legend: #T = the number of paper on this topic; #TP = the number of paper on this topic and this metabolite; #Citations/year = the total number of citations per year")
        )
      })
      output$viewDAVID <- renderUI({ 
        tagList(
          h3("View the DAVID Functional Annotation Summary of the Top N Metabolites",align = "center"),br(),
          p("This module conducts enrichment analysis using the Database for Annotation, Visualization and Integrated Discovery (DAVID)."),
          p("The result page will be shown in a new tab."),
          em("Please check the pop-up blocker of your browser if the results do not show up after clicking 'Run DAVID.'"),
          sliderInput("davidN", "N=", min=proteinMin, max=proteinMax, step=1, value=proteinDefault, width='100%'),br(),
          useShinyjs(),
          extendShinyjs(text = jsOpenURLCode),
          actionButton("runDAVID", "Run DAVID", width = "100%")
        )
      })
      output$viewPMIs <- renderUI({ 
        tagList(
          h3("View Protein-Metabolite Interactions (PMI) among the Top N Metabolites",align = "center"),br(),
          p("This module conducts PMI analysis using the STITCH Database."),
          p("The result page will be shown in a new tab."),
          em("Please check the pop-up blocker of your browser if the results do not show up after clicking 'View PMI.'"),
          sliderInput("pmiN", "N=", min=proteinMin, max=proteinMax, step=1, value=proteinDefault, width='100%'),br(),
          useShinyjs(),
          extendShinyjs(text = jsOpenURLCode),
          actionButton("runPMI", "View PMI", width = "100%")
        )
      })
      viewPublicationsTop100FileFlag<-0
      viewPublicationsTop100Filename<-paste("PURPOSE_metabolite_Results/",isolate(input$species),"/",dirName,"_TOP100[metabolite]",sep="")
      #viewPublicationsTop100Filename<-paste("PURPOSE_metabolite_Results/",dirName,"_TOP100[metabolite]",sep="")
      if (file.exists(viewPublicationsTop100Filename)) {
        fileInfo <- file.info(viewPublicationsTop100Filename)
        if (fileInfo$size>0) {
          viewPublicationsTop100FileFlag<-1
          viewPublicationsListMetaboliteID<-outputTableFile[1:100,3]
          names(viewPublicationsListMetaboliteID)<-outputTableFile[1:100,3]
          viewPublicationsTop100<-read.table(viewPublicationsTop100Filename,header=F,comment.char="",stringsAsFactors=F,sep="\t",fill=T,quote="")
          viewPublicationsTop100<-viewPublicationsTop100[order(viewPublicationsTop100[,7],decreasing=T),]
          #viewPublicationsTop100[,3]<-paste(viewPublicationsTop100[,3]," et al.",sep="")
          viewPublicationsTop100[,3]<-unlist(lapply(viewPublicationsTop100[,3],renderAuthors))
          colnames(viewPublicationsTop100)<-c("PMID","Metabolite","Authors","Title","Journal","Year","Total Citations")
          viewPublicationsTop100$PMIDLink<-createLink("PubMed", viewPublicationsTop100$PMID, "")
          viewPublicationsTop100Show<<-viewPublicationsTop100
          output$viewPublications <- renderUI({ 
            tagList(
              h3("View Publications of the Top Metabolites",align = "center"),br(),
              p("This module shows the highly-cited (>= 5 citations per year) publications for each of the top topics, sorted by the number of citations."),
              em("The table would be empty if there is no highly-cited publication matching the metabolite and the topic."),
              #em("***PLACEHOLDERS BELOW***"),br(),
              selectInput("viewPublicationsMetabolite", "Choose a metabolite:",
                          viewPublicationsListMetaboliteID),
              dataTableOutput("viewPublicationsTable"),
              downloadButton('downloadPublicationData', 'Download')
            )
          })
        }
      }
      if (viewPublicationsTop100FileFlag==0) {
        output$viewPublications <- renderUI({ 
          tagList(
            h3("View Publications of the Top Metabolites",align = "center"),br(),
            p("This module shows the highly-cited (>= 5 citations per year) publications for each of the top topics, sorted by the number of citations.."),
            em("No publication with more than 5 citations per year was found.")
          )
        })
      }
      renderOutputTable(outputTableFileVotes)
      output$queryDownloadUI <- renderUI({
        downloadButton('downloadData', 'Download')
      })
      output$queryTailUI <- renderUI({
        tags$script("$(document).on('click', '#table button', function () {
          Shiny.onInputChange('lastClickId',this.id);
          Shiny.onInputChange('lastClick', Math.random())
                    });")
      })
    }
    outputTableFileDownload<-outputTableFile[,1:8]
    colnames(outputTableFileDownload)<-c("Rank","UniProtKB_ID","GeneID","Metabolite_Name","PURPOSE_Score","nC","nTC","Citation/year")
    output$downloadData <- downloadHandler(
      filename = function() { paste(input$query, '.csv', sep='') },
      content = function(file) {
        write.csv(outputTableFileDownload, file, row.names=F)
      }
    )
  })
  ## Chemical
  output$selectionORInputC = renderUI(
    if (is.null(input$queryTypeC)) {
      return()
    } else if (input$queryTypeC=="hpp") {
      selectInput("queryC", "Step 2: Select an HPP Targeted Area:",
                  c("Brain" = "brain",
                    "Cancers" = "cancer",
                    "Cardiovascular" = "cardiovascular",
                    "Diabetes" = "diabetes",
                    "Extreme conditions" = "hot+OR+cold+OR+alkaline+condition+OR+acidic+condition+OR+hypersaline+OR+radiation",
                    "EyeOME" = "eye+OR+ocular",
                    "Food and nutrition" = "food+OR+nutrition+OR+nutrients",
                    "Glycoproteomics" = "glycoproteins",
                    "Immune-peptidome" = "immune+OR+immune+system",
                    "Infectious diseases" = "infectious+OR+infection",
                    "Kidney and urine" = "kidney+OR+urine",
                    "Liver" = "liver+OR+hepatic",
                    "Mitochondria" = "mitochondria",
                    "Model organisms" = "rat+OR+mouse",
                    "Musculoskeletal" = "muscle+OR+bone+OR+musculoskeletal",
                    "PediOme" = "pediatric+OR+newborn+OR+infant+OR+toddler+OR+child+OR+adolescent",
                    "Plasma" = "plasma+OR+serum",
                    "Protein aggregation" = "protein+aggregation",
                    "Rheumatic disorders" = "rheumatic"),selected = NULL)
      #c("Brain","Cancer","Cardiovascular","Diabetes","Epigenetic chromatin",
      #  "Extreme conditions","Eye","Food and Nutrition","Glycoproteomics",
      #  "Immune","Infectious diseases","Kidney and Urine",
      #  "Liver","Mitochondria","Model organisms","Musculoskeletal",
      #  "PediOme","Plasma","Protein aggregation","Respiratory","Stem cells",
      #  "Toxicoproteomics"),selected = NULL)
    } else if (input$queryTypeC=="diseases") {
      h3("Disease (PheWAS Code):")
      textInput.typeahead(id="queryC",
                          #width = '100%',
                          placeholder="Step 2: Enter disease here",
                          local=data.frame(name=c(disease_autocomplete_list)),
                          valueKey = "name",
                          tokens=c(1:length(disease_autocomplete_list)),
                          template = HTML("<p class='repo-language'>{{info}}</p> <p class='repo-name'>{{name}}</p>")
      )
    } else if (input$queryTypeC=="organ") {
      selectInput("queryC", "Step 2: Select an organ system:",
                  c("Cardiovascular / Circulatory system" = "cardiovascular+system+OR+circulatory+system",
                    "Digestive system / Excretory system"="digestive+system+OR+excretory+system",
                    "Endocrine system"="endocrine+system",
                    "Integumentary system"="integumentary+system", 
                    "Lymphatic system / Immune system"="lymphatic+system+OR+immune+system", 
                    "Muscular system"="muscular+system", 
                    "Nervous system"="nervous+system",
                    "Renal system / Urinary system"="renal+system+OR+urinary+system", 
                    "Reproductive system"="reproductive+system", 
                    "Respiratory system"="respiratory+system", 
                    "Skeletal system"="skeletal+system"
                  ),selected = NULL)
    } else if (input$queryTypeC=="custom") {
      textInput("queryC", "Step 2: Input custom topic query below:", "", width = "100%")
    }
  )
  renderOutputTableC<-function(outputTableFile){
    #print(outputTableFile[1,])
    #outputTableFile[,2]<-substr(outputTableFile[,2],1,6)
    #outputTable<<-outputTableFile[,c(1,4,5,6,7,8,12,2)]
    outputTable<<-outputTableFile[,c(1:3,5:8,12)]
    #outputTable<<-outputTableFile
    #print(outputTable[1,])
    colnames(protein_names_mappingSelected)[1]<-"Gene_Name"
    #outputTableVotes<<-join(outputTable, protein_names_mappingSelected, by = "Gene_Name")
    outputTableVotes<<-outputTable
    colnames(outputTableVotes)<<-c("Rank","MESH_ID","Chemical Name","Score","#P","#TP","#Citation/yr","Votes")
    print(outputTableVotes[1:2,])
    output$tableC <- renderDataTable({
      print(outputTableVotes[1:2,])
      #colonPosition<-unlist(lapply(outputTableVotes$MESH_ID,findColon))
      #outputTableVotes$MESH_ID <- createLink("meshID", substr(outputTableVotes$MESH_ID,colonPosition+1,nchar(outputTableVotes$MESH_ID)), input$species)
      outputTableVotes$MESH_ID <- unlist(lapply(outputTableVotes$MESH_ID, createLinkMesh, input$species))
      #outputTableShow<-outputTableVotes[,c(1:2,9,3:6,10,7)]
      outputTableShow<-outputTableVotes
      outputTableShow[["Actions"]]<-
        paste0('<div class="btn-group" role="group" aria-label="votes">
               <button type="button" class="btn btn-secondary upvote" id=upvote_',1:nrow(outputTableShow),'>Upvote</button>
               <button type="button" class="btn btn-secondary downvote"id=downvote_',1:nrow(outputTableShow),'>Downvote</button></div>')
      return(outputTableShow)}, options=list(rownames = FALSE), escape = FALSE)
  }
  output$viewPublicationsTableC<-renderDataTable({
    viewPublicationsTop100ShowC[viewPublicationsTop100ShowC[,2]==input$viewPublicationsChemical,c(8,3:7)]
  }, escape=FALSE)
  output$downloadPublicationDataC <- downloadHandler(
    filename = function() { paste(input$query, outputTableFile[outputTableFile[,3]==input$viewPublicationsChemical,4],'Publications.csv', sep='_') },
    content = function(file) {
      write.csv(viewPublicationsTop100ShowC[viewPublicationsTop100ShowC[,2]==input$viewPublicationsChemical,c(1,3:7)], file, row.names=F)
    }
  )
  observeEvent(input$runPubCitC, {
    print(input$pubcitNC)
    pubcitPlotTable<-outputTableFile[input$pubcitNC[1]:input$pubcitNC[2],]
    print(pubcitPlotTable[1:2,])
    yAxis <- list(
      title = "log10(value)"
    )
    pubcitPlotObject<-plot_ly(pubcitPlotTable, x=~Rank, y=~log10(Big_P), name="#P", type="scatter", mode="markers", text=~Chemical_Name) %>%
      add_trace(y=~log10(T.P), name="#TP", mode="markers") %>%
      add_trace(y=~log10(Citation), name="#Citations/year", mode="markers") %>%
      layout(yaxis = yAxis)
    output$pubcitPlotC <- renderPlotly({ 
      pubcitPlotObject
    })
  })
  observeEvent(input$runDAVIDC, {
    print(input$davidNC)
    urlPart<-outputTableFile[1,3]
    if (input$davidNC>1){
      for (i in 2:input$davidNC){
        urlPart<-paste(urlPart,outputTableFile[i,3],sep=",")
      }
    }
    js$openURL(paste("http://david.abcc.ncifcrf.gov/api.jsp?type=ENTREZ_GENE_ID&ids=",urlPart,"&tool=summary",sep=""))
    #browseURL(paste("http://david.abcc.ncifcrf.gov/api.jsp?type=ENTREZ_GENE_ID&ids=",urlPart,"&tool=summary",sep=""))
  })
  observeEvent(input$runPCIC, {
    print(input$pciNC)
    urlPart<-outputTableFile[1,3]
    if (input$pciNC==1){
      js$openURL(paste("http://stitch.embl.de/cgi/network.pl?identifier=",urlPart,"&species=",speciesID,sep=""))
      #browseURL(paste("http://stitch.embl.de/cgi/network.pl?identifier=",urlPart,"&species=9606",sep=""))
    } else {
      for (i in 2:input$pciNC){
        urlPart<-paste(urlPart,outputTableFile[i,3],sep="%0D")
      }
      js$openURL(paste("http://stitch.embl.de/cgi/network.pl?identifiers=",urlPart,"&species=",speciesID,sep=""))
      #browseURL(paste("http://stitch.embl.de/cgi/network.pl?identifiers=",urlPart,"&species=9606",sep=""))
    }
  })
  observeEvent(input$lastClickC,{
    print ("XXX")
    print(outputTableFileVotes[1:2,])
    if (substr(input$lastClickIdC,1,6)=="upvote") {
      row=as.numeric(gsub("upvote_","",input$lastClickIdC))
      write.table(t(c(input$queryC,input$speciesC,outputTableVotes[row,2],"1",as.character(Sys.time()))),sep="\t",append=T,file="data/votesC.txt",quote=F,row.names=F,col.names=F)
    } else if (substr(input$lastClickIdC,1,6)=="downvo") {
      row=as.numeric(gsub("downvote_","",input$lastClickIdC))
      write.table(t(c(input$queryC,input$speciesC,outputTableVotes[row,2],"-1",as.character(Sys.time()))),sep="\t",append=T,file="data/votesC.txt",quote=F,row.names=F,col.names=F)
    }
    outputTableFileVotes<<-countVotesC(outputTableFileVotes, isolate(input$queryC), isolate(input$speciesC))
    renderOutputTableC(outputTableFileVotes)
  })
  observeEvent(input$runC, {
    dirName<-paste(tolower(isolate(input$queryC)),"+AND+",isolate(input$speciesC),sep="")
    dirName<-gsub(" ", "+", dirName)
    #dirName<-tolower(isolate(input$queryC))
    print (dirName)
    outputTableFilename<-paste("PURPOSE_chemical_Results/",isolate(input$speciesC),"/",dirName,"_PURPOSE[chemical]",sep="")
    #outputTableFilename<-paste("PURPOSE_chemical_Results/",dirName,"_PURPOSE[chemical]",sep="")
    
    if (!file.exists(outputTableFilename)){
      # run query
      print("running query2")
      print(paste("java -Xmx2048m PURPOSE_chem2pubtator",isolate(input$queryC), isolate(input$speciesC), sep=" "))
      #print(paste("java -Xmx2048m PURPOSE_chem2pubtator",isolate(input$queryC), sep=" "))
      #progressMsg<<-""
      #logFile<-reactiveFileReader(500,session,filePath=paste("progressLog/",dirName,".txt",sep=""),readLines)
      #progressMsg<<-"Running query... It might take a few seconds..."
      #print(progressMsg)
      system(paste("java -Xmx2048m PURPOSE_chem2pubtator ", isolate(input$queryC)," ", isolate(input$speciesC)," | tee progressLog/",dirName,".txt", sep=""))
    }
    print("Completed running the java code")
    print(outputTableFilename)
    if (file.exists(outputTableFilename)) {
      outputTableSummary<<-read.table(outputTableFilename,stringsAsFactors=F,sep="\t",nrows=2)
      bigT<<-outputTableSummary[1,2]
      bigF<<-outputTableSummary[2,2]
      outputTableFile<<-read.table(outputTableFilename,header=T,comment.char="",stringsAsFactors=F,sep="\t",fill=T,quote="",skip=2)
      # remove blacklisted proteins
      if (input$excludeBlacklistedC == T){
        blacklistThisQuery<-blacklistC[(blacklistC[,1] == isolate(input$queryC) & blacklistC[,2] == isolate(input$speciesC)),]
        outputTableFile<<-outputTableFile[!outputTableFile[,1] %in% blacklistThisQuery[,3],]
      }
      outputTableFile<<-cbind((1:dim(outputTableFile)[1]),outputTableFile)
      colnames(outputTableFile)[1]<<-"Rank"
      # count votes
      outputTableFileVotes<<-cbind(outputTableFile,rep(0,dim(outputTableFile)[1]))
      colnames(outputTableFileVotes)[dim(outputTableFileVotes)[2]]<-"Votes"
      outputTableFileVotes<-countVotesC(outputTableFileVotes, isolate(input$queryC), isolate(input$speciesC))
      print("run...")
      print(outputTableFileVotes[1,])
      if (isolate(input$speciesC)=="human") {
        protein_names_mappingSelected<<-human_protein_names_mapping
        speciesID<<-"9606"
      } else if (isolate(input$speciesC)=="mouse") {
        protein_names_mappingSelected<<-mouse_protein_names_mapping
        speciesID<<-"10090"
      } else if (isolate(input$speciesC)=="rat") {
        protein_names_mappingSelected<<-rat_protein_names_mapping
        speciesID<<-"10116"
      } else if (isolate(input$speciesC)=="fly") {
        protein_names_mappingSelected<<-fly_protein_names_mapping
        speciesID<<-"7227"
      } else if (isolate(input$speciesC)=="worm") {
        protein_names_mappingSelected<<-worm_protein_names_mapping
        speciesID<<-"6239"
      } else if (isolate(input$speciesC)=="yeast") {
        protein_names_mappingSelected<<-yeast_protein_names_mapping
        speciesID<<-"4932"
      }
      output$queryValueC <- renderText({ paste("Query term: ",isolate(input$queryC),"\n",
                                              "Species: ",isolate(input$speciesC),"\n",
                                              "Number of publications on ",isolate(input$queryC)," in ",isolate(input$speciesC),": ",dim(outputTableFile)[1],"\n",sep="") })
      output$summaryC <- renderUI({ 
        tagList(
          strong("Query term: "),code(isolate(input$queryC)),br(),
          strong("Species: "),code(isolate(input$speciesC)),br(),
          #em("***BELOW CONTAINS PLACEHOLDERS***"),br(),code(sample(1:10000,1)),
          strong("Number of publications associated with the query:"),code(bigT),br(),
          strong("Number of publications in the PubMed database associated with any chemicals:"),code(bigF),br(),
          strong("Number of chemicals associated with the retrieved publications:"),code(dim(outputTableFile)[1]),br(),
          #strong("Number of retrieved publications associated with chemicals:"),code(outputTableFile[1,9]),br(),
          #strong("Number of average citations per year of the retrieved publications associated with chemicals:"),code(sample(1:10000,1)),br(),
          #strong("Number of publications in the PubMed database:"),code(sample(1:10000,1)),br(),
          br(),br(),br(),h3("Prioritized List",align="center"),
          h6("Descriptions:"),
          h6("#T = the number of paper on this topic; #TP = the number of paper on this topic and this chemical; #Citation/yr = the total number of citations per year"),
          h6("Votes: user voting on the relevance of the chemical to the queried topic. Please note that infrequently accessed topics may have smaller numbers of votes.")
        )
      })
      print(outputTableFile[1:2,])
      output$scoreScatterPlotC <- renderPlotly({ 
        yAxis <- list(
          title = "log10(Publication Score)"
        )
        plot_ly(outputTableFile, x=~Rank, y=~log10(PURPOSE_Score), text=~Chemical_Name) %>%
          layout(yaxis = yAxis)
      })
      output$viewScoreDistributionC <- renderUI({ 
        tagList(
          h3("View the Distribution of Publication Scores",align = "center"),
          h5("(it may take a few seconds to load the whole figure)",align="center"),
          plotlyOutput("scoreScatterPlotC")
        )
      })
      proteinMin<-min(1,dim(outputTableFile)[1])
      proteinMax<-min(1000,dim(outputTableFile)[1])
      proteinDefault<-min(20,dim(outputTableFile)[1])
      output$viewPubCitC <- renderUI({ 
        tagList(
          h3("View Publication/Citation Numbers of the Top-ranking Chemicals",align = "center"),br(),
          p("This module visualized the number of publication and the number of citations per year for each of the retrieved chemicals."),
          sliderInput("pubcitNC", "View chemicals whose publication score ranking are at the following range:", min=proteinMin, max=proteinMax, step=1, value=c(proteinMin,proteinDefault), width='100%'),br(),
          #sliderInput("pubcitNC", "N=", min=1, max=dim(outputTableFile)[1], value=c(1,20)),br(),
          actionButton("runPubCitC", "Run", width = "100%"),
          plotlyOutput("pubcitPlotC"),
          h6("Legend: #T = the number of paper on this topic; #TP = the number of paper on this topic and this chemical; #Citations/year = the total number of citations per year")
        )
      })
      output$viewDAVIDC <- renderUI({ 
        tagList(
          h3("View the DAVID Functional Annotation Summary of the Top N Chemicals",align = "center"),br(),
          p("This module conducts enrichment analysis using the Database for Annotation, Visualization and Integrated Discovery (DAVID)."),
          p("The result page will be shown in a new tab."),
          em("Please check the pop-up blocker of your browser if the results do not show up after clicking 'Run DAVID.'"),
          sliderInput("davidN", "N=", min=proteinMin, max=proteinMax, step=1, value=proteinDefault, width='100%'),br(),
          useShinyjs(),
          extendShinyjs(text = jsOpenURLCode),
          actionButton("runDAVID", "Run DAVID", width = "100%")
        )
      })
      output$viewPCIsC <- renderUI({ 
        tagList(
          h3("View Protein-Chemical Interactions (PCI) among the Top N Chemicals",align = "center"),br(),
          p("This module conducts PCI analysis using the STITCH Database."),
          p("The result page will be shown in a new tab."),
          em("Please check the pop-up blocker of your browser if the results do not show up after clicking 'View PCI.'"),
          sliderInput("pciNC", "N=", min=proteinMin, max=proteinMax, step=1, value=proteinDefault, width='100%'),br(),
          useShinyjs(),
          extendShinyjs(text = jsOpenURLCode),
          actionButton("runPCIC", "View PCI", width = "100%")
        )
      })
      viewPublicationsTop100FileFlag<-0
      viewPublicationsTop100Filename<-paste("PURPOSE_chemical_Results/",isolate(input$species),"/",dirName,"_TOP100[chemical]",sep="")
      #viewPublicationsTop100Filename<-paste("PURPOSE_chemical_Results/",dirName,"_TOP100[chemical]",sep="")
      if (file.exists(viewPublicationsTop100Filename)) {
        fileInfo <- file.info(viewPublicationsTop100Filename)
        if (fileInfo$size>0) {
          viewPublicationsTop100FileFlag<-1
          viewPublicationsListChemicalID<-outputTableFile[1:100,3]
          names(viewPublicationsListChemicalID)<-outputTableFile[1:100,3]
          viewPublicationsTop100<-read.table(viewPublicationsTop100Filename,header=F,comment.char="",stringsAsFactors=F,sep="\t",fill=T,quote="")
          viewPublicationsTop100<-viewPublicationsTop100[order(viewPublicationsTop100[,7],decreasing=T),]
          #viewPublicationsTop100[,3]<-paste(viewPublicationsTop100[,3]," et al.",sep="")
          viewPublicationsTop100[,3]<-unlist(lapply(viewPublicationsTop100[,3],renderAuthors))
          colnames(viewPublicationsTop100)<-c("PMID","Chemical","Authors","Title","Journal","Year","Total Citations")
          viewPublicationsTop100$PMIDLink<-createLink("PubMed", viewPublicationsTop100$PMID, "")
          viewPublicationsTop100ShowC<<-viewPublicationsTop100
          output$viewPublicationsC <- renderUI({ 
            tagList(
              h3("View Publications of the Top Chemicals",align = "center"),br(),
              p("This module shows the highly-cited (>= 5 citations per year) publications for each of the top topics, sorted by the number of citations."),
              em("The table would be empty if there is no highly-cited publication matching the chemical and the topic."),
              #em("***PLACEHOLDERS BELOW***"),br(),
              selectInput("viewPublicationsChemical", "Choose a chemical:",
                          viewPublicationsListChemicalID),
              dataTableOutput("viewPublicationsTableC"),
              downloadButton('downloadPublicationDataC', 'Download')
            )
          })
        }
      }
      if (viewPublicationsTop100FileFlag==0) {
        output$viewPublicationsC <- renderUI({ 
          tagList(
            h3("View Publications of the Top Chemicals",align = "center"),br(),
            p("This module shows the highly-cited (>= 5 citations per year) publications for each of the top topics, sorted by the number of citations.."),
            em("No publication with more than 5 citations per year was found.")
          )
        })
      }
      print(outputTableFileVotes[1:3,])
      renderOutputTableC(outputTableFileVotes)
      output$queryDownloadUIC <- renderUI({
        downloadButton('downloadDataC', 'Download')
      })
      output$queryTailUIC <- renderUI({
        tags$script("$(document).on('click', '#tableC button', function () {
                    Shiny.onInputChange('lastClickIdC',this.id);
                    Shiny.onInputChange('lastClickC', Math.random())
        });")
      })
    }
    outputTableFileDownload<-outputTableFile[,1:8]
    colnames(outputTableFileDownload)<-c("Rank","UniProtKB_ID","GeneID","Chemical_Name","PURPOSE_Score","nC","nTC","Citation/year")
    output$downloadDataC <- downloadHandler(
      filename = function() { paste(input$query, '.csv', sep='') },
      content = function(file) {
        write.csv(outputTableFileDownload, file, row.names=F)
      }
    )
  })
}


