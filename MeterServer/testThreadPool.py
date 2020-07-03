#!/usr/bin/env python
 
import socket
import threading
import time
import random
import keyboard

 


import _thread
import time
import sys,socket
from threading import Lock




s_print_lock = Lock()
receive_lock = Lock()
def s_print(message):
    """Thread safe print function"""
    s_print_lock.acquire()
    print(message)
    s_print_lock.release()


def connect_to_server( threadName, delay):
    try:
        s = socket.socket()
        s.connect((ipaddressString,int(portNumberString)))
        cnt = 0
        data = s.recv(1024)
        s_print("Thread " + threadName + " has started sending!" + data.decode())
        while (cnt< (int(numberOfPackets))):
            count = str(cnt)
            time.sleep(delay)
            timeString = time.strftime("%d/%m/%y %H:%M", time.localtime())
            lineString = f"{threadName},{timeString},{count},{count},{count},{count},{count},{count},{count},{count},{count},{count},{count} \n"
            s_print(lineString)
            s.send(lineString.encode())
            cnt = cnt + 1
        s.send("!Finished!\n".encode()) 

        time.sleep(delay)
        receive_lock.acquire()
        data = s.recv(1024)
        s_print("Thread " + threadName + " " + data.decode())
        receive_lock.release()

    except socket.error as ex:
        template = "An exception of type in thread "  + threadName + " {0} occurred. Arguments:\n{1!r}"
        message = template.format(type(ex).__name__, ex.args)
        s_print (message)




#------------------------------------------------------------------main start---------------------------------------------------------------------------------------
if(len(sys.argv) < 4):
	print("Error \n arguments should be in form: <ip address> <socket number> <number of threads> <number of packets to send>")
	quit()

ipaddressString = sys.argv[1]
portNumberString = sys.argv[2]
numberOfThreads = sys.argv[3]
numberOfPackets = sys.argv[4]





print("Connecting to {ipadd} on port {prt}, with {noofthreads} threads, each sending {noPackets} packets!".format(\
	ipadd = ipaddressString, \
	prt = portNumberString, \
	noofthreads = numberOfThreads,\
	noPackets = numberOfPackets\
	   ))

flag = False
# Define a function for the thread



    

# Create two threads as follows
try:
    maxThreads = int(numberOfThreads)
    for x in range(0,maxThreads):
        name = "%s" % x
        _thread.start_new_thread( connect_to_server, (name, 2))
      #  time.sleep(2)
    
except Exception as ex:
    template = "An exception of type {0} occurred. Arguments:\n{1!r}"
    message = template.format(type(ex).__name__, ex.args)
    print (message)

while not(keyboard.is_pressed("esc")):
	if(keyboard.is_pressed("space")):
		flag = True
	pass