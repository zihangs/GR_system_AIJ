import os
# topk

domain = ["blocks-world", "campus", "depots", "driverlog", "dwr", "easy-ipc-grid", "ferry", 
               "intrusion-detection", "kitchen", "logistics", "miconic", "rovers", "satellite", 
               "sokoban", "zeno-travel"]

phi = [10, 48, 42, 29, 50, 7, 44, 50, 23, 13, 50, 33, 41]
lamb = [2.91, 3.83, 3.0, 3.04, 3.11, 3.55, 3.0, 3.06, 3.0, 3.11, 3.1, 3.0, 2.88]
delta = [2.76, 3.21, 1.67, 1.44, 1.34, 2.55, 1.7, 2.8, 1.84, 1.91, 2.23, 2.5, 2.29]
threshold = [0.94, 0.84, 0.98, 0.96, 0.97, 0.86, 0.98, 0.99, 0.96, 0.91, 0.98, 0.98, 0.98]

for i in range(15):
	os.system("java -jar gr_ipc.jar ../synthetic_domains/topk/ %s %s %s %s %s" %(domain[i], str(phi[i]), str(lamb[i]), str(delta[i]), str(threshold[i])))