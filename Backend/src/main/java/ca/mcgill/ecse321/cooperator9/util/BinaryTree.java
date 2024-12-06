package ca.mcgill.ecse321.cooperator9.util;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Agent-Bennette (Edwin)
 *
 */
//Binary tree meant to make it easy to sort a set as long as we build it from scratch.
//Note that the current implementation of the tree makes nodes that are equal in value placed as left or
//right children. Whether it goes to the left or right alternates. May as well be random.
public class BinaryTree<T> {

	//Nodes of the Binary Tree
	class Node{
		long value;
		T data;
		Node left;
		Node right;
		Node( long value , T data ){
			this.value = value;
			this.data = data;
		}
	}
	
	//The Root of the Binary Tree
	private Node root = null;
	//Equalizer
	private boolean nextEqualsGoesLeft = true;
	//Required for making the ordered output
	private List<T> orderedList = new ArrayList<T>();
	
	//Adding method
	public void add( T data, long value ) {
		//System.out.println("received call to add to tree...");
		this.root = add( this.root, data, value );
	}
	//Recursive adding method which actually does the adding.
	private Node add( Node root, T data , long value) {
		//System.out.println("received recursive add call...");
		if(root==null) {
			root = new Node(value,data);
			//System.out.println("Found edge node. Setting and closing recursive add call.");
		}else
			if( root.value>value ) {
				root.left = add( root.left,data,value );
				//System.out.println("evaluated new data to be lesser than node. Calling left recursive add.");
			}else if( root.value<value ) {
				root.right = add( root.right,data,value );
				//System.out.println("evaluated new data to be greater than node. Calling right recursive add.");
			}else {
				if( nextEqualsGoesLeft ) {
					root.left = add( root.left,data,value );
					//System.out.println("evaluated new data to be equal to node. Calling left recursive add.");
				}else {
					root.right = add( root.right,data,value );
					//System.out.println("evaluated new data to be equal to node. Calling right recursive add.");
				nextEqualsGoesLeft = !nextEqualsGoesLeft;
				}
			}
		//System.out.println("Returning node.");
		return root;
	}
	
	//Produces a list ordered from least to greatest.
	public List<T> inOrder(){
		if(orderedList.size() != 0)
			orderedList.clear();
		traverseAndBuildIterable(root);
		return orderedList;
	}
	//Recursive adding method which actually does the tree traverse to build the iterable.
	private void traverseAndBuildIterable(Node node) {
		//System.out.println("Entering new recursion method...");
		if(node==null) {
			//System.out.println("Detected null node. Ending recursion method.");
			return;
		}
		//System.out.println("Calling left traversal...");
		traverseAndBuildIterable(node.left);
		//System.out.println("Adding to ordered list...");
		orderedList.add(node.data);
		//System.out.println("Calling right traversal...");
		traverseAndBuildIterable(node.right);
		//System.out.println("Ending recursion method.");
		return;
	}
	
	
	/*
	//Testing Code
	public static void main (String [] args) {
		System.out.println("Now testing the binary tree code!");
		
		BinaryTree<String> tree = new BinaryTree<String>();
		System.out.println("Adding elements...");
		tree.add("Warudo",1);
		tree.add("Hello", 0);
		System.out.println("Sorting elements...");
		List<String> sortedList = tree.inOrder();
		System.out.println("Printing sorted tree:");
		for(int i = 0 ; i < sortedList.size(); i++ )
			System.out.println("index " + i + " item:"+sortedList.get(i));
		System.out.println("Now ending the binary tree test code!");
	}
	*/
	
}
