package srv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import srv.Car;

public class ReadFromJSON {

    private ObjectMapper objectMapper = new ObjectMapper();



    public Car parseJSON(String params) throws JsonProcessingException {
        System.out.println(params);
        //objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        //objectMapper.enableDefaultTyping();
        Car car = objectMapper.readValue("{" +params+ "}", Car.class);
        //Car car = new Car();
        return car;
    }




}
