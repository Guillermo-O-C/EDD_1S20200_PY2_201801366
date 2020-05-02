/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Guillermo
 */

public class Block {
    public static int indexControl=1;
    public static String previousHash;
    static String currentHash;
    
    private static byte[] encrypting(String content)throws NoSuchAlgorithmException{     
       MessageDigest md = MessageDigest.getInstance("SHA-256");
       byte[] hashInBytes = md.digest(content.getBytes(StandardCharsets.UTF_8));
       return hashInBytes;
    }
    public static String toHexString(byte[] hash) 
    { 
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        } 
        currentHash=sb.toString();
        return sb.toString();
    } 
    private static int calculateNonce(String content) throws NoSuchAlgorithmException{
        int nonce =0;
        while(true){
            System.out.println(content+Integer.toString(nonce));
            byte[] hash = encrypting(content+Integer.toString(nonce));
            System.out.println(toHexString(hash).substring(0, 4));
            if(toHexString(hash).substring(0, 4).compareTo("0000")==0){
                break;
            }
            nonce++;
        }
        return nonce;
    }
    private static void assembleJSONDoc(String FileName) throws IOException, NoSuchAlgorithmException{
        JSONObject newDoc = new JSONObject();
        newDoc.put("INDEX", Integer.toString(indexControl));indexControl++;
        String timeStamp =new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
        newDoc.put("TIMESTAMP", timeStamp);
        newDoc.put("NONCE", calculateNonce(Integer.toString(indexControl)+timeStamp+previousHash+usaclibrary.USACLibrary.CurrentBlockData));
        newDoc.put("DATA", usaclibrary.USACLibrary.CurrentBlockData);
        if(indexControl==1){
            newDoc.put("PREVIOUSHASH", 0000);
        }else{
            newDoc.put("PREVIOUSHASH", previousHash);            
        }
        previousHash=currentHash;
        newDoc.put("HASH", previousHash);
        File dir = new File("BLOCKS");
        dir.mkdirs();   
        File temporal = new File(dir, FileName+".json");
        try (FileWriter TemporalFile = new FileWriter(temporal)) {
            TemporalFile.write(newDoc.toJSONString());
        }
        temporal.createNewFile();
        usaclibrary.USACLibrary.CurrentBlockData= new JSONArray();
    }
    public static void SaveJSONDoc() throws IOException, NoSuchAlgorithmException{
        assembleJSONDoc("BLOCK_"+Integer.toString(indexControl));
    }
}
