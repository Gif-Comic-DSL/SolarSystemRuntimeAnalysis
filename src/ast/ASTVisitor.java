package src.ast;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.SimpleTreeVisitor;

import javax.lang.model.element.Name;

public class ASTVisitor extends SimpleTreeVisitor {

    public ASTVisitor() {
        super();
    }

    @Override
    public Object visitClass(ClassTree node, Object o) {
        System.out.println(node.getSimpleName());
        return super.visitClass(node, o);
    }

    @Override
    public Object visitMethod(MethodTree node, Object o) {
        System.out.println(node.getModifiers());
        System.out.println(node.getName());
        System.out.println(node.getParameters());
        return super.visitMethod(node, o);
    }
}
