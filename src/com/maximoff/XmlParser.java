package com.maximoff;

/*
    Класс для парсинга gameplay.xml
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XmlParser {

    static final String STEP = "Step";
    static final String NUM = "num";
    static final String PLAYERID = "playerId";
    static final String GAMERESULT = "GameResult";
    static final String PLAYER = "Player";
    static final String ID = "id";
    static final String NAME = "name";
    static final String SYMBOL = "symbol";

    @SuppressWarnings({"unchecked", "null"})

    public List readSteps(String gameplayFile) {
        List steps = new ArrayList();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(gameplayFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document

            Step step = null;
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // Ищем необходимый элемент
                    if (startElement.getName().getLocalPart().equals(STEP)) {
                        step = new Step();
                        // Читаем атрибуты из этого тэга и записываем в объект
                        Iterator attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = (Attribute) attributes.next();
                            if (attribute.getName().toString().equals(NUM)) {
                                step.setNum(Integer.parseInt(attribute.getValue()));
                            }
                            if (attribute.getName().toString().equals(PLAYERID)) {
                                step.setPlayerId(Integer.parseInt(attribute.getValue()));
                            }
                        }
                        event = eventReader.nextEvent();
                        step.setStepValue(Integer.parseInt(event.asCharacters().getData()));
                        continue;
                    }

                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(STEP)) {
                        steps.add(step);
                    }
                }
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            System.out.println("Ошибка! Файл не найден!");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return steps;
    }

    public List readPlayers(String gameplayFile) {
        List<Player> players = new ArrayList<>();
        Player player = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            // инициализируем reader и скармливаем ему xml файл
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(gameplayFile));
            // проходим по всем элементам xml файла
            while (reader.hasNext()) {
                // получаем событие (элемент) и разбираем его по атрибутам
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("Player")) {
                        player = new Player();
                        // Получаем атрибут id для каждого элемента Player
                        Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                        if (idAttr != null) {
                            player.setPlayerID(Integer.parseInt(idAttr.getValue()));
                        }
                        Attribute nameAttr = startElement.getAttributeByName(new QName("name"));
                        if (!nameAttr.equals(null)) {
                            player.setPlayerName(nameAttr.getValue());
                        }
                        Attribute symbolAttr = startElement.getAttributeByName(new QName("symbol"));
                        if (!nameAttr.equals(null)) {
                            player.setPlayerMark(symbolAttr.getValue());
                        }
                    }

                }
                // если цикл дошел до закрывающего элемента Player,
                // то добавляем считанного из файла player в список
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Player")) {
                        players.add(player);
                    }

                }

            }

        } catch (FileNotFoundException | XMLStreamException exc) {
        exc.printStackTrace();
        }
        return players;
    }
}