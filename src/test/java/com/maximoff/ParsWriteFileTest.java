package com.maximoff;

import com.maximoff.gameplay.*;
import com.maximoff.*;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsWriteFileTest {

    final String pathJson = "src/test/resources/gameplay.json";
    final String pathXML = "src/test/resources/gameplay.xml";

    @Test
    public void writeParsJsonTest(){


        List<Player> players = new ArrayList<>();
        Player player1 = new Player(1, "Вася", "X");
        Player player2 = new Player(2, "Петя", "O");
        players.add(player1);
        players.add(player2);

        List<Step> steps = new ArrayList<>();
        steps.add(new Step(1, 1, 4));
        steps.add(new Step(2, 2, 1));
        steps.add(new Step(3, 1, 5));
        steps.add(new Step(4, 2, 3));
        steps.add(new Step(5, 1, 6));

        GameResult gameResult = new GameResult(player1);

        Gameplay gameplayIn = new Gameplay(players, new Game(steps), new GameResult(player1));
        Gameplay gameplayOut = null;

        try {

            ParsWriteFile parsWriteFile = new ParsWriteJson();
            parsWriteFile.writeFile(pathJson, gameplayIn);
            gameplayOut = parsWriteFile.parsFile(pathJson);

        } catch (
        FileNotFoundException e) {
            //            e.printStackTrace();
            System.out.println("Ошибка! Файл не найден!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Проверяем идентичность записанных и прочитанных данных
        assertEquals(gameplayIn.getPlayers().get(0).getPlayerID(), gameplayOut.getPlayers().get(0).getPlayerID());
        assertEquals(gameplayIn.getPlayers().get(0).getPlayerName(), gameplayOut.getPlayers().get(0).getPlayerName());
        assertEquals(gameplayIn.getPlayers().get(0).getPlayerMark(), gameplayOut.getPlayers().get(0).getPlayerMark());
        assertEquals(gameplayIn.getPlayers().get(1).getPlayerID(), gameplayOut.getPlayers().get(1).getPlayerID());
        assertEquals(gameplayIn.getPlayers().get(1).getPlayerName(), gameplayOut.getPlayers().get(1).getPlayerName());
        assertEquals(gameplayIn.getPlayers().get(1).getPlayerMark(), gameplayOut.getPlayers().get(1).getPlayerMark());


        for ( int i=0; i<steps.size(); i++){
            assertEquals(steps.get(i).getNum(), gameplayOut.getGame().getSteps().get(i).getNum());
            assertEquals(steps.get(i).getPlayerId(), gameplayOut.getGame().getSteps().get(i).getPlayerId());
            assertEquals(steps.get(i).getStepValue(), gameplayOut.getGame().getSteps().get(i).getStepValue());
        }

        assertEquals(gameplayIn.getGameResult().getPlayer().getPlayerID(),
                gameplayOut.getGameResult().getPlayer().getPlayerID());
        assertEquals(gameplayIn.getGameResult().getPlayer().getPlayerName(),
                gameplayOut.getGameResult().getPlayer().getPlayerName());
        assertEquals(gameplayIn.getGameResult().getPlayer().getPlayerMark(),
                gameplayOut.getGameResult().getPlayer().getPlayerMark());
    }

    @Test
    public void writeParsXmlTest(){


        List<Player> players = new ArrayList<>();
        Player player1 = new Player(1, "Вася", "X");
        Player player2 = new Player(2, "Петя", "O");
        players.add(player1);
        players.add(player2);

        List<Step> steps = new ArrayList<>();
        steps.add(new Step(1, 1, 4));
        steps.add(new Step(2, 2, 1));
        steps.add(new Step(3, 1, 5));
        steps.add(new Step(4, 2, 3));
        steps.add(new Step(5, 1, 6));

        GameResult gameResult = new GameResult(player1);

        Gameplay gameplayIn = new Gameplay(players, new Game(steps), new GameResult(player1));
        Gameplay gameplayOut = null;

        try {

            ParsWriteFile parsWriteFile = new ParsWriteXml();
            parsWriteFile.writeFile(pathXML, gameplayIn);
            gameplayOut = parsWriteFile.parsFile(pathXML);

        } catch (
                FileNotFoundException e) {
            //            e.printStackTrace();
            System.out.println("Ошибка! Файл не найден!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Проверяем идентичность записанных и прочитанных данных
        assertEquals(gameplayIn.getPlayers().get(0).getPlayerID(), gameplayOut.getPlayers().get(0).getPlayerID());
        assertEquals(gameplayIn.getPlayers().get(0).getPlayerName(), gameplayOut.getPlayers().get(0).getPlayerName());
        assertEquals(gameplayIn.getPlayers().get(0).getPlayerMark(), gameplayOut.getPlayers().get(0).getPlayerMark());
        assertEquals(gameplayIn.getPlayers().get(1).getPlayerID(), gameplayOut.getPlayers().get(1).getPlayerID());
        assertEquals(gameplayIn.getPlayers().get(1).getPlayerName(), gameplayOut.getPlayers().get(1).getPlayerName());
        assertEquals(gameplayIn.getPlayers().get(1).getPlayerMark(), gameplayOut.getPlayers().get(1).getPlayerMark());


        for ( int i=0; i<steps.size(); i++){
            assertEquals(steps.get(i).getNum(), gameplayOut.getGame().getSteps().get(i).getNum());
            assertEquals(steps.get(i).getPlayerId(), gameplayOut.getGame().getSteps().get(i).getPlayerId());
            assertEquals(steps.get(i).getStepValue(), gameplayOut.getGame().getSteps().get(i).getStepValue());
        }

        assertEquals(gameplayIn.getGameResult().getPlayer().getPlayerID(),
                gameplayOut.getGameResult().getPlayer().getPlayerID());
        assertEquals(gameplayIn.getGameResult().getPlayer().getPlayerName(),
                gameplayOut.getGameResult().getPlayer().getPlayerName());
        assertEquals(gameplayIn.getGameResult().getPlayer().getPlayerMark(),
                gameplayOut.getGameResult().getPlayer().getPlayerMark());
    }
}
