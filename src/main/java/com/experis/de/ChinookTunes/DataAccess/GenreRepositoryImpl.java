package com.experis.de.ChinookTunes.DataAccess;

import com.experis.de.ChinookTunes.Logging.Logger;
import com.experis.de.ChinookTunes.Models.Genre;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreRepositoryImpl implements GenreRepository {

    private final Logger logger;
    private SqlLiteHelper db;

    public GenreRepositoryImpl(Logger logger) {
        this.logger = logger;
        this.db = new SqlLiteHelper(logger);
    }

    @Override
    public List<Genre> getAllGenres(Integer limit, Integer offset, String orderBy) {
        ArrayList<Genre> Genres = null;

        try {
            String sql = db.prepareSql("select GenreId, Name from Genre",
                    limit, offset, orderBy);

            ResultSet resultSet = db.executeQuery(sql);

            // Process Results
            Genres = new ArrayList<>();
            while (resultSet.next()) {
                Genres.add(resultSetToGenres(resultSet));
            }
        }
        catch (Exception ex){
             logger.log("getAllGenres() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return Genres;
    }

    @Override
    public Genre getGenreById(Integer GenreId) {
        Genre Genre = null;

        try {
            ResultSet resultSet = db.executeQuery("select GenreId, Name WHERE GenreId = ?", GenreId);

            // Process Results
            Genre = resultSetToGenres(resultSet);
        }
        catch (Exception ex){
             logger.log("getGenreById("+GenreId+") failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return Genre;
    }

    private Genre resultSetToGenres(ResultSet resultSet) throws SQLException {
        Genre Genre = null;

        if (resultSet != null) {
            Genre = new Genre(
                    resultSet.getInt("GenreId"),
                    resultSet.getString("Name")
            );
        }
        return Genre;
    }
}
