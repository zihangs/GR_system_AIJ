# Section 2: Motivating Example

This part explains the details of the motivating example in section 2 of our paper. The two traces in Figure 1 (green trace and red trace) are recorded in ``fig1/two_traces.xes`` (an event log). The observed traces towards six different goals (Figure 2) are stored under the directory ``fig2/``. The files with ``.xes`` extension are event logs and all the traces within each event log are toward to a same goal (displaying the same color in Figure 2). So there are 6 event logs are corresponding to the 6 goals (A-F) shown in Figure 2. The files with ``.pnml`` extension are petri nets which are mined from event logs using the **split miner** (instructions see below).

![image](https://github.com/zihangs/fp1189_aamas2020/blob/master/pictures/fig1fig2.JPG)



### Mining models using split miner

Split miner is a process mining tool to created petri nets from event logs, we used the prototype of split miner provided by Adriano Augusto et al. (https://link.springer.com/article/10.1007/s10115-018-1214-x). To use it, firstly, we need store all event logs (``.xes`` file) in the directory ``fig2/``, give each ``.xes`` file a name ``created`` + ``n``, n is an integer better starts from 0 (for instance ``created0.xes``). Then we can run split miner using the second command below in general, more specific for our case, we run the third command below. It will generate 6 petri nets under the same directory ``fig2/``, notice that this prototype of the split miner can only work in Windows system currently. Some import issues might occur in Windows PowerShell, so Command Prompt (cmd) is recommended. We have already created these petri nets, so **it not necessary for you to run the commands below.**

```sh
# 1. change directoy to _bpmtk
cd _bpmtk

# 2. run split miner
java -Xmx14G -Xss10G -cp bpmtk.jar;lib\* au.edu.unimelb.services.ServiceProvider SMBD "<directoy>\<file name prefix>" <start number> <end number>

# 3. for example, we run this command here
java -Xmx14G -Xss10G -cp bpmtk.jar;lib\* au.edu.unimelb.services.ServiceProvider SMBD "..\fig2\created" 0 5
```



### Goal recognition tool

The goal recognition tool can only take a trace in ``sas_plan`` format for testing goal recognition (GR) performance. We convert the event log ``fig1/two_traces.xes`` to 2 files under the directory ``fig1/traces_in_plan_format/`` in the correct format. Follow the instructions below to run the GR tool.

```sh
# change the directory of tools
cd ../tools/

# GR for the red trace in Figure 1: irrational walk   
java -cp MotivatingExample.jar MotivatingExample 0

# GR for the green trace in Figure 1: rational walk
java -cp MotivatingExample.jar MotivatingExample 1
```



### Statistical results

After running the commands above, it will generate 2 ``CSV`` files under the directory ``training_examples/``, each result contains probability distribution from observing the first step to the last step (Figure 3), the first column represents the step number (starts from 0). The ``rational.csv`` shows the probability distribution chart of the green (rational) walk in Figure 1, and ``irrational.csv`` represents the red (irrational) walk in Figure 1.

![image](https://github.com/zihangs/fp1189_aamas2020/blob/master/pictures/fig3.JPG)





