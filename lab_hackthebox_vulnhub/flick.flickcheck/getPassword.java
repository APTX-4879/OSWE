import java.util.Base64;  
public class HelloWorld{
    
    public static String validate(String input) {
        char[] key = {'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 's', 'u', 'p', 'e', 'r', ' ', 's', 'e', 'c', 'r', 'e', 't', ' ', 'm', 'e', 's', 's', 'a', 'g', 'e', '!'};
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }
        return output.toString();
    }

     public static void main(String []args){
        Base64.Decoder decoder = Base64.getDecoder(); 
        String integrity_check = "YFhaRBMNFRQDFxJEFlFDExIDVUMGEhcLAUNFBVdWQGFeXBIVWEsZWQ==";
        String text = new String(decoder.decode(integrity_check));  
        String x = validate(text);
        System.out.print(x);
     }
}

//40373df4b7a1f413af61cf7fd06d03a565a51898
//ssh -o StrictHostKeyChecking=no robin@IP -> ssh: connect to host 192.168.0.129 port 22: No route to host
//ssh -o StrictHostKeyChecking=no robin@localhost (from shell www-data)
//to bryan: sudo -u bryan LD_PRELOAD=/tmp/pe.so /usr/local/bin/dice

//to Sean
