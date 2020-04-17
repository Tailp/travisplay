package apps;

import spoon.template.TemplateParameter;
import java.lang.IndexOutOfBoundsException;

import java.util.Collection;

public class CheckBoundMatcher {
  // Step 1:
  public TemplateParameter<Collection<?>> col;

  // Step 2
  public void matcher1() {
    if (col.S().size() > 10) {
      throw new IndexOutOfBoundsException();
    }
  }
}