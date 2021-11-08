import java.util.UUID;

public class myUUID{
    public static void main(String args[]){
        String myUUID = new UUID(("12345").hashCode(), ((("12345").hashCode()) << 32) | (("12345").hashCode())).toString();
        System.out.print(myUUID);
    }
}
