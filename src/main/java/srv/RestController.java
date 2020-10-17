package srv;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    String dbURL = "jdbc:postgresql://localhost:5432/postgres";
    String dbUser = "jbrains";
    String dbPass = "1";
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
    }/*
    //Вызов запроса всей таблицы без параметров фильтрации
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseBody
    public List<RestResponse> selectAll(){
        List<RestResponse> cars= new ArrayList<RestResponse>();

        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM cars";
            System.out.println("all");
            cars = fillCars((List<RestResponse>) cars, connection, sql);
        }catch (Exception e) {
           catcher(e);
        }
        return cars;
    }*/
    //Вызов запроса с фильтрами
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE
            //, params = {"brand", "color", "reg_num", "powerHP", "sold"}
            )
    @ResponseBody
    public List<RestResponse> selectFiltered(@RequestParam(name = "brand", defaultValue = "", required = false) String brand,
                                             @RequestParam(name = "color", defaultValue = "", required = false) String color,
                                             @RequestParam(name = "reg_num", defaultValue = "", required = false) String reg_num,
                                             @RequestParam(name = "powerHP", defaultValue = "", required = false) String powerHP,
                                             @RequestParam(name = "sold", defaultValue = "", required = false) String sold){
        List<RestResponse> cars = new ArrayList<RestResponse>();

        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            connection.setAutoCommit(false);
            String where = "where n=n"; //Заглушка n=n чтобы запрос не крашился, вместо перебора всех параметров
            //собираем запрос из параметров
            where +=(brand.isEmpty()  ? "": " AND brand   LIKE '%" + brand   + "%'") +
                    (color.isEmpty()  ? "": " AND color   LIKE '%" + color   + "%'") +
                    (reg_num.isEmpty()? "": " AND reg_num LIKE '%" + reg_num + "%'") +
                    (powerHP.isEmpty()? "": " AND powerhp =      " + powerHP       ) +
                    (sold.isEmpty()   ? "": " AND sold    =      " + sold          );
            String sql = "SELECT * FROM cars " + where; //полный запрос
            System.out.println(sql);
            cars = fillCars((List<RestResponse>) cars, connection, sql);


        } catch (Exception e){
            catcher(e);
        }


        return cars;
    }

    private List<RestResponse> fillCars(List<RestResponse> cars, Connection connection, String sql) throws SQLException {
        Statement statement;
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
        return cars;
    }

    void catcher(Exception e){
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }


}
