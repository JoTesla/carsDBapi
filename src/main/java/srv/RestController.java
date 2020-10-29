package srv;

import com.fasterxml.jackson.core.JsonProcessingException;
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


    //Вызов запроса с фильтрами
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE
            //, params = {"brand", "color", "reg_num", "powerHP", "sold"}
            )
    @ResponseBody
    public List<Car> selectFiltered(@RequestParam(name = "brand", defaultValue = "", required = false) String brand,
                                             @RequestParam(name = "color", defaultValue = "", required = false) String color,
                                             @RequestParam(name = "reg_num", defaultValue = "", required = false) String reg_num,
                                             @RequestParam(name = "powerHP", defaultValue = "", required = false) String powerHP,
                                             @RequestParam(name = "sold", defaultValue = "", required = false) String sold){
        List<Car> cars = new ArrayList<Car>();

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
            cars = fillCars((List<Car>) cars, connection, sql);


        } catch (Exception e){
            catcher(e);
        }


        return cars;
    }

    private List<Car> fillCars(List<Car> cars, Connection connection, String sql) throws SQLException {
        Statement statement;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            Car car = new Car();
            car.setColor(resultSet.getString("color"));
            car.setBrand(resultSet.getString("brand"));
            car.setReg_num(resultSet.getString("reg_num"));
            car.setPowerHP(resultSet.getInt("powerHP"));
            car.setSold(resultSet.getBoolean("sold"));
            cars.add(car);
        }
        return cars;
    }

    void catcher(Exception e){
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE
            , params = {"params"}
    )
    @ResponseBody
    public String getNewCar(@RequestParam(name = "params", defaultValue = "", required = false) String params) throws JsonProcessingException, SQLException {
        ReadFromJSON readFromJSON = new ReadFromJSON();
        Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
        connection.setAutoCommit(false);
        String result;
        PreparedStatement preparedStatement;
        Car car = readFromJSON.parseJSON(params);
        String sql = "insert into cars (reg_num, brand, color, powerhp, sold) VALUES (?,?,?,?,?);";
        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,car.getReg_num());
            preparedStatement.setString(2,car.getBrand());
            preparedStatement.setString(3, car.getColor());
            preparedStatement.setInt(4, car.getPowerHP());
            preparedStatement.setBoolean(5, car.isSold());
            preparedStatement.execute();
            connection.commit();
            result = "The car: " + car.getBrand() + " with number " + car.getReg_num() + " was successfully added.";
        } catch (Exception e){
            result = e.getMessage();
            catcher(e);
            connection.rollback();
        }
        return result;

    }

}
