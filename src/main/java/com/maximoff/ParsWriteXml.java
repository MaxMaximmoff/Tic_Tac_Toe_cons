package com.maximoff;

/*
    Класс для парсинга и записи в фаил XML
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import com.maximoff.gameplay.*;

import java.io.FileOutputStream;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;

public class ParsWriteXml implements ParsWriteFile{

    static final String STEP = "Step";
    static final String NUM = "num";
    static final String PLAYERID = "playerId";
    static final String PLAYER = "Player";
    static final String ID = "id";
    static final String NAME = "name";
    static final String SYMBOL = "symbol";


    public ParsWriteXml() {}


    @Override
    public Gameplay parsFile(String path) throws Exception {

        Gameplay gameplay = null;


        try {

            // Создаем XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(new FileInputStream(path));
            //
            Player player = null;
            Step step = null;
            List<Player> players = new ArrayList<>();
            List<Step> steps = new ArrayList<>();

            // Парсим XML
            while (eventReader.hasNext()) {
                // получаем событие (элемент) и разбираем его по атрибутам
                XMLEvent xmlEvent = eventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    // Ищем элемент Player
                    if (startElement.getName().getLocalPart().equals(PLAYER)) {
                        player = new Player();
                        // Получаем атрибут id для каждого элемента Player
                        Attribute idAttr = startElement.getAttributeByName(new QName(ID));
                        if (idAttr != null) {
                            player.setPlayerID(Integer.parseInt(idAttr.getValue()));
                        }
                        Attribute nameAttr = startElement.getAttributeByName(new QName(NAME));
                        if (!nameAttr.equals(null)) {
                            player.setPlayerName(nameAttr.getValue());
                        }
                        Attribute symbolAttr = startElement.getAttributeByName(new QName(SYMBOL));
                        if (!nameAttr.equals(null)) {
                            player.setPlayerMark(symbolAttr.getValue());
                        }
                    }
                    // Ищем элемент Step
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
                        xmlEvent = eventReader.nextEvent();
                        step.setStepValue(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        continue;
                    }
                }
                // если цикл дошел до закрывающего элемента, то добавляем данные в список
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals(PLAYER)) {
                        players.add(player);
                    }
                    if (endElement.getName().getLocalPart().equals(STEP)) {
                        steps.add(step);
                    }

                }
            }

            List<Player> players_ = new ArrayList<>();
            players_.add(players.get(0));
            players_.add(players.get(1));
            Player winner;
            if(players.size()==2) {
                winner = null;
            } else {
                winner = players.get(2);
            }
            Game game = new Game(steps);
            GameResult gameResult = new GameResult(winner);

            gameplay = new Gameplay(players_, game, gameResult);

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка! Файл не найден!");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return gameplay;
    }

    @Override
    public void writeFile(String path, Gameplay gameplay) throws IOException, XMLStreamException {

        // создаем XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // создаем XMLEventWriter
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(path));
        // создаем EventFactory
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create and write Start Tag
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);
        eventWriter.add(end);
        // создаем открывающий тэг Gameplay
        StartElement gameplayStartElement = eventFactory.createStartElement("",
                "", "Gameplay");
        eventWriter.add(gameplayStartElement);
        eventWriter.add(end);

        // Добавляем записи о игроках
        addPlayer(eventWriter, Integer.toString(gameplay.getPlayers().get(0).getPlayerID()),
                gameplay.getPlayers().get(0).getPlayerName(),
                gameplay.getPlayers().get(0).getPlayerMark());
        addPlayer(eventWriter, Integer.toString(gameplay.getPlayers().get(1).getPlayerID()),
                gameplay.getPlayers().get(1).getPlayerName(),
                gameplay.getPlayers().get(1).getPlayerMark());

        // Открываеи тэг Game
        eventWriter.add(tab);
        StartElement gameStartElement = eventFactory.createStartElement("",
                "", "Game");
        eventWriter.add(gameStartElement);
        eventWriter.add(end);
        // Добавляем тэги Step
        for (Step step : gameplay.getGame().getSteps()) {
            addStep(eventWriter, Integer.toString(step.getNum()),
                    Integer.toString(step.getPlayerId()),
                    Integer.toString(step.getStepValue()));
        }
        // Закрываеи тэг Game
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", "Game"));

        if(gameplay.getGameResult().getPlayer() == null){

            // Создаем открывающий тэг GameResult
            eventWriter.add(end);
            eventWriter.add(tab);
            StartElement sElement = eventFactory.createStartElement("", "", "GameResult");
            eventWriter.add(sElement);
            // create Content
            Characters characters = eventFactory.createCharacters("Draw!");
            eventWriter.add(characters);
            // Закрываем тэг GameResult
            EndElement eElement = eventFactory.createEndElement("", "", "GameResult");
            eventWriter.add(eElement);
            eventWriter.add(end);

        } else  {
            addGameResult(eventWriter, gameplay.getGameResult().getPlayer());
        }

        // Закрываеи тэг Gameplay
        eventWriter.add(eventFactory.createEndElement("", "", "Gameplay"));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }


    private void addPlayer(XMLEventWriter eventWriter, String id, String name, String symbol) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // Создаем открывающий тэг Player
        StartElement sElement = eventFactory.createStartElement("", "", "Player");
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // Добавляем атрибуты Player
        eventWriter.add(eventFactory.createAttribute("","", "id", id));
        eventWriter.add(eventFactory.createAttribute("","", "name", name));
        eventWriter.add(eventFactory.createAttribute("","", "symbol", symbol));
        // Закрываеи тэг Player
        EndElement eElement = eventFactory.createEndElement("", "", "Player");
        eventWriter.add(eElement);
        eventWriter.add(end);
    }


    private void addStep(XMLEventWriter eventWriter, String num, String playerId, String stepValue) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // Создаем открывающий тэг Step
        StartElement sElement = eventFactory.createStartElement("", "", "Step");
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // Добавляем атрибуты Step
        eventWriter.add(eventFactory.createAttribute("","", "num", num));
        eventWriter.add(eventFactory.createAttribute("","", "playerId", playerId));
        // Добавляем номер хода
        Characters characters = eventFactory.createCharacters(stepValue);
        eventWriter.add(characters);
        // Закрываем тэг Step
        EndElement eElement = eventFactory.createEndElement("", "", "Step");
        eventWriter.add(eElement);
        eventWriter.add(end);
    }


    private void addGameResult(XMLEventWriter eventWriter, Player winner) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // Создаем открывающий тэг GameResult
        eventWriter.add(end);
        eventWriter.add(tab);
        StartElement sElement = eventFactory.createStartElement("", "", "GameResult");
        eventWriter.add(sElement);
        // Создаем открывающий тэг Player
        sElement = eventFactory.createStartElement("", "", "Player");
        eventWriter.add(sElement);
        // Добавляем атрибуты Player
        eventWriter.add(eventFactory.createAttribute("","", "id",
                Integer.toString(winner.getPlayerID())));
        eventWriter.add(eventFactory.createAttribute("","", "name", winner.getPlayerName()));
        eventWriter.add(eventFactory.createAttribute("","", "symbol", winner.getPlayerMark()));
        // Закрываеи тэг Player
        EndElement eElement = eventFactory.createEndElement("", "", "Player");
        // Закрываем тэг GameResult
        eElement = eventFactory.createEndElement("", "", "GameResult");
        eventWriter.add(eElement);
    }
}
