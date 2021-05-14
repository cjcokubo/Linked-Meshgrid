package question1;

import java.util.*;

public class LinkedMeshgrid<E>{

	/* A simple linked meshgrid class */
	
	/* the private node class */

	/* maintain an array with all the element*/

	int height = 0;
	int width = 0;

	private static class Node<E> implements Position<E>{
		private E element;
		private Node<E> above;
		private Node<E> below;
		private Node<E> left;
		private Node<E> right;

		/* main constructor*/
		public Node(E e, Node<E> a, Node<E> b, Node<E> l, Node<E> r){
			element = e;
			above = a;
			below = b;
			left = l;
			right = r;
		}


		/* accessors and mutators */
		public E getElement(){
			return element;
		} 

		public Node<E> getLeft(){return left;}
		public Node<E> getRight(){return right;}
		public Node<E> getAbove(){return above;}
		public Node<E> getBelow(){return below;}

		public void setElement(E e){element = e;}

		public void setAbove(Node<E> a){above =a;} 
		public void setBelow(Node<E> b){below =b;} 
		public void setLeft(Node<E> l){left =l;} 
		public void setRight(Node<E> r){right =r;} 



	}

	private Node<E> header;
	private Node<E> trailer;


	/* Question 1: Main constructor for the LinkedMeshgrid class */
	public LinkedMeshgrid(int h, int w){
		header = new Node<>(null, null, null, null, null);
		trailer = new Node<>(null, null, null, header, null);
		header.setRight(trailer);
		
		height = h;
		width = w;

		/*To be done. Should initialize the Grid with height*width nodes. */
		Node<E> current = header;
		for(int i=0; i<h; i++) {
			Node<E> first_in_row = current;
			for(int j=0; j<w; j++) {
				Node<E> left = current.getLeft();
				Node<E> above = current.getAbove();
				if(i==0 || i==h-1) {
					if(i==0) current.setBelow(new Node<>(null, current, null, null, null));
					else current.setAbove(above); //if(i==h-1)
					
					if(j!=0) current.setLeft(left);
					if(j!=w-1) current.setRight(new Node<>(null, null, null, current, null));
				}
				else if(j==0 || j==w-1){
					if(j==0) current.setRight(new Node<>(null, null, null, current, null));
					else current.setLeft(left); //if(j==w-1)
					
					if(i!=0) current.setAbove(above);
					if(i!=h-1) current.setBelow(new Node<>(null, current, null, null, null));
				}
				else {
					current.setAbove(above);
					current.setBelow(new Node<>(null, current, null, null, null));
					current.setLeft(left);
					current.setRight(new Node<>(null, null, null, current, null));
				}
				
				// Check if done initializing all nodes in current row. If yes, move to next row.
				if(j==w-1) current = first_in_row.getBelow();
				else current = current.getRight();
			}
		}
		trailer = current;
	}

	public int grid_height(){return height;}
	public int grid_width(){return width;}

	/* validate the position and returns it as a node */
	private Node<E> validate(Position<E> p) throws IllegalArgumentException{
		if(!(p instanceof Node)) throw new IllegalArgumentException("invalid p");
		Node<E> node = (Node<E>) p; // safe cast
		if (node.getRight()==null)
			throw new IllegalArgumentException("p is not longer in the list");
		return node; 
	}

	public boolean isEmpty(){
		return ((height==0) && (width == 0));
	}


	/*returns given node as a position*/
	private Position<E> position(Node<E> node){
		if (node == header || node == trailer){return null;}
		return node ;
	}


	/* return first position in the list */
	public Position<E> first(){
		return position(header.getRight());
	} 

	public Position<E> last(){
		return position(trailer.getLeft());
	} 	


	/* returns the Position on the left of p*/
	public Position<E> leftPos(Position<E> p){
		// To be completed 
		Node<E> node = validate(p);
		return position(node.getLeft());

	} 

	/* returns the Position on the right of p*/
	public Position<E> rightPos(Position<E> p){
		// To be completed 
		Node<E> node = validate(p);
		return position(node.getRight());
	} 


	/* returns the Position on top of p*/
	public Position<E> abovePos(Position<E> p){
		// To be completed 
		Node<E> node = validate(p);
		return position(node.getAbove());

	} 

	/* returns the Position below p*/
	public Position<E> belowPos(Position<E> p){
		// To be completed 
		Node<E> node = validate(p);
		return position(node.getBelow());
	} 

	/*replace the element stored at position p and return the value 
	previously stored at that position*/
	public E set(Position<E> p, E e) throws IllegalArgumentException{
		// to be completed. 
		Node<E> node = validate(p);
		E answer = node.getElement();
		node.setElement(e);
		return answer;
	}
	
	// For Checking
	public E accessNode(int row, int col){
		Node<E> current = header;
		//int h = grid_height();
		int w = grid_width();
		
		Node<E> first_in_row = null;
		//boolean is_last_in_row = false;
		for(int i=0; i<row; i++) {
			first_in_row = current;
			for(int j=0; j<col; j++) {
				// Check if done initializing all nodes in current row. If yes, move to next row.
				if(i==0 && j==0) current = header;
				else if(j==w-1) current = first_in_row.getBelow();
				else current = current.getRight();
			}
		}

		return current.getElement();
	}
	
	/*------------------------------------------------------------------------------------------------*/
	// QUESTION 1.2
	
	// ITERATING OVER ELEMENTS
	private int currentNodeNumber = 0;

	private class listIterator implements Iterator<E>{
		
		public boolean hasNext(){
			Node<E> current = header;
			int h = grid_height();
			int w = grid_width();
			
			int counter = 0;
			boolean stop = false;
			Node<E> first_in_row = null;
			boolean is_last_in_row = false;
			for(int i=0; (i<h) && !stop; i++) {
				first_in_row = current;
				is_last_in_row = false;
				for(int j=0; (j<w) && !stop; j++) {
					// Check if done initializing all nodes in current row. If yes, move to next row.
					if(j==w-1) {
						current = first_in_row.getBelow();
						is_last_in_row = true;
					}
					else current = current.getRight();
					
					if(counter == currentNodeNumber) stop = true;
					counter++;
				}
			}

			if(is_last_in_row) return first_in_row.getBelow() != null;
			else return current.getRight() != null;
		}
		
		public E next(){
			Node<E> current = header;
			int h = grid_height();
			int w = grid_width();
			
			int counter = 0;
			boolean stop = false;
			Node<E> first_in_row = null;
			boolean is_last_in_row = false;
			for(int i=0; (i<h) && !stop; i++) {
				first_in_row = current;
				is_last_in_row = false;
				for(int j=0; (j<w) && !stop; j++) {
					// Check if done initializing all nodes in current row. If yes, move to next row.
					if(j==w-1) {
						current = first_in_row.getBelow();
						is_last_in_row = true;
					}
					else current = current.getRight();
					
					if(counter == currentNodeNumber) stop = true;
					counter += 1;
				}
			}

			currentNodeNumber++;
			
			if(is_last_in_row) return first_in_row.getBelow().getElement();
			else return current.getRight().getElement();
		}
		
		public void remove(){}

	}


	public Iterator<E> iterator(){

		Iterator<E> mylistIterator = new listIterator();

		return mylistIterator;
	}
	
	// ITERATING OVER POSITIONS
	private int currentPositionNumber = 0;

	private class positionIterator implements Iterator<Position<E>>{
		
		public boolean hasNext(){
			Node<E> current = header;
			int h = grid_height();
			int w = grid_width();
			
			int counter = 0;
			boolean stop = false;
			Node<E> first_in_row = null;
			boolean is_last_in_row = false;
			for(int i=0; (i<h) && !stop; i++) {
				first_in_row = current;
				is_last_in_row = false;
				for(int j=0; (j<w) && !stop; j++) {
					// Check if done initializing all nodes in current row. If yes, move to next row.
					if(j==w-1) {
						current = first_in_row.getBelow();
						is_last_in_row = true;
					}
					else current = current.getRight();
					
					if(counter == currentPositionNumber) stop = true;
					counter++;
				}
			}

			if(is_last_in_row) return first_in_row.getBelow() != null;
			else return current.getRight() != null;
		}

		public Position<E> next(){
			Node<E> current = header;
			int h = grid_height();
			int w = grid_width();
			
			int counter = 0;
			boolean stop = false;
			Node<E> first_in_row = null;
			boolean is_last_in_row = false;
			for(int i=0; (i<h) && !stop; i++) {
				first_in_row = current;
				is_last_in_row = false;
				for(int j=0; (j<w) && !stop; j++) {
					// Check if done initializing all nodes in current row. If yes, move to next row.
					if(j==w-1) {
						current = first_in_row.getBelow();
						is_last_in_row = true;
					}
					else current = current.getRight();
					
					if(counter == currentPositionNumber) stop = true;
					counter += 1;
				}
			}

			currentPositionNumber++;
			
			if(is_last_in_row) return first_in_row.getBelow();
			else return current.getRight();

		}

		public void remove(){}

	}


	public E getElementFromPosition(Position<E> p ){
		Node<E> myNode;
		myNode = (Node<E>) p;

		return myNode.getElement();
	} 

	// should return iterator on Positions
	private class PositionIterable implements Iterable<Position<E>>{

		public Iterator<Position<E>> iterator(){
			Iterator<Position<E>> myPositionIterator = new positionIterator();

			return myPositionIterator;
		}
	}


	public Iterable<Position<E>> position(){
		
		return new PositionIterable();

	}

}