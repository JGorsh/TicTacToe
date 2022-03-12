package com.alex.repository;

import com.alex.model.Model;
import com.alex.model.Step;
import org.w3c.dom.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SaveParseXML {

    public static String address = "C:\\java project\\Ylablearn\\TicTacToe\\src\\save\\"; //адрес папки для сохранения результата

    public static void saveXML() {
        // дата игры для уникальности имени файла
        SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd  HH_mm_ss");
        Date date = new Date();
        String dateFile = formater.format(date);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        // создаем пустой объект Document, в котором будем создавать xml-файл
        Document document = builder.newDocument();

        // добавление элемента GamePlay
        Element gameReport = document.createElement("GamePlay");
        document.appendChild(gameReport);

        // добавление элемента Player и атрибутов id, name, mark первого игрока
        Element playerOne = document.createElement("Player");
        Attr attrId = document.createAttribute("id");
        attrId.setValue(String.valueOf(Model.onePlay.getId()));
        playerOne.setAttributeNode(attrId);
        Attr attrName = document.createAttribute("name");
        attrName.setValue(String.valueOf(Model.onePlay.getName()));
        playerOne.setAttributeNode(attrName);
        Attr attrMark = document.createAttribute("mark");
        attrMark.setValue(String.valueOf(Model.onePlay.getMark()));
        playerOne.setAttributeNode(attrMark);
        gameReport.appendChild(playerOne);

        // добавление элемента Player и атрибутов id, name, mark второго игрока
        Element playerTwo = document.createElement("Player");
        Attr attrIdTwo = document.createAttribute("id");
        attrIdTwo.setValue(String.valueOf(Model.twoPlay.getId()));
        playerTwo.setAttributeNode(attrIdTwo);
        Attr attrNameTwo = document.createAttribute("name");
        attrNameTwo.setValue(String.valueOf(Model.twoPlay.getName()));
        playerTwo.setAttributeNode(attrNameTwo);
        Attr attrMarkTwo = document.createAttribute("mark");
        attrMarkTwo.setValue(String.valueOf(Model.twoPlay.getMark()));
        playerTwo.setAttributeNode(attrMarkTwo);
        gameReport.appendChild(playerTwo);

        //создаем элемент Game  и добавляем весь список содержащий Step
        Element elementStep = document.createElement("Game");
        for(Step step : Model.stepList){
            Element stepPlayer = document.createElement("Step");
            stepPlayer.appendChild(document.createTextNode(String.valueOf(step.getPlayerPosition())));

            Attr attrMove = document.createAttribute("num");
            attrMove.setValue(String.valueOf(Model.stepList.indexOf(step)+1));
            stepPlayer.setAttributeNode(attrMove);

            Attr attrPlayerId = document.createAttribute("playerId");
            attrPlayerId.setValue(String.valueOf(step.getPlayer().getId()));
            stepPlayer.setAttributeNode(attrPlayerId);

            elementStep.appendChild(stepPlayer);
        }
        gameReport.appendChild(elementStep);

        //создаем элемент GameResult и прописываем победителя
        Element gameResult = document.createElement("GameResult");
        if(Model.winner.equals(Model.firstPlayer)){
            Element playerOneWin = document.createElement("Player");
            Attr attrIdWin = document.createAttribute("id");
            attrIdWin.setValue(String.valueOf(Model.onePlay.getId()));
            playerOneWin.setAttributeNode(attrIdWin);
            Attr attrNameWin = document.createAttribute("name");
            attrNameWin.setValue(String.valueOf(Model.onePlay.getName()));
            playerOneWin.setAttributeNode(attrNameWin);
            Attr attrMarkWin = document.createAttribute("mark");
            attrMarkWin.setValue(String.valueOf(Model.onePlay.getMark()));
            playerOneWin.setAttributeNode(attrMarkWin);
            gameResult.appendChild(playerOneWin);
        }
        else if(Model.winner.equals(Model.secondPlayer)){
            Element playerTwoWin = document.createElement("Player");
            Attr attrIdTwoWin = document.createAttribute("id");
            attrIdTwoWin.setValue(String.valueOf(Model.twoPlay.getId()));
            playerTwoWin.setAttributeNode(attrIdTwoWin);
            Attr attrNameTwoWin = document.createAttribute("name");
            attrNameTwoWin.setValue(String.valueOf(Model.twoPlay.getName()));
            playerTwoWin.setAttributeNode(attrNameTwoWin);
            Attr attrMarkTwoWin = document.createAttribute("mark");
            attrMarkTwoWin.setValue(String.valueOf(Model.twoPlay.getMark()));
            playerTwoWin.setAttributeNode(attrMarkTwoWin);
            gameResult.appendChild(playerTwoWin);
        }
        else {
            gameResult.appendChild(document.createTextNode(Model.winner));
        }

        gameReport.appendChild(gameResult);

        //записываем данные
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource domSource = new DOMSource(document);
        StreamResult streamFile = new StreamResult(new File(address +
                Model.firstPlayer + " и " + Model.secondPlayer + " " + dateFile + ".xml"));
        try {
            transformer.transform(domSource,streamFile);
            System.out.println("Документ сохранен!");
        } catch (TransformerException e) {
            e.printStackTrace();
            System.out.println("Документ не сохранен!");
        }
    }

    public static void parseXML()  {

        //открываем файл для парсинга
        File file = new File("C:\\java project\\Ylablearn\\TicTacToe\\src\\save\\alexey и robot 2022_03_12  13_03_14.xml");
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

        for (int i = 0; i < nodeList.getLength(); i++){

            if (nodeList.item(i).getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            if (!nodeList.item(i).getNodeName().equals("Game")){
                continue;
            }

            NodeList gameList = nodeList.item(i).getChildNodes();
            for (int j = 0; j < gameList.getLength(); j++){

                if (gameList.item(i).getNodeType() != Node.ELEMENT_NODE){
                    continue;
                }
            }
        }
    }
}
