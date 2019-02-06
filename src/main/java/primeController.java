import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class primeController implements Initializable {

    Random rnd = new Random();
    // baseNumberの数字がランダム生成したい数の範囲
    private int baseNumber = 300;
    private int number;
    private int judge;
    private int range;
    private int answerNumber;

    // あのてーしょん
    @FXML
    private Label titleLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private Button noButton;

    @FXML
    private Button yesButton;

    @FXML
    private Label resultLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button generateButton;

    @FXML
    private Label continuousLabel;

    @FXML
    private Label continuousNumberLabel;


    // 初期化処理
    // eratosthenes() を使うための準備, 連続正解数の初期化と表示
    // yes,noボタンを使用不可にする
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        baseOption();
        answerNumber = 0;
        continuousNumberLabel.setText(answerNumber + "");
        noButton.setDisable(true);
        yesButton.setDisable(true);
    }


    // エラトステネスの篩にかけるためにbaseNumberにsqrt()をかける ⇒ a の導出
    // a < sqrt(baseNumber) < b の時 a以下の倍数全てで判定をかける ⇒ a が eratosthenes() の for文：range になる
    private int baseOption() {
        return range = (int) (Math.sqrt(baseNumber));
    }


    // generate処理
    // 条件を満たす数字を生成して表示する
    // エラトステネスの篩にかけて素数判定しておく
    // generateしたときに, yes, noボタンを使用可にする．
    private void generate() {
        // 2, 5 で割り切れる数は生成しない
        do {
            number = rnd.nextInt(baseNumber) + range + 1;
        }while (number % 2 == 0 || number % 5 == 0);
        questionLabel.setText(number + "");
        eratosthenes(number);
        noButton.setDisable(false);
        yesButton.setDisable(false);

    }

    // generateボタン処理
    @FXML
    public void generateClick() {
        generate();
    }


    // yseボタン処理
    @FXML
    public void yesClick() {
        judgeAnswer(0);
    }

    // noボタン処理
    @FXML
    public void noClick(){
        judgeAnswer(1);
    }

    // エラトステネスの篩
    // 素数ならば judge = 0
    // でなければ judge = 1
    private void eratosthenes(int judgeNumber) {
        for (int i = 2; i <= range ; i++) {
            if (judgeNumber % i == 0) {
                judge = 1;
                break;
            }
            judge = 0;
        }

        if (judgeNumber == 57) {
            judge = 57;
        }

        // Cheat debug

/*
        if (judge == 1) {
            System.out.println("Answer -> NO");
            System.out.println();
        } else {
            System.out.println("Answer -> YES");
            System.out.println();
        }
*/


    }

    // ユーザ提示のuserSelect と篩にかけたjudge の数が一致するか判定，結果を表示
    // （次の問題の数字も生成する（ボタン連打すると連続正解数が増える問題が解決できないため）
    private void judgeAnswer(int userSelect) {
        if (judge == 57) {
            resultLabel.setText("Grothendieck!!");

        } else if (userSelect == judge) {
            resultLabel.setText("Collect!!");
            answerNumber++;
            continuousNumberLabel.setText(answerNumber + "");

        } else {
            resultLabel.setText("Wrong!!");
            answerNumber = 0;
            continuousNumberLabel.setText(answerNumber + "");
        }

        // debug

/*
            System.out.println("生成された数 = " + number);
            System.out.println("素数判定の結果 = " + judge);

            if (userSelect == 0) {
                System.out.println("素数を選択");
            } else {
                System.out.println("素数じゃないを選択");
            }
            if (userSelect == judge) {
                System.out.println("OK!");
            } else {
                System.out.println("NG!");
            }
            System.out.println();
            System.out.println();
*/

        // fin.

        generate();


    }

    // exit処理
    @FXML
    public void exitClick() {
        System.exit(0);
    }

}

// yes,noボタンでの正解数カウントの誤作動を,SetDisable(boolean)を使って対策
// 篩をかけるときの範囲にある数字を素数判定の数として使用しないことを工夫