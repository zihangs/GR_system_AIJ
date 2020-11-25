FROM ubuntu:20.04

RUN apt update -y && apt upgrade -y
RUN mkdir -p /var/cache/oracle-jdk11-installer-local/
COPY ./jdk-11.0.9_linux-x64_bin.tar.gz /var/cache/oracle-jdk11-installer-local/
RUN apt-get install -y software-properties-common
RUN add-apt-repository ppa:linuxuprising/java
RUN apt-get update

#RUN apt install -y oracle-java11-installer-local

RUN apt-get install -y python3.8
RUN apt-get install -y python3-pip

RUN pip3 install notebook
RUN pip3 install numpy
RUN pip3 install pandas
RUN pip3 install ema_workbench
RUN pip3 install scipy
RUN pip3 install seaborn
RUN pip3 install ipyparallel
RUN pip3 install platypus-opt

CMD ["jupyter", "notebook", "--port=9999", "--no-browser", "--ip=0.0.0.0", "--allow-root"]

