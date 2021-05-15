# Goal Recognition System

Our goal recognition system (GR system) aims to predict the goal(s) for a given sequence of actions. The GR algorithm we proposed is based on process models which are mined from historical observations (event logs). Therefore, this system need to load a set of existing process models into memory (indexing models) and then provide inferences of goals for a given trace of observations.

### Experiments for IPC domains

From previous steps, we should obtain the process models for each domain. We need to move the process models into this directory for model indexing. In my case, I generated plans and event logs, then I mined process models and stored all these data in `./gene_data/`. (link to download: download in this dir)

Once put the process models in the right place, then you need to find the tool for running IPC experiments. All the compiled jar files are in `tools/`, the `gr_ipc.jar` is the tool for running GR experiments on IPC domains. Notice java 8 or 11 is required.

There are a few parameters need to be specify:

- Input dataset (`gene_data/`).
- The domain name.
- Phi: the constance for calculating alignment weight.
- Lambda: the base of consective mis-aligned exponential factor.
- Delta: the base of discount factor.
- Threshold: the acceptance threshold of a candidate goal to the highest possibility goal.

Run the following commands for a specific domain with all parameters.

```sh
# need to change directory to ./tools/
cd tools/

# java -jar gr_ipc.jar <input_data> <domain> <phi> <lambda> <delta> <threshold>
java -jar gr_ipc.jar ../gene_data/ blocks-world 50 1.5 1.0 1.0
```

Notice, the purpose for running experiments on IPC domains are for comparing performance with other GR algorithms.

### Experiments for BPIC domains

This GR system run on real-world domains (BPIC datasets) using the same parameters as above. Before running the code, we need to put the real-world domain datasets with the mined Petri-Nets (with `.xes.pnml` extensions) and testing traces into the directory ``./real_world_domains/``. (Link for download the tar files)

Run the following commands for a specific domain with all parameters.

```sh
# need to change directory to ./tools/
cd tools/

# java -jar gr_pm.jar <input_data> <domain> <phi> <lambda> <delta> <threshold>
java -jar gr_pm.jar ../real_world_domains/ build_prmt_82 50 1.5 1.0 1.0
```

### Outputs

The outputs will be a csv file stored in `outputs/`, all the outputs contains the predicted goals for each problems. And the CSV file will be used for calculating the overall performance.

* **Performance metrics:** the python notebooks `IPC.ipynb` and `PM.ipynb` are used for calculate the average performance over all problems in a domains. The notebooks take CSV file from `outputs/` and can calculate **precision**, **recall**, **accuracy**, and **running time**.

### Sensitivity analysis simulation

Since we have 





Notice: Outputs now is a storage for storing the intermediate data



Want to run in parallel need docker: (see dock requirements)

requirement to run without docker:



Docker:

need to download the jdk and then `docker build`



SA simulation to get 1000 runs:

os.system("java -jar gr_ipc.jar <dir> %s %s %s %s %s > /dev/null" %(domain, str(phi), str(lamb), str(delta), str(threshold)))

os.system("java -jar gr_ipc.jar ../gene_data/ %s %s %s %s %s > /dev/null" %(domain, str(phi), str(lamb), str(delta), str(threshold)))



goal folder are testing data



