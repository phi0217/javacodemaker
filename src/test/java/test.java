import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phi on 2017/4/17.
 */
public class test {
    public static void main(String[] args) {
//        deleteAllFilesOfDir();
        System.out.println();
    }
    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }
}
