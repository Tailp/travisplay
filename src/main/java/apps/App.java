package apps;

import spoon.Launcher;
import spoon.pattern.Match;
import spoon.pattern.Pattern;
import spoon.pattern.PatternBuilder;
import spoon.pattern.Quantifier;
import spoon.processing.ProcessingManager;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.factory.Factory;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.Filter;
import spoon.support.QueueProcessingManager;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.CtModel;

import spoon.pattern.Match;
import spoon.pattern.Pattern;
import spoon.pattern.PatternBuilder;
import spoon.reflect.reference.CtExecutableReference;


import spoon.support.JavaOutputProcessor;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.reflect.declaration.CtImport;
import spoon.reflect.visitor.ImportScannerImpl;

public class App {

    public static void main(String[] argz) throws Exception{
        Launcher launcher = new Launcher();
        launcher.addInputResource("src/test/resources/JEEGetClassLoader.java");
        launcher.setSourceOutputDirectory("spooned/");
        launcher.getEnvironment().setAutoImports(true);
        CtModel model = launcher.buildModel();

        final Factory factory = launcher.getFactory();
        final ProcessingManager processingManager = new QueueProcessingManager(factory);
        final JEEShouldNotGetClassLoaderProcessor processor = new JEEShouldNotGetClassLoaderProcessor(factory);
        final JavaOutputProcessor javaOutputProcessor = launcher.createOutputWriter();
        processingManager.addProcessor(processor);
        processingManager.addProcessor(javaOutputProcessor);
        processingManager.process(factory.Class().getAll());

    }
}
