# Archived Datasets

In the 5th section (experimental results) of our paper, we conducted 4 experiments to test our GR system under many different scenarios. Accordingly, we recorded and formatted the statistical outputs into 4 tables shown in our paper. Here, we provide the datasets used for replicating the 4 tables in our paper, and we will explain the details of datasets table by table as below.



### Table 1: Domains from Process Mining Community

The archived dataset ``table_1.zip`` contains 3 domains from process mining community.

1. **Activities of daily living of several individuals**

   More details about this dataset, see: https://data.4tu.nl/articles/Activities_of_daily_living_of_several_individuals/12674873.

   We pre-processed the original dataset (XES files) and we stored the pre-processed outputs under the directory ``activities_64/`` and ``activities_82/``. 

   Within each directory, either ``activities_64/`` or ``activities_82/``, the event logs (``.xes`` files) are from original datasets and each event log contains the traces towards to the same goal. There are 8 event logs (from ``0.xes`` to ``7.xes``) representing the traces towards to 8 different goals in this domain. After pre-processing, we can get another 8 event logs (from ``created0.xes`` to ``created7.xes``) which are the event logs containing the traces for training models, and we can also get another 8 directories (from ``goal0/`` to ``goal7/``) which contain all the traces for GR (testing) corresponding to each goal. 

   Notice the suffix ``_64`` simply means after pre-processing, we split the original dataset and keep 60% of the traces for training and 40% traces for testing. Similar, for the suffix ``_82`` (80% traces for training, 20% percent trace for testing). 

   The ``.tsml`` files are intermediate files. They are generated during the process mining phase (training phase) when using the *transition system miner* (TSM).

2. **BPI Challenge 2015**

   More details about this dataset, see: https://data.4tu.nl/collections/BPI_Challenge_2015/5065424.

   Similar pre-processing logic as above and pre-processed outputs are stored in ``build_prmt_64/`` and ``build_prmt_82/``.

3. **Environmental permit application process (‘WABO’), CoSeLoG project**

   More details about this dataset, see: https://data.4tu.nl/collections/Environmental_permit_application_process_WABO_CoSeLoG_project/5065529.

   Similar pre-processing logic as above and pre-processed outputs are stored in ``env_prmt_64/`` and ``env_prmt_82/``.

```sh
# General command for running the integrated simulation of domains from process mining community.
java -cp PM_Simulation.jar PM_Simulation <dataset> <goals>

# for example: using "env_prmt_64" as for the dataset and there are 5 goals in that domain.
java -cp PM_Simulation.jar PM_Simulation env_prmt_64 5
```

Notice: before running this command, you need to extract the archived dataset and put the data in correct directory, details of instructions see [tools](https://github.com/zihangs/fp1189_aamas2020/tree/master/tools).



### Table 2: The Grids Domain from Classical Planning Community

The archived dataset ``table_2.zip`` contains data for running experiments on grids domain, which is a typical domain used by many classical planning research. In the experiments, we controlled the variables and tested how does a particular factor affect the performance of our GR system. The 3 factors we tested are the *size of world*, the *number of goals* and the *optimality of traces*.

1. **Size of world:** we used 3 different settings, 10 by 10 grids, 20 by 20 grids and 30 by 30 grids

2. **Number of goals:** there can be 3 or 6 or 9 goal states located in a grids domain

3. **Optimality of traces:** for every traces towards to a particular goal, there should exist at least one optimal trace for achieving that goal. The optimal trace simply means a traces that takes least costs (steps) to reach the goal state. The optimality just measures how much a traces deviates from the optimal trace. We use 10% sub-optimal traces, which means costs of traces are between optimal cost (``1*opt``) and ``(1+10%)*opt``. Similarly, 20% means traces are between ``(1+10%)*opt`` and ``(1+20%)*opt``, 30% means between ``(1+20%)*opt`` and ``(1+30%)*opt``.

All the traces are synthetic using the [K-star planner](https://github.com/ctpelok77/kstar) (we are still improving the planner for generating traces). The traces are splitted into 2 directories ``training/`` and ``testing/``, each directory contains many sub-directories with different settings ``sizeOfWorld_numberOfGoals_optimalityOfTraces/``. 

For example, ``training/10_3_10/`` contains event logs (``.xes`` files) in a 10 by 10 grids domain with 3 goal states and all the costs of traces in the event logs are between ``optimal`` and ``(1+10%)*opt``. These traces are used for training models and we use the *transition system miner* (TSM) for mining (training). The ``.pnml`` files are the returned process models after training. Another example for testing set, ``testing/10_3_10/`` contains 3 sub-directories corresponding to 3 different goal states. Each sub-directory contains all the traces towards to the same goal, the testing traces are in ``sas_plan`` format. 

Generally, the traces in ``testing/10_3_10/`` are used for testing the GR performance after training with traces from ``training/10_3_10/``. So, sub-directories in ``training/`` and ``testing/`` are paired with each other accordingly.

```sh
# The command for running the simulation in grids domain
java -cp Grids_Simulation.jar Grids_Simulation
```

Notice: before running this command, you need to extract the archived dataset and put the data in correct directory, details of instructions see [tools](https://github.com/zihangs/fp1189_aamas2020/tree/master/tools).



### Table 3:  Learning Models Over Variate Number of Traces

This experiment is still conducted in the domain of grids as above, more specifically, we set the world size to be 10 by 10, set 6 different goal states and set the optimality of traces between ``(1+20%)*opt`` and ``(1+30%)*opt``. However, with this domain setting, we did 3 experiment independently. Each time, we use a different number of traces for training (10, 100 and 1000 traces) and observe the performance of GR system.

The archived dataset ``table_3.zip`` contains 3 training sets (``training_cases_10/``, ``training_cases_100/`` and ``training_cases_1000/``) and 1 testing set (``testing_cases_100/``). We use the same set with 100 traces for testing the 3 experiments. Inside of each set, the structures are similar with the datasets explained in table 2 above.

```sh
# The command for running the experiment simulation
java -cp IncreasingTraces.jar IncreasingTraces
```

Notice: before running this command, you need to extract the archived dataset and put the data in correct directory, details of instructions see [tools](https://github.com/zihangs/fp1189_aamas2020/tree/master/tools).



### Table 4: Comparison with Other GR Approaches in Blocksworld Domain

In this part, we pick the *Blocksworld* domain from IPC dataset and archived the dataset in ``table_4.zip``. The IPC (International Planning Competition) dataset is widely used by research related to planning, goal recognition and other relevant areas, so it is crucial to use the same dataset from comparison purpose. However, utilizing this dataset is quite challenging for our approach. This dataset is designed for planner-based approaches which don't need traces for training, so in the dataset, each domain problem (PDDL) only contains 1 trace for testing. Therefore, before conducting the testing part of our experiment, we need to generate plenty of training traces as a pre-processing stage. In the pre-processing, we generate 1000 traces for each goal within each domain problem (with K-star planner).

After pre-processing, we stored all the generated traces in event log format in ``problems/<O%>/<N>/train/``. ``<O%>/`` is a sub-directory which means the corresponding testing trace has O% steps be observed. ``<N>/`` is a sub-directory means the N^th domain problem (notice there can be many different problem in the *blocksworld* domain. If you change the size of world or the number of goals, the problem will be different). Due to RAM limit in our current virtual machine, we can only partially pre-process the original dataset, the bottleneck is on the K-star planner when generating traces in some domain problems. We will try to improve and solve this issue later. The pre-processed testing data are stored in ``test/<O%>/<N>/``. Similarly, ``<O%>/`` stands for the percentage of observation, ``<N>/`` stands for the number (a ordered label) of the problem.

We provided our pre-processed data here, so **you don't have to run the pre-process scripts** ``extract.py``.

```sh
# The command for running simulation of IPC Blocksworld domain
java -cp IPC_domains.jar IPC_domains blocks-world
```

Notice: before running this command, you need to extract the archived dataset and put the data in correct directory, details of instructions see [tools](https://github.com/zihangs/fp1189_aamas2020/tree/master/tools).