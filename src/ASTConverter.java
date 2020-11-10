package src;

import com.sun.source.util.Trees;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.List;

public class ASTConverter {

    public static Trees getAST(List<File> fileList) {
        //Get java compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        //Get standard file manager
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(fileList);

        // Create compilation task
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null,
                null, null, compilationUnits);

        final Trees trees = Trees.instance(task);
        // trees.getTree needs element --> how to generate element?

        return trees;
    }
}