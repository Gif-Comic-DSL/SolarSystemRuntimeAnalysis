package src;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import src.ast.ASTVisitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<File> fileList = getFileList();

        Iterable<? extends CompilationUnitTree> asts = ast.ASTConverter.getAST(fileList);

        for (CompilationUnitTree ast : asts) {
            System.out.println("In AST loop");
            System.out.println(ast);

            Tree astTree = (Tree) ast;
            System.out.println("class below");
            System.out.println(ast.getClass().getSimpleName());
            astTree.accept(new ASTVisitor(), null);

        }
    }

    // TODO: Get all the file names without hardcoding file names
    private static List<File> getFileList() {
        List<File> fileList = new ArrayList<>();
        File file = new File("src/ast/test.java");
        fileList.add(file);

        return fileList;
    }
}
