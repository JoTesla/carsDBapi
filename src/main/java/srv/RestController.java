package srv;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    public static class RestResponse{
        private String reg_num;
        private String model;
        private String color;

        public String getReg_num() {
            return reg_num;
        }

        public void setReg_num(String reg_num) {
            this.reg_num = reg_num;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public RestResponse restMethod(){
        RestResponse result = new RestResponse();

        result.setColor("");
        result.setModel("");
        result.setReg_num("");

        return result;
    }
}
