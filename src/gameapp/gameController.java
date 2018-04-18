package gameapp;

import admin.PlayerData;
import dbUtil.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class gameController implements Initializable  {

    @FXML
    private Label result;


    @FXML
    private Button show_btn;
    @FXML
    private Label loginstatus;

    @FXML
    private Label q_desc;
    @FXML
    private RadioButton ans1;
    @FXML
    private RadioButton ans2;
    @FXML
    private RadioButton ans3;
    @FXML
    private RadioButton ans4;
    @FXML
    private ToggleGroup toggleGroupAnswers;

    @FXML
    private RadioButton category1;
    @FXML
    private RadioButton category2;
    @FXML
    private RadioButton category3;

    @FXML
    private RadioButton helpPublic;
    @FXML
    private RadioButton helpHalf;

    @FXML
    private ToggleGroup Categories;

    private String qid;

    private PlayerData[] players;

    public void initialize(URL url, ResourceBundle rb){
        try {
            loadCategories();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        players = new PlayerData[2];
       // players[0].setName("Player1");
       // players[1].setName("Player2");
        //players[0].setMoney(0);
        //players[1].setMoney(0);
    }


    public void show_question(ActionEvent actionEvent) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;

        RadioButton selectedRadioButton = (RadioButton) Categories.getSelectedToggle();
        String selectedCategory = selectedRadioButton.getUserData().toString();

        String sql = "select * from questions where categoryId=? order by random() limit 1 ";


        try{
            Connection dbCon = dbConnection.getConnection();
            pr = dbCon.prepareStatement(sql);
            pr.setString(1,selectedCategory);

            rs = pr.executeQuery();

            if(rs.next()){
                this.q_desc.setText(rs.getString("question"));
                qid = rs.getString("qid");
                fetch_answers();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {

            pr.close();
            rs.close();

        }


    }


    public void fetch_answers() throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "select * from answers where qid=? order by random()";


        try{
            Connection dbCon = dbConnection.getConnection();

            pr = dbCon.prepareStatement(sql);
            pr.setString(1,qid);

            rs = pr.executeQuery();

            if(rs.next()){
                this.ans1.setText(rs.getString("answer"));
                this.ans1.setUserData(rs.getString("ansId"));
                rs.next();
                this.ans2.setText(rs.getString("answer"));
                this.ans2.setUserData(rs.getString("ansId"));
                rs.next();
                this.ans3.setText(rs.getString("answer"));
                this.ans3.setUserData(rs.getString("ansId"));
                rs.next();
                this.ans4.setText(rs.getString("answer"));
                this.ans4.setUserData(rs.getString("ansId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {

            pr.close();
            rs.close();

        }
    }

    public void submit_answer( ActionEvent actionEvent) throws SQLException {

        RadioButton selectedRadioButton = (RadioButton) toggleGroupAnswers.getSelectedToggle();
        String selectedAnswer = selectedRadioButton.getUserData().toString();
        System.out.println(selectedAnswer+"   kjjhjhjlkj");

        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "select * from answers where ansId=?";


        try{
            Connection dbCon = dbConnection.getConnection();

            pr = dbCon.prepareStatement(sql);
            pr.setString(1,selectedAnswer);

            rs = pr.executeQuery();

            if(rs.next()){
                String check_answer = rs.getString("CorrectOrWrong");
                if (check_answer.equals("1")){
                    result.setText("correct answer");
                }
                else {
                    result.setText("Not correct answer. End of Game");
                }
            }
            clear_answers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            pr.close();
            rs.close();
        }

    }

    private void clear_answers(){
        this.ans1.setSelected(false);
        this.ans2.setSelected(false);
        this.ans3.setSelected(false);
        this.ans4.setSelected(false);

        this.ans1.setDisable(false);
        this.ans2.setDisable(false);
        this.ans3.setDisable(false);
        this.ans4.setDisable(false);
    }
    private void checkCategory(){

    }

    private void loadCategories() throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "select * from categories";


        try{
            Connection dbCon = dbConnection.getConnection();

            pr = dbCon.prepareStatement(sql);
            rs = pr.executeQuery();

            if(rs.next()){
                this.category1.setText(rs.getString("categoryName"));
                this.category1.setUserData(rs.getString("catId"));
                rs.next();
                this.category2.setText(rs.getString("categoryName"));
                this.category2.setUserData(rs.getString("catId"));
                rs.next();
                this.category3.setText(rs.getString("categoryName"));
                this.category3.setUserData(rs.getString("catId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            pr.close();
            rs.close();
        }
    }

    public void helpPublicSelected(ActionEvent actionEvent){
        this.helpHalf.setSelected(false);
    }

    public void helpHalfSelected(ActionEvent actionEvent){
        this.helpPublic.setSelected(false);
    }

    public void getHelp(ActionEvent actionEvent) throws SQLException {
        if(this.helpHalf.isSelected()){
            this.helpHalf.setDisable(true);
            this.helpHalf.setSelected(false);

            PreparedStatement pr = null;
            ResultSet rs = null;
            String sql = "select * from answers where qid=? and CorrectOrWrong=? order by random() limit 2";


            try{
                Connection dbCon = dbConnection.getConnection();

                pr = dbCon.prepareStatement(sql);
                pr.setString(1,qid);
                pr.setString(2,"0");

                rs = pr.executeQuery();

                while(rs.next()){
                    if (this.ans1.getUserData().equals(rs.getString("ansId"))) {
                        this.ans1.setDisable(true);
                        this.ans1.setSelected(false);
                    }

                    if (this.ans2.getUserData().equals(rs.getString("ansId"))) {
                        this.ans2.setDisable(true);
                        this.ans2.setSelected(false);
                    }

                    if (this.ans3.getUserData().equals(rs.getString("ansId"))) {
                        this.ans3.setDisable(true);
                        this.ans3.setSelected(false);
                    }

                    if (this.ans4.getUserData().equals(rs.getString("ansId"))) {
                        this.ans4.setDisable(true);
                        this.ans4.setSelected(false);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            finally {
                pr.close();
                rs.close();
            }

        }
        if(this.helpPublic.isSelected()){
            this.helpPublic.setDisable(true);
            this.helpPublic.setSelected(false);

            int count = 4;
            int sum = 100;
            java.util.Random g = new java.util.Random();

            int vals[] = new int[count];
            sum -= count;

            for (int i = 0; i < count-1; ++i) {
                vals[i] = g.nextInt(sum);
            }
            vals[count-1] = sum;

            java.util.Arrays.sort(vals);
            for (int i = count-1; i > 0; --i) {
                vals[i] -= vals[i-1];
            }
            for (int i = 0; i < count; ++i) { ++vals[i]; }


            this.ans1.setText(this.ans1.getText()+" - "+vals[0]);
            this.ans2.setText(this.ans2.getText()+" - "+vals[1]);
            this.ans3.setText(this.ans3.getText()+" - "+vals[2]);
            this.ans4.setText(this.ans4.getText()+" - "+vals[3]);

        }
    }


}
