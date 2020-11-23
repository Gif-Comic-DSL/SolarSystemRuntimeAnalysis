import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.*;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;


public class JavacPlugin implements Plugin{

    public String getName() {
        return "MyPlugin";
    }

    private void addLog(MethodTree method, Context context) {
        TreeMaker factory = TreeMaker.instance(context);
        Names names = Names.instance(context);
        factory.at(((JCTree) method).pos); // set factory at method's position in the tree

        // create the names used to access the method
        Name n_system = names.fromString("System");
        Name n_out = names.fromString("out.println");

        // create identifier for system
        JCTree.JCIdent i_system = factory.Ident(n_system);

        // select the class and method call using the identifier and name?
        JCTree.JCFieldAccess log_select = factory.Select(i_system, n_out);

        // create the literal to be passed as the parameter to the method call
        JCTree.JCLiteral log_str = factory.Literal("please let this print");

        JCTree.JCMethodInvocation log_exp = factory.Apply(
                com.sun.tools.javac.util.List.nil(), // might need to provide some type args?
                log_select,
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
                                return super.visitClass(classNode, aVoid);
                            }

                            @Override
                            public Void visitMethod(MethodTree methodNode, Void aVoid)
                            {
                                addLog(methodNode, context);
                                return super.visitMethod(methodNode, aVoid);
                            }
                        },
                        null);
            }
        });
    }
}
