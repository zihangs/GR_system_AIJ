# Tools for Experiments

This directory contains all the tools for replicating the experimental results shown in our paper. The tools are complied into runnable jar files. To successfully run the experiments, we need to prepare the dataset since each jar file require a well structured dataset. The archived datasets stored in [here](https://github.com/zihangs/fp1189_aamas2020/tree/master/datasets) and we provided a more informative explanation of the datasets.



TODO: check initial values, re-compile jar files

PM_Simulation.jar: the tool to replicate table 1

```sh
java -cp PM_Simulation.jar PM_Simulation <dataset> <goals>
```
for example: using "env_prmt_64" as for the dataset and there are 5 goals in that domain problem.
```sh
java -cp PM_Simulation.jar PM_Simulation env_prmt_64 5
```
you can find the output in https://github.com/zihangs/fp1189_aamas2020/tree/master/outputs

the source codes are in ./src


after creating the simulation output, we need to analysis the performence in terms of precision and recall:

the method of calculating precision and recall: TODO





Table 2
run Jar file Grids_Simulation.jar

```sh
java -cp Grids_Simulation.jar Grids_Simulation
```

precision & recall: TODO

Table 3

```sh
java -cp IncreasingTraces.jar IncreasingTraces
```


Table 4
Run Jar file IPC_domains.jar
```sh
java -cp IPC_domains.jar IPC_domains blocks-world
```
the models are mined by using split miner

