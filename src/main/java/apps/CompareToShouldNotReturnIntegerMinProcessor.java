package apps;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtInterface;

import java.util.ArrayList;
import java.util.List;

import spoon.reflect.reference.*;
import spoon.reflect.visitor.filter.NamedElementFilter;

import java.util.Iterator;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtReturn;
import spoon.reflect.factory.Factory;
import spoon.reflect.code.CtLiteral;
import java.lang.Thread;

/**
 * Reports warnings when empty methods are found.
 */
public class CompareToShouldNotReturnIntegerMinProcessor extends AbstractProcessor<CtMethod<?>> {

	public final List<CtMethod<?>> emptyMethods = new ArrayList<>();
	private CtLiteral<?> elem2Replace;
	private Factory factory;

	public CompareToShouldNotReturnIntegerMinProcessor(Factory factory) {
		this.factory = factory;
		this.elem2Replace = factory.createLiteral(-1);
	}

	@Override
	public boolean isToBeProcessed(CtMethod<?> method) {

		String returnTypeName = method.getType().getSimpleName();
		if (method.getSimpleName().equals("compareTo") && (returnTypeName.equals("int") || returnTypeName.equals("Integer"))) {
			return true;
		}

		return false;
	}

	@Override	
	public void process(CtMethod<?> method) {
		List<CtReturn<?>> returns = method.getElements(new TypeFilter(CtReturn.class));
		for(CtReturn<?> elem : returns) {
			if (elem.getReturnedExpression().toString().indexOf("Integer.MIN_VALUE") != -1) {
				elem.getReturnedExpression().replace(this.elem2Replace);
			}
		} 
	}

}
