package com.maximoff;

/*
    Класс для записи XML
*/

import java.io.FileOutputStream;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class XmlWriter {

    private String gameplayFile;

    public XmlWriter (String gameplayFile) {
        this.gameplayFile = gameplayFile;
    }

    public void setFile(String gameplayFile) {
        this.gameplayFile = gameplayFile;
    }

    public void saveGameplay(Gameplay gameplay) throws Exception {
        // создаем XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // создаем XMLEventWriter
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(gameplayFile));
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
        addPlayer(eventWriter, Integer.toString(gameplay.getPlayer1().getPlayerID()),
                                                gameplay.getPlayer1().getPlayerName(),
                                                gameplay.getPlayer1().getPlayerMark());
        addPlayer(eventWriter, Integer.toString(gameplay.getPlayer2().getPlayerID()),
                                                gameplay.getPlayer2().getPlayerName(),
                                                gameplay.getPlayer2().getPlayerMark());

        // Открываеи тэг Game
        eventWriter.add(tab);
        StartElement gameStartElement = eventFactory.createStartElement("",
                "", "Game");
        eventWriter.add(gameStartElement);
        eventWriter.add(end);
        // Добавляем тэги Step
        gameplay.getSteps().forEach(step -> {
            try {
                addStep(eventWriter, Integer.toString(step.getNum()),
                                    Integer.toString(step.getPlayerId()),
                                    Integer.toString(step.getStepValue()));
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        });
        // Закрываеи тэг Game
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", "Game"));

        if(gameplay.getWinner().getPlayerName().equals("Draw!")){

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
            addGameResult(eventWriter, gameplay.getWinner());
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
