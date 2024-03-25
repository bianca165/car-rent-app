package domain;

public interface EntitateConverter<T extends Entitate> {
    String toString(T object);

    T fromString(String line);
}