import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.*;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.lang.model.element.Modifier;

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

    /*

        String pathname = "./pluginLogs.json";
        File log = new File(pathname);
        try {
            if (!log.exists()) {
                log.createNewFile();
                Files.write(Paths.get(pathname), "[".getBytes());
            } else {
                Files.write(Paths.get(pathname), ",\n".getBytes(), StandardOpenOption.APPEND);
            }

            **** DOING THIS PART PLUS PATHNAME FIRST ***
            String id = Integer.toString(identityHashCode(this));
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            String jsonID = "{\"id\" : \"" + id + "\",\n";
            String jsonClass = "\"class\" : \"" + className + "\",\n";
            String jsonMethod = "\"method\" : \"" + methodName + "\"}";
            Files.write(Paths.get(pathname), jsonID.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(pathname), jsonClass.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(pathname), jsonMethod.getBytes(), StandardOpenOption.APPEND);
            *******************************

            FUCK... Paths requires an import. Even a simple one.

        } catch (IOException ioe) {
            System.out.println("FAILED TO FIND/CREATE THE LOG FILE !!!");
            System.out.println(ioe);
        } catch (SecurityException se) {
            System.out.println("FAILED TO WRITE TO LOG FILE - WRITE ACCESS DENIED BY SECURITY MANAGER !!!");
            System.out.println(se);
        } catch (UnsupportedOperationException uoe) {
            System.out.println("FAILED TO WRITE TO LOG FILE - UNSUPPORTED OPERATION ATTEMPTED");
            System.out.println(uoe);
        }

     */

    private void addLog(MethodTree method, Context context) {
        TreeMaker factory = TreeMaker.instance(context);
        Names names = Names.instance(context);
        factory.at(((JCTree) method).pos); // set factory at method's position in the tree
        com.sun.tools.javac.util.List newStatements = com.sun.tools.javac.util.List.nil();

        if(method.getModifiers().getFlags().contains(Modifier.STATIC)){
            System.out.println("!!!"+method.getModifiers().getFlags());
            return;
        }

/*
        JCTree.JCLiteral pathname = factory.Literal("/tmp/logs_for_solar_ui.txt"); // will need starting [ added, last "," removed, and last ] added
        JCTree.JCFieldAccess pget = getPathsGetMethod(factory, names);
        JCTree.JCMethodInvocation m_path = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                pget,
                com.sun.tools.javac.util.List.of(pathname)
        );
        JCTree.JCVariableDecl v_path = factory.VarDef(factory.Modifiers(Flags.PRIVATE), names.fromString("log_path"), factory.Ident(names.fromString("Path")),m_path);
        newStatements = newStatements.append(v_path);
        JCTree.JCIdent i_path = factory.Ident(v_path.getName());
        JCTree.JCLiteral sop = factory.Literal(2); //StandardOpenOption.APPEND Enum constant ordinal: 2
*/
        //String id = Integer.toString(identityHashCode(this)); // can't do identityHashCode
        // instead ... Integer.toString(this.hashCode())
        JCTree.JCFieldAccess this_hash = getMethod(factory, names, "this", "hashCode");
        JCTree.JCMethodInvocation hash = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                this_hash,
                com.sun.tools.javac.util.List.nil()
        );

        JCTree.JCFieldAccess i_to_s = getMethod(factory, names, "Integer", "toString");

        JCTree.JCMethodInvocation hash_str = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                i_to_s,
                com.sun.tools.javac.util.List.of(hash)
        );

        // this.getClass().getName();
        JCTree.JCFieldAccess this_class = getMethod(factory, names, "this", "getClass");
        JCTree.JCMethodInvocation class_str = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                this_class,
                com.sun.tools.javac.util.List.nil()
        );
        JCTree.JCFieldAccess this_class_getname = factory.Select(class_str, names.fromString("getName"));
        JCTree.JCMethodInvocation class_name_str = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                this_class_getname,
                com.sun.tools.javac.util.List.nil()
        );

        //String methodName = new Throwable().getStackTrace()[0].getMethodName();
        JCTree.JCNewClass init_throwable = factory.NewClass(
                null,
                com.sun.tools.javac.util.List.nil(),
                factory.Ident(names.fromString("Throwable")),
                com.sun.tools.javac.util.List.nil(),
                null
        );

        JCTree.JCFieldAccess meth_stacktrace = factory.Select(init_throwable, names.fromString("getStackTrace"));

        JCTree.JCMethodInvocation obj_stacktrace = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                meth_stacktrace,
                com.sun.tools.javac.util.List.nil()
        );

        JCTree.JCArrayAccess top = factory.Indexed(obj_stacktrace, factory.Literal(0));
        JCTree.JCFieldAccess meth_getMethodName = factory.Select(top, names.fromString("getMethodName"));
        JCTree.JCMethodInvocation obj_methodName = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                meth_getMethodName,
                com.sun.tools.javac.util.List.nil()
        );


        // build up string expression using plus operator
        //"{\"id\": " + id + ", \"class\": \"" + className + "\", \"method\": " + methodName + "},"
        JCTree.JCExpression json1 = factory.Binary(JCTree.Tag.PLUS,factory.Literal("{\"id\": "), hash_str);
        JCTree.JCExpression json2 = factory.Binary(JCTree.Tag.PLUS, json1, factory.Literal(", \"class\": \""));
        JCTree.JCExpression json3 = factory.Binary(JCTree.Tag.PLUS, json2, class_name_str);
        JCTree.JCExpression json4 = factory.Binary(JCTree.Tag.PLUS, json3, factory.Literal("\", \"method\": "));
        JCTree.JCExpression json5 = factory.Binary(JCTree.Tag.PLUS, json4, obj_methodName);
        JCTree.JCExpression json6 = factory.Binary(JCTree.Tag.PLUS, json5, factory.Literal("},"));


        //JCTree.JCExpression json = factory.Assignop(JCTree.Tag.PLUS_ASG, factory.Ident(names.fromString("add")), factory.Literal("test"));


        //JCTree.JCVariableDecl v_jsonID = factory.VarDef(factory.Modifiers(Flags.PRIVATE), names.fromString("jsonID"), );
        //newStatements = newStatements.append(v_jsonID);
        //JCTree.JCLiteral s2 = factory.Literal("bbb");
        //JCTree.JCLiteral s3 = factory.Literal("ccc");

        JCTree.JCMethodInvocation serr1 = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                getSoutMethod(factory, names),
                com.sun.tools.javac.util.List.of(json6)
        );

        /*
        JCTree.JCMethodInvocation serr2 = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                getSoutMethod(factory, names),
                com.sun.tools.javac.util.List.of(s2)
        );

        JCTree.JCMethodInvocation serr3 = factory.Apply(
                com.sun.tools.javac.util.List.nil(),
                getSoutMethod(factory, names),
                com.sun.tools.javac.util.List.of(s3)
        );
*/
        newStatements = newStatements.append(factory.Exec(serr1));
        //newStatements = newStatements.append(factory.Exec(serr2));
        //newStatements = newStatements.append(factory.Exec(serr3));

        JCTree.JCBlock body = (JCTree.JCBlock) method.getBody();

        if(body != null){
            body.stats = body.stats.prependList(newStatements);
        }
    }

    private JCTree.JCFieldAccess getSoutMethod(TreeMaker factory, Names names) {
        // create the names used to access the method
        Name n_system = names.fromString("System");
        Name n_out = names.fromString("err");
        Name n_print = names.fromString("println");

        // create identifier for system
        JCTree.JCIdent i_system = factory.Ident(n_system);
        JCTree.JCFieldAccess i_sout = factory.Select(i_system, n_out);

        // select the class and method call using the identifier and name?
        JCTree.JCFieldAccess log_select = factory.Select(i_sout, n_print);
        return log_select;
    }


    private JCTree.JCFieldAccess getMethod(TreeMaker factory, Names names, String cls, String method) {
        // create the names used to access the method
        Name n_cls = names.fromString(cls);
        Name n_meth = names.fromString(method);

        // create identifier
        JCTree.JCIdent i_cls = factory.Ident(n_cls);

        // select method on class
        JCTree.JCFieldAccess s_meth = factory.Select(i_cls, n_meth);
        return s_meth;
    }

    private JCTree.JCFieldAccess getFilesWriteMethod(TreeMaker factory, Names names) {
        // create the names used to access the method
        Name n_files = names.fromString("Files");
        Name n_write = names.fromString("write");

        // create identifier
        JCTree.JCIdent i_files = factory.Ident(n_files);

        // select method on class
        JCTree.JCFieldAccess s_fw = factory.Select(i_files, n_write);
        return s_fw;
    }



}
