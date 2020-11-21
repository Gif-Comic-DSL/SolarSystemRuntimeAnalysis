package src.ast;

import com.sun.source.tree.*;
import com.sun.source.util.SimpleTreeVisitor;

public class ASTVisitor extends SimpleTreeVisitor {

    public ASTVisitor() {
        super();
    }

    @Override
    public Object visitPackage(PackageTree node, Object o) {
        System.out.println("\n");
        System.out.println("FOUND A PACKAGE");

        System.out.println(node.getPackageName());

        return super.visitPackage(node, o);
    }

    @Override
    public Object visitClass(ClassTree node, Object o) {
        System.out.println("\n");
        System.out.println("FOUND A CLASS");

        System.out.println(node.getSimpleName());

        return super.visitClass(node, o);
    }

    @Override
    public Object visitMethod(MethodTree node, Object o) {
        System.out.println("\n");
        System.out.println("FOUND A METHOD");

        System.out.println(node.getModifiers());
        System.out.println(node.getName());
        System.out.println(node.getParameters());

        return super.visitMethod(node, o);
    }

    @Override
    public Object visitVariable(VariableTree node, Object o) {
        System.out.println("\n");
        System.out.println("FOUND A VARIABLE");

        System.out.println(node.getModifiers());
        System.out.println(node.getName());
        System.out.println(node.getInitializer());
        System.out.println(node.getType());

        return super.visitVariable(node, o);
    }
}
