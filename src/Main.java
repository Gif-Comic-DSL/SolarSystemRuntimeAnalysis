package src;

import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreeScanner;
import com.sun.source.util.Trees;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("This is main");

        String input = "";

        try {
            Path path = Paths.get("src/test.java");
            System.out.println("Path is = " + path);
            input = Files.readString(path);
        } catch (Exception e) {
            System.out.println("EXCEPTION = " + e.getLocalizedMessage());
            System.exit(0);
        }

        Trees tree = ASTConverter.getAST(input);

        TreeScanner scanner = new TreeScanner();
        // how to use scanner to iterate over tree?
        System.out.println(tree);


    }
}
