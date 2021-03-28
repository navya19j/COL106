// You should utilise your implementation of ArrayDeque methods to implement this
public class Stack implements StackInterface {
	ArrayDeque dec = new ArrayDeque();	
	public void push(Object o){
    	//you need to implement this
		try{
			dec.insertLast(o);
		}
    	catch(Exception e){
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
  	}

	public Object pop() throws EmptyStackException{
    	//you need to implement this
    	try{
			return(dec.removeLast());
		}
    	catch(Exception e){
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public Object top() throws EmptyStackException{
    	//you need to implement this
    	try{
			return(dec.last());
		}
    	catch(Exception e){
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public boolean isEmpty(){
    	//you need to implement this
    	try{
			return(dec.isEmpty());
		}
    	catch(Exception e){
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

    public int size(){
    	//you need to implement this
    	try{
			return(dec.size());
		}
    	catch(Exception e){
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
    }
}