package com.maximoff;

import java.io.*;
import java.util.HashMap;

public class ScoreFile {

    private static File scoreFile;
    private String path;
    private String name;

    //  конструктор для класса ScoreFile
    public ScoreFile(String path, String name) throws IOException, NullPointerException {

        this.path = path;
        this.name = name;

        try {
            scoreFile = new File(path);
        }
        catch (NullPointerException e) {
            System.out.println("Имя или путь к файлу равен null!");
        }
        try {
            if(!scoreFile.exists()) {
                scoreFile.createNewFile();
                System.out.println("Новый score.txt создан.");
            }
        }
        catch (IOException e) {
            System.out.println("Файл score.txt не создан!");
        }
    }

    // пишем в счет файл
    public void addScore() {
        BufferedWriter bw = null;
        BufferedReader reader = null;
        HashMap<String, Integer> scores;

        try {
            scores = new HashMap<String, Integer>();
            reader = new BufferedReader(new FileReader(scoreFile));
            String line;
            String cache="";
            // флаг присутствия имени игрока в списке
            boolean presence = false;

            // читаем файл и записываем его данные в HashMap
            while ((line = reader.readLine()) != null) {
                String[] tline = line.split(" ");
                scores.put(tline[0], Integer.parseInt(tline[1]));
            }

            // проверяем наличие игрока в списке и инкрементируем его счет
            for(HashMap.Entry<String, Integer> entry : scores.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                if(key.equals(name)) {
                    presence = true;
                    entry.setValue(value + 1);
                }
            }

            // перезаписываем файл если увеличили счет существующего игрока
            if(presence) {
                for(HashMap.Entry<String, Integer> entry : scores.entrySet()) {
                    cache += entry.getKey() + " " + entry.getValue() + "\n";
                }
                bw = new BufferedWriter(new FileWriter(scoreFile));
                bw.write(cache);
            // или дописываем нового игрока и присваеваем ему первую победу
            } else {
                bw = new BufferedWriter(new FileWriter(scoreFile, true));
                bw.write(name + " " + "1" + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                reader.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
