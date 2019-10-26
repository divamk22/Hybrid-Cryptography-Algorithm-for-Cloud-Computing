import java.math.BigInteger; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.security.Key; 
import java.time.format.TextStyle; 
import java.util.*; 
import java.io.*; 
import javax.crypto.Cipher; 
import javax.crypto.spec.SecretKeySpec;

public class TestFileEncryption 
{ 
    private static final String ALGORITHM = "Blowfish"; 
    private static String keyString; 
    public static void encrypt(File inputFile, File outputFile) throws Exception 
    { 
        doCrypto(Cipher.ENCRYPT_MODE, inputFile, outputFile); 
        System.out.println("File encrypted successfully!"); 
    }

    public static void decrypt(File inputFile, File outputFile) throws Exception 
    { 
        doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile); 
        System.out.println("File decrypted successfully!"); 
    }

    private static void doCrypto(int cipherMode, File inputFile, File outputFile) throws Exception 
    {
        Key secretKey = new SecretKeySpec(keyString.getBytes(), ALGORITHM); 
        Cipher cipher = Cipher.getInstance(ALGORITHM); 
        cipher.init(cipherMode, secretKey); 
        FileInputStream inputStream = new FileInputStream(inputFile); 
        byte[] inputBytes = new byte[(int) inputFile.length()]; 
        inputStream.read(inputBytes); 
        byte[] outputBytes = cipher.doFinal(inputBytes); 
        FileOutputStream outputStream = new FileOutputStream(outputFile); 
        outputStream.write(outputBytes); 
        inputStream.close(); 
        outputStream.close(); 
    }

    public static void main(String[] args) 
    { 
        Scanner in = new Scanner(System.in); 
        System.out.println("Enter the blowfish key to Encrypt the file:"); 
        String s1 = in.nextLine(); 
        int num; 
        BigInteger d, n, phi, u, a, e, p, q, ua, b, c, v;
        int j;
        String encrypt = ""; 
        BigInteger t = new BigInteger("1"); 
        p = new BigInteger("11"); 
        q = new BigInteger("7"); 
        n = p.multiply(q); 
        phi = (p.subtract(t)).multiply(q.subtract(t)); 
        e = new BigInteger("7"); 
        d = new BigInteger("43"); 
        u = new BigInteger("3"); 
        a = new BigInteger("31"); 
        ua = u.pow(a.intValue()); 
        BigInteger[]arr = new BigInteger[100];
        BigInteger[]dearr = new BigInteger[100]; 
        BigInteger[]ekey = new BigInteger[100]; 
        BigInteger[]enkey = new BigInteger[100]; 
        int i1, i2;
        
        File inputFile = new File("File600.docx"); 
        File encryptedFile = new File("file600.encrypted"); 
        File decryptedFile = new File("DecryptedFile600.docx"); 
        try 
        { 
            TestFileEncryption.keyString = s1; 
            TestFileEncryption.encrypt(inputFile, encryptedFile); 
            for(j=0; j<s1.length(); j++) 
            { 
                ekey[j] = BigInteger.valueOf((int)s1.charAt(j)); 
                //System.out.println(ekey[j]);
            } 
            System.out.println("Your Encrypted key using SRNN is:"); 
            for(j=0; j<s1.length(); j++) 
            { 
                b = (ekey[j].multiply(ua)).pow(e.intValue()); 
                enkey[j] = b.mod(n); 
                System.out.println(enkey[j]); 
            }
        } 
        catch (Exception z) 
        { 
            z.printStackTrace(); 
        }

        System.out.println("To decrypt your file Enter the SRNN key generated:"); 
        v = (u.pow(phi.intValue() - a.intValue())).mod(n); 
        System.out.println("Enter the number of keys to decrypt the file:"); 
        num = in.nextInt(); 
        System.out.println("Enter the values:"); 
        for(j=0; j<num; j++)
        { 
            arr[j] = in.nextBigInteger(); 
        } 
        for(j=0; j<num; j++) 
        { 
            dearr[j] = (((v.pow(e.intValue())).multiply(arr[j])).pow(d.intValue())).mod(n);
            encrypt = encrypt + (char)dearr[j].intValue(); 
        }
        System.out.println("Your blowfish key is: " + encrypt); 
        try 
        { 
            TestFileEncryption.keyString = encrypt; 
            TestFileEncryption.decrypt(encryptedFile, decryptedFile); 
        }
        catch(Exception z)
        { 
            z.printStackTrace(); 
        } 
    } 
}
