import os
import fileinput

trace_number = 100
observation_percent = [10,30,50,70,100]



for percent in observation_percent:

	#C:\Users\zihangs\Desktop\blocks-world\problems\10\0
	for num in range(198):
		os.system("rm -rf ./problems/" + str(percent) + "/" + str(num) + "/train/goal_*")
		print(str(percent) + "" + str(num))

	
		
