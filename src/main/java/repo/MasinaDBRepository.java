package repo;

import domain.Inchiriere;
import domain.Masina;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MasinaDBRepository extends MemoryRepository<Masina> {
    private String JDBC_URL = "jdbc:sqlite:lab5.sqlite";
    Connection connection;

    public MasinaDBRepository() {
        openConnection();
        createTable();
        loadDataInMemory();
        //initData();
    }

    private void openConnection() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed()) {
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
            e.printStackTrace();
        }
    }

    private void createTable() {
        try (final Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS masini(id INT, marca VARCHAR(255), model VARCHAR(255));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDataInMemory() {
        for (Masina masina : getAll())
        {
            data.add(masina);
            //super.add(p);
        }
    }

    private void initData() {
        List<Masina> masini = new ArrayList<>();
        masini.add(new Masina(22, "Toyota", "Corolla"));
        masini.add(new Masina(23, "Honda", "Civic"));

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO masini VALUES (?,?,?)")) {
            for (Masina masina : masini) {
                statement.setInt(1, masina.getId());
                statement.setString(2, masina.getMarca());
                statement.setString(3, masina.getModel());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Masina> getAll() {
        List<Masina> masini = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM masini")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String marca = rs.getString("marca");
                String model = rs.getString("model");
                masini.add(new Masina(id, marca, model));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return masini;
    }


    public void add(Masina masina) throws RepositoryException {

        if (!data.contains(masina)) {
            super.add(masina);
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO masini VALUES (?,?,?)")) {
                statement.setInt(1, masina.getId());
                statement.setString(2, masina.getMarca());
                statement.setString(3, masina.getModel());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new DuplicateObjectException("Cannot duplicate repository objects!");
        }
    }


    public void update(int id, Masina newMasina) {
        int index = poz(id);
        if (index != -1) {
            super.update(id, newMasina);

            try (PreparedStatement statement = connection.prepareStatement("UPDATE masini SET marca=?, model=? WHERE id=?")) {
                statement.setString(1, newMasina.getMarca());
                statement.setString(2, newMasina.getModel());
                statement.setInt(3, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(int id) {
        int index = poz(id);
        if (index != -1) {
            super.delete(id);

            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM masini WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
