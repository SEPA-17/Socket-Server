#! <python dir> python3
#
# Performs creates a simple test connection to the supplied IP address and socket,
# and then pushes the CSV data to the host.
# 
#
import sys,socket

if(len(sys.argv) < 3):
	print("Not enough arguemnts!")
	print("To run this script: python testCSV.py <ip address> <port number>")
	exit()

ipaddressString = sys.argv[1]
portNumberString = sys.argv[2]

try:
	s = socket.socket()
except socket.error as err:
	print ("unable to create socket!")
	exit()

try:
	s.connect((ipaddressString,int(portNumberString)))
except Exception as e:
	print("Exception rasied in conencting to remote server")
	print(type(e))
	print(e)
	s.close()
	exit()


f = open("test.csv","rb")
headers = f.readline()
#this will only read the remain (data) values
for x in f:
	s.send(x)




s.close()
f.close()