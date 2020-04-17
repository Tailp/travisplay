package apps;

import spoon.template.TemplateParameter;
import java.lang.IndexOutOfBoundsException;

import java.util.Collection;

public class TestMatcher {
  public TemplateParameter<Collection<?>> _col_;

  public void matcher1() {
    if (_col_.S().size() > 10)
      throw new IndexOutOfBoundsException();
  }

  public void test() {
  	this.getClass().getClassLoader();
  }
}