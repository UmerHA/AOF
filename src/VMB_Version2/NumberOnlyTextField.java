package VMB_Version2;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class NumberOnlyTextField extends JTextField {
	private static final long serialVersionUID = 1L;

	     public NumberOnlyTextField(int cols) {
	         super(cols);
	     }
	     public NumberOnlyTextField() {
	         super();
	     }
	 
	     protected Document createDefaultModel() {
	         return new NumOnlyDocument();
	     }
	 
	     class NumOnlyDocument extends PlainDocument {
			private static final long serialVersionUID = 1L;

			public void insertString(int offs, String str, AttributeSet a)
	             throws BadLocationException {
	 
	             if (str == null)
	                 return;
	             if (!isNumber(str))
	            	 return;
	             
	             super.insertString(offs, str, a);
	         }
	     }
	     public static boolean isNumber (String str) {
	    	 try {
	    		 Long.parseLong(str);
	    	 } catch (Exception e) {
	    		 return false;
	    	 }

	    	 return true;
	     }
}
