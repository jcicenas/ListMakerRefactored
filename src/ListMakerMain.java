import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Iterator;
import javax.swing.JFileChooser;
// list class? for handling each individual list
public class ListMakerMain {
    private static JFileChooser chooser = new JFileChooser();
    private static String currentFileName = "";
    private static boolean currentLoaded = false;

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        HashSet<String> set = new HashSet<>();
        HashMap<String, Runnable> gamemap = new HashMap<>();
        gamemap.put("A", ()-> {
            add(set);
        });
        gamemap.put("D", ()-> {
            delete(set);
        });
        gamemap.put("V", ()-> {
            print(set);
        });
        gamemap.put("Q", ()-> {
            quit();
        });
        gamemap.put("O", ()-> {
            open(set);
        });
        gamemap.put("S", ()-> {
            save(set);
        });
        gamemap.put("C", ()-> {
            clear(set);
        });
        
        Boolean sentinel = true;
        do{
        System.out.println("A - Add Item \nD - Delete Item\n P - Print List\n Q - Quit Program \n O - Open list file \n S - Save current list file \n C - Clear all elements from current list \n V - Change Print to View");
        String temp = scan.nextLine();
        temp = temp.toUpperCase();
        if (gamemap.containsKey(temp)) {
            gamemap.get(temp).run();
        }else{
            System.out.println("Incorrect Value");
        }
        }while (sentinel);


        scan.close();
    }

    public static void add(HashSet<String> set){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a value to add to the list: ");
        String temp = scan.nextLine();
        set.add(temp);
        currentLoaded = true;
        scan.close();
    }

    public static void delete(HashSet<String> set){
        Scanner scan = new Scanner(System.in);
        boolean sentinel = true;
        do {
            System.out.println("Enter the value to remove from list: ");
        String temp = scan.nextLine();
        if(set.contains(temp)){
            set.remove(temp);
            sentinel = false;
        }else{
            System.out.println("Not available try again");
        }
        } while (sentinel);
        scan.close();
    }

    public static void print(HashSet<String> set){
        Iterator<String> iterator = set.iterator();
            
        while(iterator.hasNext()) {
            String temp = iterator.next();
            System.out.println(temp);
        }
    }
    public static void quit(){
        System.exit(0);
    }
    //new methods

    public static void open(HashSet<String> set){
        Scanner scan = new Scanner(System.in);
        //open a list file from disk
            if (currentLoaded == false) {
                currentFileName = FileAccess.openFile(set, currentLoaded, chooser);
            }else{
                if (InputHelper.getYNConfirm(scan, "Do you want to exit current list?")) {
                    //finish
                    save(set);
                    clear(set);
                    currentFileName = FileAccess.openFile(set, currentLoaded, chooser);
                }
            }
            

        
    }

    public static void save(HashSet<String> set){
        Scanner scan = new Scanner(System.in);
        //save the current list file to disk
        if (currentFileName == "") {
            currentFileName = InputHelper.getNonZeroLenString(scan, "Choose a name for the File to be saved to");
        }
        FileAccess.write(set, chooser, currentFileName);
        
    }
    public static void clear(HashSet<String> set){
        //remove all elements from the current list
        set.clear();
        currentFileName = "";
        currentLoaded = false;
    }
    
}