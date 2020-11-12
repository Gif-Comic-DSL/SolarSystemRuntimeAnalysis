package src.ast;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.List;

public class ASTConverter {

    public static Iterable<? extends CompilationUnitTree> getAST(List<File> fileList) {
        //Get java compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        //Get standard file manager
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(fileList);

        // Create compilation task and cast to JavacTask
        JavacTask task = (JavacTask) compiler.getTask(null, fileManager, null,
                null, null, compilationUnits);

        try {
            return task.parse();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return null;
    }
}