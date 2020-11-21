package src;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import src.ast.ASTConverter;
import src.ast.ASTVisitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<File> fileList = getFileList("src/input", new ArrayList<>());

        // each file in fileList is a class file, therefore each ast in asts is a class declaration
        Iterable<? extends CompilationUnitTree> asts = null;
        try {
            asts = ASTConverter.getAST(fileList).parse();
        } catch (Exception e) {
            System.out.println("EXception thrown");
        }


        if (asts != null) {
            for (CompilationUnitTree ast : asts) {
                List<ClassTree> typeDeclList = (List<ClassTree>) ast.getTypeDecls();

                if (typeDeclList != null || typeDeclList.isEmpty()) {
                    for (ClassTree typeDecl: typeDeclList) {
                        typeDecl.accept(new ASTVisitor(), null); // class has been accepted

                        List<Tree> membersList = (List<Tree>) typeDecl.getMembers();

                        for (Tree member: membersList) {
                            member.accept(new ASTVisitor(), null); // accepts methods and variables
                        }
                    }
                }
            }
        }

    }

    /*
    Method to retrieve list of files given directory
     */
    private static List<File> getFileList(String directory, List<File> fileList) {
        File dirFile = new File(directory);
        File[] fileArray = dirFile.listFiles();

        if (fileList != null) {
            for (File file : fileArray) {
                if (file.isFile()) {
                    fileList.add(file);
                } else if (file.isDirectory()) {
                    getFileList(file.getPath(), fileList);
                }
            }
        }

        return fileList;
    }
}
