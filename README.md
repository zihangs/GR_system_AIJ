# Goal Recognition System

Our goal recognition system (GR system) aims to predict the goal(s) for a given sequence of actions. The GR algorithm we proposed is based on process models which are mined from historical observations (event logs). Therefore, this system need to load a set of existing process models into memory (indexing models) and then provide inferences of goals for a given trace of observations.

### Experiments for IPC domains

From previous steps, we should obtain the process models for each domain. We need to move the process models into this directory for model indexing. In my case, I generated plans and event logs, then I mined process models and stored all these data in `gene_data/`, and moved the `gene_data/` into this directory.

Once put the process models in the right place, then you need to find the tool for running IPC experiments. All the compiled jar files are in `tools/`, the `gr_ipc.jar` is the tool for running GR experiments on IPC domains. Notice java 8 is required.

There are a few parameters need to be specify:

- Input dataset (`gene_data/`).
- The domain name.
- Phi: the constance for calculating alignment weight.
- Lambda: the base of consective mis-aligned exponential factor.
- Delta: the based of discount factor.

Run the following commands for a specific domain with all parameters.

```sh
# need to change directory to ./tool/
cd tools/

# java -jar gr_ipc.jar <input_data> <domain> <phi> <lambda> <delta>
java -jar gr_ipc.jar ../gene_data/ blocks-world 50 1.5 1.0
```

Notice, the purpose for running experiments on IPC domains are for comparing performance with other GR algorithms.

### Experiments for BPI Challenges

This is the advantage of other approach (will update later).

### Outputs

The outputs will be a csv file stored in `outputs/`, all the outputs contains the predicted goals for each problems. And the CSV file will be used for calculating statistical results (precision, recall, accuracy etc). Please see the statistical calculation part for more details.



Docker:

need to download the jdk and then `docker build`

