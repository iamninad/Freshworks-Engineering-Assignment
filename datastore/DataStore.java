/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore;
import java.io.File;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;
/**
 *
 * @author iamni
 */
public class DataStore {

    static Hashtable<String, Object> KeyValue_pair = new Hashtable<>();
    static HashMap<String, LocalTime> timing = new HashMap<>();
	
    public static void create(String key, Object value, LocalTime time) {
	KeyValue_pair.put(key, value);
	timing.put(key,time);
    }
	
    public static void read(String keyName) {
        try{
	LocalTime keytime = timing.get(keyName);
	int t = LocalTime.now().compareTo(keytime);
		
	if(t<0) {
            System.out.println(KeyValue_pair.get(keyName));
	}
	else {
            System.out.println("\tTime limit has exceeded!");
	}
        }catch(Exception e){
            System.out.println("\tNot found!!");
        }
    }
	
    public static void delete(String keyname) {
        try{
	LocalTime keyTime = timing.get(keyname);
	int tim = LocalTime.now().compareTo(keyTime);
		
	if(tim<0) {
            KeyValue_pair.remove(keyname);
            timing.remove(keyname);
	}
	else {
            System.out.println("\tTime limit has exceeded!");
	}
        }catch(Exception e){
            System.out.println("\tNot Found");
        }
    }
	
    public static void main(String[] args) throws IOException {
	Scanner sc = new Scanner(System.in);
	final int filesize = 1024*1024*1024;
	JSONObject jObj = new JSONObject();
		
	while(true) {
            System.out.println("Choose a operation to perform: ");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Delete");
            System.out.println("4. Exit");
            System.out.println("Enter your option: ");
			
            int choice = sc.nextInt();
            File f = new File("D:/json.txt");
            if(!f.exists()){
                f.createNewFile();
            }
              
            
            switch(choice) {
                
                case 1:
                    long bytes;
                    try {
                        bytes = Files.size(Paths.get("D:/json.txt"));
                        if(bytes>filesize) {
                        System.out.println("\tFile size limit exceeded!");
                        break;
                        }
                        System.out.println("Enter the key and value");
                        String key = sc.next();
                        if(KeyValue_pair.containsKey(key)) {
                            System.out.println("\tKey already exist!");
                            break;
                        }
                        else {
                            key = key.substring(0,Math.min(key.length(), 32));
                            String value = sc.next();
                            Object obj = value;
                            System.out.println("Enter the expire Time Limit in seconds: ");
                            int sec = sc.nextInt();
                            LocalTime now = LocalTime.now();
                            now = now.plusSeconds(sec);
                            create(key,value,now);
                            jObj.put(key,value);
                        }
                        break;
                    } catch (IOException e) {
                                            // TODO Auto-generated catch block
                        e.printStackTrace();

                    }
		
                case 2:
                    System.out.println("Enter the key to read: ");
                    String keyName = sc.next();
                    read(keyName);
                    break;
				
		case 3:
                    System.out.println("Enter the key to delete");
                    String keyname = sc.next();
                    delete(keyname);
                    break;
				
		case 4:
                    System.exit(0);
                    
                default:
                    System.out.println("\tPlease enter a correct choice!!");
                    System.out.println();
                }
		
                Files.write(Paths.get("D:/json.txt"), jObj.toJSONString().getBytes());
		
            }
	}
    
}
