package apps;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
public class Dummy {
	
    /* Henry */
	public Dummy() {
		try {

		}catch(Exception e1){

		}
	}


	public static String sha256hex(String input) {
        return DigestUtils.sha256Hex(input);
    }

    public void toto() {}

    public static void m1() throws Exception {
        m2();
    }

    public static void m2() throws Exception {
        throw new RuntimeException();
    }


    public void totoUndocumented() {}

    /**
     * Documented method
     */
    protected void totoProtected() {}

    protected void totoProtectedUndocumented() {}

    public ArrayList<Integer> arr = new ArrayList<Integer>();

    public ArrayList<Integer> getArray() {
        return this.arr;    
    }
}