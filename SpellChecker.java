import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class SpellChecker {
    static int wcount = 0;
    HashTable<String> linearProbe = new HashTable<>();
    HashMap<Integer,ArrayList<String>> wordsBasedOnCharCount = new HashMap<>();
    public static void main(String args[]) throws Exception{
        
        SpellChecker spellChecker = new SpellChecker(); 
        
        File file = new File("dictionary.txt");
        Scanner scanner  = new Scanner(file);
        while(scanner.hasNext()){
            String word = scanner.next();
            spellChecker.linearProbe.insert(word);
            Integer length = word.length();
            if(spellChecker.wordsBasedOnCharCount.containsKey(length)){
                ArrayList<String> list = spellChecker.wordsBasedOnCharCount.get(length);
                list.add(word);
            }else{
                ArrayList<String> list = new ArrayList<>();
                list.add(word);
                spellChecker.wordsBasedOnCharCount.put(length, list);
            }
            
        }
        File fileToBeChecked = new File("nosilverbullet.txt");
        scanner = new Scanner(fileToBeChecked);
        while(scanner.hasNext()){
            boolean noPunctuations = true;
            String incomingWord = scanner.next().toLowerCase();
            char[] charArray = incomingWord.toCharArray();
            int[] positionsOfPuncs = new int[charArray.length];
            positionsOfPuncs[0] = 0;
            int numberOfPuncs = 0;
            for(int i =0; i<charArray.length ; i++){
                char eachChar = charArray[i];
                if(eachChar>96 && eachChar<123){
                    
                }else{
                    noPunctuations = false;
                    positionsOfPuncs[++numberOfPuncs] = i;
                }
            }
            if(noPunctuations){
                spellChecker.checkSpelling(incomingWord);
            }else{
                positionsOfPuncs[++numberOfPuncs] = charArray.length;
                for(int i = 0; i<numberOfPuncs  ; i++){
                    try{
                    String subString = "";
                    if(i==0){
                        subString = incomingWord.substring(positionsOfPuncs[i], positionsOfPuncs[i+1]);
                    }else{
                        subString = incomingWord.substring(positionsOfPuncs[i]+1, positionsOfPuncs[i+1]);
                    }
                    char[] subAray = subString.toCharArray();
                    boolean noSinglePuncCheck = true;
                    if(subAray[0] < 97 || subAray[0] > 122){
                        noSinglePuncCheck = false;
                    }
                    if(!subString.equals("") && noSinglePuncCheck)
                        spellChecker.checkSpelling(subString);
                    
                    }catch(StringIndexOutOfBoundsException sioobe){
                        
                    }catch(ArrayIndexOutOfBoundsException aioobe){
                        
                    }
                }
            }
        }

    }
    
    public void checkSpelling(String word){
       if(!linearProbe.contains(word)){
            System.out.println(word + " is misspelt");
            System.out.print("Alternate Options are : ");
            char[] misspeltWordArray = word.toCharArray();
            ArrayList<String> wordsOfSameLength = wordsBasedOnCharCount.get(word.length());
            for(int i=0;i<wordsOfSameLength.size();i++){
                String alternateWord = wordsOfSameLength.get(i);
                int noOfDiff = 0;
                char[] alternateWordArray = alternateWord.toCharArray();
                for(int j = 0 ; j < word.length() ; j++){
                    if(misspeltWordArray[j]!= alternateWordArray[j]){
                        noOfDiff++;
                    }
                    if(noOfDiff>1){
                        break;
                    }
                }
                if(noOfDiff == 1){
                    System.out.print(alternateWord + " ");
                }
            }
            System.out.println("");
        }
        
    }
}
