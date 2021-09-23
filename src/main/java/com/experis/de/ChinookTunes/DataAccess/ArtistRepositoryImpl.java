package com.experis.de.ChinookTunes.DataAccess;

import com.experis.de.ChinookTunes.Logging.Logger;
import com.experis.de.ChinookTunes.Models.Artist;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class ArtistRepositoryImpl implements ArtistRepository {

    private final Logger logger;
    private SqlLiteHelper db;

    public ArtistRepositoryImpl(Logger logger) {
        this.logger = logger;
        this.db = new SqlLiteHelper(logger);
    }

    @Override
    public ArrayList<Artist> getAllArtists(Integer limit, Integer offset, String orderBy) {
        ArrayList<Artist> Artists = null;

        try {
            String sql = db.prepareSql("select ArtistId, Name from Artist",
                    limit, offset, orderBy);

            ResultSet resultSet = db.executeQuery(sql);

            // Process Results
            Artists = new ArrayList<>();
            while (resultSet.next()) {
                Artists.add(resultSetToArtist(resultSet));
            }
        }
        catch (Exception ex){
             logger.log("getAllArtists() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return Artists;
    }

    @Override
    public Artist getArtistById(Integer ArtistId) {
        Artist Artist = null;

        try {
            ResultSet resultSet = db.executeQuery("select ArtistId, Name WHERE ArtistId = ?", ArtistId);

            // Process Results
            Artist = resultSetToArtist(resultSet);
        }
        catch (Exception ex){
             logger.log("getArtistById("+ArtistId+") failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return Artist;
    }

    private Artist resultSetToArtist(ResultSet resultSet) throws SQLException {
        Artist Artist = null;

        if (resultSet != null) {
            Artist = new Artist(
                    resultSet.getInt("ArtistId"),
                    resultSet.getString("Name")
            );
        }
        return Artist;
    }
}
