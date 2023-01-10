package bigcircle.travel.repository.jdbc.h2;

public class AddressSQLs {
    public final String SAVE = "INSERT INTO ADDRESS (zonecode, address) " + "VALUES (:zonecode, :address)";
    public final String FIND_BY_ZONECODE = "SELECT * FROM ADDRESS WHERE zonecode = :zonecode";
}
