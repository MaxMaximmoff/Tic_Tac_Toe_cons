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
        List players = new ArrayList();

        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(gameplayFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Player player =  null;
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // Ищем необходимый элемент
                    if (startElement.getName().getLocalPart().equals(PLAYER)) {
                        player =  new Player();
                        // Читаем атрибуты из этого тэга и записываем в объект
                        Iterator attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = (Attribute) attributes.next();
                            if (attribute.getName().toString().equals(ID)) {
                                player.setPlayerID(Integer.parseInt(attribute.getValue()));
                            }
                            if (attribute.getName().toString().equals(NAME)) {
                                player.setPlayerName(attribute.getValue());
                            }
                            if (attribute.getName().toString().equals(SYMBOL)) {
                                player.setPlayerMark(attribute.getValue());
                            }
                        }
                        continue;
                    }


                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(PLAYER)) {
                        players.add(player);
                    }
                }
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            System.out.println("Ошибка! Файл не найден!");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return players;
    }
}





//                if (event.isStartElement()) {
//                    StartElement startElement = event.asStartElement();
//                    // If we have an item element, we create a new item
//                    if (startElement.getName().getLocalPart().equals(ITEM)) {
//                        item = new Item();
//                        // We read the attributes from this tag and add the date
//                        // attribute to our object
//                        Iterator attributes = startElement
//                                .getAttributes();
//                        while (attributes.hasNext()) {
//                            Attribute attribute = (Attribute) attributes.next();
//                            if (attribute.getName().toString().equals(DATE)) {
//                                item.setDate(attribute.getValue());
//                            }
//                        }
//                    }
//                    if (event.isStartElement()) {
//                        if (event.asStartElement().getName().getLocalPart()
//                                .equals(MODE)) {
//                            event = eventReader.nextEvent();
//                            item.setMode(event.asCharacters().getData());
//                            continue;
//                        }
//                    }
//                    if (event.asStartElement().getName().getLocalPart()
//                            .equals(UNIT)) {
//                        event = eventReader.nextEvent();
//                        item.setUnit(event.asCharacters().getData());
//                        continue;
//                    }
//                    if (event.asStartElement().getName().getLocalPart()
//                            .equals(CURRENT)) {
//                        event = eventReader.nextEvent();
//                        item.setCurrent(event.asCharacters().getData());
//                        continue;
//                    }
//                    if (event.asStartElement().getName().getLocalPart()
//                            .equals(INTERACTIVE)) {
//                        event = eventReader.nextEvent();
//                        item.setInteractive(event.asCharacters().getData());
//                        continue;
//                    }
//                }
//                // If we reach the end of an item element, we add it to the list
//                if (event.isEndElement()) {
//                    EndElement endElement = event.asEndElement();
//                    if (endElement.getName().getLocalPart().equals(ITEM)) {
//                        items.add(item);
//                    }
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (XMLStreamException e) {
//            e.printStackTrace();
//        }
//        return items;
//    }
//}
