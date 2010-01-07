package com.vygovskiy.controls.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    public static List<Car> getCars() {
        List<Car> cars = new ArrayList<Car>();
        cars.add(new Car("Ford", "Focus", 2005));
        cars.add(new Car("Ford", "Mustang", 1979));
        cars.add(new Car("BMW", "Admiral", 1939));
        cars.add(new Car("Nissan", "Teana", 2008));
        return cars;
    }

    final private String producer;
    final private String model;
    final private int year;

    public Car(String producer, String model, int year) {
        super();
        this.producer = producer;
        this.model = model;
        this.year = year;
    }

    /**
     * @return the producer
     */
    public String getProducer() {
        return producer;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("producer = %s, model = %s, year = %s", producer,
                model, year);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result
                + ((producer == null) ? 0 : producer.hashCode());
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Car other = (Car) obj;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (producer == null) {
            if (other.producer != null)
                return false;
        } else if (!producer.equals(other.producer))
            return false;
        if (year != other.year)
            return false;
        return true;
    }

}
