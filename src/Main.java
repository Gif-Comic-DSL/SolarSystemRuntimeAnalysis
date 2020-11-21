import ast.ASTConverter;

import static java.lang.System.identityHashCode;

public class Main {
    public static void main(String[] args) {

        ASTConverter obj1 = new ASTConverter();
        ASTConverter obj2 = new ASTConverter();

        System.out.println("from main class:");
        System.out.println(identityHashCode(obj1));
        System.out.println(identityHashCode(obj2));

        System.out.println("\nfrom ASTConverter class:\n");
        obj1.printInfo();
        obj2.printInfo();
    }
}
//        List<File> fileList = getFileList("src/input", new ArrayList<>());
//
//        // each file in fileList is a class file, therefore each ast in asts is a class declaration
//        Iterable<? extends CompilationUnitTree> asts = null;
//        try {
//            asts = ASTConverter.getAST(fileList).parse();
//        } catch (Exception e) {
//            System.out.println("EXception thrown");
//        }
//
//
//        if (asts != null) {
//            for (CompilationUnitTree ast : asts) {
//                List<ClassTree> typeDeclList = (List<ClassTree>) ast.getTypeDecls();
//
//                if (typeDeclList != null || typeDeclList.isEmpty()) {
//                    for (ClassTree typeDecl: typeDeclList) {
//                        typeDecl.accept(new ASTVisitor(), null); // class has been accepted
//
//                        List<Tree> membersList = (List<Tree>) typeDecl.getMembers();
//
//                        for (Tree member: membersList) {
//                            member.accept(new ASTVisitor(), null); // accepts methods and variables
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//
//    /*
//    Method to retrieve list of files given directory
//     */
//    private static List<File> getFileList(String directory, List<File> fileList) {
//        File dirFile = new File(directory);
//        File[] fileArray = dirFile.listFiles();
//
//        if (fileList != null) {
//            for (File file : fileArray) {
//                if (file.isFile()) {
//                    fileList.add(file);
//                } else if (file.isDirectory()) {
//                    getFileList(file.getPath(), fileList);
//                }
//            }
//        }
//        return fileList;
//    }
//}
