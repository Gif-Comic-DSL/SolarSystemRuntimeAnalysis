import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.*;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Log;

import javax.crypto.spec.PSource;
import javax.lang.model.element.Name;

public class JavacPlugin implements Plugin{

    public String getName() {
        return "MyPlugin";
    }

    private void addLog(MethodTree method, Context context) {
        TreeMaker factory = TreeMaker.instance(context);
        JCTree.JCLiteral log_str = factory.Literal("please let this print");
        System.out.println("asdf");
        JCTree.JCExpression systemExp = factory.Select(names.from);
        JCTree.JCMethodInvocation log_exp = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                factory.Select(
                        systemExp,
                        names.fromString("out.println")
                ),
                com.sun.tools.javac.util.List.of(log_str)
        );
        JCTree.JCStatement log_statement = factory.Call(log_exp);
        JCTree.JCBlock body = (JCTree.JCBlock) method.getBody();
        body.stats = body.stats.prepend(log_statement);
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
                e.getCompilationUnit().accept(
                        new TreeScanner<Void, Void>() {
                            @Override
                            public Void visitClass(ClassTree classNode, Void aVoid)
                            {
                                return super.visitClass(node, aVoid);
                            }

                            @Override
                            public Void visitMethod(MethodTree methodNode, Void aVoid)
                            {
                                addLog(methodNode, context);
                                return super.visitMethod(node, aVoid);
                            }
                        },
                        null);
            }
        });
    }
}
