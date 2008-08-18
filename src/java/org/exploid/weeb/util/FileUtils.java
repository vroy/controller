package org.exploid.weeb.util;

import java.io.File;
import java.util.List;

/**
 * @author vince
 */
public class FileUtils {

    public static List<String> getClassNames(List<String> names, File file, String pack) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                names = getClassNames(names, f, pack + f.getName() + ".");
            } else {
                String name = f.getName().replaceAll(".class", "");
                names.add(pack + name);
            }
        }

        return names;
    }

    public static void generateSkeleton(String ... dirs) {
        for (String dir : dirs) {
            new File(dir).mkdirs();
        }
    }
    
    public static void cleanup(String ... dirs) {
        for (String dir : dirs) {
            deleteFilesRecursively(new File(dir));
        }
    }

    public static void deleteFilesRecursively(File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                deleteFilesRecursively(f);
            } else {
                f.delete();
            }
        }
    }
    
}