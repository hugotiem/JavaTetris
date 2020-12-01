package save;

import java.io.*;
import java.util.ArrayList;

public class Storage {

    private static File file;// = new File("save.txt");

    /**
     * Converti un nom en un nom de fichier
     * @param name le nom du joueur
     * @return le nom de sa sauvegarde
     */
    private static String getFileName(String name){
        String[] tmp = name.split(" ");
        StringBuilder fileName = new StringBuilder(tmp[0]);
        for (int i = 1; i < tmp.length; i++){
            fileName.append("_").append(tmp[i]);
        }

        return fileName.toString();
    }

    /**
     * Ecrit des donnees dans un fichier txt
     * @param data les donnees a ecrire
     * @throws IOException si le fichier ne peut pas s'ouvrir
     */
    public static void save(ArrayList<String> data) throws IOException {

        String fileName = getFileName(data.get(0));
        file = new File(fileName+".txt");

        int i = 1;
        while(file.exists()){
            file = new File(fileName + "_" + i + ".txt");
            i++;
        }
        file.createNewFile();
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for (String d: data){
            bw.write(d + "\n");
        }
        bw.close();
        System.out.println("La partie a bien ete sauvegarde");
    }

    /**
     * Permet d'obtenir la liste des sauvegardes d'un joueur
     * @param name le nom ndu joueur
     * @return la liste des sauvegardes
     */
    public static ArrayList<String> getSaves(String name){
        ArrayList<String> saves = new ArrayList<>();
        String fileName = getFileName(name);

        file = new File(fileName + ".txt");

        String tmp = fileName;

        int i = 1;
        while (file.canRead()){
            saves.add(tmp);
            tmp = fileName  + "_" + i;
            file = new File(tmp + ".txt");
            i++;
        }
        return saves;
    }

    /**
     * Permet d'obtenir les donnees d'un fichier txt
     * @param name le nom de la sauvegarde
     * @return une liste des donnees
     * @throws FileNotFoundException si le fichier selectionnee  est introuvable
     * @throws IOException si le fichier ne peut pas s'ouvrir
     */
    public static ArrayList<String> getData(String name) throws FileNotFoundException, IOException {

        ArrayList<String> data = new ArrayList<>();
        //String fileName = getFileName(name);
        file = new File(name+".txt");

        FileReader fr = new FileReader(file.getAbsoluteFile());
        BufferedReader br = new BufferedReader(fr);

        String ligne;

        while((ligne = br.readLine()) != null){
            data.add(ligne);
        }
        br.close();

        return data;
    }
}
