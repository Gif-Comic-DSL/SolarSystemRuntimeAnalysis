package ast;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.SimpleTreeVisitor;

import javax.lang.model.element.Name;

public class ASTVisitor extends SimpleTreeVisitor {

    public ASTVisitor() {
        super();
    }

    public Name visitClass(ClassTree node, String x) {
        this.visitClass(node, x);
        System.out.println("Node is: " + node);
        return node.getSimpleName();
    }
}
