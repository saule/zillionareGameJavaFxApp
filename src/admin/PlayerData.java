package admin;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayerData {

    private final StringProperty name;
    private int money;


    public PlayerData(String name, int money){
        this.name = new SimpleStringProperty(name);
        this.money = money;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String id) {
        this.name.set(id);
    }

    public int getMoney() {
        return money;
    }


    public void setMoney(int money) {
        this.money=money;
    }

}
