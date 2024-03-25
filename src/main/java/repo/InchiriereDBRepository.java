package repo;

import domain.Inchiriere;
import domain.Masina;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InchiriereDBRepository extends MemoryRepository<Inchiriere> {
    private String JDBC_URL = "jdbc:sqlite:lab5.sqlite";
    Connection connection;

    public InchiriereDBRepository()
    {
        openConnection();
        createTable();
        loadDataInMemory();
        //initData();
    }

    private void openConnection()
    {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed())
            {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e. printStackTrace();
        }
    }

    private void createTable() {
        try (final Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS inchirieri(id int, idMasina int, marca VARCHAR(255), model VARCHAR(255), dataInceput date, dataSfarsit date);");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void loadDataInMemory() {
        for (Inchiriere inchiriere : getAll())
        {
            data.add(inchiriere);
            //super.add(p);
        }
    }
    public List<Inchiriere> getAll() {
        List<Inchiriere> inchirieri = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM inchirieri")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idMasina = rs.getInt("idMasina");
                String marca = rs.getString("marca");
                String model = rs.getString("model");
                LocalDate dataInceput = rs.getDate("dataInceput").toLocalDate();
                LocalDate dataSfarsit = rs.getDate("dataSfarsit").toLocalDate();
                inchirieri.add(new Inchiriere(id, new Masina(idMasina, marca, model), dataInceput, dataSfarsit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inchirieri;
    }

    public void add(Inchiriere inchiriere) {
        try {
            if (!data.contains(inchiriere)) {
                Masina masina = inchiriere.getMasina();
                super.add(inchiriere);
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO inchirieri VALUES (?,?,?,?,?,?)")) {
                    statement.setInt(1, inchiriere.getId());
                    statement.setInt(2, masina.getId());
                    statement.setString(3, masina.getMarca());
                    statement.setString(4, masina.getModel());
                    statement.setDate(5, Date.valueOf(inchiriere.getDataInceput()));
                    statement.setDate(6, Date.valueOf(inchiriere.getDataSfarsit()));
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                throw new DuplicateObjectException("Cannot duplicate repository objects!");
            }
        } catch (RepositoryException e) {
            System.out.println("Error adding Inchiriere: " + e.getMessage());
        }
    }


    public void update(int id, Inchiriere newInchiriere) {
        super.update(id, newInchiriere);
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE inchirieri SET idMasina = ?, dataInceput = ?, dataSfarsit = ? WHERE id = ?")) {
            statement.setInt(1, newInchiriere.getMasina().getId());
            statement.setDate(2, Date.valueOf(newInchiriere.getDataInceput()));
            statement.setDate(3, Date.valueOf(newInchiriere.getDataSfarsit()));
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id) {
        super.delete(id);
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM inchirieri WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}