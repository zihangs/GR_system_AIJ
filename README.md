# Goal Recognition System

Our goal recognition system (GR system) aims to predict the goal(s) for a given sequence of actions. The GR algorithm we proposed is based on process models which are mined from historical observations (event logs). Therefore, this system need to load a set of existing process models into memory (indexing models) and then provide inferences of goals for a given trace of observations.

### Experiments for IPC domains

From previous steps, we should obtain the process models for each domain. We need to move the process models into this directory for model indexing. In my case, I generated plans and event logs, then I mined process models and stored all these data in `./gene_data/`. (Link)

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

This GR system run on real-world domains (BPIC datasets) using the same parameters as above. Before running the code, we need to put the real-world domain datasets with the mined Petri-Nets (with `.xes.pnml` extensions) and testing traces into the directory ``./real_world_domains/``. (Link for download)

Run the following commands for a specific domain with all parameters.

```sh
# need to change directory to ./tools/
cd tools/

# java -jar gr_pm.jar <input_data> <domain> <phi> <lambda> <delta> <threshold>
java -jar gr_pm.jar ../real_world_domains/ build_prmt_82 50 1.5 1.0 1.0
```

### Outputs

The outputs will be a csv file stored in `outputs/`, all the outputs contains the predicted goals for each problems. And the CSV file will be used for calculating the overall performance.

* **Performance metrics:** The python notebooks `IPC.ipynb` and `PM.ipynb` are used for calculate the average performance over all problems in a domains. The notebooks take CSV file from `outputs/` and can calculate **precision**, **recall**, **accuracy**, and **running time**.
* **Run with the recommended parameters:** The PRIM algorithm in sensitivity analysis can recommend a set of parameters for good performance. Take the recomeneded parameters and run GR experiment, then calculate the performance.

### Sensitivity analysis simulation

The sensitivity analysis can test whether the parameters (phi, delta, lambda, threshold) have significant impact on the performance. To analysis the sensitivity of parameters, we need to simulate GR experiments over a large number of iterations. For each iteration, we need to set up different parameters so that we can confirm if the parameters are different, would the performance also be different. Two files `model.py` and `experiment_simulator.py` are used for running the simulation. The simulation results are stored in a `tar.gz` file which includes all the performance statistics and the associtate parameter settings. We can analysis the simulation results in [here](https://github.com/zihangs/GR_model_sensitivity_analysis).

* **Notice:** During the simulation, the `outputs/` directory will  store the intermediate GR results, so need to be careful with the overwrite issues.

Requirements for running the simulation: a few python package need to be installed (see the docker file, `Dockerfile`, if you don't want to simulate in a docker container, you need to install the requirements directly on your machine).

**Docker container:** running in docker container allow us to run multiple simulation in parallel.

We use OpenJDK 11 in the container, the legacy code are using Oracle's JDK 11, both of the version are ok. For the first time, you need to build the docker image (the default docker file is `Dockerfile`).

```sh
# docker build -t <image_name>:<version> <the Dockerfile> 
docker build -t sa:1.0 .
```

Once the docker image is built successfully, we can start a docker container and mount volume, also we need start with internal terminal.

```sh
# docker run -it -v <local_volume>:<docker_volume> <image_name>:<version> /bin/bash
docker run -it -v /home/ubuntu/data_storage/GR_system_real_m/:/home sa:2.0 /bin/bash
```

You may need to make a slightly change to match the directories on your machine.



### Configure EMA models

We should configure the EMA model code before running simualtion. The places need to check:

In `model.py`, check the wrapped command code:

```python
# for ipc: gr_ipc.jar, ../gene_data/
# for pm: gr_pm.jar, ../real_world_domains/ or ../real_world_domains/binary/
os.system("java -jar gr_ipc.jar <dir> %s %s %s %s %s > /dev/null" %(domain, str(phi), str(lamb), str(delta), str(threshold)))
```

In `experiment_simulator.py`, specify the number of iteration and output file name

```python
# sa
results = perform_experiments(model, 1000) # numebr of iteration
save_results(results, '1000_scenarios_%s_new_model_diverse.tar.gz'%domain) # file name

# sobol
sa_results = perform_experiments(model, 1050, uncertainty_sampling='sobol')
save_results(sa_results, '1050_scenarios_%s_sobol_new_model_diverse.tar.gz'%domain)
```



