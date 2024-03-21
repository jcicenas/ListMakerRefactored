//Allows us to use the FileChooser wizard GUI to pick files
import javax.swing.*;
//Needed imports for working w/ IO (input/output)
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;
import java.util.*;

public class  FileAccess {
    
    public static String openFile(HashSet<String> set, boolean loaded, JFileChooser chooser){
        
        try {
            
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);
            
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                
                File selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while (reader.ready()) {
                    set.add(reader.readLine());
                }
                loaded = true;
                in.close();
                return selectedFile.getName().toString();
            }
        } catch (Exception e) {
            System.out.println("do sum");
        }
        return null;
    }

    public static void write(HashSet<String> set, JFileChooser chooser, String name){
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + name);
        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            for (String s : set) {
                writer.write(s, 0, s.length());
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}