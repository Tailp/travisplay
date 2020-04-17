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
import spoon.template.TemplateMatcher;
import spoon.legacy.NameFilter;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import spoon.pattern.Match;
import spoon.pattern.Pattern;
import spoon.pattern.PatternBuilder;
import spoon.reflect.reference.CtExecutableReference;

import java.lang.Object;
import java.lang.Class;
import java.util.Arrays;

import spoon.support.JavaOutputProcessor;


import sonarquberepair.Main;
import spoon.reflect.visitor.filter.TypeFilter;


import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawTextComparator;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;

import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.api.Git;
import java.util.Arrays;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.dircache.DirCacheIterator;

import org.eclipse.jgit.treewalk.AbstractTreeIterator;

import java.nio.file.Paths;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.util.Arrays;

import spoon.reflect.declaration.CtImport;
import spoon.reflect.visitor.ImportScannerImpl;

public class App {
    private static final String DIFF = "diff --git ";
    private static final String REGULAR_FILE = "100644";

    public static File[] finder( String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() { 
                 public boolean accept(File dir, String filename)
                      { return filename.endsWith(".java"); }
        } );

    }

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

    private static void getDiff(String file1, String file2) {

        try {
            FileOutputStream out = new FileOutputStream("change.patch");
            RawText rt1 = new RawText(new File(file1));
            RawText rt2 = new RawText(new File(file2));
            EditList diffList = new EditList();
            diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2));
            DiffFormatter diffForm = new DiffFormatter(out);
            diffForm.format(diffList, rt1, rt2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*private static String makeDiffHeader(String pathA, String pathB,
            ObjectId aId,
            ObjectId bId) {
        String a = aId.abbreviate(8).name();
        String b = bId.abbreviate(8).name();
        return DIFF + "a/" + pathA + " " + "b/" + pathB + "\n" + //
                "index " + a + ".." + b + " " + REGULAR_FILE + "\n" + //
                "--- a/" + pathA + "\n" + //
                "+++ b/" + pathB + "\n";
    }*/
}
