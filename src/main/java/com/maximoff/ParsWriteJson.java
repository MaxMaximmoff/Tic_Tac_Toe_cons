package com.maximoff;

/*
    Класс для парсинга и записи в фаил Json
*/

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.maximoff.gameplay.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ParsWriteJson implements ParsWriteFile{

    public ParsWriteJson() {}

    @Override
    public Gameplay parsFile(String path) throws IOException {

        Json jsonGameplay = null;

        try {

            jsonGameplay = new ObjectMapper()
                    .readValue(new File(path), Json.class);
            return jsonGameplay.getGameplay();

        } catch (FileNotFoundException e) {

            System.out.println("Ошибка! Файл не найден!");
        }
        return jsonGameplay.getGameplay();
    }

    @Override
    public void writeFile(String path, Gameplay gameplay) throws IOException {

        Json json = new Json(gameplay);

        // Создаем printer
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        // Задаем отступ
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
        printer.indentObjectsWith(indenter); // Отступ для объектов в JSON
        printer.indentArraysWith(indenter);  // Отступ для массивов в JSON
        // Записываем json
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer(printer).writeValue(new FileOutputStream(path), json);

    }

}
