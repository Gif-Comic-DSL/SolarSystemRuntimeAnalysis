package ast;

import static java.lang.System.identityHashCode;

public class ASTConverter {

    public void printInfo() {
        System.out.println("ID: " + identityHashCode(this));
        System.out.println("class: ASTConverter");
        System.out.println("method: printInfo\n");
    }
//
//    public static JavacTask getAST(List<File> fileList) {
//        //Get java compiler
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//
//        //Get standard file manager
//        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
//
//        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(fileList);
//
//        // Create compilation task and cast to JavacTask
//        JavacTask task = (JavacTask) compiler.getTask(null, fileManager, null,
//                null, null, compilationUnits);
//
//        try {
//            return task;
//        } catch (Exception e) {
//            System.out.println("Exception: " + e.getMessage());
//        }
//
//        return null;
//    }
}