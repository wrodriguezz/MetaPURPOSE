# README #

### Summary ###
* App Name: MetaPURPOSE: A Cloud-Based Metabolite and Chemical Prioritization System for the Biology/Disease-driven Human Proteome Project.
* Version: 1.0.0


### Dependencies ###
* R version 3 or above
* R packages: shiny, shinyjs, shinyBS, shinysky, V8, ggplot2, plotly.


### Summary of Methods ###
* Metabolites/chemicals are prioritized by the Protein Universal Reference Publication-Originated Search Engine (PURPOSE), defined as
(1 + log10(nTC) + log10((Sum(Cit/yr)+1)/10)) * (1 + log10(nU/nT) + log10(nU/nC)),
where nTC is the number of PubMed publication related to both the chemical and topic (TC), Cit/yr is the sum of the annualized number of citation of TC, nU is the number of PubMed publication, nT is the number of publication regarding the topic of interest, and nC is the number of publication regarding the chemical of interest.

* The most updated PubTator data is obtained from its File Transfer Protocol (FTP) site. An automated downloader is implemented in "updater_chemical2pubtator_PURPOSE_project.java"

* The number of PubMed publications and citations are retrieved by eSummary tools, and the platform is implemented in Java and R.


### The Running Instance ###
* http://rebrand.ly/metapurpose


### Citation ###
* Kun-Hsing Yu, Tsung-Lu Michael Lee, Yu-Ju Chen, Christopher Ré, S. C. Kou, Jung-Hsien Chiang, Isaac S. Kohane, Michael Snyder. A Cloud-Based Metabolite and Chemical Prioritization System for the Biology/Disease-driven Human Proteome Project. (In Revision)


### Funding ###
* K.-H. Y. is a Harvard Data Science Fellow. This work was supported in part by grants from National Human Genome Research Institute, National Institutes of Health, grant number 5P50HG007735, National Cancer Institute, National Institutes of Health, grant number 5U24CA160036, the Defense Advanced Research Projects Agency (DARPA) Simplifying Complexity in Scientific Discovery (SIMPLEX) grant number N66001-15-C-4043 and the Data-Driven Discovery of Models contract number FA8750-17-2-0095, and the Ministry of Science and Technology Research Grant, Taiwan, grant number MOST 103-2221-E-006-254-MY2.


### Acknowledgments ###
* We express appreciation to Professor Griffin Weber for his insight on citation counts, Dr. Stephen Bach and Mr. Chen-Ruei Liu for their valuable advice on literature mining and suggestions on the manuscript, Dr. Mu-Hung Tsai for pointing out the literature mining resources, and Ms. Samantha Lemos for her administrative support. The authors thank the AWS Cloud Credits for Research, Microsoft Azure Research Award, and the NVIDIA Corporation for their supports on the computational infrastructure. This work used the Extreme Science and Engineering Discovery Environment (XSEDE) Bridges Pylon at the Pittsburgh Supercomputing Center through allocation TG-BCS180016, which is supported by National Science Foundation grant number ACI-1548562.

### Contact ###
* Kun-Hsing Yu (Kun [hyphen] Hsing [underscore] Yu [at] hms dot harvard dot edu)
