## ui.R
# proteomics text mining app UI
#
# Kun-Hsing Yu
# July 22, 2017

library(shiny)
library(shinythemes)
library(shinyBS)

shinyUI(fluidPage(
  theme=shinytheme("yeti"),
    navbarPage(
      #theme="sandstone",
      "Metabolite and Chemical Prioritization for the HUPO B/D-HPP",
      #"PURPOSE: Protein Universal Reference Publication-Originated Search Engine",
      tabPanel("Metabolite Prioritization", 
               sidebarLayout(
                 sidebarPanel(
                  tags$head(
                    tags$style(type="text/css", "
                           #loadmessage {
                               position: fixed;
                               top: 0px;
                               left: 0px;
                               width: 100%;
                               padding: 5px 0px 5px 0px;
                               text-align: center;
                               font-weight: bold;
                               font-size: 100%;
                               color: #000000;
                               background-color: #CCFF66;
                               z-index: 10000;
                               }
                               "),
                    tags$style(type="text/css", "select { max-width: 340px; }"),
                    tags$style(type="text/css", ".span4 { max-width: 390px; }"),
                    tags$style(type="text/css", ".well { max-width: 380px; }"),
                    tags$style(type="text/css", ".well { min-width: 340px; }")
                  ),
                  conditionalPanel(condition="$('html').hasClass('shiny-busy')",
                                   tags$div(progressMsg,id="loadmessage")),
                  #br(),
                  h3("Metabolite Prioritization"),br(),
                  selectInput("queryType", "Step 1: Select a query type:",
                              c("HPP Targeted Area" = "hpp",
                                "Human Diseases (PheWAS codes)" = "diseases",
                                "Organ Systems" = "organ",
                                "Custom Search" = "custom")),
                  bsTooltip("queryType", "Select categories of terms or perform a customized search", trigger = "hover", options = NULL),
                  uiOutput("selectionORInput"),
                  bsTooltip("query", "Select or enter a search term", trigger = "hover", options = NULL),
                  br(),
                  selectInput("species", "Step 3: Choose a species:",
                              c("Human (Homo sapiens)" = "human",
                                "Mouse (Mus musculus)" = "mouse",
                                "Rat (Rattus norvegicus)" = "rat",
                                "Fly (Drosophila melanogaster)" = "fly",
                                "Worm (Caenorhabditis elegans)" = "worm",
                                "Yeast (Saccharomyces cerevisiae)" = "yeast")),
                  bsTooltip("species", "Filter the serarch results by species", trigger = "hover", options = NULL),
                  strong("Step 4 (Optional):"),br(),
                  checkboxInput("excludeBlacklisted", "Exclude blacklisted metabolites*", TRUE),
                  bsTooltip("excludeBlacklisted", "Exclude metabolites that are known to be irrelevant to the query term", trigger = "hover", options = NULL),
                  h6("* Blacklisted metabolites: metabolites that are known to be irrelevant to the query term"),
                  #checkboxInput("citation", "Weighted by the number of citations", TRUE),
                  #bsTooltip("citation", "Weighted the co-publication score by the number of citation", trigger = "hover", options = NULL),
                  br(),
                  strong("Step 5: "),br(),
                  actionButton("run", "Run!", width = "100%"),
                  bsTooltip("run", "Run the analysis", trigger = "hover", options = NULL)
                ),
                mainPanel(
                  tabsetPanel(
                    tabPanel("Prioritized List", 
                             uiOutput("summary"),
                             dataTableOutput("table"),
                             uiOutput("queryDownloadUI"),
                             uiOutput("queryTailUI")),
                    tabPanel("Publication Score Distribution", uiOutput("viewScoreDistribution")),
                    tabPanel("Publication/Citation Numbers", uiOutput("viewPubCit")),
                    #tabPanel("DAVID Annotations", uiOutput("viewDAVID")),
                    tabPanel("Protein-Metabolite Interactions", uiOutput("viewPMIs")),
                    tabPanel("View Publications", uiOutput("viewPublications"))
                  )
                )
                )
              ),
      tabPanel("Chemical Prioritization", 
               sidebarLayout(
                 sidebarPanel(
                   tags$head(
                     tags$style(type="text/css", "
                                #loadmessage {
                                position: fixed;
                                top: 0px;
                                left: 0px;
                                width: 100%;
                                padding: 5px 0px 5px 0px;
                                text-align: center;
                                font-weight: bold;
                                font-size: 100%;
                                color: #000000;
                                background-color: #CCFF66;
                                z-index: 10000;
                                }
                                "),
                     tags$style(type="text/css", "select { max-width: 340px; }"),
                     tags$style(type="text/css", ".span4 { max-width: 390px; }"),
                     tags$style(type="text/css", ".well { max-width: 380px; }"),
                     tags$style(type="text/css", ".well { min-width: 340px; }")
                     ),
                   conditionalPanel(condition="$('html').hasClass('shiny-busy')",
                                    tags$div(progressMsg,id="loadmessage")),
                   #br(),
                   h3("Chemical Prioritization"),br(),
                   selectInput("queryTypeC", "Step 1: Select a query type:",
                               c("HPP Targeted Area" = "hpp",
                                 "Human Diseases (PheWAS codes)" = "diseases",
                                 "Organ Systems" = "organ",
                                 "Custom Search" = "custom")),
                   bsTooltip("queryTypeC", "Select categories of terms or perform a customized search", trigger = "hover", options = NULL),
                   uiOutput("selectionORInputC"),
                   bsTooltip("queryC", "Select or enter a search term", trigger = "hover", options = NULL),
                   br(),
                   selectInput("speciesC", "Step 3: Choose a species:",
                               c("Human (Homo sapiens)" = "human",
                                 "Mouse (Mus musculus)" = "mouse",
                                 "Rat (Rattus norvegicus)" = "rat",
                                 "Fly (Drosophila melanogaster)" = "fly",
                                 "Worm (Caenorhabditis elegans)" = "worm",
                                 "Yeast (Saccharomyces cerevisiae)" = "yeast")),
                   bsTooltip("speciesC", "Filter the serarch results by species", trigger = "hover", options = NULL),
                   strong("Step 4 (Optional):"),br(),
                   checkboxInput("excludeBlacklistedC", "Exclude blacklisted chemicals*", TRUE),
                   bsTooltip("excludeBlacklistedC", "Exclude chemicals that are known to be irrelevant to the query term", trigger = "hover", options = NULL),
                   h6("* Blacklisted chemicals: chemicals that are known to be irrelevant to the query term"),
                   #checkboxInput("citation", "Weighted by the number of citations", TRUE),
                   #bsTooltip("citation", "Weighted the co-publication score by the number of citation", trigger = "hover", options = NULL),
                   br(),
                   strong("Step 5: "),br(),
                   actionButton("runC", "Run!", width = "100%"),
                   bsTooltip("runC", "Run the analysis", trigger = "hover", options = NULL)
                   ),
                 mainPanel(
                   tabsetPanel(
                     tabPanel("Prioritized List", 
                              uiOutput("summaryC"),
                              dataTableOutput("tableC"),
                              uiOutput("queryDownloadUIC"),
                              uiOutput("queryTailUIC")),
                     tabPanel("Publication Score Distribution", uiOutput("viewScoreDistributionC")),
                     tabPanel("Publication/Citation Numbers", uiOutput("viewPubCitC")),
                     #tabPanel("DAVID Annotations", uiOutput("viewDAVIDC")),
                     tabPanel("Protein-Chemical Interactions", uiOutput("viewPCIsC")),
                     tabPanel("View Publications", uiOutput("viewPublicationsC"))
                   )
                 )
           )
           ),
      #tabPanel("Protein Prioritization [External Link]",
      #         h4("The protein prioritization tool is available at"),
      #         a("http://rebrand.ly/proteinpurpose", href="http://rebrand.ly/proteinpurpose")
      #),
      tabPanel("About",
               h4("Summary of Methods:"),
               p("Metabolites/chemicals are prioritized by their publication score, defined as"),
               code("(1 + log10(nTC) + log10((Sum(Cit/yr)+1)/10)) * (1 + log10(nU/nT) + log10(nU/nC))"),
               p("where nTC is the number of PubMed publication related to both the topic and the metabolites/chemical (TC), Cit/yr is the sum of the annualized number of citation of TC, nU is the number of PubMed publication, nT is the number of publication regarding the topic of interest, and nC is the number of publication regarding the metabolite/chemical of interest."),
               p("The number of PubMed publications and citations are retrieved by eSummary tools, and the platform is implemented in Java and R."),br(),
               h4("Citation:"),
               p("Kun-Hsing Yu, Tsung-Lu Michael Lee, Yu-Ju Chen, Christopher Ré, S. C. Kou, Jung-Hsien Chiang, Michael Snyder, Isaac S. Kohane. A Cloud-Based Metabolite and Chemical Prioritization System for the Biology/Disease-driven Human Proteome Project. J Proteome Res. 2018 Aug 10. doi: 10.1021/acs.jproteome.8b00378. [Epub ahead of print]"),br(),
               h4("Related work:"),
               p("A related protein prioritization tool is available at"),
               a("http://rebrand.ly/proteinpurpose", href="http://rebrand.ly/proteinpurpose"),br(),br(),
               h4("Funding:"),
               p("K.-H. Y. is a Harvard Data Science Fellow. This work was supported in part by grants from National Human Genome Research Institute, National Institutes of Health, grant number 5P50HG007735, National Cancer Institute, National Institutes of Health, grant number 5U24CA160036, the Defense Advanced Research Projects Agency (DARPA) Simplifying Complexity in Scientific Discovery (SIMPLEX) grant number N66001-15-C-4043 and the Data-Driven Discovery of Models contract number FA8750-17-2-0095, and the Ministry of Science and Technology Research Grant, Taiwan, grant number MOST 103-2221-E-006-254-MY2."),br(),
               h4("Acknowledgments:"),
               p("The authors express their appreciation to Professor Griffin Weber for his insight on citation counts, Dr. Stephen Bach and Mr. Chen-Ruei Liu for their valuable advice on literature mining and suggestions on the manuscript, Dr. Mu-Hung Tsai for pointing out the literature mining resources, and Ms. Samantha Lemos for her administrative support. The authors thank the AWS Cloud Credits for Research, Microsoft Azure Research Award, and the NVIDIA Corporation for their supports on the computational infrastructure. This work used the Extreme Science and Engineering Discovery Environment (XSEDE) Bridges Pylon at the Pittsburgh Supercomputing Center through allocation TG-BCS180016, which is supported by National Science Foundation grant number ACI-1548562.")
      )
    )
  )
)

