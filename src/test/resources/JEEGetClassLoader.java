package apps;


import java.lang.ClassLoader;
import java.lang.ClassNotFoundException;
import javax.ejb.embeddable.EJBContainer;

public class JEEGetClassLoader {

	ClassLoader d = this.getClass().getClassLoader();

	public void case1() {
		ClassLoader c = this.getClass().getClassLoader();
	}

	public void case2() throws ClassNotFoundException{
		Dummy.class.getClassLoader().loadClass("anotherclass");
	}

	/* case 3 - anonymous class */
	Person t = new Person() {
		public void eat() {
			ClassLoader e = this.getClass().getClassLoader();
		}   
	};

	/* case 4 - inner class */
	abstract class InnerClass {
		ClassLoader f = this.getClass().getClassLoader();

		public void hello() {
			this.getClass().getClassLoader();
		}
	}

	public void usingEJB() {
		EJBContainer container = new EJBContainer();
	}
}