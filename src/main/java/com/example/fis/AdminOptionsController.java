package com.example.fis;

import com.jfoenix.controls.JFXButton;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;


public class AdminOptionsController implements Initializable {
    @FXML
    private AnchorPane anchorVisible;

    @FXML
    private JFXButton applyAdd;


    @FXML
    private DatePicker dateAdd;


    @FXML
    private TextField genreAdd;

    @FXML
    private TextField lengthAdd;

    @FXML
    private TextField nameAdd;

    @FXML
    private ComboBox<String> roomAdd;

    @FXML
    private ComboBox<Integer> startAdd;

    @FXML
    private ComboBox<Integer> stopAdd;


    @FXML
    void showAdd(ActionEvent event) {
        tabel.setVisible(false);
        anchorVisible.setVisible(true);
    }


    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton customersButton;

    @FXML
    private TableColumn<DataClass, String> dateT;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXButton closeButton;

    @FXML
    private TableColumn<DataClass, Integer> finish;

    @FXML
    private TableColumn<DataClass, String> roomT;

    @FXML
    private TextField search;
    @FXML
    private AnchorPane pane;

    @FXML
    TableColumn<DataClass, Integer> startT;

    ObservableList<DataClass> data = FXCollections.observableArrayList();
    @FXML
    TableView<DataClass> tabel = new TableView<DataClass>(data);

    @FXML
    TableColumn<DataClass, String> title;

    @FXML
    void EditMovies(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("operationsOnMovie"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController controller = fxmlLoader.getController();
        stage.setScene(scene);
        controller.setStage(stage);
        stage.show();
    }

    @FXML
    void addMovies(ActionEvent event) {

    }

    @FXML
    void closeWindow(ActionEvent event) {
        stage.close();
    }

    @FXML
    void deleteMovies(ActionEvent event) {
        DataClass selectedFilm = tabel.getSelectionModel().getSelectedItem();

        if (selectedFilm != null) {
            try {
                // Move the selected film to the DeletedFilms table in the database
                Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
                String insertQuery = "INSERT INTO DeletedFilms (name,  id_user) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                String insertQuery2 = "delete from Reservations where Reservations.id_reservation=" + selectedFilm.getId_reservation();
                PreparedStatement insertStatement2 = connection.prepareStatement(insertQuery2);

                // Set the values for the insert statement
                insertStatement.setString(1, selectedFilm.getTitle());
                insertStatement.setInt(2, selectedFilm.getId_user()); // Assuming getUserId() returns the appropriate id_user value

                // Execute the insert statement
                insertStatement.executeUpdate();
                insertStatement2.executeUpdate();

                // Remove the selected film from the TableView
                tabel.getItems().remove(selectedFilm);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle any database errors
            }
        }
    }


    @FXML
    void searchMovie(ActionEvent event) {

    }

    @FXML
    void seeCustomers(ActionEvent event) {

    }

    private double x = 0, y = 0;

    ObservableList<DataAdd> dataAdd = FXCollections.observableArrayList();



//    private void startCollisions(ActionEvent event){
//
//    }
//
//    private void stopCollisions(ActionEvent event){
//
//    }
//
//    private void roomCollisions(ActionEvent event){
//
//    }



    public void startCollisions(MouseEvent event) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

            Integer selectedStart = startAdd.getValue();
            Integer selectedStop = stopAdd.getValue();
            String selectedRoom = roomAdd.getValue();
            LocalDate selectedDate = dateAdd.getValue();

            if (selectedStart != null && selectedStop != null && selectedRoom != null) {
                try {
                    String selectQuery = "SELECT COUNT(*) FROM Reservations WHERE type_room = ? AND date = ? " +
                            "AND start < ? AND stop > ?";

                    PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                    selectStatement.setString(1, selectedRoom);
                    selectStatement.setString(2, selectedDate.toString());
                    selectStatement.setInt(3, selectedStop);
                    selectStatement.setInt(4, selectedStart);

                    ResultSet resultSet = selectStatement.executeQuery();
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        // Disable roomAdd, startAdd, and stopAdd
                        roomAdd.setDisable(true);
                        startAdd.setDisable(true);
                        stopAdd.setDisable(true);

                        // Set custom cell factory for roomAdd to disable specific options
                        roomAdd.setCellFactory(param -> new ListCell<String>() {
                            @Override
                            protected void updateItem(String room, boolean empty) {
                                super.updateItem(room, empty);
                                if (room == null || empty) {
                                    setDisable(false);
                                } else if (!room.equals(selectedRoom)) {
                                    setDisable(true);
                                }
                                setText(room);
                            }
                        });

                        // Set custom cell factory for stopAdd to disable specific options
                        stopAdd.setCellFactory(param -> new ListCell<Integer>() {
                            @Override
                            protected void updateItem(Integer stop, boolean empty) {
                                super.updateItem(stop, empty);
                                if (stop == null || empty) {
                                    setDisable(false);
                                } else if (stop < selectedStart || stop > selectedStop) {
                                    setDisable(true);
                                }else if (selectedStop < selectedStart){
                                    ObservableList<Integer> aux=new ObservableList<Integer>() {
                                        @Override
                                        public void addListener(ListChangeListener<? super Integer> listChangeListener) {

                                        }

                                        @Override
                                        public void removeListener(ListChangeListener<? super Integer> listChangeListener) {

                                        }

                                        @Override
                                        public boolean addAll(Integer... integers) {
                                            return false;
                                        }

                                        @Override
                                        public boolean setAll(Integer... integers) {
                                            return false;
                                        }

                                        @Override
                                        public boolean setAll(Collection<? extends Integer> collection) {
                                            return false;
                                        }

                                        @Override
                                        public boolean removeAll(Integer... integers) {
                                            return false;
                                        }

                                        @Override
                                        public boolean retainAll(Integer... integers) {
                                            return false;
                                        }

                                        @Override
                                        public void remove(int i, int i1) {

                                        }

                                        @Override
                                        public int size() {
                                            return 0;
                                        }

                                        @Override
                                        public boolean isEmpty() {
                                            return false;
                                        }

                                        @Override
                                        public boolean contains(Object o) {
                                            return false;
                                        }

                                        @Override
                                        public Iterator<Integer> iterator() {
                                            return null;
                                        }

                                        @Override
                                        public Object[] toArray() {
                                            return new Object[0];
                                        }

                                        @Override
                                        public <T> T[] toArray(T[] a) {
                                            return null;
                                        }

                                        @Override
                                        public boolean add(Integer integer) {
                                            return false;
                                        }

                                        @Override
                                        public boolean remove(Object o) {
                                            return false;
                                        }

                                        @Override
                                        public boolean containsAll(Collection<?> c) {
                                            return false;
                                        }

                                        @Override
                                        public boolean addAll(Collection<? extends Integer> c) {
                                            return false;
                                        }

                                        @Override
                                        public boolean addAll(int index, Collection<? extends Integer> c) {
                                            return false;
                                        }

                                        @Override
                                        public boolean removeAll(Collection<?> c) {
                                            return false;
                                        }

                                        @Override
                                        public boolean retainAll(Collection<?> c) {
                                            return false;
                                        }

                                        @Override
                                        public void clear() {

                                        }

                                        @Override
                                        public Integer get(int index) {
                                            return null;
                                        }

                                        @Override
                                        public Integer set(int index, Integer element) {
                                            return null;
                                        }

                                        @Override
                                        public void add(int index, Integer element) {

                                        }

                                        @Override
                                        public Integer remove(int index) {
                                            return null;
                                        }

                                        @Override
                                        public int indexOf(Object o) {
                                            return 0;
                                        }

                                        @Override
                                        public int lastIndexOf(Object o) {
                                            return 0;
                                        }

                                        @Override
                                        public ListIterator<Integer> listIterator() {
                                            return null;
                                        }

                                        @Override
                                        public ListIterator<Integer> listIterator(int index) {
                                            return null;
                                        }

                                        @Override
                                        public List<Integer> subList(int fromIndex, int toIndex) {
                                            return null;
                                        }

                                        @Override
                                        public void addListener(InvalidationListener invalidationListener) {

                                        }

                                        @Override
                                        public void removeListener(InvalidationListener invalidationListener) {

                                        }
                                    };
                                    for(int i=selectedStart+1;i<=22;i++){
                                        aux.add(i);
                                    }
                                    stopAdd.setItems(aux);
                                }
                                setText(stop == null ? "" : stop.toString());
                            }
                        });
                    } else {
                        // Enable roomAdd, startAdd, and stopAdd
                        roomAdd.setDisable(false);
                        startAdd.setDisable(false);
                        stopAdd.setDisable(false);

                        // Reset cell factory for roomAdd to enable all options
                        roomAdd.setCellFactory(null);

                        // Reset cell factory for stopAdd to enable all options
                        stopAdd.setCellFactory(null);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void stopCollisions(MouseEvent event) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

            Integer selectedStart = startAdd.getValue();
            Integer selectedStop = stopAdd.getValue();
            String selectedRoom = roomAdd.getValue();
            LocalDate selectedDate = dateAdd.getValue();

            if (selectedStart != null && selectedStop != null && selectedRoom != null) {
                try {
                    String selectQuery = "SELECT COUNT(*) FROM Reservations WHERE type_room = ? AND date = ? " +
                            "AND start < ? AND stop > ?";

                    PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                    selectStatement.setString(1, selectedRoom);
                    selectStatement.setString(2, selectedDate.toString());
                    selectStatement.setInt(3, selectedStop);
                    selectStatement.setInt(4, selectedStart);

                    ResultSet resultSet = selectStatement.executeQuery();
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        // Disable roomAdd, startAdd, and stopAdd
                        roomAdd.setDisable(true);
                        startAdd.setDisable(true);
                        stopAdd.setDisable(true);

                        // Set custom cell factory for roomAdd to disable specific options
                        roomAdd.setCellFactory(param -> new ListCell<String>() {
                            @Override
                            protected void updateItem(String room, boolean empty) {
                                super.updateItem(room, empty);
                                if (room == null || empty) {
                                    setDisable(false);
                                } else if (!room.equals(selectedRoom)) {
                                    setDisable(true);
                                }
                                setText(room);
                            }
                        });

                        // Set custom cell factory for stopAdd to disable specific options
                        startAdd.setCellFactory(param -> new ListCell<Integer>() {
                            @Override
                            protected void updateItem(Integer start, boolean empty) {
                                super.updateItem(start, empty);
                                if (start == null || empty) {
                                    setDisable(false);
                                } else if (start > selectedStart || start < selectedStop) {
                                    setDisable(true);
                                }else if (selectedStop < selectedStart){
                                    ObservableList<Integer> aux=new ObservableList<Integer>() {
                                        @Override
                                        public void addListener(ListChangeListener<? super Integer> listChangeListener) {

                                        }

                                        @Override
                                        public void removeListener(ListChangeListener<? super Integer> listChangeListener) {

                                        }

                                        @Override
                                        public boolean addAll(Integer... integers) {
                                            return false;
                                        }

                                        @Override
                                        public boolean setAll(Integer... integers) {
                                            return false;
                                        }

                                        @Override
                                        public boolean setAll(Collection<? extends Integer> collection) {
                                            return false;
                                        }

                                        @Override
                                        public boolean removeAll(Integer... integers) {
                                            return false;
                                        }

                                        @Override
                                        public boolean retainAll(Integer... integers) {
                                            return false;
                                        }

                                        @Override
                                        public void remove(int i, int i1) {

                                        }

                                        @Override
                                        public int size() {
                                            return 0;
                                        }

                                        @Override
                                        public boolean isEmpty() {
                                            return false;
                                        }

                                        @Override
                                        public boolean contains(Object o) {
                                            return false;
                                        }

                                        @Override
                                        public Iterator<Integer> iterator() {
                                            return null;
                                        }

                                        @Override
                                        public Object[] toArray() {
                                            return new Object[0];
                                        }

                                        @Override
                                        public <T> T[] toArray(T[] a) {
                                            return null;
                                        }

                                        @Override
                                        public boolean add(Integer integer) {
                                            return false;
                                        }

                                        @Override
                                        public boolean remove(Object o) {
                                            return false;
                                        }

                                        @Override
                                        public boolean containsAll(Collection<?> c) {
                                            return false;
                                        }

                                        @Override
                                        public boolean addAll(Collection<? extends Integer> c) {
                                            return false;
                                        }

                                        @Override
                                        public boolean addAll(int index, Collection<? extends Integer> c) {
                                            return false;
                                        }

                                        @Override
                                        public boolean removeAll(Collection<?> c) {
                                            return false;
                                        }

                                        @Override
                                        public boolean retainAll(Collection<?> c) {
                                            return false;
                                        }

                                        @Override
                                        public void clear() {

                                        }

                                        @Override
                                        public Integer get(int index) {
                                            return null;
                                        }

                                        @Override
                                        public Integer set(int index, Integer element) {
                                            return null;
                                        }

                                        @Override
                                        public void add(int index, Integer element) {

                                        }

                                        @Override
                                        public Integer remove(int index) {
                                            return null;
                                        }

                                        @Override
                                        public int indexOf(Object o) {
                                            return 0;
                                        }

                                        @Override
                                        public int lastIndexOf(Object o) {
                                            return 0;
                                        }

                                        @Override
                                        public ListIterator<Integer> listIterator() {
                                            return null;
                                        }

                                        @Override
                                        public ListIterator<Integer> listIterator(int index) {
                                            return null;
                                        }

                                        @Override
                                        public List<Integer> subList(int fromIndex, int toIndex) {
                                            return null;
                                        }

                                        @Override
                                        public void addListener(InvalidationListener invalidationListener) {

                                        }

                                        @Override
                                        public void removeListener(InvalidationListener invalidationListener) {

                                        }
                                    };
                                    for(int i=10;i<selectedStop;i++){
                                        aux.add(i);
                                    }
                                    startAdd.setItems(aux);
                                }
                                setText(start == null ? "" : start.toString());
                            }
                        });
                    } else {
                        // Enable roomAdd, startAdd, and stopAdd
                        roomAdd.setDisable(false);
                        startAdd.setDisable(false);
                        stopAdd.setDisable(false);

                        // Reset cell factory for roomAdd to enable all options
                        roomAdd.setCellFactory(null);

                        // Reset cell factory for stopAdd to enable all options
                        startAdd.setCellFactory(null);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void roomCollisions(MouseEvent event) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

            Integer selectedStart = startAdd.getValue();
            Integer selectedStop = stopAdd.getValue();
            String selectedRoom = roomAdd.getValue();
            LocalDate selectedDate = dateAdd.getValue();

            if (selectedStart != null && selectedStop != null && selectedRoom != null) {
                try {
                    String selectQuery = "SELECT COUNT(*) FROM Reservations WHERE type_room = ? AND date = ? " +
                            "AND start < ? AND stop > ?";

                    PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                    selectStatement.setString(1, selectedRoom);
                    selectStatement.setString(2, selectedDate.toString());
                    selectStatement.setInt(3, selectedStop);
                    selectStatement.setInt(4, selectedStart);

                    ResultSet resultSet = selectStatement.executeQuery();
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        // Disable roomAdd, startAdd, and stopAdd
                        roomAdd.setDisable(true);
                        startAdd.setDisable(true);
                        stopAdd.setDisable(true);

                        // Set custom cell factory for roomAdd to disable specific options
                        roomAdd.setCellFactory(param -> new ListCell<String>() {
                            @Override
                            protected void updateItem(String room, boolean empty) {
                                super.updateItem(room, empty);
                                if (room == null || empty) {
                                    setDisable(false);
                                } else if (!room.equals(selectedRoom)) {
                                    setDisable(true);
                                }
                                setText(room);
                            }
                        });

                        // Set custom cell factory for stopAdd to disable specific options
                        stopAdd.setCellFactory(param -> new ListCell<Integer>() {
                            @Override
                            protected void updateItem(Integer stop, boolean empty) {
                                super.updateItem(stop, empty);
                                if (stop == null || empty) {
                                    setDisable(false);
                                } else if (stop < selectedStart || stop > selectedStop) {
                                    setDisable(true);
                                }
                                setText(stop == null ? "" : stop.toString());
                            }
                        });
                    } else {
                        // Enable roomAdd, startAdd, and stopAdd
                        roomAdd.setDisable(false);
                        startAdd.setDisable(false);
                        stopAdd.setDisable(false);

                        // Reset cell factory for roomAdd to enable all options
                        roomAdd.setCellFactory(null);

                        // Reset cell factory for stopAdd to enable all options
                        stopAdd.setCellFactory(null);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabel.setVisible(true);
        addButton.setVisible(true);
        deleteButton.setVisible(true);
        customersButton.setVisible(true);
        editButton.setVisible(true);
        try {
            showMovies();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        pane.setBackground(Background.EMPTY);
        closeButton.setRipplerFill(Color.TRANSPARENT);


        pane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        pane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });


            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
                dataAdd = FXCollections.observableArrayList();

                LocalDate today = LocalDate.now();
                LocalDate weekLater = today.plusDays(7);
                dateAdd.setValue(today);
                dateAdd.setDayCellFactory(this::createDateCell);

                for (int i = 10; i <= 22; i++) {
                startAdd.getItems().add(i);
                stopAdd.getItems().add(i);
            }

            roomAdd.getItems().addAll("2D", "3D", "IMAX", "4D");

            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database connection errors
            }


    }

    private DateCell createDateCell(DatePicker picker) {
        LocalDate today = LocalDate.now();
        LocalDate weekLater = today.plusDays(7);
        dateAdd.setValue(today);
        return new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(today) || date.isAfter(weekLater));

            }
        };
    }


//        try {
//            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
//
//            LocalDate today = LocalDate.now();
//            LocalDate weekLater = today.plusDays(7);
//            dateAdd.setValue(today);
//            dateAdd.setDayCellFactory(picker -> new DateCell() {
//                public void updateItem(LocalDate date, boolean empty) {
//                    super.updateItem(date, empty);
//                    setDisable(empty || date.isBefore(today) || date.isAfter(weekLater));
//
//                    if (!isDisabled() && !empty) {
//                        String selectedTypeRoom = roomAdd.getValue();
//                        int selectedStart = startAdd.getValue();
//                        int selectedStop = stopAdd.getValue();
//                        String selectedDate = date.toString();
//
//                        try {
//                            String selectQuery = "SELECT * FROM Reservations WHERE type_room = ? AND date = ? AND start <= ? AND stop >= ?";
//                            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
//                            selectStatement.setString(1, selectedTypeRoom);
//                            selectStatement.setString(2, selectedDate);
//                            selectStatement.setInt(3, selectedStart);
//                            selectStatement.setInt(4, selectedStop);
//
//                            ResultSet resultSet = selectStatement.executeQuery();
//                            setDisable(resultSet.isBeforeFirst());
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                            // Handle any database errors
//                        }
//                    }
//                }
//            });
//
//            for (int i = 10; i <= 22; i++) {
//                startAdd.getItems().add(i);
//                stopAdd.getItems().add(i);
//            }
//
//            roomAdd.getItems().addAll("2D", "3D", "IMAX", "4D");
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

//try {
//        Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
//
//        LocalDate today = LocalDate.now();
//        LocalDate weekLater = today.plusDays(7);
//        dateAdd.setValue(today);
//        dateAdd.setDayCellFactory(picker -> new DateCell() {
//            public void updateItem(LocalDate date, boolean empty) {
//                super.updateItem(date, empty);
//                setDisable(empty || date.isBefore(today) || date.isAfter(weekLater));
//                String insertQuery = "SELECT date FROM Reservations WHERE date = ?";
//                try {
//                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
//                    insertStatement.setString(1, date.toString());
//
//                    ResultSet resultSet = insertStatement.executeQuery();
//                    setDisable(resultSet.isBeforeFirst());
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        for (int i = 10; i <= 22; i++) {
//            startAdd.getItems().add(i);
//            stopAdd.getItems().add(i);
//        }
//
//        roomAdd.getItems().addAll("2D", "3D", "IMAX", "4D");
//
//        String selectQuery = "SELECT id_film, start, stop, type_room, date FROM Reservations";
//        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
//        ResultSet rs = selectStatement.executeQuery();
//
//        // Loop through the result set and add each row to the observable list
//        while (rs.next()) {
//            int id_film = rs.getInt("id_film");
//            int stop = rs.getInt("stop");
//            int start = rs.getInt("start");
//            String type_room = rs.getString("type_room");
//            String date = rs.getString("date");
//            dataAdd.add(new DataAdd(id_film, start, stop, type_room, date));
//        }
//
//        rs.close();
//
//        int selectedStart = startAdd.getValue();
//        int selectedStop = stopAdd.getValue();
//        String selectedDate = dateAdd.getValue().toString();
//
//        boolean hasConflict = false;
//        for (DataAdd data : dataAdd) {
//            if (data.getDate().equals(selectedDate) && data.getStart() <= selectedStop && data.getStop() >= selectedStart) {
//                hasConflict = true;
//                break;
//            }
//        }
//
//        startAdd.setDisable(hasConflict);
//        stopAdd.setDisable(hasConflict);
//        if (!hasConflict) {
//            // Update the values in startAdd and stopAdd if needed
//            // startAdd.setValue(selectedStart);
//            // stopAdd.setValue(selectedStop);
//        }
//
//        String selectedTypeRoom = roomAdd.getValue();
//        if (selectedTypeRoom != null) {
//
//            try {
//                String selectQuery1 = "SELECT * FROM Reservations WHERE type_room = ? AND date = ? AND start <= ? AND stop >= ?";
//                PreparedStatement selectStatement1 = connection.prepareStatement(selectQuery1);
//                selectStatement1.setString(1, selectedTypeRoom);
//                selectStatement1.setString(2, selectedDate);
//                selectStatement1.setInt(3, selectedStart);
//                selectStatement1.setInt(4, selectedStop);
//                ResultSet resultSet = selectStatement1.executeQuery();
//                roomAdd.setDisable(resultSet.isBeforeFirst());
//            } catch (SQLException e) {
//                e.printStackTrace();
//                // Handle any database errors
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }

    

    public boolean isReservationConflict(int selectedFilmId, String selectedTypeRoom, int selectedStart, int selectedStop) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

        String selectQuery = "SELECT * FROM Reservations JOIN Film ON Reservations.id_film = Film.id_film " +
                "WHERE Reservations.id_film = ? AND type_room = ? " +
                "AND ((start <= ? AND stop >= ?) OR (start <= ? AND stop >= ?)) AND ((start+(Film.length%60+1)) <= stop)";;
        try {
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, selectedFilmId);
            selectStatement.setString(2, selectedTypeRoom);
            selectStatement.setInt(3, selectedStart);
            selectStatement.setInt(4, selectedStop);
            selectStatement.setInt(5, selectedStart);
            selectStatement.setInt(6, selectedStop);

            ResultSet resultSet = selectStatement.executeQuery();
            return resultSet.isBeforeFirst(); // Returns true if there is a conflict, false otherwise
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    private void showMovies(){
//        try {
//            // create a new JDBC connection
//            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
//
//            // create a new JDBC statement
//            Statement statement = connection.createStatement();
//
//            // execute the SQL statements and retrieve the results
//            ResultSet nameResult = statement.executeQuery("SELECT Film.name FROM Film JOIN Reservations R ON Film.id_film = R.id_film;");
//            ResultSet startResult = statement.executeQuery("SELECT start FROM Reservations;");
//            ResultSet stopResult = statement.executeQuery("SELECT stop FROM Reservations;");
//            ResultSet dateResult = statement.executeQuery("SELECT date FROM Reservations;");
//            ResultSet roomResult = statement.executeQuery("SELECT type_room FROM Reservations;");
//
//            // process the results
//            while (nameResult.next()) {
//                String name = nameResult.getString("name");
//                title.setCellFactory(tc -> new TableCell<DataClass,String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                title.setCellValueFactory(new PropertyValueFactory<>(name));
//                tabel.getColumns().add(title);
//                System.out.println(title.getText());
//            }
//            while (startResult.next()) {
//                String start = startResult.getString("start");
//
//                startT.setCellFactory(tc -> new TableCell<>() {
//                    @Override
//                    protected void updateItem(Integer item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                startT.setCellValueFactory(new PropertyValueFactory<>(start));
//                startT.getColumns().add(startT);
//            }
//            while (stopResult.next()) {
//                String stop = stopResult.getString("stop");
//
//                finish.setCellFactory(tc -> new TableCell<>() {
//                    @Override
//                    protected void updateItem(Integer item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                finish.setCellValueFactory(new PropertyValueFactory<>(stop));
//                finish.getColumns().add(finish);
//            }
//            while (dateResult.next()) {
//                String date = dateResult.getString("date");
//
//                dateT.setCellFactory(tc -> new TableCell<>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                dateT.setCellValueFactory(new PropertyValueFactory<>(date));
//                dateT.getColumns().add(dateT);
//            }
//            while (roomResult.next()) {
//                String room = roomResult.getString("type_room");
//
//
//                roomT.setCellFactory(tc -> new TableCell<>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                roomT.setCellValueFactory(new PropertyValueFactory<>(room));
//                roomT.getColumns().add(roomT);
//            }
//
//            // close the JDBC objects
//            roomResult.close();
//            dateResult.close();
//            stopResult.close();
//            startResult.close();
//            nameResult.close();
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//            // handle the exception
//        }
//    }

    int index=0;
    public void showMovies() throws SQLException {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

            // create a new JDBC statement
            Statement statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("SELECT start,stop,type_room,date,name,F.image,Reservations.id_user,Reservations.id_reservation from Reservations join Film F on F.id_film = Reservations.id_film");


        // Loop through the result set and add each row to the observable list
        while (rs.next()) {
            String name = rs.getString("name");
            int stop = rs.getInt("stop");
            int start = rs.getInt("start");
            String type_room = rs.getString("type_room");
            String date=rs.getString("date");
            int id_user= rs.getInt("id_user");
            int id_reservation= rs.getInt("id_reservation");
            data.add(new DataClass(name,start,stop,date,type_room,id_user,id_reservation));
            //System.out.println(data.get(index).getTitle()+data.get(index).getRoomT()+data.get(index).getDateT()+data.get(index).getId_user());
            index++;
        }


        // Define the columns for the TableView

        title.setCellValueFactory(new PropertyValueFactory<DataClass, String>("title"));
        startT.setCellValueFactory(new PropertyValueFactory<DataClass,Integer>("startT"));
        finish.setCellValueFactory(new PropertyValueFactory<DataClass, Integer>("finish"));
        roomT.setCellValueFactory(new PropertyValueFactory<DataClass, String>("roomT"));
        dateT.setCellValueFactory(new PropertyValueFactory<DataClass, String>("dateT"));
        tabel.setItems(data);


    }


    private Stage stage=new Stage();
    public void setStage(Stage stage) {
        this.stage=stage;
    }
}