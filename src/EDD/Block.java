/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;
import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Guillermo
 */

public class Block {
    public static int indexControl=1;
    public static String previousHash="0000";
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
    public static BigInteger calculateNonce(String content) throws NoSuchAlgorithmException{
        BigInteger nonce =new BigInteger("0");
        Thread t =new Thread();
        while(true){
            System.out.println(content+nonce);
            byte[] hash = encrypting(content+nonce);
            System.out.println(toHexString(hash).substring(0, 4));
            if(toHexString(hash).substring(0, 4).compareTo("0000")==0){
                break;
            }
            nonce = nonce.add(BigInteger.ONE);
        }
        return nonce;
    }
    public static boolean validateBlock(String content, String nonce) throws NoSuchAlgorithmException{
         byte[] hash = encrypting(content+nonce);
            System.out.println(toHexString(hash).substring(0, 4));
            if(toHexString(hash).substring(0, 4).compareTo("0000")==0){
                return true;
            }
         return false;
    }
    private static void assembleJSONDoc(String FileName) throws IOException, NoSuchAlgorithmException{
        JSONObject newDoc = new JSONObject();
        newDoc.put("INDEX", Integer.toString(indexControl));
        String timeStamp =new SimpleDateFormat("yyyy.MM.d::HH:mm:ss").format(new Date());
        newDoc.put("TIMESTAMP", timeStamp);
        newDoc.put("NONCE", calculateNonce(Integer.toString(indexControl)+timeStamp+previousHash+usaclibrary.USACLibrary.CurrentBlockData));
        newDoc.put("DATA", usaclibrary.USACLibrary.CurrentBlockData);
        if(indexControl==1){
            newDoc.put("PREVIOUSHASH", "0000");
        }else{
            newDoc.put("PREVIOUSHASH", previousHash);            
        }
        indexControl++;
        previousHash=currentHash;
        newDoc.put("HASH", previousHash);
        File dir = new File("BLOCKS");
        dir.mkdirs();   
        File temporal = new File(dir, FileName+".json");
        try (FileWriter TemporalFile = new FileWriter(temporal)) {
            TemporalFile.write(newDoc.toJSONString());
        }
        temporal.createNewFile();
        File myObj = new File(dir, "BLOCK_"+Integer.toString(indexControl-1)+".json");
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                usaclibrary.USACLibrary.BLOCK_String+=myReader.nextLine();
            }
            usaclibrary.USACLibrary.BLOCK_String+="#@";
        }
        usaclibrary.USACLibrary.CurrentBlockData= new JSONArray();
    }
    public static void SaveJSONDoc() throws IOException, NoSuchAlgorithmException{
        assembleJSONDoc("BLOCK_"+Integer.toString(indexControl));
    }
    public static void SaveRecibedBlock(String entry) throws IOException, NoSuchAlgorithmException{
        File dir = new File("BLOCKS");
        dir.mkdirs();   
        File temporal = new File(dir, "BLOCK_"+Integer.toString(indexControl)+".json");indexControl++;        
        try (FileWriter TemporalFile = new FileWriter(temporal)) {
            TemporalFile.write(entry);
        }
        temporal.createNewFile();
    }
    public static String GetBlockChain(){
        String blockChain = "";
        for(int i =1;i<indexControl;i++){
            try {
                blockChain+="#@"; 
                File dir = new File("BLOCKS");
                dir.mkdirs();
                File myObj = new File(dir, "BLOCK_"+Integer.toString(i)+".json");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    blockChain+=myReader.nextLine();
                }
                myReader.close();
              } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
        }
        System.out.println("Current blockchain is:"+blockChain);
        return blockChain;
    }
}
