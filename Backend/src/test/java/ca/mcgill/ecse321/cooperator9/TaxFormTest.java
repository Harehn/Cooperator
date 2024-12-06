package ca.mcgill.ecse321.cooperator9;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse321.cooperator9.model.TaxForm;

public class TaxFormTest {

	@Test
	public void test() {
		TaxForm taxForm= createTaxForm();
		assertEquals("Description",taxForm.getDescription());
		assertEquals((short)1998,taxForm.getYear());
		assertEquals("Instructions",taxForm.getInstructions());
		assertEquals("Name",taxForm.getName());
		assertEquals("C://Users/Candy/ecse321-group-project-09/Backend/",taxForm.getUri());
		for( int i = 0 ; i < 4 ; i++ )
			assertTrue(taxForm.getData()[i] == (byte)i);
		//assertEquals(19,taxForm.getDocID());
	}
	
	public TaxForm createTaxForm() {
		byte[] byteArray = {(byte)0,(byte)1,(byte)2,(byte)3};
		TaxForm taxForm = 
				new TaxForm("Name","C://Users/Candy/ecse321-group-project-09/Backend/",byteArray,(short)1998);
		taxForm.setDescription("Description");
		taxForm.setInstructions("Instructions");
		return taxForm;

 }
}