package src;

import com.sun.source.util.Trees;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ASTConverter {

    private static final JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

    public static Trees getAST(String input) {
        //Get an instance of java compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        //Get a new instance of the standard file manager implementation
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        // Get the list of java file objects, in this case we have only one file, TestClass.java
        List<File> fileList = new ArrayList<>();
        File file = new File("src/test.java");
        fileList.add(file);

        Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjectsFromFiles(fileList);

        // Create the compilation task
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null,
                null, null, compilationUnits1);

        // Perform the compilation task.
        final Trees trees = Trees.instance(task);

        //task.call();

        return trees;
    }
}