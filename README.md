**Игра крестики-нолики 3x3**

Игра в ООП стиле. Использованы паттерны MVC, Adapter и Singleton.

Реализована возможность выбора роли X или Y. Реализован ввод имени игроков и ведение счета побед в файле score.txt. По окончании игры на экран выводится отсортированнй рейтинг игроков в порядке убывания. В таком же порядке окончательно рейтинг записывается в файл.


Игроки, последовательность ходов, и победитель записывается в файлы gameplay.xml и gameplay.json по окончании сеанса игры.

Режимы игры:

1 - Игра с человеком. 

2 - Игра с ботом.

3 - Проигрывание последней игры из gameplay.xml или gameplay.json.

4 - Завершение игры.


**Файлы:**

Main.java - Класс для запуска игры.

GameController.java - Контроллер игры.

GameModel.java - Класс логики игры.

GameView.java - Класс для отображения визуальных данных игры.

ParsWriteFile.java - Интерфейс для парсинга и записи в фаил.

ParsWriteJson.java - Класс для парсинга и записи в фаил Json.

ParsWriteXml.java - Класс для парсинга и записи в фаил XML.

ScoreFile.java - Класс для записи рейтинга игроков в текстовый файл и воспроизведения его на экране.

StepValueAdapter.java -  Класс который переводит координаты ходов из 
                                формата [int, int] в int и обратно.

SingletonGV.java - Класс Singleton для класса GameView

AbstractPlayer.java - Абстрактный класс игрока.

Player.java - Класс игрока.

Game.java - Класс для хранения шагов игры.

Gameplay.java - Класс Gameplay для хранения игроков, игры с шагами, результата игры.

GameResult.java - Класс обертка для хранения результата игры.

Json.java - Класс обертка для представления файла Json.

Step.java - Класс описывающий шаг игры.




