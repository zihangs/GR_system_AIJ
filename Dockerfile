FROM ubuntu:20.04

RUN apt update -y && apt upgrade -y
RUN apt-get update

RUN apt install -y openjdk-11-jdk

RUN apt-get install -y python3.8
RUN apt-get install -y python3-pip

RUN pip3 install numpy==1.24.4
RUN pip3 install pandas==2.0.3
RUN pip3 install ema_workbench==2.4.1
RUN pip3 install scipy==1.10.1
RUN pip3 install seaborn==0.12.2

# CMD ["jupyter", "notebook", "--port=9999", "--no-browser", "--ip=0.0.0.0", "--allow-root"]

