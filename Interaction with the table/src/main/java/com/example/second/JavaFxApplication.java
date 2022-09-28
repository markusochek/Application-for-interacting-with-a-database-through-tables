package com.example.second;

import com.example.second.contracts.Contract;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JavaFxApplication extends Application {

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder()
                .sources(SecondApplication.class)
                .run(args);
    }

    public String request() throws IOException {
        final URL url = new URL("http://localhost:8080/contracts");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public void start(Stage stage) throws IOException {

        //GET запрос для получения JSON ответа
        String jsonString = request();

        //разворачивание JSON ответа в List контрактов
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        List<Contract> contractList = mapper.readValue(jsonString, new TypeReference<>(){});

        //создание ContractTable объектов, для добавления их в таблицу
        List<ContractTable> contractTableList = new ArrayList<>();
        for (Contract contract : contractList) {
            contractTableList.add(new ContractTable(contract));
        }

        //работа с JavaFx TableView
        ObservableList<ContractTable> contracts = FXCollections.observableArrayList(contractTableList);

        TableView<ContractTable> table = new TableView<>(contracts);
        table.setPrefWidth(1000);
        table.setPrefHeight(500);

        TableColumn<ContractTable, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getColumns().add(dateColumn);

        TableColumn<ContractTable, Integer> numberColumn = new TableColumn<>("Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        table.getColumns().add(numberColumn);

        TableColumn<ContractTable, LocalDate> dateOfLastUpdateColumn = new TableColumn<>("DateOfLastUpdate");
        dateOfLastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfLastUpdate"));
        table.getColumns().add(dateOfLastUpdateColumn);

        TableColumn<ContractTable, Integer> checkBoxColumn = new TableColumn<>("CheckBox");
        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        table.getColumns().add(checkBoxColumn);

        FlowPane root = new FlowPane(10, 10, table);
        Scene scene = new Scene(root, 1000, 500);

        stage.setScene(scene);
        stage.setTitle("Contracts");
        stage.show();
    }
}
