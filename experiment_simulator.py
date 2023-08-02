from ema_workbench import (RealParameter, IntegerParameter, CategoricalParameter, 
                           ScalarOutcome, Constant,
                           Model, ema_logging, perform_experiments)

from SALib.analyze import sobol
from ema_workbench import Samplers

from model import gr_system
import os
import sys
import subprocess
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

# inputs
option = sys.argv[1]
domain = sys.argv[2]

if option == "IPC-d":
    jar = "gr_ipc.jar"
    data_dir = "synthetic_domains/diverse"

elif option == "IPC-t":
    jar = "gr_ipc.jar"
    data_dir = "synthetic_domains/topk"

elif option == "PM-m":
    jar = "gr_pm.jar"
    data_dir = "business_logs/multiple"

elif option == "PM-b":
    jar = "gr_pm.jar"
    data_dir = "business_logs/binary"

else:
    print("Option not found!")
    exit(0)


ema_logging.LOG_FORMAT = '[%(name)s/%(levelname)s/%(processName)s] %(message)s'
ema_logging.log_to_stderr(ema_logging.INFO)
    
model = Model('grsystem', function=gr_system)

# set levers
model.uncertainties = [IntegerParameter("phi", 0,100),
                RealParameter("delta", 0, 5),
                RealParameter("lamb", 1, 5),
                RealParameter("threshold", 0.6, 1.0)]

# model.levers = [CategoricalParameter("domain", ["sokoban", "blocks-world"])]
model.constants = [Constant("jar", jar),
                   Constant("data_dir", data_dir),
                   Constant("domain", domain)]

#specify outcomes
model.outcomes = [ScalarOutcome('p_10'),
                  ScalarOutcome('r_10'),
                  ScalarOutcome('a_10'),
                  ScalarOutcome('p_30'),
                  ScalarOutcome('r_30'),
                  ScalarOutcome('a_30'),
                  ScalarOutcome('p_50'),
                  ScalarOutcome('r_50'),
                  ScalarOutcome('a_50'),
                  ScalarOutcome('p_70'),
                  ScalarOutcome('r_70'),
                  ScalarOutcome('a_70'),
                  ScalarOutcome('p_100'),
                  ScalarOutcome('r_100'),
                  ScalarOutcome('a_100'),
                  ScalarOutcome('p_avg'),
                  ScalarOutcome('r_avg'),
                  ScalarOutcome('a_avg')]


from ema_workbench import save_results
results = perform_experiments(model, 1000)
save_results(results, '1000_scenarios_%s_%s.tar.gz'% (option, domain))

sa_results = perform_experiments(model, 1050, uncertainty_sampling=Samplers.SOBOL)
save_results(sa_results, '1050_scenarios_sobol_%s_%s.tar.gz'%(option, domain))


