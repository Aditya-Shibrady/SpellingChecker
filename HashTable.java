public class HashTable<AnyType> {
	    
	    public HashTable(){
	        SSize = nextPrime(99999);
	        hashArray = new HashEntry[SSize];
	        allocateArray(SSize);
	        theSize = SSize;
	    }
	    
	    private void allocateArray(int n){
	        for(int i = 0 ; i < n; i++){
	            hashArray[i] = new HashEntry<>(null);
	        }
	    }
	    private static int SSize = 0;
	    private int theSize;
	    private int occupied=0;
	    private HashEntry<AnyType> hashArray[];
	    
	    public boolean contains(AnyType element){
	        int key = hashIt(element);
	        HashEntry valueToBeChecked = hashArray[key];
	        if(element.equals(valueToBeChecked.element)){
	            return true;
	        }else{
	            for(int i = key + 1 ; i < theSize ; i++){
	                if(element.equals(hashArray[i].element)){
	                        return true;
	                }else if(hashArray[i].element == null){
	                    return false;
	                }
	            }
	            for(int i=0 ; i < key; i++ ){
	                if(element.equals(hashArray[i].element)){
	                        return true;
	                }else if(hashArray[i].element == null){
	                    return false;
	                }
	            }
	            
	        }
	        return false;
	    }
	    
public boolean insert(AnyType element){
	        
	        boolean flagForInsert = false;
	        if(contains(element))
	            return false;
	        else{
	            int position = hashIt(element);
	            if(hashArray[position].element == null){
	                hashArray[position] = new HashEntry<AnyType>(element);
	                occupied++;
	                
	            }else{
	                int currentPos = position;
	                for(int i= ++position; i<theSize; i++){
	                    if(hashArray[i].element == null){
	                        hashArray[i] = new HashEntry<AnyType>(element);
	                        flagForInsert = true;
	                        occupied++;	                        
	                        break;
	                    }
	                }
	                if(!flagForInsert){
	                    for(int i= 0; i<currentPos; i++){
	                    if(hashArray[i].element == null){
	                        hashArray[i] = new HashEntry<AnyType>(element);
	                        flagForInsert = true;
	                        occupied++;	                        
	                        break;
	                    }
	                }
	                }	                
	            }
	        }
	        
	        if(occupied > hashArray.length/2){
	            rehash();
	        }
	        return true;
	    }

	    private int hashIt(AnyType element){
	        int hashCodeValue = element.hashCode() % theSize;
	        if(hashCodeValue < 0){
	            hashCodeValue += theSize;
	        }
	        return hashCodeValue;
	    }
	    
	    private void rehash(){
	        int newHashTableSize = nextPrime(theSize * 2);
	        HashEntry[] oldArray = new HashEntry[theSize];
	        for(int i = 0 ; i<theSize ; i++){
	            oldArray[i]= hashArray[i];
	        }
	        theSize = newHashTableSize;
	        hashArray = new HashEntry[theSize];
	        doClear();
	        allocateArray(theSize);
	        for(HashEntry<AnyType> hashEntry: oldArray){
	            if(hashEntry.element != null){
	                insert(hashEntry.element);
	            }
	        }
	    }
	    
	    private void doClear(){
	        allocateArray(theSize);
	        occupied = 0;
	    }
	    private int nextPrime(int n){
	        for(int i = n; ; i++){
	            if(isPrime(i)){
	                return i;
	            }
	        }
	      
	    }
	    
	    private boolean isPrime(int num){
	        if(num % 2 == 0) return num ==2;
	        if(num % 3 == 0) return num == 3;
	        int turn =4;
	        long sqrt = (long)Math.sqrt(num) + 1;
	        for(int i=5; i<sqrt ; turn = 6 - turn, i = i + turn){
	            if(num % i == 0){
	                return false;
	            }
	        }
	        return true;
	    }
	    
	    private static class HashEntry<AnyType>{
	        public AnyType element;
	        
	        
	        public HashEntry(AnyType element){
	            this.element = element;
	           }
	    }	   
	}


