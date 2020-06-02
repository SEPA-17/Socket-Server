#!/usr/bin/env python
 
import socket
import threading
import time
import random
import keyboard
HOST = "localhost"
PORT = 1234
 
#sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#sock.connect((HOST, PORT))


#loop = True
 

#while(loop):
#    message = input()
#
#   sock.sendall(message.encode())
#    data = sock.recv(1024)
#    data = data.decode("ASCII")
#
#    print(data)
#     
#    if ( data == "exit\n" ):
#        loop = false
#        sock.close()


# class myThread(threading.Thread):
#   def ___init___(self,threadID):
#       threading.Thread.___init___(self)
#       self.threadID = threadID

#   def run(self):
#       print_time(self.threadID)


# def print_time(threadID):
#   while True:
#       print("%s: %s" % (threadID, time.ctime(time.time)()))

# for x in range(0,100):
#   thread = myThread((x))
#   thread.start()


import _thread
import time
import sys,socket


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
def connect_to_server( threadName, delay):
    try:
        s = socket.socket()
        s.connect((ipaddressString,int(portNumberString)))
        cnt = 0
        while (cnt< (int(numberOfPackets))):
            count = str(cnt)
            time.sleep(delay)
            timeString = time.strftime("%d/%m/%y %H:%M", time.localtime())
            lineString = f"{threadName},{timeString},{count},{count},{count},{count},{count},{count},{count},{count},{count},{count},{count} \n"
            print(lineString)
            s.send(lineString.encode())
            cnt = cnt + 1
        s.send("!Finished!\n".encode()) 
        data = s.recv(1024)
        print(data.decode())

    except socket.error as ex:
        template = "An exception of type {0} occurred. Arguments:\n{1!r}"
        message = template.format(type(ex).__name__, ex.args)
        print (message)


    

# Create two threads as follows
try:
    maxThreads = int(numberOfThreads)
    for x in range(0,maxThreads):
        name = "%s" % x
        _thread.start_new_thread( connect_to_server, (name, 2, ))
    
except Exception as ex:
    template = "An exception of type {0} occurred. Arguments:\n{1!r}"
    message = template.format(type(ex).__name__, ex.args)
    print (message)

while not(keyboard.is_pressed("esc")):
	if(keyboard.is_pressed("space")):
		flag = True
	pass