public class SinglyLinkedList {       
     
    //Represent the head and tail of the singly linked list    
    public int data;    
    public SinglyLinkedList tail = null;
    public int size = 1;
        
    public SinglyLinkedList(int i) {
		data = i;
	}

	//addNode() will add a new node to the list    
    public SinglyLinkedList addNode(int data) {      
    	SinglyLinkedList newHead = new SinglyLinkedList(data); 
    	newHead.size = size+1;
        newHead.tail = this;
        return newHead;
    }    
        
    //display() will display all the nodes present in the list    
    public boolean contains(int i) {    
        //Node current will point to head
    	if (data == i) return true;
    	SinglyLinkedList currentTail = tail;
        while(currentTail != null) {  
        	if (currentTail.data == i)
        		return true;
        	currentTail = currentTail.tail;    
        }    
        return false;
    }
    
    public int accessElement() {
    	return data;
    }
    
    public int size() {
    	return size;
    }
    
    public static void main(String[] args) {    
        
        SinglyLinkedList sList = new SinglyLinkedList(0);    
            
        //Add nodes to the list    
        sList = sList.addNode(1);    
        sList = sList.addNode(2);    
        sList = sList.addNode(3);    
        sList = sList.addNode(4);    
            
        //System.out.println(sList.contains(2));
    }
}