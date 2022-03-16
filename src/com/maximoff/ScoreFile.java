package com.maximoff;

/*
    Класс для записи рейтинга игроков в фаил в игре крестики-нолики
*/

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

    // Выврдит на экран рейтинг игроков по убыванию и в таком же порядке записывает рейтинг в файл
    public void showScore()  throws IOException {
        BufferedWriter bw = null;
        BufferedReader reader = null;
        HashMap<String, Integer> scores;

        try {
            scores = new HashMap<String, Integer>();
            reader = new BufferedReader(new FileReader(scoreFile));
            String line;
            String cache = "";

            // читаем файл и записываем его данные в HashMap
            while ((line = reader.readLine()) != null) {
                String[] tline = line.split(" ");
                scores.put(tline[0], Integer.parseInt(tline[1]));
            }

//            Сортировка в прямом порядке
//            Map<String, Integer> sortedMap = scores.entrySet()
//                                                        .stream()
//                                                        .sorted(Map.Entry.comparingByValue())
//                                                        .collect(Collectors
//                                                                .toMap(Map.Entry::getKey,
//                                                                        Map.Entry::getValue,
//                                                                        (e1, e2) -> e1,
//                                                                        LinkedHashMap::new));

//          Сортировка в обратном порядке
            Map<String, Integer> sortedMapDesc = scores.entrySet()
                                                        .stream()
                                                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                                                        .collect(Collectors
                                                                .toMap(Map.Entry::getKey,
                                                                        Map.Entry::getValue,
                                                                        (e1, e2) -> e1,
                                                                        LinkedHashMap::new));
            //  Выводим на экран отсортированный рейтинг и записываем его обратно в файл
            System.out.println("Рейтинг игроков:");
            for(Map.Entry entry : sortedMapDesc.entrySet()) {
                    cache += entry.getKey() + " " + entry.getValue() + "\n";
                    System.out.println(entry.getKey() + " " + entry.getValue());
            }
            bw = new BufferedWriter(new FileWriter(scoreFile));
            bw.write(cache);

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
