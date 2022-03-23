package com.maximoff;

/*
    Интерфейс для парсинга и записи в фаил
*/

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import com.maximoff.gameplay.*;

public interface ParsWriteFile {

    default Gameplay parsFile(String path) throws Exception {
        return null;
    }

    public void writeFile(String path, Gameplay gameplay) throws IOException, XMLStreamException;
}
