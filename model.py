import os
import subprocess
import numpy as np
import pandas as pd

def func_precision(stringList, answer):
    goal_count = 0
    found = 0
    for result in stringList:
        if result == str(answer):
            found = 1
        goal_count += 1
    return found/(goal_count-1)

def func_recall(stringList, answer):
    found = 0
    for result in stringList:
        if result == str(answer):
            found = 1
            break
    return found

def func_accuracy(total, stringList, answer):
    tp = 0
    tn = 0
    fp = 0
    fn = 0
    for result in stringList[0:-1]:
        if result == str(answer):
            tp += 1
        else:
            fp += 1
    
    fn = 1 - tp
    
    # total is the number of all goals
    tn = total - tp - fp - fn
    return (tp + tn)/(tn + tp + fp + fn)


def calculate_statistics(rows):
    length = rows.shape[0]

    precision = 0
    recall = 0
    accuracy = 0
        
    for index, row in rows.iterrows():
        
        answer = row["Real_Goal"]
        results = row["Results"].split("/")
        all_candidates = row["Cost"].split("/")
        
        total = len(all_candidates)-1   # the last one is /
        
        p = func_precision(results, answer)
        r = func_recall(results, answer)
        a = func_accuracy(total, results, answer)
        
        precision += p
        recall += r
        accuracy += a
        
    precision = precision/length
    recall = recall/length
    accuracy = accuracy/length 
    
    return precision, recall, accuracy

def gr_system(phi, lamb, delta, threshold, domain):
    # run GR with phi lamb delta
    os.chdir("./tools/")
    os.system("java -jar gr_ipc.jar ../gene_data/ %s %s %s %s %s > /dev/null" %(domain, str(phi), str(lamb), str(delta), str(threshold)))
    os.chdir("../")
    # calculate p r a
    
    # print("passed")
    data = pd.read_csv("./outputs/%s.csv" % domain)
    rows_10 = data.loc[data['Percent'] == 10]
    rows_30 = data.loc[data['Percent'] == 30]
    rows_50 = data.loc[data['Percent'] == 50]
    rows_70 = data.loc[data['Percent'] == 70]
    rows_100= data.loc[data['Percent'] == 100]
    
    p_10, r_10, a_10 = calculate_statistics(rows_10)
    p_30, r_30, a_30 = calculate_statistics(rows_30)
    p_50, r_50, a_50 = calculate_statistics(rows_50)
    p_70, r_70, a_70 = calculate_statistics(rows_70)
    p_100,r_100,a_100= calculate_statistics(rows_100)
    p_avg,r_avg,a_avg= calculate_statistics(data)
    
    return p_10, r_10, a_10, p_30, r_30, a_30, p_50, r_50, a_50, p_70, r_70, a_70, p_100, r_100, a_100, p_avg, r_avg, a_avg
