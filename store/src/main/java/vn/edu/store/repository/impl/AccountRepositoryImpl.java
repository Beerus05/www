package vn.edu.store.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.store.model.Account;
import vn.edu.store.repository.AccountRepository;
import vn.edu.store.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();

        String sql = " SELECT ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD, DATEOFBIRTH FROM account ORDER BY ID ";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }

    @Override
    public Optional<Account> findById(int id) {
        String sql = " SELECT ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD, DATEOFBIRTH FROM account WHERE ID = ? ";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAccount(rs));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public boolean save(Account account) {
        String sql = " INSERT INTO account (FIRSTNAME, LASTNAME, EMAIL, PASSWORD, DATEOFBIRTH) VALUES (?, ?, ?, ?, ?) ";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getPassword());

            if (account.getDateOfBirth() != null) {
                statement.setDate(
                        5,
                        java.sql.Date.valueOf(account.getDateOfBirth())
                );
            } else {
                statement.setNull(
                        5,
                        java.sql.Types.DATE
                );
            }

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Account account) {
        String sql = " UPDATE account SET FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, PASSWORD = ?, DATEOFBIRTH = ? WHERE ID = ? ";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getPassword());

            if (account.getDateOfBirth() != null) {
                statement.setDate( 5,                        java.sql.Date.valueOf(account.getDateOfBirth())
                );
            } else {
                statement.setNull(
                        5,
                        java.sql.Types.DATE
                );
            }

            statement.setInt(6, account.getId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = " DELETE FROM account WHERE ID = ? ";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Account mapResultSetToAccount(ResultSet rs) throws Exception {
        Account account = new Account();

        account.setId(
                rs.getInt("ID")
        );

        account.setFirstName(
                rs.getString("FIRSTNAME")
        );

        account.setLastName(
                rs.getString("LASTNAME")
        );

        account.setEmail(
                rs.getString("EMAIL")
        );

        account.setPassword(
                rs.getString("PASSWORD")
        );

        if (rs.getDate("DATEOFBIRTH") != null) {
            account.setDateOfBirth(
                    rs.getDate("DATEOFBIRTH").toLocalDate()
            );
        }

        return account;
    }
}
