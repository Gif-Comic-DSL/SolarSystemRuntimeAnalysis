package src;

import com.sun.source.tree.Tree;
import com.sun.source.util.TreeScanner;
import com.sun.source.util.Trees;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<File> fileList = getFileList();

        Trees trees = ASTConverter.getAST(fileList);
//        visit trees --> Trees has no visit method, Tree does. How to split Trees into its individual tree components
//        and visit each with ASTVisitor?

//        ASTVisitor visitor = new ASTVisitor();
//        visitor.visit(trees, null); // this does not work
        System.out.println(trees);
    }


    // Get the list of java file objects, in this case we have only one file, TestClass.java
    // to get the AST of an entire program, add each file to this list?
    // is there a way to get all the file names without hardcoding like below?
    private static List<File> getFileList() {
        List<File> fileList = new ArrayList<>();
        File file = new File("src/test.java");
        fileList.add(file);

        return fileList;
    }
}
