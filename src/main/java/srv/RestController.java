package srv;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    public static class RestResponse{
        private String reg_num;
        private String brand;
        private String color;
        private int powerHP;
        private boolean sold;

        public String getReg_num() {
            return reg_num;
        }

        public void setReg_num(String reg_num) {
            this.reg_num = reg_num;
        }

           public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getPowerHP() {
            return powerHP;
        }

        public void setPowerHP(int powerHP) {
            this.powerHP = powerHP;
        }

        public boolean isSold() {
            return sold;
        }

        public void setSold(boolean sold) {
            this.sold = sold;
        }


        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public List<RestResponse> restMethod(){
        List<RestResponse> cars= new ArrayList<RestResponse>();

        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","jbrains", "1");
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM cars";

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                RestResponse result = new RestResponse();
                result.setColor(resultSet.getString("color"));
                result.setBrand(resultSet.getString("brand"));
                result.setReg_num(resultSet.getString("reg_num"));
                result.setPowerHP(resultSet.getInt("powerHP"));
                result.setSold(resultSet.getBoolean("sold"));
                cars.add(result);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return cars;
    }
}
