import java.security.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CS210project{
	//main method declaration
    public static void main (String[] args) throws FileNotFoundException{
    	//used do while loop until a specified number is found
    	int maxmatch = 0;
    	do {
    		File file = new File("src/words.txt");
    		Scanner sc = new Scanner(file);
    		String[] sentence = new String[7308];
        	for(int i = 0; i < sentence.length; i++) {
    			sentence[i] = sc.nextLine();
    		}
        	String[] str = new String[100];
        	for(int i = 0; i < str.length; i++) {
        		//sentences are chosen randomly from the words.txt file
        		str[i] = sentence[(int)(Math.random()*sentence.length)];
        	}
    		
    		//turns all sentences into hashes and put them into a new array
    		String[] hash = new String[str.length];
    		for(int i = 0; i < hash.length; i++) {
    			hash[i] = sha256(str[i]);
    		}
    		sc.close();
    		
    		int count = 0;
    		int max = 0;
    		String closest1 = "";
    		String closest2 = "";
    		//using a triple for loop, compare every combination of sentences and its hashes
    		for(int i = 0; i < str.length; i++) {
    			for(int j = i+1; j < str.length; j++) {
    				for(int k = 0; k < 64; k++) {
    					//if two sentences has the same hash index, increment count
    					if(hash[i].charAt(k) == hash[j].charAt(k)) {
    						count++;
    					}
    				}
    				//if a new maximum has be found, update max
    				if((max < count) && (count != 64)) { //count != 64 makes sure two same sentences are not printed out
    					max = count;
    					//put the sentence that has the closest matches into variables
    					closest1 = str[i];
    					closest2 = str[j];
    				}
    				//resets the count
    				count = 0;
    			}
    		}
    		maxmatch = max;
    		System.out.println("");
    		System.out.println(closest1);
    		System.out.println(closest2);
    		System.out.println(sha256(closest1));
    		System.out.println(sha256(closest2));
    		System.out.println("\nHighest hash match is " + max);
    	}while(maxmatch < 18);
    }
    
    public static String sha256(String input){
    	//returns the hash of a sentence (given code by Dr. Phil)
        try{
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] salt = "CS210+".getBytes("UTF-8");
            mDigest.update(salt);
            byte[] data = mDigest.digest(input.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<data.length;i++){
                sb.append(Integer.toString((data[i]&0xff)+0x100,16).substring(1));
            }
            return sb.toString();
        }catch(Exception e){
            return(e.toString());
        }
    }
}