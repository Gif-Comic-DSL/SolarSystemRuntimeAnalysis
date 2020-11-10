package src;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ASTConverter {

    public static Iterable<? extends CompilationUnitTree> getAST(List<File> fileList) {
        // Option 1: Javac
        //Get java compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        //Get standard file manager
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(fileList);

        // Create compilation task and cast to JavacTask
        JavacTask task = (JavacTask) compiler.getTask(null, fileManager, null,
                null, null, compilationUnits);

        final Trees trees = Trees.instance(task);

        try {
            Iterable<? extends CompilationUnitTree> asts = (Iterable<? extends CompilationUnitTree>) task.parse();
            task.analyze();
            return asts;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return null;
    }
}