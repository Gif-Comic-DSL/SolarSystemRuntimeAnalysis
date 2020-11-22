import ast.ASTVisitor;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.*;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;

public class JavacPlugin implements Plugin{

    public String getName() {
        return "MyPlugin";
    }

    public void init(JavacTask javacTask, String... strings) {
        Context context = ((BasicJavacTask) javacTask).getContext();
        Log.instance(context)
                .printRawLines(Log.WriterKind.NOTICE, "Hello from " + getName());
        javacTask.addTaskListener(new TaskListener()
        {
            public void started(TaskEvent e)
            {
                System.out.println(e);
            }

            public void finished(TaskEvent e)
            {
                if (e.getKind() != TaskEvent.Kind.PARSE)
                {
                    return;
                }
                e.getCompilationUnit().accept(new TreeScanner<Void, Void>()
                {
                    @Override
                    public Void visitClass(ClassTree node, Void aVoid)
                    {
                        return super.visitClass(node, aVoid);
                    }

                    @Override
                    public Void visitMethod(MethodTree node, Void aVoid)
                    {
                        return super.visitMethod(node, aVoid);
                    }
                }, null);
            }
        });
    }
}
