package apps;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtInterface;

import java.util.ArrayList;
import java.util.List;

import spoon.reflect.reference.*;
import spoon.reflect.visitor.filter.NamedElementFilter;

import java.util.Iterator;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.factory.Factory;
import java.lang.Thread;
import spoon.support.reflect.declaration.CtTypeImpl;
import java.util.HashMap;

import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.visitor.ImportScannerImpl;
/**
 * Reports warnings when empty methods are found.
 */
public class JEEShouldNotGetClassLoaderProcessor extends AbstractProcessor<CtInvocation<?>> {
	private HashMap<Integer,Boolean> hashCodesOfTypesUsingJEE = new HashMap<Integer,Boolean>(); 

	private Factory factory;
	public JEEShouldNotGetClassLoaderProcessor(Factory factory) {
		this.factory = factory;
	}

	@Override
	public boolean isToBeProcessed(CtInvocation<?> invocation) {
		String invocationStr = invocation.toString();
		if (invocationStr.indexOf("getClass().getClassLoader()") != -1 || invocationStr.indexOf(".class.getClassLoader()") != -1) {
			CtType t = (CtType)invocation.getParent(CtType.class);
			CtType topParent = t.getReference().getTopLevelType().getDeclaration();
			Integer hashCode = new Integer(topParent.hashCode());

			if (this.hashCodesOfTypesUsingJEE.get(hashCode) == null) {
				ImportScannerImpl scanner = new ImportScannerImpl();
				scanner.scan(topParent);
				this.hashCodesOfTypesUsingJEE.put(hashCode,new Boolean(false));
		        for (CtImport imp : scanner.getAllImports()) {
		        	if (imp.toString().contains("javax")) {
		        		this.hashCodesOfTypesUsingJEE.put(hashCode,new Boolean(true));
		        	}
		        }
			}

			Boolean useJEE = this.hashCodesOfTypesUsingJEE.get(hashCode);
			return useJEE.booleanValue();

		}
		return false;
	}

	@Override	
	public void process(CtInvocation<?> element) {
		CtClass<?> c = this.factory.Class().get(Thread.class);
		CtTypeAccess<?> access = this.factory.createTypeAccess(c.getReference()); 
		CtMethod<?> method1 = c.getMethodsByName("currentThread").get(0);
		CtMethod<?> method2 = c.getMethodsByName("getContextClassLoader").get(0);

		CtInvocation invo1 = this.factory.createInvocation(access,method1.getReference());
		CtInvocation invo2 = this.factory.createInvocation(invo1,method2.getReference());

		element.replace(invo2);	
	}
}
