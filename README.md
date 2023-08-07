# PM-Based Goal Recognition System

The process mining-based goal recognition system (GR system) aims to predict the goal(s) for a given sequence of actions. This system needs to load a set of process (skill) models into memory (indexing models) and then it can provide inferences of goals for a given trace of observations. The process models are mined from historical observations. The process models and traces of observations are available  [here](https://doi.org/10.26188/21749570) (please download the dataset and extract the included tar files before conducting the following experiments).

### Experiments for IPC domains

The GR problem instances and testing cases for IPC domains are contained in the `synthetic_domains.tar.bz2`. After downloading and extracting the dataset, change the directory to `tools/`, and you will find the compiled jar `gr_ipc.jar`, which is the tool for running GR experiments on IPC domains (java 8 or 11 is required).

There are a few parameters need to be specify:

- Root directory of input data (e.g. `synthetic_domains/topk/`).
- The domain name.
- Phi: the constance for calculating alignment weight (int).
- Lambda: the base of consective mis-aligned exponential factor (double).
- Delta: the base of discount factor (double).
- Threshold: the acceptance threshold of a candidate goal to the highest possibility goal (double).

Run the following commands to test the GR system in a specific IPC domain with default parameters.

```sh
# need to change directory to ./tools/
cd tools/

# java -jar gr_ipc.jar <input_data_dir> <domain> <phi> <lambda> <delta> <threshold>
java -jar gr_ipc.jar ../synthetic_domains/topk/ blocks-world 50 1.5 1.0 1.0
```

According to the PRIM algorithm, the optimized parameter ranges can be found in tables 7-8 in the paper (we selected the middle points of the ranges to configure each of the parameters).

### Experiments for BPIC domains

The GR problem instances and testing cases for real-world domains (BPIC datasets) are contained in the `business_logs.tar.bz2`. After downloading and extracting the dataset, change the directory to `tools/`, and you will find the compiled jar `gr_pm.jar` to test the GR performance in real-world domains (java 8 or 11 is required). Note that the two compiled jars, `gr_ipc.jar` and `gr_pm.jar`, are slightly different due to differences in their input data structures, but the GR functions are the same.

Test the GR system in a specific real-world domain with default parameters.

```sh
# need to change directory to ./tools/
cd tools/

# java -jar gr_pm.jar <input_data_dir> <domain> <phi> <lambda> <delta> <threshold>
java -jar gr_pm.jar ../business_logs/multiple/ build_prmt_82 50 1.5 1.0 1.0
```

The **outputs** will be a csv file stored in `outputs/`. All the outputs contains the predicted goals (inferences) for each problem instances. The CSV file will be used for calculating the overall performance. Don't delete `outputs/` to avoid small issues, but the generated CSV files in this directory can be removed.

* **Performance metrics:** The python notebooks `IPC.ipynb` and `PM.ipynb` are used for calculate the average performance over all problems in a domains. The notebooks take CSV file from `outputs/` and can calculate **precision**, **recall**, **accuracy**, and **execution time**.
* **Run with the recommended parameters:** The PRIM algorithm mentioned in paper provided a solution for optimizing the parameters.

### Sensitivity Analysis

We use the [EMA Workbench](https://emaworkbench.readthedocs.io/en/latest/) for Sobol sensitivity analysis and open exploration (PRIM). The required dependencies are included in the `Dockerfile`. To start a container with all the necessary requirements, simply run the Dockerfile. Alternatively, if you prefer, you can install the requirements on your own device.

Build the docker image, the default docker file is `Dockerfile`.

```sh
# docker build -t <image_name>:<version> <the Dockerfile> 
docker build -t sensitivity_analysis:1.0 .
```

After building the image, start a container with a mounted volume, and initiate an internal terminal within the container. `/my_path` refers to the absolute path of this local directory.

```sh
# docker run -it -v <local_volume>:<docker_volume> <image_name>:<version> <internal_path>
docker run -it -v /my_path:/mnt sensitivity_analysis:1.0 /bin/bash

# change directory to /mnt within the container
cd /mnt
```



Sensitivity analysis aims to verify whether the parameters (phi, delta, lambda, threshold) have significant impact on the performance. The simulator (EMA workbench) conducts GR experiments over numerous iterations, with different parameters configured for each iteration. The sensitivity analysis is then performed on the results of these experiments to illustrate whether varying the parameters affects the system's performance. Two files, `model.py` and `experiment_simulator.py`, are used for running the simulation. The simulation results, comprising various measure metrics and their corresponding parameter values, are stored in a `tar.gz` file. 

Run the following commands in the running container to simulate 1000 experiments for PRIM and 1050 $\times$ 10 experiments for Sobol sensitivity analysis.

```sh
# python3 experiment_simulator.py <option> <domain_name>
python3 experiment_simulator.py IPC-d depots
```

Please find the domain names (extracted directory) in `synthetic_domains/topk/`, `synthetic_domains/diverse/`, `business_logs/multiple/`, and `business_logs/binary/`.

- IPC-d tests the GR performance in synthetic domains and training set generated by diverse planner.
- IPC-t tests the GR performance in synthetic domains and training set generated by top-k planner.
- PM-m tests the GR performance in real-world domains for multi-goal candidates inference.
- PM-b tests the GR performance in real-world domains for binary-goal candidate inference.

**Notice:** During the simulation, the `outputs/` directory will store the intermediate GR results, so be careful to avoid overwriting them.

**Visualization:** the simulation outputs are stored in `tar.gz` files and we can run `senstivity_analysis.ipynb` to visualize. We renamed and moved our simulation results into `diverse/`,  `topk/`, `real_m/`, and `real_b/` directories.











