package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.service.CoOperatorService;

public class UnionIntersectTest {

	@Test
	public void test() {
		//int[] arr = {1,2,3};
		int[][] arr1 =          {{}, {}     , {1,2,3}, {1,2,3}, {1,2,3}      , {1,2,3}  , {1,3,5} };
		int[][] arr2 =          {{}, {1,2,3}, {}     , {1,2,3}, {4,5,6}      , {1,2,3,4}, {3,5,1} };
		int[][] expectedUnion = {{}, {1,2,3}, {1,2,3}, {1,2,3}, {1,2,3,4,5,6}, {1,2,3,4}, {1,3,5} };
		int[][] expectedIntersect
							  = {{}, {}     , {}     , {1,2,3}, {}           , {1,2,3}  , {1,3,5} };
		
		
		for(int i =0; i<arr1.length;i++) {
			ArrayList <Integer> actual=(ArrayList<Integer>) CoOperatorService.getUnion( mI(arr1[i]), mI(arr2[i]) );
			assertTrue("Union: \n"+ getError(mI(arr1[i]),mI(arr2[i]),mI(expectedUnion[i]),actual)
					,testEquals(mI(expectedUnion[i]),actual));
		}
		
		for(int i =0; i<arr1.length;i++) {
			
			ArrayList <Integer> actual=(ArrayList<Integer>) CoOperatorService.getIntersect( mI(arr1[i]), mI(arr2[i]) );
			assertTrue("Intersection"+ getError(mI(arr1[i]),mI(arr2[i]),mI(expectedIntersect[i]),actual)
					,testEquals(mI(expectedIntersect[i]),actual));
		}
		
	}
	
	public boolean testEquals(ArrayList a1,ArrayList a2) {
		if(a1.isEmpty()&&a2.isEmpty()) {
			return true;
		} else {
			if(a1.size()!=a2.size()) {
				return false;
			}
			for (Object el:a1) {
			
				if(!a2.contains(el)) {
					return false;
				}
				
				a2.remove(el);
			}
		}
		return true;
	}
	
	public ArrayList <Integer> mI(int[] arr) {
		ArrayList <Integer>thisArrList= new ArrayList <Integer>();
		for (int a :arr) {
			thisArrList.add(new Integer(a));
		}
		return thisArrList;
	}
	
	public String getError(ArrayList a1,ArrayList a2, ArrayList expected,ArrayList actual) {
		return("Input: "+a1.toString()+"\t"+a2.toString()+"\n"
					+"Expected: "+expected.toString()+
					"Actual: "+ actual.toString()
				);
	}
	
}
