package it.sisca.dunkest.dunkestsimulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HelloController implements Initializable {
    public static final String GUARD = "Guardia";
    public static final String COACH = "Coach";
    public static final String FORWARD = "Ala";
    public static final String CENTER = "Centro";
    private final List<Giocatore> squadra = new ArrayList<>();
    @FXML
    public Label center0;
    @FXML
    public Label center1;
    @FXML
    public Label forward0;
    @FXML
    public Label forward1;
    @FXML
    public Label forward2;
    @FXML
    public Label forward3;
    @FXML
    public Label guard0;
    @FXML
    public Label guard1;
    @FXML
    public Label guard2;
    @FXML
    public Label guard3;
    @FXML
    public Label coach;
    private List<String> listOfRole = new ArrayList<>();
    private FilteredList<Giocatore> listaGiocatori;
    private ObservableList<String> listaSquadre;
    private ObservableList<String> listaRuoli;
    private String teamName = "";
    @FXML
    private Label labelCrediti;
    @FXML
    private Label selectedPlayerName;
    @FXML
    private Label selectedPlayerRole;
    @FXML
    private Label selectedPlayerTeam;
    @FXML
    private Label selectedPlayerPrice;
    @FXML
    private Label filterCreditLabel;
    @FXML
    private Label center0CR;
    @FXML
    private Label center1CR;
    @FXML
    private Label guard0CR;
    @FXML
    private Label guard1CR;
    @FXML
    private Label guard2CR;
    @FXML
    private Label guard3CR;
    @FXML
    private Label forward0CR;
    @FXML
    private Label forward1CR;
    @FXML
    private Label forward2CR;
    @FXML
    private Label forward3CR;
    @FXML
    private Label coachCR;
    @FXML
    private Slider slider;
    @FXML
    private Button listoneDunkest;
    @FXML
    private CheckBox checkGuard;
    @FXML
    private CheckBox checkCoach;
    @FXML
    private CheckBox checkCenter;
    @FXML
    private CheckBox checkForward;
    @FXML
    private ComboBox teamFilter;
    @FXML
    private ListView playerList;
    @FXML
    private TextField nameFilter;
    @FXML
    private Button center0Remove;

    @FXML
    private TextField ownCredit;
    @FXML
    private Button center1Remove;
    @FXML
    private Button guard0Remove;
    @FXML
    private Button guard1Remove;
    @FXML
    private Button guard2Remove;
    @FXML
    private Button guard3Remove;
    @FXML
    private Button forward0Remove;
    @FXML
    private Button forward1Remove;
    @FXML
    private Button forward2Remove;
    @FXML
    private Button forward3Remove;
    @FXML
    private Button coachRemove;
    @FXML
    private AnchorPane center0BP;
    @FXML
    private AnchorPane center1BP;
    @FXML
    private AnchorPane guard0BP;
    @FXML
    private AnchorPane guard1BP;
    @FXML
    private AnchorPane guard2BP;
    @FXML
    private AnchorPane guard3BP;
    @FXML
    private AnchorPane forward0BP;
    @FXML
    private AnchorPane forward1BP;
    @FXML
    private AnchorPane forward2BP;
    @FXML
    private AnchorPane forward3BP;
    @FXML
    private AnchorPane coachBP;
    @FXML
    private BorderPane bottomPane;

    private boolean flag = true;
    private boolean flagLBJ = true;

    @FXML
    protected void caricaListoneDunkest() {
        final FileChooser fileChooser = new FileChooser();
        listoneDunkest.setOnMouseClicked(actionEvent -> {
            File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
            if (file != null) {
                listaGiocatori = new FilteredList<>(FXCollections.observableArrayList(ReadListoneDunkest.getListaGiocatori(file.getAbsolutePath())));
                resetAllLists();
                setTeamFilter();

            }
        });
    }

    private void resetAllLists() {
        listaRuoli = getListaRuoli(listaGiocatori);
        listaSquadre = getListaSquadre(listaGiocatori);
        setListItemGiocatori();
    }

    private void setTeamFilter() {
        teamFilter.setItems(listaSquadre);
    }

    private void setListItemGiocatori() {
        playerList.setItems(listaGiocatori);
    }

    private FilteredList<String> getListaNomi(ObservableList<Giocatore> listaGiocatori) {
        return new FilteredList<>(FXCollections.observableArrayList(listaGiocatori.stream().map(Giocatore::nome).collect(Collectors.toList())));
    }

    private ObservableList<String> getListaRuoli(ObservableList<Giocatore> listaGiocatori) {
        return FXCollections.observableArrayList(listaGiocatori.stream().map(Giocatore::ruolo).distinct().collect(Collectors.toList()));
    }

    private ObservableList<String> getListaSquadre(ObservableList<Giocatore> listaGiocatori) {
        return FXCollections.observableArrayList(listaGiocatori.stream().map(Giocatore::squadra).distinct().collect(Collectors.toList()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBackgournd();
        filterCreditLabel.textProperty().bind(slider.valueProperty().asString("%.1f"));
        slider.setOnMouseClicked(mouseEvent -> applyFilter());
        slider.valueProperty().addListener((observableValue, number, t1) -> applyFilter());

        playerList.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                Giocatore giocatore = (Giocatore) playerList.getSelectionModel().getSelectedItem();
                scleltaOK(giocatore);

            }
            Giocatore giocatore = (Giocatore) playerList.getSelectionModel().getSelectedItem();
            trollingStuff(giocatore);
            selectedPlayerName.setText(giocatore.nome());
            selectedPlayerRole.setText(giocatore.ruolo());
            selectedPlayerPrice.setText(giocatore.crediti().toString());
            selectedPlayerTeam.setText(giocatore.squadra());
        });
        teamFilter.getSelectionModel().selectedItemProperty().addListener(mouseEvent -> {
            teamName = (String) teamFilter.getSelectionModel().getSelectedItem();
            applyFilter();
        });
        filterRole();
        checkGuard.setOnAction(actionEvent -> filterRole());
        checkCoach.setOnAction(actionEvent -> filterRole());
        checkForward.setOnAction(actionEvent -> filterRole());
        checkCenter.setOnAction(actionEvent -> filterRole());

        nameFilter.addEventFilter(KeyEvent.ANY, event -> {
            applyFilter();
        });
        ownCredit.addEventFilter(KeyEvent.ANY, event -> {
            if (!event.getCharacter().trim().matches("\\d?\\,?")) {
                event.consume();
            }
            aggiornaLabel();

        });
        removePlayerFromTeam();
    }

    private void removePlayerFromTeam() {
        center0Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(center0.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            center0.setText("");
            center0.setBackground(null);

            center0CR.setText("");
            center0CR.setBackground(null);
            aggiornaLabel();
        });
        center1Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(center1.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            center1.setText("");
            center1.setBackground(null);

            center1CR.setText("");
            center1CR.setBackground(null);
            aggiornaLabel();
        });
        guard0Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(guard0.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            guard0.setText("");
            guard0.setBackground(null);

            guard0CR.setText("");
            guard0CR.setBackground(null);
            aggiornaLabel();
        });
        guard1Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(guard1.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            guard1.setText("");
            guard1.setBackground(null);

            guard1CR.setText("");
            guard1CR.setBackground(null);

            aggiornaLabel();
        });
        guard2Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(guard2.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            guard2.setText("");
            guard2.setBackground(null);

            guard2CR.setText("");
            guard2CR.setBackground(null);
            aggiornaLabel();
        });
        guard3Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(guard3.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            guard3.setText("");
            guard3.setBackground(null);

            guard3CR.setText("");
            guard3CR.setBackground(null);
            aggiornaLabel();
        });
        forward0Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(forward0.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            forward0.setText("");
            forward0.setBackground(null);

            forward0CR.setText("");
            forward0CR.setBackground(null);

            aggiornaLabel();
        });
        forward1Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(forward1.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            forward1.setText("");
            forward1.setBackground(null);

            forward1CR.setText("");
            forward1CR.setBackground(null);
            aggiornaLabel();
        });
        forward2Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(forward2.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            forward2.setText("");
            forward2.setBackground(null);

            forward2CR.setText("");
            forward2CR.setBackground(null);
            aggiornaLabel();
        });
        forward3Remove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(forward3.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            forward3.setText("");
            forward3.setBackground(null);

            forward3CR.setText("");
            forward3CR.setBackground(null);
            aggiornaLabel();
        });
        coachRemove.setOnAction(keyEvent -> {
            Optional<Giocatore> giocatoreTrovato = squadra.stream().filter(giocatore -> giocatore.nome().equalsIgnoreCase(coach.getText())).findFirst();
            if (giocatoreTrovato.isPresent()) {
                squadra.remove(giocatoreTrovato.get());
            }
            coach.setText("");
            coach.setBackground(null);

            coachCR.setText("");
            coachCR.setBackground(null);
            aggiornaLabel();
        });
    }

    private void trollingStuff(Giocatore giocatore) {
        if (giocatore.nome().contains("ANTHONY DAVIS")) {
            AudioClip audio = new AudioClip(getClass().getResource("/vai_mostroh.mp4").toExternalForm());
            audio.play();

        }
        if (giocatore.nome().contains("DRUMMOND")) {
            AudioClip audio = new AudioClip(getClass().getResource("/drummond.mp3").toExternalForm());
            audio.play();
        }
        if (giocatore.nome().contains("SIMMONS") && flag) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
            Image image = new Image(getClass().getResource("/ma che cazz.jpg").toExternalForm());
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.show();
            flag = false;
        }

        if (giocatore.nome().contains("LEBRON") || giocatore.nome().contains("STEPHEN CURRY") && flagLBJ) {
            AudioClip audio = new AudioClip(getClass().getResource("/love-is-in-the-air-cutmp3.mp3").toExternalForm());
            audio.play();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Love is in the air", ButtonType.FINISH);
            Image image = new Image(getClass().getResource("/perplesso.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.show();
            flagLBJ = false;
        }

    }

    private void setBackgournd() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/maglietta.png", 128, 128, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        center0BP.setBackground(new Background(backgroundImage));
        center1BP.setBackground(new Background(backgroundImage));
        guard0BP.setBackground(new Background(backgroundImage));
        guard1BP.setBackground(new Background(backgroundImage));
        guard2BP.setBackground(new Background(backgroundImage));
        guard3BP.setBackground(new Background(backgroundImage));
        forward0BP.setBackground(new Background(backgroundImage));
        forward1BP.setBackground(new Background(backgroundImage));
        forward2BP.setBackground(new Background(backgroundImage));
        forward3BP.setBackground(new Background(backgroundImage));
        coachBP.setBackground(new Background(backgroundImage));
    }

    private void scleltaOK(Giocatore giocatore) {
        if (squadra.contains(giocatore)) return;
        assignOrNot(giocatore);

    }

    private void assignOrNot(Giocatore scelta) {
        switch (scelta.ruolo()) {
            case COACH -> {
                if (squadra.stream().filter(giocatore -> COACH.equalsIgnoreCase(giocatore.ruolo())).count() != 1 && !squadra.contains(scelta)) {
                    squadra.add(scelta);
                    coach.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    coach.setText(scelta.nome());
                    coachCR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    coachCR.setText(scelta.crediti().toString());
                    aggiornaLabel();
                }
            }
            case CENTER -> {
                if (squadra.stream().filter(giocatore -> CENTER.equalsIgnoreCase(giocatore.ruolo())).count() != 2 && !squadra.contains(scelta)) {
                    squadra.add(scelta);
                    if (center0.getText().isBlank()) {
                        center0.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        center0.setText(scelta.nome());
                        center0CR.setText(scelta.crediti().toString());
                        center0CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else if (center1.getText().isBlank()) {
                        center1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        center1.setText(scelta.nome());
                        center1CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        center1CR.setText(scelta.crediti().toString());
                    } else break;
                    aggiornaLabel();
                }
            }
            case FORWARD -> {
                if (squadra.stream().filter(giocatore -> FORWARD.equalsIgnoreCase(giocatore.ruolo())).count() != 4 && !squadra.contains(scelta)) {
                    squadra.add(scelta);
                    if (forward0.getText().isBlank()) {
                        forward0.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        forward0.setText(scelta.nome());
                        forward0CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        forward0CR.setText(scelta.crediti().toString());

                    } else if (forward1.getText().isBlank()) {
                        forward1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        forward1.setText(scelta.nome());
                        forward1CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        forward1CR.setText(scelta.crediti().toString());
                    } else if (forward2.getText().isBlank()) {
                        forward2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        forward2.setText(scelta.nome());
                        forward2CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        forward2CR.setText(scelta.crediti().toString());
                    } else if (forward3.getText().isBlank()) {
                        forward3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        forward3.setText(scelta.nome());
                        forward3CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        forward3CR.setText(scelta.crediti().toString());
                    } else break;
                    aggiornaLabel();
                }
            }
            case GUARD -> {
                if (squadra.stream().filter(giocatore -> GUARD.equalsIgnoreCase(giocatore.ruolo())).count() != 4 && !squadra.contains(scelta)) {
                    squadra.add(scelta);
                    if (guard0.getText().isBlank()) {
                        guard0.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        guard0.setText(scelta.nome());
                        guard0CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        guard0CR.setText(scelta.crediti().toString());

                    } else if (guard1.getText().isBlank()) {
                        guard1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        guard1.setText(scelta.nome());
                        guard1CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        guard1CR.setText(scelta.crediti().toString());

                    } else if (guard2.getText().isBlank()) {
                        guard2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        guard2.setText(scelta.nome());
                        guard2CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        guard2CR.setText(scelta.crediti().toString());

                    } else if (guard3.getText().isBlank()) {
                        guard3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        guard3.setText(scelta.nome());
                        guard3CR.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        guard3CR.setText(scelta.crediti().toString());

                    } else break;
                    aggiornaLabel();
                }
            }
        }

    }

    private void aggiornaLabel() {

        double sum = squadra.stream().mapToDouble(Giocatore::crediti).sum();
        labelCrediti.setText("Hai speso [" + sum + "] su [" + ownCredit.getText() + "]");

        if (Double.parseDouble(ownCredit.getText()) < sum) {
            bottomPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            bottomPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        }

    }

    private void filterRole() {
        listOfRole = new ArrayList<>();
        String guard = checkGuard.isSelected() ? GUARD : "";
        String coach = checkCoach.isSelected() ? COACH : "";
        String forward = checkForward.isSelected() ? FORWARD : "";
        String center = checkCenter.isSelected() ? CENTER : "";

        if (!guard.isBlank()) listOfRole.add(guard);
        if (!coach.isBlank()) listOfRole.add(coach);
        if (!forward.isBlank()) listOfRole.add(forward);
        if (!center.isBlank()) listOfRole.add(center);
        applyFilter();
    }

    public void applyFilter() {
        if (listaGiocatori != null) {
            if (nameFilter.textProperty().getValue().isBlank()) {
                if (teamName.isBlank() || teamName.equalsIgnoreCase("*")) {
                    listaGiocatori.setPredicate(giocatore -> listOfRole.contains(giocatore.ruolo()) && giocatore.crediti() < slider.getValue());
                } else {
                    listaGiocatori.setPredicate(giocatore -> giocatore.squadra().equalsIgnoreCase(teamName) && listOfRole.contains(giocatore.ruolo()) && giocatore.crediti() < slider.getValue());
                }
            } else {
                if (teamName.isBlank() || teamName.equalsIgnoreCase("*")) {
                    listaGiocatori.setPredicate(giocatore -> listOfRole.contains(giocatore.ruolo()) && giocatore.crediti() < slider.getValue() && giocatore.nome().contains(nameFilter.textProperty().getValue().toUpperCase()));
                } else {
                    listaGiocatori.setPredicate(giocatore -> giocatore.squadra().equalsIgnoreCase(teamName) && listOfRole.contains(giocatore.ruolo()) && giocatore.crediti() < slider.getValue() && giocatore.nome().contains(nameFilter.textProperty().getValue().toUpperCase()));
                }

            }
        }

    }


    public void showRemove(MouseEvent mouseEvent) {
        switch (((Pane) mouseEvent.getSource()).idProperty().getValue()) {
            case "center0BP":
                center0Remove.setVisible(true);
                break;
            case "center1BP":
                center1Remove.setVisible(true);
                break;
            case "guard0BP":
                guard0Remove.setVisible(true);
                break;
            case "guard1BP":
                guard1Remove.setVisible(true);
                break;
            case "guard2BP":
                guard2Remove.setVisible(true);
                break;
            case "guard3BP":
                guard3Remove.setVisible(true);
                break;
            case "forward0BP":
                forward0Remove.setVisible(true);
                break;
            case "forward1BP":
                forward1Remove.setVisible(true);
                break;
            case "forward2BP":
                forward2Remove.setVisible(true);
                break;
            case "forward3BP":
                forward3Remove.setVisible(true);
                break;
            case "coachBP":
                coachRemove.setVisible(true);
                break;
        }

    }

    public void hideRemove(MouseEvent mouseEvent) {
        switch (((Pane) mouseEvent.getSource()).idProperty().getValue()) {
            case "center0BP":
                center0Remove.setVisible(false);
                break;
            case "center1BP":
                center1Remove.setVisible(false);
                break;
            case "guard0BP":
                guard0Remove.setVisible(false);
                break;
            case "guard1BP":
                guard1Remove.setVisible(false);
                break;
            case "guard2BP":
                guard2Remove.setVisible(false);
                break;
            case "guard3BP":
                guard3Remove.setVisible(false);
                break;
            case "forward0BP":
                forward0Remove.setVisible(false);
                break;
            case "forward1BP":
                forward1Remove.setVisible(false);
                break;
            case "forward2BP":
                forward2Remove.setVisible(false);
                break;
            case "forward3BP":
                forward3Remove.setVisible(false);
                break;
            case "coachBP":
                coachRemove.setVisible(false);
                break;
        }

    }
}