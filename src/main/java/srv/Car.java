package srv;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;


public class Car {

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

        Car() {
            this.setReg_num("");
            this.setBrand("");
            this.setColor("");
            this.setPowerHP(0);
            this.setSold(false);
        }

        Car(String reg_num, String brand) {
            this.setReg_num(reg_num);
            this.setBrand(brand);
            this.setColor("");
            this.setPowerHP(0);
            this.setSold(false);
        }

        Car(String reg_num, String brand, String color) {
            this.setReg_num(reg_num);
            this.setBrand(brand);
            this.setColor(color);
            this.setPowerHP(0);
            this.setSold(false);
        }

        Car(String reg_num, String brand, String color, int powerHP) {
            this.setReg_num(reg_num);
            this.setBrand(brand);
            this.setColor(color);
            this.setPowerHP(powerHP);
            this.setSold(false);
        }

        Car(String reg_num, String brand, String color, int powerHP, boolean sold) {
            this.setReg_num(reg_num);
            this.setBrand(brand);
            this.setColor(color);
            this.setPowerHP(powerHP);
            this.setSold(sold);
        }
}
