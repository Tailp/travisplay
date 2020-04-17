package apps;



public class Template4Matcher {
	ClassLoader c = this.getClass().getClassLoader();

	public void case1() {
		ClassLoader c = this.getClass().getClassLoader();
	}


	public void case2() {
		Template4Matcher.class.getClassLoader();
	}

	public void case4() {
		ClassLoader c = this.getClass().getClassLoader().loadClass("newClass").loadClass("AnotherClass");
	}
}