package com.alex.repository;

import com.alex.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class SaveParse implements SaveParseInterface{

    // дата игры для уникальности имени файла
    public static SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd  HH_mm_ss");
    public static Date date = new Date();
    public static String dateFile = formater.format(date);


    @Override
    public void saveXML() {


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
        Attr attrMark = document.createAttribute("symbol");
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
        Attr attrMarkTwo = document.createAttribute("symbol");
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
            Attr attrMarkWin = document.createAttribute("symbol");
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
            Attr attrMarkTwoWin = document.createAttribute("symbol");
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
        StreamResult streamFile = new StreamResult(new File(
                Model.firstPlayer + " и " + Model.secondPlayer + " " + dateFile + ".xml"));
        try {
            transformer.transform(domSource,streamFile);
            System.out.println("Документ сохранен!");
        } catch (TransformerException e) {
            e.printStackTrace();
            System.out.println("Документ не сохранен!");
        }
    }

    // основной метод парсинга Xml файла
    @Override
    public void parseXML(String url) {

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

    @Override
    public void saveJSON(){

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Root root = new Root();

        GamePlay gamePlay = new GamePlay();
        gamePlay.players.add(Model.onePlay);
        gamePlay.players.add(Model.twoPlay);
        root.setGamePlay(gamePlay);

        Game game = new Game();
        game.setStepList(Model.stepList);
        gamePlay.setGame(game);

        GameResult gameResult = new GameResult();
        if(Model.winner.equals(Model.firstPlayer)){
            gameResult.setWinner(Model.onePlay);
        }
        else if (Model.winner.equals(Model.secondPlayer)){
            gameResult.setWinner(Model.twoPlay);
        }

        gamePlay.setGameResult(gameResult);

        try {
            objectMapper.writeValue(new File(Model.firstPlayer + " и " + Model.secondPlayer + " " + dateFile + ".json"), root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseJSON(String url){
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        JsonNode nodePlayers = null;
        JsonNode nodeStep = null;
        JsonNode nodeGameResult = null;
        try {
            nodePlayers = objectMapper.readTree(new File(url)).get("GamePlay").get("Player");
            nodeStep = objectMapper.readTree(new File(url)).get("GamePlay").get("Game").get("Step");
            nodeGameResult = objectMapper.readTree(new File(url)).get("GamePlay").get("GameResult").get("Player");
        } catch (IOException e) {
            e.printStackTrace();
        }
        GamePlay gamePlay = new GamePlay();
        try {
            gamePlay.setPlayers(Arrays.asList(objectMapper.readValue(nodePlayers.toString(), Player[].class)));
            Model.stepList = Arrays.asList(objectMapper.readValue(nodeStep.toString(), Step[].class));
            Model.winnerPlay = objectMapper.readValue(nodeGameResult.toString(),Player.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Model.onePlay = gamePlay.getPlayers().get(0);
        Model.firstPlayer = Model.onePlay.getName();
        Model.twoPlay = gamePlay.getPlayers().get(1);
        Model.secondPlayer = Model.twoPlay.getName();
    }

    @Override
    public void parseHttpJSON(URL url) {
        BufferedReader bufferedReader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        HttpURLConnection connection;

        try {
           connection = (HttpURLConnection)url.openConnection() ;

            //request
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();

            if(status>299){
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line= bufferedReader.readLine()) !=null){
                    responseContent.append(line);
                }
                bufferedReader.close();
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line= bufferedReader.readLine()) !=null){
                    responseContent.append(line);
                }
                bufferedReader.close();
            }
            //System.out.println(responseContent);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // обработка Jackson полученный String Json из responseContent
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        JsonNode nodePlayers = null;
        JsonNode nodeStep = null;
        JsonNode nodeGameResult = null;
        try {
            nodePlayers = objectMapper.readTree(responseContent.toString()).get("GamePlay").get("Player");
            nodeStep = objectMapper.readTree(responseContent.toString()).get("GamePlay").get("Game").get("Step");
            nodeGameResult = objectMapper.readTree(responseContent.toString()).get("GamePlay").get("GameResult").get("Player");
        } catch (IOException e) {
            e.printStackTrace();
        }
        GamePlay gamePlay = new GamePlay();
        try {
            gamePlay.setPlayers(Arrays.asList(objectMapper.readValue(nodePlayers.toString(), Player[].class)));
            Model.stepList = Arrays.asList(objectMapper.readValue(nodeStep.toString(), Step[].class));
            Model.winnerPlay = objectMapper.readValue(nodeGameResult.toString(),Player.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Model.onePlay = gamePlay.getPlayers().get(0);
        Model.firstPlayer = Model.onePlay.getName();
        Model.twoPlay = gamePlay.getPlayers().get(1);
        Model.secondPlayer = Model.twoPlay.getName();

    }
}
