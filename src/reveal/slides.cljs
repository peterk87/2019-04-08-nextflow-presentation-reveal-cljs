(ns reveal.slides)

(defn hspace
      ([]
        [:span {:style "margin-left:1em"}])
      ([space]
        [:span {:style (str "margin-left:" space "em")}]))

(defn style-height
      ([h u] (str "height:" h u ";"))
      ([h] (style-height h "em"))
      ([] (style-height "1" "em")))

(def height-1em (style-height))
(def height-2em (style-height 2))
(def height-3em (style-height 3))

(def color-green "color:rgb(39, 174, 96);")

(def style-green {:style color-green})

(defn img
      ([filename style]
        [:img.plain {:data-src (str "img/" filename) :style style}])
      ([filename]
        (img filename height-1em)))

(def code-style-red {:style "color:rgb(204, 147, 147);background-color:rgb(63, 63, 63);"})
(def code-style-yellow {:style "color:rgb(239, 239, 175);background-color:rgb(63, 63, 63);"})
(defn font-size-style
      [size]
      {:style (str "font-size:" size "em;")})
(def main-nf "main.nf")

(defn h3-code-green [s]
      [:h3 [:code style-green s]])

(defn nf-code [code]
      [:pre
       [:code.hljs.groovy code]])


(defn nf-code-section
      [filename code]
      [:section
       (h3-code-green filename)
       (nf-code code)])


(def s-intro
  [:section
   [:h1
    [:span {:style color-green}
     "Next"] "flow"
    " Intro"]
   [:p "A gentle intro to " (img "nextflow_logo.png") " workflow development for bioinformatics"]
   (for [x ["Lab Meeting Presentation - April 8, 2019"
            "Peter Kruczkiewicz"
            [:span "Genomics Unit, NCFAD, CFIA " (img "canada.png")]]]
     [:p (font-size-style 0.5) x])
   ])

(def vec-s-about-nf
  [
   [:section
    [:h1
     (img "nextflow_logo.png")]
    [:ul
     [:li "Fast Prototyping"]
     [:li "Simple Concurrency"]
     [:li "Scalability"]
     [:li "Reproducibility"]]]
   [:section
    [:h3 style-green "Fast Prototyping"]
    [:p "Quickly put together an analysis pipeline from a few command-lines"]
    [:p (img "sonic.gif" height-3em)]]
   [:section
    [:h3 style-green "Simple Concurrency"]
    [:p "Easily run things in parallel"]
    (for [j (range 10)] (for [i (range 10)] (img "assembly.png" height-1em)))]
   [:section
    [:h3 style-green "Scalability"]
    (img "pc-to-cluster.png" (style-height 8))
    [:p "From a single computer to an HPC cluster (or to the cloud)"]
    ]
   [:section
    [:h3 style-green "Reproducibility"]
    [:ul
     [:li
      "Manage software versions"
      [:ul
       [:li
        (img "conda_logo.svg")
        ", "
        (img "docker_logo.svg")
        ", "
        (img "singularity.png")
        "ingularity"]]]
     [:li (img "git.png")
      " and "
      (img "github.png")
      " Github integration for workflow sharing and distribution"]
     [:li "Build self-contained workflows"]
     [:li "Workflows that run the same anywhere!"]]]
   [:section
    [:h3 style-green "Easy to Install"]
    [:p "Install from Conda"]
    [:pre [:code.bash "conda install nextflow"]]
    [:p "Or if you have Java 8+, install locally"]
    [:pre
     [:code.bash
"curl -s https://get.nextflow.io | bash
# move 'nextflow' binary to directory in PATH
mkdir -p ~/bin/
mv nextflow ~/bin/"]]
    [:p "Run a test workflow!"]
    [:pre
     [:code.bash
      "nextflow run hello"]]]
   [:section
    [:h3 style-green "Run a test workflow!"]
    (img "nextflow-hello.cast.svg" 8)]])

(def vec-s-workflow-concepts
  [
   [:section
    [:h3 [:span style-green "Nextflow "] "| Concepts"]
    [:ul
     [:li [:strong "Processes"]]
     [:ul
      [:li "Building blocks of a workflow"]
      [:li "Contain command lines or Python/Perl/R/etc code to execute"]]]
    [:p (img "pipeline-schematic-1.svg" (style-height 4))]]
   [:section
    [:h3 [:span style-green "Nextflow"] " | Concepts"]
    [:ul
     [:li [:strong "Processes"]]
     [:ul
      [:li "Building blocks of a workflow"]
      [:li "Contain command lines or Python/Perl/R/etc code to execute"]]
     [:li [:strong "Channels"]
      [:ul
       [:li "Communication " [:em "channels"] " between processes"]]]]
    [:p (img "pipeline-schematic-2.svg" (style-height 4))]]])

(def vec-s-process-struct
  [
   [:section
    [:h3 [:span style-green "Process"] " Structure"]
    [:pre [:code.hljs.groovy
"process process_name {
  input:
    {input channels}
  output:
    {output channels}
  \"\"\"
  {your command-line or script}
  \"\"\"
}"]]]
   [:section
    [:h3 [:span style-green "Example"] " Process"]
    [:pre
     [:code.hljs.groovy
"process subsample {
  input:
    file(\"reads.fastq\") from ch_reads
  output:
    file(\"subsampled.fastq\") into ch_subsampled_reads
  \"\"\"
  seqtk reads.fastq 1000 > subsampled.fastq
  \"\"\"
}"]]]
   [:section
    [:h3 [:span style-green "Example"] " Process"]
    [:pre
     [:code.hljs.groovy
"process subsample {
  input:
    file(\"reads.fastq\") from ch_reads
  output:
    file(\"subsampled.fastq\") into ch_subsampled_reads
  \"\"\"
  seqtk reads.fastq 1000 > subsampled.fastq
  \"\"\"
}"]]
    [:p [:em "Where do we get " [:code "ch_reads"] " from?"]]]
   [:section
    [:h3 [:span style-green "Example"] " Process"]
    [:pre
     [:code.hljs.groovy
"Channel
  .fromPath(\"my-reads/*.fastq\")
  .set { ch_reads }

process subsample {
  input:
    file(\"reads.fastq\") from ch_reads
  output:
    file(\"subsampled.fastq\") into ch_subsampled_reads
  \"\"\"
  seqtk reads.fastq 1000 > subsampled.fastq
  \"\"\"
}"]]
    [:p [:em "Where do we get " [:code "ch_reads"] " from?"]]]
   [:section
    [:h3 [:span style-green "Example"] " Process"]
    (img "subsample.nf.svg" 6)
    [:pre
     [:code.hljs.groovy
"Channel
  .fromPath(\"my-reads/*.fastq\")
  .set { ch_reads }

process subsample {
  input:
    file(\"reads.fastq\") from ch_reads
  output:
    file(\"subsampled.fastq\") into ch_subsampled_reads
  \"\"\"
  seqtk reads.fastq 1000 > subsampled.fastq
  \"\"\"
}"]]]])

(def file-tree
  ".
├── <mark>main.nf</mark>
├── nextflow.config
└── reads
    ├── Sample-04e4_1.fastq
    ├── Sample-04e4_2.fastq
    ├── Sample-36db_1.fastq
    ├── Sample-36db_2.fastq
    ├── Sample-80ae_1.fastq
    ├── Sample-80ae_2.fastq
    ├── Sample-9f50_1.fastq
    ├── Sample-9f50_2.fastq
    ├── Sample-a4a7_1.fastq
    ├── Sample-a4a7_2.fastq
    ├── Sample-e814_1.fastq
    ├── Sample-e814_2.fastq
    ├── Sample-e90c_1.fastq
    └── Sample-e90c_2.fastq

1 directory, 16 files")

(def s-lets-create-a-pipeline
  [:section
   [:h2 "Let's create a " [:span style-green "pipeline"]]
   [:ul
    [:li [:strong "Raw Data"]
     [:ul
      [:li "Paired end Illumina reads"]]]
    [:li [:strong "Expected Output"]
     [:ul
      [:li "Top BLAST hit (NCBI nt DB)"]]]
    [:li [:strong "What do we want to do?"]
     [:ul
      [:li "Read filtering"]
      [:li "Assembly"]
      [:li "???"]]]]])

(def s-pipeline-struct
  [:section
   [:h3 "Pipeline structure"]
   [:pre
    [:code.bash {:data-noescape true} file-tree]]])

(def code-main-nf-1
  "#!/usr/bin/env nextflow

// TODO: make pipeline")

(def code-main-nf-2
  "#!/usr/bin/env nextflow

Channel.fromFilePairs(\"reads/*{1,2}.fastq\").println()")

(def code-main-nf-2b
  "#!/usr/bin/env nextflow

Channel
  .fromFilePairs(\"reads/*{1,2}.fastq\", flat: true)
  .println()")

(def code-main-nf-3
"#!/usr/bin/env nextflow

Channel
  .fromFilePairs(\"reads/*{1,2}.fastq\", flat: true)
  .set { ch_reads }

process filter_reads {
  input:
    set sample_id, file(r1), file(r2) from ch_reads
  output:
    <mark>???</mark>
  \"\"\"
  fastp <mark>???</mark>
  \"\"\"
}")

(def code-main-nf-4
"#!/usr/bin/env nextflow

Channel
  .fromFilePairs(\"reads/*{1,2}.fastq\", flat: true)
  .set { ch_reads }

process filter_reads {
  echo true
  input:
    set sample_id, file(r1), file(r2) from ch_reads
  \"\"\"
  echo 'Current directory is `pwd`'
  echo 'sample_id=$sample_id'
  echo 'r1=$r1'
  echo 'r2=$r2'
  ls -lha
  echo
  \"\"\"
}")

(def code-main-nf-5
"#!/usr/bin/env nextflow

Channel
  .fromFilePairs(\"reads/*{1,2}.fastq\", flat: true)
  .set { ch_reads }

process filter_reads {
  input:
    set sample_id, file(r1), file(r2) from ch_reads
  output:
    set sample_id, file(\"R1.fq\"), file(\"R2.fq\") into ch_filtered_reads
  \"\"\"
  fastp -i $r1 -I $r2 -o R1.fq -O R2.fq
  \"\"\"
}")

(def fastp-out-dir
"   1 .exitcode
 945 .command.err
 945 .command.log
460K fastp.html
117K fastp.json
3.4M R1.fq
3.4M R2.fq
   0 .command.out
  40 Sample-04e4_1.fastq -> /tmp/nf-simple/reads/Sample-04e4_1.fastq
  40 Sample-04e4_2.fastq -> /tmp/nf-simple/reads/Sample-04e4_2.fastq
   0 .command.begin
2.0K .command.run
  86 .command.sh")


(def code-main-nf-6-spades-1
"// using '<mark>ch_filtered_reads</mark>' from '<mark>filter_reads</mark>' process
process assembly {
  // process directives go here

  input:
    set sample_id, file(r1), file(r2) from <mark>ch_filtered_reads</mark>
  output:
    set sample_id, file(\"out/contigs.fasta\") into ch_contigs

  \"\"\"
  spades.py -1 $r1 -2 $r2 -o out
  \"\"\"
}")

(def code-main-nf-6-spades-2
"// using 'ch_filtered_reads' from 'filter_reads' process
process assembly {
  // process directives
  // setting tag to more easily identify processes
  tag \"$sample_id\"
  <mark>cpus 8</mark>

  input:
    set sample_id, file(r1), file(r2) from ch_filtered_reads
  output:
    set sample_id, file(\"out/contigs.fasta\") into ch_contigs

  \"\"\"
  spades.py <mark>-t ${task.cpus}</mark> -1 $r1 -2 $r2 -o out
  \"\"\"
}")

(def code-main-nf-6-spades-3
"// using 'ch_filtered_reads' from 'filter_reads process'
process assembly {
  // process directives
  tag \"$sample_id\"
  cpus 8
  // Install SPAdes v3.13.0 from the BioConda
  // {channel}::{package}={version}
  <mark>conda 'bioconda::spades=3.13.0'</mark>

  input:
    set sample_id, file(r1), file(r2) from ch_filtered_reads
  output:
    set sample_id, file(\"out/contigs.fasta\") into ch_contigs

  \"\"\"
  spades.py -t ${task.cpus} -1 $r1 -2 $r2 -o out
  \"\"\"
}")

(def code-main-nf-6-spades-4
"// using 'ch_filtered_reads' from 'filter_reads process'
process assembly {
  // process directives
  tag \"$sample_id\"
  cpus 8
  // Install SPAdes v3.13.0 from the BioConda
  // {channel}::{package}={version}
  conda 'bioconda::spades=3.13.0'

  input:
    set sample_id, file(r1), file(r2) from ch_filtered_reads
  output:
    set sample_id, file(\"<mark>${sample_id}.fasta</mark>\") into ch_contigs
  // Run SPAdes
  // Create link to 'contigs.fasta' in out/ directory
  // Link will have desired filename
  \"\"\"
  spades.py -t ${task.cpus} -1 $r1 -2 $r2 -o out
  ln -s out/contigs.fasta <mark>${sample_id}.fasta</mark>
  \"\"\"
}")

(def code-main-nf-6-spades-final
"// using 'ch_filtered_reads' from 'filter_reads process'
process assembly {
  // process directives
  tag \"$sample_id\"
  cpus 8
  // Install SPAdes v3.13.0 from the BioConda
  // {channel}::{package}={version}
  conda 'bioconda::spades=3.13.0'
  // Save the assembly to the output directory
  <mark>publishDir \"$outdir/assemblies/\", mode: 'copy'</mark>

  input:
    set sample_id, file(r1), file(r2) from ch_filtered_reads
  output:
    set sample_id, file(\"${sample_id}.fasta\") into ch_contigs
  // Run SPAdes
  // Create link to 'contigs.fasta' in out/ directory
  // Link will have desired filename
  \"\"\"
  spades.py -t ${task.cpus} -1 $r1 -2 $r2 -o out
  ln -s out/contigs.fasta ${sample_id}.fasta
  \"\"\"
}")


(def vec-s-main-nf
  [
   (conj (nf-code-section main-nf code-main-nf-1)
         [:p "Let's start with the input"]
         [:p "Reads under " [:code "reads/"] " directory"])
   (conj (nf-code-section main-nf code-main-nf-2)
         [:blockquote [:code style-green "fromFilePairs"]
          " creates a channel emitting the file pairs matching a glob pattern"]
         [:a (font-size-style 0.5)
          "https://www.nextflow.io/docs/latest/channel.html#fromfilepairs"])
   [:section
    [:h3 "What does " [:code "fromFilePairs"] " do?"]
    (img "nextflow-reads-println.cast.svg" 4)]
   [:section
    [:h3 "Grouping files on common name"]
    (nf-code "[Sample-9f50,
    [
      /tmp/nf-simple/reads/Sample-9f50_1.fastq,
      /tmp/nf-simple/reads/Sample-9f50_2.fastq
    ]
]")
    [:p "Not " [:em "quite"] " how we want things to look"]
    [:em "List of FASTQ files is not as easy to work with"]]
   (conj (nf-code-section main-nf code-main-nf-2b)
         [:blockquote
          [:code code-style-red "flat"] ": When " [:code code-style-yellow "true"]
          " the matching files are produced as sole elements in the emitted tuples (default: " [:code code-style-yellow "false"] ")."]
         [:p (font-size-style 0.3)  "Not sure why it's false by default..."])
   [:section
    [:h3 "Flatten file pairs"]
    (img "nextflow-reads-flatten-println.cast.svg" 4)]
   (conj (nf-code-section main-nf code-main-nf-3)
         [:p (font-size-style 0.5) [:code "fastp"] ": An ultra-fast all-in-one FASTQ preprocessor (QC/adapters/trimming/filtering/splitting...)"]
         [:a (font-size-style 0.5) "https://github.com/OpenGene/fastp"])
   (nf-code-section main-nf code-main-nf-4)
   [:section
    [:h3 "Run process echo test"]
    (img "nextflow-echo-process.cast.svg" 4)]
   (conj (nf-code-section main-nf code-main-nf-5)
         [:p (font-size-style 0.5) [:code "fastp"] ": An ultra-fast all-in-one FASTQ preprocessor (QC/adapters/trimming/filtering/splitting...)"]
         [:a (font-size-style 0.5) "https://github.com/OpenGene/fastp"])
   [:section
    [:h3 "Run with " [:code "fastp"] " process"]
    (img "nf-fastp.cast.svg" 4)]
   [:section
    [:h4 "What is '[ed/7c6ea3e]' in 'Submitted process ...'?"]
    [:p (font-size-style 0.6) "Reference to the process work directory"]
    [:pre [:code fastp-out-dir]]
    [:p (font-size-style 0.6) [:code code-style-yellow ".command.*"] " files are generated by Nextflow!"]
    [:p (font-size-style 0.6) "Input files are " [:em "symlinked"] " (like a Windows shortcut)"]
    [:p (font-size-style 0.6) "Each " [:em "process"] " is executed in its own " [:strong "sandboxed"] " directory"]]
   (for [x [code-main-nf-6-spades-1
            code-main-nf-6-spades-2
            code-main-nf-6-spades-3
            code-main-nf-6-spades-4
            code-main-nf-6-spades-final]]
        [:section
         [:h3 [:span style-green "Assembly "] "Process"]
         (nf-code x)])
   [:section
    [:h3 style-green "Resume Workflow"]
    [:p (font-size-style 0.5) "Use " [:code code-style-yellow "-resume"] " to resume workflow!"]
    (img "nf-spades.cast.svg" 6)]
   [:section
    [:h3 style-green "Next Steps?"]
    [:ul
     [:li "Save workflow development progress with " (img "git.png")]
     [:li "Add more " [:bold style-green "Processes"] "!"]
     [:li [:em "Document"] " how to " [:em "install"] " and " [:em "run"] " your workflow"]
     [:li "Save your workflow to " (img "github.png") " Github so it can be deployed anywhere"]
     [:li "Containerize your workflow with " (img "docker_logo.svg") " and " (img "singularity.png")]]]
   [:section
    [:h3 [:span style-green "nf-iav-illumina"] " | Influenza A virus workflow"]
    [:pre [:code "nextflow run peterk87/nf-iav-illumina --help"]]
    (img "nf-iav-illumina-github.png" (style-height 20))]
   [:section
    [:h3 [:span style-green "nf-iav-illumina"] " | Influenza A virus workflow"]
    (img "nf-iav-illumina-dag.svg" (style-height 15))]])

(def nf-core-create-code "$ nf-core create\n\n                                          ,--./,-.\n          ___     __   __   __   ___     /,-._.--~\\\n    |\\ | |__  __ /  ` /  \\ |__) |__         }  {\n    | \\| |       \\__, \\__/ |  \\ |___     \\`-._,-`-,\n                                          `._,._,'\n\nWorkflow Name: nextbigthing\nDescription: This pipeline analyses data from the next big 'omics technique\nAuthor: Big Steve\n\nINFO: Creating new nf-core pipeline: nf-core/nextbigthing\n\nINFO: Initialising pipeline git repository\n\nINFO: Done. Remember to add a remote and push to GitHub:\n  cd /path/to/nf-core-nextbigthing\n  git remote add origin git@github.com:USERNAME/REPO_NAME.git\n  git push")


(def vec-s-nf-core
  [
   [:section
    (img "nf-core-website.png" (style-height 15))]
   [:section
    (img "nf-core-website-2.png" (style-height 10))]
   [:section
    [:pre
     [:code
      nf-core-create-code]]]])

(def vec-s-sublime-text-nextflow
  [
   [:section
    [:h4 "Sublime Text 3 " [:span style-green "Nextflow"] " Package"]
    (img "st3-nextflow-package.png" (style-height 20))]
   [:section
    [:h3 (img "nextflow_logo.png") " | Syntax Highlighting for Sublime Text 3"]
    [:img.plain {:data-src (str "img/" "sublime-nextflow-syntax.png")
                 :style "height:600px"}]]
   [:section
    [:h3 (img "nextflow_logo.png") " | Snippets and Completions for Sublime Text 3"]
    [:video {:controls true :loop true :height 600}
     [:source {:src "videos/sublime-text-nextflow-init.mp4" :type "video/mp4"}]]]])

(def vec-s-nf-resources
  [
   [:section
    [:h3 (img "nextflow_logo.png") " Resources"]
    [:ul
     [:li [:span style-green "Website"] " (https://www.nextflow.io/)"]
     [:li [:span style-green "Docs"] " (https://www.nextflow.io/docs/latest/index.html)"]
     [:li [:span style-green "Github"] " (https://github.com/nextflow-io/nextflow)"]
     [:li [:span style-green "Discussion Forum"] " (https://groups.google.com/forum/#!forum/nextflow)"]
     [:li [:span style-green "nf-core"] " (https://nf-co.re/)"]]]])

(defn all
      "Add here all slides you want to see in your presentation."
      []
      [s-intro
       (for [x vec-s-about-nf] x)
       (for [x vec-s-workflow-concepts] x)
       (for [x vec-s-process-struct] x)
       s-lets-create-a-pipeline
       s-pipeline-struct
       (for [x vec-s-main-nf] x)
       (for [x vec-s-nf-core] x)
       (for [x vec-s-sublime-text-nextflow] x)
       (for [x vec-s-nf-resources] x)])
