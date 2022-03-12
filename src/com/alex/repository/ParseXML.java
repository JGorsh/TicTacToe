package com.alex.repository;

import com.alex.model.Model;
import com.alex.model.Player;
import com.alex.model.Step;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class ParseXML {

    public static String url = "C:\\java project\\Ylablearn\\TicTacToe\\src\\save\\alexey и robot 2022_03_12  18_47_26.xml";

    // основной метод парсинга Xml файла
    public static void parseXML()  {
        String nameParse;
        int idParse;
        String symbolParse;
        int positionParse;
        Step parseStep;

        //открываем файл для парсинга
        File file = new File(url);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document;
        try {
            document = factory.newDocumentBuilder().parse(file);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        Node rootNode = document.getFirstChild();
        NodeList nodeList = rootNode.getChildNodes();

        //проходим по списку nodelist и выбираем и обрабатываем нужные нам элементы
        for (int i = 0; i < nodeList.getLength(); i++){

            //обработка элемента Player
            if (nodeList.item(i).getNodeName().equals("Player")) {
                nameParse = ((Element) nodeList.item(i)).getAttribute("name");
                idParse =Integer.valueOf(((Element) nodeList.item(i)).getAttribute("id"));
                symbolParse =((Element) nodeList.item(i)).getAttribute("symbol");
                if(idParse==1){
                    Model.onePlay = new Player(idParse, nameParse,symbolParse);
                    Model.firstPlayer = Model.onePlay.getName();
                }
                else {
                    Model.twoPlay = new Player(idParse, nameParse,symbolParse);
                    Model.secondPlayer = Model.twoPlay.getName();
                }
            }

            if (nodeList.item(i).getNodeType() != Node.ELEMENT_NODE){
                continue;
            }

            //обработка элемента Game
            if (nodeList.item(i).getNodeName().equals("Game")){
                NodeList gameList = nodeList.item(i).getChildNodes();
                for (int j = 0; j < gameList.getLength(); j++){

                    if (gameList.item(j).getNodeType() != Node.ELEMENT_NODE){
                        continue;
                    }
                    if (gameList.item(j).getNodeName().equals("Step")){

                        // убрал пробелы в id если они присутствуют, подготовил для адаптера
                        positionParse =Integer.valueOf(gameList.item(j).getTextContent().replaceAll(" ",""));
                        if(((Element) gameList.item(j)).getAttribute("playerId").equals("1")){
                            parseStep = new Step(Model.onePlay, positionParse);
                        }
                        else{
                            parseStep = new Step(Model.twoPlay, positionParse);
                        }
                        Model.stepList.add(parseStep);
                    }
                }
            }

            //обработка элемента Player в GameResult
            NodeList resultNode = document.getElementsByTagName("Player");
            if(resultNode.getLength()>2){
                Node winnerNode = resultNode.item(resultNode.getLength()-1);
                nameParse = ((Element)winnerNode).getAttribute("name");
                idParse =Integer.valueOf(((Element)winnerNode).getAttribute("id"));
                symbolParse =((Element) winnerNode).getAttribute("symbol");
                Model.winnerPlay = new Player(idParse, nameParse, symbolParse);
            }
        }

    }
}
