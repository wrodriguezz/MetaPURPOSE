while read species; do
  echo $species
  while read hppTopic; do
    echo $hppTopic
    java PURPOSE_chem2pubtator $hppTopic $species
    #java PURPOSE_chem2pubtator $hppTopic human
    #java PURPOSE_meta2pubtator $hppTopic $species
    #java PURPOSE_meta2pubtator $hppTopic human
  done < data/hpp.txt
done < data/species.txt
